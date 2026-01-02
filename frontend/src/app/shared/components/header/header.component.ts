import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService, User } from 'src/app/services/auth.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  @Output() onSearch = new EventEmitter<string>();
  searchQuery: string = '';
  user: User | null = null;

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit(): void {
    this.authService.currentUser$.subscribe((u: User | null) => this.user = u);
  }

  handleSearch(e: Event): void {
    e.preventDefault();
    this.onSearch.emit(this.searchQuery);
  }

  get initials(): string {
    if (!this.user || !this.user.name) return 'U';
    return this.user.name.split(' ').map((n: string) => n[0]).join('').substring(0, 2).toUpperCase();
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}
