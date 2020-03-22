import { Injectable } from '@angular/core';
import { MessageService } from './message.service';
import { OrderService } from './order.service';
import { AngularFireAuth } from  "@angular/fire/auth";
import { StateService } from "@uirouter/core";
import { Observable } from 'rxjs';
import { map, catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class CartService {

  cart_products = [];
  private order_creating_in_process: boolean = false;

  constructor(
    private messageService: MessageService,
    private order_service: OrderService,
    private  afAuth:  AngularFireAuth,
    private state_service: StateService
  ) {
  	this.cart_products = [];

    this.afAuth.authState.subscribe(user => {
        if (user) {
          if (this.order_creating_started()) {
            this.create_order();
          }
        }
      })
  }

  private comparing_addins(addins1, addins2) {
  	if (addins1.length !== addins2.length) {
  		return false;
  	}
  	if (JSON.stringify(addins1.map(addin => addin.id).sort()) === JSON.stringify(addins2.map(addin => addin.id).sort())) {
	    return true;
	}
  	return false;
  }

  public clear_cart() {
  	this.cart_products = [];
  }

  public add_product(cart_product) {
  	let add_new = true;
  	this.cart_products.map(product => {
  		if (product.drink_id == cart_product.drink_id && product.size_id == cart_product.size_id && this.comparing_addins(cart_product.addins, product.addins)) { 
	    	product.qty += cart_product.qty;
	    	add_new = false;
	    }
  	});
  	if (add_new) {
  		this.cart_products.push(cart_product);
  	}
    this.messageService.show_success('Товар успешно добавлен в корзину!');
  }

  get_products() {
  	return this.cart_products;
  }

  get_products_qty() {
  	return this.cart_products.reduce(function(prev, cur) 
    {
        return prev + cur.qty;
    }, 0);
  }

  remove_product(index) {
  	if (index > -1) {
	   this.cart_products.splice(index, 1);
     this.messageService.show_success('Товар удалён из корзины!');
	  }
  }

  get_total_price() {
  	return this.cart_products.reduce(function(prev, cur) 
    {
        return prev + cur.qty * cur.price;
    }, 0);
  }

  start_order_creating() {
    this.order_creating_in_process = true;
  }

  order_creating_started() {
    return this.order_creating_in_process;
  }

  create_order() {
    this.order_service.create_order(this.cart_products).subscribe(
     (data: any) => {
        let order = data;
        order['products'] = this.cart_products;
        this.order_service.set_order(order);
        this.order_service.start_longpolling_order_finishing();
        this.clear_cart();
        this.cart_products = this.get_products();
        if (this.order_creating_started()) {
          this.state_service.go('order');
          this.order_creating_in_process = false;
        }
        this.messageService.show_success('Заказ оформлен!');
      });    
  }
}
