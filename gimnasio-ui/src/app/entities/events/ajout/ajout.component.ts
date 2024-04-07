import { Component, OnInit } from '@angular/core';
import { IEvent } from '../events.model';
import { EventsService } from '../service/events.service';
import { Router } from '@angular/router';


@Component({
  selector: 'jhi-ajout',
  templateUrl: './ajout.component.html',
  styleUrls: ['./ajout.component.scss']
})
export class AjoutComponent implements OnInit {

  startTimeDate: string;

  constructor(private eventsService:EventsService, private router:Router) { }
  event: IEvent = {
    eventName: '',
    region: '',
    registration: false,
    gameName: '',
    startTime: 0,
    minPlayers: 0,
    maxPlayers: 0,
    players: []
  };

  ngOnInit(): void {
  }

  submit(){
    console.log(this.event);
    this.eventsService.Events.push(this.event);
    this.router.navigate(["admin/events"]);
  }

  onStartTimeChange(){
    this.event.startTime = new Date(this.startTimeDate).getTime();
  }

}
