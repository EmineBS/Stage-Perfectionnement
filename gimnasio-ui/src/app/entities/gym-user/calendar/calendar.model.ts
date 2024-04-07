import { IGym } from 'app/entities/gym/gym.model';

export interface ICalendar {
  id: number;
  gymId?: number | null;
  gymName?: string | null;
  endHour?: string | null;
  startHour?: string | null;
  offDays?: number[] | null;
  unit?: number | null;
  gym?: Pick<IGym, 'id'> | null;
  lastModifiedDate?: Date | null;
}

export type NewCalendar = Omit<ICalendar, 'id'> & { id: null };
