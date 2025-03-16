import { RouterModule, Routes } from '@angular/router';
import { MyrecipesComponent } from './pages/myrecipes/myrecipes.component';
import { NgModule } from '@angular/core';

export const routes: Routes = [
  { path: '', component: MyrecipesComponent },
  { path: '**', redirectTo: '' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
