import { ChangeDetectionStrategy, Component, OnInit, Output, TemplateRef, ViewChild } from '@angular/core';
import { HttpHeaders } from '@angular/common/http';
import { ActivatedRoute, Data, ParamMap, Router } from '@angular/router';
import { combineLatest, filter, Observable, Subject, switchMap, tap } from 'rxjs';
import { NgbModal, NgbOffcanvas } from '@ng-bootstrap/ng-bootstrap';

import { ICalendar } from '../calendar.model';

import { CalendarService } from '../service/calendar.service';
import { CalendarDeleteDialogComponent } from '../delete/calendar-delete-dialog.component';
import {
  CalendarEvent,
  CalendarView,
  CalendarEventTimesChangedEvent,
  CalendarEventTitleFormatter,
  CalendarEventAction,
} from 'angular-calendar';
import { setHours, setMinutes, isSameDay } from 'date-fns';

import { colors } from './colors';
import { ModalCreateVisitComponent } from '../../badge-calendar/modal-create-visit/modal-create-visit.component';
import { IGym } from '../../gym.model';
import { SessionStorageService } from 'ngx-webstorage';
import { VisitService, EntityArrayResponseType } from '../../visit/service/visit.service';
import { IVisit, NewVisit } from '../../visit/visit.model';
import { CustomEventTitleFormatter } from '../custom-title-formatter.provider';
import { isSameMonth } from 'ngx-bootstrap/chronos';
import { ITEMS_PER_PAGE, PAGE_HEADER, TOTAL_COUNT_RESPONSE_HEADER } from 'app/config/pagination.constants';
import { ASC, DESC, SORT, ITEM_DELETED_EVENT, DEFAULT_SORT_DATA } from 'app/config/navigation.constants';
import { AddUserVisitComponent } from '../user-add-visit/add-user-visit/add-user-visit.component';

import { registerLocaleData } from '@angular/common';
import localeFr from '@angular/common/locales/fr';
import { LangChangeEvent, TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'jhi-calendar',
  changeDetection: ChangeDetectionStrategy.OnPush,
  templateUrl: './calendar.component.html',
  providers: [
    {
      provide: CalendarEventTitleFormatter,
      useClass: CustomEventTitleFormatter,
    },
  ],
})
export class CalendarComponent implements OnInit {
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
  idgym = this.sessionStorageService.retrieve('gym').id;

  locale: string = 'fr';

  constructor(
    protected calendarService: CalendarService,
    protected activatedRoute: ActivatedRoute,
    public router: Router,
    private sessionStorageService: SessionStorageService,
    private visitService: VisitService,
    private offcanvasService: NgbOffcanvas,
    protected modalService: NgbModal,
    private translateService: TranslateService
  ) {
    this.maxDate.setDate(this.maxDate.getDate() + 7);
    this.bsInlineRangeValue = [this.bsInlineValue, this.maxDate];
    this.translateService.onLangChange.subscribe(lang=>{
      this.locale = lang.lang;
      this.refreshView()
  })

  }

  trackId = (_index: number, item: ICalendar): number => this.calendarService.getCalendarIdentifier(item);

  ngOnInit(): void {
    this.load();
    registerLocaleData(localeFr);
  //   this.translateService.onLangChange.subscribe((event: LangChangeEvent) => {
  //     this.locale = event.lang;
  //     this.refreshView()
  // });

  }

  // exclude weekends
  excludeDays: number[] = [0, 6];

  onDateChange(event: Date): void {
    this.viewDate = new Date(event);
    // Perform further operations with the selected date
  }

  /* Template default calendar */
  view: CalendarView = CalendarView.Week;

  CalendarView = CalendarView;

  viewDate: Date = new Date();

  visite: CalendarEvent;

  dataToShow: IVisit;

  eventClicked(content: any, { event }: { event: any }): void {
    console.log('Event clicked', event);
    this.dataToShow = event.meta;
    this.offcanvasService.open(content, { ariaLabelledBy: 'offcanvas-basic-title', backdrop: true, position: 'end' }).result.then(
      result => {
        this.closeResult = `Closed with: ${result}`;
      },
      reason => {}
    );
  }

  hourSegmentClicked(event: any): void {
    this.clickedDate = event;

    /*  this.addVisit() */
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
  /* documentation */

  load() {
    this.calendarService.findCalendar(this.idgym).subscribe(data => {
      if (data.body) {
        this.calendar = data.body!;
        this.hourSegments = 1;
        this.hourDuration = this.calendar.unit!;
        this.dayStartHour = parseInt(this.calendar.startHour?.slice(0, 2)!);
        this.dayEndHour = parseInt(this.calendar.endHour?.slice(0, 2)!);
        this.excludeDays = this.calendar.offDays!;
        //load all visits
        this.loadGymVisit();
      }
    });
  }

  /*  addVisit(): void {
    const modalRef = this.modalService.open(AddUserVisitComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.DateVisit = this.clickedDate.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
    modalRef.componentInstance.idgym=this.idgym
    const clickedDateObj = new Date(this.clickedDate);
    clickedDateObj.setMinutes(clickedDateObj.getMinutes() + this.hourDuration);
    modalRef.componentInstance.VisitDay = this.clickedDate;
    modalRef.componentInstance.DateEnds = clickedDateObj.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
    modalRef.closed
      .pipe(
        filter(reason => reason === "CONFIRMED"),
      )
      .subscribe(() => {
        this.CreateVisite();
        this.load();

      });
  }
 */

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
    this.visitService.create(Newvisit).subscribe(() => {
      this.load();
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
      // prepare date to push it to event
      const dateToStart = new Date(event.fromDate!);
      const dateToFinish = new Date(event.toDate!);

      const visit: CalendarEvent = {
        title: event.profileEmail + ' for pack ' + event.packName,
        start: dateToStart,
        end: dateToFinish,
        color: this.EventColor(event.status!),
        meta: event,
        /* actions: [
          {
            label: 'delete',
            cssClass:'text-danger',
            onClick: ({ event }: { event: CalendarEvent }): void => {
              this.events = this.events.filter((iEvent) => iEvent !== event);
              console.log('Event deleted', event);
            },
          },
        ],*/
      };
      this.events.push(visit);
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
      switchMap(() => this.queryBackend())
    );
  }

  protected queryBackend(): Observable<EntityArrayResponseType> {
    this.isLoading = true;
    return this.visitService.findAllByGym(this.idgym).pipe(tap(() => (this.isLoading = false)));
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
}
