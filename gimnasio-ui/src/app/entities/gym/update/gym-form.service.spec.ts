import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../gym.test-samples';

import { GymFormService } from './gym-form.service';

describe('Gym Form Service', () => {
  let service: GymFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GymFormService);
  });

  describe('Service methods', () => {
    describe('createGymFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createGymFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            matFiscal: expect.any(Object),
            location: expect.any(Object),
            email: expect.any(Object),
            badgeAmount: expect.any(Object),
            phoneNumber: expect.any(Object),
          })
        );
      });

      it('passing IGym should create a new form with FormGroup', () => {
        const formGroup = service.createGymFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            matFiscal: expect.any(Object),
            location: expect.any(Object),
            email: expect.any(Object),
            badgeAmount: expect.any(Object),
            phoneNumber: expect.any(Object),
          })
        );
      });
    });

    describe('getGym', () => {
      it('should return NewGym for default Gym initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createGymFormGroup(sampleWithNewData);

        const gym = service.getGym(formGroup) as any;

        expect(gym).toMatchObject(sampleWithNewData);
      });

      it('should return NewGym for empty Gym initial value', () => {
        const formGroup = service.createGymFormGroup();

        const gym = service.getGym(formGroup) as any;

        expect(gym).toMatchObject({});
      });

      it('should return IGym', () => {
        const formGroup = service.createGymFormGroup(sampleWithRequiredData);

        const gym = service.getGym(formGroup) as any;

        expect(gym).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IGym should not enable id FormControl', () => {
        const formGroup = service.createGymFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewGym should disable id FormControl', () => {
        const formGroup = service.createGymFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
