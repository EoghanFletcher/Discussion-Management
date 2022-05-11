import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { EmailPasswordProvider } from '../../interface/email-password-provider';
import { FacadeService } from '../../service/facade.service';
import {urlComponent} from '../../GlobalVariables/global-variables';

@Component({
  selector: 'app-register',
  templateUrl: './register.page.html',
  styleUrls: ['./register.page.scss'],
})
export class RegisterPage implements OnInit {

  private postData: FormGroup;
  private email: FormControl;
  private password: FormControl;
  private username: FormControl;
  data: any;

  constructor(private router: Router,
    private route: ActivatedRoute,
    private facadeService: FacadeService,
    private http: HttpClient) { }

  ngOnInit() {
    this.postData = new FormGroup({
      email: new FormControl(),
      password: new FormControl(),
      username: new FormControl()
    });

    if (this.route.snapshot.data.special) {
      this.data = this.route.snapshot.data.special;
    }
  }

  ionViewWillEnter() {
    this.postData.get("email").setValue("");
    this.postData.get("password").setValue("");
    this.postData.get("username").setValue("");
  }

  async register() {
    console.log("register");
    this.email = this.postData.get("email").value;
    this.password = this.postData.get("password").value;
    this.password = this.postData.get("username").value;

    let emailString: string = this.postData.get("email").value;
    let passwordString: string = this.password = this.postData.get("password").value;
    let usernameString: string = this.username = this.postData.get("username").value;

    let auth:
    EmailPasswordProvider = {
      emailAddress: emailString,
      password: passwordString,
      username: usernameString
    }

    await this.facadeService.registerAuthenticationService(auth);

    // Add the user to the list of registered users
    this.addMasterList(emailString, usernameString);

    if (typeof this.facadeService.getDataDataService("signedIn") !== 'undefined') {
      this.router.navigateByUrl("profile");  
    }
  }

  navigateToPage(page) {
    console.log("navigateToPage");
    this.router.navigateByUrl(page);
  }

  addMasterList(email, username) {
    console.log("addMasterList");
    console.log("uId: " + this.facadeService.getDataDataService("uid"));
    console.log("username: " + this.facadeService.getDataDataService("username"));

    let url = urlComponent + "employeeAttendance/addMasterList";
    let response = this.http.post(url, {"username": username }).subscribe(responseLamdba => { 
      console.log(JSON.stringify(responseLamdba))
   });   
  }
}
