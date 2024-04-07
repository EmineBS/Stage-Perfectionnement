import { HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/service/user.service';

import { HttpHeaders } from '@angular/common/http';
import { ActivatedRoute, Data, ParamMap, Router } from '@angular/router';
import { combineLatest, filter, Observable, switchMap, tap } from 'rxjs';
import { map } from 'rxjs';
import { IGym } from '../../gym.model';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { EntityArrayResponseType, UsergymService } from '../service/user.service';
import { IuserGym, NewUserGym } from './user.model';
import { Imodal } from 'app/layouts/modal/Imodal.model';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ModalComponent } from 'app/layouts/modal/modal.component';
import { ITEMS_PER_PAGE, PAGE_HEADER, TOTAL_COUNT_RESPONSE_HEADER } from 'app/config/pagination.constants';
import { ASC, DESC, SORT, ITEM_DELETED_EVENT, DEFAULT_SORT_DATA } from 'app/config/navigation.constants';
import { DeleteUserFromGymComponent } from '../delete-user-from-gym/delete-user-from-gym.component';
import { ViewUserModalComponent } from '../view-user-modal/view-user-modal.component';

@Component({
  selector: 'jhi-gym-users',
  templateUrl: './gym-users.component.html',
  styleUrls: ['./gym-users.component.scss'],
})
export class GymUsersComponent implements OnInit {
  options = [];

  isSaving = false;
  gym: IGym | null = null;
  idgym: number;
  usersSharedCollection: IUser[] = [];
  usersSharedCollectionv1: IUser[] = [];

  selectedOptions: string[] = [];
  gymName: string;
  modal = {} as Imodal;
  isLoading = false;

  predicate = 'id';
  users?: IuserGym[];
  ascending = true;

  itemsPerPage = ITEMS_PER_PAGE;
  totalItems = 0;
  page = 1;
  loginList: string[];

  constructor(
    protected userService: UserService,
    protected UsergymService: UsergymService,
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

  compareUser = (o1: IUser | null, o2: IUser | null): boolean => this.userService.compareUser(o1, o2);

  protected loadRelationshipsOptions(): void {
    this.userService
      .queryAdmin()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((userToadd: IUser[]) => this.userService.addUserToCollectionIfMissing<IUser>(userToadd, this.gym?.user)))
      .subscribe((userToadd: IUser[]) => (this.usersSharedCollection = userToadd));
  }

  save() {
    const uid = this.form.value['userToadd'] as string;
    const userGymItem: NewUserGym = {
      id: null,
      userId: uid,
      gymId: this.idgym,
    };

    this.UsergymService.create(userGymItem).subscribe({
      next: () => {
        this.modal.title = 'User Successfully Added to Gym';
        this.modal.body = 'The user has been successfully added to the gym.';
        this.modalShow(this.modal);
        this.load();
      },
      error: () => {
        this.modal.title = 'Failed to Add User to Gym';
        this.modal.body = 'The attempt to add the user to the gym Failed.';
        this.modalShow(this.modal);
        this.load();
      },
    });
  }
  form = new FormGroup({
    userToadd: new FormControl('', [Validators.required]),
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
    this.users = dataFromBody;
    this.loginList = this.users.map(user => user.userId!);
    this.usersSharedCollectionv1 = this.usersSharedCollection;
    this.usersSharedCollection = this.usersSharedCollection.filter(user => !this.loginList.includes(user.login!));
  }

  protected fillComponentAttributesFromResponseBody(data: IuserGym[] | null): IuserGym[] {
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
    return this.UsergymService.gymUsers(this.idgym, queryObject).pipe(tap(() => (this.isLoading = false)));
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

  delete(user: IuserGym): void {
    const modalRef = this.modalService.open(DeleteUserFromGymComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.user = user;
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

  viewDetails(user: IuserGym): void {
    const modalRef = this.modalService.open(ViewUserModalComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.user = this.findUserByLogin(user.userId!);
  }

  findUserByLogin(login: string): IUser | undefined {
    return this.usersSharedCollectionv1.find(user => user.login === login);
  }

  formatId(id: number): string {
    return id.toString().padStart(4, '0');
  }
}
