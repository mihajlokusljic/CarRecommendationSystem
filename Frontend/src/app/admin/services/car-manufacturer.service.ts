import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { CarManufacturerDTO } from './../models/CarManufacturerDTO';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

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

  public getAllManufacturers(): Observable<Array<CarManufacturerDTO>> {
    return this.httpClient.get<Array<CarManufacturerDTO>>(`${this.baseUrl}/all`);
  }
}
