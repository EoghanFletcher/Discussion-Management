import { Component, OnInit } from '@angular/core';
import { Route, Router } from '@angular/router';
import { FacadeService } from 'src/app/service/facade.service';

@Component({
  selector: 'app-create-task-btn',
  templateUrl: './create-task-btn.component.html',
  styleUrls: ['./create-task-btn.component.scss'],
})
export class CreateTaskBtnComponent implements OnInit {

  constructor(public facadeService: FacadeService,
    public router: Router) { }

  ngOnInit() {}

  createTask() {
    console.log("createTask");
    this.router.navigateByUrl("create-task");
  }

}
