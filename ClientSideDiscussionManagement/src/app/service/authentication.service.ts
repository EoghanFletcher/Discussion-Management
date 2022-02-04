
import { Injectable } from '@angular/core';
import { Firestore } from '@angular/fire/firestore';
import { createUserWithEmailAndPassword, getAuth, sendPasswordResetEmail, signInWithEmailAndPassword, signOut } from 'firebase/auth';
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
    console.log("Email: " + credentials.emailAddress);
    console.log("Password: " + credentials.password);


    await signInWithEmailAndPassword(this.auth, credentials.emailAddress, credentials.password)
    .then(res => {this.dataService.setData("signedIn", res),
    console.log("data: " + JSON.stringify(this.dataService.getData("signedIn")));
    this.dataService.setData("isLoggedIn", true) });
  }

  async logout() {
    await signOut(this.auth).then(res => { 
      this.dataService.setData("isLoggedIn", false),
    console.log("SignedOut") });
    this.dataService.setData("signedIn", undefined);
  }

  // Sign up
  async signUp(credentials: EmailPasswordProvider) {
    console.log("login auth");
    console.log("Email: " + credentials.emailAddress);
    console.log("Password: " + credentials.password);

    await createUserWithEmailAndPassword(this.auth, credentials.emailAddress, credentials.password)
    .then(res => {console.log("res: " + res),
      this.dataService.setData("signedIn", res),
    console.log("data: " + JSON.stringify(this.dataService.getData("signedIn")));
    this.dataService.setData("isLoggedIn", true) });
  }

  async resetPassword(credentials: ForgotPassword) {
    console.log("reset password");
    console.log("Email: " + credentials.emailAddress);

    await sendPasswordResetEmail(this.auth, credentials.emailAddress)
    .then(res => {console.log("res: " + res)});
  }

}
