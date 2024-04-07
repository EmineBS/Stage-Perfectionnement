import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICriteria } from '../criteria.model';
import { CriteriaService } from '../service/criteria.service';

@Injectable({ providedIn: 'root' })
export class CriteriaRoutingResolveService implements Resolve<ICriteria | null> {
  constructor(protected service: CriteriaService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICriteria | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((criteria: HttpResponse<ICriteria>) => {
          if (criteria.body) {
            return of(criteria.body);
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
