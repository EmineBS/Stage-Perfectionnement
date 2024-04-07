import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IGym, NewGym } from '../gym.model';

export type PartialUpdateGym = Partial<IGym> & Pick<IGym, 'id'>;

export type EntityResponseType = HttpResponse<IGym>;
export type EntityArrayResponseType = HttpResponse<IGym[]>;

@Injectable({ providedIn: 'root' })
export class GymService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/admin/gyms');
  protected resourceUrlBadgeAccount = this.applicationConfigService.getEndpointFor('api/gyms');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(gym: NewGym): Observable<EntityResponseType> {
    return this.http.post<IGym>(this.resourceUrl, gym, { observe: 'response' });
  }

  update(gym: IGym): Observable<EntityResponseType> {
    return this.http.put<IGym>(`${this.resourceUrl}/${this.getGymIdentifier(gym)}`, gym, { observe: 'response' });
  }

  partialUpdate(gym: PartialUpdateGym): Observable<EntityResponseType> {
    return this.http.patch<IGym>(`${this.resourceUrl}/${this.getGymIdentifier(gym)}`, gym, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IGym>(`api/gyms/${id}`, { observe: 'response' });
  }

  findBySession(): Observable<EntityResponseType> {
    return this.http.get<IGym>(`${this.resourceUrlBadgeAccount}/badge-account`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGym[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getGymIdentifier(gym: Pick<IGym, 'id'>): number {
    return gym.id;
  }

  compareGym(o1: Pick<IGym, 'id'> | null, o2: Pick<IGym, 'id'> | null): boolean {
    return o1 && o2 ? this.getGymIdentifier(o1) === this.getGymIdentifier(o2) : o1 === o2;
  }

  addGymToCollectionIfMissing<Type extends Pick<IGym, 'id'>>(gymCollection: Type[], ...gymsToCheck: (Type | null | undefined)[]): Type[] {
    const gyms: Type[] = gymsToCheck.filter(isPresent);
    if (gyms.length > 0) {
      const gymCollectionIdentifiers = gymCollection.map(gymItem => this.getGymIdentifier(gymItem)!);
      const gymsToAdd = gyms.filter(gymItem => {
        const gymIdentifier = this.getGymIdentifier(gymItem);
        if (gymCollectionIdentifiers.includes(gymIdentifier)) {
          return false;
        }
        gymCollectionIdentifiers.push(gymIdentifier);
        return true;
      });
      return [...gymsToAdd, ...gymCollection];
    }
    return gymCollection;
  }
}
