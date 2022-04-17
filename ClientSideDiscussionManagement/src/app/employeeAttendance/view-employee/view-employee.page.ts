import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { signOut } from 'firebase/auth';
import { DataService } from 'src/app/service/data.service';
import { FacadeService } from 'src/app/service/facade.service';

@Component({
  selector: 'app-view-employee',
  templateUrl: './view-employee.page.html',
  styleUrls: ['./view-employee.page.scss'],
})
export class ViewEmployeePage implements OnInit {
data: any;
username: string;

  constructor(private route: ActivatedRoute,
    private router: Router,
    private facadeService: FacadeService,
    private http: HttpClient) { }

  ngOnInit() {
    console.log("ngOnInit")
  }

  ionViewWillEnter() {
    console.log("ionViewWillEnter");
    this.data = this.facadeService.getDataDataService("userCredentials");
    console.log("id: " + JSON.stringify(this.facadeService.getDataDataService("id")));

    if (this.route.snapshot.data.special) {
      this.username = this.route.snapshot.data.special;
    }
    console.log("this.data:" + JSON.stringify(this.data));
  }

}
