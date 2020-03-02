import { TestBed } from '@angular/core/testing';

import { DrinksService } from './drinks.service';

describe('DrinksService', () => {
  let service: DrinksService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DrinksService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
