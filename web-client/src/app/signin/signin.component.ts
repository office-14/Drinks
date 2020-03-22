import { Component, OnInit } from '@angular/core';
import { AuthService } from "../auth/auth.service";
import { CartService } from '../cart.service';
import { StateService } from "@uirouter/core";
import { MessageService } from '../message.service';
import { Transition } from "@uirouter/core";


@Component({
  selector: 'app-signin',
  templateUrl: './signin.component.html',
  styleUrls: ['./signin.component.css']
})
export class SigninComponent implements OnInit {

  order_creating_started = false;

  constructor(
    private auth_service: AuthService,
    private cart_service: CartService,
    private state_service: StateService,
    private message_service: MessageService,
    private trans: Transition,
  ) { }

  ngOnInit(): void {
    if (this.trans.params().order_creating_started) {
      this.order_creating_started = true;
    }
  }

  google_auth() {
    if (this.order_creating_started) {
      this.cart_service.order_creating_started = true;
    }
  	this.auth_service.google_auth().then((result) => {
  		this.state_service.go('drinks');
  	}).catch((error) => {
      this.cart_service.order_creating_started = false;
	    this.message_service.show_error('Произошла ошибка во время авторизации. Попробуйте позже попробовать снова.');
    });
  }

}
