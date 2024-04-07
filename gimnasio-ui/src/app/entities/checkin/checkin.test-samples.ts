import { ICheckin, NewCheckin } from './checkin.model';

export const sampleWithRequiredData: ICheckin = {
  id: 72434,
};

export const sampleWithPartialData: ICheckin = {
  id: 39421,
  status: 'Rubber',
};

export const sampleWithFullData: ICheckin = {
  id: 30211,
  status: 'Rubber',
};

export const sampleWithNewData: NewCheckin = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
