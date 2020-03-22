import { Injectable } from '@angular/core';
import { MessageService } from './message.service';
import { OrderService } from './order.service';
import { AngularFireAuth } from  "@angular/fire/auth";
import { StateService } from "@uirouter/core";
import { Observable } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { LocalStorageService } from 'angular-2-local-storage';

@Injectable({
  providedIn: 'root'
})
export class CartService {

  cart_products = [];
  order_creating_started = false;

  constructor(
    private message_serivce: MessageService,
    private order_service: OrderService,
    private afAuth: AngularFireAuth,
    private state_service: StateService,
    private local_storage_service: LocalStorageService
  ) {
  	this.cart_products = [];

    if (this.local_storage_service.get('cart_products')) {
      this.cart_products = this.local_storage_service.get('cart_products');
    }

    this.afAuth.authState.subscribe(user => {
      if (user) {
        if (this.local_storage_service.get('order')) {
          this.order_service.set_order(this.local_storage_service.get('order'));
          if (this.order_creating_started) {
            this.message_serivce.show_error('Не аозможно создать заказ, так как есть текущий!');
          }
        } else {
          if (this.order_creating_started) {
            this.create_order();
          }
        }
        this.order_creating_started = false;
      }
    });
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

    this.local_storage_service.set('cart_products', this.cart_products);
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
    this.local_storage_service.set('cart_products', this.cart_products);
    this.message_serivce.show_success('Товар успешно добавлен в корзину!');
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
      this.local_storage_service.set('cart_products', this.cart_products);
      this.message_serivce.show_success('Товар удалён из корзины!');
	  }
  }

  get_total_price() {
  	return this.cart_products.reduce(function(prev, cur) 
    {
        return prev + cur.qty * cur.price;
    }, 0);
  }

  create_order() {
    this.order_service.create_order(this.cart_products).subscribe(
     (data: any) => {
        this.clear_cart();
        this.state_service.go('order');
        this.message_serivce.show_success('Заказ оформлен!');
      });    
  }
}
