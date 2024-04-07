import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICriteria, NewCriteria } from '../criteria.model';
import { IBadgeProgress, IProgress, IProgressValue } from 'app/entities/badge/badge.model';

export type PartialUpdateCriteria = Partial<ICriteria> & Pick<ICriteria, 'id'>;

export type EntityResponseType = HttpResponse<ICriteria>;
export type EntityArrayResponseType = HttpResponse<ICriteria[]>;
export type EntityArrayResponseTypeBadgeProgress = HttpResponse<IBadgeProgress[]>;

export type PartialUpdateCriterias = Partial<IProgress> & Pick<IProgress, 'id'>;

export type EntityResponseTypes = HttpResponse<IProgress>;
export type EntityArrayResponseTypes = HttpResponse<IProgress[]>;

@Injectable({ providedIn: 'root' })
export class CriteriaService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/criterias');
  protected resourceUrlRel = this.applicationConfigService.getEndpointFor('/api/progresss');
  protected resourceUrlRelCriteriaProgress = this.applicationConfigService.getEndpointFor('/api/relCriteriaProgresss');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(criteria: NewCriteria): Observable<EntityResponseType> {
    return this.http.post<ICriteria>(this.resourceUrl, criteria, { observe: 'response' });
  }

  update(criteria: ICriteria): Observable<EntityResponseType> {
    return this.http.put<ICriteria>(`${this.resourceUrl}/${this.getCriteriaIdentifier(criteria)}`, criteria, { observe: 'response' });
  }

  partialUpdate(criteria: PartialUpdateCriteria): Observable<EntityResponseType> {
    return this.http.patch<ICriteria>(`${this.resourceUrl}/${this.getCriteriaIdentifier(criteria)}`, criteria, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICriteria>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  // findCriteriasPerBadge(id: number, req?: any): Observable<EntityArrayResponseTypes> {
  //   const options = createRequestOption(req);
  //   return this.http.get<IProgress[]>(`${this.resourceUrlRel}/badge/${id}`, { params: options, observe: 'response' });
  // }

  findCriteriasPerGym(id: number, req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICriteria[]>(`${this.resourceUrl}/gym/${id}`, { params: options, observe: 'response' });
  }
  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICriteria[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  queryProgressByBadge(idGym: number, idBadge: number, req?: any): Observable<EntityArrayResponseTypeBadgeProgress> {
    const options = createRequestOption(req);
    return this.http.get<IBadgeProgress[]>(`${this.resourceUrlRelCriteriaProgress}/gym/badge/${idGym}/${idBadge}`, { params: options, observe: 'response' });
  }

  queryBySession(req?: any): Observable<EntityArrayResponseTypeBadgeProgress> {
    const options = createRequestOption(req);
    return this.http.get<IBadgeProgress[]>(`${this.resourceUrlRelCriteriaProgress}/account`, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  // deleteRel(id: number): Observable<HttpResponse<{}>> {
  //   return this.http.delete(`${this.resourceUrlRel}/${id}`, { observe: 'response' });
  // }

  getCriteriaIdentifier(criteria: Pick<ICriteria, 'id'>): number {
    return criteria.id;
  }

  compareCriteria(o1: Pick<ICriteria, 'id'> | null, o2: Pick<ICriteria, 'id'> | null): boolean {
    return o1 && o2 ? this.getCriteriaIdentifier(o1) === this.getCriteriaIdentifier(o2) : o1 === o2;
  }

  addCriteriaToCollectionIfMissing<Type extends Pick<ICriteria, 'id'>>(
    criteriaCollection: Type[],
    ...criteriasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const criterias: Type[] = criteriasToCheck.filter(isPresent);
    if (criterias.length > 0) {
      const criteriaCollectionIdentifiers = criteriaCollection.map(criteriaItem => this.getCriteriaIdentifier(criteriaItem)!);
      const criteriasToAdd = criterias.filter(criteriaItem => {
        const criteriaIdentifier = this.getCriteriaIdentifier(criteriaItem);
        if (criteriaCollectionIdentifiers.includes(criteriaIdentifier)) {
          return false;
        }
        criteriaCollectionIdentifiers.push(criteriaIdentifier);
        return true;
      });
      return [...criteriasToAdd, ...criteriaCollection];
    }
    return criteriaCollection;
  }
}
