import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICheckin } from '../checkin.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../checkin.test-samples';

import { CheckinService } from './checkin.service';

const requireRestSample: ICheckin = {
  ...sampleWithRequiredData,
};

describe('Checkin Service', () => {
  let service: CheckinService;
  let httpMock: HttpTestingController;
  let expectedResult: ICheckin | ICheckin[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CheckinService);
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

    it('should create a Checkin', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const checkin = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(checkin).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Checkin', () => {
      const checkin = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(checkin).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Checkin', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Checkin', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Checkin', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCheckinToCollectionIfMissing', () => {
      it('should add a Checkin to an empty array', () => {
        const checkin: ICheckin = sampleWithRequiredData;
        expectedResult = service.addCheckinToCollectionIfMissing([], checkin);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(checkin);
      });

      it('should not add a Checkin to an array that contains it', () => {
        const checkin: ICheckin = sampleWithRequiredData;
        const checkinCollection: ICheckin[] = [
          {
            ...checkin,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCheckinToCollectionIfMissing(checkinCollection, checkin);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Checkin to an array that doesn't contain it", () => {
        const checkin: ICheckin = sampleWithRequiredData;
        const checkinCollection: ICheckin[] = [sampleWithPartialData];
        expectedResult = service.addCheckinToCollectionIfMissing(checkinCollection, checkin);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(checkin);
      });

      it('should add only unique Checkin to an array', () => {
        const checkinArray: ICheckin[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const checkinCollection: ICheckin[] = [sampleWithRequiredData];
        expectedResult = service.addCheckinToCollectionIfMissing(checkinCollection, ...checkinArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const checkin: ICheckin = sampleWithRequiredData;
        const checkin2: ICheckin = sampleWithPartialData;
        expectedResult = service.addCheckinToCollectionIfMissing([], checkin, checkin2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(checkin);
        expect(expectedResult).toContain(checkin2);
      });

      it('should accept null and undefined values', () => {
        const checkin: ICheckin = sampleWithRequiredData;
        expectedResult = service.addCheckinToCollectionIfMissing([], null, checkin, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(checkin);
      });

      it('should return initial array if no Checkin is added', () => {
        const checkinCollection: ICheckin[] = [sampleWithRequiredData];
        expectedResult = service.addCheckinToCollectionIfMissing(checkinCollection, undefined, null);
        expect(expectedResult).toEqual(checkinCollection);
      });
    });

    describe('compareCheckin', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCheckin(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCheckin(entity1, entity2);
        const compareResult2 = service.compareCheckin(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareCheckin(entity1, entity2);
        const compareResult2 = service.compareCheckin(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareCheckin(entity1, entity2);
        const compareResult2 = service.compareCheckin(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
