import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { BadgeRoutingResolveService } from './badge-routing-resolve.service';
import { ASC, DESC } from 'app/config/navigation.constants';
import { NavbarComponent } from 'app/layouts/navbar/navbar.component';

const badgeRoutes: Routes = [];

@NgModule({
  imports: [RouterModule.forChild(badgeRoutes)],
  exports: [RouterModule],
})
export class BadgeRoutingModule {}
