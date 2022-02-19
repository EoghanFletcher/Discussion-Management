import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FacadeService } from 'src/app/service/facade.service';

@Component({
  selector: 'app-create-group-btn',
  templateUrl: './create-group-btn.component.html',
  styleUrls: ['./create-group-btn.component.scss'],
})
export class CreateGroupBtnComponent implements OnInit {

  constructor(public facadeService: FacadeService,
    public router: Router) { }

  ngOnInit() {}

  createGroup() {
    this.router.navigateByUrl("create-group");
  }

}
