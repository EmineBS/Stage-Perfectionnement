import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../visit.test-samples';

import { VisitFormService } from './visit-form.service';

describe('Visit Form Service', () => {
  let service: VisitFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(VisitFormService);
  });

  describe('Service methods', () => {
    describe('createVisitFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createVisitFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            fromDate: expect.any(Object),
            toDate: expect.any(Object),
            enabled: expect.any(Object),
            status: expect.any(Object),
            calendarId: expect.any(Object),
            relBadgePackId: expect.any(Object),
          })
        );
      });

      it('passing IVisit should create a new form with FormGroup', () => {
        const formGroup = service.createVisitFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            fromDate: expect.any(Object),
            toDate: expect.any(Object),
            enabled: expect.any(Object),
            status: expect.any(Object),
            calendarId: expect.any(Object),
            relBadgePackId: expect.any(Object),
          })
        );
      });
    });

    describe('getVisit', () => {
      it('should return NewVisit for default Visit initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createVisitFormGroup(sampleWithNewData);

        const visit = service.getVisit(formGroup) as any;

        expect(visit).toMatchObject(sampleWithNewData);
      });

      it('should return NewVisit for empty Visit initial value', () => {
        const formGroup = service.createVisitFormGroup();

        const visit = service.getVisit(formGroup) as any;

        expect(visit).toMatchObject({});
      });

      it('should return IVisit', () => {
        const formGroup = service.createVisitFormGroup(sampleWithRequiredData);

        const visit = service.getVisit(formGroup) as any;

        expect(visit).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IVisit should not enable id FormControl', () => {
        const formGroup = service.createVisitFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewVisit should disable id FormControl', () => {
        const formGroup = service.createVisitFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
