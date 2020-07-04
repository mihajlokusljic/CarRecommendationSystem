import { Router } from '@angular/router';
import { SnackbarService } from './../../../shared/services/snackbar.service';
import { CarModelPage } from './../../../shared/models/CarModelPage';
import { CarModelService } from 'src/app/shared/services/car-model.service';
import { PagingInfo } from './../../../shared/models/pagingInfo';
import { Component, OnInit } from '@angular/core';
import { CarModelDTO } from 'src/app/shared/models/CarModelDTO';

@Component({
  selector: 'app-car-models',
  templateUrl: './car-models.component.html',
  styleUrls: ['./car-models.component.scss']
})
export class CarModelsComponent implements OnInit {

  carModels: Array<CarModelDTO> = [];
  loadingData = false;
  dataFound = false;
  displayedColumns: string[] = ['manufacturerName', 'name', 'type', 'basePriceEuros', 'actions'];
  pagingInfo: PagingInfo = {
    selectedPageIndex: 0,
    selectedPageSize: 10
  };

  constructor(
    private carModelService: CarModelService,
    private router: Router,
    private snackbarservice: SnackbarService
  ) { }

  ngOnInit() {
    this.fetchCarModels();
  }

  private fetchCarModels() {
    this.loadingData = true;
    this.carModelService.getCarModelsByPage(this.pagingInfo.selectedPageIndex, this.pagingInfo.selectedPageSize).subscribe(
      (result: CarModelPage) => {
        this.carModels = result.content;
        this.pagingInfo.totalPages = result.totalPages;
        this.pagingInfo.totalItemsFound = result.totalElements;
        this.dataFound = this.carModels.length > 0;
        this.loadingData = false;
      },
      (error: any) => {
        this.snackbarservice.displayMessage('An error occured while loading car models. Please check your connection or try again later.');
        this.loadingData = false;
        this.dataFound = false;
      }
    );
  }

  onPagingChanged(pagination) {
    if (this.pagingInfo.selectedPageSize !== pagination.pageSize) {
      this.pagingInfo.selectedPageIndex = 0;
    } else {
      this.pagingInfo.selectedPageIndex = pagination.pageIndex;
    }
    this.pagingInfo.selectedPageSize = pagination.pageSize;
    this.fetchCarModels();
  }

  onEditCarModel(carModelId: number) {
    this.router.navigate([`admin/editModel/${carModelId}`]);
  }

  onDeleteCarModel(carModelId: number) {
    this.carModelService.deleteCarModel(carModelId).subscribe(
      (success: any) => {
        this.snackbarservice.displayMessage('Car model has been deleted successfully.');
        this.fetchCarModels();
      },
      (error: any) => {
        this.snackbarservice.displayMessage('An error occured while deleting car model. Please check your connection or try again later.');
      }
    );
  }

}
