import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FacadeService } from '../../service/facade.service';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http'
import { SelectedCredential } from '../../interface/selected-credential';
import { ModalController } from '@ionic/angular';
import { ModalNavigationComponentComponent } from 'src/app/NavigationMenuModal/modal-navigation-component/modal-navigation-component.component';
import { EmailPasswordProvider } from 'src/app/interface/email-password-provider';
import { DataService } from 'src/app/service/data.service';
import { getAuth, GoogleAuthProvider, signInWithPopup } from 'firebase/auth';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.page.html',
  styleUrls: ['./profile.page.scss'],
})
export class ProfilePage implements OnInit {

  data: any;
  count: any = 0;

  constructor(private route: ActivatedRoute,
    private router: Router,
    private facadeService: FacadeService,
    private http: HttpClient,
    private dataService: DataService) { }

  ngOnInit() {
  }

  ionViewWillEnter() {
    this.data = this.facadeService.getDataDataService("uid");

    this.getUserData();
  }
  
  getUserData() {
    console.log("getUserData");
    let url = "http://localhost:8080/api/user/authenticateEmailPassword";

    let response = this.http.post<EmailPasswordProvider>(url, {"uId": this.facadeService.getDataDataService("uid"),
                                      "email": this.facadeService.getDataDataService("email"),
                                      "username": this.facadeService.getDataDataService("username")}
    ).subscribe(responseLamdba => { this.data = responseLamdba,
      this.dataService.setData("username", responseLamdba.username);
    });
  }

  async loginGoogleExistingAccount() { // Needed to getEmail address // https://firebase.google.com/docs/auth/web/account-linking#:~:text=You%20can%20allow%20users%20to,they%20used%20to%20sign%20in.
    console.log("loginGoogleExistingAccount");
    let result;
    result = await this.facadeService.authenticationService.googleSigninExistingAccount().then((details: any) => {
        console.log("details: " + details),
        console.log(JSON.stringify(details));
    });
  }


  createCredential() {
    console.log("createCredential");
    this.router.navigateByUrl("create-credential");
  }

  modifyCredential(keySeleted, valueSelected) {
    console.log("modifyCredential");

    let credential:
    SelectedCredential = {
      key: keySeleted,
      value: valueSelected
    }

    this.facadeService.setDataDataService("id", credential);
    this.router.navigateByUrl("profile-crud/id");
  }

}
