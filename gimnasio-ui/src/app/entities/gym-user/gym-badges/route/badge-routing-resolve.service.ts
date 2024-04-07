import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';
import { BadgeService } from 'app/entities/badge/service/badge.service';
import { IBadge } from 'app/entities/badge/badge.model';

@Injectable({ providedIn: 'root' })
export class BadgeRoutingResolveService implements Resolve<IBadge | null> {
  constructor(protected service: BadgeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBadge | null | never> {
    const id = route.params['uid'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((badge: HttpResponse<IBadge>) => {
          if (badge.body) {
            return of(badge.body);
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
