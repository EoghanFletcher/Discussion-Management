import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FacadeService } from '../service/facade.service';
import { HttpClient } from '@angular/common/http'

@Component({
  selector: 'app-profile',
  templateUrl: './profile.page.html',
  styleUrls: ['./profile.page.scss'],
})
export class ProfilePage implements OnInit {

  data: any;

  constructor(private route: ActivatedRoute,
    private router: Router,
    private facadeService: FacadeService,
    private http: HttpClient) { }

  ngOnInit() {
    // if (this.route.snapshot.data.special) {
    //   this.data = this.route.snapshot.data.special;
    // }

    // console.log(JSON.stringify(this.facadeService.getDataDataService("signedIn")));
    // console.log(this.facadeService.getDataDataService("signedIn"));
    // this.data = this.facadeService.getDataDataService("signedIn");
    console.log("here");
    this.data = this.facadeService.getDataDataService("uid");
    this.getUserData();
  }

  getUserData() {
    console.log("getUserData");
    let url: string = "http://localhost:8080/api/user/test";
    let response = this.http.get(url).subscribe(responseLamdba => {console.log("Response: " + responseLamdba);}) ;
    
  }

}
