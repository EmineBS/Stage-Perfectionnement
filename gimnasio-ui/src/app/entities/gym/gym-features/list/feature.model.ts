import { IUser } from 'app/entities/user/user.model';
import { IGym } from '../../gym.model';
import { IFeature } from 'app/entities/feature/feature.model';


export interface IGymFeature {
  id: number | null;
  gym?: Pick<IGym, 'id' | 'name' > | null;
  feature?: Pick<IFeature, 'id' | 'name' > | null;
  createdBy?: string | null;
  createdDate?: Date | null;
  lastModifiedBy?: Date | null;
  lastModifiedDate?: Date | null;
  gymId?: number | null;
  featureId?: number | null;
  featureName?:string | null;
}

export type NewGymFeature = Omit<IGymFeature, 'id'> & { id: null };
