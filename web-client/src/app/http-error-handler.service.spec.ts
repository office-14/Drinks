import { TestBed } from '@angular/core/testing';

import { HttpErrorHandlerService } from './http-error-handler.service';
import { MessageService } from './message.service';
import {MatSnackBar} from '@angular/material/snack-bar';
import { MatSnackBarModule } from '@angular/material/snack-bar';

describe('HttpErrorHandlerService', () => {
  let service: HttpErrorHandlerService;

  beforeEach(() => {
    TestBed.configureTestingModule({
    	imports: [ MatSnackBarModule ],
    	providers: [
    		MessageService,
	    	HttpErrorHandlerService
	    ]
    });
    service = TestBed.inject(HttpErrorHandlerService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
