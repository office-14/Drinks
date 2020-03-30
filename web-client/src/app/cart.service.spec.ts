import { TestBed } from '@angular/core/testing';

import { CartService } from './cart.service';
import { AppModule } from './app.module';

describe('CartService', () => {
  let service: CartService;

  beforeEach(() => {
    TestBed.configureTestingModule({
    	imports: [
    		AppModule
    	]
    });
    service = TestBed.inject(CartService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
