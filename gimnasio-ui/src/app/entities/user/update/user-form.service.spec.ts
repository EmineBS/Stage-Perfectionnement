import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../user.test-samples';

import { UserFormService } from './user-form.service';

describe('User Form Service', () => {
  let service: UserFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UserFormService);
  });

  describe('Service methods', () => {
    describe('createUserFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createUserFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            description: expect.any(Object),
            workoutSessions: expect.any(Object),
            badge: expect.any(Object),
          })
        );
      });

      it('passing IUser should create a new form with FormGroup', () => {
        const formGroup = service.createUserFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            description: expect.any(Object),
            workoutSessions: expect.any(Object),
            badge: expect.any(Object),
          })
        );
      });
    });

    describe('getUser', () => {
      it('should return NewUser for default User initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createUserFormGroup(sampleWithNewData);

        const user = service.getUser(formGroup) as any;

        expect(user).toMatchObject(sampleWithNewData);
      });

      it('should return NewUser for empty User initial value', () => {
        const formGroup = service.createUserFormGroup();

        const user = service.getUser(formGroup) as any;

        expect(user).toMatchObject({});
      });

      it('should return IUser', () => {
        const formGroup = service.createUserFormGroup(sampleWithRequiredData);

        const user = service.getUser(formGroup) as any;

        expect(user).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IUser should not enable id FormControl', () => {
        const formGroup = service.createUserFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewUser should disable id FormControl', () => {
        const formGroup = service.createUserFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
