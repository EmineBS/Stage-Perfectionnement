import { Component, OnInit, ViewChild } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data, ParamMap, Router } from '@angular/router';
import {
  combineLatest,
  debounceTime,
  distinctUntilChanged,
  filter,
  finalize,
  map,
  Observable,
  OperatorFunction,
  switchMap,
  tap,
} from 'rxjs';
import { NgbAlert, NgbModal, NgbOffcanvas, OffcanvasDismissReasons } from '@ng-bootstrap/ng-bootstrap';

import { ITEMS_PER_PAGE, PAGE_HEADER, TOTAL_COUNT_RESPONSE_HEADER } from 'app/config/pagination.constants';
import { ASC, DESC, SORT, ITEM_DELETED_EVENT, DEFAULT_SORT_DATA, ITEM_CREATE_EVENT } from 'app/config/navigation.constants';
import { IBadge, IPackBadge, NewPackBadge, NewProgress, NewSaveProgress, ICriteriaValue } from 'app/entities/badge/badge.model';
import { BadgeService, EntityArrayResponseType } from 'app/entities/badge/service/badge.service';
import { BadgeDeleteDialogComponent } from 'app/entities/badge/delete/badge-delete-dialog.component';
import { IPack } from 'app/entities/pack/pack.model';
import { PackService } from 'app/entities/pack/service/pack.service';
import { BadgeFormGroup, BadgeFormService } from 'app/entities/badge/update/badge-form.service';
import { IGym, IGymFeature } from '../../gym.model';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import { ProfileService } from 'app/entities/profile/service/profile.service';
import { NewProfile } from 'app/entities/profile/profile.model';
import { STATUS } from 'app/entities/enumerations/status.model';
import { DeactivateBadgeComponent } from './deactivate-badge/deactivate-badge.component';
import { ActivateBadgeComponent } from './activate-badge/activate-badge.component';
import { CriteriaService } from 'app/entities/criteria/service/criteria.service';
import { ICriteria } from 'app/entities/criteria/criteria.model';

import { NotifierService } from 'angular-notifier';
import { GymService } from '../../service/gym.service';

@Component({
  selector: 'jhi-gym-badges',
  templateUrl: './gym-badges.component.html',
  styleUrls: ['./gym-badges.component.scss'],
})
export class GymBadgesComponent implements OnInit {
  activeMenu = -1;

  @ViewChild('myAlert') myAlert: NgbAlert;
  badges?: IBadge[];
  isLoading = false;

  predicate = '';
  ascending = true;
  gym: IGym;
  idgym: number;
  itemsPerPage = ITEMS_PER_PAGE;
  result: any[] = [];

  totalItems = 0;
  page = 1;
  closeResult = '';
  isSaving = false;

  searching = false;
  searchFailed = false;
  FiltredBadges?: IBadge[];
  filter = 'id';

  notAssigned : string = 'NOTASSIGNED'
   
  featureDesgination: Boolean = false;

  formProgress = new FormGroup({});

  constructor(
    protected badgeService: BadgeService,
    protected activatedRoute: ActivatedRoute,
    public router: Router,
    protected modalService: NgbModal,
    private offcanvasService: NgbOffcanvas,
    protected packService: PackService,
    protected criteriaService: CriteriaService,
    protected badgeFormService: BadgeFormService,
    private formBuilder: FormBuilder,
    private profileService: ProfileService,
    private notifier: NotifierService,
    private gymService: GymService
  ) {}

  trackId = (_index: number, item: IBadge): number => this.badgeService.getBadgeIdentifier(item);

