import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { isPresent } from 'app/core/util/operators';
import { Pagination } from 'app/core/request/request.model';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/service/user.service';
import { Iuser, IuserGym, NewUser, NewUserGym } from '../list/user.model';

export type PartialUpdateGym = Partial<IuserGym> & Pick<IuserGym, 'id'>;

export type EntityResponseType = HttpResponse<IuserGym>;
export type EntityArrayResponseType = HttpResponse<IuserGym[]>;

@Injectable({ providedIn: 'root' })
export class UsergymService {
  private resourceUrlAdmin = this.applicationConfigService.getEndpointFor('api/rel_user_gym');

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService, private userService: UserService) {}

  query(req?: Pagination): Observable<HttpResponse<IUser[]>> {
    const options = createRequestOption(req);
    return this.http.get<IUser[]>(this.resourceUrlAdmin, { params: options, observe: 'response' });
  }

  gymUsers(idgym: number, req?: Pagination): Observable<HttpResponse<IuserGym[]>> {
    const options = createRequestOption(req);
    return this.http.get<IuserGym[]>(`${this.resourceUrlAdmin}/users/${idgym}`, { params: options, observe: 'response' });
  }

  create(gym: NewUserGym): Observable<EntityResponseType> {
    return this.http.post<IuserGym>(this.resourceUrlAdmin, gym, { observe: 'response' });
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
