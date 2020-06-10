import { CarManufacturerService } from './../../services/car-manufacturer.service';
import { CarModelDTO } from './../../../shared/models/CarModelDTO';
import { Component, OnInit } from '@angular/core';
import { CarModelService } from 'src/app/shared/services/car-model.service';
import { Observable } from 'rxjs';
import { startWith, map } from 'rxjs/operators';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { CarManufacturerDTO } from '../../models/CarManufacturerDTO';

@Component({
  selector: 'app-add-car-model',
  templateUrl: './add-car-model.component.html',
  styleUrls: ['./add-car-model.component.scss']
})
export class AddCarModelComponent implements OnInit {

  filteredManufacturersList: Observable<any[]>;
  manufacturersList: any;

  conviniences: string[] = [];

  conviniencesBro: string[] = ['Bluetooth connective', 'Onboard navigatio system', 'Parking sensors', 'Passanger airbags',
  'Rear parking camera', 'Child seat mounts'];


  addModelForm = new FormGroup({
    manufacturer: new FormControl('', [Validators.required]),
    name: new FormControl('', [Validators.required]),
    price: new FormControl('', [Validators.required, Validators.min(1)]),
    type: new FormControl('', [Validators.required]),
    transmission: new FormControl('', [Validators.required]),
    doorsNum: new FormControl('', [Validators.required, Validators.min(1)]),
    seatsNum: new FormControl('', [Validators.required, Validators.min(1)]),
    bootCapacity: new FormControl('', [Validators.required, Validators.min(1)]),
    topSpeed: new FormControl('', [Validators.required, Validators.min(1)]),
    fuelType: new FormControl('', [Validators.required]),
    engineDisplacement: new FormControl('', [Validators.required, Validators.min(1)]),
    enginePower: new FormControl('', [Validators.required, Validators.min(1)]),
    mileage: new FormControl('', [Validators.required, Validators.min(0)]),
    conviniencesControl: new FormControl(''),
  });

  get manufacturer() {
    return this.addModelForm.get('manufacturer');
  }

  constructor(
    private carModelService: CarModelService,
    private carManufacturerService: CarManufacturerService
  ) { }

  ngOnInit() {
    this.carManufacturerService.getAllManufacturers().subscribe(
      (manufacturers: Array<CarManufacturerDTO>) => {
        this.manufacturersList = manufacturers;
        this.filteredManufacturersList = this.manufacturer.valueChanges.pipe(startWith(''), map(value => this._filter(value)));
      }
    );
  }

  private _filter(value: string): any[] {
    const filterValue = value.toLowerCase();
    return this.manufacturersList.filter(manufacturer => manufacturer.name.toLowerCase().includes(filterValue));
  }

  displayFn(manufacturer: CarManufacturerDTO): string {
    if (!manufacturer) {
      return '';
    }

    return manufacturer.name;
  }

  onSubmit() {
    /*
    newCarModel: CarModelDTO = {

    }
    */
  }

}
