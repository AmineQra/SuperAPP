import { Component, OnDestroy, OnInit } from '@angular/core';
import { Recipe } from '../../core/models/recipe';
import { RecipesService } from '../../core/services/recipes/recipes.service';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatChipsModule } from '@angular/material/chips';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { SearchbarComponent } from '../../shared/components/searchbar/searchbar.component';
import {
  trigger,
  state,
  style,
  transition,
  animate,
} from '@angular/animations';

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
  animations: [
    trigger('slideInOut', [
      state('true', style({ height: '*', opacity: 1 })),
      state('false', style({ height: '0px', opacity: 0 })),
      transition('true <=> false', animate('300ms ease-in-out')),
    ]),
  ],
})
export class RecipesComponent implements OnInit, OnDestroy {
  public recipes: Recipe[] | undefined;
  showDetailsMap: { [key: number]: boolean } = {};
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

  toggleDetails(recipeId: number) {
    this.showDetailsMap[recipeId] = !this.showDetailsMap[recipeId];
  }

  constructor(private recipeService: RecipesService) {}

  ngOnInit(): void {
    this.getRecipes();
  }

  ngOnDestroy(): void {
    this.recipes = [];
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
