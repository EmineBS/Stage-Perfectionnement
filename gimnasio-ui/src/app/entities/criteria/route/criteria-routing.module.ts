import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CriteriaComponent } from '../list/criteria.component';
import { CriteriaDetailComponent } from '../detail/criteria-detail.component';
import { CriteriaUpdateComponent } from '../update/criteria-update.component';
import { CriteriaRoutingResolveService } from './criteria-routing-resolve.service';
import { ASC, DESC } from 'app/config/navigation.constants';
import { NavCriteriaComponent } from '../nav-criteria/nav-criteria.component';

const criteriaRoute: Routes = [
  {
    path: '',
    component: CriteriaComponent,
    data: {
      defaultSort: 'createdDate,' + DESC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: NavCriteriaComponent,
    resolve: {
      criteria: CriteriaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CriteriaUpdateComponent,
    resolve: {
      criteria: CriteriaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CriteriaUpdateComponent,
    resolve: {
      criteria: CriteriaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(criteriaRoute)],
  exports: [RouterModule],
})
export class CriteriaRoutingModule {}
