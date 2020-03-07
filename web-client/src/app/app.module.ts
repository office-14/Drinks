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
    MatSnackBarModule
  ],
  providers: [
  	HttpErrorHandler,
    MessageService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
	constructor(router: UIRouterModule) {
    // Use a custom replacer to display function names in the route configs
    // const replacer = (key, value) => (typeof value === 'function') ? value.name : value;

    // console.log('Routes: ', JSON.stringify(router.config, replacer, 2));
  }
}
