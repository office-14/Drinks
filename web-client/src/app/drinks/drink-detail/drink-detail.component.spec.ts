import { async, ComponentFixture, TestBed } from '@angular/core/testing';

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

export class MockTransition {
  params() {
    return {
      drink_id: 1
    };
  }
}

export class MockOrderService extends OrderService{
  constructor(
    protected http: HttpClient,
    httpErrorHandler: HttpErrorHandlerService,
    protected auth_service: AuthService,
    protected local_storage_service: LocalStorageService,
    protected afAuth: AngularFireAuth,
    protected state_service: StateService
  ) {
     super(http, httpErrorHandler, auth_service, local_storage_service, afAuth, state_service);
  }
}

describe('DrinkDetailComponent', () => {
  let component: DrinkDetailComponent;
  let fixture: ComponentFixture<DrinkDetailComponent>;

  beforeEach(async(() => {
    let mockDrinksServiceFactory = (http_client: HttpClient, http_error_handler_service: HttpErrorHandlerService) => {
      let mock_drinks_service = new MockDrinksService(http_client, http_error_handler_service);
      mock_drinks_service.loadDrinks();

      return mock_drinks_service;
    };

    TestBed.configureTestingModule({
      imports: [
        HttpClientModule,
        MatSnackBarModule,
        AngularFireModule.initializeApp(environment.firebase),
        AngularFireAuthModule,
        LocalStorageModule.forRoot({
            prefix: environment.local_storage.prefix,
            storageType: 'localStorage'
        }),
        AppRoutingModule
      ],
      providers: [
        HttpErrorHandlerService,
        MessageService,
        CartService,
        {
          provide: OrderService,
          useFactory: (http: HttpClient, httpErrorHandler: HttpErrorHandlerService, auth_service: AuthService, local_storage_service: LocalStorageService, afAuth: AngularFireAuth, state_service: StateService) => {
            return new MockOrderService(http, httpErrorHandler, auth_service, local_storage_service, afAuth, state_service);
          },
          deps: [HttpClient, HttpErrorHandlerService, AuthService, LocalStorageService, AngularFireAuth, StateService]
        },
        AuthService,
        { provide: DrinksService, useFactory: mockDrinksServiceFactory, deps: [HttpClient, HttpErrorHandlerService] },
        { provide: Transition, useClass: MockTransition }
      ],
      declarations: [ DrinkDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DrinkDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
