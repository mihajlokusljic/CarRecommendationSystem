import { Observable } from 'rxjs';
import { environment } from './../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { TrendingConstraintDTO } from '../models/TrendingConstraintDTO';

@Injectable({
  providedIn: 'root'
})
export class TrendingConstraintService {

  private baseUrl = `${environment.baseUrl}/api/trending-constraint`;

  constructor(
    private httpClient: HttpClient
  ) { }

  public getConstraint(): Observable<Array<TrendingConstraintDTO>> {
    return this.httpClient.get<Array<TrendingConstraintDTO>>(this.baseUrl);
  }

  public setConstraint(newConstraint: TrendingConstraintDTO): Observable<TrendingConstraintDTO> {
    return this.httpClient.put<TrendingConstraintDTO>(this.baseUrl, newConstraint);
  }
}
