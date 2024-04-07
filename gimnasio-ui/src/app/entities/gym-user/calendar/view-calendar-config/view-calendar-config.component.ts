import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { Observable, finalize } from 'rxjs';
import { HttpResponse } from '@angular/common/http';
import { ICalendar } from '../calendar.model'; 
import { CalendarFormGroup, CalendarFormService } from '../update/calendar-form.service';
import { CalendarService } from '../service/calendar.service';
import { NotifierService } from 'angular-notifier';
@Component({
  selector: 'jhi-view-calendar-config',
  templateUrl: './view-calendar-config.component.html',
  styleUrls: ['./view-calendar-config.component.scss'],
})
export class ViewCalendarConfigComponent implements OnInit {
  calendar: ICalendar| null = null;
  isSaving = false;
  id: number;

  edit = false;
  

  constructor(
    protected activatedRoute: ActivatedRoute,

    protected router: Router,
    protected calendarService: CalendarService,
    protected calendarFormService: CalendarFormService,
    private notifier: NotifierService
  ) {}

  editForm: CalendarFormGroup = this.calendarFormService.createCalendarFormGroup();

  daysList = ['SUNDAY','MONDAY','TUESDAY','WEDNESDAY','THURSDAY','FRIDAY','SATURDAY']

  weekdays = [
    {index:0, day: 'SUNDAY'},
    {index:1, day: 'MONDAY'}, 
    {index:2, day: 'TUESDAY'}, 
    {index:3, day: 'WEDNESDAY'}, 
    {index:4, day: 'THURSDAY'}, 
    {index:5, day: 'FRIDAY'}, 
    {index:6, day: 'SATURDAY'}, 
  ];
  selectedWeekday: string[] = [];
  
  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ calendar }) => {
      console.log(" calendar details + ", calendar)
      this.calendar = calendar;

      this.id = calendar.id;
    });
  }

  previousState(): void {
    window.history.back();
  }

  confirm(): void {
    this.edit = false;
    this.isSaving = true;

    const calendar = this.calendarFormService.getCalendar(this.editForm);

    if (calendar.id) {
      this.subscribeToSaveResponse(this.calendarService.partialUpdate(calendar));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICalendar>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => {
        this.showNotification('success', 'Configuration updated successfuly');
        this.reloadCurrentRoute();
      },
      error: () => this.onSaveError(),
    });
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }
  
  protected onSaveError(): void {
    // Api for inheritance.
  }
  
  reloadCurrentRoute() {
    this.calendarService.find(this.id).subscribe(calendar => {
      this.calendar = calendar.body;
    });
  }

  editF(): void {
    this.edit = true;
    this.updateForm(this.calendar!);
  }

  protected updateForm(calendar: ICalendar): void {
    this.calendar = calendar;
    this.calendarFormService.resetForm(this.editForm, calendar);
  }
  
  cancelEdit(): void {
    this.edit = false;
  }

  public showNotification(type: string, message: string): void {
    console.log('notifying:' + message);
    this.notifier.notify(type, message);
  }
}
