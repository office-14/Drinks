import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpHeaders, HttpResponse } from '@angular/common/http';
import { AjaxResponse } from "./ajax-response";
import { Observable, of } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { Order } from './order/order';
import { HttpErrorHandler, HandleError } from './http-error-handler.service';
import { AuthService } from './auth/auth.service';

@Injectable({
  providedIn: 'root'
})
export class OrderService {
  order: Order;
  private handleError: HandleError;

  constructor(
    private http: HttpClient,
    httpErrorHandler: HttpErrorHandler,
    private auth_service: AuthService
  ) {
    this.handleError = httpErrorHandler.createHandleError('DrinksService');
  }

  if_order_exist() {
  	if (this.order) {
  		return true;
  	}
  	return false;
  }

  create_order(cart_products): Observable<any> {
  	let order_products = [];
  	for (let product of cart_products) {
  	  for (let i = product.qty; i > 0; i--) {
  			order_products.push({
  				"drink_id": product.drink_id,
  				"size_id": product.size_id,
  				"add-ins": product.addins.map(addin => addin.id)
  			});
  		}
  	}

    const http_options = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json',
        'Authorization': 'Bearer ' + this.auth_service.get_access_token()
      })
    };

  	return this.http.post<AjaxResponse<any>>('http://localhost:5000/api/orders', { drinks: order_products }, http_options).pipe(
        catchError(this.handleError('create_order')),
        map((ajax_response: AjaxResponse<any>) => ajax_response.payload)
      );;
  }

  set_order(order) {
  	this.order = order;
  }

  clear_order() {
  	this.order = null;
  }

  get_order() {
  	return this.order;
  }

  finish_order(): Observable<any> {
  	return this.http.post(`http://localhost:5000/api/orders/${this.order['id']}/finish`, {}, {
      headers: new HttpHeaders({
        'Content-Type':  'application/json',
        'Authorization': 'Bearer ' + this.auth_service.get_access_token()
      }),
      observe: 'response'
    }).pipe(
      catchError(this.handleError('finish_order'))
    );
  }
}
