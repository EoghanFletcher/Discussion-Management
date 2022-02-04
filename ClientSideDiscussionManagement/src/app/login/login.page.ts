import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { EmailPasswordProvider } from '../interface/email-password-provider';
import { AuthenticationService } from '../service/authentication.service';
import { DataService } from '../service/data.service';

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
    private dataService: DataService,
    private authenticate: AuthenticationService
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

    await this.authenticate.login(auth);

    alert(JSON.stringify(this.dataService.getData("signedIn")));

    if (typeof this.dataService.getData("signedIn") !== 'undefined') {
      alert("Login successful")
      this.router.navigateByUrl("profile");  
    }
    else {
      alert("Login failed, Username or password is incorrect");
      this.router.navigateByUrl("login"); 
    }

  }

  navigateToPage(page) {
    console.log("navigateToPage");
    console.log(page)
    this.router.navigateByUrl(page);
  }

}
