import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { EmailPasswordProvider } from '../interface/email-password-provider';
import { AuthenticationService } from '../service/authentication.service';
import { DataService } from '../service/data.service';
import { FacadeService } from '../service/facade.service';

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
    private facadeService: FacadeService
    ) { }

  ngOnInit() {
    this.postData = new FormGroup({
      email: new FormControl(),
      password: new FormControl()
    });

    if (this.route.snapshot.data.special) {
      this.data = this.route.snapshot.data.special;
    }
  }

  async login() {
    console.log("login");
    this.email = this.postData.get("email").value;
    this.password = this.postData.get("password").value;

    let auth:
    EmailPasswordProvider = {
      emailAddress: String = this.postData.get("email").value,
      password: String = this.postData.get("password").value
    }

    await this.facadeService.loginAuthenticationService(auth);

    if (typeof this.facadeService.getDataDataService("signedIn") !== 'undefined') {
      console.log("profile");
      this.router.navigateByUrl("profile");  
    }
    else {
      // Display a message // I am still on the login page
    }

  }

  navigateToPage(page) {
    console.log("navigateToPage");
    console.log(page)
    this.router.navigateByUrl(page);
  }
}
