import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { EmailPasswordProvider } from '../interface/email-password-provider';
import { AuthenticationService } from '../service/authentication.service';
import { DataService } from '../service/data.service';
import { FacadeService } from '../service/facade.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.page.html',
  styleUrls: ['./register.page.scss'],
})
export class RegisterPage implements OnInit {

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

  async register() {
    console.log("register");
    this.email = this.postData.get("email").value;
    this.password = this.postData.get("password").value;

    console.log("emailAddressInput: " + this.email);
    console.log("passwordInput: " + this.password);

    // String
    let emailAddressString: string = this.postData.get("email").value;
    let passwordString: string = this.password = this.postData.get("password").value;

    let auth:
    EmailPasswordProvider = {
      emailAddress: emailAddressString,
      password: passwordString
    }

    await this.facadeService.registerAuthenticationService(auth);

    alert(JSON.stringify(this.facadeService.getDataDataService("signedIn")));

    if (typeof this.facadeService.getDataDataService("signedIn") !== 'undefined') {
      alert("signUp successful")
      this.router.navigateByUrl("profile");  
    }
    else {
      alert("SignUp failed, Username or password is incorrect");
      this.router.navigateByUrl("login"); 
    }
  }

  navigateToPage(page) {
    console.log("navigateToPage");
    console.log(page)
    this.router.navigateByUrl(page);
  }

}
