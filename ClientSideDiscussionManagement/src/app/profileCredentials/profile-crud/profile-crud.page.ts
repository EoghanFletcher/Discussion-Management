import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { SelectedCredential } from '../../interface/selected-credential';
import { FacadeService } from '../../service/facade.service';
import {urlComponent} from '../../GlobalVariables/global-variables';

@Component({
  selector: 'app-profile-crud',
  templateUrl: './profile-crud.page.html',
  styleUrls: ['./profile-crud.page.scss'],
})
export class ProfileCrudPage implements OnInit {

  data: any;
  credential: SelectedCredential;
  private postData: FormGroup;
  private value: FormControl;

  constructor(private route: ActivatedRoute,
    private facadeService: FacadeService,
    private http: HttpClient,
    private router: Router) { }

  ngOnInit() {
    this.postData = new FormGroup({
      value: new FormControl()
    });
    
    this.data = this.facadeService.getDataDataService("uid");

    if (this.route.snapshot.data.special) {
      this.credential = this.route.snapshot.data.special;
    }
  }

  update() {
    console.log("update");

    let chosenValue: string = this.postData.get("value").value;
    let url = urlComponent + "credentials/update";
    let response = this.http.post(url, {"uId": this.facadeService.getDataDataService("uid"),
                                        "username": this.facadeService.getDataDataService("username"),
                                        "chosenKey": this.facadeService.getDataDataService("id").key,
                                      "chosenValue": chosenValue}
    ).subscribe(responseLamdba => { this.data = responseLamdba });    
    this.router.navigateByUrl("profile");
  }

  deleteCredential(keySelected) {
    console.log("deleteCredential");

    if (keySelected !== "email" ||
        keySelected !== "uId" || 
        keySelected !== "username") {
          console.log("not equal");
    let url = urlComponent + "credentials/delete";
    let response = this.http.post(url, {"uId": this.facadeService.getDataDataService("uid"),
                                        "username": this.facadeService.getDataDataService("username"),
                                        "deleteKey": keySelected}
    ).subscribe(responseLamdba => { this.data = responseLamdba });
  }
    this.router.navigateByUrl("profile");
  }
}
