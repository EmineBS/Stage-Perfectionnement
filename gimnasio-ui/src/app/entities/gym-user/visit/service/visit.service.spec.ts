import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IVisit } from '../visit.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../visit.test-samples';

import { VisitService } from './visit.service';

const requireRestSample: IVisit = {
  ...sampleWithRequiredData,
};

describe('Visit Service', () => {
  let service: VisitService;
  let httpMock: HttpTestingController;
  let expectedResult: IVisit | IVisit[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(VisitService);
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

    it('should create a Visit', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const visit = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(visit).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Visit', () => {
      const visit = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(visit).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Visit', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Visit', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Visit', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addVisitToCollectionIfMissing', () => {
      it('should add a Visit to an empty array', () => {
        const visit: IVisit = sampleWithRequiredData;
        expectedResult = service.addVisitToCollectionIfMissing([], visit);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(visit);
      });

      it('should not add a Visit to an array that contains it', () => {
        const visit: IVisit = sampleWithRequiredData;
        const visitCollection: IVisit[] = [
          {
            ...visit,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addVisitToCollectionIfMissing(visitCollection, visit);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Visit to an array that doesn't contain it", () => {
        const visit: IVisit = sampleWithRequiredData;
        const visitCollection: IVisit[] = [sampleWithPartialData];
        expectedResult = service.addVisitToCollectionIfMissing(visitCollection, visit);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(visit);
      });

      it('should add only unique Visit to an array', () => {
        const visitArray: IVisit[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const visitCollection: IVisit[] = [sampleWithRequiredData];
        expectedResult = service.addVisitToCollectionIfMissing(visitCollection, ...visitArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const visit: IVisit = sampleWithRequiredData;
        const visit2: IVisit = sampleWithPartialData;
        expectedResult = service.addVisitToCollectionIfMissing([], visit, visit2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(visit);
        expect(expectedResult).toContain(visit2);
      });

      it('should accept null and undefined values', () => {
        const visit: IVisit = sampleWithRequiredData;
        expectedResult = service.addVisitToCollectionIfMissing([], null, visit, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(visit);
      });

      it('should return initial array if no Visit is added', () => {
        const visitCollection: IVisit[] = [sampleWithRequiredData];
        expectedResult = service.addVisitToCollectionIfMissing(visitCollection, undefined, null);
        expect(expectedResult).toEqual(visitCollection);
      });
    });

    describe('compareVisit', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareVisit(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareVisit(entity1, entity2);
        const compareResult2 = service.compareVisit(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareVisit(entity1, entity2);
        const compareResult2 = service.compareVisit(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareVisit(entity1, entity2);
        const compareResult2 = service.compareVisit(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
