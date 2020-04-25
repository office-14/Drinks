import { BrowserModule } from '@angular/platform-browser';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { DrinksModule }            from './drinks/drinks.module';

import { HttpErrorHandlerService }     from './http-error-handler.service';
import { MessageService }       from './message.service';
import { CartService }       from './cart.service';
import { OrderService }       from './order.service';
import { AuthService }       from './auth/auth.service';

import { HttpClientModule } from '@angular/common/http';
import { UIRouterModule } from "@uirouter/angular";

import { FormsModule } from '@angular/forms';
import { MatSnackBarModule } from '@angular/material/snack-bar';

import { AngularFireModule } from '@angular/fire';
import { AngularFireAuthModule } from '@angular/fire/auth';
import { environment } from '../environments/environment';

import { LocalStorageModule } from 'angular-2-local-storage';

import { LoadingBarHttpClientModule } from '@ngx-loading-bar/http-client';


@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    HttpClientModule,
    DrinksModule,
    FormsModule,
    MatSnackBarModule,
    AngularFireModule.initializeApp(environment.firebase),
    AngularFireAuthModule, // imports firebase/auth, only needed for auth features
    LocalStorageModule.forRoot({
        prefix: environment.local_storage.prefix,
        storageType: 'localStorage'
    }),
    LoadingBarHttpClientModule,
  ],
  providers: [
  	HttpErrorHandlerService,
    CartService,
    OrderService,
    MessageService,
    AuthService,
  ],
  schemas: [ CUSTOM_ELEMENTS_SCHEMA ],
  bootstrap: [AppComponent]
})
export class AppModule {
	constructor() {}
}
