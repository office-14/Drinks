import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpHeaders, HttpResponse } from '@angular/common/http';
import { AjaxResponse } from "./ajax-response";
import { Observable, of, Subject, throwError } from 'rxjs';
import { map, catchError, delay, tap } from 'rxjs/operators';
import { Order } from './order/order';
import { HttpErrorHandlerService, HandleError } from './http-error-handler.service';
import { AuthService } from './auth/auth.service';
import { CartService } from './cart.service';
import { environment } from '../environments/environment';
import { BaseError } from './base-error';

@Injectable()
export class OrderService {
  _READY_STATUS_ = 'READY';
  _COOKING_STATUS_ = 'COOKING';
  last_order: Order = null;
  order_creating_started = false;

  private handleError: HandleError;
  private post_orders_url = environment.api_urls.post_orders;
  private get_order_url = environment.api_urls.get_order;
  private get_last_order_url = environment.api_urls.get_last_order_url;

  constructor(
    protected http: HttpClient,
    httpErrorHandler: HttpErrorHandlerService,
    protected auth_service: AuthService,
    protected cart_service: CartService
  ) {
    this.handleError = httpErrorHandler.createHandleError('OrderService');
  }

  protected api_get_last_order(): Observable<any> {
    const http_options = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json',
        'Authorization': 'Bearer ' + this.auth_service.get_access_token()
      })
    };
    return this.http.get<AjaxResponse<Order>>(this.get_last_order_url, http_options).pipe(
      catchError(this.handleError('get_last_order')),
      map((ajax_response: AjaxResponse<Order>) => ajax_response.payload)
    )
  }

  protected api_create_order(order_data): Observable<any> {
    const http_options = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json',
        'Authorization': 'Bearer ' + this.auth_service.get_access_token()
      })
    };

    return this.http.post<AjaxResponse<any>>(this.post_orders_url, order_data, http_options).pipe(
      catchError(this.handleError('create_order')),
      map((ajax_response: AjaxResponse<any>) => ajax_response.payload)
    );
  }

  protected refresh_order_status(status_code, status_name) {
    if (this.last_order.status_code == this._COOKING_STATUS_ && status_code == this._READY_STATUS_) {
      this.last_order.became_ready = true;
    }
    this.last_order.status_code = status_code;
    this.last_order.status_name = status_name;
  }

  protected check_order_finishing() {
    if (this.is_last_order_exist() && this.is_order_status_cooking()) {
      this.api_get_last_order()
      .subscribe(order => {
        if (order.status_code == this._READY_STATUS_) {
          this.refresh_order_status(order.status_code, order.status_name);
        } else {
          setTimeout(() => {this.check_order_finishing();}, 3000);
        }
      });
    }
  }

  protected set_order(order) {
    this.last_order = order;
    if (this.is_order_status_cooking()) {
      this.check_order_finishing();
    }
  }

  load_last_order(): Observable<Order> {
    return this.api_get_last_order()
      .pipe(
        tap(order => {
          if (order !== null) {
            this.set_order(order);
          }
        })
      );
  }

  create_order(): Observable<any> {
    let cart_products = this.cart_service.get_products();
    let comment = this.cart_service.get_comment();
    if (cart_products.length == 0) {
      const base_error: BaseError = {
        'error_type': 'other',
        'text': 'В корзине нет товаров'
      };
      return throwError(base_error);
    }

    let drinks = [];
    for (let product of cart_products) {
      drinks.push({
        "drink_id": product.drink_id,
        "size_id": product.size_id,
        "add-ins": product.addins.map(addin => addin.id),
        "count": product.qty
      });
    }
    let order_data = {
      "drinks": drinks,
      "comment": comment
    }

    return this.api_create_order(order_data).pipe(
      tap(order => {
          order['drinks'] = drinks;
          order['comment'] = comment;
          this.set_order(order);
      }),
      tap(resp => this.cart_service.clear_cart())
    );
  }

  is_allow_to_order() {
  	if (this.is_last_order_exist() && this.is_order_status_cooking()) {
  		return false;
  	}
  	return true;
  }

  is_order_status_ready() {
    return (this.last_order.status_code == this._READY_STATUS_);
  }

  is_order_status_cooking() {
    return (this.last_order.status_code == this._COOKING_STATUS_);
  }

  is_last_order_exist() {
    if (this.last_order) {
      return true;
    }
    return false;
  }

  get_order() {
    return this.last_order;
  }

  remove_order() {
  	this.last_order = null;
  }

  is_order_became_ready() {
    return this.last_order.became_ready;
  }
}
