import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICheckin, NewCheckin } from '../checkin.model';

export type PartialUpdateCheckin = Partial<ICheckin> & Pick<ICheckin, 'id'>;

export type EntityResponseType = HttpResponse<ICheckin>;
export type EntityArrayResponseType = HttpResponse<ICheckin[]>;

@Injectable({ providedIn: 'root' })
export class CheckinService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('/api/checkins');
  protected resourceUrlBadge = this.applicationConfigService.getEndpointFor('/api/checkin');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(checkin: NewCheckin): Observable<EntityResponseType> {
    return this.http.post<ICheckin>(this.resourceUrl, checkin, { observe: 'response' });
  }

  update(checkin: ICheckin): Observable<EntityResponseType> {
    return this.http.put<ICheckin>(`${this.resourceUrl}/${this.getCheckinIdentifier(checkin)}`, checkin, { observe: 'response' });
  }

  partialUpdate(checkin: PartialUpdateCheckin): Observable<EntityResponseType> {
    return this.http.patch<ICheckin>(`${this.resourceUrl}/${this.getCheckinIdentifier(checkin)}`, checkin, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICheckin>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  checkin(uid: string): Observable<EntityResponseType> {
    return this.http.get<ICheckin>(`${this.resourceUrlBadge}/${uid}`, { observe: 'response' });
  }

  checkinConfirm(checkin: PartialUpdateCheckin): Observable<EntityResponseType> {
    return this.http.post<ICheckin>(`${this.resourceUrlBadge}/confirm`, checkin, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICheckin[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  queryBySession(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICheckin[]>(`${this.resourceUrl}/badge/account`, { params: options, observe: 'response' });
  }

  checkinBadge(id: string, req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICheckin[]>(`${this.resourceUrl}/badge/${id}`, { params: options, observe: 'response' });
  }

  checkinGym(id: string, req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICheckin[]>(`${this.resourceUrl}/gym/${id}`, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCheckinIdentifier(checkin: Pick<ICheckin, 'id'>): number {
    return checkin.id;
  }

  compareCheckin(o1: Pick<ICheckin, 'id'> | null, o2: Pick<ICheckin, 'id'> | null): boolean {
    return o1 && o2 ? this.getCheckinIdentifier(o1) === this.getCheckinIdentifier(o2) : o1 === o2;
  }

  addCheckinToCollectionIfMissing<Type extends Pick<ICheckin, 'id'>>(
    checkinCollection: Type[],
    ...checkinsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const checkins: Type[] = checkinsToCheck.filter(isPresent);
    if (checkins.length > 0) {
      const checkinCollectionIdentifiers = checkinCollection.map(checkinItem => this.getCheckinIdentifier(checkinItem)!);
      const checkinsToAdd = checkins.filter(checkinItem => {
        const checkinIdentifier = this.getCheckinIdentifier(checkinItem);
        if (checkinCollectionIdentifiers.includes(checkinIdentifier)) {
          return false;
        }
        checkinCollectionIdentifiers.push(checkinIdentifier);
        return true;
      });
      return [...checkinsToAdd, ...checkinCollection];
    }
    return checkinCollection;
  }
}
