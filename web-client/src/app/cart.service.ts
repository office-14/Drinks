import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map, catchError, tap } from 'rxjs/operators';
import { LocalStorageService } from 'angular-2-local-storage';

@Injectable()
export class CartService {
  cart = {
    cart_products: [],
    comment: ''
  };

  constructor(
    private local_storage_service: LocalStorageService
  ) {}

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
  	this.cart.cart_products = [];
    this.cart.comment = '';
    this.local_storage_service.set('cart', this.cart);
  }

  public add_product(cart_product) {
  	let add_new = true;
  	this.cart.cart_products.map(product => {
  		if (product.drink_id == cart_product.drink_id && product.size_id == cart_product.size_id && this.comparing_addins(cart_product.addins, product.addins)) { 
	    	product.qty += cart_product.qty;
	    	add_new = false;
	    }
  	});
  	if (add_new) {
  		this.cart.cart_products.push(cart_product);
  	}
    this.local_storage_service.set('cart', this.cart);
    
    return true;
  }

  get_cart() {
  	return this.cart;
  }

  get_products() {
    return this.cart.cart_products;
  }

  get_comment() {
    return this.cart.comment;
  }

  get_products_qty() {
  	return this.cart.cart_products.reduce(function(prev, cur) 
    {
        return prev + cur.qty;
    }, 0);
  }

  remove_product(index) {
  	if (index > -1) {
      this.cart.cart_products.splice(index, 1);
      this.local_storage_service.set('cart', this.cart);

      return true;
	  }
    return false;
  }

  get_total_price() {
  	return this.cart.cart_products.reduce(function(prev, cur) 
    {
        return prev + cur.qty * cur.price;
    }, 0);
  }

  load_products_from_local_storage() {
    if (this.local_storage_service.get('cart')) {
      this.cart = this.local_storage_service.get('cart');
    }
  }
}
