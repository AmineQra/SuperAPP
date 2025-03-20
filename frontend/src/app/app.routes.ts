import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';
import { RecipesComponent } from './pages/recipes/recipes.component';
import { HomeComponent } from './pages/home/home.component';
import { SearchComponent } from './pages/search/search.component';
import { PlanningComponent } from './pages/planning/planning.component';

export const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  {
    path: 'home',
    component: HomeComponent,
    data: { pageTitle: 'Home' },
    title: 'Home Page - Dishu',
  },
  {
    path: 'search',
    component: SearchComponent,
    data: { pageTitle: 'Search' },
    title: 'Search Page - Dishu',
  },
  {
    path: 'recipes',
    component: RecipesComponent,
    data: { pageTitle: 'Recipes' },
    title: 'Recipes Page - Dishu',
    runGuardsAndResolvers: 'always',
  },
  {
    path: 'planning',
    component: PlanningComponent,
    data: { pageTitle: 'Planning' },
    title: 'Planning Page - Dishu',
  },
  { path: '**', redirectTo: '' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
