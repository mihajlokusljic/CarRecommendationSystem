import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
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
}
