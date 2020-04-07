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
  constructor (
    private service: DrinksService
  ) {}

  ngOnInit() {
    this.service.loadDrinks();
  }

  getDrinks(): Drink[] {
    return this.service.getDrinks();
  }

}
