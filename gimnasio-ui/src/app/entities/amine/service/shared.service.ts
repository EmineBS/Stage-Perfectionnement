import { Injectable } from '@angular/core';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { IAmine } from '../amine.model';
import { createRequestOption } from 'app/core/request/request-util';
import { Pagination } from 'app/core/request/request.model';
import { Observable } from 'rxjs';

export type EntityArrayResponseType = HttpResponse<IAmine[]>;
export type EntityResponseType = HttpResponse<IAmine>;


@Injectable({
  providedIn: 'root'
})
export class SharedService {
  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) { }
  private resourceUrl = this.applicationConfigService.getEndpointFor('api/amines');

  create(amine: IAmine): Observable<EntityResponseType> {
    return this.http.post<IAmine>(this.resourceUrl, amine, { observe: 'response' });
  }

  query(req?: Pagination): Observable<HttpResponse<IAmine[]>> {
    const options = createRequestOption(req);
    return this.http.get<IAmine[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IAmine>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
