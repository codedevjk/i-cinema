import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgForm } from '@angular/forms';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-login-signup',
  templateUrl: './login-signup.component.html',
  styleUrls: ['./login-signup.component.css']
})
export class LoginSignupComponent {
  isLoginMode = true;
  error: string | null = null;
  isLoading = false;

  constructor(private router: Router, private authService: AuthService) {
    if (this.authService.isLoggedIn()) {
      this.router.navigate(['/home']);
    }
  }

  onSwitchMode(mode: 'login' | 'signup') {
    this.isLoginMode = mode === 'login';
    this.error = null; // Clear errors on switch
  }

  onSubmit(form: NgForm) {
    if (!form.valid) {
      return;
    }

    const email = form.value.email;
    const password = form.value.password;

    this.isLoading = true;
    this.error = null;

    let authObs;

    if (this.isLoginMode) {
      authObs = this.authService.login(email, password);
    } else {
      const name = form.value.name;
      authObs = this.authService.signup(name, email, password);
    }

    authObs.subscribe({
      next: (resData) => {
        console.log('Authentication successful', resData);
        this.isLoading = false;
        this.router.navigate(['/home']);
      },
      error: (errorMessage) => {
        console.error('Authentication Error', errorMessage);
        
        // Handle backend error message nicely
        if (errorMessage.error && typeof errorMessage.error === 'string') {
           this.error = errorMessage.error; // Backend often returns simple string in simple setup
        } else if (errorMessage.error && errorMessage.error.message) {
            this.error = errorMessage.error.message;
        } else {
            this.error = 'An unknown error occurred. Please try again.';
        }
        
        this.isLoading = false;
      }
    });

    form.reset();
  }
}
