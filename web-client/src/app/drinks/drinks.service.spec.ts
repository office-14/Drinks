import { TestBed } from '@angular/core/testing';
import { HttpClientModule } from '@angular/common/http';

import { DrinksService } from './drinks.service';
import { HttpErrorHandlerService } from '../http-error-handler.service';
import { MessageService } from '../message.service';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { HttpClient } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { defer, of } from 'rxjs';
import { MockDrinksService } from './mock-drinks.service';
import { tap } from 'rxjs/operators';

export function asyncData<T>(data: T) {
  return defer(() => Promise.resolve(data));
}

describe('DrinksService', () => {
	let service: DrinksService;

	beforeEach(() => {
		TestBed.configureTestingModule({
			imports: [
		    	HttpClientModule,
		    	MatSnackBarModule,
		    	BrowserAnimationsModule
			],
			providers: [
				HttpErrorHandlerService,
				MessageService,
				{ provide: DrinksService, useClass: MockDrinksService }
			]
		});
		service = TestBed.inject(DrinksService);
		service.loadDrinks();
		service.loadAddins();
	});

	it('should be created', () => {
		expect(service).toBeTruthy();
	});

	it('#getDrinks should be returned drinks', () => {
		let drinks = service.getDrinks();
		expect(drinks.length).toBe(3, 'service loaded right drinks length');
		expect(drinks[0]).toEqual({
	        "id": 1,
	        "name": "Drink 1",
	        "description": "About drink 1",
	        "photo_url": "https://globalassets.starbucks.com/assets/f12bc8af498d45ed92c5d6f1dac64062.jpg?impolicy=1by1_wide_1242",
	        "smallest_size_price": 100,
	        "sizes": []
	    }, 'returned right first drink');
		expect(drinks[1]).toEqual({
	        "id": 2,
	        "name": "Drink 2",
	        "description": "About drink 2",
	        "photo_url": "https://globalassets.starbucks.com/assets/5c515339667943ce84dc56effdf5fc1b.jpg?impolicy=1by1_wide_1242",
	        "smallest_size_price": 120,
	        "sizes": []
	    }, 'returned right second drink');
		expect(drinks[2]).toEqual({
	        "id": 3,
	        "name": "Drink 3",
	        "description": "About drink 3",
	        "photo_url": "https://globalassets.starbucks.com/assets/ec519dd5642c41629194192cce582135.jpg?impolicy=1by1_wide_1242",
	        "smallest_size_price": 130,
	        "sizes": []
	    }, 'returned right third drink');
	});

	it('#getDrink should be returned drink', () => {
		let drink = service.getDrink(1);
		expect(drink.id).toBe(1, 'first drink got right');
		drink = service.getDrink(2);
		expect(drink.id).toBe(2, 'second drink got right');
	});

	it('#getSizes should be returned sizes of the corresponding drink', () => {
		let drink_1_sizes = [];
		service.getSizes(1).subscribe(sizes => drink_1_sizes = sizes);
		expect(drink_1_sizes[0].id).toBe(11, 'first drink_1 size detected');
		expect(drink_1_sizes[1].id).toBe(12, 'second drink_1 size detected');

		let drink_2_sizes = [];
		service.getSizes(2).subscribe(sizes => drink_2_sizes = sizes);
		expect(drink_2_sizes[0].id).toBe(21, 'first drink_2 size detected');
		expect(drink_2_sizes[1].id).toBe(22, 'second drink_2 size detected');

		let drink_3_sizes = [];
		service.getSizes(3).subscribe(sizes => drink_3_sizes = sizes);
		expect(drink_3_sizes[0].id).toBe(31, 'first drink_3 size detected');
		expect(drink_3_sizes[1].id).toBe(32, 'second drink_3 size detected');
	});

	it('#getDraftCartProduct should be returned right value', () => {
		let drink_1_sizes = [];
		let drink_id = 1;
		service.getDrink(drink_id);
		service.getSizes(drink_id).subscribe(sizes => sizes);
		let draft_cart_products = service.getDraftCartProduct();
		expect(draft_cart_products.addins.length).toBe(2, 'addins length is right');
		expect(draft_cart_products.size.id).toBe(11, 'size is is right');
	});
});
