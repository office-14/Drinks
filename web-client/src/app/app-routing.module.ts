import { NgModule } from '@angular/core';
import { UIRouterModule } from "@uirouter/angular";
import { CartComponent }   from './cart/cart.component';
import { OrderComponent }   from './order/order.component';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

const cartState = { name: "cart", url: "/cart", component: CartComponent };
const orderState = { name: "order", url: "/order", component: OrderComponent };

@NgModule({
  declarations: [
    OrderComponent,
    CartComponent
  ],
  imports: [
    UIRouterModule.forRoot({ states: [cartState, orderState], useHash: true, otherwise: '/drinks' }),
    CommonModule,
    FormsModule
  ],
  exports: [UIRouterModule]
})
export class AppRoutingModule { }
