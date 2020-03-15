import { Component, OnInit } from '@angular/core';
import { AuthService } from "../auth/auth.service";
import { CartService } from '../cart.service';
import { StateService } from "@uirouter/core";
import { MessageService } from '../message.service';


@Component({
  selector: 'app-signin',
  templateUrl: './signin.component.html',
  styleUrls: ['./signin.component.css']
})
export class SigninComponent implements OnInit {

  constructor(
  	private auth_service: AuthService,
  	private cart_service: CartService,
	private state_service: StateService,
	private message_service: MessageService
  ) { }

  ngOnInit(): void {

  }

  is_order_creating_in_process() {
  	return this.cart_service.order_creating_started();
  }

  google_auth() {
  	this.auth_service.google_auth().then((result) => {
  		if (this.cart_service.order_creating_started()) {
  			this.cart_service.create_order();
  		} else {
  			this.state_service.go('drinks');
  		}
	}).catch((error) => {
	    this.message_service.show_error('Произошла ошибка во время авторизации. Попробуйте позже попробовать снова.');
    })
  }

}
