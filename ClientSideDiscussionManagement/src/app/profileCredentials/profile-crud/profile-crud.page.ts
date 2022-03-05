import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { SelectedCredential } from '../../interface/selected-credential';
import { FacadeService } from '../../service/facade.service';

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
    console.log("id: " + JSON.stringify(this.facadeService.getDataDataService("id")));

    if (this.route.snapshot.data.special) {
      this.credential = this.route.snapshot.data.special;
    console.log("credential: " + this.credential.key);
    console.log("credential: " + this.credential.value);
    }
  }

  update() {
    console.log("update");

    // console.log("keySeleted: " + keySeleted);

    let chosenValue: string = this.postData.get("value").value;

    let url = "http://localhost:8080/api/credentials/update";
    let response = this.http.post(url, {"uId": this.facadeService.getDataDataService("uid"),
                                        "chosenKey": this.facadeService.getDataDataService("id").key,
                                      "chosenValue": chosenValue}
    ).subscribe(responseLamdba => { this.data = responseLamdba });    

    this.router.navigateByUrl("profile");
  }

  deleteCredential(keySeleted) {
    console.log("deleteCredential");

    console.log("keySeleted: " + keySeleted);

    let url = "http://localhost:8080/api/credentials/delete";
    let response = this.http.post(url, {"uId": this.facadeService.getDataDataService("uid"),
                                        "deleteKey": keySeleted}
    ).subscribe(responseLamdba => { this.data = responseLamdba });    

    this.router.navigateByUrl("profile");
  }

}
