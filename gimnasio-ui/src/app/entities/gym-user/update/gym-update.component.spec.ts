import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { GymFormService } from './gym-form.service';
import { GymService } from '../service/gym.service';
import { IGym } from '../gym.model';

import { GymUpdateComponent } from './gym-update.component';

describe('Gym Management Update Component', () => {
  let comp: GymUpdateComponent;
  let fixture: ComponentFixture<GymUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let gymFormService: GymFormService;
  let gymService: GymService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [GymUpdateComponent],
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
      .overrideTemplate(GymUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(GymUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    gymFormService = TestBed.inject(GymFormService);
    gymService = TestBed.inject(GymService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const gym: IGym = { id: 456 };

      activatedRoute.data = of({ gym });
      comp.ngOnInit();

      expect(comp.gym).toEqual(gym);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGym>>();
      const gym = { id: 123 };
      jest.spyOn(gymFormService, 'getGym').mockReturnValue(gym);
      jest.spyOn(gymService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ gym });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: gym }));
      saveSubject.complete();

      // THEN
      expect(gymFormService.getGym).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(gymService.update).toHaveBeenCalledWith(expect.objectContaining(gym));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGym>>();
      const gym = { id: 123 };
      jest.spyOn(gymFormService, 'getGym').mockReturnValue({ id: null });
      jest.spyOn(gymService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ gym: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: gym }));
      saveSubject.complete();

      // THEN
      expect(gymFormService.getGym).toHaveBeenCalled();
      expect(gymService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGym>>();
      const gym = { id: 123 };
      jest.spyOn(gymService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ gym });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(gymService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
