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

  changeArrowDirection() {
    if (this.arrowDirection == '▲') this.arrowDirection = '▼';
    else {
      this.arrowDirection = '▲';
    }
  }

  toggleDetails() {
    this.showDetails = !this.showDetails;
    this.changeArrowDirection();
  }

  constructor(private recipeService: RecipesService) {}

  ngOnInit(): void {}

  public getRecipes(): void {
    try {
      this.recipeService.getRecipes().subscribe((response: Recipe[]) => {
        this.recipes = response;
      });
    } catch (error) {
      alert(error);
    }
  }
}
