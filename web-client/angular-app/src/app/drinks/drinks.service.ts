import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { map, catchError } from 'rxjs/operators';

import { Drink } from "./drink";
import { Addin } from "./addin";
import { Size } from "./size";
import { AjaxResponse } from "../ajax-response";
import { HttpErrorHandler, HandleError } from '../http-error-handler.service';

@Injectable({
  providedIn: 'root'
})
export class DrinksService {
  drinksUrl = 'http://localhost:5000/api/drinks';
  addinsUrl = 'http://localhost:5000/api/add-ins';

  private handleError: HandleError;
  private drinks: Observable<AjaxResponse<Drink[]>>;
  private addins: Observable<AjaxResponse<Addin[]>>;

  constructor(
    private http: HttpClient,
    httpErrorHandler: HttpErrorHandler) {
    this.handleError = httpErrorHandler.createHandleError('DrinksService');
    this.drinks = this.loadDrinks();
    this.addins = this.loadAddins();
  }

  loadDrinks (): Observable<AjaxResponse<Drink[]>> {
    return this.http.get<AjaxResponse<Drink[]>>(this.drinksUrl);
  }

  loadAddins (): Observable<AjaxResponse<Addin[]>> {
    return this.http.get<AjaxResponse<Addin[]>>(this.addinsUrl);
  }

  getSizes (drink_id: string): Observable<AjaxResponse<Size[]>> {
    return this.http.get<AjaxResponse<Size[]>>(`http://localhost:5000/api/drinks/${drink_id}/sizes`);
  }

  getDrinks (): Observable<AjaxResponse<Drink[]>> {
    return this.drinks;
  }

  getAddins (): Observable<AjaxResponse<Addin[]>> {
    return this.addins;
  }

  getDrink(id: number | string) {
    return this.getDrinks().pipe(
      map((drinks: AjaxResponse<Drink[]>) => drinks.payload.find(drink => drink.id === +id))
    );
  }
}
