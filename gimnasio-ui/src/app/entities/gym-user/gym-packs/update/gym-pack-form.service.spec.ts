import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from 'app/entities/pack/pack.test-samples';

import { GymPackFormService } from './gym-pack-form.service';

describe('Pack Form Service', () => {
  let service: GymPackFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GymPackFormService);
  });

  describe('Service methods', () => {
    describe('createPackFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPackFormGroup();

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

      it('passing IPack should create a new form with FormGroup', () => {
        const formGroup = service.createPackFormGroup(sampleWithRequiredData);

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

    describe('getPack', () => {
      it('should return NewPack for default Pack initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createPackFormGroup(sampleWithNewData);

        const pack = service.getPack(formGroup) as any;

        expect(pack).toMatchObject(sampleWithNewData);
      });

      it('should return NewPack for empty Pack initial value', () => {
        const formGroup = service.createPackFormGroup();

        const pack = service.getPack(formGroup) as any;

        expect(pack).toMatchObject({});
      });

      it('should return IPack', () => {
        const formGroup = service.createPackFormGroup(sampleWithRequiredData);

        const pack = service.getPack(formGroup) as any;

        expect(pack).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPack should not enable id FormControl', () => {
        const formGroup = service.createPackFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPack should disable id FormControl', () => {
        const formGroup = service.createPackFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
