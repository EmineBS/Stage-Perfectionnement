export interface IVisit {
  id: number;
  fromDate?: Date | null;
  toDate?: Date | null;
  enabled?: string | null;
  status?: string | null;
  calendarId?: number | null;
  relBadgePackId?: number | null;
  packName?: string | null;
  profileEmail?: string | null;
  badgeId?: number | null;
}

export type NewVisit = Omit<IVisit, 'id'> & { id: null };
