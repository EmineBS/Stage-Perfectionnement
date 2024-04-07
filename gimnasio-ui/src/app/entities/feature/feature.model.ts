export interface IFeature {
  id: number;
  name?: string | null;
  description?: string | null;
  enabled?: Boolean | null;
  price?: number | null;
  createdBy?: string | null;
  createdDate?: Date | null;
  lastModifiedBy?: Date | null;
  lastModifiedDate?: Date | null;
}


export type NewFeature = Omit<IFeature, 'id'> & { id: null };
