import { UserService } from './../../services/user.service';
import { Router } from '@angular/router';
import { CarModelService } from 'src/app/shared/services/car-model.service';
import { CarModelPage } from './../../models/CarModelPage';
import { Component, OnInit } from '@angular/core';
import { CarModelDTO } from '../../models/CarModelDTO';
import { UserRole } from '../../models/UserRole';
import { AuthService } from '../../services/auth.service';
import { PagingInfo } from '../../models/pagingInfo';

@Component({
  selector: 'app-trending-models',
  templateUrl: './trending-models.component.html',
  styleUrls: ['./trending-models.component.scss']
})
export class TrendingModelsComponent implements OnInit {

  carModels: Array<CarModelDTO> = [];
  private pagingInfo: PagingInfo = { selectedPageIndex: 0, selectedPageSize: 5 };
  private currentUserRole = UserRole.UNREGISTERED;

  constructor(
    private carModelService: CarModelService,
    private router: Router,
    private authService: AuthService
  ) { }

  ngOnInit() {
    this.authService.currentUserRole.subscribe(
      (role: UserRole) => {
        this.currentUserRole = role;
      }
    );

    this.fetchTrendingModels();
  }

  fetchTrendingModels() {
    this.carModelService.getTrendingmodels(this.pagingInfo.selectedPageIndex, this.pagingInfo.selectedPageSize).subscribe(
      (result: CarModelPage) => {
        this.carModels = result.content;
        this.pagingInfo.totalPages = result.totalPages;
        this.pagingInfo.totalItemsFound = result.totalElements;
      }
    );
  }

  onChangeTrending() {
    this.router.navigate(['/admin/trendingConstraint']);
  }

  onPagingInfoChanged(newPagingInfo: PagingInfo) {
    this.pagingInfo = newPagingInfo;
    // this.router.navigate(['/events/searchResults'], { queryParams: this.generateQueryParams() });
    this.fetchTrendingModels();
  }

  isAdmin() {
    return this.currentUserRole === UserRole.ADMINISTRATOR;
  }

}
