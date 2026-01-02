import { Component, OnInit } from '@angular/core';
import { BookingService, BookingHistory } from 'src/app/services/booking.service';
import { Location } from '@angular/common';

@Component({
  selector: 'app-booking-history',
  templateUrl: './booking-history.component.html',
  styleUrls: ['./booking-history.component.css']
})
export class BookingHistoryComponent implements OnInit {
  bookings: BookingHistory[] = [];
  isLoading = true;
  error: string | null = null;

  upcomingBookings: BookingHistory[] = [];
  pastBookings: BookingHistory[] = [];
  activeTab: 'upcoming' | 'past' = 'upcoming';

  constructor(private bookingService: BookingService, private location: Location) {}

  ngOnInit() {
    this.loadBookings();
  }

  loadBookings() {
    this.bookingService.getUserBookings().subscribe({
      next: (data) => {
        this.bookings = data;
        this.filterBookings();
        this.isLoading = false;
      },
      error: (err) => {
        console.error('Failed to load bookings', err);
        this.error = 'Could not load booking history.';
        this.isLoading = false;
      }
    });
  }

  filterBookings() {
    const now = new Date();
    this.upcomingBookings = this.bookings.filter(b => new Date(b.showTime) >= now);
    this.pastBookings = this.bookings.filter(b => new Date(b.showTime) < now);
  }

  get filteredBookings() {
    return this.activeTab === 'upcoming' ? this.upcomingBookings : this.pastBookings;
  }

  setActiveTab(tab: 'upcoming' | 'past') {
    this.activeTab = tab;
  }

  goBack() {
    this.location.back();
  }
}
