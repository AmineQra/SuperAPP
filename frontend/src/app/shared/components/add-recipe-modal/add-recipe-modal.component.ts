// add-recipe-modal.component.ts
import { Component, Input, Output, EventEmitter } from '@angular/core';
import { trigger, transition, style, animate } from '@angular/animations';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { IngredientService } from '../../../core/services/ingredients/ingredient.service';
import { FormsModule } from '@angular/forms';
import { Ingredient } from '../../../core/models/ingredient';

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
  selectedImage: string = '';
  ingredientQuery: string = '';
  ingredientQuantity: number = 1;
  suggestions: Ingredient[] = [];
  selectedIngredient: Ingredient | undefined;
  addedIngredients: { name: string; quantity: number }[] = [];
  units: string[] = ['g', 'ml'];
  selectedUnit: string = this.units[0];

  currentStep: number = 1;

  constructor(private ingredientService: IngredientService) {}

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

  addIngredient(ingredientName: string): void {
    const quantity = this.ingredientQuantity || 1;
    this.addedIngredients.push({ name: ingredientName, quantity });
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
    // Vous pouvez ensuite renvoyer l'url via un Output ou l'utiliser dans votre logique
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

  saveRecipe() {
    console.log('TODO save recipe');

    this.closeModal();
  }

  closeModal() {
    this.currentStep = 1;
    this.closeModalEvent.emit();
  }
}
