import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { BadgeComponent } from './list/badge.component';
import { BadgeDetailComponent } from './detail/badge-detail.component';
import { BadgeUpdateComponent } from './update/badge-update.component';
import { BadgeDeleteDialogComponent } from './delete/badge-delete-dialog.component';
import { BadgeRoutingModule } from './route/badge-routing.module';

import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { NavBadgeComponent } from './nav-badge/nav-badge.component';
import { BadgePackComponent } from './badge-pack/badge-pack.component';
import { DeleteComponent } from './badge-pack/delete/delete.component';
import { CheckinBadgeComponent } from './checkin-badge/checkin-badge.component';

@NgModule({
  imports: [SharedModule, BadgeRoutingModule, BsDropdownModule.forRoot()],
  declarations: [
    BadgeComponent,
    BadgeDetailComponent,
    BadgeUpdateComponent,
    BadgeDeleteDialogComponent,
    NavBadgeComponent,
    BadgePackComponent,
    DeleteComponent,
    CheckinBadgeComponent,
  ],
})
export class BadgeModule {}
