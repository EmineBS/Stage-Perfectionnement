import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { GymPackFormService } from './gym-pack-form.service';
import { PackService } from '../service/pack.service';
import { IPack } from '../pack.model';
import { IBadge } from 'app/entities/badge/badge.model';
import { BadgeService } from 'app/entities/badge/service/badge.service';

import { GymPackUpdateComponent } from './gym-pack-update.component';

describe('Pack Management Update Component', () => {
  let comp: GymPackUpdateComponent;
  let fixture: ComponentFixture<GymPackUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let GymPackFormService: GymPackFormService;
  let packService: PackService;
  let badgeService: BadgeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [GymPackUpdateComponent],
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
      .overrideTemplate(GymPackUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(GymPackUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    GymPackFormService = TestBed.inject(GymPackFormService);
    packService = TestBed.inject(PackService);
    badgeService = TestBed.inject(BadgeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Badge query and add missing value', () => {
      const pack: IPack = { id: 456 };
      const badge: IBadge = { id: 8832 };
      pack.badge = badge;

      const badgeCollection: IBadge[] = [{ id: 55566 }];
      jest.spyOn(badgeService, 'query').mockReturnValue(of(new HttpResponse({ body: badgeCollection })));
      const additionalBadges = [badge];
      const expectedCollection: IBadge[] = [...additionalBadges, ...badgeCollection];
      jest.spyOn(badgeService, 'addBadgeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ pack });
      comp.ngOnInit();

      expect(badgeService.query).toHaveBeenCalled();
      expect(badgeService.addBadgeToCollectionIfMissing).toHaveBeenCalledWith(
        badgeCollection,
        ...additionalBadges.map(expect.objectContaining)
      );
      expect(comp.badgesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const pack: IPack = { id: 456 };
      const badge: IBadge = { id: 92912 };
      pack.badge = badge;

      activatedRoute.data = of({ pack });
      comp.ngOnInit();

      expect(comp.badgesSharedCollection).toContain(badge);
      expect(comp.pack).toEqual(pack);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPack>>();
      const pack = { id: 123 };
      jest.spyOn(GymPackFormService, 'getPack').mockReturnValue(pack);
      jest.spyOn(packService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pack });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pack }));
      saveSubject.complete();

      // THEN
      expect(GymPackFormService.getPack).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(packService.update).toHaveBeenCalledWith(expect.objectContaining(pack));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPack>>();
      const pack = { id: 123 };
      jest.spyOn(GymPackFormService, 'getPack').mockReturnValue({ id: null });
      jest.spyOn(packService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pack: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pack }));
      saveSubject.complete();

      // THEN
      expect(GymPackFormService.getPack).toHaveBeenCalled();
      expect(packService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPack>>();
      const pack = { id: 123 };
      jest.spyOn(packService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pack });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(packService.update).toHaveBeenCalled();
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
