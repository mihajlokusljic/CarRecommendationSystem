import { Router } from '@angular/router';
import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { RecommendationDTO } from '../../models/RecommendationDTO';

@Component({
  selector: 'app-recommendations-list',
  templateUrl: './recommendations-list.component.html',
  styleUrls: ['./recommendations-list.component.scss']
})
export class RecommendationsListComponent implements OnInit {

  @Input() recommendations: Array<RecommendationDTO>;
  @Output() backToSearchEvent = new EventEmitter<boolean>();
  activeRecommendationIndex = 0;
  activeRecommendation: RecommendationDTO;
  showingDetails = false;

  constructor(
    private router: Router
  ) { }

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

  onDetailsClicked(carModelId: number) {
    // this.router.navigate([`carModel/${carModelId}`]);
    this.showingDetails = true;
  }

  onBackToRecommendationsClicked() {
    this.showingDetails = false;
  }

  onReturn() {
    this.backToSearchEvent.emit(true);
  }

}
