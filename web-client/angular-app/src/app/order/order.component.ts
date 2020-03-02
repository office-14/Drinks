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
  constructor(private order_service: OrderService) { }

  ngOnInit(): void {
  	this.order = this.order_service.get_order();
  }

  if_order_exist() {
  	return this.order_service.if_order_exist();
  }

  finish_order() {
  	this.order_service.finish_order().subscribe(response => {
  		if (response.status == 204) {
  			this.order_service.clear_order();
  			this.order = this.order_service.get_order();
  		}
  	});
  }

}
