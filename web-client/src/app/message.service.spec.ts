import { TestBed } from '@angular/core/testing';

import { MessageService } from './message.service';
import { MatSnackBarModule } from '@angular/material/snack-bar';

describe('MessageService', () => {
  let service: MessageService;

  beforeEach(() => {
    TestBed.configureTestingModule({
    	imports: [
    		MatSnackBarModule
    	],
    	providers: [
    		MessageService
    	]
    });
    service = TestBed.inject(MessageService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
