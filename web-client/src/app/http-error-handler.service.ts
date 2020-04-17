import { Injectable } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';

import { Observable, of, throwError } from 'rxjs';

import { MessageService } from './message.service';
import { BaseError } from './base-error';

export type HandleError =
  <T> (operation?: string, result?: T) => (error: HttpErrorResponse) => Observable<T>;

/** Handles HttpClient errors */
@Injectable()
export class HttpErrorHandlerService {
  constructor(private messageService: MessageService) { }

  createHandleError = (serviceName = '') => <T>
    (operation = 'operation', result = {} as T) => this.handleError(serviceName, operation, result);

  /**
   * Returns a function that handles Http operation failures.
   * This error handler lets the app continue to run as if no error occurred.
   * @param serviceName = name of the data service that attempted the operation
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
  handleError<T> (serviceName = '', operation = 'operation', result = {} as T) {

    return (error: HttpErrorResponse): Observable<T> => {
      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      let error_title = 'Неизвестная ошибка';
      if (error.status == 0) {
        error_title = 'Не установлено соединение с сервером';
      }
      else if(error.status == 404) {
        error_title = 'Сервер не нашёл данных ддя ответа';
      }
      else if(error.status == 500) {
        error_title = error.error.title;
      }

      const message = (error.error instanceof ErrorEvent) ?
        error.error.message :
       `Сервер вернул код ${error.status} с описанием ошибки "${error_title}"`;

      this.messageService.show_error(`${serviceName}: ${operation} ошибка: ${message}`);
      let base_error: BaseError= {
        'error_type': 'http_error'
      };

      return throwError(base_error);
    };

  }
}