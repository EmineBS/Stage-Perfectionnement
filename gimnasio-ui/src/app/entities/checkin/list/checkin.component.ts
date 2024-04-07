import { Component, OnInit, ViewChild } from '@angular/core';
import { HttpHeaders } from '@angular/common/http';
import { ActivatedRoute, Data, ParamMap, Router } from '@angular/router';
import { combineLatest, filter, Observable, switchMap, tap } from 'rxjs';
import { NgbAlert, NgbModal, NgbOffcanvas, OffcanvasDismissReasons } from '@ng-bootstrap/ng-bootstrap';

import { ICheckin } from '../checkin.model';

import { ITEMS_PER_PAGE, PAGE_HEADER, TOTAL_COUNT_RESPONSE_HEADER } from 'app/config/pagination.constants';
import { ASC, DESC, SORT, ITEM_DELETED_EVENT, DEFAULT_SORT_DATA } from 'app/config/navigation.constants';
import { EntityArrayResponseType, CheckinService } from '../service/checkin.service';
import { CheckinDeleteDialogComponent } from '../delete/checkin-delete-dialog.component';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { SessionStorageService } from 'ngx-webstorage';
import { NotifierService } from 'angular-notifier';
import { CHECKIN_STATUS } from 'app/entities/enumerations/checkinStatus';

@Component({
  selector: 'jhi-checkin',
  templateUrl: './checkin.component.html',
})
export class CheckinComponent implements OnInit {
  checkins?: ICheckin[];
  isLoading = false;

  closeResult = '';
  predicate = 'id';
  ascending = true;
  itemsPerPage = ITEMS_PER_PAGE;
  totalItems = 0;
  page = 1;

  @ViewChild('myAlert') myAlert: NgbAlert;

  constructor(
    protected checkinService: CheckinService,
    protected activatedRoute: ActivatedRoute,
    public router: Router,
    protected modalService: NgbModal,
    private offcanvasService: NgbOffcanvas,
    private sessionStorageService: SessionStorageService,
    private notifier: NotifierService
  ) {}

  trackId = (_index: number, item: ICheckin): number => this.checkinService.getCheckinIdentifier(item);
  idgym = this.sessionStorageService.retrieve('gym').id;
  form = new FormGroup({
    badge: new FormControl('', [Validators.required]),
  });

  ngOnInit(): void {
    this.load();
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
    const sort = (params.get(SORT) ?? data[DEFAULT_SORT_DATA]).split(',');
    this.predicate = sort[0];
    this.ascending = sort[1] === ASC;
  }

  protected onResponseSuccess(response: EntityArrayResponseType): void {
    this.fillComponentAttributesFromResponseHeader(response.headers);
    const dataFromBody = this.fillComponentAttributesFromResponseBody(response.body);
    this.checkins = dataFromBody;
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
    return this.checkinService.checkinGym(this.idgym, queryObject).pipe(tap(() => (this.isLoading = false)));
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

  /* Offcanvas open */
  open(content: any) {
    this.checkin();
    this.offcanvasService.open(content, { ariaLabelledBy: 'offcanvas-basic-title', backdrop: true, position: 'end' }).result.then(
      result => {
        this.closeResult = `Closed with: ${result}`;
      },
      reason => {
        this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
      }
    );
  }

  successMessage = 'No pack available for this Badge!';
  alertYype = 'danger';
  private getDismissReason(reason: any): string {
    if (reason === OffcanvasDismissReasons.ESC) {
      return 'by pressing ESC';
    } else if (reason === OffcanvasDismissReasons.BACKDROP_CLICK) {
      return 'by clicking on the backdrop';
    } else {
      return `with: ${reason}`;
    }
  }

  getBadgeClass(statusState: string): string {
    if (statusState === 'CANCELED' || statusState === 'REFUSED') {
      return 'bg-danger';
    } else if (statusState === 'PENDING') {
      return 'bg-warning';
    }
    return 'bg-success';
  }

  message: string;
  badge: ICheckin;

  errrorMessage: string;
  checkin(): void {
    this.message = this.form.value['badge'] as string;

    this.checkinService.checkin(this.message).subscribe(
      data => {
        console.log('id checkin for data : ' + data.body?.id);
        this.badge = data.body!;

        this.load();
      },

      error => {
        this.errrorMessage = error.error.detail;
      }
    );
  }

  cancelCheckin(checkin: ICheckin): void {
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

    this.checkinService.partialUpdate(checkin).subscribe({});
  }

  formatId(id: number): string {
    return id.toString().padStart(4, '0');
  }

  confirmCheckin(checkin: ICheckin) {
    console.log('checking... ', checkin);
    checkin.status = CHECKIN_STATUS.CONFIRMED;
    // this.checkinService.partialUpdate(checkin).subscribe(() => {
    //   this.showNotification("success", "checkin confirmed susscessfully")
    //   this.load();
    // });
    this.checkinService.checkinConfirm(checkin).subscribe(() => {
      this.showNotification('success', 'checkin confirmed susscessfully');
      this.load();
    });
  }

  public showNotification(type: string, message: string): void {
    console.log('notifying:' + message);
    this.notifier.notify(type, message);
  }
}
