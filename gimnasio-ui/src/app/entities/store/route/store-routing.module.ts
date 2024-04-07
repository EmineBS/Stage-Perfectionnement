import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { StoreComponent } from '../list/store.component';
import { AjoutComponent } from '../ajout/ajout.component';
import { NavStoreComponent } from '../nav-store/nav-store.component';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';

const storeRoute: Routes= [
  {
    path: '',
    component: StoreComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AjoutComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: NavStoreComponent,
    canActivate: [UserRouteAccessService],
  },
]

@NgModule({
  imports: [RouterModule.forChild(storeRoute)],
  exports: [RouterModule]
})
export class StoreRoutingModule { }
