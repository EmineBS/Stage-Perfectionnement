import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IGym } from '../gym.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../gym.test-samples';

import { GymService } from './gym.service';

const requireRestSample: IGym = {
  ...sampleWithRequiredData,
};

describe('Gym Service', () => {
  let service: GymService;
  let httpMock: HttpTestingController;
  let expectedResult: IGym | IGym[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(GymService);
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

    it('should create a Gym', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const gym = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(gym).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Gym', () => {
      const gym = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(gym).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Gym', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Gym', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Gym', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addGymToCollectionIfMissing', () => {
      it('should add a Gym to an empty array', () => {
        const gym: IGym = sampleWithRequiredData;
        expectedResult = service.addGymToCollectionIfMissing([], gym);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(gym);
      });

      it('should not add a Gym to an array that contains it', () => {
        const gym: IGym = sampleWithRequiredData;
        const gymCollection: IGym[] = [
          {
            ...gym,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addGymToCollectionIfMissing(gymCollection, gym);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Gym to an array that doesn't contain it", () => {
        const gym: IGym = sampleWithRequiredData;
        const gymCollection: IGym[] = [sampleWithPartialData];
        expectedResult = service.addGymToCollectionIfMissing(gymCollection, gym);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(gym);
      });

      it('should add only unique Gym to an array', () => {
        const gymArray: IGym[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const gymCollection: IGym[] = [sampleWithRequiredData];
        expectedResult = service.addGymToCollectionIfMissing(gymCollection, ...gymArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const gym: IGym = sampleWithRequiredData;
        const gym2: IGym = sampleWithPartialData;
        expectedResult = service.addGymToCollectionIfMissing([], gym, gym2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(gym);
        expect(expectedResult).toContain(gym2);
      });

      it('should accept null and undefined values', () => {
        const gym: IGym = sampleWithRequiredData;
        expectedResult = service.addGymToCollectionIfMissing([], null, gym, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(gym);
      });

      it('should return initial array if no Gym is added', () => {
        const gymCollection: IGym[] = [sampleWithRequiredData];
        expectedResult = service.addGymToCollectionIfMissing(gymCollection, undefined, null);
        expect(expectedResult).toEqual(gymCollection);
      });
    });

    describe('compareGym', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareGym(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareGym(entity1, entity2);
        const compareResult2 = service.compareGym(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareGym(entity1, entity2);
        const compareResult2 = service.compareGym(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareGym(entity1, entity2);
        const compareResult2 = service.compareGym(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
