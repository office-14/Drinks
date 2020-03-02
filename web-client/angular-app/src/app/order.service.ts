import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { AjaxResponse } from "./ajax-response";
import { Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OrderService {
  order = {};

  constructor(private http: HttpClient) { }

  if_order_exist() {
  	if (this.order.hasOwnProperty('id')) {
  		return true;
  	}
  	return false;
  }

  create_order(cart_products): Observable<AjaxResponse<any>> {
  	let order_products = cart_products.map(function(product) {
	  return {
	  	"drink_id": product.drink_id,
	  	"size_id": product.size_id,
	  	"add-ins": product.addins.map(addin => addin.id)
	  };
	});

  	return this.http.post<AjaxResponse<any>>('http://localhost:5000/api/orders', { drinks: order_products });
  }

  set_order(order) {
  	this.order = order;
  }

  clear_order() {
  	this.order = {};
  }

  get_order() {
  	return this.order;
  }

  finish_order(): Observable<any> {
  	return this.http.post<any>(`http://localhost:5000/api/orders/${this.order['id']}/finish`, {}, {
      observe: 'response'
    });
  }
}
