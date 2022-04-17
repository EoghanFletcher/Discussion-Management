import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FacadeService } from 'src/app/service/facade.service';

@Component({
  selector: 'app-present-employees',
  templateUrl: './present-employees.page.html',
  styleUrls: ['./present-employees.page.scss'],
})
export class PresentEmployeesPage implements OnInit {
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

    let url = "http://localhost:8080/api/employeeAttendance/presentAbsentList";
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
}
