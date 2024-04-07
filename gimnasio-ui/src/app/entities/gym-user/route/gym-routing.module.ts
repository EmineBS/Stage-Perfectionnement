import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { GymUpdateComponent } from '../update/gym-update.component';
import { GymRoutingResolveService } from './gym-routing-resolve.service';
import { NavGymComponent } from '../nav-gym/nav-gym.component';
import { GymBadgesComponent } from 'app/entities/gym-user/gym-badges/badges/gym-badges.component';
import { DESC } from 'app/config/navigation.constants';

const gymRoute: Routes = [
  {
    path: 'view',
    component: NavGymComponent,
    resolve: {
      gym: GymRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'edit',
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
