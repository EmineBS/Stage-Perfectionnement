import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { StoreComponent } from './list/store.component';
import { AjoutComponent } from './ajout/ajout.component';
import { NavStoreComponent } from './nav-store/nav-store.component';
import { StoreRoutingModule } from './route/store-routing.module';
import { SharedModule } from 'app/shared/shared.module';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';


@NgModule({
  declarations: [
    StoreComponent,
    AjoutComponent,
    NavStoreComponent
  ],
  imports: [
    SharedModule,
    StoreRoutingModule,
    BsDropdownModule.forRoot()
  ]
})
export class StoreModule { }
