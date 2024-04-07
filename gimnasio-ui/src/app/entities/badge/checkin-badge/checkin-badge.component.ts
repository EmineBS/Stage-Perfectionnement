import { Component, OnInit, ViewChild } from '@angular/core';
import { HttpHeaders } from '@angular/common/http';
import { ActivatedRoute, Data, ParamMap, Router } from '@angular/router';
import { combineLatest, debounceTime, distinctUntilChanged, filter, map, Observable, OperatorFunction, switchMap, tap } from 'rxjs';
import { NgbAlert, NgbModal, NgbOffcanvas, OffcanvasDismissReasons } from '@ng-bootstrap/ng-bootstrap';

import { ITEMS_PER_PAGE, PAGE_HEADER, TOTAL_COUNT_RESPONSE_HEADER } from 'app/config/pagination.constants';
import { ASC, DESC, SORT, ITEM_DELETED_EVENT, DEFAULT_SORT_DATA } from 'app/config/navigation.constants';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { CheckinService, EntityArrayResponseType } from 'app/entities/checkin/service/checkin.service';
import { ICheckin } from 'app/entities/checkin/checkin.model';
import { CheckinDeleteDialogComponent } from 'app/entities/checkin/delete/checkin-delete-dialog.component';
import { IBadge } from '../badge.model';

@Component({
  selector: 'jhi-checkin-badge',
  templateUrl: './checkin-badge.component.html',
  styleUrls: ['./checkin-badge.component.scss'],
})
export class CheckinBadgeComponent implements OnInit {
  checkins?: ICheckin[];
  isLoading = false;
  result: any[] = [];
  closeResult = '';
  predicate = 'id';
  ascending = true;

  itemsPerPage = ITEMS_PER_PAGE;
  totalItems = 0;
  searching = false;
  searchFailed = false;
  Filtredcheckins?: ICheckin[];
  filter = '';
  page = 1;
  badge: IBadge;

  constructor(
    protected checkinService: CheckinService,
    protected activatedRoute: ActivatedRoute,
    public router: Router,
    protected modalService: NgbModal,
    private offcanvasService: NgbOffcanvas
  ) {}

  trackId = (_index: number, item: ICheckin): number => this.checkinService.getCheckinIdentifier(item);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ badge }) => {
      this.badge = badge;
      this.load();

      /* this.result = gym.packs.map((pack: { value: any; }) => pack.value);
       */
    });
  }

  filterAndSort(): void {
    this.Filtredcheckins = this.checkins!.filter(
      pack => !this.filter || pack.createdDate?.toString().toLowerCase().includes(this.filter.toLowerCase())
    );
  }
  search: OperatorFunction<string, readonly string[]> = (text$: Observable<string>) =>
    text$.pipe(
      debounceTime(200),
      distinctUntilChanged(),
      map(term => (term.length < 1 ? [] : this.result.filter(v => v.toLowerCase().indexOf(term.toLowerCase()) > -1).slice(0, 10)))
    );

  delete(checkin: ICheckin): void {
    const modalRef = this.modalService.open(CheckinDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.checkin = checkin;
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
      tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
      switchMap(() => this.queryBackend(this.page, this.predicate, this.ascending))
    );
  }

  protected fillComponentAttributeFromRoute(params: ParamMap, data: Data): void {
    const page = params.get(PAGE_HEADER);
    this.page = +(page ?? 1);
    this.predicate = 'createdDate';
    this.ascending = false;
  }

  protected onResponseSuccess(response: EntityArrayResponseType): void {
    this.fillComponentAttributesFromResponseHeader(response.headers);
    const dataFromBody = this.fillComponentAttributesFromResponseBody(response.body);
    this.checkins = dataFromBody;
    this.filterAndSort();
  }

  protected fillComponentAttributesFromResponseBody(data: ICheckin[] | null): ICheckin[] {
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
    return this.checkinService.checkinBadge(this.badge.uid!, queryObject).pipe(tap(() => (this.isLoading = false)));
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
    if (statusState === 'CANCELED' || statusState === 'REFUSED') {
      return 'bg-danger';
    } else if (statusState == 'PENDING') {
      return 'bd-warning';
    }
    return 'bg-success';
  }
}
