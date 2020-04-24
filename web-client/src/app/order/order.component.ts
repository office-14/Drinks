import { Component, OnInit } from '@angular/core';
import { OrderService } from '../order.service';
import { Order } from './order';

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.css']
})
export class OrderComponent implements OnInit {
  order: Order;
  constructor(
    private order_service: OrderService
  ) { }

  ngOnInit(): void {
  }

  is_last_order_exist() {
  	return this.order_service.is_last_order_exist();
  }

  get_order() {
    let order = this.order_service.get_order();

    return order;
  }

  is_order_status_cooking() {
    return this.order_service.is_order_status_cooking();
  }

}
