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
  comment = '';

  constructor(
  	private cart_service: CartService,
  	private order_service: OrderService,
    private message_service: MessageService,
    private auth_service: AuthService,
    private state_service: StateService
  ) { }

  ngOnInit(): void {}

  change_product_qty(product) {
  	if (product.qty < 1) {
  		product.qty = 1;
  	}
  }

  remove_from_cart(index) {
    if (this.cart_service.remove_product(index)) {
      this.message_service.show_success('Товар удалён из корзины!');
    }
  }

  get_total_price() {
  	return this.cart_service.get_total_price();
  }

  is_allow_to_order() {
  	return this.order_service.is_allow_to_order()
  }

  create_order() {
      if (this.auth_service.check_auth()) {
        if (this.order_service.is_allow_to_order()) {
          this.order_service.create_order().subscribe(
            resp => {
              this.state_service.go('order');
              this.message_service.show_success('Заказ оформлен!');
            },
            err => {
              if (err.error_type != 'http_error') {
                this.message_service.show_error('Нельзя создать новый заказ! ' + err.text);
              }
            }
          );
        } else {
          this.message_service.show_error('Нельзя создать новый заказ! У вас есть текущий заказ, который ещё не готов.');
        }
      } else {
        this.state_service.go('signin', {order_creating_started: true});
      }
  }

  get_cart() {
    return this.cart_service.get_cart();
  }

  get_products() {
    return this.cart_service.get_products();
  }

  get_products_qty(): number {
    return this.cart_service.get_products_qty();
  }

  minus_product_qty(product) {
    if (product.qty > 1) {
      product.qty -= 1;
    }
  }

}