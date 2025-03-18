import { Component, OnInit } from '@angular/core';
import { Recipe } from '../../core/models/recipe';
import { RecipesService } from '../../core/services/recipes/recipes.service';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatChipsModule } from '@angular/material/chips';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-recipes',
  imports: [
    CommonModule,
    MatCardModule,
    MatChipsModule,
    MatButtonModule,
    MatIconModule,
  ],
  templateUrl: './recipes.component.html',
  styleUrl: './recipes.component.css',
})
export class RecipesComponent implements OnInit {
  public recipes: Recipe[] | undefined;
  showDetails: boolean = false;
  arrowDirection: string = '▼';
  showModal: boolean = false;

  changeArrowDirection() {
    if (this.arrowDirection == '▲') this.arrowDirection = '▼';
    else {
      this.arrowDirection = '▲';
    }
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

  toggleDetails() {
    this.showDetails = !this.showDetails;
    this.changeArrowDirection();
  }

  constructor(private recipeService: RecipesService) {}

  ngOnInit(): void {
    this.getRecipes();
  }

  public getRecipes(): void {
    try {
      this.recipeService.getRecipes().subscribe((response: Recipe[]) => {
        const uniqueRecipes = new Map(
          response.map((recipe) => [recipe.id, recipe])
        );
        this.recipes = Array.from(uniqueRecipes.values());
      });
    } catch (error) {
      alert(error);
    }
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
