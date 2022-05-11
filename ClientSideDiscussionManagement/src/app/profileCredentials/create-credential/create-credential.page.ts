import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { SelectedCredential } from 'src/app/interface/selected-credential';
import { FacadeService } from 'src/app/service/facade.service';
import {urlComponent} from '../../GlobalVariables/global-variables';

@Component({
  selector: 'app-create-credential',
  templateUrl: './create-credential.page.html',
  styleUrls: ['./create-credential.page.scss'],
})
export class CreateCredentialPage implements OnInit {

  data: any;
  credential: SelectedCredential;
  private postData: FormGroup;
  private chosenKey: FormControl;
  private chosenValue: FormControl;

  constructor(private route: ActivatedRoute,
    private facadeService: FacadeService,
    private router: Router,
    private http: HttpClient) { }

  ngOnInit() {
    this.postData = new FormGroup({
      chosenKey: new FormControl(),
      chosenValue: new FormControl()
    });

    this.data = this.facadeService.getDataDataService("uid");
  }

  create() {
    console.log("create");

    let chosenKey: string = this.postData.get("chosenKey").value;
    let chosenValue: string = this.postData.get("chosenValue").value;

    let url = urlComponent + "credentials/create";
    let response = this.http.post(url, {"uId": this.facadeService.getDataDataService("uid"),
                                        "username": this.facadeService.getDataDataService("username"),
                                        "chosenKey": chosenKey,
                                        "chosenValue": chosenValue}
    ).subscribe(responseLamdba => { this.data = responseLamdba });
    this.router.navigateByUrl("profile");  
  }
}
