import { TestBed } from '@angular/core/testing';
import { HttpClientModule } from '@angular/common/http';

import { DrinksService } from './drinks.service';
import { HttpErrorHandlerService } from '../http-error-handler.service';
import { MessageService } from '../message.service';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { HttpClient } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { defer } from 'rxjs';

export function asyncData<T>(data: T) {
  return defer(() => Promise.resolve(data));
}

describe('DrinksService', () => {
	let service: DrinksService;

	let test_drinks = [];
	const drinks_service = jasmine.createSpyObj('DrinksService', ['loadDrinks']);
	drinks_service.loadDrinks.and.returnValue( asyncData(test_drinks) );

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
				{ provide: DrinksService, useValue: drinks_service },
			]
		});
		service = TestBed.inject(DrinksService);
	});

	it('should be created', () => {
		expect(service).toBeTruthy();
	});
});
