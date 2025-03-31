import { Component } from '@angular/core';
import {
  FormControl,
  FormGroup,
  Validators,
  ReactiveFormsModule,
} from '@angular/forms';
import { RegisterService } from '../../../core/services/auth/register.service';
import { Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-register',
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css',
})
export class RegisterComponent {
  isLoading: boolean = false;
  errorMessage: string = '';

  constructor(
    private registerService: RegisterService,
    private router: Router
  ) {}

  registerForm = new FormGroup({
    firstName: new FormControl('', [
      Validators.required,
      Validators.minLength(2),
    ]),
    lastName: new FormControl('', [
      Validators.required,
      Validators.minLength(2),
    ]),
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [
      Validators.required,
      Validators.minLength(8),
    ]),
  });

  passwordMatchValidator(form: FormGroup) {
    const password = form.get('password')?.value;
    const confirmPassword = form.get('confirmPassword')?.value;
    return password === confirmPassword ? null : { mismatch: true };
  }

  async onRegister() {
    if (this.registerForm.valid) {
      this.isLoading = true;
      const { firstName, lastName, email, password } = this.registerForm.value;
      await new Promise((resolve) => setTimeout(resolve, 1000));

      this.registerService
        .register(firstName!, lastName!, email!, password!)
        .subscribe({
          next: () => {
            this.router.navigate(['/recipes']);
          },
          error: (err) => {
            this.isLoading = false;
            this.errorMessage =
              err.error?.message || 'Login failed. Please try again.';
          },
          complete: () => {
            this.isLoading = false;
          },
        });
    }
  }
}
