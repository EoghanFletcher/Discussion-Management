import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { FacadeService } from 'src/app/service/facade.service';
import {urlComponent} from '../../GlobalVariables/global-variables';

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

    let url = urlComponent + "groupAndTask/createTask";

    let taskNameString: string = this.formData.get("taskName").value;
    let taskDescriptionString: string = this.formData.get("taskDescription").value;
    let taskTypeString: string = this.formData.get("taskType").value;
    let dateTimeOfEventString: Date = this.formData.get("dateTimeOfEvent").value;

    let response = this.http.post(url, {"username": this.facadeService.getDataDataService("username"),
      	                                "groupName": this.facadeService.getDataDataService("id").key,
                                        "taskName": taskNameString,
                                      "taskDescription": taskDescriptionString,
                                      "taskType": taskTypeString,
                                      "dateTimeOfEvent": dateTimeOfEventString}
    ).subscribe(responseLamdba => { this.data = responseLamdba });    

    this.router.navigateByUrl("list-groups");
  }

  getEvents() {
    console.log("listEvents");

    let url = urlComponent + "groupAndTask/listEvents";
    let response = this.http.get(url).subscribe(responseLamdba => { this.events = responseLamdba });    
  }
}
