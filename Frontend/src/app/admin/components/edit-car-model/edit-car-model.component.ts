import { FuelType } from './../../../shared/models/FuelType';
import { Transmission } from './../../../shared/models/Transmission';
import { CarType } from './../../../shared/models/CarType';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { CarModelService } from 'src/app/shared/services/car-model.service';
import { CarManufacturerService } from '../../services/car-manufacturer.service';
import { SnackbarService } from 'src/app/shared/services/snackbar.service';
import { CarModelDTO } from 'src/app/shared/models/CarModelDTO';
import { CarConvenience } from 'src/app/shared/models/CarConvenience';

@Component({
  selector: 'app-edit-car-model',
  templateUrl: './edit-car-model.component.html',
  styleUrls: ['./edit-car-model.component.scss']
})
export class EditCarModelComponent implements OnInit {

  carModelId: number;
  carModel: CarModelDTO;
  carTypeIndex: string;
  transmissionTypeIndex: string;
  fuelTypeIndex: string;
  manufacturerId: number;
  conviniencesBro: string[] = ['Bluetooth connective', 'Onboard navigation system', 'Parking sensors', 'Passanger airbags',
  'Rear parking camera', 'Child seat mounts'];

  selectedConviniences: number[] = [];
  carTypes = ['COUPE', 'HATCHBACK', 'MICRO', 'MINIVAN', 'PICKUP', 'SEDAN', 'SUV', 'WAGON'];
  transmissionTypes = ['AUTOMATIC', 'MANUAL'];
  fuelTypes = ['DIESEL', 'ELECTRIC', 'HIBRID', 'PETROL'];


  editModelForm = new FormGroup({
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
    private activatedRoute: ActivatedRoute,
    private carModelService: CarModelService,
    private carManufacturerService: CarManufacturerService,
    private router: Router,
    private snackbarService: SnackbarService
  ) { }

  get manufacturer() {
    return this.editModelForm.get('manufacturer');
  }

  get name() {
    return this.editModelForm.get('name');
  }

  get price() {
    return this.editModelForm.get('price');
  }

  get type() {
    return this.editModelForm.get('type');
  }

  get transmission() {
    return this.editModelForm.get('transmission');
  }

  get seatsNum() {
    return this.editModelForm.get('seatsNum');
  }

  get doorsNum() {
    return this.editModelForm.get('doorsNum');
  }

  get bootCapacity() {
    return this.editModelForm.get('bootCapacity');
  }

  get topSpeed() {
    return this.editModelForm.get('topSpeed');
  }

  get fuelType() {
    return this.editModelForm.get('fuelType');
  }

  get engineDisplacement() {
    return this.editModelForm.get('engineDisplacement');
  }

  get enginePower() {
    return this.editModelForm.get('enginePower');
  }

  get mileage() {
    return this.editModelForm.get('mileage');
  }

  get conviniencesControl() {
    return this.editModelForm.get('conviniencesControl');
  }

  ngOnInit() {
    this.activatedRoute.paramMap.subscribe(
      (params: ParamMap) => {
        const idStr = params.get('id');
        const id = parseInt(idStr, 10);
        if (!isNaN(id)) {
          this.carModelId = id;
          this.loadCarMdoel(id);
        } else {
          this.snackbarService.displayMessage('Invalid car model.');
          this.router.navigate(['/carModels']);
        }
      }
    );
  }

  loadCarMdoel(id: number) {
    this.carModelService.getCarModelDetails(id).subscribe(
      (result: CarModelDTO) => {
        this.carModel = result;
        this.manufacturer.setValue(result.manufacturerName);
        this.name.setValue(result.name);
        this.price.setValue(result.basePriceEuros);
        this.carTypeIndex = this.carTypes.indexOf(result.type).toString();
        this.transmissionTypeIndex = this.transmissionTypes.indexOf(result.transmission).toString();
        this.doorsNum.setValue(result.doorsNumber);
        this.seatsNum.setValue(result.seatsNumber);
        this.bootCapacity.setValue(result.bootCapacityLitres);
        this.topSpeed.setValue(result.topSpeedKmh);
        this.fuelTypeIndex = this.fuelTypes.indexOf(result.fuelType).toString();
        this.engineDisplacement.setValue(result.engineDisplacementCcm);
        this.enginePower.setValue(result.enginePowerBhp);
        this.mileage.setValue(result.mileage);
        this.manufacturerId = result.manufacturerId;

        if (result.havingPassangerAirbags) {
          this.selectedConviniences.push(CarConvenience[CarConvenience[CarConvenience.AIRBAGS]]);
        }
        if (result.supportingChildSeatMounts) {
          this.selectedConviniences.push(CarConvenience[CarConvenience[CarConvenience.CHILD_SEAT_MOUNTS]]);
        }
        if (result.bluetoothConnective) {
          this.selectedConviniences.push(CarConvenience[CarConvenience[CarConvenience.CONNECTIVITY]]);
        }
        if (result.havingNavigationSystem) {
          this.selectedConviniences.push(CarConvenience[CarConvenience[CarConvenience.NAVIGATION]]);
        }
        if (result.havingParkingSensors) {
          this.selectedConviniences.push(CarConvenience[CarConvenience[CarConvenience.PARKING_SENSORS]]);
        }
        if (result.havingRearCamera) {
          this.selectedConviniences.push(CarConvenience[CarConvenience[CarConvenience.REAR_CAMERA]]);
        }
        console.log('Selected conveniences:', this.selectedConviniences);
      },
      (error: any) => {
        this.snackbarService.displayMessage('An error occured while loading car model. Plesase check your connection or try adain later.');
        this.router.navigate(['/carModels']);
      }
    );
  }

  hasConvenience(index: number): boolean {
    const conv: CarConvenience = CarConvenience[CarConvenience[index]];
    if (conv === CarConvenience.AIRBAGS) {
      return this.carModel.havingPassangerAirbags;
    }
    if (conv === CarConvenience.CHILD_SEAT_MOUNTS) {
      return this.carModel.supportingChildSeatMounts;
    }
    if (conv === CarConvenience.CONNECTIVITY) {
      return this.carModel.bluetoothConnective;
    }
    if (conv === CarConvenience.NAVIGATION) {
      return this.carModel.havingNavigationSystem;
    }
    if (conv === CarConvenience.PARKING_SENSORS) {
      return this.carModel.havingParkingSensors;
    }
    if (conv === CarConvenience.REAR_CAMERA) {
      return this.carModel.havingRearCamera;
    }
    return false;
  }

  onConvinienceSelection(conviniences) {
    this.selectedConviniences = conviniences.selectedOptions.selected.map(item => item.value);
  }

  onCancel() {
    this.router.navigate(['/admin/carModels']);
  }

  onSubmit() {

    const newCarModelData: CarModelDTO = {
      id: this.carModelId,
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
      manufacturerId: this.manufacturerId,
      mileage: this.mileage.value,
      name: this.name.value,
      seatsNumber: this.seatsNum.value,
      supportingChildSeatMounts: this.selectedConviniences.includes(CarConvenience.CHILD_SEAT_MOUNTS),
      topSpeedKmh: this.topSpeed.value,
      transmission: Transmission[this.transmission.value],
      type: CarType[this.type.value]
    };

    this.carModelService.editCarModel(newCarModelData).subscribe(
     (result: CarModelDTO) => {
       this.snackbarService.displayMessage('Car model was successfully modified.');
       this.router.navigate(['/admin/carModels']);
     },
     (error: any) => {
       this.snackbarService.displayMessage('An error occured while modifying car model data. Please check your connection or try again later.');
     }
   );
  }

}
