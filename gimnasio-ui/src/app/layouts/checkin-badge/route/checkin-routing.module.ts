import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { CheckinBadgeComponent } from 'app/layouts/checkin-badge/checkin-badge.component';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';

const CheckinRoute: Routes = [
  {
    path: 'qr',
    component: CheckinBadgeComponent,
    data: {
      pageTitle: 'checkin.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'qr/:id',
    component: CheckinBadgeComponent,
    data: {
      pageTitle: 'checkin.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(CheckinRoute)],
  exports: [RouterModule],
})
export class CheckinRoutingModule {}
