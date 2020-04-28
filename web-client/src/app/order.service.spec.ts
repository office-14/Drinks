import { TestBed, tick, fakeAsync } from '@angular/core/testing';

import { HttpErrorHandlerService } from './http-error-handler.service';
import { AuthService } from './auth/auth.service';
import { LocalStorageService } from 'angular-2-local-storage';
import { AngularFireAuth } from  "@angular/fire/auth";
import { StateService } from "@uirouter/core";

import { OrderService } from './order.service';
import { MockOrderService } from './mock-order.service';

import { HttpClientModule } from '@angular/common/http';
import { AngularFireAuthModule } from '@angular/fire/auth';
import { AngularFireModule } from '@angular/fire';
import { environment } from '../environments/environment';
import { HttpClient, HttpParams, HttpHeaders, HttpResponse } from '@angular/common/http';
import { CartService } from './cart.service';
import { MockCartService } from './mock-cart.service';
import { MessageService } from './message.service';

describe('OrderService', () => {
  let service: OrderService;
  let http_error_handler_service: HttpErrorHandlerService;

  beforeEach(() => {
    const message_service = jasmine.createSpyObj('MessageService', ['']);
    const local_storage_service = jasmine.createSpyObj('LocalStorageService', ['set', 'get']);
    TestBed.configureTestingModule({
    	imports: [
	    	HttpClientModule,
	    	AngularFireModule.initializeApp(environment.firebase),
	    	AngularFireAuthModule
    	],
    	providers: [
			  HttpErrorHandlerService,
			  AuthService,
        { provide: CartService, useClass: MockCartService },
        { provide: MessageService, useValue: message_service },
        { provide: LocalStorageService, useValue: local_storage_service },
        { provide: OrderService, useClass: MockOrderService }
    	]
    });
    service = TestBed.inject(OrderService);
    http_error_handler_service = TestBed.inject(HttpErrorHandlerService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('#load_last_order_status should return last order ', () => {
    service.load_last_order();
    let order = service.get_order();
    expect(order.id).toBe(2);
  });

  it('#load_last_order should return last order ', () => {
    service.load_last_order();
    let order = service.get_order();
    expect(order.id).toBe(2);
  });

  it('#create should set new order ', () => {
    service.create_order().subscribe();
    let order = service.get_order();
    expect(order.id).toBe(2);
  });

  it('#check_order_finishing should work correctly', fakeAsync(() => {
    service.create_order().subscribe();
    let order = service.get_order();
    expect(order.status_code).toBe('COOKING', 'order status_code is COOKING');
    tick(3000);
    order = service.get_order();
    expect(order.status_code).toBe('READY', 'order status_code is READY after 3 seconds');
  }));
});
