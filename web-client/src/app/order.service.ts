import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpHeaders, HttpResponse } from '@angular/common/http';
import { AjaxResponse } from "./ajax-response";
import { Observable, of, Subject } from 'rxjs';
import { map, catchError, delay, tap } from 'rxjs/operators';
import { Order } from './order/order';
import { HttpErrorHandlerService, HandleError } from './http-error-handler.service';
import { AuthService } from './auth/auth.service';
import { LocalStorageService } from 'angular-2-local-storage';
import { StateService } from "@uirouter/core";
import { environment } from '../environments/environment';


@Injectable()
export class OrderService {
  _READY_STATUS_ = 'READY';
  _COOKING_STATUS_ = 'COOKING';
  order: Order;

  private handleError: HandleError;
  private stored_order: Subject<any>;
  private post_orders_url = environment.api_urls.post_orders;
  private get_order_url = environment.api_urls.get_order;

  constructor(
    protected http: HttpClient,
    httpErrorHandler: HttpErrorHandlerService,
    protected auth_service: AuthService,
    protected local_storage_service: LocalStorageService,
    protected state_service: StateService
  ) {
    this.stored_order = new Subject;
    this.handleError = httpErrorHandler.createHandleError('DrinksService');
    this.auth_service.auth_state().subscribe(user => {
      if (user) {
        if (this.local_storage_service.get('order')) {
          this.set_order(this.local_storage_service.get('order'));
        }
        this.stored_order.next(this.if_order_exist());
      } else {
        this.clear_order();

        this.state_service.go('drinks');
      }
    });
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

  	return this.http.post<AjaxResponse<any>>(this.post_orders_url, { drinks: order_products }, http_options).pipe(
        catchError(this.handleError('create_order')),
        map((ajax_response: AjaxResponse<any>) => ajax_response.payload),
        tap(resp => {
          resp['products'] = cart_products;
          this.set_order(resp);
        }) 
      );
  }

  refresh_order_status(order) {
    this.order.status_code = order.status_code;
    this.order.status_name = order.status_name;
    this.local_storage_service.set('order', this.order);
  }

  start_longpolling_order_finishing() {
    this.check_order_finishing();
  }

  check_order_finishing() {
    if (this.if_order_exist() && this.is_order_status_cooking()) {

      if (this.auth_service.get_access_token() == false) {
        return false;
      }
      const http_options = {
        headers: new HttpHeaders({
          'Content-Type':  'application/json',
          'Authorization': 'Bearer ' + this.auth_service.get_access_token()
        })
      };


      return this.http.get<AjaxResponse<any>>(this.get_order_url.replace(/\{order_id\}/gi, this.order.id.toString()), http_options)
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
    this.local_storage_service.set('order', this.order);
    this.start_longpolling_order_finishing();
  }

  clear_order() {
  	this.order = null;
    if (this.auth_service.get_access_token()) {
      this.local_storage_service.remove('order');
    }
  }

  get_order() {
  	return this.order;
  }

  get_stored_order(): Subject<any> {
    return this.stored_order;
  }
}
