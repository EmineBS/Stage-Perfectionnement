import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BadgeCalendarComponent } from '../badge-calendar.component';
import { ASC } from 'app/config/navigation.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';

const badgeCalendarRoute: Routes = [
  {
    path: '',
    component: BadgeCalendarComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(badgeCalendarRoute)],
  exports: [RouterModule],
})
export class CalendarRoutingModule {}
