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

  async ionViewWillEnter() {
    this.data = this.facadeService.getDataDataService("uid");

    await this.getUserData();
  }
  
  async getUserData() {
    console.log("getUserData");
    // this.data = await this.facadeService.getUseInformation();
    // console.log("here3: " + JSON.stringify(this.data))

    let url = "http://localhost:8080/api/user/authenticateEmailPassword";

    let response = this.http.post<EmailPasswordProvider>(url, {"uId": this.facadeService.getDataDataService("uid"),
                                      "email": this.facadeService.getDataDataService("email"),
                                      "username": this.facadeService.getDataDataService("username")}
    ).subscribe(responseLamdba => { console.log("responseLamdba: " + JSON.stringify(responseLamdba)),
    console.log("responseLamdba.username: " + responseLamdba.username)
    this.data = responseLamdba,
      this.facadeService.setDataDataService("username", responseLamdba.username);
      this.facadeService.setDataDataService("userCredentials", responseLamdba);

      console.log("get: " + this.facadeService.getDataDataService("username")),

      // After the data has been retrieved confirm the user has logged in today
      this.confirmAttendance();
    });
  }

  async linkGoogleAccount() { // Needed to getEmail address // https://firebase.google.com/docs/auth/web/account-linking#:~:text=You%20can%20allow%20users%20to,they%20used%20to%20sign%20in.
    console.log("linkGoogleAccount");
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

  async confirmAttendance() {
    console.log("confirmAttendance");
    console.log("uId: " + this.facadeService.getDataDataService("uid"));
    console.log("username: " + this.facadeService.getDataDataService("username"));

    let url = "http://localhost:8080/api/employeeAttendance/confirmAttendance";
    let response = this.http.post(url, {"uId": this.facadeService.getDataDataService("uid"),
                                              "username": this.facadeService.getDataDataService("username")}).subscribe(responseLamdba => { 
      console.log(JSON.stringify(responseLamdba))
   });    
  }

}
