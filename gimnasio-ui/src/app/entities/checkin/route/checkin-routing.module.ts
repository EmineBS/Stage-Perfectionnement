import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CheckinComponent } from '../list/checkin.component';
import { CheckinDetailComponent } from '../detail/checkin-detail.component';
import { CheckinUpdateComponent } from '../update/checkin-update.component';
import { CheckinRoutingResolveService } from './checkin-routing-resolve.service';
import { ASC, DESC } from 'app/config/navigation.constants';
import { NavCheckinComponent } from '../nav-checkin/nav-checkin.component';

const checkinRoute: Routes = [
  {
    path: '',
    component: CheckinComponent,
    data: {
      defaultSort: 'createdDate,' + DESC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: NavCheckinComponent,
    resolve: {
      checkin: CheckinRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(checkinRoute)],
  exports: [RouterModule],
})
export class CheckinRoutingModule {}
