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

  it('should create', () => {
    let component: OrderComponent;
    let fixture: ComponentFixture<OrderComponent>;
    fixture = TestBed.createComponent(OrderComponent);
    component = fixture.componentInstance;
    expect(component).toBeTruthy();
  });

  it('order fields should display correctly after loaded last order and finished', fakeAsync(() => {
    let component: OrderComponent;
    let fixture: ComponentFixture<OrderComponent>;
    fixture = TestBed.createComponent(OrderComponent);
    component = fixture.componentInstance;
    tick(3000);
    fixture.detectChanges();
    const order_number: HTMLElement = fixture.nativeElement.querySelector('h4');
    expect(order_number.textContent).toMatch('ORD-2', 'order number displayed correctly after loaded last order');

    let order_status: HTMLElement = fixture.nativeElement.querySelector('.order__status');
    expect(order_status.textContent).toMatch('Заказ готовится', 'order state displayed correctly after loaded last order');

    const order_comment: HTMLElement = fixture.nativeElement.querySelector('.order__comment');
    expect(order_comment.textContent).toMatch('Test comment', 'order comment displayed correctly after loaded last order');

    const order_created: HTMLElement = fixture.nativeElement.querySelector('.order__created');
    expect(order_created.textContent).toMatch('апреля 2020 г.', 'order created displayed correctly after loaded last order');
    
    tick(3000);
    fixture.detectChanges();
    let order_status2: HTMLElement = fixture.nativeElement.querySelector('.order__status');
    expect(order_status2.textContent).toEqual('Заказ готов', 'order state displayed correctly after finished last order');
  }));
});
