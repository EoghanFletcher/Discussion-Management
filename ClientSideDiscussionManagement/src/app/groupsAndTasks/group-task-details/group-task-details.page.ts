import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { SelectedGroup } from 'src/app/interface/selected-group';
import { FacadeService } from 'src/app/service/facade.service';
import {urlComponent} from '../../GlobalVariables/global-variables';

@Component({
  selector: 'app-group-task-details',
  templateUrl: './group-task-details.page.html',
  styleUrls: ['./group-task-details.page.scss'],
})
export class GroupTaskDetailsPage implements OnInit {

  data: any;
  group: SelectedGroup;
  requestToLeaveData: any;
  search: any
  subString: any

  private formData: FormGroup;
  private chosenUser: FormControl;

  constructor(private route: ActivatedRoute,
    private facadeService: FacadeService,
    private http: HttpClient,
    private router: Router) { }

  ngOnInit() {
  this.data = this.facadeService.getDataDataService("uid");

  this.formData = new FormGroup({
    chosenUser: new FormControl()
  });

  if (this.route.snapshot.data.special) {
    this.group = this.route.snapshot.data.special;
    this.facadeService.setDataDataService("groupName", this.group.key);

    if (this.group.requestsToLeave != null) {
    this.requestToLeaveData = Object.keys(this.group.requestsToLeave);
  }

  // if (this.group.members != null) {
  //   console.log("yes, member");
  //   this.search = Object.keys(this.group.members);
  //   console.log("search[0]: " + this.search[0]);
  // }
  else {
    console.log("no, member");
  }

    // console.log("typeof: " + typeof(this.requestToLeaveData[0]));
    // console.log("requestToLeaveData[0]: " + this.requestToLeaveData[0]);
  }
  this.getTasks();

  this.getAllUsers();
}

getAllUsers() {
  console.log("getAllUsers");

  let url = urlComponent + "user/getAllUsers";
    let response = this.http.post(url, {  }
    ).subscribe(responseLamdba => { // this.data = responseLamdba,
                                    console.log("responseLamdba: " + JSON.stringify(responseLamdba)),
                                    this.search = responseLamdba; });
}

getStaff(event) {
  console.log("setStaff");

  let subString: string = event.target.value;

  console.log("SubString: " + subString);

  if (subString != "" && subString != null) {
    console.log("not null");
    // this.subString = this.search.filter(subString);
    this.subString = this.search.filter((subString) => {
      console.log("this: " + this.subString);
    })
  }
}

addMember() {
  console.log("addMember");

  let chosenUser: string = this.formData.get("chosenUser").value;

  let url = urlComponent + "groupAndTask/groupAddMember";
    let response = this.http.post(url, {"username": chosenUser,
                                       "groupName": this.group.key}
    ).subscribe(responseLamdba => { this.data = responseLamdba });
}

requestsToLeaveVerdict(verdict, username) {
  console.log("requestsToLeaveVerdict");
  
  console.log("username: " + username);
  console.log("groupName: " + this.group.key);
  console.log("verdict: " + verdict);

  let url = urlComponent + "groupAndTask/leaveGroupVerdict";
    let response = this.http.post(url, {"username": username,
                                       "groupName": this.group.key,
                                        "verdict": verdict}
    ).subscribe(responseLamdba => { this.data = responseLamdba });
}

createTask() {
  console.log("createTask");
  this.router.navigateByUrl("create-task/id");
}

getTasks() {
  console.log("getTasks");

  let url = urlComponent + "groupAndTask/listTasks";
    let response = this.http.post(url, {"uId": this.facadeService.getDataDataService("uid"),
                                       "groupName": this.group.key}
    ).subscribe(responseLamdba => { this.data = responseLamdba });
}

deleteDeactivateTask(taskName) {
console.log("deleteDeactivateTask");

  let url = urlComponent + "groupAndTask/deactivateTask";
    let response = this.http.post(url, {"groupName": this.group.key,
                                      "taskName": taskName}
    ).subscribe(responseLamdba => { this.data = responseLamdba });
}

}

