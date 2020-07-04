import { SnackbarService } from './../../../shared/services/snackbar.service';
import { Router } from '@angular/router';
import { CarManufacturersPage } from './../../models/CarManufacturersPage';
import { CarManufacturerService } from './../../services/car-manufacturer.service';
import { CarManufacturerDTO } from './../../models/CarManufacturerDTO';
import { Component, OnInit } from '@angular/core';
import { PagingInfo } from 'src/app/shared/models/pagingInfo';

@Component({
  selector: 'app-car-manufacturers',
  templateUrl: './car-manufacturers.component.html',
  styleUrls: ['./car-manufacturers.component.scss']
})
export class CarManufacturersComponent implements OnInit {

  carManufacturers: Array<CarManufacturerDTO> = [];
  private pagingInfo: PagingInfo = { selectedPageIndex: 0, selectedPageSize: 10 };
  displayedColumns: string[] = ['name', 'countryName', 'actions'];
  loadingData = false;
  dataFound = false;


  constructor(
    private carManufacturerService: CarManufacturerService,
    private router: Router,
    private snackbarService: SnackbarService
  ) { }

  ngOnInit() {
    this.fetchCarManufacturers();
  }

  fetchCarManufacturers() {
    this.loadingData = true;
    this.carManufacturerService.getManufacturersByPage(this.pagingInfo.selectedPageIndex, this.pagingInfo.selectedPageSize).subscribe(
      (result: CarManufacturersPage) => {
        this.carManufacturers = result.content;
        this.pagingInfo.totalPages = result.totalPages;
        this.pagingInfo.totalItemsFound = result.totalElements;
        this.dataFound = this.carManufacturers.length > 0;
        this.loadingData = false;
      }
    );
  }

  onEditManufacturer(manufacturerId: number) {
    this.router.navigate([`admin/editManufacturer/${manufacturerId}`]);
  }

  onDeleteManufacturer(manufacturerId: number) {
    this.carManufacturerService.deleteCarManufacturer(manufacturerId).subscribe(
      (success: any) => {
        this.snackbarService.displayMessage('Car manufacturer was successfully deleted.');
        this.fetchCarManufacturers();
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
    this.fetchCarManufacturers();
  }

}
