import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { isPresent } from 'app/core/util/operators';
import { Pagination } from 'app/core/request/request.model';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/service/user.service';
import { NewGymFeature, IGymFeature } from '../list/feature.model';

export type PartialUpdateGym = Partial<IGymFeature> & Pick<IGymFeature, 'id'>;

export type EntityResponseType = HttpResponse<IGymFeature>;
export type EntityArrayResponseType = HttpResponse<IGymFeature[]>;

@Injectable({ providedIn: 'root' })
export class GymFeatureService {
  private resourceUrlAdmin = this.applicationConfigService.getEndpointFor('api/admin/rel_gym_features');

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService, private userService: UserService) {}

  query(req?: Pagination): Observable<HttpResponse<IUser[]>> {
    const options = createRequestOption(req);
    return this.http.get<IUser[]>(this.resourceUrlAdmin, { params: options, observe: 'response' });
  }

  gymFeatures(idgym: number, req?: Pagination): Observable<HttpResponse<IGymFeature[]>> {
    const options = createRequestOption(req);
    return this.http.get<IGymFeature[]>(`${this.resourceUrlAdmin}/features/${idgym}`, { params: options, observe: 'response' });
  }

  create(gym: IGymFeature): Observable<EntityResponseType> {
    return this.http.post<IGymFeature>(this.resourceUrlAdmin, gym, { observe: 'response' });
  }

  queryAdmin(req?: Pagination): Observable<HttpResponse<IUser[]>> {
    const options = createRequestOption(req);
    return this.http.get<IUser[]>(this.resourceUrlAdmin, { params: options, observe: 'response' });
  }

  compareUser(o1: Pick<IUser, 'id'> | null, o2: Pick<IUser, 'id'> | null): boolean {
    return o1 && o2 ? o1.id === o2.id : o1 === o2;
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrlAdmin}/${id}`, { observe: 'response' });
  }

  addUserToCollectionIfMissing<Type extends Partial<IUser> & Pick<IUser, 'id'>>(
    userCollection: Type[],
    ...usersToCheck: (Type | null | undefined)[]
  ): IUser[] {
    const users: Type[] = usersToCheck.filter(isPresent);
    if (users.length > 0) {
      const userCollectionIdentifiers = userCollection.map(userItem => this.userService.getUserIdentifier(userItem)!);
      const usersToAdd = users.filter(userItem => {
        const userIdentifier = this.userService.getUserIdentifier(userItem);
        if (userCollectionIdentifiers.includes(userIdentifier)) {
          return false;
        }
        userCollectionIdentifiers.push(userIdentifier);
        return true;
      });
      return [...usersToAdd, ...userCollection];
    }
    return userCollection;
  }
}
