import { STATUS } from 'app/entities/enumerations/status.model';

import { IBadge, NewBadge } from './badge.model';

export const sampleWithRequiredData: IBadge = {
  id: 22734,
};

export const sampleWithPartialData: IBadge = {
  id: 85895,
  uid: 'gold',
};

export const sampleWithFullData: IBadge = {
  id: 16402,
  uid: 'orange',
  status: STATUS['ACTIF'],
};

export const sampleWithNewData: NewBadge = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
