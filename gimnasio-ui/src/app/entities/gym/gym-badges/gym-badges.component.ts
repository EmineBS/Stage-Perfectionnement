import { Component, OnInit } from '@angular/core';
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
import { NgbModal, NgbOffcanvas, OffcanvasDismissReasons } from '@ng-bootstrap/ng-bootstrap';

import { ITEMS_PER_PAGE, PAGE_HEADER, TOTAL_COUNT_RESPONSE_HEADER } from 'app/config/pagination.constants';
import { ASC, DESC, SORT, ITEM_DELETED_EVENT, DEFAULT_SORT_DATA } from 'app/config/navigation.constants';
import { IBadge, IPackBadge, NewPackBadge } from 'app/entities/badge/badge.model';
import { BadgeService, EntityArrayResponseType } from 'app/entities/badge/service/badge.service';
import { BadgeDeleteDialogComponent } from 'app/entities/badge/delete/badge-delete-dialog.component';
import { IGym } from '../gym.model';
import { IPack } from 'app/entities/pack/pack.model';
import { PackService } from 'app/entities/pack/service/pack.service';
import { BadgeFormGroup, BadgeFormService } from 'app/entities/badge/update/badge-form.service';

@Component({
  selector: 'jhi-gym-badges',
  templateUrl: './gym-badges.component.html',
  styleUrls: ['./gym-badges.component.scss'],
})
export class GymBadgesComponent implements OnInit {
  badges?: IBadge[];
  isLoading = false;

  predicate = 'id';
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
  filter = '';

  constructor(
    protected badgeService: BadgeService,
    protected activatedRoute: ActivatedRoute,
    public router: Router,
    protected modalService: NgbModal,
    private offcanvasService: NgbOffcanvas,
    protected packService: PackService,
    protected badgeFormService: BadgeFormService
  ) {}

  trackId = (_index: number, item: IBadge): number => this.badgeService.getBadgeIdentifier(item);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ gym }) => {
      this.gym = gym;
      this.idgym = gym.id;

      /* this.result = gym.badges.map((badge: { value: any; }) => badge.value);
       */
      this.load();
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
  }

  protected onResponseSuccess(response: EntityArrayResponseType): void {
    this.fillComponentAttributesFromResponseHeader(response.headers);
    const dataFromBody = this.fillComponentAttributesFromResponseBody(response.body);
    this.badges = dataFromBody;
    this.filterAndSort();
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
    if (statusState === 'inactif') {
      return 'bg-danger';
    }
    return 'bg-success';
  }

  badgeToUpdate: IBadge;

  open(content: any, badge: IBadge) {
    this.offcanvasService.open(content, { ariaLabelledBy: 'offcanvas-basic-title', backdrop: true, position: 'end' }).result.then(
      result => {
        this.closeResult = `Closed with: ${result}`;
      },
      reason => {
        this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
      }
    );
    console.log('badge to update : ' + badge.id);
    this.loadRelationshipsOptions();
    this.badgeToUpdate = badge;
  }

  private getDismissReason(reason: any): string {
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
  protected loadRelationshipsOptions(): void {
    this.packService
      .query()
      .pipe(map((res: HttpResponse<IPack[]>) => res.body ?? []))
      .pipe(map((packs: IPack[]) => this.packService.addPackToCollectionIfMissing<IPack>(packs, this.badgeToUpdate?.packId)))
      .subscribe((packs: IPack[]) => (this.packsSharedCollection = packs));
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

    //badge.id=this.badgeToUpdate.id

    console.log('pack to add  : ' + packtoSave.pack?.id);

    this.subscribeToSaveResponse(this.badgeService.createRel(packtoSave));
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPackBadge>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }
  successMessage = '';
  protected onSaveSuccess(): void {
    this.successMessage = 'Pack Activated successfully';
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }
}
