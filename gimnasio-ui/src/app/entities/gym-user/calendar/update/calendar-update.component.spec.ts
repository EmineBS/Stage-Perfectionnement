import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CalendarFormService } from './calendar-form.service';
import { CalendarService } from '../service/calendar.service';
import { ICalendar } from '../calendar.model';
import { IGym } from 'app/entities/gym/gym.model';
import { GymService } from 'app/entities/gym/service/gym.service';

import { CalendarUpdateComponent } from './calendar-update.component';

describe('Calendar Management Update Component', () => {
  let comp: CalendarUpdateComponent;
  let fixture: ComponentFixture<CalendarUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let calendarFormService: CalendarFormService;
  let calendarService: CalendarService;
  let gymService: GymService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CalendarUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(CalendarUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CalendarUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    calendarFormService = TestBed.inject(CalendarFormService);
    calendarService = TestBed.inject(CalendarService);
    gymService = TestBed.inject(GymService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call gym query and add missing value', () => {
      const calendar: ICalendar = { id: 456 };
      const gym: IGym = { id: 88758 };
      calendar.gym = gym;

      const gymCollection: IGym[] = [{ id: 94698 }];
      jest.spyOn(gymService, 'query').mockReturnValue(of(new HttpResponse({ body: gymCollection })));
      const expectedCollection: IGym[] = [gym, ...gymCollection];
      jest.spyOn(gymService, 'addGymToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ calendar });
      comp.ngOnInit();

      expect(gymService.query).toHaveBeenCalled();
      expect(gymService.addGymToCollectionIfMissing).toHaveBeenCalledWith(gymCollection, gym);
      expect(comp.gymsCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const calendar: ICalendar = { id: 456 };
      const gym: IGym = { id: 78940 };
      calendar.gym = gym;

      activatedRoute.data = of({ calendar });
      comp.ngOnInit();

      expect(comp.gymsCollection).toContain(gym);
      expect(comp.calendar).toEqual(calendar);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICalendar>>();
      const calendar = { id: 123 };
      jest.spyOn(calendarFormService, 'getCalendar').mockReturnValue(calendar);
      jest.spyOn(calendarService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ calendar });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: calendar }));
      saveSubject.complete();

      // THEN
      expect(calendarFormService.getCalendar).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(calendarService.update).toHaveBeenCalledWith(expect.objectContaining(calendar));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICalendar>>();
      const calendar = { id: 123 };
      jest.spyOn(calendarFormService, 'getCalendar').mockReturnValue({ id: null });
      jest.spyOn(calendarService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ calendar: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: calendar }));
      saveSubject.complete();

      // THEN
      expect(calendarFormService.getCalendar).toHaveBeenCalled();
      expect(calendarService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICalendar>>();
      const calendar = { id: 123 };
      jest.spyOn(calendarService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ calendar });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(calendarService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareGym', () => {
      it('Should forward to gymService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(gymService, 'compareGym');
        comp.compareGym(entity, entity2);
        expect(gymService.compareGym).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
