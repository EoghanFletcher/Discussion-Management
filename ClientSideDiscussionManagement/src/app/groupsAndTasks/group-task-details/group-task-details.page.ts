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

  if (this.route.snapshot.data.special) {
    this.group = this.route.snapshot.data.special;

    console.log("group: " + this.group.administration);
  }
  this.getTasks();
}

createTask() {
  console.log("createTask");
  this.router.navigateByUrl("create-task/id");
}

getTasks() {
  console.log("getTasks");

  let url = "http://localhost:8080/api/groupAndTask/listTasks";
    let response = this.http.post(url, {"uId": this.facadeService.getDataDataService("uid"),
                                       "groupName": this.group.key}
    ).subscribe(responseLamdba => { this.data = responseLamdba });
}

deleteDeactivateTask(taskName) {
console.log("deleteDeactivateTask");

  let url = "http://localhost:8080/api/groupAndTask/deactivateTask";
    let response = this.http.post(url, {"groupName": this.group.key,
                                      "taskName": taskName}
    ).subscribe(responseLamdba => { this.data = responseLamdba });
}

}

