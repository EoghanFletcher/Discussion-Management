import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { SelectedCredential } from '../interface/selected-credential';
import { FacadeService } from '../service/facade.service';

@Component({
  selector: 'app-profile-crud',
  templateUrl: './profile-crud.page.html',
  styleUrls: ['./profile-crud.page.scss'],
})
export class ProfileCrudPage implements OnInit {

  credential: SelectedCredential;
  private postData: FormGroup;
  private email: FormControl;
  private password: FormControl;

  constructor(private route: ActivatedRoute,
    facadeService: FacadeService) { }

  ngOnInit() {
    this.postData = new FormGroup({
      email: new FormControl(),
      password: new FormControl()
    });
    
    if (this.route.snapshot.data.special) {
      this.credential = this.route.snapshot.data.special;
    console.log("credential: " + this.credential.key);
    console.log("credential: " + this.credential.value);
    }
  }

  update() {
    console.log("update");
  }

  deleteCredential() {
    console.log("deleteCredential");

    // let url = "http://localhost:8080/api/credentials/delete";
    // let response = this.http.post(url, {"uId": this.facadeService.getDataDataService("uid")}
    // ).subscribe(responseLamdba => { this.data = responseLamdba });    
  }

}
