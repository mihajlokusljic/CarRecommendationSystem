import { CarManufacturerService } from './../../services/car-manufacturer.service';
import { CarModelDTO } from './../../../shared/models/CarModelDTO';
import { Component, OnInit } from '@angular/core';
import { CarModelService } from 'src/app/shared/services/car-model.service';
import { Observable } from 'rxjs';
import { startWith, map } from 'rxjs/operators';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { CarManufacturerDTO } from '../../models/CarManufacturerDTO';
import { CarConvenience } from 'src/app/shared/models/CarConvenience';
import { FuelType } from 'src/app/shared/models/FuelType';
import { Transmission } from 'src/app/shared/models/Transmission';
import { CarType } from 'src/app/shared/models/CarType';
import { SnackbarService } from 'src/app/shared/services/snackbar.service';

@Component({
  selector: 'app-add-car-model',
  templateUrl: './add-car-model.component.html',
  styleUrls: ['./add-car-model.component.scss']
})
export class AddCarModelComponent implements OnInit {

  filteredManufacturersList: Observable<any[]>;
  manufacturersList: any;

  conviniences: string[] = [];

  conviniencesBro: string[] = ['Bluetooth connective', 'Onboard navigation system', 'Parking sensors', 'Passanger airbags',
  'Rear parking camera', 'Child seat mounts'];

  selectedConviniences: number[] = [];


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

  constructor(
    private carModelService: CarModelService,
    private carManufacturerService: CarManufacturerService,
    private snackbarService: SnackbarService
  ) { }

  get manufacturer() {
    return this.addModelForm.get('manufacturer');
  }

  get name() {
    return this.addModelForm.get('name');
  }

  get price() {
    return this.addModelForm.get('price');
  }

  get type() {
    return this.addModelForm.get('type');
  }

  get transmission() {
    return this.addModelForm.get('transmission');
  }

  get seatsNum() {
    return this.addModelForm.get('seatsNum');
  }

  get doorsNum() {
    return this.addModelForm.get('doorsNum');
  }

  get bootCapacity() {
    return this.addModelForm.get('bootCapacity');
  }

  get topSpeed() {
    return this.addModelForm.get('topSpeed');
  }

  get fuelType() {
    return this.addModelForm.get('fuelType');
  }

  get engineDisplacement() {
    return this.addModelForm.get('engineDisplacement');
  }

  get enginePower() {
    return this.addModelForm.get('enginePower');
  }

  get mileage() {
    return this.addModelForm.get('mileage');
  }

  get conviniencesControl() {
    return this.addModelForm.get('conviniencesControl');
  }

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

  onConvinienceSelection(conviniences) {
    this.selectedConviniences = conviniences.selectedOptions.selected.map(item => item.value);
  }

  onSubmit() {

    const newCarModel: CarModelDTO = {
      basePriceEuros: this.price.value,
      bluetoothConnective: this.selectedConviniences.includes(CarConvenience.CONNECTIVITY),
      bootCapacityLitres: this.bootCapacity.value,
      doorsNumber: this.doorsNum.value,
      engineDisplacementCcm: this.engineDisplacement.value,
      enginePowerBhp: this.enginePower.value,
      fuelType: FuelType[this.fuelType.value],
      havingNavigationSystem: this.selectedConviniences.includes(CarConvenience.NAVIGATION),
      havingParkingSensors: this.selectedConviniences.includes(CarConvenience.PARKING_SENSORS),
      havingPassangerAirbags: this.selectedConviniences.includes(CarConvenience.AIRBAGS),
      havingRearCamera: this.selectedConviniences.includes(CarConvenience.REAR_CAMERA),
      manufacturerId: this.manufacturer.value.id,
      mileage: this.mileage.value,
      name: this.name.value,
      seatsNumber: this.seatsNum.value,
      supportingChildSeatMounts: this.selectedConviniences.includes(CarConvenience.CHILD_SEAT_MOUNTS),
      topSpeedKmh: this.topSpeed.value,
      transmission: Transmission[this.transmission.value],
      type: CarType[this.type.value]
    };

    console.log(newCarModel);
    this.carModelService.addCarmodel(newCarModel).subscribe(
      (newModel) => {
        this.snackbarService.displayMessage('Car model was successfully added.');
      },

      (error) => {
        this.snackbarService.displayMessage('An error occured while adding car model.');
      }
    );
  }

}
