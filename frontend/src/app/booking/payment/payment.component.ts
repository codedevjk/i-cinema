import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { BookingService } from 'src/app/services/booking.service';
import { BookingResponse, CardType, PaymentRequest } from 'src/app/shared/models/models';

@Component({
  selector: 'app-payment',
  templateUrl: './payment.component.html',
  styleUrls: ['./payment.component.css']
})
export class PaymentComponent implements OnInit {
  bookingId: number = 0;
  booking: BookingResponse | undefined;
  paymentForm: FormGroup;
  cardTypes = Object.values(CardType);
  finalAmount: number = 0;
  isProcessing = false;
  isConfirmed = false;

  // Breakdown properties
  ticketPrice: number = 0;
  convenienceFee: number = 50;
  gst: number = 0;
  discountAmount: number = 0;

  constructor(
    private route: ActivatedRoute,
    private bookingService: BookingService,
    private fb: FormBuilder,
    private router: Router
  ) {
    this.paymentForm = this.fb.group({
      cardType: [CardType.DEBIT, Validators.required],
      cardNumber: ['', [Validators.required]], // Validation in submit
      expiryDate: ['', [Validators.required]],
      cvv: ['', [Validators.required]]
    });
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.bookingId = Number(params.get('bookingId'));
      if (this.bookingId) {
        this.loadBooking();
      }
    });

    // Recalculate amount on card type change
    this.paymentForm.get('cardType')?.valueChanges.subscribe(() => this.calculateFinalAmount());
  }

  loadBooking(): void {
    this.bookingService.getBooking(this.bookingId).subscribe(data => {
      this.booking = data;
      this.calculateFinalAmount();
    });
  }

  // Form Helpers
  formatCardNumber(value: string): string {
    const v = value.replace(/\s+/g, '').replace(/[^0-9]/gi, '');
    const matches = v.match(/\d{4,16}/g);
    const match = (matches && matches[0]) || '';
    const parts = [];
    for (let i = 0, len = match.length; i < len; i += 4) {
      parts.push(match.substring(i, i + 4));
    }
    return parts.length ? parts.join(' ') : value;
  }

  validateForm(): boolean {
    const values = this.paymentForm.value;
    const cardNumber = values.cardNumber ? values.cardNumber.replace(/\s/g, '') : '';
    const expiryDate = values.expiryDate;
    const cvv = values.cvv;

    // Card Number
    if (cardNumber.length !== 16) {
      alert('Please enter a valid 16-digit card number');
      return false;
    }

    // Expiry Format
    const expiryRegex = /^(0[1-9]|1[0-2])\/([0-9]{2})$/;
    if (!expiryRegex.test(expiryDate)) {
      alert('Please enter a valid expiry date (MM/YY)');
      return false;
    }

    // Expiry Logic
    const [month, year] = expiryDate.split('/');
    const expiry = new Date(2000 + parseInt(year), parseInt(month) - 1);
    const now = new Date();
    now.setDate(1); 
    now.setHours(0,0,0,0);
    
    if (expiry < now) {
      alert('Card has expired');
      return false;
    }

    // CVV
    if (!cvv || cvv.length !== 3) {
      alert('Please enter a valid 3-digit CVV');
      return false;
    }

    return true;
  }

  calculateFinalAmount(): void {
    if (!this.booking) return;
    
    // Reverse Calculate Breakdown for Display (Assuming Backend: (Seat + 50) * 1.18 = Total)
    const totalWithTax = this.booking.totalAmount;
    const preTax = totalWithTax / 1.18;
    this.gst = totalWithTax - preTax;
    this.convenienceFee = 50; 
    this.ticketPrice = preTax - this.convenienceFee;

    // Discount
    this.discountAmount = 0;
    const type = this.paymentForm.get('cardType')?.value;
    let discountRate = 0;
    if (type === CardType.CREDIT) discountRate = 0.10;
    if (type === CardType.DEBIT) discountRate = 0.05;

    this.discountAmount = this.booking.totalAmount * discountRate;
    this.finalAmount = this.booking.totalAmount - this.discountAmount;
  }

  onSubmit(): void {
    if (this.paymentForm.invalid || !this.validateForm()) return;

    this.isProcessing = true;
    const request: PaymentRequest = {
      bookingId: this.bookingId,
      cardType: this.paymentForm.get('cardType')?.value,
      cardNumber: this.paymentForm.get('cardNumber')?.value.replace(/\s/g, ''),
      cvv: this.paymentForm.get('cvv')?.value,
      expiryDate: this.paymentForm.get('expiryDate')?.value
    };

    this.bookingService.processPayment(request).subscribe({
      next: (response) => {
        this.isProcessing = false;
        this.isConfirmed = true;
        this.booking = response; 
      },
      error: (err) => {
        console.error(err);
        this.isProcessing = false;
        alert('Payment failed. Please try again.');
      }
    });
  }

  onCardNumberInput(event: any): void {
    const input = event.target as HTMLInputElement;
    const formatted = this.formatCardNumber(input.value);
    this.paymentForm.patchValue({ cardNumber: formatted }, { emitEvent: false });
  }

  goBack(): void {
      // Assuming showId is stored or can be navigated back via history, 
      // but for now history.back() is simplest for "Back"
      window.history.back();
  }

  goHome(): void {
    this.router.navigate(['/']);
  }
}
