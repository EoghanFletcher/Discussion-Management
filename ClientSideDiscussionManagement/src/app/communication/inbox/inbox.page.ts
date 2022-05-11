import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Email } from 'src/app/interface/email';
import { FacadeService } from 'src/app/service/facade.service';
import {urlComponent} from '../../GlobalVariables/global-variables';

@Component({
  selector: 'app-inbox',
  templateUrl: './inbox.page.html',
  styleUrls: ['./inbox.page.scss'],
})
export class InboxPage implements OnInit {
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
    let response = this.http.put(url, {"messageType":"INBOX"}).subscribe(responseLamdba => { 
      this.data = responseLamdba
   });    
  }

  replyMessage(to: string, from: string, subject: string): void {
    console.log("replyMessage")

    let emailMessage: Email = {
      to: to,
      subject: subject,
      body: ""
    }

    this.facadeService.setDataDataService("emailMessage", emailMessage);
    this.router.navigateByUrl("compose-message/emailMessage");
  }
}
