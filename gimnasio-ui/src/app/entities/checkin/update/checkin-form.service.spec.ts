import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../checkin.test-samples';

import { CheckinFormService } from './checkin-form.service';

describe('Checkin Form Service', () => {
  let service: CheckinFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CheckinFormService);
  });

  describe('Service methods', () => {
    describe('createCheckinFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCheckinFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            status: expect.any(Object),
            user: expect.any(Object),
            badge: expect.any(Object),
          })
        );
      });

      it('passing ICheckin should create a new form with FormGroup', () => {
        const formGroup = service.createCheckinFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            status: expect.any(Object),
            user: expect.any(Object),
            badge: expect.any(Object),
          })
        );
      });
    });

    describe('getCheckin', () => {
      it('should return NewCheckin for default Checkin initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createCheckinFormGroup(sampleWithNewData);

        const checkin = service.getCheckin(formGroup) as any;

        expect(checkin).toMatchObject(sampleWithNewData);
      });

      it('should return NewCheckin for empty Checkin initial value', () => {
        const formGroup = service.createCheckinFormGroup();

        const checkin = service.getCheckin(formGroup) as any;

        expect(checkin).toMatchObject({});
      });

      it('should return ICheckin', () => {
        const formGroup = service.createCheckinFormGroup(sampleWithRequiredData);

        const checkin = service.getCheckin(formGroup) as any;

        expect(checkin).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICheckin should not enable id FormControl', () => {
        const formGroup = service.createCheckinFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCheckin should disable id FormControl', () => {
        const formGroup = service.createCheckinFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
