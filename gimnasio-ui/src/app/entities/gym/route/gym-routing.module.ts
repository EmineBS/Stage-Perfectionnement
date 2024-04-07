import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { GymComponent } from '../list/gym.component';
import { GymDetailComponent } from '../detail/gym-detail.component';
import { GymUpdateComponent } from '../update/gym-update.component';
import { GymRoutingResolveService } from './gym-routing-resolve.service';
import { ASC, DESC } from 'app/config/navigation.constants';
import { NavGymComponent } from '../nav-gym/nav-gym.component';

const gymRoute: Routes = [
  {
    path: '',
    component: GymComponent,
    data: {
      defaultSort: 'createdDate,' + DESC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: NavGymComponent,
    resolve: {
      gym: GymRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: GymUpdateComponent,
    resolve: {
      gym: GymRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: GymUpdateComponent,
    resolve: {
      gym: GymRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(gymRoute)],
  exports: [RouterModule],
})
export class GymRoutingModule {}
