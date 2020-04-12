import { Injectable } from '@angular/core';
import { auth } from  'firebase/app';
import { AngularFireAuth } from  "@angular/fire/auth";
import { tap } from 'rxjs/operators';


@Injectable()
export class AuthService {
  	user: any; 

	constructor(
		public  afAuth:  AngularFireAuth
	) {
		this.auth_state().subscribe(user => {
	      if (user) {
	        this.user = user;
	      } else {
	        this.user = null;
	      }
	    });
	}

	auth_state() {
		return this.afAuth.authState;
	}

	get_access_token() {
		const current_user = JSON.parse(JSON.stringify(this.afAuth.auth.currentUser));
		if (current_user) {
			return current_user.stsTokenManager.accessToken;
		}
		return false;
	}

	check_auth() {
		if (this.user) {
			return true;
		}

		return false;
	}

	google_auth(){
		return this.afAuth.auth.signInWithPopup(new auth.GoogleAuthProvider());
	}

	sign_out() {
		return this.afAuth.auth.signOut().then(() => {
			this.user = null;
	    })
	}
}
