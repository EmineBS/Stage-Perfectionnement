import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { Pagination } from 'app/core/request/request.model';
import { Observable } from 'rxjs';
import { ITour } from '../tournament.model';

export type EntityArrayResponseType = HttpResponse<ITour[]>;
export type EntityResponseType = HttpResponse<ITour>;

@Injectable({
  providedIn: 'root'
})
export class TournamentService {
  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) { }
  private resourceUrl = this.applicationConfigService.getEndpointFor('api/tournaments');

  create(tournament: ITour): Observable<EntityResponseType> {
    return this.http.post<ITour>(this.resourceUrl, tournament, { observe: 'response' });
  }

  query(req?: Pagination): Observable<HttpResponse<ITour[]>> {
    const options = createRequestOption(req);
    return this.http.get<ITour[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<ITour>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
