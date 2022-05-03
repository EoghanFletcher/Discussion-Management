import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FacadeService } from 'src/app/service/facade.service';
import {urlComponent} from '../../GlobalVariables/global-variables';


@Component({
  selector: 'app-user-interface',
  templateUrl: './user-interface.component.html',
  styleUrls: ['./user-interface.component.scss'],
})
export class UserInterfaceComponent implements OnInit {

  data: any;

  constructor(private route: ActivatedRoute,
    private facadeService: FacadeService,
    private router: Router,
    private http: HttpClient) { }

  ngOnInit() {
    console.log("ngOnInit");
    this.getEmails();
  }

  ionViewWillEnter() {
    console.log("ionViewWillEnter");
    this.getEmails();
  }

  getEmails() {
    console.log("getEmails");

    let url = urlComponent + "communication/message";
    let response = this.http.get(url).subscribe(responseLamdba => { console.log(JSON.stringify(responseLamdba)),
      this.data = responseLamdba,
    console.log("\nPosition 0: " + JSON.stringify(this.data[0])),
    console.log("\nPosition 0 : " + JSON.stringify(this.data[0].payload)),
    console.log("\nPosition 0 : " + JSON.stringify(this.data[0].payload.headers[25])),
    console.log("\nPosition 0 22: " + JSON.stringify(this.data[0].payload.headers[22].name)),
    console.log("\nPosition 0 23: " + JSON.stringify(this.data[0].payload.headers[23].name)),
    console.log("\nPosition 0 24: " + JSON.stringify(this.data[0].payload.headers[24].name)),
    console.log("\nPosition 0 25: " + JSON.stringify(this.data[0].payload.headers[25].name)) });    
  }

}
