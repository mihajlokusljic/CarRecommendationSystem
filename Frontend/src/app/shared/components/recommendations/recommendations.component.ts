import { RecommendationQueryDTO } from './../../models/RecommendationQueryDTO';
import { Component, OnInit } from '@angular/core';
import { RecommendationService } from '../../services/recommendation.service';
import { RecommendationDTO } from '../../models/RecommendationDTO';

@Component({
  selector: 'app-recommendations',
  templateUrl: './recommendations.component.html',
  styleUrls: ['./recommendations.component.scss']
})
export class RecommendationsComponent implements OnInit {
  recommendations: Array<RecommendationDTO> = [];
  querying = true;
  loading = false;

  constructor(
    private recommendationService: RecommendationService
  ) { }

  ngOnInit() {
  }

  onSearchRecommendations(query: RecommendationQueryDTO) {
    this.querying = false;
    this.loading = true;
    this.recommendationService.getRecommendations(query).subscribe(
      (result: Array<RecommendationDTO>) => {
        this.recommendations = result;
        this.loading = false;
      }
    );
  }

}
