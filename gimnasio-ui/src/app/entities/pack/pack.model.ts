import { IBadge } from 'app/entities/badge/badge.model';
import { IGym } from '../gym/gym.model';

export interface IPack {
  id: number;
  name?: string | null;
  description?: string | null;
  workoutSessions?: number | null;
  gym?: Pick<IGym, 'id' | 'name'> | null;
  createdBy?: string | null;
  createdDate?: Date | null;
  lastModifiedBy?: Date | null;
  lastModifiedDate?: Date | null;
  price?: number | null;
  autoConfirm?: boolean | null;
  rdvEnabled?: boolean | null;
}

export type NewPack = Omit<IPack, 'id'> & { id: null };
