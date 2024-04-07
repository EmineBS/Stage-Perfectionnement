import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { CalendarFormService, CalendarFormGroup } from './calendar-form.service';
import { ICalendar } from '../calendar.model';
import { CalendarService } from '../service/calendar.service';
import { IGym } from 'app/entities/gym/gym.model';
import { GymService } from 'app/entities/gym/service/gym.service';

@Component({
  selector: 'jhi-calendar-update',
  templateUrl: './calendar-update.component.html',
})
export class CalendarUpdateComponent implements OnInit {
  isSaving = false;
  calendar: ICalendar | null = null;

  gymsCollection: IGym[] = [];

  editForm: CalendarFormGroup = this.calendarFormService.createCalendarFormGroup();

  constructor(
    protected calendarService: CalendarService,
    protected calendarFormService: CalendarFormService,
    protected gymService: GymService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareGym = (o1: IGym | null, o2: IGym | null): boolean => this.gymService.compareGym(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ calendar }) => {
      this.calendar = calendar;
      if (calendar) {
        this.updateForm(calendar);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const calendar = this.calendarFormService.getCalendar(this.editForm);
    if (calendar.id !== null) {
      this.subscribeToSaveResponse(this.calendarService.update(calendar));
    } else {
      this.subscribeToSaveResponse(this.calendarService.create(calendar));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICalendar>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(calendar: ICalendar): void {
    this.calendar = calendar;
    this.calendarFormService.resetForm(this.editForm, calendar);

    this.gymsCollection = this.gymService.addGymToCollectionIfMissing<IGym>(this.gymsCollection, calendar.gym);
  }

  protected loadRelationshipsOptions(): void {
    this.gymService
      .query({ filter: 'calendar-is-null' })
      .pipe(map((res: HttpResponse<IGym[]>) => res.body ?? []))
      .pipe(map((gyms: IGym[]) => this.gymService.addGymToCollectionIfMissing<IGym>(gyms, this.calendar?.gym)))
      .subscribe((gyms: IGym[]) => (this.gymsCollection = gyms));
  }
}
