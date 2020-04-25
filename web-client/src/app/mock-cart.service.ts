import { Injectable } from '@angular/core';
import { of } from 'rxjs';
import { CartService } from './cart.service';
import { tap } from 'rxjs/operators';

@Injectable()
export class MockCartService extends CartService{
  cart = {
    cart_products: [
      {
        "drink_id": 1,
        "drink_name": "Drink 1",
        "drink_image": "https://globalassets.starbucks.com/assets/f12bc8af498d45ed92c5d6f1dac64062.jpg?impolicy=1by1_wide_1242",
        "size_id": 11,
        "size_volume": '11',
        "addins": [
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
        ],
        "price": 14,
        "qty": 1
      },
      {
        "drink_id": 2,
        "drink_name": "Drink 2",
        "drink_image": "https://globalassets.starbucks.com/assets/5c515339667943ce84dc56effdf5fc1b.jpg?impolicy=1by1_wide_1242",
        "size_id": 22,
        "size_volume": '22',
        "addins": [],
        "price": 22,
        "qty": 2
      }
    ],
    comment: 'Test comment'
  };
  
};