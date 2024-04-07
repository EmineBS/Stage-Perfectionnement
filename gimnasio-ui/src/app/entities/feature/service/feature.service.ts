import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFeature, NewFeature } from '../feature.model';
import { IBadgeProgress, IProgress, IProgressValue } from 'app/entities/badge/badge.model';

export type PartialUpdateFeature = Partial<IFeature> & Pick<IFeature, 'id'>;

export type EntityResponseType = HttpResponse<IFeature>;
export type EntityArrayResponseType = HttpResponse<IFeature[]>;
export type EntityArrayResponseTypeBadgeProgress = HttpResponse<IBadgeProgress[]>;

export type PartialUpdateFeatures = Partial<IProgress> & Pick<IProgress, 'id'>;

export type EntityResponseTypes = HttpResponse<IProgress>;
export type EntityArrayResponseTypes = HttpResponse<IProgress[]>;

@Injectable({ providedIn: 'root' })
export class FeatureService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/admin/features');
  protected resourceUrlGymUser = this.applicationConfigService.getEndpointFor('api/features');
  protected resourceUrlRel = this.applicationConfigService.getEndpointFor('/api/progresss');
  protected resourceUrlRelFeatureProgress = this.applicationConfigService.getEndpointFor('/api/relFeatureProgresss');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(feature: NewFeature): Observable<EntityResponseType> {
    return this.http.post<IFeature>(this.resourceUrl, feature, { observe: 'response' });
  }

  update(feature: IFeature): Observable<EntityResponseType> {
    return this.http.put<IFeature>(`${this.resourceUrl}/${this.getFeatureIdentifier(feature)}`, feature, { observe: 'response' });
  }

  partialUpdate(feature: PartialUpdateFeature): Observable<EntityResponseType> {
    return this.http.patch<IFeature>(`${this.resourceUrl}/${this.getFeatureIdentifier(feature)}`, feature, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFeature>(`${this.resourceUrlGymUser}/${id}`, { observe: 'response' });
  }

  // findFeaturesPerBadge(id: number, req?: any): Observable<EntityArrayResponseTypes> {
  //   const options = createRequestOption(req);
  //   return this.http.get<IProgress[]>(`${this.resourceUrlRel}/badge/${id}`, { params: options, observe: 'response' });
  // }

  findFeaturesPerGym(id: number, req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFeature[]>(`${this.resourceUrl}/gym/${id}`, { params: options, observe: 'response' });
  }

  findFeaturesPerGymForUserGym(id: number, req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFeature[]>(`${this.resourceUrlGymUser}/gym/${id}`, { params: options, observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFeature[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  queryProgressByBadge(idGym: number, idBadge: number, req?: any): Observable<EntityArrayResponseTypeBadgeProgress> {
    const options = createRequestOption(req);
    return this.http.get<IBadgeProgress[]>(`${this.resourceUrlRelFeatureProgress}/gym/badge/${idGym}/${idBadge}`, { params: options, observe: 'response' });
  }

  queryBySession(req?: any): Observable<EntityArrayResponseTypeBadgeProgress> {
    const options = createRequestOption(req);
    return this.http.get<IBadgeProgress[]>(`${this.resourceUrlRelFeatureProgress}/account`, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  // deleteRel(id: number): Observable<HttpResponse<{}>> {
  //   return this.http.delete(`${this.resourceUrlRel}/${id}`, { observe: 'response' });
  // }

  getFeatureIdentifier(feature: Pick<IFeature, 'id'>): number {
    return feature.id;
  }

  compareFeature(o1: Pick<IFeature, 'id'> | null, o2: Pick<IFeature, 'id'> | null): boolean {
    return o1 && o2 ? this.getFeatureIdentifier(o1) === this.getFeatureIdentifier(o2) : o1 === o2;
  }

  addFeatureToCollectionIfMissing<Type extends Pick<IFeature, 'id'>>(
    featureCollection: Type[],
    ...featuresToCheck: (Type | null | undefined)[]
  ): Type[] {
    const features: Type[] = featuresToCheck.filter(isPresent);
    if (features.length > 0) {
      const featureCollectionIdentifiers = featureCollection.map(featureItem => this.getFeatureIdentifier(featureItem)!);
      const featuresToAdd = features.filter(featureItem => {
        const featureIdentifier = this.getFeatureIdentifier(featureItem);
        if (featureCollectionIdentifiers.includes(featureIdentifier)) {
          return false;
        }
        featureCollectionIdentifiers.push(featureIdentifier);
        return true;
      });
      return [...featuresToAdd, ...featureCollection];
    }
    return featureCollection;
  }
}
