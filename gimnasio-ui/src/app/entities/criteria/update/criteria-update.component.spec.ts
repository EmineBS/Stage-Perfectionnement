import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CriteriaFormService } from './criteria-form.service';
import { CriteriaService } from '../service/criteria.service';
import { ICriteria } from '../criteria.model';
import { IBadge } from 'app/entities/badge/badge.model';
import { BadgeService } from 'app/entities/badge/service/badge.service';

import { CriteriaUpdateComponent } from './criteria-update.component';

describe('Criteria Management Update Component', () => {
  let comp: CriteriaUpdateComponent;
  let fixture: ComponentFixture<CriteriaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let criteriaFormService: CriteriaFormService;
  let criteriaService: CriteriaService;
  let badgeService: BadgeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CriteriaUpdateComponent],
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
      .overrideTemplate(CriteriaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CriteriaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    criteriaFormService = TestBed.inject(CriteriaFormService);
    criteriaService = TestBed.inject(CriteriaService);
    badgeService = TestBed.inject(BadgeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Badge query and add missing value', () => {
      const criteria: ICriteria = { id: 456 };
      const badge: IBadge = { id: 8832 };
      criteria.badge = badge;

      const badgeCollection: IBadge[] = [{ id: 55566 }];
      jest.spyOn(badgeService, 'query').mockReturnValue(of(new HttpResponse({ body: badgeCollection })));
      const additionalBadges = [badge];
      const expectedCollection: IBadge[] = [...additionalBadges, ...badgeCollection];
      jest.spyOn(badgeService, 'addBadgeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ criteria });
      comp.ngOnInit();

      expect(badgeService.query).toHaveBeenCalled();
      expect(badgeService.addBadgeToCollectionIfMissing).toHaveBeenCalledWith(
        badgeCollection,
        ...additionalBadges.map(expect.objectContaining)
      );
      expect(comp.badgesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const criteria: ICriteria = { id: 456 };
      const badge: IBadge = { id: 92912 };
      criteria.badge = badge;

      activatedRoute.data = of({ criteria });
      comp.ngOnInit();

      expect(comp.badgesSharedCollection).toContain(badge);
      expect(comp.criteria).toEqual(criteria);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICriteria>>();
      const criteria = { id: 123 };
      jest.spyOn(criteriaFormService, 'getCriteria').mockReturnValue(criteria);
      jest.spyOn(criteriaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ criteria });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: criteria }));
      saveSubject.complete();

      // THEN
      expect(criteriaFormService.getCriteria).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(criteriaService.update).toHaveBeenCalledWith(expect.objectContaining(criteria));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICriteria>>();
      const criteria = { id: 123 };
      jest.spyOn(criteriaFormService, 'getCriteria').mockReturnValue({ id: null });
      jest.spyOn(criteriaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ criteria: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: criteria }));
      saveSubject.complete();

      // THEN
      expect(criteriaFormService.getCriteria).toHaveBeenCalled();
      expect(criteriaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICriteria>>();
      const criteria = { id: 123 };
      jest.spyOn(criteriaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ criteria });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(criteriaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareBadge', () => {
      it('Should forward to badgeService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(badgeService, 'compareBadge');
        comp.compareBadge(entity, entity2);
        expect(badgeService.compareBadge).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
