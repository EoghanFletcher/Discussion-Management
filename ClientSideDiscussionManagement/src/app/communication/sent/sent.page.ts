import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FacadeService } from 'src/app/service/facade.service';

@Component({
  selector: 'app-sent',
  templateUrl: './sent.page.html',
  styleUrls: ['./sent.page.scss'],
})
export class SentPage implements OnInit {

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

    let url = "http://localhost:8080/api/communication/message";
    let response = this.http.put(url, {"messageType":"SENT"}).subscribe(responseLamdba => { console.log(JSON.stringify(responseLamdba)),
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
