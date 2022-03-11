
import { Injectable } from '@angular/core';
import { Firestore } from '@angular/fire/firestore';
import { createUserWithEmailAndPassword, getAuth, GoogleAuthProvider, sendPasswordResetEmail, signInWithEmailAndPassword, signInWithPopup, signOut } from 'firebase/auth';
import { EmailPasswordProvider } from '../interface/email-password-provider';
import { ForgotPassword } from '../interface/forgot-password';
import { DataService } from './data.service';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  auth = getAuth();

  constructor(private firestore: Firestore,
              private dataService: DataService) { }


  // Login
  async login(credentials: EmailPasswordProvider) {
    console.log("login auth");

    await signInWithEmailAndPassword(this.auth, credentials.emailAddress, credentials.password)
    .then(res => {this.dataService.setData("signedIn", res);
    this.dataService.setData("uid", res.user.uid);
    this.dataService.setData("email", res.user.email);
    });
  }

  async logout() {
    await signOut(this.auth).then(res => {
    console.log("SignedOut") });
    this.dataService.setData("signedIn", undefined);
    this.dataService.setData("uid", undefined);
  }

  // Sign up
  async signUp(credentials: EmailPasswordProvider) {
    console.log("login auth");

    await createUserWithEmailAndPassword(this.auth, credentials.emailAddress, credentials.password)
    .then(res => { this.dataService.setData("signedIn", res);
      this.dataService.setData("uid", res.user.uid);
      this.dataService.setData("email", res.user.email);
      this.dataService.setData("username", credentials.username);
    });
  }

  async resetPassword(credentials: ForgotPassword) {
    console.log("reset password");

    await sendPasswordResetEmail(this.auth, credentials.emailAddress)
    .then(res => {  });
  }
  
  async googleSignin() {
    console.log("loginGoogle");

    // https://firebase.google.com/docs/auth/web/google-signin

    const googleSignInProvider = new GoogleAuthProvider();

    // googleSignInProvider.addScope("")

    // Diffent interfaces can be usd for desktop and mobile devices. I should check for this

console.log("id: " + googleSignInProvider.providerId);

    const auth = getAuth();
    await signInWithPopup(auth, googleSignInProvider)
      .then((result) => {
        // This gives you a Google Access Token. You can use it to access the Google API.
        console.log("result: " + JSON.stringify(result));
        const credential = GoogleAuthProvider.credentialFromResult(result);
        return credential;
        
        // ...
      }).catch((error) => {
        // Handle Errors here.
        const errorCode = error.code;
        const errorMessage = error.message;
        // The email of the user's account used.
        const email = error.email;
        // The AuthCredential type that was used.
        const credential = GoogleAuthProvider.credentialFromError(error);
        // ...
      });
  }

}
