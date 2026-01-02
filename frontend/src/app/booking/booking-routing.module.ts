import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BookingComponent } from './booking.component';
import { SeatSelectionComponent } from './seat-selection/seat-selection.component';
import { PaymentComponent } from './payment/payment.component';
import { ShowSelectionComponent } from './show-selection/show-selection.component';

const routes: Routes = [
  {
    path: '', component: BookingComponent, children: [
      { path: 'shows/:movieId', component: ShowSelectionComponent },
      { path: 'seat-selection/:showId', component: SeatSelectionComponent },
      { path: 'payment/:bookingId', component: PaymentComponent }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BookingRoutingModule { }
