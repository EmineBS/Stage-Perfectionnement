import { IProfile, NewProfile } from './profile.model';

export const sampleWithRequiredData: IProfile = {
  id: 11865,
};

export const sampleWithPartialData: IProfile = {
  id: 32544,
  username: 'Senior Concrete',
  name: 'XSS solution-oriented',
  lastName: 'Watsica',
  email: 'Megane.Pacocha32@hotmail.com',
  phoneNumber: 'North index Concrete',
  badgeId: 42106,
};

export const sampleWithFullData: IProfile = {
  id: 76041,
  username: 'virtual optical',
  name: 'RAM blockchains Cotton',
  lastName: 'Simonis',
  email: 'Joaquin.Brekke7@yahoo.com',
  phoneNumber: 'Roads withdrawal',
  badgeId: 6969,
};

export const sampleWithNewData: NewProfile = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
