import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ProgressComponent } from '../progress.component';
import { ProgressRoutingResolveService } from './progress-routing-resolve.service';

const progressRoute: Routes = [
  {
    path: '',
    component: ProgressComponent,
    // To be removed when we will create an api that return badge details by session
    resolve: {
      progress: ProgressRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  }
];

@NgModule({
  imports: [RouterModule.forChild(progressRoute)],
  exports: [RouterModule],
})
export class ProgressRoutingModule {}
