import { Component, OnInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data, ParamMap, Router } from '@angular/router';
import { combineLatest, filter, Observable, switchMap, tap } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IUser } from '../user.model';

import { ITEMS_PER_PAGE, PAGE_HEADER, TOTAL_COUNT_RESPONSE_HEADER } from 'app/config/pagination.constants';
import { ASC, DESC, SORT, ITEM_DELETED_EVENT, DEFAULT_SORT_DATA } from 'app/config/navigation.constants';
import { EntityArrayResponseType, UserService } from '../service/user.service';
import { UserDeleteDialogComponent } from '../delete/user-delete-dialog.component';
import { NotifierService } from 'angular-notifier';
// import { UserDeleteDialogComponent } from '../delete/user-delete-dialog.component';

@Component({
  selector: 'jhi-user',
  templateUrl: './user.component.html',
})
export class UserComponent implements OnInit {
  users: IUser[] | null;
  isLoading = false;

  predicate = 'id';
  ascending = true;

  itemsPerPage = ITEMS_PER_PAGE;
  totalItems = 0;
  page = 1;

  constructor(
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    public router: Router,
    protected modalService: NgbModal,
    private notifier: NotifierService
  ) {}

  trackId = (_index: number, item: IUser): string => this.userService.getUserIdentifier(item);

  ngOnInit(): void {
    console.log("test call comp")
    this.load();
  }

  delete(user: IUser): void {
    const modalRef = this.modalService.open(UserDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.user = user;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed
      .pipe(
        filter(reason => reason === ITEM_DELETED_EVENT)
      )
      .subscribe({
        next: () => {
          this.showNotification('success', 'user deleted susscessfully');
          this.load();
        },
      });
  }

  load(): void {
    // this.loadFromBackendWithRouteInformations().subscribe({
    //   next: (res: EntityArrayResponseType) => {
    //     // this.onResponseSuccess(res);
    //     this.userService.queryAdmin().pipe(tap(() => (this.isLoading = false)));
    //   },
    // });
    this.userService.queryAdmin().subscribe(
      (response: HttpResponse<IUser[]>): void => {
        this.users = response.body;
      },
      (error) => {
        console.error(error);
      }
    );
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
    const sort = (params.get(SORT) ?? data[DEFAULT_SORT_DATA]).split(',');
    this.predicate = sort[0];
    this.ascending = sort[1] === ASC;
  }

  protected onResponseSuccess(response: EntityArrayResponseType): void {
    this.fillComponentAttributesFromResponseHeader(response.headers);
    const dataFromBody = this.fillComponentAttributesFromResponseBody(response.body);
    this.users = dataFromBody;
  }

  protected fillComponentAttributesFromResponseBody(data: IUser[] | null): IUser[] {
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
    // return this.userService.query(queryObject).pipe(tap(() => (this.isLoading = false)));
    return this.userService.queryAdmin().pipe(tap(() => (this.isLoading = false)));
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

  public showNotification(type: string, message: string): void {
    console.log('notifying:' + message);
    this.notifier.notify(type, message);
  }
}
