import { Injectable } from '@angular/core';
import { of } from 'rxjs';
import { OrderService } from './order.service';
import { tap } from 'rxjs/operators';

@Injectable()
export class MockOrderService extends OrderService{
  private mock_new_order_finished = {
    "id": 2,
    "status_code": "READY",
    "status_name": "READY",
    "order_number": "ORD-2",
    "total_price": 2345,
    "products": []
  };
  private mock_last_order = {
    "id": 1,
    "status_code": "READY",
    "status_name": "READY",
    "order_number": "ORD-1",
    "total_price": 1234,
    "products": []
  };
  private mock_new_order = {
    "id": 2,
    "status_code": "COOKING",
    "status_name": "COOKING",
    "order_number": "ORD-2",
    "total_price": 2345,
    "products": []
  };

  private last_order_counter = 0;

  api_get_last_order_status() {
    this.last_order_counter += 1;
    if (this.last_order_counter > 1) {
      return of(this.mock_new_order_finished);
    }

    return of(this.mock_new_order);    
  }

  api_get_last_order() {
    return of(this.mock_new_order);    
  }

  api_create_order(order_products) {
    return of(this.mock_new_order);
  }

};