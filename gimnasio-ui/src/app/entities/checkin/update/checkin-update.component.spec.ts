import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CheckinFormService } from './checkin-form.service';
import { CheckinService } from '../service/checkin.service';
import { ICheckin } from '../checkin.model';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { IBadge } from 'app/entities/badge/badge.model';
import { BadgeService } from 'app/entities/badge/service/badge.service';

import { CheckinUpdateComponent } from './checkin-update.component';

describe('Checkin Management Update Component', () => {
  let comp: CheckinUpdateComponent;
  let fixture: ComponentFixture<CheckinUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let checkinFormService: CheckinFormService;
  let checkinService: CheckinService;
  let userService: UserService;
  let badgeService: BadgeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CheckinUpdateComponent],
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
      .overrideTemplate(CheckinUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CheckinUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    checkinFormService = TestBed.inject(CheckinFormService);
    checkinService = TestBed.inject(CheckinService);
    userService = TestBed.inject(UserService);
    badgeService = TestBed.inject(BadgeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call User query and add missing value', () => {
      const checkin: ICheckin = { id: 456 };
      const user: IUser = { id: '93916888-b778-4c1d-883e-b8979bdfa49c' };
      checkin.user = user;

      const userCollection: IUser[] = [{ id: '105439b9-9110-47af-b266-7dcf8eae21fc' }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [user];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ checkin });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(
        userCollection,
        ...additionalUsers.map(expect.objectContaining)
      );
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Badge query and add missing value', () => {
      const checkin: ICheckin = { id: 456 };
      const badge: IBadge = { id: 27719 };
      checkin.badge = badge;

      const badgeCollection: IBadge[] = [{ id: 53536 }];
      jest.spyOn(badgeService, 'query').mockReturnValue(of(new HttpResponse({ body: badgeCollection })));
      const additionalBadges = [badge];
      const expectedCollection: IBadge[] = [...additionalBadges, ...badgeCollection];
      jest.spyOn(badgeService, 'addBadgeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ checkin });
      comp.ngOnInit();

      expect(badgeService.query).toHaveBeenCalled();
      expect(badgeService.addBadgeToCollectionIfMissing).toHaveBeenCalledWith(
        badgeCollection,
        ...additionalBadges.map(expect.objectContaining)
      );
      expect(comp.badgesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const checkin: ICheckin = { id: 456 };
      const user: IUser = { id: '07107c7a-98f0-4299-9e10-85b26fa192e2' };
      checkin.user = user;
      const badge: IBadge = { id: 54570 };
      checkin.badge = badge;

      activatedRoute.data = of({ checkin });
      comp.ngOnInit();

      expect(comp.usersSharedCollection).toContain(user);
      expect(comp.badgesSharedCollection).toContain(badge);
      expect(comp.checkin).toEqual(checkin);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICheckin>>();
      const checkin = { id: 123 };
      jest.spyOn(checkinFormService, 'getCheckin').mockReturnValue(checkin);
      jest.spyOn(checkinService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ checkin });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: checkin }));
      saveSubject.complete();

      // THEN
      expect(checkinFormService.getCheckin).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(checkinService.update).toHaveBeenCalledWith(expect.objectContaining(checkin));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICheckin>>();
      const checkin = { id: 123 };
      jest.spyOn(checkinFormService, 'getCheckin').mockReturnValue({ id: null });
      jest.spyOn(checkinService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ checkin: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: checkin }));
      saveSubject.complete();

      // THEN
      expect(checkinFormService.getCheckin).toHaveBeenCalled();
      expect(checkinService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICheckin>>();
      const checkin = { id: 123 };
      jest.spyOn(checkinService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ checkin });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(checkinService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareUser', () => {
      it('Should forward to userService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(userService, 'compareUser');
        comp.compareUser(entity, entity2);
        expect(userService.compareUser).toHaveBeenCalledWith(entity, entity2);
      });
    });

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
