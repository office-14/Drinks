import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { map, catchError, tap } from 'rxjs/operators';

import { Drink } from "./drink";
import { Addin } from "./addin";
import { Size } from "./size";
import { AjaxResponse } from "../ajax-response";
import { HttpErrorHandlerService, HandleError } from '../http-error-handler.service';
import { DraftCartProduct } from "./draft-cart-product";

@Injectable()
export class DrinksService {
  drinksUrl = 'http://localhost:5000/api/drinks';
  addinsUrl = 'http://localhost:5000/api/add-ins';

  private handleError: HandleError;
  protected drinks: Drink[] = [];
  protected draft_cart_product: DraftCartProduct;

  constructor(
    protected http: HttpClient,
    httpErrorHandler: HttpErrorHandlerService
  ) {
    this.handleError = httpErrorHandler.createHandleError('DrinksService');
    this.draft_cart_product = {
      size: null,
      addins: [],
      qty: 1,
      drink_id: 0
    }
  }

  loadDrinks () {
    this.http.get<AjaxResponse<Drink[]>>(this.drinksUrl)
      .pipe(
        catchError(this.handleError('loadDrinks')),
        map((ajax_response: AjaxResponse<Drink[]>) => ajax_response.payload)
      )
      .subscribe(drinks => this.drinks = drinks);
  }

  loadAddins () {
    this.http.get<AjaxResponse<Addin[]>>(this.addinsUrl).pipe(
      catchError(this.handleError('loadAddins')),
      map((ajax_response: AjaxResponse<Addin[]>) => ajax_response.payload)
    )
    .subscribe(addins => this.draft_cart_product.addins = addins);
  }

  getDraftCartProduct() {
    return this.draft_cart_product;
  }

  getSizes (drink_id: number | string):Observable<Size[]> {
    return this.http.get<AjaxResponse<Size[]>>(`http://localhost:5000/api/drinks/${drink_id}/sizes`)
    .pipe(
      catchError(this.handleError('getSizes')),
      map((ajax_response: AjaxResponse<Size[]>) => ajax_response.payload),
      tap(sizes => this.draft_cart_product.size = sizes[0])
    )
  }

  getDrinks (): Drink[] {
    return this.drinks;
  }

  getDrink(id: number | string): Drink {
    return this.drinks.find(drink => drink.id === +id);
  }
}
