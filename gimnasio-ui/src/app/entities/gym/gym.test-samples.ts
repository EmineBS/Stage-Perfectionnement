import { IGym, NewGym } from './gym.model';

export const sampleWithRequiredData: IGym = {
  id: 55769,
  name: 'Escudo Borders',
  registrationNumber: 'Senior networks Analyst',
};

export const sampleWithPartialData: IGym = {
  id: 85697,
  name: 'Ergonomic',
  registrationNumber: 'Incredible',
  location: 'Lakes olive leverage',
};

export const sampleWithFullData: IGym = {
  id: 41157,
  name: 'Bahrain',
  registrationNumber: 'Jamaican',
  location: 'customized utilize',
  email: 'Natasha_Parisian81@gmail.com',
  badgeAmount: 97152,
  phoneNumber: 'Generic Sports azure',
};

export const sampleWithNewData: NewGym = {
  name: 'Fresh enable',
  registrationNumber: 'Rupee',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
