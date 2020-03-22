import { Component, OnInit } from '@angular/core';
import { DrinksService }  from '../drinks.service';

import { Transition } from "@uirouter/core";
import { Drink } from "../drink";
import { Addin } from "../addin";
import { Observable } from 'rxjs';
import { DraftCartProduct } from "../draft-cart-product";

import { FormsModule } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { CartService } from '../../cart.service';

@Component({
  selector: 'app-drink-detail',
  templateUrl: './drink-detail.component.html',
  styleUrls: ['./drink-detail.component.css']
})
export class DrinkDetailComponent implements OnInit {
  drink: Drink;
  addins: Addin[];
  draft_cart_product: DraftCartProduct = {
  size: null,
	addins: [],
	qty: 1,
	drink_id: 0
  };
  selected_test = null;

  constructor (
    private service: DrinksService,
    private trans: Transition,
    private cart_service: CartService
  ) {}

  ngOnInit() {
  	this.getDrink();
  	this.service.getAddins().subscribe(addins => this.draft_cart_product.addins = addins);
  }

  getDrink() {
  	this.service.getDrink(this.trans.params().drink_id).subscribe(
  		drink => (
  			this.service.getSizes(this.trans.params().drink_id).subscribe(size => {
		  		this.drink = drink;
				  this.drink.sizes = size;
  				this.draft_cart_product.size = this.drink.sizes[0];
  				this.get_selected_price();
			})
  		) 		
  	);
  }

  public addToCart(event) {
  	this.cart_service.add_product({
  		drink_id: this.drink.id,
  		drink_name: this.drink.name,
  		drink_image: this.drink.photo_url,
  		size_id: this.draft_cart_product.size.id,
  		size_volume: this.draft_cart_product.size.volume,
  		addins: this.draft_cart_product.addins.filter(addin => addin.selected == true),
  		price: this.get_selected_price(),
  		qty: this.draft_cart_product.qty
  	});
  }

  public change_draft_cart_product_qty() {
  	if (this.draft_cart_product.qty < 1) {
  		this.draft_cart_product.qty = 1;
  	}
  }

  public get_selected_price() {
  	let total_price = 0;
    if (this.draft_cart_product.size) {
      total_price += this.draft_cart_product.size.price;
    }
    total_price += this.draft_cart_product.addins.
	  filter(addin => addin.selected == true).
	  reduce(function(prev, cur) 
	    {
	        return prev + cur.price;
	    }, 0);
    
  	return total_price;
  }

}
