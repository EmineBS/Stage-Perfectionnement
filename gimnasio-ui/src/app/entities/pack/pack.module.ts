import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PackComponent } from './list/pack.component';
import { PackDetailComponent } from './detail/pack-detail.component';
import { PackUpdateComponent } from './update/pack-update.component';
import { PackDeleteDialogComponent } from './delete/pack-delete-dialog.component';
import { PackRoutingModule } from './route/pack-routing.module';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { NavPackComponent } from './nav-pack/nav-pack.component';

@NgModule({
  imports: [SharedModule, PackRoutingModule, BsDropdownModule.forRoot()],
  declarations: [PackComponent, PackDetailComponent, PackUpdateComponent, PackDeleteDialogComponent, NavPackComponent],
})
export class PackModule {}
