import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { VisitComponent } from '../list/visit.component';
import { VisitDetailComponent } from '../detail/visit-detail.component';
import { VisitUpdateComponent } from '../update/visit-update.component';
import { VisitRoutingResolveService } from './visit-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const visitRoute: Routes = [
  {
    path: '',
    component: VisitComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: VisitDetailComponent,
    resolve: {
      visit: VisitRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: VisitUpdateComponent,
    resolve: {
      visit: VisitRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: VisitUpdateComponent,
    resolve: {
      visit: VisitRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(visitRoute)],
  exports: [RouterModule],
})
export class VisitRoutingModule {}
