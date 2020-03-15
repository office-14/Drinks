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

  ngOnInit(): void {}

  is_order_exist() {
  	return this.order_service.if_order_exist();
  }

  get_order() {
    return this.order_service.get_order()
  }

  finish_order() {
    if (this.auth_service.check_auth()) {
      this.order_service.finish_order().subscribe(response => {
        if (response.status == 204) {
          this.order_service.clear_order();
          this.messageService.show_success('Заказ готов!');
        }
      });
    } else {
      this.messageService.show_error('Не возможно завершить заказ. Вам необходимо авторизоваться на сайте!');
    }
  }

}
