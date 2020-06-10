import { RecommendationDTO } from './../models/RecommendationDTO';
import { RecommendationQueryDTO } from './../models/RecommendationQueryDTO';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from './../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class RecommendationService {
  private recommendationsUrl = `${environment.baseUrl}/api/recommendations`;

  constructor(private http: HttpClient) { }

  public getRecommendations(query: RecommendationQueryDTO): Observable<Array<RecommendationDTO>> {
    return this.http.post<Array<RecommendationDTO>>(this.recommendationsUrl, query);
  }
}
