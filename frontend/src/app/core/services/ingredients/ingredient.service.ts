import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment.development';
import { Ingredient } from '../../models/ingredient';

@Injectable({
  providedIn: 'root',
})
export class IngredientService {
  private apiUrl = `${environment.apiServerUrl}/api/ingredients/search`;

  constructor(private http: HttpClient) {}

  searchIngredients(query: string): Observable<Ingredient[]> {
    return this.http.post<Ingredient[]>(this.apiUrl, { query });
  }
}
