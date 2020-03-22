import { Component, OnInit } from '@angular/core';
import { OrderService } from '../order.service';
import { Order } from './order';
import { MessageService } from '../message.service';
import { AuthService } from '../auth/auth.service';

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.css']
})
export class OrderComponent implements OnInit {
  order: Order;
  constructor(
    private order_service: OrderService,
    private messageService: MessageService,
    private auth_service: AuthService
  ) { }

  ngOnInit(): void {
    
    
  }
  

  is_order_exist() {
  	return this.order_service.if_order_exist();
  }

  get_order() {
    return this.order_service.get_order();
  }

  is_order_status_ready() {
    return this.order_service.is_order_status_ready();
  }

  clear_ready_order() {
    if (this.order_service.is_order_status_ready()) {
      this.order_service.clear_order();
    }
  }

}
