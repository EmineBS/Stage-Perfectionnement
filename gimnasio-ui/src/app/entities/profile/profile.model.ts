export interface IProfile {
  id: number;
  username?: string | null;
  name?: string | null;
  lastName?: string | null;
  email?: string | null;
  phoneNumber?: string | null;
  badgeId?: number | null;
}

export type NewProfile = Omit<IProfile, 'id'> & { id: null };
