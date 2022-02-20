import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FacadeService } from 'src/app/service/facade.service';

@Component({
  selector: 'app-list-groups',
  templateUrl: './list-groups.page.html',
  styleUrls: ['./list-groups.page.scss'],
})
export class ListGroupsPage implements OnInit {

  data: any;

  constructor(private route: ActivatedRoute,
    private facadeService: FacadeService,
    private router: Router,
    private http: HttpClient) { }

  ngOnInit() {
    this.data = this.facadeService.getDataDataService("uid");

    this.getGroupsInvolvingUser();
  }

  getGroupsInvolvingUser() {
    console.log("getUserData");
    let url = "http://localhost:8080/api/groupAndTask/listGroups";
    let response = this.http.post(url, {"uId": this.facadeService.getDataDataService("uid"),
                                      "email": this.facadeService.getDataDataService("email")}
    ).subscribe(responseLamdba => { this.data = responseLamdba,
    console.log(responseLamdba[0]); });    
  }
}
