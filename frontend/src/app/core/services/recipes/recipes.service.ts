import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Recipe } from '../../models/recipe';
import { environment } from '../../../../environments/environment.development';

@Injectable({
  providedIn: 'root',
})
export class RecipesService {
  private apiServerUrl = environment.apiServerUrl;

  constructor(private http: HttpClient) {}

  public getRecipes(): Observable<Recipe[]> {
    return this.http.get<Recipe[]>(`${this.apiServerUrl}/api/recipes`);
  }

  public createNewRecipe(recipe: Recipe): Observable<Recipe> {
    return this.http.post<Recipe>(
      `${this.apiServerUrl}/api/recipes/add`,
      recipe
    );
  }

  public deleteRecipe(recipeId: number): Observable<void> {
    let urlLink = `${this.apiServerUrl}/api/recipes/delete/${recipeId}`;
    return this.http.delete<void>(urlLink);
  }
}
