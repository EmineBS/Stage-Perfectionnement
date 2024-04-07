import { ChangeDetectionStrategy, Component, OnInit, Output, TemplateRef, ViewChild } from '@angular/core';
import { ITEMS_PER_PAGE, PAGE_HEADER, TOTAL_COUNT_RESPONSE_HEADER } from 'app/config/pagination.constants';
import { IVisit, NewVisit } from '../../visit/visit.model';
import { ICalendar } from '../../calendar/calendar.model';
import { NgbModal, NgbOffcanvas } from '@ng-bootstrap/ng-bootstrap';

import { ActivatedRoute, Data, ParamMap, Router } from '@angular/router';
import { combineLatest, filter, Observable, Subject, switchMap, tap } from 'rxjs';
import {
  CalendarEvent,
  CalendarView,
  CalendarEventTimesChangedEvent,
  CalendarEventTitleFormatter,
  CalendarEventAction,
} from 'angular-calendar';
import { setHours, setMinutes, isSameDay } from 'date-fns';
import { CalendarService } from '../../calendar/service/calendar.service';
import { SessionStorageService } from 'ngx-webstorage';
import { EntityArrayResponseType, VisitService } from '../../visit/service/visit.service';
import { HttpHeaders } from '@angular/common/http';
import { ASC, DEFAULT_SORT_DATA, DESC, SORT } from 'app/config/navigation.constants';
import { colors } from '../../calendar/list/colors';
import { ModalCreateVisitComponent } from '../modal-create-visit/modal-create-visit.component';
import { isSameMonth } from 'ngx-bootstrap/chronos';
import { NotifierService } from 'angular-notifier';
import { VISIT_STATUS } from 'app/entities/enumerations/visitStatus';
import { TranslateService } from '@ngx-translate/core';
import { registerLocaleData } from '@angular/common';
import localeFr from '@angular/common/locales/fr';

@Component({
  selector: 'jhi-badge-calendar',
  templateUrl: './badge-calendar.component.html',
  styleUrls: ['./badge-calendar.component.scss'],
})
export class BadgeCalendarComponent implements OnInit {
  calendar?: ICalendar;
  isLoading = false;
  toggleMobileSidebar: boolean = false;
  predicate = 'id';
  ascending = true;
  activeDayIsOpen: boolean = false;
  visits?: IVisit[];
  itemsPerPage = ITEMS_PER_PAGE;
  totalItems = 0;
  page = 1;
  bsInlineValue = new Date();
  bsInlineRangeValue: Date[];
  maxDate = new Date();
  hourDuration = 50;
  hourSegments = 1;
  dayStartHour = 8;
  dayEndHour = 17;
  closeResult = '';

  locale: string = 'fr';

  // exclude weekends
  excludeDays: number[] = [0, 6];

  onDateChange(event: Date): void {
    this.viewDate = new Date(event);
    // Perform further operations with the selected date
  }

  /* Template default calendar */
  view: CalendarView = CalendarView.Day;

  CalendarView = CalendarView;

  viewDate: Date = new Date();

  visite: CalendarEvent;
  hourSegmentClicked(event: any): void {
    this.clickedDate = event;

    this.addVisit();
  }

  events: CalendarEvent[] = [];

  setView(view: CalendarView) {
    this.view = view;
  }

  clickedDate: Date;
  refresh = new Subject<void>();

  refreshView(): void {
    this.refresh.next();
  }
  eventTimesChanged({ event, newStart, newEnd }: CalendarEventTimesChangedEvent): void {
    event.start = newStart;
    event.end = newEnd;
    this.refresh.next();
  }

  constructor(
    protected calendarService: CalendarService,
    protected activatedRoute: ActivatedRoute,
    public router: Router,
    private sessionStorageService: SessionStorageService,
    private visitService: VisitService,
    protected modalService: NgbModal,
    private offcanvasService: NgbOffcanvas,
    private notifier: NotifierService,
    private translateService: TranslateService
  ) {
    this.maxDate.setDate(this.maxDate.getDate() + 7);
    this.bsInlineRangeValue = [this.bsInlineValue, this.maxDate];
    this.translateService.onLangChange.subscribe(lang=>{
      this.locale = lang.lang;
      this.refreshView()
  })
  }

  ngOnInit(): void {
    this.load();
    registerLocaleData(localeFr);
  }

  load() {
    this.calendarService.queryByBadgeSession().subscribe(data => {
      this.calendar = data.body!;
      this.hourSegments = 1;
      this.hourDuration = this.calendar.unit!;
      this.dayStartHour = parseInt(this.calendar.startHour?.slice(0, 2)!);
      this.dayEndHour = parseInt(this.calendar.endHour?.slice(0, 2)!);
      this.excludeDays = this.calendar.offDays!;
      //load all visits
      this.loadGymVisit();
    });
  }
  VisitGym: CalendarEvent;
  loadGymVisit(): void {
    this.loadFromBackendWithRouteInformations().subscribe({
      next: (res: EntityArrayResponseType) => {
        this.onResponseSuccess(res);
        this.refreshView();
      },
    });
  }

