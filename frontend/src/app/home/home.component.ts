import { Component, OnInit } from '@angular/core';
import { MovieService } from '../services/movie.service';
import { Movie, Genre, Language } from '../shared/models/models';
import { Router } from '@angular/router';
import { AuthService, User } from '../services/auth.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  movies: Movie[] = [];
  filteredMovies: Movie[] = [];
  user: User | null = null;
  isDropdownOpen = false;

  // Filters
  searchTerm: string = '';
  selectedGenres: string[] = [];
  selectedLanguages: string[] = [];
  selectedRating: string | null = null;

  // Enums for UI
  genres = Object.values(Genre);
  languages = Object.values(Language);
  ratings = ['9.0+', '8.0+', '7.0+', '6.0+'];

  constructor(private movieService: MovieService, private router: Router) { }

  ngOnInit(): void {
    // Subscribe to global search to update local list if needed
    // In this design, the header drives the search via event, but we can also listen to service
    this.movieService.currentSearchTerm$.subscribe(term => {
      this.searchTerm = term;
      if (term.trim()) {
        this.searchMovies(term);
      } else {
        this.loadMovies();
      }
    });
  }

  loadMovies(): void {
    this.movieService.getAllMovies().subscribe({
      next: (data) => {
        this.movies = data;
        this.applyFilters(); 
      },
      error: (err) => console.error('Error fetching movies', err)
    });
  }

  // Called by Header Component
  handleSearch(term: string): void {
    this.movieService.updateSearchTerm(term);
  }

  searchMovies(term: string): void {
    this.movieService.searchMovies(term).subscribe(data => {
      this.movies = data;
      this.applyFilters();
    });
  }

  // Filter Handlers
  handleGenreChange(genre: string): void {
    if (this.selectedGenres.includes(genre)) {
      this.selectedGenres = this.selectedGenres.filter(g => g !== genre);
    } else {
      this.selectedGenres.push(genre);
    }
    this.applyFilters();
  }

  handleLanguageChange(lang: string): void {
    if (this.selectedLanguages.includes(lang)) {
      this.selectedLanguages = this.selectedLanguages.filter(l => l !== lang);
    } else {
      this.selectedLanguages.push(lang);
    }
    this.applyFilters();
  }

  handleRatingChange(rating: string | null): void {
    this.selectedRating = rating;
    this.applyFilters();
  }

  handleClearAll(): void {
    this.selectedGenres = [];
    this.selectedLanguages = [];
    this.selectedRating = null;
    this.applyFilters();
  }

  applyFilters(): void {
    this.filteredMovies = this.movies.filter(movie => {
      // Genre Filter (OR logic within genres: match any selected)
      const matchGenre = this.selectedGenres.length === 0 || this.selectedGenres.includes(movie.genre);
      
      // Language Filter (OR logic)
      const matchLang = this.selectedLanguages.length === 0 || this.selectedLanguages.includes(movie.language);
      
      // Rating Filter
      let matchRating = true;
      if (this.selectedRating && this.selectedRating !== 'All') {
        const minRating = parseInt(this.selectedRating.replace('+', '')); 
        matchRating = movie.rating >= minRating;
      }

      return matchGenre && matchLang && matchRating;
    });
  }

  navigateToDetails(movieId: number): void {
    this.router.navigate(['home', 'movie', movieId]);
  }

  formatDuration(minutes: number): string {
    const h = Math.floor(minutes / 60);
    const m = minutes % 60;
    return `${h}h ${m}m`;
  }
}
