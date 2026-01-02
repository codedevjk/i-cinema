import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { MovieService } from './services/movie.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'i-cinema';
  searchTerm: string = '';

  constructor(private movieService: MovieService, private router: Router) { }

  onSearch(): void {
    this.movieService.updateSearchTerm(this.searchTerm);
    // If not on home page, navigate to home to see results
    if (this.router.url !== '/home') {
      this.router.navigate(['/home']);
    }
  }
}
