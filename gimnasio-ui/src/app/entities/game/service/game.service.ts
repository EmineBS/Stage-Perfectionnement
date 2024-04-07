import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { Pagination } from 'app/core/request/request.model';
import { Observable } from 'rxjs';
import { IGame } from '../game.model';

export type EntityArrayResponseType = HttpResponse<IGame[]>;
export type EntityResponseType = HttpResponse<IGame>;

@Injectable({
  providedIn: 'root'
})
export class GameService {
  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) { }
  private resourceUrl = this.applicationConfigService.getEndpointFor('api/games');
  //games:IGame[]=[];

  create(game: IGame): Observable<EntityResponseType> {
    return this.http.post<IGame>(this.resourceUrl, game, { observe: 'response' });
  }

  query(req?: Pagination): Observable<HttpResponse<IGame[]>> {
    const options = createRequestOption(req);
    return this.http.get<IGame[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IGame>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

}
