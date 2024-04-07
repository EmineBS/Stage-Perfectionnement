import { Injectable } from '@angular/core';
import { IEvent } from '../events.model';

@Injectable({
  providedIn: 'root'
})
export class EventsService {
  Events: IEvent[]=[];
  constructor() { }
}
