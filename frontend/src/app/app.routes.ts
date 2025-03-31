import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';
import { RecipesComponent } from './pages/recipes/recipes.component';
import { HomeComponent } from './pages/home/home.component';
import { SearchComponent } from './pages/search/search.component';
import { PlanningComponent } from './pages/planning/planning.component';
import { LoginComponent } from './pages/auth/login/login.component';
import { RegisterComponent } from './pages/auth/register/register.component';

export const routes: Routes = [
  {
    path: '',
    component: LoginComponent,
    data: { pageTitle: 'Login' },
    title: 'Login Page - Dishu',
  },
  {
    path: 'register',
    component: RegisterComponent,
    data: { pageTitle: 'Login' },
    title: 'Register Page - Dishu',
  },

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
