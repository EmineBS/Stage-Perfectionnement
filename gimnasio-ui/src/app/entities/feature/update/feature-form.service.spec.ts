import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../feature.test-samples';

import { FeatureFormService } from './feature-form.service';

describe('Feature Form Service', () => {
  let service: FeatureFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FeatureFormService);
  });

  describe('Service methods', () => {
    describe('createFeatureFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createFeatureFormGroup();

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

      it('passing IFeature should create a new form with FormGroup', () => {
        const formGroup = service.createFeatureFormGroup(sampleWithRequiredData);

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

    describe('getFeature', () => {
      it('should return NewFeature for default Feature initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createFeatureFormGroup(sampleWithNewData);

        const feature = service.getFeature(formGroup) as any;

        expect(feature).toMatchObject(sampleWithNewData);
      });

      it('should return NewFeature for empty Feature initial value', () => {
        const formGroup = service.createFeatureFormGroup();

        const feature = service.getFeature(formGroup) as any;

        expect(feature).toMatchObject({});
      });

      it('should return IFeature', () => {
        const formGroup = service.createFeatureFormGroup(sampleWithRequiredData);

        const feature = service.getFeature(formGroup) as any;

        expect(feature).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IFeature should not enable id FormControl', () => {
        const formGroup = service.createFeatureFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewFeature should disable id FormControl', () => {
        const formGroup = service.createFeatureFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
