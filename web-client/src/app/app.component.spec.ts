import { TestBed, async } from '@angular/core/testing';
import { AppComponent } from './app.component';
import { OrderService } from './order.service';
import { CartService } from './cart.service';
import { MessageService } from './message.service';
import { AuthService } from './auth/auth.service';
import { AppRoutingModule } from './app-routing.module';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { HttpClientModule } from '@angular/common/http';
import { HttpErrorHandlerService } from './http-error-handler.service';
import { AngularFireAuthModule } from '@angular/fire/auth';
import { AngularFireModule } from '@angular/fire';
import { environment } from '../environments/environment';
import { LocalStorageModule } from 'angular-2-local-storage';
import { of } from 'rxjs';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

describe('AppComponent', () => {
  beforeEach(async(() => {
    const auth_service = jasmine.createSpyObj('AuthService', ['check_auth', 'sign_out', 'auth_state']);
    auth_service.check_auth.and.returnValue(false);
    auth_service.auth_state.and.returnValue(of(false));
    const cart_service = jasmine.createSpyObj('AuthService', ['load_products_from_local_storage', 'get_products_qty']);
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
        })
      ],
      providers: [
        OrderService,
        MessageService,
        { provide: AuthService, useValue: auth_service },
        { provide: CartService, useValue: cart_service },
        HttpErrorHandlerService
      ],
      schemas: [ CUSTOM_ELEMENTS_SCHEMA ],
      declarations: [
        AppComponent
      ],
    }).compileComponents();
  }));

  it('should create the app', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });

  // it(`should have as title 'angular-app'`, () => {
  //   const fixture = TestBed.createComponent(AppComponent);
  //   const app = fixture.componentInstance;
  //   expect(app.title).toEqual('angular-app');
  // });

  // it('should render title', () => {
  //   const fixture = TestBed.createComponent(AppComponent);
  //   fixture.detectChanges();
  //   const compiled = fixture.nativeElement;
  //   expect(compiled.querySelector('.content span').textContent).toContain('angular-app app is running!');
  // });
});
