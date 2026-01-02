import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { BookingService } from 'src/app/services/booking.service';
import { MovieService } from 'src/app/services/movie.service';
import { ShowService } from 'src/app/services/show.service';
import { Seat, SeatStatus, Show, BookingRequest, Movie } from 'src/app/shared/models/models';

@Component({
  selector: 'app-seat-selection',
  templateUrl: './seat-selection.component.html',
  styleUrls: ['./seat-selection.component.css']
})
export class SeatSelectionComponent implements OnInit {
  showId: number = 0;
  show: Show | undefined;
  movie: Movie | undefined; // Added movie property
  seats: Seat[] = [];
  selectedSeatIds: number[] = [];

  // Seat Grid Layout Helpers

  constructor(
    private route: ActivatedRoute,
    private showService: ShowService,
    private bookingService: BookingService,
    private movieService: MovieService, // Injected MovieService
    private router: Router
  ) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.showId = Number(params.get('showId'));
      if (this.showId) {
        this.loadShowDetails();
        this.loadSeats();
      }
    });
  }

  loadShowDetails(): void {
    this.showService.getShowById(this.showId).subscribe(data => {
      this.show = data;
      if (this.show && this.show.movieId) {
        this.loadMovie(this.show.movieId);
      }
    });
  }

  loadMovie(movieId: number): void {
    this.movieService.getMovieById(movieId).subscribe(data => this.movie = data);
  }

  loadSeats(): void {
    this.showService.getSeatsForShow(this.showId).subscribe({
      next: (data) => {
        if (!data || data.length === 0) {
          console.warn('No seats found for show:', this.showId);
        }
        this.seats = data || [];
        this.groupSeats();
      },
      error: (err) => {
        console.error('Failed to load seats', err);
      }
    });
  }

  groupedSeats: { rowLabel: string, seats: Seat[] }[] = [];

  groupSeats(): void {
    const groups: { [key: string]: Seat[] } = {};

    this.seats.forEach(seat => {
      if (!seat.seatNumber) return;
      
      const rowLabel = seat.seatNumber.charAt(0);
      
      // Apply User Pricing Rule
      // A-G: Regular (180), H-J: Premium (220)
      const isPremium = ['H', 'I', 'J'].includes(rowLabel.toUpperCase());
      seat.price = isPremium ? 220 : 180;
      // We can store 'type' in a transient property if needed, or just infer from price

      if (!groups[rowLabel]) {
        groups[rowLabel] = [];
      }
      groups[rowLabel].push(seat);
    });

    const sortedRows = Object.keys(groups).sort();
    
    // Sort logic: A (closest to screen) to J (back)
    // No change needed if alphabetical sort works for A-J.

    this.groupedSeats = sortedRows.map(row => {
      return {
        rowLabel: row,
        seats: groups[row].sort((a, b) => {
          // Extract number part: A1 -> 1, A10 -> 10
          const numA = parseInt(a.seatNumber.substring(1));
          const numB = parseInt(b.seatNumber.substring(1));
          return numA - numB;
        })
      };
    });
  }

  // Helper logic moved to template
  isBookingValid(): boolean {
    return this.selectedSeatIds.length > 0;
  }

  toggleSeat(seat: Seat): void {
    if (seat.status === SeatStatus.BOOKED) return;

    if (this.selectedSeatIds.includes(seat.id)) {
      this.selectedSeatIds = this.selectedSeatIds.filter(id => id !== seat.id);
      seat.status = SeatStatus.AVAILABLE; // Visual only
    } else {
      this.selectedSeatIds.push(seat.id);
      // seat.status = SeatStatus.SELECTED; // Visual only, though logic uses array
    }
  }

  getTotalPrice(): number {
    return this.selectedSeatIds.reduce((total, id) => {
      const seat = this.seats.find(s => s.id === id);
      return total + (seat ? seat.price : 0);
    }, 0);
  }

  getSelectedSeatsString(): string {
    if (this.selectedSeatIds.length === 0) return '';
    const selected = this.seats.filter(s => this.selectedSeatIds.includes(s.id));
    // Sort by seat number for display (e.g. A1, A2, B1)
    return selected
      .sort((a, b) => a.seatNumber.localeCompare(b.seatNumber))
      .map(s => s.seatNumber)
      .join(', ');
  }

  isProcessing: boolean = false;

  proceedToPay(): void {
    if (this.isProcessing) return; // Prevent double clicks
    
    console.log('Proceed to Pay clicked. Selected Seats:', this.selectedSeatIds);
    if (this.selectedSeatIds.length === 0) {
      alert('Please select at least one seat.');
      return;
    }

    this.isProcessing = true; // Lock button

    const request: BookingRequest = {
      showId: this.showId,
      seatIds: this.selectedSeatIds,
      userEmail: 'guest@example.com' 
    };

    console.log('Initiating Booking Request:', request);
    this.bookingService.initiateBooking(request).subscribe({
      next: (response) => {
        console.log('Booking Successful:', response);
        if (response.bookingId) {
             this.router.navigate(['/booking/payment', response.bookingId]);
        } else {
             console.error('No Booking ID returned!');
             alert('Booking processed but ID missing. Check your email or try again.');
             this.isProcessing = false;
        }
      },
      error: (err) => {
        console.error('Booking Failed:', err);
        alert('Failed to initiate booking. Seats might be taken.');
        this.loadSeats(); 
        this.isProcessing = false; 
      }
    });
  }
}
