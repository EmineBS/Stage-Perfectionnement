import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { UserComponent } from './list/user.component';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { UserRoutingModule } from './route/user-routing.module';
import { UserUpdateComponent } from './update/user-update.component';
import { UserDetailComponent } from './detail/user-detail.component';
import { UserDeleteDialogComponent } from './delete/user-delete-dialog.component';
import { NotifierModule, NotifierOptions, NotifierService } from 'angular-notifier';

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
  imports: [SharedModule, UserRoutingModule, BsDropdownModule.forRoot(), NotifierModule.withConfig(customNotifierOptions)],
  declarations: [UserComponent, UserUpdateComponent, UserDetailComponent, UserDeleteDialogComponent],
  providers: [NotifierService],
})
export class UserModule {}
