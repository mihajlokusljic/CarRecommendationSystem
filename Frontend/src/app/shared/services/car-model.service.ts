import { CarModelPage } from './../models/CarModelPage';
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient, HttpParams } from '@angular/common/http';
import { CarModelDTO } from '../models/CarModelDTO';

@Injectable({
  providedIn: 'root'
})
export class CarModelService {

  private baseUrl = `${environment.baseUrl}/api/car-models`;

  constructor(
    private httpClient: HttpClient
  ) { }

  public addCarmodel(newCarModel: CarModelDTO): Observable<CarModelDTO> {
    return this.httpClient.post<CarModelDTO>(this.baseUrl, newCarModel);
  }

  public getCarModelsByPage(pageIndex: number, pageSize: number): Observable<CarModelPage> {
    let params = new HttpParams();
    params = params.set('page', pageIndex.toString());
    params = params.set('size', pageSize.toString());
    return this.httpClient.get<CarModelPage>(this.baseUrl, { params });
  }

  public getCarModelDetails(id: number): Observable<CarModelDTO> {
    return this.httpClient.get<CarModelDTO>(`${this.baseUrl}/${id}`);
  }

  public getTrendingmodels(page: number, size: number): Observable<CarModelPage> {
    let params = new HttpParams();
    params = params.set('page', page.toString());
    params = params.set('size', size.toString());
    return this.httpClient.get<CarModelPage>(`${this.baseUrl}/trending`, { params });
  }

  public editCarModel(newModelData: CarModelDTO): Observable<CarModelDTO> {
    return this.httpClient.put<CarModelDTO>(this.baseUrl, newModelData);
  }

  public deleteCarModel(id: number) {
    return this.httpClient.delete(`${this.baseUrl}/${id}`);
  }
}
