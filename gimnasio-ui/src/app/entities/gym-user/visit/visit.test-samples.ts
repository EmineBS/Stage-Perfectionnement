import { IVisit, NewVisit } from './visit.model';

export const sampleWithRequiredData: IVisit = {
  id: 31906,
};

export const sampleWithPartialData: IVisit = {
  id: 7142,
  enabled: 'viral',
  calendarId: 90847,
};

export const sampleWithFullData: IVisit = {
  id: 60689,
  fromDate: 'Checking Rupiah Administrator',
  toDate: 'optical Heights',
  enabled: 'PCI Account primary',
  status: true,
  calendarId: 22300,
  relBadgePackId: 1072,
};

export const sampleWithNewData: NewVisit = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
