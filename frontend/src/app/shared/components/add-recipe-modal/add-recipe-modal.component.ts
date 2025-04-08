// add-recipe-modal.component.ts
import { Component, Input, Output, EventEmitter } from '@angular/core';
import { trigger, transition, style, animate } from '@angular/animations';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { IngredientService } from '../../../core/services/ingredients/ingredient.service';
import { FormsModule } from '@angular/forms';
import { Ingredient } from '../../../core/models/ingredient';
import { RecipesService } from '../../../core/services/recipes/recipes.service';
import { Recipe, RecipeType } from '../../../core/models/recipe';

@Component({
  selector: 'app-add-recipe-modal',
  templateUrl: './add-recipe-modal.component.html',
  imports: [CommonModule, MatIconModule, FormsModule],
  styleUrls: ['./add-recipe-modal.component.css'],
  animations: [
    trigger('stepAnimation', [
      transition('* => *', [
        style({ opacity: 0, transform: 'translateX(50px)' }),
        animate(
          '300ms ease-out',
          style({ opacity: 1, transform: 'translateX(0)' })
        ),
      ]),
    ]),
  ],
})
export class AddRecipeModalComponent {
  public iFocused: boolean = false;
  @Input() showModal: boolean = false;
  @Output() closeModalEvent = new EventEmitter<void>();
  imageList: string[] = [
    'img/cocotte.jpg',
    'img/burger.jpg',
    'img/gateau.jpg',
    'img/plat.jpg',
    'img/poele.jpg',
    'img/sandwich.jpg',
    'img/sushi.jpg',
    'img/taco.jpg',
    'img/pizza.jpg',
  ];
  successMessage: string = '';
  recipetitle: string = '';
  recipeDescription: string = '';
  selectedImage: string = '';
  ingredientQuery: string = '';
  ingredientQuantity: number = 1;
  suggestions: Ingredient[] = [];
  selectedIngredient: Ingredient | undefined;
  addedIngredients: { id: number; name: string; quantity: number }[] = [];
  units: string[] = ['g', 'ml'];
  selectedUnit: string = this.units[0];
  recipeMealTypes: RecipeType[] = [
    RecipeType.MATIN,
    RecipeType.GOUTER,
    RecipeType.MIDI,
    RecipeType.SOIR,
  ];
  selectedMealTypes: RecipeType[] = [];

  @Output() recipeCreated = new EventEmitter<Recipe>();

  currentStep: number = 1;

  constructor(
    private ingredientService: IngredientService,
    private recipesService: RecipesService
  ) {}

  onIngredientQueryChange(): void {
    if (this.ingredientQuery.length >= 3) {
      this.ingredientService
        .searchIngredients(this.ingredientQuery)
        .subscribe((results) => {
          console.log(results);
          this.suggestions = results;
        });
    } else {
      this.suggestions = [];
    }
  }

  addIngredient(ingredient: Ingredient): void {
    const quantity = this.ingredientQuantity || 1;
    this.addedIngredients.push({
      id: ingredient.id,
      name: ingredient.name,
      quantity,
    });
    this.ingredientQuantity = 1;
    this.ingredientQuery = '';
    this.suggestions = [];
  }

  removeIngredient(ingredient: { name: string; quantity: number }): void {
    this.addedIngredients = this.addedIngredients.filter(
      (i) => i !== ingredient
    );
  }

  selectImage(imageUrl: string): void {
    this.selectedImage = imageUrl;
    console.log('Image sélectionnée:', imageUrl);
  }

  nextStep() {
    if (this.currentStep < 5) {
      this.currentStep++;
    }
  }

  prevStep() {
    if (this.currentStep > 1) {
      this.currentStep--;
    }
  }

  clearRecipeData() {
    this.recipetitle = '';
    this.recipeDescription = '';
    this.selectedImage = '';
  }

  saveRecipe() {
    const ingredientsId = this.addedIngredients.map((ingredient) => {
      return ingredient.id;
    });
    const recipeObject = {
      id: null,
      name: this.recipetitle,
      description: this.recipeDescription,
      ingredients: ingredientsId,
      types: this.selectedMealTypes,
      img: this.selectedImage,
    };

    console.log(recipeObject);

    this.recipesService.createNewRecipe(recipeObject).subscribe({
      next: (createdRecipe) => {
        console.log(`Recipe ${recipeObject} added successfully`);
        this.clearRecipeData();
        this.recipeCreated.emit(createdRecipe);
      },
      error: (err) => {
        console.error('Error adding recipe:', err);
      },
    });

    this.closeModal();
  }

  onCheckboxChange(event: Event, mealType: RecipeType): void {
    const checkbox = event.target as HTMLInputElement;
    if (checkbox.checked) {
      if (!this.selectedMealTypes.includes(mealType)) {
        this.selectedMealTypes.push(mealType);
      }
    } else {
      this.selectedMealTypes = this.selectedMealTypes.filter(
        (type) => type !== mealType
      );
    }
    console.log('Selected meal types:', this.selectedMealTypes);
  }

  closeModal() {
    this.currentStep = 1;
    this.closeModalEvent.emit();
  }
}
