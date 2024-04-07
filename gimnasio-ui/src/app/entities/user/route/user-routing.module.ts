import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { UserComponent } from '../list/user.component';
import { UserDetailComponent } from '../detail/user-detail.component';
import { UserUpdateComponent } from '../update/user-update.component';
import { UserRoutingResolveService } from './user-routing-resolve.service';
import { ASC, DESC } from 'app/config/navigation.constants';
//import { NavUserComponent } from '../nav-user/nav-user.component';

const userRoute: Routes = [
  {
    path: '',
    component: UserComponent,
    // data: {
    //   defaultSort: 'createdDate,' + DESC,
    // },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: UserDetailComponent,
    resolve: {
      user: UserRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: UserUpdateComponent,
    resolve: {
      user: UserRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  // {
  //   path: ':id/edit',
  //   component: UserUpdateComponent,
  //   resolve: {
  //     user: UserRoutingResolveService,
  //   },
  //   canActivate: [UserRouteAccessService],
  // },
];

@NgModule({
  imports: [RouterModule.forChild(userRoute)],
  exports: [RouterModule],
})
export class UserRoutingModule {}
