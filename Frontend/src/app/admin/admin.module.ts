import { SharedModule } from './../shared/shared.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AdminRoutingModule } from './admin-routing.module';
import { CarManufacturersComponent } from './components/car-manufacturers/car-manufacturers.component';
import { AddCarManufacturerComponent } from './components/add-car-manufacturer/add-car-manufacturer.component';
import { CarModelsComponent } from './components/car-models/car-models.component';
import { AddCarModelComponent } from './components/add-car-model/add-car-model.component';
import { MatStepperModule } from '@angular/material/stepper';
import { TrendingConstraintConfigComponent } from './components/trending-constraint-config/trending-constraint-config.component';
import { EditCarManufacturerComponent } from './components/edit-car-manufacturer/edit-car-manufacturer.component';
import { EditCarModelComponent } from './components/edit-car-model/edit-car-model.component';


@NgModule({
  declarations: [
    CarManufacturersComponent,
    AddCarManufacturerComponent,
    CarModelsComponent,
    AddCarModelComponent,
    TrendingConstraintConfigComponent,
    EditCarManufacturerComponent,
    EditCarModelComponent
  ],
  imports: [
    CommonModule,
    AdminRoutingModule,
    SharedModule,
    MatStepperModule
  ],
  exports: [
    CarManufacturersComponent
  ]
})
export class AdminModule { }
