import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { GameRoutingResolveService } from './game-routing-resolve.service';
import { ASC, DESC } from 'app/config/navigation.constants';
import { GameComponent } from '../list/game.component';
import { AjoutComponent } from '../ajout/ajout.component';

const gameRoute: Routes = [
  {
    path: '',
    component: GameComponent,
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
  /**{
    path: 'new',
    component: GymUpdateComponent,
    resolve: {
      gym: GameRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: GymUpdateComponent,
    resolve: {
      gym: GameRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },**/
];

@NgModule({
  imports: [RouterModule.forChild(gameRoute)],
  exports: [RouterModule],
})
export class GameRoutingModule {}