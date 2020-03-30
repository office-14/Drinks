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

export class MockTransition {
  params() {
    return {
      drink_id: 0
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

export function asyncData<T>(data: T) {
  return defer(() => Promise.resolve(data));
}

describe('DrinkDetailComponent', () => {
  let component: DrinkDetailComponent;
  let fixture: ComponentFixture<DrinkDetailComponent>;

  beforeEach(async(() => {
    let test_drink = {
      sizes: []
    };
    let test_addins = [];
    let test_sizes = {
      
    };
    const drinks_service = jasmine.createSpyObj('DrinksService', ['getDrink', 'getAddins', 'getSizes']);
    drinks_service.getDrink.and.returnValue( asyncData(test_drink) );
    drinks_service.getAddins.and.returnValue( asyncData(test_addins) );
    drinks_service.getSizes.and.returnValue( asyncData(test_sizes) );


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
        { provide: DrinksService, useValue: drinks_service },
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
