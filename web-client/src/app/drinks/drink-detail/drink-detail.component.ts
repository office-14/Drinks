import { Component, OnInit } from '@angular/core';
import { DrinksService }  from '../drinks.service';

import { Transition } from "@uirouter/core";
import { Drink } from "../drink";
import { Addin } from "../addin";
import { Observable } from 'rxjs';

import { CartService } from '../../cart.service';

@Component({
  selector: 'app-drink-detail',
  templateUrl: './drink-detail.component.html',
  styleUrls: ['./drink-detail.component.css']
})
export class DrinkDetailComponent implements OnInit {
  drink: Drink;
  addins: Addin[];
  selected_test = null;

  constructor (
    private drinks_service: DrinksService,
    private trans: Transition,
    private cart_service: CartService
  ) {}

  ngOnInit() {
  	this.drinks_service.loadAddins();
    this.getDrink();
  }

  protected getCurrent_drinkId() {
    return this.trans.params().drink_id;
  }

  getDraftCartProduct() {
    return this.drinks_service.getDraftCartProduct();
  }

  getDrink() {
  	this.drink = this.drinks_service.getDrink(this.getCurrent_drinkId());
    this.drinks_service.getSizes(this.drink.id).subscribe(size => {
			  this.drink.sizes = size;
		});
  }

  public addToCart(event) {
    let draft_cart_product = this.drinks_service.getDraftCartProduct();
  	this.cart_service.add_product({
  		drink_id: this.drink.id,
  		drink_name: this.drink.name,
  		drink_image: this.drink.photo_url,
  		size_id: draft_cart_product.size.id,
  		size_volume: draft_cart_product.size.volume,
  		addins: draft_cart_product.addins.filter(addin => addin.selected == true),
  		price: this.get_selected_price(),
  		qty: draft_cart_product.qty
  	});
  }

  public change_draft_cart_product_qty() {
    let draft_cart_product = this.drinks_service.getDraftCartProduct();
  	if (draft_cart_product.qty < 1) {
  		draft_cart_product.qty = 1;
  	}
  }

  public get_selected_price() {
    let draft_cart_product = this.drinks_service.getDraftCartProduct();
  	let total_price = 0;
    if (draft_cart_product.size) {
      total_price += draft_cart_product.size.price;
    }
    total_price += draft_cart_product.addins.
	  filter(addin => addin.selected == true).
	  reduce(function(prev, cur) 
	    {
	        return prev + cur.price;
	    }, 0);
    
  	return total_price;
  }

}
