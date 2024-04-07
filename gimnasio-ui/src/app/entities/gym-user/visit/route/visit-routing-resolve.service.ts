import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IVisit } from '../visit.model';
import { VisitService } from '../service/visit.service';

@Injectable({ providedIn: 'root' })
export class VisitRoutingResolveService implements Resolve<IVisit | null> {
  constructor(protected service: VisitService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IVisit | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((visit: HttpResponse<IVisit>) => {
          if (visit.body) {
            return of(visit.body);
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
