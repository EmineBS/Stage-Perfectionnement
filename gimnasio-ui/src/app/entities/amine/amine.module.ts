import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { AmineRoutingModule } from './route/amine-routing.module';
import { AmineUpdateComponent } from './update/amine-update.component';
import { ListComponent } from './list/list.component';
import { AjoutAmineComponent } from './ajout/ajout.component';
import { NavAmineComponent } from './nav-amine/nav-amine.component';
import { DetailComponent } from './detail/detail.component'


@NgModule({
  imports: [SharedModule, AmineRoutingModule, BsDropdownModule.forRoot()],
  declarations: [
    ListComponent,
    AmineUpdateComponent,
    AjoutAmineComponent,
    NavAmineComponent,
    DetailComponent
  ],
})
export class AmineModule { }
