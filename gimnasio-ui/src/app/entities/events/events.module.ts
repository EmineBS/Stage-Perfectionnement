import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { CommonModule } from '@angular/common';
import { EventsComponent } from './list/events.component';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AjoutComponent } from './ajout/ajout.component';
import { NavEventsComponent } from './nav-events/nav-events.component';
import { EventsRoutingModule } from './route/events-routing.module';
import { DatePipe } from '@angular/common';

@NgModule({
  imports: [SharedModule,EventsRoutingModule,BsDropdownModule.forRoot()],
  declarations: [
    EventsComponent,
    AjoutComponent,
    NavEventsComponent],
  providers: [DatePipe],
})
export class EventsModule {}