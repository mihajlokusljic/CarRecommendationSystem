import { Component, OnInit, Input } from '@angular/core';
import { RecommendationDTO } from '../../models/RecommendationDTO';

@Component({
  selector: 'app-recommendations-list',
  templateUrl: './recommendations-list.component.html',
  styleUrls: ['./recommendations-list.component.scss']
})
export class RecommendationsListComponent implements OnInit {

  @Input() recommendations: Array<RecommendationDTO>;
  activeRecommendationIndex = 0;
  activeRecommendation: RecommendationDTO;

  constructor() { }

  ngOnInit() {
    if (this.recommendations.length > 0) {
      this.activeRecommendation = this.recommendations[0];
    }
  }

  onPrevoius() {
    this.activeRecommendationIndex -= 1;
    this.activeRecommendation = this.recommendations[this.activeRecommendationIndex];
  }

  onNext() {
    this.activeRecommendationIndex += 1;
    this.activeRecommendation = this.recommendations[this.activeRecommendationIndex];
  }

}
