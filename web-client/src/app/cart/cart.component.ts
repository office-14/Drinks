import { Component, OnInit } from '@angular/core';
import { CartService } from '../cart.service';
import { OrderService } from '../order.service';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {
  products = [];

  constructor(
  	private cart_service: CartService,
  	private order_service: OrderService
  ) { }

  ngOnInit(): void {
  	this.products = this.cart_service.get_products();
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
  	if (!this.if_order_exist()) {
  		this.order_service.create_order(this.products).subscribe(data => {
  			let order = data.payload;
  			order['products'] = this.products;
		    this.order_service.set_order(order);
		    this.cart_service.clear_cart();
		    this.products = this.cart_service.get_products();
		});
  	}
  }

}