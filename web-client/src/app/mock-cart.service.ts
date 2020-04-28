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
        "drink_image": "https://storage.googleapis.com/images.office-14.com/testing/stub_americano.jpg",
        "size_id": 11,
        "size_volume": '11',
        "addins": [
          {
            "id": 1,
            "name": "Addin 1",
            "description": "About addin 1",
            "photo_url": "https://storage.googleapis.com/images.office-14.com/testing/stub_ice-cream.jpg",
            "price": 1
          },
          {
            "id": 2,
            "name": "Addin 2",
            "description": "About addin 2",
            "photo_url": "https://storage.googleapis.com/images.office-14.com/testing/stub_vanilla.jpg",
            "price": 2
          }
        ],
        "price": 14,
        "qty": 1
      },
      {
        "drink_id": 2,
        "drink_name": "Drink 2",
        "drink_image": "https://storage.googleapis.com/images.office-14.com/testing/stub_cappuccino.jpg",
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