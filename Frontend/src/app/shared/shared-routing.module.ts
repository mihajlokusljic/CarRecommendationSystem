import { RecommendationsComponent } from './components/recommendations/recommendations.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { RecommendationFormComponent } from './components/recommendation-form/recommendation-form.component';


const routes: Routes = [
  {
    path: 'recommendations',
    component: RecommendationsComponent
  },
  {
    path: '',
    redirectTo: 'recommendations',
    pathMatch: 'full'
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SharedRoutingModule { }
