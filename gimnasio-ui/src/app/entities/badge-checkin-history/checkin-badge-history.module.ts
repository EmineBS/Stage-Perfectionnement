import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CheckinBadgeHistoryComponent } from './checkin-badge-history.component'
import { NgApexchartsModule } from 'ng-apexcharts';
import { CheckinBadgeHistoryComponentRoutingModule } from './route/checkin-badge-history-routing.module';

@NgModule({
  imports: [SharedModule,CheckinBadgeHistoryComponentRoutingModule],
  declarations: [
    CheckinBadgeHistoryComponent
  ],
})
export class CheckinBadgeHistoryComponentModule {}
