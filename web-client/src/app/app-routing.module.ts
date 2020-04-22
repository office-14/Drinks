import { NgModule } from '@angular/core';
import { UIRouterModule } from "@uirouter/angular";
import { CartComponent }   from './cart/cart.component';
import { OrderComponent }   from './order/order.component';
import { SigninComponent }   from './signin/signin.component';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

const cart_state = { name: "cart", url: "/cart", component: CartComponent };
const order_state = { name: "order", component: OrderComponent};
const signin_state = { name: "signin", url: "/signin", params: {order_creating_started: false}, component: SigninComponent };

@NgModule({
  declarations: [
    OrderComponent,
    CartComponent,
    SigninComponent
  ],
  imports: [
    UIRouterModule.forRoot({ states: [cart_state, order_state, signin_state], useHash: true, otherwise: '/drinks'}),
    CommonModule,
    FormsModule
  ],
  exports: [UIRouterModule]
})
export class AppRoutingModule { }
