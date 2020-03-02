import { Component } from '@angular/core';
import { CartService } from './cart.service';
import { OrderService } from './order.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'angular-app';

  constructor(
  	private cart_service: CartService,
  	private order_service: OrderService
  ) {

  }

  public get_cart_products_qty() {
  	return this.cart_service.get_products_qty();
  }

  public if_order_exist() {
  	return this.order_service.if_order_exist()
  }
}
