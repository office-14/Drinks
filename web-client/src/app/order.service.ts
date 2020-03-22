import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpHeaders, HttpResponse } from '@angular/common/http';
import { AjaxResponse } from "./ajax-response";
import { Observable, of } from 'rxjs';
import { map, catchError, startWith, switchMap, takeUntil, filter, delay } from 'rxjs/operators';
import { Order } from './order/order';
import { HttpErrorHandler, HandleError } from './http-error-handler.service';
import { AuthService } from './auth/auth.service';
import {interval} from "rxjs/internal/observable/interval";


@Injectable({
  providedIn: 'root'
})
export class OrderService {
  _READY_STATUS_ = 'READY';
  _COOKING_STATUS_ = 'COOKING';
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

  is_order_status_ready() {
    if (this.order && this.order.status_code == this._READY_STATUS_) {
      return true;
    }
    return false;
  }

  is_order_status_cooking() {
    if (this.order && this.order.status_code == this._COOKING_STATUS_) {
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
      );
  }

  refresh_order_status(order) {
    this.order.status_code = order.status_code;
    this.order.status_name = order.status_name;
  }

  start_longpolling_order_finishing() {
    this.check_order_finishing();
  }

  check_order_finishing() {
    if (this.if_order_exist() && this.is_order_status_cooking()) {
      const http_options = {
        headers: new HttpHeaders({
          'Content-Type':  'application/json',
          'Authorization': 'Bearer ' + this.auth_service.get_access_token()
        })
      };
      return this.http.get<AjaxResponse<any>>(`http://localhost:5000/api/orders/${this.order.id}`, http_options)
        .pipe(
          catchError(this.handleError('check_order_finishing')),
          delay(3000),
          map((ajax_response: AjaxResponse<any>) => ajax_response.payload)
        )
        .subscribe(res => {
          if (res.status_code == this._READY_STATUS_) {
            this.refresh_order_status(res);
          } else {
            this.check_order_finishing();
          }
        });
    }
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
}
