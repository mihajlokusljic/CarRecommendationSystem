import { ParamMap } from '@angular/router';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { CarManufacturerDTO } from './../models/CarManufacturerDTO';
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { CarManufacturersPage } from '../models/CarManufacturersPage';

@Injectable({
  providedIn: 'root'
})
export class CarManufacturerService {
  private baseUrl = `${environment.baseUrl}/api/car-manufacturers`;

  constructor(
    private httpClient: HttpClient
  ) { }

  public addCarManufacturer(newManufacturer: CarManufacturerDTO): Observable<CarManufacturerDTO> {
    return this.httpClient.post<CarManufacturerDTO>(this.baseUrl, newManufacturer);
  }

  public getManufacturerById(id: number): Observable<CarManufacturerDTO> {
    return this.httpClient.get<CarManufacturerDTO>(`${this.baseUrl}/${id}`);
  }

  public getManufacturersByPage(pageIndex: number, pageSize: number): Observable<CarManufacturersPage> {
    let params = new HttpParams();
    params = params.set('page', pageIndex.toString());
    params = params.set('size', pageSize.toString());
    return this.httpClient.get<CarManufacturersPage>(this.baseUrl, { params });
  }

  public getAllManufacturers(): Observable<Array<CarManufacturerDTO>> {
    return this.httpClient.get<Array<CarManufacturerDTO>>(`${this.baseUrl}/all`);
  }

  public editCarManufacturer(newData: CarManufacturerDTO): Observable<CarManufacturerDTO> {
    return this.httpClient.put<CarManufacturerDTO>(this.baseUrl, newData);
  }

  public deleteCarManufacturer(id: number) {
    return this.httpClient.delete(`${this.baseUrl}/${id}`);
  }
}
