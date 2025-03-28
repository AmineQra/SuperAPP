import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { Recipe, RecipeType } from '../../core/models/recipe';
import { RecipesService } from '../../core/services/recipes/recipes.service';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatChipsModule } from '@angular/material/chips';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { SearchbarComponent } from '../../shared/components/searchbar/searchbar.component';

@Component({
  selector: 'app-recipes',
  imports: [
    CommonModule,
    MatCardModule,
    MatChipsModule,
    MatButtonModule,
    MatIconModule,
    SearchbarComponent,
  ],
  templateUrl: './recipes.component.html',
  styleUrl: './recipes.component.css',
})
export class RecipesComponent implements OnInit, OnDestroy {
  public recipes: Recipe[] | undefined;
  showDetailsMap: { [key: number]: boolean } = {};
  showModal: boolean = false;
  recipeType: typeof RecipeType;

  constructor(private recipeService: RecipesService) {
    this.recipeType = RecipeType;
  }

  ngOnInit(): void {
    this.getRecipes();
  }

  ngOnDestroy(): void {
    this.recipes = [];
  }

  closeModal() {
    this.showModal = false;
  }

  saveRecipe() {
    console.log('recipe saved');
  }

  openModal() {
    this.showModal = !this.showModal;
  }

  toggleDetails(recipeId: number) {
    this.showDetailsMap[recipeId] = !this.showDetailsMap[recipeId];
  }

  public getRecipes(): void {
    this.recipeService.getRecipes().subscribe((response: Recipe[]) => {
      this.recipes = [];
      this.recipes = response;
    });
  }

  public deleteRecipe(recipeId: number): void {
    this.recipeService.deleteRecipe(recipeId).subscribe({
      next: () => {
        console.log(`Recipe with ID ${recipeId} deleted successfully`);
        this.recipes = this.recipes?.filter((recipe) => recipe.id !== recipeId);
      },
      error: (err) => {
        console.error('Error deleting recipe:', err);
      },
    });
  }
}
