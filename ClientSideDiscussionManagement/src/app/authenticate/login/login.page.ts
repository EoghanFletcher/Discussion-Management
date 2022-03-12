import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { getAuth, GoogleAuthProvider, signInWithPopup } from 'firebase/auth';
import { EmailPasswordProvider } from '../../interface/email-password-provider';
import { FacadeService } from '../../service/facade.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.page.html',
  styleUrls: ['./login.page.scss'],
})
export class LoginPage implements OnInit {

  private postData: FormGroup;
  private email: FormControl;
  private password: FormControl;
  data: any;

  constructor(private router: Router,
    private route: ActivatedRoute,
    private facadeService: FacadeService) { }

  ngOnInit() {
    this.postData = new FormGroup({
      email: new FormControl(),
      password: new FormControl()
    });

    if (this.route.snapshot.data.special) {
      this.data = this.route.snapshot.data.special;
    }
  }

  ionViewWillEnter() {
    this.postData.get("email").setValue("");
    this.postData.get("password").setValue("");
  }

  async loginEmailAndPassword() {
    console.log("loginEmailAndPassword");
    this.email = this.postData.get("email").value;
    this.password = this.postData.get("password").value;

    let emailString: string = this.postData.get("email").value;
    let passwordString: string = this.postData.get("password").value;

    let auth:
    EmailPasswordProvider = {
      emailAddress: emailString,
      password: passwordString
    }

    
    await this.facadeService.loginAuthenticationService(auth);

    if (typeof this.facadeService.getDataDataService("signedIn") !== 'undefined') {
      console.log("signedin")
      this.router.navigateByUrl("profile");  
    }
    else {
      // Display a message // I am still on the login page
    }

  }

  loginGoogleNewAccount() { // Needed to getEmail address
    console.log("loginGoogleNewAccount");
    let result;
    result = this.facadeService.authenticationService.googleSigninNewAccount().then((details: any) => {
    // const token = credential.accessToken;
        // The signed-in user info.
        // const user = result.user;
        
    //     console.log("details: " + details),
    //     console.log(JSON.stringify(details));

    //     console.log("result: " + JSON.stringify(details));
    //     const credential = details.user.providerId;
    //     console.log("credential: " + credential);
    //     // const credential = GoogleAuthProvider.credentialFromResult(details);

    //     console.log("\n\n\n\n")
    // const token = credential.accessToken;

    // console.log("access token: " + token);
        // console.log("user: " + JSON.stringify(user));

  

    // alert("access token: " + token);

    });

    console.log("result: " + result);
  }

  navigateToPage(page) {
    console.log("navigateToPage");
    this.router.navigateByUrl(page);
  }
}
