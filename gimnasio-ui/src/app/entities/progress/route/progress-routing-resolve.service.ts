import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { CriteriaService, EntityArrayResponseTypeBadgeProgress } from 'app/entities/criteria/service/criteria.service';
import { IBadgeProgress } from 'app/entities/badge/badge.model';

@Injectable({ providedIn: 'root' })
export class ProgressRoutingResolveService implements Resolve<IBadgeProgress[] | null> {
  constructor(protected service: CriteriaService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBadgeProgress[] | null | never> {
    return this.service.queryBySession().pipe(
      mergeMap((progresss: EntityArrayResponseTypeBadgeProgress) => {
        if (progresss.body || []) {
          return of(progresss.body);
        } else {
          this.router.navigate(['404']);
          return EMPTY;
        }
      })
    );
  }
}
