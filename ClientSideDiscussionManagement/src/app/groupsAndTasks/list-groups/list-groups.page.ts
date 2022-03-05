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
    ).subscribe(responseLamdba => { this.data = responseLamdba,
      console.log("responseLamdba: " + JSON.stringify(responseLamdba)),
    // console.log("data: " + JSON.stringify(this.data[0]))
    console.log("data: " + JSON.stringify(this.data[0].Administration))
    });    
  }

  viewGroup(keySeleted, valueSelected) {
    console.log("viewGroup");
    console.log("keySelected: " + keySeleted);
    console.log("valueSelected: " + valueSelected);

    let group:
    SelectedGroup = {
      key: keySeleted,
      value: valueSelected,
      administration: null
    }

    let username = this.facadeService.getDataDataService("username");

    this.data.forEach(element => {
      console.log("element: " + JSON.stringify(element));
      // console.log("element group name: " + element.groupName)
      if (element.groupName === keySeleted) {
        console.log("here");
        console.log("administration: " + JSON.stringify(element.Administration));

        let administration = element.Administration;
        console.log(administration.hasOwnProperty(username));
        if(administration.hasOwnProperty(username)) {
        group.administration = username;
        console.log("group admin: " + group.administration);
        }
        // console.log("administration: " + element.Administration);
        // console.log("username: " + element.Administration.username);
        // group.administration = element.Administration.get .{username};
      }
      else {
        console.log("no");
      }

    this.facadeService.setDataDataService("id", group);
    this.router.navigateByUrl("group-task-details/id");
  });
  }
}
