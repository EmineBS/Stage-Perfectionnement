export interface Imodal {
  title: string;
  body: string;
  size: string;
  backdrop: string;
}

export type newImodel = Omit<Imodal, 'id'> & { id: null };
