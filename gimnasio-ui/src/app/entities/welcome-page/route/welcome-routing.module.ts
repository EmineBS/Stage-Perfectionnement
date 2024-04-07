import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { WelcomePageComponent } from '../welcome-page.component';
import { CheckinBadgeComponent } from 'app/layouts/checkin-badge/checkin-badge.component';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';

const welcomeRoutes: Routes = [
  {
    path: '',
    component: WelcomePageComponent,
  },
  {
    path: 'qr/:id',
    component: CheckinBadgeComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'qr',
    component: CheckinBadgeComponent,
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(welcomeRoutes)],
  exports: [RouterModule],
})
export class WelcomeRoutingModule {}
