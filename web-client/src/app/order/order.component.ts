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
    this.order_service.load_last_order();
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

  get_formated_date() {
    const formatter = new Intl.DateTimeFormat("ru", {
      year: 'numeric',
      month: 'long',
      day: 'numeric',
      weekday: 'long',
      hour: 'numeric',
      minute: 'numeric'
    });
    let order = this.order_service.get_order();
    if (order.created) {
      return formatter.format(order.created)
    }
    return '';
  }

}
