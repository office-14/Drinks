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
  private drinks: Observable<Drink[]>;
  private addins: Observable<Addin[]>;

  constructor(
    private http: HttpClient,
    httpErrorHandler: HttpErrorHandler) {
    this.handleError = httpErrorHandler.createHandleError('DrinksService');
    this.drinks = this.loadDrinks();
    this.addins = this.loadAddins();
  }

  loadDrinks (): Observable<Drink[]> {
    return this.http.get<AjaxResponse<Drink[]>>(this.drinksUrl)
      .pipe(
        catchError(this.handleError('loadDrinks')),
        map((ajax_response: AjaxResponse<Drink[]>) => ajax_response.payload)
      );
  }

  loadAddins (): Observable<Addin[]> {
    return this.http.get<AjaxResponse<Addin[]>>(this.addinsUrl).pipe(
      catchError(this.handleError('loadAddins')),
      map((ajax_response: AjaxResponse<Addin[]>) => ajax_response.payload)
    );
  }

  getSizes (drink_id: string):Observable<Size[]> {
    return this.http.get<AjaxResponse<Size[]>>(`http://localhost:5000/api/drinks/${drink_id}/sizes`)
    .pipe(
      catchError(this.handleError('getSizes')),
      map((ajax_response: AjaxResponse<Size[]>) => ajax_response.payload)
    );
  }

  getDrinks (): Observable<Drink[]> {
    return this.drinks;
  }

  getAddins (): Observable<Addin[]> {
    return this.addins;
  }

  getDrink(id: number | string): Observable<Drink> {
    return this.getDrinks()
    .pipe(
      map((drinks: Drink[]) => drinks.find(drink => drink.id === +id))
    );
  }
}
