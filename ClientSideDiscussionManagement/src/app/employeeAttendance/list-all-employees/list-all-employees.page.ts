import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FacadeService } from 'src/app/service/facade.service';
import {urlComponent} from '../../GlobalVariables/global-variables';

@Component({
  selector: 'app-list-all-employees',
  templateUrl: './list-all-employees.page.html',
  styleUrls: ['./list-all-employees.page.scss'],
})
export class ListAllEmployeesPage implements OnInit {
  data: any;

  constructor(private route: ActivatedRoute,
    private facadeService: FacadeService,
    private router: Router,
    private http: HttpClient) { }

  ngOnInit() {
    console.log("ngOnInit");
    this.getList();
  }

  ionViewWillEnter() {
    console.log("ionViewWillEnter");
    // this.getList();
  }

  getList() {
    console.log("getList");

    let url = urlComponent + "employeeAttendance/listAllEmployees";
    let response = this.http.post(url, {  }).subscribe(responseLamdba => { 
      console.log(JSON.stringify(responseLamdba)),
      this.data = responseLamdba
   });    
  }  

  viewEmployee() {
    console.log("viewEmployee")

    this.facadeService.setDataDataService("id", this.facadeService.getDataDataService("username"));
    this.router.navigateByUrl("view-employee/id");
  }
}
