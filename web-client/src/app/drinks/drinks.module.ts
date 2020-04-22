import { NgModule, NO_ERRORS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DrinksRoutingModule } from './drinks-routing.module';
import { DrinkListComponent } from './drink-list/drink-list.component';
import { DrinkDetailComponent } from './drink-detail/drink-detail.component';
import { FormsModule } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { DrinksService } from "./drinks.service";

@NgModule({
  declarations: [DrinkListComponent, DrinkDetailComponent],
  imports: [
    CommonModule,
    DrinksRoutingModule,
    FormsModule,
    ReactiveFormsModule,
  ],
  providers: [
  	DrinksService
  ],
  schemas: [NO_ERRORS_SCHEMA]
})
export class DrinksModule { }
