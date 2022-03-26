import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FacadeService } from 'src/app/service/facade.service';

@Component({
  selector: 'app-drafts',
  templateUrl: './drafts.page.html',
  styleUrls: ['./drafts.page.scss'],
})
export class DraftsPage implements OnInit {

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

    let url = "http://localhost:8080/api/communication/getDrafts";
    let response = this.http.get(url).subscribe(responseLamdba => { console.log(JSON.stringify(responseLamdba)),
      this.data = responseLamdba,
    console.log("\nPosition 0: " + JSON.stringify(this.data[0]))
    console.log("\nPosition 0 payload : " + JSON.stringify(this.data[0].payload)),
    console.log("\nPosition 0 headers : " + JSON.stringify(this.data[0].payload.headers[2])),
    console.log("\nPosition 0 headers name: " + JSON.stringify(this.data[0].payload.headers[2].name)),
    console.log("\nPosition 0 headers value: " + JSON.stringify(this.data[0].payload.headers[2].value))
  });    
  }
}
