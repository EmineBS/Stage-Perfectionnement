import { Component, OnInit } from '@angular/core';
import { HttpHeaders } from '@angular/common/http';
import { ActivatedRoute, Data, ParamMap, Router } from '@angular/router';
import { combineLatest, debounceTime, distinctUntilChanged, filter, map, Observable, OperatorFunction, switchMap, tap } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITEMS_PER_PAGE, PAGE_HEADER, TOTAL_COUNT_RESPONSE_HEADER } from 'app/config/pagination.constants';
import { ASC, DESC, SORT, ITEM_DELETED_EVENT, DEFAULT_SORT_DATA } from 'app/config/navigation.constants';
import { IPack } from 'app/entities/pack/pack.model';
import { PackService, EntityArrayResponseTypes, EntityArrayResponseTypesRS } from 'app/entities/pack/service/pack.service';
import { IBadge, IPackBadge, IPackBadgeRS } from 'app/entities/badge/badge.model';
import { BadgeService } from 'app/entities/badge/service/badge.service';

@Component({
  selector: 'jhi-profile-pack',
  templateUrl: './profile-pack.component.html',
  styleUrls: ['./profile-pack.component.scss'],
})
export class ProfilePackComponent implements OnInit {
  packs?: IPackBadgeRS[];
  isLoading = false;

  predicate = 'id';
  ascending = true;
  idbadge: number;
  itemsPerPage = ITEMS_PER_PAGE;
  result: any[] = [];

  totalItems = 0;
  page = 1;

  searching = false;
  searchFailed = false;
  Filtredpacks?: IPackBadgeRS[];
  filter = '';
  data: IPackBadge;

  constructor(
    protected packService: PackService,
    protected badgeService: BadgeService,
    protected activatedRoute: ActivatedRoute,
    public router: Router,
    protected modalService: NgbModal
  ) {}

  trackId = (_index: number, item: IPack): number => this.packService.getPackIdentifier(item);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ profile }) => {
      this.idbadge = profile.badgeId;
      this.load();

      /* this.result = gym.packs.map((pack: { value: any; }) => pack.value);
       */
    });
  }
  filterAndSort(): void {
    this.Filtredpacks = this.packs!.filter(pack => !this.filter || pack.pack?.name?.toLowerCase().includes(this.filter.toLowerCase()));
  }
  search: OperatorFunction<string, readonly string[]> = (text$: Observable<string>) =>
    text$.pipe(
      debounceTime(200),
      distinctUntilChanged(),
      map(term => (term.length < 1 ? [] : this.result.filter(v => v.toLowerCase().indexOf(term.toLowerCase()) > -1).slice(0, 10)))
    );

  load(): void {
    this.loadFromBackendWithRouteInformations().subscribe({
      next: (res: EntityArrayResponseTypesRS) => {
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

  protected loadFromBackendWithRouteInformations(): Observable<EntityArrayResponseTypesRS> {
    return combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data]).pipe(
      tap(([params, Data]) => this.fillComponentAttributeFromRoute(params, Data)),
      switchMap(() => this.queryBackend(this.page, this.predicate, this.ascending))
    );
  }

  protected fillComponentAttributeFromRoute(params: ParamMap, data: Data): void {
    const page = params.get(PAGE_HEADER);
    this.page = +(page ?? 1);
  }

  protected onResponseSuccess(response: EntityArrayResponseTypesRS): void {
    this.fillComponentAttributesFromResponseHeader(response.headers);
    const dataFromBody = this.fillComponentAttributesFromResponseBody(response.body);
    this.packs = dataFromBody;
    this.filterAndSort();
  }

  protected fillComponentAttributesFromResponseBody(data: IPackBadgeRS[] | null): IPackBadgeRS[] {
    return data ?? [];
  }

  protected fillComponentAttributesFromResponseHeader(headers: HttpHeaders): void {
    this.totalItems = Number(headers.get(TOTAL_COUNT_RESPONSE_HEADER));
  }

  protected queryBackend(page?: number, predicate?: string, ascending?: boolean): Observable<EntityArrayResponseTypesRS> {
    this.isLoading = true;
    const pageToLoad: number = page ?? 1;
    const queryObject = {
      page: pageToLoad - 1,
      size: this.itemsPerPage,
      sort: this.getSortQueryParam(predicate, ascending),
    };
    return this.packService.queryBySession(queryObject).pipe(tap(() => (this.isLoading = false)));
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

  getpackClass(statusState: string): string {
    if (statusState === 'ACTIVE') {
      return 'bg-success';
    }
    return 'bg-danger';
  }

  cancelPack(pack: IPackBadge) {
    pack.status = 'RELEASED';
    this.badgeService.partialUpdateRel(pack).subscribe(() => {
      this.load();
    });
  }
}
