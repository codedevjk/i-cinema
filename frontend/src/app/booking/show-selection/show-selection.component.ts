import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ShowService } from 'src/app/services/show.service';
import { MovieService } from 'src/app/services/movie.service';
import { Show, Movie } from 'src/app/shared/models/models';

interface TheatreGroup {
    name: string;
    location: string;
    availabilityNote: string;
    showTimes: { id: number, time: string, price: number }[];
}

interface DateCard {
    date: Date;
    dayName: string;
    dayNumber: string;
    month: string;
    isSelected: boolean;
}

@Component({
    selector: 'app-show-selection',
    templateUrl: './show-selection.component.html',
    styleUrls: ['./show-selection.component.css']
})
export class ShowSelectionComponent implements OnInit {
    movieId: number = 0;
    movie: Movie | undefined;
    theatres: TheatreGroup[] = [];
    availableDates: DateCard[] = [];
    
    // UI Helpers
    movieTitle: string = '';
    movieMeta: string = '';

    allShows: Show[] = [];

    constructor(
        private route: ActivatedRoute,
        private router: Router,
        private showService: ShowService,
        private movieService: MovieService
    ) {
        this.generateDates();
    }

    ngOnInit(): void {
        this.route.paramMap.subscribe(params => {
            this.movieId = Number(params.get('movieId'));
            if (this.movieId) {
                this.loadMovie();
                this.loadShows();
            }
        });
    }

    generateDates(): void {
        const today = new Date();
        const days = ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'];
        const months = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];

        for (let i = 0; i < 7; i++) {
            const d = new Date(today);
            d.setDate(today.getDate() + i);
            
            this.availableDates.push({
                date: d,
                dayName: days[d.getDay()],
                dayNumber: String(d.getDate()).padStart(2, '0'),
                month: months[d.getMonth()],
                isSelected: i === 0
            });
        }
    }

    loadMovie(): void {
        this.movieService.getMovieById(this.movieId).subscribe(data => {
            this.movie = data;
            this.movieTitle = data.title;
            // Construct meta string
            const parts = [
                data.durationInMinutes ? `${data.durationInMinutes}m` : '',
                data.genre,
                data.language,
                data.censorRating,
                data.rating ? `⭐ ${data.rating}/10` : ''
            ].filter(p => p).join(' • ');
            this.movieMeta = parts;
        });
    }

    loadShows(): void {
        this.showService.getShowsByMovie(this.movieId).subscribe(data => {
            this.allShows = data;
            this.filterShows();
        });
    }

    filterShows(): void {
        const selectedDateCard = this.availableDates.find(d => d.isSelected);
        if (!selectedDateCard) return;

        const dateToMatch = selectedDateCard.date;
        const year = dateToMatch.getFullYear();
        const month = String(dateToMatch.getMonth() + 1).padStart(2, '0');
        const day = String(dateToMatch.getDate()).padStart(2, '0');
        const dateString = `${year}-${month}-${day}`;

        const filtered = this.allShows.filter(show => show.showDate.toString() === dateString);
        this.groupShowsByTheatre(filtered);
    }

    groupShowsByTheatre(shows: Show[]): void {
        const map = new Map<string, Show[]>();
        shows.forEach(show => {
            if (!map.has(show.theatreName)) {
                map.set(show.theatreName, []);
            }
            map.get(show.theatreName)?.push(show);
        });

        this.theatres = [];
        map.forEach((theatreShows, theatreName) => {
            theatreShows.sort((a, b) => a.showTime.localeCompare(b.showTime));
            
            // Transform to UI model
            const uiTheatre: TheatreGroup = {
                name: theatreName,
                location: theatreShows[0].location || 'Downtown', // Fallback location if DTO not showing
                availabilityNote: 'Cancellation Available',
                showTimes: theatreShows.map(s => ({
                    id: s.id,
                    time: s.showTime.substring(0, 5),
                    price: s.ticketPrice || 150 // Fallback price
                }))
            };
            this.theatres.push(uiTheatre);
        });
    }

    onSelectDate(dateCard: DateCard): void {
        this.availableDates.forEach(d => d.isSelected = false);
        dateCard.isSelected = true;
        this.filterShows();
    }

    onSelectShow(theatreName: string, time: string): void {
        // Find the specific show ID. 
        // In a real app we'd pass showId directly, but the template passed theatreName/time.
        // We'll fix the template binding to be simpler in the HTML step, 
        // but let's support finding it here just in case.
    }
    
    // Helper to accept specific show ID directly for cleaner binding
    bookShow(showId: number): void {
        this.router.navigate(['/booking/seat-selection', showId]);
    }
}
