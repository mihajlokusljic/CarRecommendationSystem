import { Component, OnInit } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { CarManufacturerService } from '../../services/car-manufacturer.service';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import { SnackbarService } from 'src/app/shared/services/snackbar.service';
import { startWith, map } from 'rxjs/operators';
import { CarManufacturerDTO } from '../../models/CarManufacturerDTO';

@Component({
  selector: 'app-edit-car-manufacturer',
  templateUrl: './edit-car-manufacturer.component.html',
  styleUrls: ['./edit-car-manufacturer.component.scss']
})
export class EditCarManufacturerComponent implements OnInit {

  countriesList = environment.countriesList;
  filteredCountriesList: Observable<any[]>;
  manufacturerId: number;

  editManufacturerForm = new FormGroup({
    name: new FormControl('', Validators.required),
    country: new FormControl('', Validators.required)
  });

  constructor(
    private activatedRoute: ActivatedRoute,
    private carManufacturerService: CarManufacturerService,
    private router: Router,
    private snackbarService: SnackbarService
  ) { }

  get name() {
    return this.editManufacturerForm.get('name');
  }

  get country() {
    return this.editManufacturerForm.get('country');
  }

  ngOnInit() {
    this.filteredCountriesList = this.country.valueChanges
      .pipe(
        startWith(''),
        map(value => typeof value === 'string' ? value : value.name),
        map(name => name ? this._filter(name) : this.countriesList.slice())
      );

    this.activatedRoute.paramMap.subscribe(
        (params: ParamMap) => {
          const idStr = params.get('id');
          const id = parseInt(idStr, 10);
          if (!isNaN(id)) {
            this.manufacturerId = id;
            this.loadManufacturer(id);
          } else {
            this.snackbarService.displayMessage('Invalid car manufacturer.');
            this.router.navigate(['/carManufacturers']);
          }
        }
      );
  }

  loadManufacturer(id: number) {
    this.carManufacturerService.getManufacturerById(id).subscribe(
      (result: CarManufacturerDTO) => {
        this.name.setValue(result.name);
        this.country.setValue(this.getCountryById(result.countryId));
      }
    );

  }

  private getCountryById(id: number) {
    for (let country of this.countriesList) {
      if (country.id === id) {
        return country;
      }
    }
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

  onCancel() {
    this.router.navigate(['/admin/carManufacturers']);
  }

  onSubmit() {
    const newManufacturerData: CarManufacturerDTO = {
      id: this.manufacturerId,
      name: this.name.value,
      countryId: this.country.value.id
    };

    this.carManufacturerService.editCarManufacturer(newManufacturerData).subscribe(
      (result: CarManufacturerDTO) => {
        this.snackbarService.displayMessage('Car manufacturer was successfully edited.');
        this.router.navigate(['/admin/carManufacturers']);
      },
      (error: any) => {
        this.snackbarService.displayMessage('Ad error occured while editing car manufacturer. Please check your connection or try again later.');
      }
    );
  }

}
