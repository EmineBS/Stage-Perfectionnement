import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ProgressComponent } from './progress.component';
import { NgApexchartsModule } from 'ng-apexcharts';
import { ProgressRoutingModule } from './route/progress-routing.module';

@NgModule({
  imports: [SharedModule, NgApexchartsModule, ProgressRoutingModule],
  declarations: [
    ProgressComponent
  ],
})
export class ProgressModule {}
