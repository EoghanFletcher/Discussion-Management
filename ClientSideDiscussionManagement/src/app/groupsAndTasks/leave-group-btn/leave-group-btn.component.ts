import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Route, Router } from '@angular/router';
import { FacadeService } from 'src/app/service/facade.service';

@Component({
  selector: 'app-leave-group-btn',
  templateUrl: './leave-group-btn.component.html',
  styleUrls: ['./leave-group-btn.component.scss'],
})
export class LeaveGroupBtnComponent implements OnInit {

  constructor(public facadeService: FacadeService,
    public router: Router,
    public http: HttpClient) { }

  ngOnInit() {}

  leaveGroup() {
    console.log("leaveGroup");
    let url = "http://localhost:8080/api/groupAndTask/requestToLeaveGroup";

    let response = this.http.post(url, {"groupName": this.facadeService.getDataDataService("groupName"),
                                      "username": this.facadeService.getDataDataService("username")}
    ).subscribe(responseLamdba => {  });
  }

}
