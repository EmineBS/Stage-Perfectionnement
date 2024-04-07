import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CommonModule } from '@angular/common';

import { CalendarRoutingModule } from './route/badge-calendar-routing.module';
import { CalendarModule, DateAdapter, CalendarNativeDateFormatter, CalendarDateFormatter, DateFormatterParams } from 'angular-calendar';
import { adapterFactory } from 'angular-calendar/date-adapters/date-fns';

import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { BadgeCalendarComponent } from './badge-calendar.component';

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
          useClass: GymBadgeCalendarModule,
        },
      }
    ),

    BsDatepickerModule.forRoot(),
    NotifierModule.withConfig(customNotifierOptions),
  ],
  declarations: [BadgeCalendarComponent],
  providers: [NotifierService],
})
export class GymBadgeCalendarModule extends CalendarNativeDateFormatter {
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
