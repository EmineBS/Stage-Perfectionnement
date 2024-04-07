import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { UserFormService } from './user-form.service';
import { UserService } from '../service/user.service';
import { IUser } from '../user.model';
import { IBadge } from 'app/entities/badge/badge.model';
import { BadgeService } from 'app/entities/badge/service/badge.service';

import { UserUpdateComponent } from './user-update.component';

describe('User Management Update Component', () => {
  let comp: UserUpdateComponent;
  let fixture: ComponentFixture<UserUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let userFormService: UserFormService;
  let userService: UserService;
  let badgeService: BadgeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [UserUpdateComponent],
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
      .overrideTemplate(UserUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(UserUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    userFormService = TestBed.inject(UserFormService);
    userService = TestBed.inject(UserService);
    badgeService = TestBed.inject(BadgeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Badge query and add missing value', () => {
      const user: IUser = { id: 456 };
      const badge: IBadge = { id: 8832 };
      user.badge = badge;

      const badgeCollection: IBadge[] = [{ id: 55566 }];
      jest.spyOn(badgeService, 'query').mockReturnValue(of(new HttpResponse({ body: badgeCollection })));
      const additionalBadges = [badge];
      const expectedCollection: IBadge[] = [...additionalBadges, ...badgeCollection];
      jest.spyOn(badgeService, 'addBadgeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ user });
      comp.ngOnInit();

      expect(badgeService.query).toHaveBeenCalled();
      expect(badgeService.addBadgeToCollectionIfMissing).toHaveBeenCalledWith(
        badgeCollection,
        ...additionalBadges.map(expect.objectContaining)
      );
      expect(comp.badgesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const user: IUser = { id: 456 };
      const badge: IBadge = { id: 92912 };
      user.badge = badge;

      activatedRoute.data = of({ user });
      comp.ngOnInit();

      expect(comp.badgesSharedCollection).toContain(badge);
      expect(comp.user).toEqual(user);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IUser>>();
      const user = { id: 123 };
      jest.spyOn(userFormService, 'getUser').mockReturnValue(user);
      jest.spyOn(userService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ user });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: user }));
      saveSubject.complete();

      // THEN
      expect(userFormService.getUser).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(userService.update).toHaveBeenCalledWith(expect.objectContaining(user));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IUser>>();
      const user = { id: 123 };
      jest.spyOn(userFormService, 'getUser').mockReturnValue({ id: null });
      jest.spyOn(userService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ user: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: user }));
      saveSubject.complete();

      // THEN
      expect(userFormService.getUser).toHaveBeenCalled();
      expect(userService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IUser>>();
      const user = { id: 123 };
      jest.spyOn(userService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ user });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(userService.update).toHaveBeenCalled();
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
