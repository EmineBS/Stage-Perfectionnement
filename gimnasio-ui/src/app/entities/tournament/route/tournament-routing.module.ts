import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TournamentRoutingResolve } from './tournament-routing-resolve.service';
import { ASC, DESC } from 'app/config/navigation.constants';
import { TournamentComponent } from '../list/tournament.component';
import { AjoutComponent } from '../ajout/ajout.component';

const tournamentRoute: Routes = [
  {
    path: '',
    component: TournamentComponent,
    data: {
      defaultSort: 'createdDate,' + DESC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AjoutComponent,
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(tournamentRoute)],
  exports: [RouterModule],
})
export class TournamentRoutingModule { }
