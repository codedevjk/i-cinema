export enum Genre {
    ACTION = 'ACTION',
    COMEDY = 'COMEDY',
    DRAMA = 'DRAMA',
    HORROR = 'HORROR',
    ROMANCE = 'ROMANCE',
    SCIFI = 'SCIFI',
    THRILLER = 'THRILLER',
    ANIMATION = 'ANIMATION'
}

export enum Language {
    ENGLISH = 'ENGLISH',
    HINDI = 'HINDI',
    TELUGU = 'TELUGU',
    TAMIL = 'TAMIL',
    KANNADA = 'KANNADA',
    MALAYALAM = 'MALAYALAM'
}

export enum SeatStatus {
    AVAILABLE = 'AVAILABLE',
    BOOKED = 'BOOKED',
    SELECTED = 'SELECTED'
}

export enum CardType {
    DEBIT = 'DEBIT',
    CREDIT = 'CREDIT'
}

export interface Movie {
    id: number;
    title: string;
    genre: Genre;
    language: Language;
    description: string;
    durationInMinutes: number;
    rating: number;
    imageUrl?: string;
    censorRating?: string;
    totalVotes?: number;
}

export interface Show {
    id: number;
    movieId: number;
    theatreId: number;
    theatreName: string;
    location?: string; // Added location
    showDate: Date;
    showTime: string;
    ticketPrice?: number; // Added price
}

export interface Seat {
    id: number;
    seatNumber: string;
    status: SeatStatus;
    price: number;
}

export interface BookingRequest {
    showId: number;
    seatIds: number[];
    userEmail: string;
}

export interface BookingResponse {
    bookingId: number;
    totalAmount: number;
    bookingTime: string;
    status: string;
}

export interface PaymentRequest {
    bookingId: number;
    cardType: CardType;
    cardNumber: string;
    cvv: string;
    expiryDate: string;
}
