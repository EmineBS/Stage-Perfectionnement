import { ICriteria } from '../criteria/criteria.model';
import { BADGE_PACK_STATUS } from '../enumerations/badgePackStatus';
import { STATUS } from '../enumerations/status.model';
import { IGym } from '../gym/gym.model';
import { IPack } from '../pack/pack.model';

export interface IBadge {
  id: number;
  relBadgePackStatus?:  BADGE_PACK_STATUS | null;
  uid?: string | null;
  status?: STATUS | null;
  createdBy?: string | null;
  createdDate?: Date | null;
  lastModifiedBy?: Date | null;
  lastModifiedDate?: Date | null;
  packId?: Pick<IPack, 'id' | 'name'> | null;
  gym?: Pick<IGym, 'id' | 'name'> | null;
  gymName?: string | null;
  packName?: string | null;
  gymId?: number | null;
  extBadgesDesignationStatus?: string | null;
  remainingSessions?: number | null;
}

export interface IPackBadge {
  id: number;
  enabled: boolean;
  status: string;
  pack?: Pick<IPack, 'id' | 'name' | 'workoutSessions'> | null;
  badge: Pick<IBadge, 'id' | 'uid' | 'relBadgePackStatus'>;
}

export interface IPackBadgeRS {
  id: number;
  enabled: boolean;
  status: string;
  remainingSessions: number | null;
  pack?: Pick<IPack, 'id' | 'name' | 'workoutSessions'> | null;
  badge: Pick<IBadge, 'id' | 'uid' | 'relBadgePackStatus'>;
}

export interface IProgress {
  id: number;
  value: number;
  badge: Pick<IBadge, 'id' | 'uid' | 'relBadgePackStatus'>;
  criteria?: Pick<ICriteria, 'id' | 'name' | 'description' | 'enabled' | 'display'> | null;
}

export interface IBadgeProgressIProgress {
  id: number;
  value: number;
  badge: Pick<IBadge, 'id' | 'uid' | 'relBadgePackStatus'>;
  criteria?: Pick<ICriteria, 'id' | 'name' | 'description' | 'enabled' | 'display'> | null;
}

export interface ICriteriaValue {
  idCriteria: number;
  value: number;
}

export interface ISaveProgress {
  id: number;
  badgeId: number | null;
  criteriaValues: ICriteriaValue[] | null;
}

export interface IProgressValue {
  when?: string | null;
  value: number | null;
}
export interface IBadgeProgress {
  id: number;
  name: string | null;
  description: string | null;
  enabled: boolean | null;
  progressValues: IProgressValue[] | null;
}

export type NewBadge = Omit<IBadge, 'id'> & { id: null };
export type NewPackBadge = Omit<IPackBadge, 'id'> & { id: null };
export type NewProgress = Omit<IProgress, 'id'> & { id: null };
export type NewSaveProgress = Omit<ISaveProgress, 'id'> & { id: null };
export type NewBadgeProgress = Omit<IBadgeProgress, 'id'> & { id: null };
