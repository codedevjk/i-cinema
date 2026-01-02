import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { BookingRequest, BookingResponse, PaymentRequest } from '../shared/models/models';
import { environment } from '../../environments/environment';
import { AuthService } from './auth.service';

export interface BookingHistory {
  bookingId: number;
  movieTitle: string;
  movieImage?: string;
  theatreName: string;
  showTime: string; // ISO string
  numberOfSeats: number;
  seatNumbers: string;
  totalAmount: number;
  status: string;
}

@Injectable({
  providedIn: 'root'
})
export class BookingService {
  private apiUrl = `${environment.apiUrl}/bookings`;
  private usersUrl = `${environment.apiUrl}/users`;

  constructor(private http: HttpClient, private authService: AuthService) { }

  initiateBooking(request: BookingRequest): Observable<BookingResponse> {
    // Automatically attach user email if logged in
    const currentUser = this.authService.currentUserValue;
    if (currentUser) {
      request.userEmail = currentUser.email;
    }
    return this.http.post<BookingResponse>(this.apiUrl, request);
  }

  processPayment(request: PaymentRequest): Observable<BookingResponse> {
    return this.http.post<BookingResponse>(`${this.apiUrl}/payment`, request);
  }

  getBooking(id: number): Observable<BookingResponse> {
    return this.http.get<BookingResponse>(`${this.apiUrl}/${id}`);
  }

  getUserBookings(): Observable<BookingHistory[]> {
    const currentUser = this.authService.currentUserValue;
    if (!currentUser) {
      // Should handle this better, maybe return empty or throw
       return new Observable(observer => observer.error("User not logged in"));
    }
    return this.http.get<BookingHistory[]>(`${this.usersUrl}/${currentUser.id}/bookings`);
  }
}
