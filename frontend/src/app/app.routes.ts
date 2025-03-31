import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';
import { RecipesComponent } from './pages/recipes/recipes.component';
import { HomeComponent } from './pages/home/home.component';
import { SearchComponent } from './pages/search/search.component';
import { PlanningComponent } from './pages/planning/planning.component';
import { LoginComponent } from './pages/auth/login/login.component';
import { RegisterComponent } from './pages/auth/register/register.component';
import { GuestGuard } from './core/guards/guest.guard';
import { AuthGuard } from './core/guards/auth.guard';

export const routes: Routes = [
  {
    path: '',
    component: LoginComponent,
    data: { pageTitle: 'Login' },
    title: 'Login Page - Dishu',
    canActivate: [GuestGuard],
  },
  {
    path: 'register',
    component: RegisterComponent,
    data: { pageTitle: 'Login' },
    title: 'Register Page - Dishu',
    canActivate: [GuestGuard],
  },

  {
    path: 'home',
    component: HomeComponent,
    data: { pageTitle: 'Home' },
    title: 'Home Page - Dishu',
    canActivate: [AuthGuard],
  },
  {
    path: 'search',
    component: SearchComponent,
    data: { pageTitle: 'Search' },
    title: 'Search Page - Dishu',
    canActivate: [AuthGuard],
  },
  {
    path: 'recipes',
    component: RecipesComponent,
    data: { pageTitle: 'Recipes' },
    title: 'Recipes Page - Dishu',
    runGuardsAndResolvers: 'always',
    canActivate: [AuthGuard],
  },
  {
    path: 'planning',
    component: PlanningComponent,
    data: { pageTitle: 'Planning' },
    title: 'Planning Page - Dishu',
    canActivate: [AuthGuard],
  },
  { path: '**', redirectTo: 'not-found-page' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
