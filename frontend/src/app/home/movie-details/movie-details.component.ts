import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MovieService } from 'src/app/services/movie.service';
import { Movie } from 'src/app/shared/models/models';

@Component({
  selector: 'app-movie-details',
  templateUrl: './movie-details.component.html',
  styleUrls: ['./movie-details.component.css']
})
export class MovieDetailsComponent implements OnInit {
  movie: Movie | undefined;
  movieId: number = 0;

  // Rating Logic
  selectedRating: number | null = null;
  isRatingSubmitting = false;
  ratingError: string | null = null;

  // Toast Logic
  showToast: boolean = false;
  toastMessage: string = '';

  constructor(
    private route: ActivatedRoute,
    private movieService: MovieService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.movieId = Number(params.get('id'));
      if (this.movieId) {
        this.loadMovie();
      }
    });
  }

  loadMovie(): void {
    this.movieService.getMovieById(this.movieId).subscribe(data => {
      this.movie = data;
    });
  }

  bookMyShow(): void {
    this.router.navigate(['/booking/shows', this.movieId]);
  }

  submitRating(): void {
    this.ratingError = null;
    const rating = Number(this.selectedRating);

    if (!rating || rating < 1 || rating > 10) {
      this.ratingError = 'Please enter a valid rating between 1 and 10.';
      return;
    }

    this.isRatingSubmitting = true;
    this.movieService.rateMovie(this.movieId, rating).subscribe({
      next: (updatedMovie) => {
        console.log('Updated Movie Response:', updatedMovie);
        // Show Toast
        this.showNotification(`Rating for "${updatedMovie.title}" submitted! You rated it ${rating} stars.`);
        
        this.isRatingSubmitting = false;
        this.selectedRating = null;
        this.movie = updatedMovie; 
      },
      error: (err) => {
        console.error(err);
        this.isRatingSubmitting = false;
        this.ratingError = 'Failed to submit rating. Please try again.';
      }
    });
  }

  private showNotification(message: string): void {
    this.toastMessage = message;
    this.showToast = true;
    setTimeout(() => {
      this.showToast = false;
    }, 3000);
  }
}
