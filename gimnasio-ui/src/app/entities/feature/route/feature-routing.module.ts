import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { FeatureComponent } from '../list/feature.component';
import { FeatureDetailComponent } from '../detail/feature-detail.component';
import { FeatureUpdateComponent } from '../update/feature-update.component';
import { FeatureRoutingResolveService } from './feature-routing-resolve.service';
import { ASC, DESC } from 'app/config/navigation.constants';
import { NavFeatureComponent } from '../nav-feature/nav-feature.component';

const featureRoute: Routes = [
  {
    path: '',
    component: FeatureComponent,
    data: {
      defaultSort: 'createdDate,' + DESC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: NavFeatureComponent,
    resolve: {
      feature: FeatureRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FeatureUpdateComponent,
    resolve: {
      feature: FeatureRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FeatureUpdateComponent,
    resolve: {
      feature: FeatureRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(featureRoute)],
  exports: [RouterModule],
})
export class FeatureRoutingModule {}
