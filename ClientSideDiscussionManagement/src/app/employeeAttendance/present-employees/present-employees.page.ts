import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FacadeService } from 'src/app/service/facade.service';
import {urlComponent} from '../../GlobalVariables/global-variables';

@Component({
  selector: 'app-present-employees',
  templateUrl: './present-employees.page.html',
  styleUrls: ['./present-employees.page.scss'],
})
export class PresentEmployeesPage implements OnInit {
  data: any;
  notes: any;

  constructor(private route: ActivatedRoute,
    private facadeService: FacadeService,
    private router: Router,
    private http: HttpClient) { }

  ngOnInit() {
    console.log("ngOnInit");
    this.getList();
    this.getNotes();
  }

  ionViewWillEnter() {
    console.log("ionViewWillEnter");
    // this.getList();
  }

  getList() {
    console.log("getList");

    let url = urlComponent + "employeeAttendance/presentAbsentList";
    let response = this.http.post(url, {"listType": "Present"}).subscribe(responseLamdba => { 
      console.log(JSON.stringify(responseLamdba)),
      this.data = responseLamdba
   });    
  }  

  viewEmployee() {
    console.log("viewEmployee")

    this.facadeService.setDataDataService("id", this.facadeService.getDataDataService("username"));
    this.router.navigateByUrl("view-employee/id");
  }

  getNotes() {
    console.log("getNotes");

    let url = urlComponent + "employeeAttendance/getNotes";
    let response = this.http.post(url, {"username": this.facadeService.getDataDataService("username")}).subscribe(responseLamdba => { 
      console.log(JSON.stringify(responseLamdba)),
      this.notes = responseLamdba
   });   
  }
}
