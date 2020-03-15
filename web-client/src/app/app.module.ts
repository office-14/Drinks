import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { DrinksModule }            from './drinks/drinks.module';

import { HttpErrorHandler }     from './http-error-handler.service';
import { MessageService }       from './message.service';

import { HttpClientModule } from '@angular/common/http';
import { UIRouterModule } from "@uirouter/angular";

import { FormsModule } from '@angular/forms';
import {
  MatSnackBarModule
} from '@angular/material/snack-bar';

import { AngularFireModule } from '@angular/fire';
import { AngularFireAuthModule } from '@angular/fire/auth';
import { environment } from '../environments/environment';

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
  ],
  providers: [
  	HttpErrorHandler,
    MessageService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
	constructor(router: UIRouterModule) {}
}
