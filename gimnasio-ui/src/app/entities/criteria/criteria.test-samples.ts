import { CRITERIA_DISPLAY } from '../enumerations/criteriaDisplay';
import { ICriteria, NewCriteria } from './criteria.model';

export const sampleWithRequiredData: ICriteria = {
  id: 52238,
};

export const sampleWithPartialData: ICriteria = {
  id: 40494,
  name: 'weigth',
  enabled: true,
  display: CRITERIA_DISPLAY.GRAPH,
};

export const sampleWithFullData: ICriteria = {
  id: 50565,
  name: 'heigth',
  enabled: false,
  display: CRITERIA_DISPLAY.PIE,
};

export const sampleWithNewData: NewCriteria = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
