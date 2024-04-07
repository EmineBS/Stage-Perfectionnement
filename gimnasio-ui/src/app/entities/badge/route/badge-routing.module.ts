import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { BadgeComponent } from '../list/badge.component';
import { BadgeDetailComponent } from '../detail/badge-detail.component';
import { BadgeUpdateComponent } from '../update/badge-update.component';
import { BadgeRoutingResolveService } from './badge-routing-resolve.service';
import { ASC, DESC } from 'app/config/navigation.constants';
import { NavbarComponent } from 'app/layouts/navbar/navbar.component';
import { NavBadgeComponent } from '../nav-badge/nav-badge.component';

const badgeRoute: Routes = [
  {
    path: ':id/pack',
    data: { pageTitle: 'gimnasio-uiApp.pack.home.title' },
    loadChildren: () => import('../../pack/pack.module').then(m => m.PackModule),
  },
  {
    path: '',
    component: BadgeComponent,
    data: {
      defaultSort: 'createdDate,' + DESC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: NavBadgeComponent,
    resolve: {
      badge: BadgeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  } /* 
  {
    path: 'new',
    component: BadgeUpdateComponent,
    resolve: {
      badge: BadgeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  }, */,
  /* {
    path: ':id/edit',
    component: BadgeUpdateComponent,
    resolve: {
      badge: BadgeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  }, */
];

@NgModule({
  imports: [RouterModule.forChild(badgeRoute)],
  exports: [RouterModule],
})
export class BadgeRoutingModule {}
