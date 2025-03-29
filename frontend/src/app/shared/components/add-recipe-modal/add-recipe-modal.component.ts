// add-recipe-modal.component.ts
import { Component, Input, Output, EventEmitter } from '@angular/core';
import { trigger, transition, style, animate } from '@angular/animations';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-add-recipe-modal',
  templateUrl: './add-recipe-modal.component.html',
  imports: [CommonModule, MatIconModule],
  styleUrls: ['./add-recipe-modal.component.css'],
  animations: [
    trigger('stepAnimation', [
      transition('* => *', [
        // Starting state for new page
        style({ opacity: 0, transform: 'translateX(50px)' }),
        // Animate in
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

  currentStep: number = 1; // Track which page is active

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
