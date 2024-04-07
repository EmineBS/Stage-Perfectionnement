import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';
import { CalendarService } from 'app/entities/gym-user/calendar/service/calendar.service';
import { ICalendar } from 'app/entities/gym-user/calendar/calendar.model';

@Injectable({ providedIn: 'root' })
export class CalendarRoutingResolveService implements Resolve<ICalendar | null> {
  constructor(protected service: CalendarService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICalendar | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((calendar: HttpResponse<ICalendar>) => {
          if (calendar.body) {
            return of(calendar.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
