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
    public router: Router) { }

    message: string = "requestToLeaveGroup";

  ngOnInit() {}

  leaveGroup() {
    console.log("leaveGroup");
  }

}
