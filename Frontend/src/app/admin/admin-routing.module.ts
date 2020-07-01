import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CarManufacturersComponent } from './components/car-manufacturers/car-manufacturers.component';
import { AddCarManufacturerComponent } from './components/add-car-manufacturer/add-car-manufacturer.component';
import { CarModelsComponent } from './components/car-models/car-models.component';
import { AddCarModelComponent } from './components/add-car-model/add-car-model.component';
import { TrendingConstraintConfigComponent } from './components/trending-constraint-config/trending-constraint-config.component';


const routes: Routes = [
  {
    path: 'admin/carManufacturers',
    component: CarManufacturersComponent
  },
  {
    path: 'admin/addManufacturer',
    component: AddCarManufacturerComponent
  },
  {
    path: 'admin/carModels',
    component: CarModelsComponent
  },
  {
    path: 'admin/addModel',
    component: AddCarModelComponent
  },
  {
    path: 'admin/trendingConstraint',
    component: TrendingConstraintConfigComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }
