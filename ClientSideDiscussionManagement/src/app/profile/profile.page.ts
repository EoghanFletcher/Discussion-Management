import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FacadeService } from '../service/facade.service';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http'

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
    console.log("here");
    this.data = this.facadeService.getDataDataService("uid");

    console.log("profile uId: " + this.facadeService.getDataDataService("uid"));
    this.getUserData();
  }

  getUserData() {
    console.log("getUserData");
    let url = "http://localhost:8080/api/user/authenticate";
    let response = this.http.post(url, {"uId": this.facadeService.getDataDataService("uid")}
    ).subscribe(responseLamdba => { this.data = responseLamdba });    
  }
}
