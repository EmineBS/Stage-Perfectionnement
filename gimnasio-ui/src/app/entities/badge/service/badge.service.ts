import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IBadge, IPackBadge, NewBadge, NewPackBadge, NewSaveProgress } from '../badge.model';

export type PartialUpdateBadge = Partial<IBadge> & Pick<IBadge, 'id'>;

export type EntityResponseType = HttpResponse<IBadge>;
export type EntityArrayResponseType = HttpResponse<IBadge[]>;

export type PartialUpdateIPackBadge = Partial<IPackBadge> & Pick<IPackBadge, 'id'>;
export type EntityResponseTypes = HttpResponse<IPackBadge>;
export type EntityArrayResponseTypes = HttpResponse<IPackBadge[]>;

@Injectable({ providedIn: 'root' })
export class BadgeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/badges');
  protected resourceUrlByAccount = this.applicationConfigService.getEndpointFor('api/badges/account');
  protected resourceUrlRel = this.applicationConfigService.getEndpointFor('/api/rel_badge_pack');
  protected resourceProgress = this.applicationConfigService.getEndpointFor('/api/progresss');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(badge: NewBadge): Observable<EntityResponseType> {
    return this.http.post<IBadge>(this.resourceUrl, badge, { observe: 'response' });
  }

  createRel(badge: NewPackBadge): Observable<EntityResponseTypes> {
    return this.http.post<IPackBadge>(this.resourceUrlRel, badge, { observe: 'response' });
  }

  createProgress(progress: NewSaveProgress): Observable<EntityResponseTypes> {
    return this.http.post<IPackBadge>(this.resourceProgress, progress, { observe: 'response' });
  }

  update(badge: IBadge): Observable<EntityResponseType> {
    return this.http.put<IBadge>(`${this.resourceUrl}/${this.getBadgeIdentifier(badge)}`, badge, { observe: 'response' });
  }

  partialUpdate(badge: PartialUpdateBadge): Observable<EntityResponseType> {
    return this.http.patch<IBadge>(`${this.resourceUrl}/${this.getBadgeIdentifier(badge)}`, badge, { observe: 'response' });
  }

  partialUpdateRel(IPackBadge: PartialUpdateIPackBadge): Observable<EntityResponseTypes> {
    return this.http.patch<IPackBadge>(`${this.resourceUrlRel}/${this.getBadgePackIdentifier(IPackBadge)}`, IPackBadge, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBadge>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBadge[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  querySingle(req?: any): Observable<EntityResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBadge>(this.resourceUrl, { params: options, observe: 'response' });
  }

  querySingleAccount(req?: any): Observable<EntityResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBadge>(this.resourceUrlByAccount, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getBadgeIdentifier(badge: Pick<IBadge, 'id'>): number {
    return badge.id;
  }

  getBadgePackIdentifier(badge: Pick<IPackBadge, 'id'>): number {
    return badge.id;
  }

  compareBadge(o1: Pick<IBadge, 'id'> | null, o2: Pick<IBadge, 'id'> | null): boolean {
    return o1 && o2 ? this.getBadgeIdentifier(o1) === this.getBadgeIdentifier(o2) : o1 === o2;
  }

  findBadgefromGym(id: number, req?: any) {
    const options = createRequestOption(req);
    return this.http.get<IBadge[]>(`/api/badges-full/gym/${id}`, { params: options, observe: 'response' });
  }

  addBadgeToCollectionIfMissing<Type extends Pick<IBadge, 'id'>>(
    badgeCollection: Type[],
    ...badgesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const badges: Type[] = badgesToCheck.filter(isPresent);
    if (badges.length > 0) {
      const badgeCollectionIdentifiers = badgeCollection.map(badgeItem => this.getBadgeIdentifier(badgeItem)!);
      const badgesToAdd = badges.filter(badgeItem => {
        const badgeIdentifier = this.getBadgeIdentifier(badgeItem);
        if (badgeCollectionIdentifiers.includes(badgeIdentifier)) {
          return false;
        }
        badgeCollectionIdentifiers.push(badgeIdentifier);
        return true;
      });
      return [...badgesToAdd, ...badgeCollection];
    }
    return badgeCollection;
  }
}
