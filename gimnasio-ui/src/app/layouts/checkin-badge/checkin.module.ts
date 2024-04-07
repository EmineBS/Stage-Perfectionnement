import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { CheckinBadgeComponent } from './checkin-badge.component';
import { ConfirmComponent } from './confirm/confirm.component';
import { VerifyComponent } from './verify/verify.component';

@NgModule({
  imports: [SharedModule, RouterModule],
  declarations: [CheckinBadgeComponent, ConfirmComponent],
})
export class CheckinModule {}
