import { TestBed, tick, fakeAsync, ComponentFixture, async } from '@angular/core/testing';

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
import { LocalStorageService } from 'angular-2-local-storage';

describe('OrderComponent', () => {
  let component: OrderComponent;
  let fixture: ComponentFixture<OrderComponent>;
  let cart_service: CartService;
  let order_service: OrderService;

  beforeEach(async(() => {
    const auth_service = jasmine.createSpyObj('AuthService', ['get_access_token']);
    const message_service = jasmine.createSpyObj('MessageService', ['show_success', 'show_error']);
    const local_storage_service = jasmine.createSpyObj('LocalStorageService', ['set', 'get']);

    auth_service.get_access_token.and.returnValue('test_token');

    TestBed.configureTestingModule({
      imports: [
        HttpClientModule
      ],
      providers: [
        { provide: OrderService, useClass: MockOrderService },
        HttpErrorHandlerService,
        { provide: AuthService, useValue: auth_service },
        { provide: CartService, useClass: MockCartService },
        { provide: MessageService, useValue: message_service },
        { provide: LocalStorageService, useValue: local_storage_service },
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

  it('order fields should display correctly after order created and finished', fakeAsync(() => {
    order_service.create_order().subscribe();
    fixture.detectChanges();
    const order_number: HTMLElement = fixture.nativeElement.querySelector('h4');
    expect(order_number.textContent).toMatch('ORD-2', 'order number displayed correctly after create');

    const order_status: HTMLElement = fixture.nativeElement.querySelector('.order__status');
    expect(order_status.textContent).toMatch('готовится', 'order state displayed correctly after create');

    const order_comment: HTMLElement = fixture.nativeElement.querySelector('.order__comment');
    expect(order_comment.textContent).toMatch('Test comment', 'order comment displayed correctly after create');

    tick(3000);
    fixture.detectChanges();
    order_status: HTMLElement = fixture.nativeElement.querySelector('.order__status');
    expect(order_status.textContent).toMatch('готов', 'order state displayed correctly after finish');
  }));
});
