import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ProfileComponent } from '../list/profile.component';
import { ProfileDetailComponent } from '../detail/profile-detail.component';
import { ProfileUpdateComponent } from '../update/profile-update.component';
import { ProfileRoutingResolveService } from './profile-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';
import { ProfilNavComponent } from '../profil-nav/profil-nav.component';

const profileRoute: Routes = [
  /* 
  {
    path: '',
    component: ProfileComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  }, */
  {
    path: '',
    component: ProfilNavComponent,
    // To be removed when we will create an api that return badge details by session
    resolve: {
      profile: ProfileRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  /*  {
    path: 'new',
    component: ProfileUpdateComponent,
    resolve: {
      profile: ProfileRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  }, */
  /*   {
    path: ':id/edit',
    component: ProfileUpdateComponent,
    resolve: {
      profile: ProfileRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  }, */
];

@NgModule({
  imports: [RouterModule.forChild(profileRoute)],
  exports: [RouterModule],
})
export class ProfileRoutingModule {}
