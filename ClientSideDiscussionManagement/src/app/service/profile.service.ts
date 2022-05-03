import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { EmailPasswordProvider } from '../interface/email-password-provider';
import { DataService } from './data.service';
import {urlComponent} from '../GlobalVariables/global-variables';


@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  data: any;

  constructor(private dataService: DataService,
           private http: HttpClient,) { }

async getUserData() {
  console.log("profileService:getUserData")
  let url = urlComponent + "user/authenticateEmailPassword";

    // let response = 
    await this.http.post<EmailPasswordProvider>(url, {"uId": this.dataService.getData("uid"),
                                      "email": this.dataService.getData("email"),
                                      "username": this.dataService.getData("username")}
    ).subscribe(responseLamdba => { this.data = responseLamdba,
      this.dataService.setData("username", responseLamdba.username);
      console.log(JSON.stringify("here1: " + JSON.stringify(this.data)));
      // return this.data;
    });
}

}
