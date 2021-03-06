import { TestBed } from '@angular/core/testing';

import { AuthService } from './auth.service';
import { AngularFireAuthModule } from '@angular/fire/auth';
import { AngularFireModule } from '@angular/fire';
import { environment } from '../../environments/environment';

describe('AuthService', () => {
  let service: AuthService;

  beforeEach(() => {
    TestBed.configureTestingModule({
    	imports: [
	        AngularFireModule.initializeApp(environment.firebase),
	        AngularFireAuthModule,
    	],
    	providers: [
    		AuthService
    	]
    });
    service = TestBed.inject(AuthService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
