import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DrinkListComponent } from './drink-list.component';
import { DrinksService }  from '../drinks.service';
import { HttpClientModule } from '@angular/common/http';
import { HttpErrorHandlerService } from '../../http-error-handler.service';
import { MessageService } from '../../message.service';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { defer, of } from 'rxjs';
import { Drink } from '../drink';
import { MockDrinksService } from '../mock-drinks.service';


export function asyncData<T>(data: T) {
  return defer(() => Promise.resolve(data));
}

describe('DrinkListComponent', () => {
  let component: DrinkListComponent;
  let fixture: ComponentFixture<DrinkListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientModule,
        MatSnackBarModule
      ],
      providers: [
        HttpErrorHandlerService,
        MessageService,
        { provide: DrinksService, useClass: MockDrinksService },
      ],
      declarations: [ DrinkListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DrinkListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have three <div>s with drinks info', () => {
    const drink_items: HTMLElement = fixture.nativeElement.querySelectorAll('div.cell');

    expect(drink_items[0].querySelector('h5').textContent).toEqual('Drink 1', 'drink 1 name detected');
    expect(drink_items[1].querySelector('h5').textContent).toEqual('Drink 2', 'drink 2 name detected');
    expect(drink_items[2].querySelector('h5').textContent).toEqual('Drink 3', 'drink 3 name detected');

    expect(drink_items[0].querySelector('p').textContent).toMatch('100', 'drink 1 price detected');
    expect(drink_items[1].querySelector('p').textContent).toMatch('120', 'drink 2 price detected');
    expect(drink_items[2].querySelector('p').textContent).toMatch('130', 'drink 3 price detected');

    expect(drink_items[0].querySelector('img').getAttribute('src')).toEqual('https://globalassets.starbucks.com/assets/f12bc8af498d45ed92c5d6f1dac64062.jpg?impolicy=1by1_wide_1242', 'drink 1 img detected');
    expect(drink_items[1].querySelector('img').getAttribute('src')).toEqual('https://globalassets.starbucks.com/assets/5c515339667943ce84dc56effdf5fc1b.jpg?impolicy=1by1_wide_1242', 'drink 2 img detected');
    expect(drink_items[2].querySelector('img').getAttribute('src')).toEqual('https://globalassets.starbucks.com/assets/ec519dd5642c41629194192cce582135.jpg?impolicy=1by1_wide_1242', 'drink 3 img detected');
  });
});
