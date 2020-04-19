import { TestBed } from '@angular/core/testing';

import { CartService } from './cart.service';
import { MockCartService } from './mock-cart.service';
import { LocalStorageService } from 'angular-2-local-storage';

describe('CartService', () => {
  let service: CartService;
  const local_storage_service = jasmine.createSpyObj('LocalStorageService', ['get', 'set']);
  local_storage_service.get.and.returnValue([]);

  beforeEach(() => {
    TestBed.configureTestingModule({
    	imports: [],
      providers: [
        { provide: CartService, useClass: MockCartService },
        { provide: LocalStorageService, useValue: local_storage_service }
      ]
    });
    service = TestBed.inject(CartService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('#get_products_qty should return correct value', () => {
    expect(service.get_products_qty()).toBe(3);
  });

  it('#get_products should return all products', () => {
    const cart_products = service.get_products();
    expect(cart_products[0].drink_id).toBe(1);
    expect(cart_products[1].drink_id).toBe(2);
  });

  it('#remove_product should remove product by index', () => {
    service.remove_product(0);
    const cart_products = service.get_products();
    expect(cart_products[0].drink_id).toBe(2, 'first product id is right');
    expect(service.get_products_qty()).toBe(2, 'products length is right');
  });

  it('#add_product should add the product correctly', () => {
    service.add_product({
      "drink_id": 1,
      "drink_name": "Drink 1",
      "drink_image": "https://globalassets.starbucks.com/assets/f12bc8af498d45ed92c5d6f1dac64062.jpg?impolicy=1by1_wide_1242",
      "size_id": 11,
      "size_volume": '11',
      "addins": [
        {
          "id": 1,
          "name": "Addin 1",
          "description": "About addin 1",
          "photo_url": "https://www.tasteofhome.com/wp-content/uploads/2018/08/shutterstock_413974858.jpg",
          "price": 1
        },
        {
          "id": 2,
          "name": "Addin 2",
          "description": "About addin 2",
          "photo_url": "https://www.tasteofhome.com/wp-content/uploads/2018/10/vanilla-extract.jpg",
          "price": 2
        }
      ],
      "price": 14,
      "qty": 1
    });
    const cart_products = service.get_products();
    expect(cart_products[0].drink_id).toBe(1, 'first product id is right');
    expect(cart_products[0].qty).toBe(2, 'first product quantities is right');
    expect(service.get_products_qty()).toBe(4, 'products length is right');
  });

  it('#get_total_price should return correct value', () => {
    expect(service.get_total_price()).toBe(58);
  });
});
