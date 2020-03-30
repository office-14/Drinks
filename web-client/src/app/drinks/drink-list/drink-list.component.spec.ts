import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DrinkListComponent } from './drink-list.component';
import { DrinksService }  from '../drinks.service';
import { HttpClientModule } from '@angular/common/http';
import { HttpErrorHandlerService } from '../../http-error-handler.service';
import { MessageService } from '../../message.service';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { defer } from 'rxjs';

export function asyncData<T>(data: T) {
  return defer(() => Promise.resolve(data));
}

describe('DrinkListComponent', () => {
  let component: DrinkListComponent;
  let fixture: ComponentFixture<DrinkListComponent>;

  beforeEach(async(() => {
    let test_drinks = [];
    const drinks_service = jasmine.createSpyObj('DrinksService', ['getDrinks']);
    drinks_service.getDrinks.and.returnValue( asyncData(test_drinks) );
    TestBed.configureTestingModule({
      imports: [
        HttpClientModule,
        MatSnackBarModule
      ],
      providers: [
        HttpErrorHandlerService,
        MessageService,
        { provide: DrinksService, useValue: drinks_service },
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
});
