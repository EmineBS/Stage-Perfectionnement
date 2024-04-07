import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PackComponent } from '../list/pack.component';
import { PackDetailComponent } from '../detail/pack-detail.component';
import { PackUpdateComponent } from '../update/pack-update.component';
import { PackRoutingResolveService } from './pack-routing-resolve.service';
import { ASC, DESC } from 'app/config/navigation.constants';
import { NavPackComponent } from '../nav-pack/nav-pack.component';

const packRoute: Routes = [
  {
    path: '',
    component: PackComponent,
    data: {
      defaultSort: 'createdDate,' + DESC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: NavPackComponent,
    resolve: {
      pack: PackRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PackUpdateComponent,
    resolve: {
      pack: PackRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PackUpdateComponent,
    resolve: {
      pack: PackRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(packRoute)],
  exports: [RouterModule],
})
export class PackRoutingModule {}
