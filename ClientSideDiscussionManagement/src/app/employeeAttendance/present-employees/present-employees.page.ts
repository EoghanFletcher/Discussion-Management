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

    let url = "http://localhost:8080/api/employeeAttendance/presentList";
    let response = this.http.post(url, {"uId": this.facadeService.getDataDataService("uid"),
                                              "username": this.facadeService.getDataDataService("username")}).subscribe(responseLamdba => { 
      console.log(JSON.stringify(responseLamdba)),
      this.data = responseLamdba
   });    
  }  
}
