import { Router } from '@angular/router';
import { SnackbarService } from './../../../shared/services/snackbar.service';
import { TrendingConstraintService } from './../../services/trending-constraint.service';
import { TrendingConstraintDTO } from './../../models/TrendingConstraintDTO';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-trending-constraint-config',
  templateUrl: './trending-constraint-config.component.html',
  styleUrls: ['./trending-constraint-config.component.scss']
})
export class TrendingConstraintConfigComponent implements OnInit {

  currentConstraint: TrendingConstraintDTO;
  constraintIsSet = false;
  processing = false;

  trendingConstraintForm = new FormGroup({
    daysCount: new FormControl('', [Validators.required, Validators.min(1), Validators.max(31)]),
    minRecommendations: new FormControl('', [Validators.required, Validators.min(1)]),
  });

  constructor(
    private trendingConstraintService: TrendingConstraintService,
    private snackbarService: SnackbarService,
    private router: Router
  ) { }

  ngOnInit() {
    this.trendingConstraintService.getConstraint().subscribe(
      (result: Array<TrendingConstraintDTO>) => {
        if (result.length > 0) {
          this.currentConstraint = result[0];
          this.constraintIsSet = true;
        } else {
          this.constraintIsSet = false;
        }
      },
      (error: any) => {
        this.snackbarService.displayMessage('An error occured while loading current trending constraint.');
      }
    );
  }

  get daysCount() {
    return this.trendingConstraintForm.get('daysCount');
  }

  get minRecommendations() {
    return this.trendingConstraintForm.get('minRecommendations');
  }

  onSubmit() {
    const newConstraint: TrendingConstraintDTO = {
      minimumRecommendations: this.minRecommendations.value,
      previousDaysCount: this.daysCount.value
    };

    this.processing = true;
    this.trendingConstraintService.setConstraint(newConstraint).subscribe(
      (result: TrendingConstraintDTO) => {
        this.router.navigate(['/trendingModels']);
        this.snackbarService.displayMessage('New trending requirenments have been set successfully.');
      },
      (error: any) => {
        this.snackbarService.displayMessage('An error occured while processing new trending requirements.');
        this.processing = false;
      }
    );
  }

}
