import { ICalendar, NewCalendar } from './calendar.model';

export const sampleWithRequiredData: ICalendar = {
  id: 70069,
};

export const sampleWithPartialData: ICalendar = {
  id: 51921,
  gymId: 63483,
};

export const sampleWithFullData: ICalendar = {
  id: 70714,
  gymId: 34791,
};

export const sampleWithNewData: NewCalendar = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