  protected onResponseSuccess(response: EntityArrayResponseType): void {
    this.fillComponentAttributesFromResponseHeader(response.headers);
    const dataFromBody = this.fillComponentAttributesFromResponseBody(response.body);
    this.visits = dataFromBody;
    this.events = [];
    for (const event of this.visits) {
      if (event.status != 'CANCELED' && event.status != 'DONE') {
        // prepare date to push it to event
        const dateToStart = new Date(event.fromDate!);
        const dateToFinish = new Date(event.toDate!);
        let visit: CalendarEvent;
        if (event.badgeId) {
          visit = {
            title: event.packName!,
            start: dateToStart,
            end: dateToFinish,
            color: colors.green,
            meta: event,
          };
        } else {
          visit = {
            title: 'not available',
            start: dateToStart,
            end: dateToFinish,
            color: colors.red,
          };
        }
        this.events.push(visit);
      }
    }
  }

  protected fillComponentAttributesFromResponseBody(data: IVisit[] | null): IVisit[] {
    return data ?? [];
  }

  protected fillComponentAttributesFromResponseHeader(headers: HttpHeaders): void {
    this.totalItems = Number(headers.get(TOTAL_COUNT_RESPONSE_HEADER));
  }

  protected loadFromBackendWithRouteInformations(): Observable<EntityArrayResponseType> {
    return combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data]).pipe(
      tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
      switchMap(() => this.queryBackendMine())
    );
  }

  protected queryBackendMine(): Observable<EntityArrayResponseType> {
    this.isLoading = true;
    return this.visitService.findAllByBadgeSession().pipe(tap(() => (this.isLoading = false)));
  }

  protected getSortQueryParam(predicate = this.predicate, ascending = this.ascending): string[] {
    const ascendingQueryParam = ascending ? ASC : DESC;
    if (predicate === '') {
      return [];
    } else {
      return [predicate + ',' + ascendingQueryParam];
    }
  }

  protected fillComponentAttributeFromRoute(params: ParamMap, data: Data): void {
    const page = params.get(PAGE_HEADER);
    this.page = +(page ?? 1);
    const sort = (params.get(SORT) ?? data[DEFAULT_SORT_DATA]).split(',');
    this.predicate = sort[0];
    this.ascending = sort[1] === ASC;
  }

  EventColor(status: string) {
    if (status === 'CONFIRMED') return colors.green;
    else if (status === 'PENDING') return colors.yellow;
    else return colors.red;
  }

  addVisit(): void {
    const modalRef = this.modalService.open(ModalCreateVisitComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.DateVisit = this.clickedDate.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
    const clickedDateObj = new Date(this.clickedDate);
    clickedDateObj.setMinutes(clickedDateObj.getMinutes() + this.hourDuration);
    modalRef.componentInstance.VisitDay = this.clickedDate;
    modalRef.componentInstance.DateEnds = clickedDateObj.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
    modalRef.componentInstance.PackName = 'Current pack';
    modalRef.closed.pipe(filter(reason => reason === 'CONFIRMED')).subscribe(() => {
      this.CreateVisite();
      this.load();
    });
  }

  CreateVisite() {
    const start = this.clickedDate.toLocaleTimeString([], { hour: '2-digit' });
    const startMin = this.clickedDate.toLocaleTimeString([], { minute: '2-digit' });
    const startHour = parseInt(start, 10);
    const startMinNumber = parseInt(startMin, 10);

    const visit: CalendarEvent = {
      title: 'My event',
      start: setHours(setMinutes(this.clickedDate, startMinNumber), startHour),
      color: colors.yellow,
    };

    const newTime = new Date(this.clickedDate.getTime() + this.calendar?.unit! * 60000);
    const Newvisit: NewVisit = { id: null, calendarId: this.calendar?.id, enabled: 'true', fromDate: this.clickedDate, toDate: newTime };

    this.visitService.create(Newvisit).subscribe(
      //GEt all toast format from "src\app\layouts\notifications-toastr\notifications-toastr.component.html"
      data => {
        this.load();
        this.showNotification('success', 'RDV created susscessfully');
      },
      error => {
        this.showNotification('danger', error.error.detail);
      }
    );
  }
  dataToShow: IVisit;

  eventClicked(content: any, { event }: { event: any }): void {
    this.dataToShow = event.meta;
    if (event.meta) {
      this.offcanvasService.open(content, { ariaLabelledBy: 'offcanvas-basic-title', backdrop: true, position: 'end' }).result.then(
        result => {
          this.closeResult = `Closed with: ${result}`;
        },
        reason => {
          console.log('offcanvasService closed ');
        }
      );
    }
  }

  cancelVisit(dataToShow: IVisit): void {
    dataToShow.status = VISIT_STATUS.CANCELED;

    this.visitService.partialUpdate(dataToShow).subscribe(() => {
      console.log('canceled visit 2', dataToShow);
      this.showNotification('success', 'RDV canceled susscessfully');
      this.load();
      this.offcanvasService.dismiss();
    });
  }

  dayClicked({ date, events }: { date: Date; events: CalendarEvent[] }): void {
    if (isSameMonth(date, this.viewDate)) {
      if ((isSameDay(this.viewDate, date) && this.activeDayIsOpen === true) || events.length === 0) {
        this.activeDayIsOpen = false;
      } else {
        this.activeDayIsOpen = true;
      }
      this.viewDate = date;
    }
  }

  public showNotification(type: string, message: string): void {
    console.log('notifying:' + message);
    this.notifier.notify(type, message);
  }
}
