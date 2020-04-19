import { Component, OnInit } from '@angular/core';
import { CartService } from './cart.service';
import { OrderService } from './order.service';
import { AuthService } from './auth/auth.service';
import { StateService } from "@uirouter/core";
import { MessageService } from './message.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'angular-app';

  constructor(
  	private cart_service: CartService,
  	private order_service: OrderService,
    private auth_service: AuthService,
    private state_service: StateService,
    private message_service: MessageService
  ) {}

  ngOnInit(): void {
    this.auth_service.auth_state().subscribe(user => {
      if (user) {
        this.order_service.load_last_order().subscribe(
            last_order => {
              if (this.order_service.order_creating_started) {
                if (this.order_service.is_allow_to_order()) {
                  this.order_service.create_order().subscribe(
                    order => {
                      this.message_service.show_success('Заказ оформлен!');
                    }
                  );
                } else {
                  this.message_service.show_error('Не возможно создать заказ, так как есть текущий!');
                }
                this.order_service.order_creating_started = false;
              }
            }
        );
      } else {
        this.order_service.remove_order();
      }
    });
    this.cart_service.load_products_from_local_storage();
  }

  public get_cart_products_qty() {
  	return this.cart_service.get_products_qty();
  }

  public is_last_order_exist() {
  	return this.order_service.is_last_order_exist();
  }

  public is_user_logged_in() {
    return this.auth_service.check_auth();
  }

  public sign_out() {
    return this.auth_service.sign_out();
  }
  
  public sign_in() {
    this.state_service.go('signin');
  }
}
