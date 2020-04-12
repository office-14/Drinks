import { TestBed } from '@angular/core/testing';

import { HttpErrorHandlerService } from './http-error-handler.service';
import { AuthService } from './auth/auth.service';
import { LocalStorageService } from 'angular-2-local-storage';
import { AngularFireAuth } from  "@angular/fire/auth";
import { StateService } from "@uirouter/core";

import { OrderService } from './order.service';

import { HttpClientModule } from '@angular/common/http';

import { MessageService } from './message.service';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { AngularFireAuthModule } from '@angular/fire/auth';
import { AngularFireModule } from '@angular/fire';
import { environment } from '../environments/environment';
import { LocalStorageModule } from 'angular-2-local-storage';
import { HttpClient, HttpParams, HttpHeaders, HttpResponse } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';

export class MockOrderService extends OrderService{
  constructor(
    protected http: HttpClient,
    httpErrorHandler: HttpErrorHandlerService,
    protected auth_service: AuthService,
    protected local_storage_service: LocalStorageService,
    protected state_service: StateService
  ) {
     super(http, httpErrorHandler, auth_service, local_storage_service, state_service);
  }
}


describe('OrderService', () => {
  let service: OrderService;
  let http_error_handler_service: HttpErrorHandlerService;

  beforeEach(() => {
    TestBed.configureTestingModule({
    	imports: [
    		AppRoutingModule,
	    	HttpClientModule,
	    	MatSnackBarModule,
	    	AngularFireModule.initializeApp(environment.firebase),
	    	AngularFireAuthModule,
	    	LocalStorageModule.forRoot({
		        prefix: environment.local_storage.prefix,
		        storageType: 'localStorage'
		    })
    	],
    	providers: [
			  HttpErrorHandlerService,
			  AuthService,
    		MessageService,
    		{
          provide: OrderService,
          useFactory: (http: HttpClient, httpErrorHandler: HttpErrorHandlerService, auth_service: AuthService, local_storage_service: LocalStorageService, state_service: StateService) => {
            return new MockOrderService(http, httpErrorHandler, auth_service, local_storage_service, state_service);
          },
          deps: [HttpClient, HttpErrorHandlerService, AuthService, LocalStorageService, StateService]
        }
    	]
    });
    service = TestBed.inject(OrderService);
    http_error_handler_service = TestBed.inject(HttpErrorHandlerService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
