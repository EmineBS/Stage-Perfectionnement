import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CheckinBadgeHistoryComponent } from '../checkin-badge-history.component';
import { CheckinRoutingResolveService } from './checkin-badge-history-routing-resolve.service';

const progressRoute: Routes = [
  {
    path: '',
    component: CheckinBadgeHistoryComponent,
    // To be removed when we will create an api that return badge details by session
    // resolve: {
    //   badge: CheckinRoutingResolveService,
    // },
    canActivate: [UserRouteAccessService],
  }
];

@NgModule({
  imports: [RouterModule.forChild(progressRoute)],
  exports: [RouterModule],
})
export class CheckinBadgeHistoryComponentRoutingModule {}
