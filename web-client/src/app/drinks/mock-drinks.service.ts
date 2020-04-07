import { Injectable } from '@angular/core';
import { of } from 'rxjs';
import { DrinksService } from './drinks.service';
import { tap } from 'rxjs/operators';

@Injectable()
export class MockDrinksService extends DrinksService{
  loadDrinks () {
    let test_drinks = [
      {
        "id": 1,
        "name": "Drink 1",
        "description": "About drink 1",
        "photo_url": "https://globalassets.starbucks.com/assets/f12bc8af498d45ed92c5d6f1dac64062.jpg?impolicy=1by1_wide_1242",
        "smallest_size_price": 100,
        "sizes": []
      },
      {
        "id": 2,
        "name": "Drink 2",
        "description": "About drink 2",
        "photo_url": "https://globalassets.starbucks.com/assets/5c515339667943ce84dc56effdf5fc1b.jpg?impolicy=1by1_wide_1242",
        "smallest_size_price": 120,
        "sizes": []
      },
      {
        "id": 3,
        "name": "Drink 3",
        "description": "About drink 3",
        "photo_url": "https://globalassets.starbucks.com/assets/ec519dd5642c41629194192cce582135.jpg?impolicy=1by1_wide_1242",
        "smallest_size_price": 130,
        "sizes": []
      }
    ];
    of(test_drinks).subscribe(drinks => this.drinks = drinks);
  }

  loadAddins () {
    let test_addins = [
      {
        "id": 1,
        "name": "Addin 1",
        "description": "About addin 1",
        "photo_url": "https://www.tasteofhome.com/wp-content/uploads/2018/08/shutterstock_413974858.jpg",
        "price": 1
      },
      {
        "id": 2,
        "name": "Addin 2",
        "description": "About addin 2",
        "photo_url": "https://www.tasteofhome.com/wp-content/uploads/2018/10/vanilla-extract.jpg",
        "price": 2
      }
    ];
    of(test_addins).subscribe(addins => this.draft_cart_product.addins = addins)
  }

  getSizes (drink_id: number | string) {
    let sizes = {
      1: [
        {
          "id": 11,
          "price": 11,
          "name": 'Size 1-1',
          "volume": '11'
        },
        {
          "id": 12,
          "price": 12,
          "name": 'Size 1-2',
          "volume": '12'
        }
      ],
      2: [
        {
          "id": 21,
          "price": 21,
          "name": 'Size 2-1',
          "volume": '21'
        },
        {
          "id": 22,
          "price": 22,
          "name": 'Size 2-2',
          "volume": '22'
        }
      ],
      3: [
        {
          "id": 31,
          "price": 31,
          "name": 'Size 3-1',
          "volume": '31'
        },
        {
          "id": 32,
          "price": 32,
          "name": 'Size 3-2',
          "volume": '32'
        }
      ]
    };
    return of(sizes[drink_id]).pipe(
      tap(sizes => this.draft_cart_product.size = sizes[0])
    );
  }
};