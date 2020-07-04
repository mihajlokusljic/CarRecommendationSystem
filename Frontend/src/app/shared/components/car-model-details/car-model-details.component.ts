import { CarConvenience } from './../../models/CarConvenience';
import { SnackbarService } from './../../services/snackbar.service';
import { CarModelService } from 'src/app/shared/services/car-model.service';
import { Component, OnInit, Input } from '@angular/core';
import { CarModelDTO } from '../../models/CarModelDTO';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { UserRole } from '../../models/UserRole';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-car-model-details',
  templateUrl: './car-model-details.component.html',
  styleUrls: ['./car-model-details.component.scss']
})
export class CarModelDetailsComponent implements OnInit {

  @Input() carModelId: number;
  @Input() edit = false;
  carModel: CarModelDTO;
  private currentUserRole = UserRole.UNREGISTERED;
  conviniencesBro: string[] = ['Bluetooth connective', 'Onboard navigation system', 'Parking sensors', 'Passanger airbags',
  'Rear parking camera', 'Child seat mounts'];

  constructor(
    private activatedRoute: ActivatedRoute,
    private authService: AuthService,
    private carModelService: CarModelService,
    private snackbarService: SnackbarService
  ) { }

  ngOnInit() {
    this.authService.currentUserRole.subscribe(
      (role: UserRole) => {
        this.currentUserRole = role;
      }
    );

    this.loadCarModel(this.carModelId);
  }

  private loadCarModel(id: number) {
    this.carModelService.getCarModelDetails(id).subscribe(
      (result: CarModelDTO) => {
        this.carModel = result;
      },
      (error: any) => {
        this.snackbarService.displayMessage('An error occured while loading car model.');
      }
    );
  }

  isAdmin() {
    return this.currentUserRole === UserRole.ADMINISTRATOR;
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

}
