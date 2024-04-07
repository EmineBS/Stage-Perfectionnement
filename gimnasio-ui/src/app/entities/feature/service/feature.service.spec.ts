import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IFeature } from '../feature.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../feature.test-samples';

import { FeatureService } from './feature.service';

const requireRestSample: IFeature = {
  ...sampleWithRequiredData,
};

describe('Feature Service', () => {
  let service: FeatureService;
  let httpMock: HttpTestingController;
  let expectedResult: IFeature | IFeature[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(FeatureService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a Feature', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const feature = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(feature).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Feature', () => {
      const feature = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(feature).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Feature', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Feature', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Feature', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addFeatureToCollectionIfMissing', () => {
      it('should add a Feature to an empty array', () => {
        const feature: IFeature = sampleWithRequiredData;
        expectedResult = service.addFeatureToCollectionIfMissing([], feature);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(feature);
      });

      it('should not add a Feature to an array that contains it', () => {
        const feature: IFeature = sampleWithRequiredData;
        const featureCollection: IFeature[] = [
          {
            ...feature,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addFeatureToCollectionIfMissing(featureCollection, feature);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Feature to an array that doesn't contain it", () => {
        const feature: IFeature = sampleWithRequiredData;
        const featureCollection: IFeature[] = [sampleWithPartialData];
        expectedResult = service.addFeatureToCollectionIfMissing(featureCollection, feature);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(feature);
      });

      it('should add only unique Feature to an array', () => {
        const featureArray: IFeature[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const featureCollection: IFeature[] = [sampleWithRequiredData];
        expectedResult = service.addFeatureToCollectionIfMissing(featureCollection, ...featureArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const feature: IFeature = sampleWithRequiredData;
        const feature2: IFeature = sampleWithPartialData;
        expectedResult = service.addFeatureToCollectionIfMissing([], feature, feature2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(feature);
        expect(expectedResult).toContain(feature2);
      });

      it('should accept null and undefined values', () => {
        const feature: IFeature = sampleWithRequiredData;
        expectedResult = service.addFeatureToCollectionIfMissing([], null, feature, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(feature);
      });

      it('should return initial array if no Feature is added', () => {
        const featureCollection: IFeature[] = [sampleWithRequiredData];
        expectedResult = service.addFeatureToCollectionIfMissing(featureCollection, undefined, null);
        expect(expectedResult).toEqual(featureCollection);
      });
    });

    describe('compareFeature', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareFeature(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareFeature(entity1, entity2);
        const compareResult2 = service.compareFeature(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareFeature(entity1, entity2);
        const compareResult2 = service.compareFeature(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareFeature(entity1, entity2);
        const compareResult2 = service.compareFeature(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
