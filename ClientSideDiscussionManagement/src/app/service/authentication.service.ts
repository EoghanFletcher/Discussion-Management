
import { Injectable } from '@angular/core';
import { Firestore } from '@angular/fire/firestore';
import { createUserWithEmailAndPassword, getAuth, GoogleAuthProvider, linkWithPopup, sendPasswordResetEmail, signInWithEmailAndPassword, signInWithPopup, signOut } from 'firebase/auth';
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
    .then((res) => {this.dataService.setData("signedIn", res);
    this.dataService.setData("uid", res.user.uid);
    this.dataService.setData("email", res.user.email);

    console.log("result: " + JSON.stringify(res.user));

    console.log("\n\n\n\n\n");

    let token =  JSON.stringify(res.user.getIdTokenResult().then(
      (x) => {
      console.log("token: " + x.token),
      this.dataService.setData("accessToken", x.token);
      }
    ));


    // console.log("providerId: " + JSON.stringify(res.providerId))

    console.log("\n\n\n\n\n");

        // let credential = GoogleAuthProvider.credentialFromResult(res);

        // console.log("credential: " + credential);
    });


    
// // Sign in using a popup.
// const provider = new GoogleAuthProvider();
// // provider.addScope('profile');
// // provider.addScope('email');
// const result = await signInWithPopup(this.auth, provider);

// console.log("==============================================")
// console.log("\n")
// console.log("result: " + JSON.stringify(result));
// console.log("\n")

// console.log("==============================================")
// console.log("\n")
// // The signed-in user info.
// const user = result.user;
// console.log("result.user: " + JSON.stringify(user));

// console.log("\n")
// console.log("==============================================")
// console.log("\n")

// // This gives you a Google Access Token.
// const credential = GoogleAuthProvider.credentialFromResult(result);

// console.log("\n")
// console.log("==============================================")
// console.log("\n")

// console.log("credential: " + credential);
// console.log("\n")
// console.log("==============================================")
// console.log("\n")
// const token = credential.accessToken;

// console.log("token: " + token)



  }

  async logout() {
    await signOut(this.auth).then(res => {
    console.log("SignedOut") });
    this.dataService.setData("signedIn", undefined);
    this.dataService.setData("uid", undefined);
  }

  // Sign up
  async signUp(credentials: EmailPasswordProvider) {
    console.log("signUp");

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
  

  
  async googleSigninNewAccount() {
    console.log("googleSigninNewAccount");

    // https://firebase.google.com/docs/auth/web/google-signin

    const googleSignInProvider = new GoogleAuthProvider();

    // googleSignInProvider.addScope("")

    // Diffent interfaces can be usd for desktop and mobile devices. I should check for this

    const auth = getAuth();
    await signInWithPopup(auth, googleSignInProvider)
      .then((result) => {
        // This gives you a Google Access Token. You can use it to access the Google API.
        // console.log("result: " + JSON.stringify(result));
        const credential = GoogleAuthProvider.credentialFromResult(result);

        
        console.log(JSON.stringify(result));
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

  async googleSigninExistingAccount() {
    console.log("googleSigninExistingAccount");

    // https://firebase.google.com/docs/auth/web/google-signin

    const googleSignInProvider = new GoogleAuthProvider();

    
    // Diffent interfaces can be usd for desktop and mobile devices. I should check for this
    const auth = getAuth();
    //  Link accounts
    await linkWithPopup(auth.currentUser, googleSignInProvider).then((result) => {
      console.log("-linkWithPopup")
      // Accounts successfully linked.
      const credential = GoogleAuthProvider.credentialFromResult(result);
      const user = result.user;
      }).catch((error) => {
        console.log("error");
        const errorCode = error.code;
        const errorMessage = error.message;
        const email = error.email;
        console.log("error message, code: " + errorCode);

        const credential = GoogleAuthProvider.credentialFromError(error);
        // ...
      });


    // Get oAuth credentials
    await signInWithPopup(auth, googleSignInProvider).then((result) => {
    console.log("-signInWithPopup");
    console.log("here3")
    // This gives you a Google Access Token. You can use it to access the Google API.
    const credential = GoogleAuthProvider.credentialFromResult(result);
    const token = credential.accessToken;
    // The signed-in user info.
    const user = result.user;
    console.log("token: " + token)
    return result;
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
