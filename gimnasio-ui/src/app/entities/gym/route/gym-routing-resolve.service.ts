import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IGym } from '../gym.model';
import { GymService } from '../service/gym.service';

@Injectable({ providedIn: 'root' })
export class GymRoutingResolveService implements Resolve<IGym | null> {
  constructor(protected service: GymService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGym | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((gym: HttpResponse<IGym>) => {
          if (gym.body) {
            return of(gym.body);
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
