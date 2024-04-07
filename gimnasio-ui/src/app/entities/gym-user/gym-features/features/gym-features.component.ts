import { Component, OnInit } from '@angular/core';
import { HttpHeaders } from '@angular/common/http';
import { ActivatedRoute, Data, ParamMap, Router } from '@angular/router';
import { combineLatest, debounceTime, distinctUntilChanged, filter, map, Observable, OperatorFunction, switchMap, tap } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITEMS_PER_PAGE, PAGE_HEADER, TOTAL_COUNT_RESPONSE_HEADER } from 'app/config/pagination.constants';
import { ASC, DESC, SORT, ITEM_DELETED_EVENT, DEFAULT_SORT_DATA } from 'app/config/navigation.constants';
import { IFeature } from 'app/entities/feature/feature.model';
import { FeatureService, EntityArrayResponseType } from 'app/entities/feature/service/feature.service';
import { FeatureDeleteDialogComponent } from 'app/entities/feature/delete/feature-delete-dialog.component';
import { IGym } from '../../gym.model';

@Component({
  selector: 'jhi-gym-features',
  templateUrl: './gym-features.component.html',
  styleUrls: ['./gym-features.component.scss'],
})
export class GymFeaturesComponent implements OnInit {
  features?: IFeature[];
  isLoading = false;

  predicate = 'id';
  ascending = true;
  gym: IGym;
  idgym: number;
  itemsPerPage = ITEMS_PER_PAGE;
  result: any[] = [];

  totalItems = 0;
  page = 1;

  searching = false;
  searchFailed = false;
  Filtredfeatures?: IFeature[];
  filter = '';

  constructor(
    protected featureService: FeatureService,
    protected activatedRoute: ActivatedRoute,
    public router: Router,
    protected modalService: NgbModal
  ) {}

  trackId = (_index: number, item: IFeature): number => this.featureService.getFeatureIdentifier(item);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ gym }) => {
      this.gym = gym;
      this.idgym = gym.id;

      /* this.result = gym.features.map((feature: { value: any; }) => feature.value);
       */
      this.load();
    });
  }
  filterAndSort(): void {
    this.Filtredfeatures = this.features!.filter(feature => !this.filter || feature.name?.toLowerCase().includes(this.filter.toLowerCase()));
  }
  search: OperatorFunction<string, readonly string[]> = (text$: Observable<string>) =>
    text$.pipe(
      debounceTime(200),
      distinctUntilChanged(),
      map(term => (term.length < 1 ? [] : this.result.filter(v => v.toLowerCase().indexOf(term.toLowerCase()) > -1).slice(0, 10)))
    );

  delete(feature: IFeature): void {
    const modalRef = this.modalService.open(FeatureDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
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
    this.features = dataFromBody;
    this.filterAndSort();
  }

  protected fillComponentAttributesFromResponseBody(data: IFeature[] | null): IFeature[] {
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
    return this.featureService.findFeaturesPerGymForUserGym(this.idgym, queryObject).pipe(tap(() => (this.isLoading = false)));
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

  getfeatureClass(statusState: string): string {
    if (statusState === 'inactif') {
      return 'bg-danger';
    }
    return 'bg-success';
  }
}
