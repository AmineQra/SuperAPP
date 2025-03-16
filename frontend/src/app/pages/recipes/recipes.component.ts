import { Component, OnInit } from '@angular/core';
import { Recipe } from '../../core/models/recipe';
import { RecipesService } from '../../core/services/recipes/recipes.service';
import { HttpErrorResponse } from '@angular/common/http';
import { error } from 'console';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-recipes',
  imports: [CommonModule],
  templateUrl: './recipes.component.html',
  styleUrl: './recipes.component.css',
})
export class RecipesComponent implements OnInit {
  public recipes: Recipe[] | undefined;

  constructor(private recipeService: RecipesService) {}

  ngOnInit(): void {
    this.getRecipes();
  }

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
