import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ListComponent } from  '../list/list.component'
import { AjoutAmineComponent } from '../ajout/ajout.component';
import { NavAmineComponent } from '../nav-amine/nav-amine.component';

const amineRoute: Routes = [
    {
      path: '',
      component: ListComponent,
      canActivate: [UserRouteAccessService],
    },
    {
      path: 'new',
      component: AjoutAmineComponent,
      canActivate: [UserRouteAccessService],
    },
    {
      path: ':id/view',
      component: NavAmineComponent,
      canActivate: [UserRouteAccessService],
    },
  ];
  
  @NgModule({
    imports: [RouterModule.forChild(amineRoute)],
    exports: [RouterModule],
  })
  export class AmineRoutingModule {}