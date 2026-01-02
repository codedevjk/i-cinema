import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { AuthService, User } from 'src/app/services/auth.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  user: User | null = null;
  name = '';
  phone = '';
  
  isLoading = false;
  isSaving = false;
  successMessage: string | null = null;
  errorMessage: string | null = null;

  constructor(private authService: AuthService, private location: Location) {}

  ngOnInit() {
    this.user = this.authService.currentUserValue;
    if (this.user) {
      this.name = this.user.name;
      this.phone = this.user.phone || '';
    }
  }

  getInitials(): string {
    if (this.name) {
      return this.name.split(' ').map(n => n[0]).join('').toUpperCase().slice(0, 2);
    }
    return this.user?.email[0].toUpperCase() || 'U';
  }

  onSubmit() {
    this.isSaving = true;
    this.successMessage = null;
    this.errorMessage = null;

    this.authService.updateProfile(this.name, this.phone).subscribe({
      next: (updatedUser) => {
        this.user = updatedUser;
        this.successMessage = 'Profile updated successfully!';
        this.isSaving = false;
      },
      error: (err) => {
        console.error('Failed to update profile', err);
        this.errorMessage = 'Failed to update profile. Please try again.';
        this.isSaving = false;
      }
    });
  }

  goBack() {
    this.location.back();
  }

  logout() {
    this.authService.logout();
    // Router redirect will happen in app component or just manual
    window.location.href = '/'; 
  }
}
