import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { SelectedGroup } from 'src/app/interface/selected-group';
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

  ionViewWillEnter() {
    this.getGroupsInvolvingUser();
  }

  createGroup() {
    this.router.navigateByUrl("create-group");
  }

  getGroupsInvolvingUser() {
    console.log("getUserData");
    let url = "http://localhost:8080/api/groupAndTask/listGroups";
    let response = this.http.post(url, {"uId": this.facadeService.getDataDataService("uid"),
                                      "username": this.facadeService.getDataDataService("username")}
    ).subscribe(responseLamdba => { this.data = responseLamdba });    
  }

  viewGroup(keySeleted, valueSelected) {
    console.log("viewGroup");

    let group:
    SelectedGroup = {
      key: keySeleted,
      value: valueSelected,
      administration: null,
      requestsToLeave: null,
      members: null
    }

    let username = this.facadeService.getDataDataService("username");

    this.data.forEach(element => {
      if (element.groupName === keySeleted) {

        let administration = element.Administration;

        if(administration.hasOwnProperty(username)) {
        group.administration = username;
        }

        group.requestsToLeave = element.RequestToLeave;
        group.members = element.Membership;
      }
      

    this.facadeService.setDataDataService("id", group);
    this.router.navigateByUrl("group-task-details/id");
  });
  }
}
