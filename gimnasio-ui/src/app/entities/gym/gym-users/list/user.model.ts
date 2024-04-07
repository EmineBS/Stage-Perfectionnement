import { IUser } from 'app/entities/user/user.model';
import { IGym } from '../../gym.model';

export interface Iuser {
  id: number;
  login?: string;
  firstName?: string;
  email?: string;
}

export interface IuserGym {
  id: number;
  user?: Pick<IUser, 'id' | 'login'> | null;
  gym?: Pick<IGym, 'id'> | null;
  createdBy?: string | null;
  createdDate?: Date | null;
  lastModifiedBy?: Date | null;
  lastModifiedDate?: Date | null;
  gymId?: number | null;
  userId?: string | null;
}

export type NewUser = Omit<Iuser, 'id'> & { id: null };

export type NewUserGym = Omit<IuserGym, 'id'> & { id: null };
