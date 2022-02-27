import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { FacadeService } from 'src/app/service/facade.service';

@Component({
  selector: 'app-create-task',
  templateUrl: './create-task.page.html',
  styleUrls: ['./create-task.page.scss'],
})
export class CreateTaskPage implements OnInit {

  private formData: FormGroup;
  private taskName: FormControl;
  private taskDescription: FormControl;
  private taskType: FormControl;
  private dateTimeOfEvent: FormControl;
  data: any;
  events: any;

  constructor(private router: Router,
    private route: ActivatedRoute,
    private facadeService: FacadeService,
    private http: HttpClient) { }

  ngOnInit() {
    this.formData = new FormGroup({
      taskName: new FormControl(),
      taskDescription: new FormControl(),
      taskType: new FormControl(),
      dateTimeOfEvent: new FormControl()
    });
    this.getEvents();
  }

  createTask() {
    console.log("createTask");

    console.log("Group Name: " + this.facadeService.getDataDataService("id"));
    let url = "http://localhost:8080/api/groupAndTask/createTask";

    let taskNameString: string = this.formData.get("taskName").value;
    let taskDescriptionString: string = this.formData.get("taskDescription").value;
    let taskTypeString: string = this.formData.get("taskType").value;
    let dateTimeOfEventString: Date = this.formData.get("dateTimeOfEvent").value;

    console.log("date: " + this.formData.get("dateTimeOfEvent").value);

      // console.log("date: " + dateTimeOfEventString.getDate());
      // console.log("time: " + dateTimeOfEventString.getTime());
      // console.log("toLocaleDateString: " + dateTimeOfEventString.toLocaleDateString());
    let response = this.http.post(url, {"username": this.facadeService.getDataDataService("username"),
      	                                "groupName": this.facadeService.getDataDataService("id").key,
                                        "taskName": taskNameString,
                                      "taskDescription": taskDescriptionString,
                                      "taskType": taskTypeString,
                                      "dateTimeOfEvent": dateTimeOfEventString}
    ).subscribe(responseLamdba => { this.data = responseLamdba });    
9
    this.router.navigateByUrl("list-groups");
  }

  getEvents() {
    console.log("listEvents");

    console.log("Group Name: " + this.facadeService.getDataDataService("id"));
    let url = "http://localhost:8080/api/groupAndTask/listEvents";

    let response = this.http.get(url).subscribe(responseLamdba => { this.events = responseLamdba,
    console.log(JSON.stringify(responseLamdba)); });    
  }

}
