import { Injectable } from '@angular/core';
import { of } from 'rxjs';
import { OrderService } from './order.service';
import { tap } from 'rxjs/operators';

@Injectable()
export class MockOrderService extends OrderService{
  private mock_order = {
    "id": 1,
    "status_code": "COOKING",
    "status_name": "COOKING",
    "order_number": "ORD-1",
    "total_price": 1234,
    "products": []
  };

  create_order(cart_products) {
    return of(this.mock_order).pipe(
      tap(order => order.products = cart_products),
      tap(order => this.order = order)
    );
  }
};