import { Observable } from 'rxjs';
import { switchMap } from 'rxjs/operators';
import { Component, OnInit } from '@angular/core';

import { DrinksService }  from '../drinks.service';
import { Drink } from '../drink';

@Component({
  selector: 'app-drink-list',
  templateUrl: './drink-list.component.html',
  styleUrls: ['./drink-list.component.css']
})
export class DrinkListComponent implements OnInit {
  drinks: Drink[];

  constructor (
    private service: DrinksService
  ) {}

  ngOnInit() {
    this.getDrinks();
  }

  getDrinks(): void {
    this.service.getDrinks()
      .subscribe(drinks => (this.drinks = drinks));
  }

}
