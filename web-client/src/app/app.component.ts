import { Component } from '@angular/core';
import { CartService } from './cart.service';
import { OrderService } from './order.service';
import { AuthService } from './auth/auth.service';
import { StateService } from "@uirouter/core";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'angular-app';

  constructor(
  	private cart_service: CartService,
  	private order_service: OrderService,
    private auth_service: AuthService,
    private state_service: StateService
  ) {

  }

  public get_cart_products_qty() {
  	return this.cart_service.get_products_qty();
  }

  public is_allow_to_order() {
  	return this.order_service.is_allow_to_order();
  }

  public is_user_logged_in() {
    return this.auth_service.check_auth();
  }

  public sign_out() {
    return this.auth_service.sign_out();
  }
  
  public sign_in() {
    this.state_service.go('signin');
  }
}
