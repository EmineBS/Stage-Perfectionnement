import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICheckin } from '../checkin.model';
import { CheckinService } from '../service/checkin.service';

@Injectable({ providedIn: 'root' })
export class CheckinRoutingResolveService implements Resolve<ICheckin | null> {
  constructor(protected service: CheckinService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICheckin | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((checkin: HttpResponse<ICheckin>) => {
          if (checkin.body) {
            return of(checkin.body);
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
