import { IUser } from 'app/entities/user/user.model';
import { IBadge } from 'app/entities/badge/badge.model';

export interface ICheckin {
  id: number;
  status?: string | null;
  user?: Pick<IUser, 'id'> | null;
  badge?: Pick<IBadge, 'id'> | null;
  userId?: string | null;
  badgeUid?: string | null;
  lastModifiedDate?: Date | null;
  lastModifiedBy?: string | null;
  createdDate?: Date | null;
  createdBy?: string | null;
  badgeId: number;
  packId: number;
  packName: string | null;
  packWorkoutSessions: number;
  checkinLeft: number;
  relBadgePackId: number;
}

export type NewCheckin = Omit<ICheckin, 'id'> & { id: null };
