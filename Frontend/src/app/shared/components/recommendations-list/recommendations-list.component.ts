import { Component, OnInit, Input } from '@angular/core';
import { RecommendationDTO } from '../../models/RecommendationDTO';

@Component({
  selector: 'app-recommendations-list',
  templateUrl: './recommendations-list.component.html',
  styleUrls: ['./recommendations-list.component.scss']
})
export class RecommendationsListComponent implements OnInit {

  @Input() recommendations: Array<RecommendationDTO>;

  constructor() { }

  ngOnInit() {
  }

}
