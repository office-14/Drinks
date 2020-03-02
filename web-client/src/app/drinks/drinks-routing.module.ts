import { NgModule } from '@angular/core';
import { UIRouterModule } from "@uirouter/angular";

import { DrinkListComponent } from './drink-list/drink-list.component';
import { DrinkDetailComponent } from './drink-detail/drink-detail.component';

import { DrinksService } from "./drinks.service";
import { Transition } from "@uirouter/core";

/** States */
const drinksState = { name: "drinks", url: "/drinks", component: DrinkListComponent };
const drinkState = {
	name: "drink", 
	url: "/drinks", 
	component: DrinkDetailComponent, 
	params: {drink_id: 0},
	resolve: [
		{
		  token: "drink",
		  deps: [Transition, DrinksService],
		  resolveFn: (trans: Transition, drinkService: DrinksService) =>
		    drinkService.getDrink(trans.params().drink_id)
		}
	]

};

@NgModule({
  imports: [
    UIRouterModule.forChild({ states: [drinksState, drinkState]})
  ],
  exports: [UIRouterModule]
})
export class DrinksRoutingModule { }
