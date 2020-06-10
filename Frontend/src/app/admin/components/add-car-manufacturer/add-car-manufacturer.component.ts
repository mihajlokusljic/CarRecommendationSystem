import { SnackbarService } from './../../../shared/services/snackbar.service';
import { CarManufacturerDTO } from './../../models/CarManufacturerDTO';
import { CarManufacturerService } from './../../services/car-manufacturer.service';
import { environment } from 'src/environments/environment';
import { SharedModule } from './../../../shared/shared.module';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { startWith, map } from 'rxjs/operators';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-car-manufacturer',
  templateUrl: './add-car-manufacturer.component.html',
  styleUrls: ['./add-car-manufacturer.component.scss']
})
export class AddCarManufacturerComponent implements OnInit {
  countriesList = environment.countriesList;
  filteredCountriesList: Observable<any[]>;

  addManufacturerForm = new FormGroup({
    name: new FormControl('', Validators.required),
    country: new FormControl('', Validators.required)
  });

  constructor(
    private carManufacturerService: CarManufacturerService,
    private router: Router,
    private snackbarService: SnackbarService
  ) { }

  get name() {
    return this.addManufacturerForm.get('name');
  }

  get country() {
    return this.addManufacturerForm.get('country');
  }

  ngOnInit() {
    this.filteredCountriesList = this.country.valueChanges
      .pipe(
        startWith(''),
        map(value => typeof value === 'string' ? value : value.name),
        map(name => name ? this._filter(name) : this.countriesList.slice())
      );
  }

  private _filter(value: string): any[] {
    const filterValue = value.toLowerCase();

    return this.countriesList.filter(country => country.name.toLowerCase().includes(filterValue));
  }

  displayCountry(country: any) {
    if (!country) {
      return '';
    }

    return `${country.name} (${country.code})`;
  }

  onSubmit() {
    const newManufacturerData: CarManufacturerDTO = {
      name: this.name.value,
      countryId: this.country.value.id
    };

    this.carManufacturerService.addCarManufacturer(newManufacturerData).subscribe(
      (result: CarManufacturerDTO) => {
        this.snackbarService.displayMessage('Car manufacturer has been added.');
        this.router.navigate(['/admin/carManufacturers']);
      },
      (error: any) => {
        this.snackbarService.displayMessage('Ad error occured while adding new car manufacturer.');
      }
    );
  }

}
