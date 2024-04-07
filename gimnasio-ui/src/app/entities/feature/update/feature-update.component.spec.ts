import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { FeatureFormService } from './feature-form.service';
import { FeatureService } from '../service/feature.service';
import { IFeature } from '../feature.model';
import { IBadge } from 'app/entities/badge/badge.model';
import { BadgeService } from 'app/entities/badge/service/badge.service';

import { FeatureUpdateComponent } from './feature-update.component';

describe('Feature Management Update Component', () => {
  let comp: FeatureUpdateComponent;
  let fixture: ComponentFixture<FeatureUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let featureFormService: FeatureFormService;
  let featureService: FeatureService;
  let badgeService: BadgeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [FeatureUpdateComponent],
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
      .overrideTemplate(FeatureUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FeatureUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    featureFormService = TestBed.inject(FeatureFormService);
    featureService = TestBed.inject(FeatureService);
    badgeService = TestBed.inject(BadgeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Badge query and add missing value', () => {
      const feature: IFeature = { id: 456 };
      const badge: IBadge = { id: 8832 };
      feature.badge = badge;

      const badgeCollection: IBadge[] = [{ id: 55566 }];
      jest.spyOn(badgeService, 'query').mockReturnValue(of(new HttpResponse({ body: badgeCollection })));
      const additionalBadges = [badge];
      const expectedCollection: IBadge[] = [...additionalBadges, ...badgeCollection];
      jest.spyOn(badgeService, 'addBadgeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ feature });
      comp.ngOnInit();

      expect(badgeService.query).toHaveBeenCalled();
      expect(badgeService.addBadgeToCollectionIfMissing).toHaveBeenCalledWith(
        badgeCollection,
        ...additionalBadges.map(expect.objectContaining)
      );
      expect(comp.badgesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const feature: IFeature = { id: 456 };
      const badge: IBadge = { id: 92912 };
      feature.badge = badge;

      activatedRoute.data = of({ feature });
      comp.ngOnInit();

      expect(comp.badgesSharedCollection).toContain(badge);
      expect(comp.feature).toEqual(feature);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFeature>>();
      const feature = { id: 123 };
      jest.spyOn(featureFormService, 'getFeature').mockReturnValue(feature);
      jest.spyOn(featureService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ feature });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: feature }));
      saveSubject.complete();

      // THEN
      expect(featureFormService.getFeature).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(featureService.update).toHaveBeenCalledWith(expect.objectContaining(feature));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFeature>>();
      const feature = { id: 123 };
      jest.spyOn(featureFormService, 'getFeature').mockReturnValue({ id: null });
      jest.spyOn(featureService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ feature: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: feature }));
      saveSubject.complete();

      // THEN
      expect(featureFormService.getFeature).toHaveBeenCalled();
      expect(featureService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFeature>>();
      const feature = { id: 123 };
      jest.spyOn(featureService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ feature });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(featureService.update).toHaveBeenCalled();
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
