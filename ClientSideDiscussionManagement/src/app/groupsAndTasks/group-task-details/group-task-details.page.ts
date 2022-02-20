import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { SelectedGroup } from 'src/app/interface/selected-group';
import { FacadeService } from 'src/app/service/facade.service';

@Component({
  selector: 'app-group-task-details',
  templateUrl: './group-task-details.page.html',
  styleUrls: ['./group-task-details.page.scss'],
})
export class GroupTaskDetailsPage implements OnInit {

  data: any;
  group: SelectedGroup;

  constructor(private route: ActivatedRoute,
    private facadeService: FacadeService,
    private http: HttpClient,
    private router: Router) { }

  ngOnInit() {
  this.data = this.facadeService.getDataDataService("uid");
  console.log("id: " + JSON.stringify(this.facadeService.getDataDataService("id")));

  if (this.route.snapshot.data.special) {
    this.group = this.route.snapshot.data.special;
  console.log("group: " + this.group.key);
  console.log("group: " + this.group.value);
  console.log("group: " + this.group);
  }

  this.getTasks();
}

createTask() {
  console.log("createTask");
  this.router.navigateByUrl("create-task/id");
}

getTasks() {
  console.log("getTasks");

  console.log("key: " + this.group)

  // let groupNameString: string = this.formData.get("dateTimeOfEvent").value;

  let url = "http://localhost:8080/api/groupAndTask/listTasks";
    let response = this.http.post(url, {"uId": this.facadeService.getDataDataService("uid"),
                                       "groupName": this.group.key}
    ).subscribe(responseLamdba => { this.data = responseLamdba,
    console.log(responseLamdba); });
}


}

