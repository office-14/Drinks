import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OrderComponent } from './order.component';
import { AppModule } from '../app.module';
import { OrderService } from '../order.service';
import { MockOrderService } from '../mock-order.service';
import { HttpClientModule } from '@angular/common/http';
import { HttpErrorHandlerService } from '../http-error-handler.service';
import { MessageService } from '../message.service';
import { AuthService } from '../auth/auth.service';
import { of } from 'rxjs';
import { LocalStorageModule } from 'angular-2-local-storage';
import { environment } from '../../environments/environment';
import { StateService } from "@uirouter/core";
import { CartService } from '../cart.service';
import { MockCartService } from '../mock-cart.service';
import { AngularFireAuthModule } from '@angular/fire/auth';
import { AngularFireModule } from '@angular/fire';

describe('OrderComponent', () => {
  let component: OrderComponent;
  let fixture: ComponentFixture<OrderComponent>;
  let cart_service: CartService;
  let order_service: OrderService;

  const message_service = jasmine.createSpyObj('MessageService', ['show_success', 'show_error']);
  const auth_service = jasmine.createSpyObj('AuthService', ['check_auth', 'get_access_token', 'auth_state']);
  const state_service = jasmine.createSpyObj('StateService', ['go']);

  beforeEach(async(() => {
    auth_service.check_auth.and.returnValue(true);
    auth_service.get_access_token.and.returnValue('test_token');
    auth_service.auth_state.and.returnValue(of(true));

    TestBed.configureTestingModule({
      imports: [
        HttpClientModule,
        LocalStorageModule.forRoot({
            prefix: environment.local_storage.prefix,
            storageType: 'localStorage'
        }),
        AngularFireAuthModule,
        AngularFireModule.initializeApp(environment.firebase),
      ],
      providers: [
        { provide: OrderService, useClass: MockOrderService },
        HttpErrorHandlerService,
        { provide: MessageService, useValue: message_service },
        { provide: AuthService, useValue: auth_service },
        { provide: StateService, useValue: state_service },
        { provide: CartService, useClass: MockCartService },
      ],
      declarations: [ OrderComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OrderComponent);
    component = fixture.componentInstance;
    cart_service = TestBed.get(CartService);
    order_service = TestBed.get(OrderService);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('order state should display correctly after order created', () => {
    order_service.create_order(cart_service.get_products()).subscribe(order => true);
    fixture.detectChanges();
    expect(order_service.if_order_exist()).toBeTruthy('order created');

    let order = order_service.get_order();
    expect(order.id).toBe(1, '#get_order returned right id');

    const order_status: HTMLElement = fixture.nativeElement.querySelector('.order__status');
    expect(order_status.textContent).toMatch('COOKING', 'order state displayed correctly');

    order_service.clear_order();
    fixture.detectChanges();
  });

  it('order state should display correctly after order finished', () => {
    order_service.create_order(cart_service.get_products()).subscribe(order => true);
    fixture.detectChanges();
    const order = {
      status_code: 'READY',
      status_name: 'READY'
    };
    order_service.refresh_order_status(order);
    fixture.detectChanges();
    const order_status: HTMLElement = fixture.nativeElement.querySelector('.order__status');
    expect(order_status.textContent).toMatch('READY', 'order state displayed correctly');

    const clear_button: HTMLElement = fixture.nativeElement.querySelector('.order__clear-button');
    expect(clear_button).not.toBe(null, 'clear button was dispayed after order status became ready');

    order_service.clear_order();
    fixture.detectChanges();
  });
});
