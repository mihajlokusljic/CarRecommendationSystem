import { TrendingModelsComponent } from './components/trending-models/trending-models.component';
import { LoginComponent } from './components/login/login.component';
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
    path: 'admin/login',
    component: LoginComponent
  },
  {
    path: 'trendingModels',
    component: TrendingModelsComponent
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
