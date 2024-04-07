import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CommonModule } from '@angular/common';

import { CalendarComponent } from './list/calendar.component';
import { CalendarDetailComponent } from './detail/calendar-detail.component';
import { CalendarUpdateComponent } from './update/calendar-update.component';
import { CalendarDeleteDialogComponent } from './delete/calendar-delete-dialog.component';
import { CalendarRoutingModule } from './route/calendar-routing.module';
import { CalendarModule, DateAdapter, CalendarNativeDateFormatter, CalendarDateFormatter, DateFormatterParams } from 'angular-calendar';
import { adapterFactory } from 'angular-calendar/date-adapters/date-fns';

import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { ModalCreateVisitComponent } from '../badge-calendar/modal-create-visit/modal-create-visit.component';
import { AddUserVisitComponent } from './user-add-visit/add-user-visit/add-user-visit.component';
import { NotifierModule, NotifierOptions, NotifierService } from 'angular-notifier';
import { ViewCalendarConfigComponent } from './view-calendar-config/view-calendar-config.component';
import { NgSelectModule } from '@ng-select/ng-select';

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
  imports: [
    CommonModule,
    SharedModule,
    CalendarRoutingModule,
    CalendarModule.forRoot(
      {
        provide: DateAdapter,
        useFactory: adapterFactory,
      },
      {
        dateFormatter: {
          provide: CalendarDateFormatter,
          useClass: GymCalendarModule,
        },
      }
    ),
    NgSelectModule,

    BsDatepickerModule.forRoot(),
    NotifierModule.withConfig(customNotifierOptions)
  ],
  declarations: [
    CalendarComponent,
    CalendarDetailComponent,
    CalendarUpdateComponent,
    CalendarDeleteDialogComponent,
    ModalCreateVisitComponent,
    AddUserVisitComponent,
    ViewCalendarConfigComponent
  ],
})
export class GymCalendarModule extends CalendarNativeDateFormatter {
  public weekViewHour({ date, locale }: DateFormatterParams): string {
    return new Intl.DateTimeFormat('en-US', {
      hour: 'numeric',
      minute: 'numeric',
    }).format(date);
  }
  public dayViewHour({ date, locale }: DateFormatterParams): string {
    return new Intl.DateTimeFormat('en-US', {
      hour: 'numeric',
      minute: 'numeric',
    }).format(date);
  }
}
