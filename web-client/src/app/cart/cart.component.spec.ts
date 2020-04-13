import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { CartComponent } from './cart.component';
import { OrderService } from '../order.service';
import { CartService } from '../cart.service';
import { MessageService } from '../message.service';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { HttpClientModule } from '@angular/common/http';
import { HttpErrorHandlerService } from '../http-error-handler.service';
import { AuthService } from '../auth/auth.service';
import { AngularFireAuthModule } from '@angular/fire/auth';
import { AngularFireModule } from '@angular/fire';
import { environment } from '../../environments/environment';
import { LocalStorageModule } from 'angular-2-local-storage';
import { AppRoutingModule } from '../app-routing.module';
import { MockOrderService } from '../mock-order.service';
import { MockCartService } from '../mock-cart.service';
import { FormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { StateService } from "@uirouter/core";
import { of } from 'rxjs';

describe('CartComponent', () => {
  let component: CartComponent;
  let fixture: ComponentFixture<CartComponent>;
  let cart_service: CartService;
  let router: StateService;

  const message_service = jasmine.createSpyObj('MessageService', ['show_success', 'show_error']);
  const auth_service = jasmine.createSpyObj('AuthService', ['check_auth', 'get_access_token', 'auth_state']);
  const state_service = jasmine.createSpyObj('StateService', ['go']);

  beforeEach(async(() => {
    auth_service.check_auth.and.returnValue(true);
    auth_service.get_access_token.and.returnValue('test_token');
    auth_service.auth_state.and.returnValue(of(true));

    TestBed.configureTestingModule({
      imports: [
        AppRoutingModule,
        MatSnackBarModule,
        HttpClientModule,
        AngularFireModule.initializeApp(environment.firebase),
        AngularFireAuthModule,
        LocalStorageModule.forRoot({
            prefix: environment.local_storage.prefix,
            storageType: 'localStorage'
        }),
        FormsModule,
        BrowserAnimationsModule
      ],
      providers: [
        { provide: OrderService, useClass: MockOrderService },
        { provide: CartService, useClass: MockCartService },
        { provide: MessageService, useValue: message_service },
        { provide: AuthService, useValue: auth_service },
        { provide: StateService, useValue: state_service },
        HttpErrorHandlerService,
      ],
      declarations: [ CartComponent ]
    })
    .compileComponents();
  }));

  beforeEach(async(() => {
    fixture = TestBed.createComponent(CartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    cart_service = TestBed.get(CartService);
    router = TestBed.get(StateService);
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should diplay correct products info', () => {
    const products = cart_service.get_products();
    const product_name_tags: HTMLElement = fixture.nativeElement.querySelectorAll('h5');
    expect(product_name_tags[0].textContent).toMatch(products[0].drink_name, 'first product name displayed correctly');
    expect(product_name_tags[1].textContent).toMatch(products[1].drink_name, 'second product name displayed correctly');

    const product_image_tags: HTMLElement = fixture.nativeElement.querySelectorAll('.product-item__image');
    expect(product_image_tags[0].getAttribute('src')).toBe(products[0].drink_image, 'first product image displayed correctly');
    expect(product_image_tags[1].getAttribute('src')).toBe(products[1].drink_image, 'second product image displayed correctly');

    const product_size_tags: HTMLElement = fixture.nativeElement.querySelectorAll('.product-item__size-volume');
    expect(product_size_tags[0].textContent).toMatch(products[0].size_volume, 'first product size volume displayed correctly');
    expect(product_size_tags[1].textContent).toMatch(products[1].size_volume, 'second product size volume displayed correctly');

    const product_tags: HTMLElement = fixture.nativeElement.querySelectorAll('.product-item');
    const first_product_addins = product_tags[0].querySelectorAll('.product-item-addins__name');
    const second_product_addins = product_tags[1].querySelectorAll('.product-item-addins__name');
    expect(first_product_addins[0].textContent).toMatch(products[0].addins[0].name, 'first addin name of the first product displayed correctly');
    expect(first_product_addins[1].textContent).toMatch(products[0].addins[1].name, 'first addin name of the first product displayed correctly');
    expect(second_product_addins.length).toBe(0, 'length of  the second product addins displayed correctly');

    const product_qty_inputs: HTMLInputElement = fixture.nativeElement.querySelectorAll('.product-item-addins__qty');
    expect(product_qty_inputs[0].value).toBe(products[0].qty.toString(), 'first product qty displayed correctly');
    expect(product_qty_inputs[1].value).toBe(products[1].qty.toString(), 'second product qty displayed correctly');

    const product_price_tags: HTMLElement = fixture.nativeElement.querySelectorAll('.product-item__price');
    expect(product_price_tags[0].textContent).toMatch((products[0].qty * products[0].price).toString(), 'first product price displayed correctly');
    expect(product_price_tags[1].textContent).toMatch((products[1].qty * products[1].price).toString(), 'second product price displayed correctly');
  });

  it('should display correct total price', () => {
    const total_price_tags: HTMLElement = fixture.nativeElement.querySelector('.total-price');
    expect(total_price_tags.textContent).toMatch(cart_service.get_total_price().toString(), 'total price displayed correctly');
  });

  it('should change product qty', () => {
    const default_products = cart_service.get_products();
    const product_1_qty = default_products[0].qty;
    const product_2_qty = default_products[1].qty;
    const qty_inputs: HTMLInputElement = fixture.nativeElement.querySelectorAll('input.product-item-addins__qty');
    qty_inputs[0].value = (+qty_inputs[0].value + 1).toString();
    qty_inputs[0].dispatchEvent(new Event('input'));
    fixture.detectChanges();
    qty_inputs[1].value = (+qty_inputs[1].value - 1).toString();
    qty_inputs[1].dispatchEvent(new Event('input'));
    fixture.detectChanges();
    const changed_products = cart_service.get_products();
    expect(product_1_qty).toBe((changed_products[0].qty - 1), 'first product qty changed correctly');
    expect(product_2_qty).toBe((changed_products[1].qty + 1), 'second product qty changed correctly');

    const total_price_tags: HTMLElement = fixture.nativeElement.querySelector('.total-price');
    expect(total_price_tags.textContent).toMatch(cart_service.get_total_price().toString(), 'total price displayed correctly');
  });

  it('should remove product', () => {
    const product_remove_buttons = fixture.nativeElement.querySelectorAll('.product-item__remove-button');
    product_remove_buttons[0].click();
    fixture.detectChanges();

    const new_product_remove_buttons = fixture.nativeElement.querySelectorAll('.product-item__remove-button');
    expect(new_product_remove_buttons.length).toBe((1), 'remove button elements length is correct');
    const cart_products = cart_service.get_products();

    expect(cart_products.length).toBe(1, 'cart products length is correct');
  });

  it('should remove product', () => {
    const product_remove_buttons = fixture.nativeElement.querySelectorAll('.product-item__remove-button');
    product_remove_buttons[0].click();
    fixture.detectChanges();

    const new_product_remove_buttons = fixture.nativeElement.querySelectorAll('.product-item__remove-button');
    expect(new_product_remove_buttons.length).toBe((1), 'remove button elements length is correct');
    const cart_products = cart_service.get_products();

    expect(cart_products.length).toBe(1, 'cart products length is correct');
  });

  it('should create order when create order button clicked', () => {
    const create_order_button : HTMLButtonElement = fixture.nativeElement.querySelector('.create-order-button');
    create_order_button.click();
    fixture.detectChanges();
    let order_service = TestBed.get(OrderService);

    expect(order_service.get_order().id).toBe(1, 'order created');
  });

  it('should not allow creating a new order when there is a current order', () => {
    const cart_products = cart_service.get_products();
    const create_order_button : HTMLButtonElement = fixture.nativeElement.querySelector('.create-order-button');
    create_order_button.click();
    fixture.detectChanges();
    let order_service = TestBed.get(OrderService);
    const no_products_text : HTMLElement = fixture.nativeElement.querySelector('.no-products-text');
    expect(no_products_text).not.toBe(null, 'no products text displayed');

    cart_products.forEach(function (cart_product) {
      cart_service.add_product(cart_product);
    }); 
    fixture.detectChanges();
    const new_no_products_text : HTMLElement = fixture.nativeElement.querySelector('.no-products-text');
    expect(new_no_products_text).toBe(null, 'no products text hid');

    const deprecation_text : HTMLElement = fixture.nativeElement.querySelector('.not-allow-create-order-text');
    expect(deprecation_text).not.toBe(null, 'deprecation text displayed');
  });
});
