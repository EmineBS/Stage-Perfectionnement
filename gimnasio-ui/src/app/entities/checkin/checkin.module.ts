import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CheckinComponent } from './list/checkin.component';
import { CheckinDetailComponent } from './detail/checkin-detail.component';
import { CheckinUpdateComponent } from './update/checkin-update.component';
import { CheckinDeleteDialogComponent } from './delete/checkin-delete-dialog.component';
import { CheckinRoutingModule } from './route/checkin-routing.module';
import { NavCheckinComponent } from './nav-checkin/nav-checkin.component';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { NotifierModule, NotifierOptions, NotifierService } from 'angular-notifier';

/**
 * Custom angular notifier options
 */
const customNotifierOptions: NotifierOptions = {
  position: {
    horizontal: {
      position: 'right',
      distance: 12,
    },
    vertical: {
      position: 'top',
      distance: 12,
      gap: 10,
    },
  },
  theme: 'uifort',
  behaviour: {
    autoHide: 5000,
    onClick: 'hide',
    onMouseover: 'pauseAutoHide',
    showDismissButton: true,
    stacking: 4,
  },
  animations: {
    enabled: true,
    show: {
      preset: 'slide',
      speed: 300,
      easing: 'ease',
    },
    hide: {
      preset: 'fade',
      speed: 300,
      easing: 'ease',
      offset: 50,
    },
    shift: {
      speed: 300,
      easing: 'ease',
    },
    overlap: 150,
  },
};

@NgModule({
  imports: [SharedModule, CheckinRoutingModule, BsDropdownModule.forRoot(), NotifierModule.withConfig(customNotifierOptions)],
  declarations: [CheckinComponent, CheckinDetailComponent, CheckinUpdateComponent, CheckinDeleteDialogComponent, NavCheckinComponent],
  providers: [NotifierService],
})
export class CheckinModule {}
