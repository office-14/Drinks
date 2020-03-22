import { Component, OnInit } from '@angular/core';
import { CartService } from '../cart.service';
import { OrderService } from '../order.service';
import { MessageService } from '../message.service';
import { AuthService } from '../auth/auth.service';
import { StateService } from "@uirouter/core";

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {
  constructor(
  	private cart_service: CartService,
  	private order_service: OrderService,
    private message_service: MessageService,
    private auth_service: AuthService,
    private state_service: StateService
  ) { }

  ngOnInit(): void {
  	this.get_products();
  }

  change_product_qty(product) {
  	if (product.qty < 1) {
  		product.qty = 1;
  	}
  }

  remove_from_cart(index) {
  	this.cart_service.remove_product(index);
  }

  get_total_price() {
  	return this.cart_service.get_total_price();
  }

  if_order_exist() {
  	return this.order_service.if_order_exist()
  }

  create_order() {
      if (this.auth_service.check_auth()) {
        if (!this.if_order_exist()) {
          this.cart_service.create_order();
        } else {
          this.message_service.show_error('Нельзя создать новый заказ, пока есть текущий!');
        }
      } else {
        this.state_service.go('signin', {order_creating_started: true});
      }
  }

  get_products() {
    return this.cart_service.get_products();
  }

  get_products_qty(): number {
    return this.cart_service.get_products_qty();
  }

}