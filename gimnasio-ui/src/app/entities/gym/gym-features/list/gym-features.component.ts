import { HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { IFeature } from 'app/entities/feature/feature.model';

import { HttpHeaders } from '@angular/common/http';
import { ActivatedRoute, Data, ParamMap, Router } from '@angular/router';
import { combineLatest, filter, Observable, switchMap, tap } from 'rxjs';
import { map } from 'rxjs';
import { IGym } from '../../gym.model';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { EntityArrayResponseType, GymFeatureService } from '../service/gym-features.service';
import { IGymFeature, NewGymFeature } from './feature.model';
import { Imodal } from 'app/layouts/modal/Imodal.model';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ModalComponent } from 'app/layouts/modal/modal.component';
import { ITEMS_PER_PAGE, PAGE_HEADER, TOTAL_COUNT_RESPONSE_HEADER } from 'app/config/pagination.constants';
import { ASC, DESC, SORT, ITEM_DELETED_EVENT, DEFAULT_SORT_DATA } from 'app/config/navigation.constants';
import { DeleteFeatureFromGymComponent } from '../delete-feature-from-gym/delete-feature-from-gym.component';
import { ViewUserModalComponent } from '../view-feature-modal/view-user-modal.component';
import { FeatureService } from 'app/entities/feature/service/feature.service';

@Component({
  selector: 'jhi-gym-features',
  templateUrl: './gym-features.component.html',
  styleUrls: ['./gym-features.component.scss'],
})
export class GymFeautersComponent implements OnInit {
  options = [];

  isSaving = false;
  gym: IGym | null = null;
  idgym: number;
  featuresSharedCollection: IFeature[] = [];
  featuresSharedCollectionv1: IFeature[] = [];

  selectedOptions: string[] = [];
  gymName: string;
  modal = {} as Imodal;
  isLoading = false;

  predicate = 'id';
  features?: IGymFeature[];
  ascending = true;

  itemsPerPage = ITEMS_PER_PAGE;
  totalItems = 0;
  page = 1;
  featuresList: string[];

  constructor(
    protected featureService: FeatureService,
    protected gymFeatureService: GymFeatureService,
    protected activatedRoute: ActivatedRoute,
    public router: Router,
    private modalService: NgbModal
  ) {}

  ngOnInit(): void {
    this.loadRelationshipsOptions();
    this.activatedRoute.data.subscribe(({ gym }) => {
      this.gym = gym;
      this.idgym = gym.id;
      this.gymName = gym.name;

      /* this.result = gym.packs.map((pack: { value: any; }) => pack.value);
       */
      this.load();
    });
  }

  compareUser = (o1: IFeature | null, o2: IFeature | null): boolean => this.featureService.compareFeature(o1, o2);

  protected loadRelationshipsOptions(): void {
    this.featureService
      .query()
      .pipe(map((res: HttpResponse<IFeature[]>) => res.body ?? []))
      .pipe(map((featureToadd: IFeature[]) => this.featureService.addFeatureToCollectionIfMissing<IFeature>(featureToadd, this.gym?.feature)))
      .subscribe((featureToAdd: IFeature[]) => (this.featuresSharedCollection = featureToAdd));
  }

  save() {
    const featureId = this.form.value['featureId'] as string;
    console.log(" the feature id is "+ featureId);
    const userGymItem: NewGymFeature = {
      id: null,
      featureId: parseInt(featureId),
      gymId: this.idgym,
    };
    console.log(" the userGymItem id is "+ userGymItem);

    this.gymFeatureService.create(userGymItem).subscribe({
      next: () => {
        this.modal.title = 'Feature Successfully Added to Gym';
        this.modal.body = 'The Feature has been successfully added to the gym.';
        this.modalShow(this.modal);
        this.load();
      },
      error: () => {
        this.modal.title = 'Failed to Add Feature to Gym';
        this.modal.body = 'The attempt to add the Feature to the gym Failed.';
        this.modalShow(this.modal);
        this.load();
      },
    });
  }
  form = new FormGroup({
    featureId: new FormControl('', [Validators.required]),
  });

  get f() {
    return this.form.controls;
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
      tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
      switchMap(() => this.queryBackend(this.page, this.predicate, this.ascending))
    );
  }

  protected fillComponentAttributeFromRoute(params: ParamMap, data: Data): void {
    const page = params.get(PAGE_HEADER);
    this.page = +(page ?? 1);
    this.predicate = 'id';
    this.ascending = ASC === ASC;
  }

  protected onResponseSuccess(response: EntityArrayResponseType): void {
    this.fillComponentAttributesFromResponseHeader(response.headers);
    const dataFromBody = this.fillComponentAttributesFromResponseBody(response.body);
    this.features = dataFromBody;
    this.featuresList = this.features.map(feature => feature.feature?.name!);
    this.featuresSharedCollectionv1 = this.featuresSharedCollection;
    this.featuresSharedCollection = this.featuresSharedCollection.filter(feature => !this.featuresList.includes(feature.name!));
  }

  protected fillComponentAttributesFromResponseBody(data: IGymFeature[] | null): IGymFeature[] {
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
    return this.gymFeatureService.gymFeatures(this.idgym, queryObject).pipe(tap(() => (this.isLoading = false)));
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

  modalShow(modal: Imodal): void {
    const modalRef = this.modalService.open(ModalComponent, { size: modal.size, backdropClass: modal.backdrop, centered: true });
    modalRef.componentInstance.modalTitle = modal.title;
    modalRef.componentInstance.modalBody = modal.body;
    modalRef.closed.subscribe();
  }

  delete(feature: IGymFeature): void {
    const modalRef = this.modalService.open(DeleteFeatureFromGymComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.feature = feature;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed
      .pipe(
        filter(reason => reason === ITEM_DELETED_EVENT),
        switchMap(() => this.loadFromBackendWithRouteInformations())
      )
      .subscribe({
        next: (res: EntityArrayResponseType) => {
          this.onResponseSuccess(res);
          this.loadRelationshipsOptions();
          this.load();
        },
      });
  }

  viewDetails(feature: IGymFeature): void {
    const modalRef = this.modalService.open(ViewUserModalComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.user = this.findFeatureByName(feature.feature!.name!);
  }

  findFeatureByName(name: string): IFeature | undefined {
    return this.featuresSharedCollectionv1.find(feature => feature.name === name);
  }

  formatId(id: number | null): string {
    return id!.toString().padStart(4, '0');
  }
}
