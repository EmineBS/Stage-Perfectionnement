import { Component, OnInit } from '@angular/core';
import { IEvent } from '../events.model';
import { EventsService } from '../service/events.service';
import { DatePipe } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'jhi-events',
  templateUrl: './events.component.html',
  styleUrls: ['./events.component.scss']
})
export class EventsComponent implements OnInit {

  constructor(private eventsService: EventsService, private datePipe: DatePipe, private router: Router) { }
  events: IEvent[];
  ngOnInit(): void {
    this.events = this.eventsService.Events;
  }

  Close(event:IEvent){
    event.registration=false;
    this.router.navigate(['admin/events']);
  }

  Delete(event:IEvent){
    this.eventsService.Events = this.eventsService.Events.filter(item => item !== event);
    this.events = this.eventsService.Events;
    this.router.navigate(['admin/events']);
  }

  Activate(event:IEvent){
    event.registration=true;
    this.router.navigate(['admin/events']);
  }

  convertTimestampToDateString(timestamp: number | null): string {
    if (timestamp!==null){
      const milliseconds = timestamp;
      const formattedDate = this.datePipe.transform(milliseconds, 'yyyy-MM-dd HH:mm:ss');
      if (formattedDate !== null) {
        return formattedDate;
      } else {
        return '';
      }
    }else{
      return '';
    }
  }

  registration(state: Boolean | null) : string {
    if(state===true){
      return "Open";
    }else{
      return "Close";
    }
  }

}
