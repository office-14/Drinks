import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpHeaders, HttpResponse } from '@angular/common/http';
import { AjaxResponse } from "./ajax-response";
import { Observable, of, Subject, throwError } from 'rxjs';
import { map, catchError, delay, tap } from 'rxjs/operators';
import { Order } from './order/order';
import { HttpErrorHandlerService, HandleError } from './http-error-handler.service';
import { AuthService } from './auth/auth.service';
import { CartService } from './cart.service';
import { LocalStorageService } from 'angular-2-local-storage';
import { StateService } from "@uirouter/core";
import { environment } from '../environments/environment';
import { MessageService } from './message.service';
import { BaseError } from './base-error';

@Injectable()
export class OrderService {
  _READY_STATUS_ = 'READY';
  _COOKING_STATUS_ = 'COOKING';
  last_order: Order = null;

  private handleError: HandleError;
  private post_orders_url = environment.api_urls.post_orders;
  private get_order_url = environment.api_urls.get_order;
  private get_last_order_url = environment.api_urls.get_last_order_url;

  constructor(
    private message_service: MessageService,
    protected http: HttpClient,
    httpErrorHandler: HttpErrorHandlerService,
    protected auth_service: AuthService,
    protected local_storage_service: LocalStorageService,
    protected state_service: StateService,
    protected cart_service: CartService
  ) {
    this.handleError = httpErrorHandler.createHandleError('OrderService');
    this.auth_service.auth_state().subscribe(user => {
      if (user) {

        if (this.cart_service.order_creating_started) {
          this.create_order();
        }
        if (this.is_allow_to_order()) {
          
        } else {
          if (this.cart_service.order_creating_started) {
            this.message_service.show_error('Не возможно создать заказ, так как есть текущий!');
          }
        }
        this.cart_service.order_creating_started = false;
      } else {
        this.clear_order();
        this.state_service.go('drinks');
      }
    });
  }

  is_allow_to_order() {
  	if (this.last_order && this.last_order.status_code == this._COOKING_STATUS_) {
  		return true;
  	}
  	return false;
  }

  is_order_status_ready() {
    if (this.last_order && this.last_order.status_code == this._READY_STATUS_) {
      return true;
    }
    return false;
  }

  is_order_status_cooking() {
    if (this.last_order && this.last_order.status_code == this._COOKING_STATUS_) {
      return true;
    }
    return false;
  }

  get_last_order(): Observable<Order> {
    return this.http.get<AjaxResponse<Order>>(this.get_last_order_url).pipe(
      catchError(this.handleError('get_last_order')),
      map((ajax_response: AjaxResponse<Order>) => ajax_response.payload)
    );
  }

  create_order(): Observable<any> {
    let cart_products = this.cart_service.get_products();
    if (cart_products.length == 0) {
      const base_error: BaseError = {
        'error_type': 'other',
        'text': 'В корзине нет товаров'
      };
      return throwError(base_error);
    }
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

  	return this.http.post<AjaxResponse<any>>(this.post_orders_url, { drinks: order_products }, http_options).pipe(
      catchError(this.handleError('create_order')),
      map((ajax_response: AjaxResponse<any>) => ajax_response.payload),
      map(resp => {
        resp['products'] = cart_products;
        this.set_order(resp);
      }) 
    );
  }

  refresh_order_status(order) {
    this.last_order.status_code = order.status_code;
    this.last_order.status_name = order.status_name;
    this.local_storage_service.set('order', this.last_order);
  }

  start_longpolling_order_finishing() {
    this.check_order_finishing();
  }

  check_order_finishing() {
    if (this.is_allow_to_order() && this.is_order_status_cooking()) {

      if (this.auth_service.get_access_token() == false) {
        return false;
      }
      const http_options = {
        headers: new HttpHeaders({
          'Content-Type':  'application/json',
          'Authorization': 'Bearer ' + this.auth_service.get_access_token()
        })
      };


      return this.http.get<AjaxResponse<any>>(this.get_order_url.replace(/\{order_id\}/gi, this.last_order.id.toString()), http_options)
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
  	this.last_order = order;
    this.local_storage_service.set('order', this.last_order);
    this.start_longpolling_order_finishing();
  }

  clear_order() {
  	this.last_order = null;
    if (this.auth_service.get_access_token()) {
      this.local_storage_service.remove('order');
    }
  }

  get_order() {
  	return this.last_order;
  }
}
