import { IBadge } from '../badge/badge.model';
import { IUser } from '../user/user.model';

export interface IGym {
  id: number;
  name?: string | null;
  description?: String | null;
  location?: string | null;
  email?: string | null;
  badgeAmount?: number | null;
  phoneNumber?: string | null;
  registrationNumber?: string | null;
  createdBy?: string | null;
  createdDate?: Date | null;
  lastModifiedBy?: Date | null;
  status?: String | null;
  lastModifiedDate?: Date | null;
  badges?: Pick<IBadge, 'id' | 'uid' | 'status' | 'lastModifiedDate'>[] | null;
  user?: Pick<IUser, 'id' | 'login'> | null;
  userId?: String | null;
}

export interface IGymFeature{
  id: number;
  gymId?: number | null;
  featureId: number | null;
  createdBy?: string | null;
  createdDate?: Date | null;
  lastModifiedBy?: Date | null;
  lastModifiedDate?: Date | null;
}

export type NewGym = Omit<IGym, 'id'> & { id: null };
