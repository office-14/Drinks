import { Component, OnInit } from '@angular/core';
import { OrderService } from '../order.service';
import { DrinksService } from '../drinks/drinks.service';
import { Order } from './order';

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.css']
})
export class OrderComponent implements OnInit {
  order: Order;
  constructor(
    private order_service: OrderService,
    private drinks_service: DrinksService
  ) { }

  ngOnInit(): void {
    this.drinks_service.loadAddins();
    // this.drink = this.drinks_service.getDrink(this.getCurrent_drinkId());
    // this.drinks_service.getSizes(this.drink.id).subscribe(size => {
    //     this.drink.sizes = size;
    // });
  }

  protected convert_order_products (order_drinks) {

    return order_drinks.map((order_drink) => {
      let drink = this.drinks_service.getDrink(order_drink.drink_id);
      // let size = drink.sizes.find((size) => {
      //   if (size.id == order_drink.size_id) {
      //     return size;
      //   }
      //   return false;
      // });
      let draft_cart_product = this.drinks_service.getDraftCartProduct();
      let selected_addins = draft_cart_product.addins.filter(addin => order_drink['add-ins'].includes(addin.id)).map(addin => addin);

      let addins_price_sum = selected_addins.reduce(function(prev, cur) 
        {
            return prev + cur.price;
        }, 0);

      let size = {
        volume: '200 Ml',
        price: 140
      };

      order_drink.drink_image = drink.photo_url;
      order_drink.drink_id = drink.id;
      order_drink.drink_name = drink.name;
      order_drink.addins = selected_addins;
      order_drink.size_volume = size.volume;
      order_drink.price = size.price + addins_price_sum;

      return order_drink;
    });
  }

  is_last_order_exist() {
  	return this.order_service.is_last_order_exist();
  }

  get_order() {
    let order = this.order_service.get_order();
    order.drinks = this.convert_order_products(order.drinks);

    return order;
  }

  is_order_status_cooking() {
    return this.order_service.is_order_status_cooking();
  }

}
