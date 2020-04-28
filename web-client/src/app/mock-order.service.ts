import { Injectable } from '@angular/core';
import { of } from 'rxjs';
import { OrderService } from './order.service';
import { tap } from 'rxjs/operators';

@Injectable()
export class MockOrderService extends OrderService{
  private mock_order_status_finished = {
    "id": 2,
    "status_code": "READY",
    "status_name": "READY"
  };
  private mock_order_status_cooking = {
    "id": 2,
     "status_code": "COOKING",
    "status_name": "COOKING",
  };
  private mock_last_order = {
    "id": 2,
    "status_code": "COOKING",
    "status_name": "COOKING",
    "order_number": "ORD-2",
    "total_price": 2345,
    "comment": "string",
    "drinks": [
      {
        "drink": {
          "id": 1,
          "name": "Drink 1",
          "photo_url": "https://storage.googleapis.com/images.office-14.com/testing/stub_americano.jpg"
        },
        "drink_size": {
          "id": 1,
          "name": "Size 1",
          "volume": "200 ml"
        },
        "add-ins": [
          {
            "id": 1,
            "name": "Vanilla"
          }
        ],
        "count": 1,
        "price": 100
      }
    ]
  };

  private last_order_counter = 0;

  api_get_last_order_status() {
    this.last_order_counter += 1;
    if (this.last_order_counter > 1) {
      return of(this.mock_order_status_finished);
    }

    return of(this.mock_order_status_cooking);    
  }

  api_get_last_order() {
    return of(this.mock_last_order);    
  }

  api_create_order(order_products) {
    return of(this.mock_last_order);
  }

};