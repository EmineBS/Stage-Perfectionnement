import { IBadge } from 'app/entities/badge/badge.model';
import { IGym } from '../gym/gym.model';
import { CRITERIA_DISPLAY } from '../enumerations/criteriaDisplay';

export interface ICriteria {
  id: number;
  name?: string | null;
  description?: string | null;
  enabled?: Boolean | null;
  display?: CRITERIA_DISPLAY | null;
  gym?: Pick<IGym, 'id' | 'name'> | null;
  gymId?: number | null;
  gymName?: string | null;
  createdBy?: string | null;
  createdDate?: Date | null;
  lastModifiedBy?: Date | null;
  lastModifiedDate?: Date | null;
}

export type NewCriteria = Omit<ICriteria, 'id'> & { id: null };
