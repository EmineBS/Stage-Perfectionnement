import { IPack, NewPack } from './pack.model';

export const sampleWithRequiredData: IPack = {
  id: 52238,
};

export const sampleWithPartialData: IPack = {
  id: 40494,
  name: 'Ball',
  workoutSessions: 49158,
};

export const sampleWithFullData: IPack = {
  id: 43035,
  name: 'primary Mexico',
  description: 'Shoes',
  workoutSessions: 42986,
};

export const sampleWithNewData: NewPack = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
