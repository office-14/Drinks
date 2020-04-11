import { async, ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { DrinkDetailComponent } from './drink-detail.component';
import { DrinksService }  from '../drinks.service';
import { HttpClientModule } from '@angular/common/http';
import { HttpErrorHandlerService } from '../../http-error-handler.service';
import { MessageService } from '../../message.service';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { DrinksModule } from '../drinks.module';
import { AppRoutingModule } from '../../app-routing.module';
import { AppModule } from "../../app.module";
import { Transition } from "@uirouter/core";
import { HttpClient } from '@angular/common/http';
import { CartService } from '../../cart.service';
import { OrderService } from '../../order.service';
import { AuthService } from '../../auth/auth.service';
import { LocalStorageService } from 'angular-2-local-storage';
import { AngularFireAuth } from  "@angular/fire/auth";
import { StateService } from "@uirouter/core";
import { AngularFireModule } from '@angular/fire';
import { AngularFireAuthModule } from '@angular/fire/auth';
import { environment } from '../../../environments/environment';
import { LocalStorageModule } from 'angular-2-local-storage';
import { of, defer } from 'rxjs';
import { MockDrinksService } from '../mock-drinks.service';
import { By } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';

export class MockTransition {
  params() {
    return {
      drink_id: 1
    };
  }
}

describe('DrinkDetailComponent', () => {
  let component: DrinkDetailComponent;
  let cart_service: CartService;
  let fixture: ComponentFixture<DrinkDetailComponent>;

  beforeEach(async(() => {
    let mockDrinksServiceFactory = (http_client: HttpClient, http_error_handler_service: HttpErrorHandlerService) => {
      let mock_drinks_service = new MockDrinksService(http_client, http_error_handler_service);
      mock_drinks_service.loadDrinks();

      return mock_drinks_service;
    };

    const local_storage_service = jasmine.createSpyObj('LocalStorageService', ['get', 'set']);
    local_storage_service.get.and.returnValue([]);

    TestBed.configureTestingModule({
      imports: [
        HttpClientModule,
        MatSnackBarModule,
        AngularFireModule.initializeApp(environment.firebase),
        AngularFireAuthModule,
        AppRoutingModule,
        FormsModule
      ],
      providers: [
        HttpErrorHandlerService,
        MessageService,
        CartService,
        { provide: LocalStorageService, useValue: local_storage_service },
        OrderService,
        AuthService,
        { provide: DrinksService, useFactory: mockDrinksServiceFactory, deps: [HttpClient, HttpErrorHandlerService] },
        { provide: Transition, useClass: MockTransition }
      ],
      declarations: [ DrinkDetailComponent ]

    })
    .compileComponents()
    .then(() => {
      fixture = TestBed.createComponent(DrinkDetailComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
      cart_service = TestBed.get(CartService);
    });
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should display original addins titles', () => {
    const addin_items: HTMLElement = fixture.nativeElement.querySelectorAll('.addins-item');
    expect(addin_items[0].querySelector('.addins-item__name').textContent).toEqual('Addin 1', 'addin 1 name detected');

    expect(addin_items[1].querySelector('.addins-item__name').textContent).toEqual('Addin 2', 'addin 1 name detected');
  });

  it('should display original sizes titles', () => {
    const size_options: HTMLElement = fixture.nativeElement.querySelectorAll('.size-item__option');
    expect(size_options[0].textContent).toMatch('Size 1-1', 'size 1 name detected');

    expect(size_options[1].textContent).toMatch('Size 1-2', 'size 2 name detected');
  });

  it('should display original qty input', () => {
    const qty_input: HTMLInputElement = fixture.nativeElement.querySelector('.drinks__qty-input');
    expect(qty_input.value).toEqual('1');
  });

  it('should add to cart selected size, addins and qty', () => {
    const sizes_select: HTMLSelectElement = fixture.nativeElement.querySelector('.sizes-select');
    const sizes_select_options: HTMLOptionElement = fixture.nativeElement.querySelectorAll('.size-item__option');
    sizes_select.value = sizes_select_options[1].value;
    sizes_select.dispatchEvent(new Event('change'));
    fixture.detectChanges();
    const qty_input: HTMLInputElement = fixture.nativeElement.querySelector('.drinks__qty-input');
    qty_input.value = '2';
    qty_input.dispatchEvent(new Event('input'));
    fixture.detectChanges();
    const addin_items: HTMLElement = fixture.nativeElement.querySelectorAll('.addins-item');
    addin_items[0].querySelector('.addins-item__name').click();
    fixture.detectChanges();
    addin_items[1].querySelector('.addins-item__name').click();
    fixture.detectChanges();
    const price_span: HTMLElement = fixture.nativeElement.querySelector('.price__value');
    expect(price_span.textContent).toBe('30', 'total price calculated right');

    const add_to_cart_button: HTMLElement = fixture.nativeElement.querySelector('.add-to-cart-button');
    add_to_cart_button.click();
    expect(cart_service.get_products_qty()).toBe(2, 'cart products qty returned 1');
  });
});
