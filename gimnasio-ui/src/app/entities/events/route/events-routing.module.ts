import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { EventsComponent } from '../list/events.component';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AjoutComponent } from '../ajout/ajout.component';
import { NavEventsComponent } from '../nav-events/nav-events.component';


const eventsRoute: Routes= [
  {
    path: '',
    component: EventsComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AjoutComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: NavEventsComponent,
    canActivate: [UserRouteAccessService],
  },
]

@NgModule({
  imports: [CommonModule,RouterModule.forChild(eventsRoute)],
  exports: [RouterModule]
})
export class EventsRoutingModule { }