  criteriasSharedCollection: ICriteria[] = [];

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ gym }) => {
      this.gym = gym;
      this.idgym = gym.id;
    });
    this.gymService.findFeatureDesignation(this.idgym)
      .subscribe(
        (response: HttpResponse<IGymFeature>): void => {
          if(response.body)this.featureDesgination = true
        },
        (error) => {
          console.error(error);
        }
      );
    this.load();
    this.criteriaService
      .findCriteriasPerGym(this.idgym)
      .pipe(map((res: HttpResponse<IPack[]>) => res.body ?? []))
      .subscribe((criterias: ICriteria[]) => {
        this.criteriasSharedCollection = criterias;

        this.criteriasSharedCollection.forEach(x => {
          this.formProgress.addControl(x.id?.toString(), new FormControl('', Validators.required));
        });
      });
  }

  filterAndSort(): void {
    this.FiltredBadges = this.badges!.filter(badge => !this.filter || badge.uid?.toLowerCase().includes(this.filter.toLowerCase()));
  }
  search: OperatorFunction<string, readonly string[]> = (text$: Observable<string>) =>
    text$.pipe(
      debounceTime(200),
      distinctUntilChanged(),
      map(term => (term.length < 1 ? [] : this.result.filter(v => v.toLowerCase().indexOf(term.toLowerCase()) > -1).slice(0, 10)))
    );

  delete(badge: IBadge): void {
    const modalRef = this.modalService.open(BadgeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.badge = badge;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed
      .pipe(
        filter(reason => reason === ITEM_DELETED_EVENT),
        switchMap(() => this.loadFromBackendWithRouteInformations())
      )
      .subscribe({
        next: (res: EntityArrayResponseType) => {
          this.onResponseSuccess(res);
        },
      });
  }

  load(): void {
    this.loadFromBackendWithRouteInformations().subscribe({
      next: (res: EntityArrayResponseType) => {
        this.onResponseSuccess(res);
      },
    });
  }

  navigateToWithComponentValues(): void {
    this.handleNavigation(this.page, this.predicate, this.ascending);
  }

  navigateToPage(page = this.page): void {
    this.handleNavigation(page, this.predicate, this.ascending);
  }

  protected loadFromBackendWithRouteInformations(): Observable<EntityArrayResponseType> {
    return combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data]).pipe(
      tap(([params, Data]) => this.fillComponentAttributeFromRoute(params, Data)),
      switchMap(() => this.queryBackend(this.page, this.predicate, this.ascending))
    );
  }

  protected fillComponentAttributeFromRoute(params: ParamMap, data: Data): void {
    const page = params.get(PAGE_HEADER);
    this.page = +(page ?? 1);
    const sort = (params.get(SORT) ?? data[DEFAULT_SORT_DATA])?.split(',');
    if (sort) {
      this.predicate = sort[0];
      this.ascending = sort[1] === ASC;
    }
  }

  protected onResponseSuccess(response: EntityArrayResponseType): void {
    this.fillComponentAttributesFromResponseHeader(response.headers);
    const dataFromBody = this.fillComponentAttributesFromResponseBody(response.body);
    this.badges = dataFromBody;
  }

  protected fillComponentAttributesFromResponseBody(data: IBadge[] | null): IBadge[] {
    return data ?? [];
  }

  protected fillComponentAttributesFromResponseHeader(headers: HttpHeaders): void {
    this.totalItems = Number(headers.get(TOTAL_COUNT_RESPONSE_HEADER));
  }

  protected queryBackend(page?: number, predicate?: string, ascending?: boolean): Observable<EntityArrayResponseType> {
    this.isLoading = true;
    const pageToLoad: number = page ?? 1;
    const queryObject = {
      page: pageToLoad - 1,
      size: this.itemsPerPage,
      sort: this.getSortQueryParam(predicate, ascending),
    };
    return this.badgeService.findBadgefromGym(this.idgym, queryObject).pipe(tap(() => (this.isLoading = false)));
  }

  protected handleNavigation(page = this.page, predicate?: string, ascending?: boolean): void {
    const queryParamsObj = {
      page,
      size: this.itemsPerPage,
      sort: this.getSortQueryParam(predicate, ascending),
    };

    this.router.navigate(['./'], {
      relativeTo: this.activatedRoute,
      queryParams: queryParamsObj,
    });
  }

  protected getSortQueryParam(predicate = this.predicate, ascending = this.ascending): string[] {
    const ascendingQueryParam = ascending ? ASC : DESC;
    if (predicate === '') {
      return [];
    } else {
      return [predicate + ',' + ascendingQueryParam];
    }
  }

  getBadgeClass(statusState: string): string {
    // inactivated is a html text - NOTACTIVATED is the status stocked in the data base
    if (statusState === 'inactif' || statusState === 'NOTACTIVATED') {
      return 'bg-danger';
    }
    return 'bg-success';
  }

  getBadgeAccountClass(badgeAccount: string): string {
    // inactivated is a html text - NOTACTIVATED is the status stocked in the data base
    console.log("status test + ", badgeAccount)
    if (badgeAccount === 'NOTASSIGNED' || !badgeAccount ) {
      return 'bg-danger';
    }
    return 'bg-success';
  }

  badgeToUpdate: IBadge;
  form: FormGroup;

  open(content: any, badge: IBadge, menu: number) {
    this.activeMenu = menu;
    this.offcanvasService.open(content, { ariaLabelledBy: 'offcanvas-basic-title', backdrop: true, position: 'end' }).result.then(
      result => {
        this.closeResult = `Closed with: ${result}`;
        this.activeMenu = -1;
      },
      reason => {
        this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
        this.activeMenu = -1;
        this.load();
      }
    );
    this.successMessage = '';
    this.failedMessage = '';
    this.successActivation = '';
    this.failedActivation = '';
    if (menu == 0) {
      this.data = badge;
      this.form = this.formBuilder.group({
        mail: ['', [Validators.required, Validators.email]],
        tel: ['', Validators.required],
        firstName: [''],
        lastName: [''],
      });
    } else if (menu == 1) {
      this.loadRelationshipsOptions();
      this.badgeToUpdate = badge;
    } else if (menu == 2){
      this.badgeToUpdate = badge;
    }
  }

  private getDismissReason(reason: any): string {
    this.editForm.get('packId')?.reset();
    if (reason === OffcanvasDismissReasons.ESC) {
      return 'by pressing ESC';
    } else if (reason === OffcanvasDismissReasons.BACKDROP_CLICK) {
      return 'by clicking on the backdrop';
    } else {
      return `with: ${reason}`;
    }
  }

  packsSharedCollection: IPack[] = [];

  comparePack = (o1: IPack | null, o2: IPack | null): boolean => this.packService.comparePack(o1, o2);
  compareCriteria = (o1: ICriteria | null, o2: ICriteria | null): boolean => this.criteriaService.compareCriteria(o1, o2);
  protected loadRelationshipsOptions(): void {
    this.packService
      .findPacksPerGym(this.idgym)
      .pipe(map((res: HttpResponse<IPack[]>) => res.body ?? []))
      .pipe(map((packs: IPack[]) => this.packService.addPackToCollectionIfMissing<IPack>(packs, this.badgeToUpdate?.packId)))
      .subscribe((packs: IPack[]) => (this.packsSharedCollection = packs));
  }

  protected loadRelationshipsOptionsProgress(): void {
    this.criteriaService
      .findCriteriasPerGym(this.idgym)
      .pipe(map((res: HttpResponse<IPack[]>) => res.body ?? []))
      //.pipe(map((packs: IPack[]) => this.packService.addPackToCollectionIfMissing<IPack>(packs, this.badgeToUpdate?.packId)))
      .subscribe((criterias: ICriteria[]) => (this.criteriasSharedCollection = criterias));
  }

  previousState(): void {
    window.history.back();
  }

  editForm: BadgeFormGroup = this.badgeFormService.createBadgeFormGroup();
  data: IBadge;

  save(): void {
    this.isSaving = true;
    const badge = this.badgeFormService.getBadge(this.editForm);
    badge.uid = this.badgeToUpdate.uid;
    badge.id = this.badgeToUpdate.id;
    this.data = this.badgeToUpdate;
    this.data.packId = badge.packId;

    console.log('pack id is : ' + this.data.packId?.id);

    const packtoSave: NewPackBadge = { id: null, enabled: true, status: 'ACTIVE', badge: this.data, pack: this.data.packId };

    //badge.id=this.badgeToUpdate.i

    this.subscribeToSaveResponse(this.badgeService.createRel(packtoSave));
  }

  saveProgress(): void {
    const formData = this.formProgress.value;
    console.log('data recieved:' + JSON.stringify(formData));
    this.isSaving = true;

    const progressToSave: NewSaveProgress = { id: null, badgeId: this.badgeToUpdate?.id, criteriaValues: [] };

    Object.entries(formData).forEach(([key, value]) => {
      if (typeof value === 'string') {
        const val: string = value as string;
        const criteriaValue: ICriteriaValue = { idCriteria: parseInt(key), value: parseFloat(val) };
        progressToSave.criteriaValues?.push(criteriaValue);
      }
    });
    // console.log("data to send:" + JSON.stringify(progressToSave));
    // this.subscribeToSaveResponse(this.badgeService.createProgress(progressToSave));

    this.badgeService.createProgress(progressToSave).subscribe(() => {
      this.showNotification('success', 'Progress have been added');
      this.formProgress.reset();
      this.isSaving = false;
      this.offcanvasService.dismiss();
    });
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPackBadge>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  //this message displayed after adding the pack.
  successMessage = '';
  protected onSaveSuccess(): void {
    this.successMessage = 'Pack Activated successfully';
    if (this.activeMenu > -1) {
      this.offcanvasService.dismiss();
    }
  }

  failedMessage = '';
  protected onSaveError(): void {
    this.failedMessage = 'The badge is currently associated with an active pack';
  }

  protected onSaveFinalize(): void {
    setTimeout(() => {
      this.failedMessage = '';
      this.successMessage = '';
      this.editForm.get('packId')?.reset();
    }, 2000);
    this.isSaving = false;
  }

  //this function to change id displayed format (exemples : 0001, 0022,0300)
  formatId(id: number): string {
    return id.toString().padStart(4, '0');
  }

  successActivation = '';
  failedActivation = '';

  // For to assign badge form.
  submitAccountForm() {
    const badge = this.data;
    if (this.form.valid) {
      const { mail, tel, firstName, lastName } = this.form.value;
      const profile: NewProfile = {
        id: null,
        badgeId: badge.id,
        phoneNumber: tel,
        email: mail,
        lastName: lastName,
        name: firstName,
      };

      this.profileService.create(profile).subscribe(
        data => {
          this.successActivation = 'Badge assigned successfully';
          this.resetForm();
          this.load();
          this.offcanvasService.dismiss();
        },
        error => {
          this.failedActivation = error.error.detail;
        }
      );
    }
  }

  resetForm() {
    this.form.reset();
  }
  // ends For to activate badge Form.

  deactivateBadge(badge: IBadge): void {
    const modalRef = this.modalService.open(DeactivateBadgeComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.badge = badge;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed
      .pipe(
        filter(reason => reason === ITEM_DELETED_EVENT),
        switchMap(() => this.loadFromBackendWithRouteInformations())
      )
      .subscribe({
        next: (res: EntityArrayResponseType) => {
          this.onResponseSuccess(res);
        },
      });
  }

  activationBadge(badge: IBadge): void {
    const modalRef = this.modalService.open(ActivateBadgeComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.badge = badge;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed
      .pipe(
        filter(reason => reason === ITEM_CREATE_EVENT),
        switchMap(() => this.loadFromBackendWithRouteInformations())
      )
      .subscribe({
        next: (res: EntityArrayResponseType) => {
          this.onResponseSuccess(res);
        },
      });
  }

  public showNotification(type: string, message: string): void {
    console.log('notifying:' + message);
    this.notifier.notify(type, message);
  }
}
