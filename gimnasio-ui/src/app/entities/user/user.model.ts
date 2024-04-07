export interface IUser {
  id: string;
  login?: string | null;
  email?: string | null;
  password?: string | null;
}

export class User implements IUser {
  constructor(public id: string, public login: string) {}
}


export type NewUser= Omit<IUser, 'id'> & { id: null };