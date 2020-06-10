import { RecommendationQueryDTO } from './../../models/RecommendationQueryDTO';
import { Component, OnInit, EventEmitter, Output } from '@angular/core';
import { Observable } from 'rxjs';
import { startWith, map } from 'rxjs/operators';
import { countriesList } from '../../models/Countries';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { RecommendationService } from '../../services/recommendation.service';
import { RecommendationDTO } from '../../models/RecommendationDTO';

@Component({
  selector: 'app-recommendation-form',
  templateUrl: './recommendation-form.component.html',
  styleUrls: ['./recommendation-form.component.scss']
})
export class RecommendationFormComponent implements OnInit {
  countriesList = countriesList;
  filteredCountriesList: Observable<any[]>;
  @Output() searchRecommendations = new EventEmitter<RecommendationQueryDTO>();

  recommendationForm = new FormGroup({
    budget: new FormControl('', [Validators.required, Validators.min(1)]),
    country: new FormControl('', [Validators.required]),
    beginner: new FormControl(false),
    travel: new FormControl(false),
    family: new FormControl(false),
    connectivity: new FormControl(false),
    usage: new FormControl('', [Validators.required])
  });

  constructor() { }

  get budget() {
    return this.recommendationForm.get('budget');
  }

  get country() {
    return this.recommendationForm.get('country');
  }

  get beginner() {
    return this.recommendationForm.get('beginner');
  }

  get travel() {
    return this.recommendationForm.get('travel');
  }

  get family() {
    return this.recommendationForm.get('family');
  }

  get connectivity() {
    return this.recommendationForm.get('connectivity');
  }

  get usage() {
    return this.recommendationForm.get('usage');
  }

  ngOnInit() {
    this.filteredCountriesList = this.country.valueChanges
      .pipe(
        startWith(''),
        map(value => typeof value === 'string' ? value : value.name),
        map(name => name ? this._filter(name) : this.countriesList.slice())
      );
  }

  private _filter(value: string): any[] {
    const filterValue = value.toLowerCase();

    return this.countriesList.filter(country => country.name.toLowerCase().includes(filterValue));
  }

  displayCountry(country: any) {
    if (!country) {
      return '';
    }

    return `${country.name} (${country.code})`;
  }

  onSubmit(value: any) {
    const query: RecommendationQueryDTO = {
      beginner: this.beginner.value,
      budget: this.budget.value,
      forCargoTransport: this.usage.value === 'cargo',
      forCityTraffic: this.usage.value === 'city',
      forOffroading: this.usage.value === 'offroad',
      forSport: this.usage.value === 'sport',
      forTravelling: this.travel.value,
      hasFamily: this.family.value,
      needsConnectivity: this.connectivity.value,
      userCountryId: this.country.value.id
    };

    this.searchRecommendations.emit(query);
  }

}
