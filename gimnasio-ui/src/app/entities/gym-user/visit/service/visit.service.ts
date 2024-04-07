import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IVisit, NewVisit } from '../visit.model';

export type PartialUpdateVisit = Partial<IVisit> & Pick<IVisit, 'id'>;

export type EntityResponseType = HttpResponse<IVisit>;
export type EntityArrayResponseType = HttpResponse<IVisit[]>;

@Injectable({ providedIn: 'root' })
export class VisitService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/rdvs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(visit: NewVisit): Observable<EntityResponseType> {
    return this.http.post<IVisit>(this.resourceUrl, visit, { observe: 'response' });
  }

  update(visit: IVisit): Observable<EntityResponseType> {
    return this.http.put<IVisit>(`${this.resourceUrl}/${this.getVisitIdentifier(visit)}`, visit, { observe: 'response' });
  }

  partialUpdate(visit: PartialUpdateVisit): Observable<EntityResponseType> {
    return this.http.patch<IVisit>(`${this.resourceUrl}/${this.getVisitIdentifier(visit)}`, visit, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IVisit>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  findAllByGym(gymId: number): Observable<EntityArrayResponseType> {
    return this.http.get<IVisit[]>(`${this.resourceUrl}/gym/${gymId}`, { observe: 'response' });
  }

  findAllMineByBadgeSession(): Observable<EntityArrayResponseType> {
    return this.http.get<IVisit[]>(`${this.resourceUrl}/badge-account/`, { observe: 'response' });
  }

  findAllOthersByBadgeSession(): Observable<EntityArrayResponseType> {
    return this.http.get<IVisit[]>(`${this.resourceUrl}/badge-others`, { observe: 'response' });
  }

  findAllByBadgeSession(): Observable<EntityArrayResponseType> {
    return this.http.get<IVisit[]>(`${this.resourceUrl}/badge-calendar`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IVisit[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getVisitIdentifier(visit: Pick<IVisit, 'id'>): number {
    return visit.id;
  }

  compareVisit(o1: Pick<IVisit, 'id'> | null, o2: Pick<IVisit, 'id'> | null): boolean {
    return o1 && o2 ? this.getVisitIdentifier(o1) === this.getVisitIdentifier(o2) : o1 === o2;
  }

  addVisitToCollectionIfMissing<Type extends Pick<IVisit, 'id'>>(
    visitCollection: Type[],
    ...visitsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const visits: Type[] = visitsToCheck.filter(isPresent);
    if (visits.length > 0) {
      const visitCollectionIdentifiers = visitCollection.map(visitItem => this.getVisitIdentifier(visitItem)!);
      const visitsToAdd = visits.filter(visitItem => {
        const visitIdentifier = this.getVisitIdentifier(visitItem);
        if (visitCollectionIdentifiers.includes(visitIdentifier)) {
          return false;
        }
        visitCollectionIdentifiers.push(visitIdentifier);
        return true;
      });
      return [...visitsToAdd, ...visitCollection];
    }
    return visitCollection;
  }
}
