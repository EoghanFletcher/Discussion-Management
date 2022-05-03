import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { signOut } from 'firebase/auth';
import { Email } from 'src/app/interface/email';
import { FacadeService } from 'src/app/service/facade.service';
import {urlComponent} from '../../GlobalVariables/global-variables';

@Component({
  selector: 'app-compose-message',
  templateUrl: './compose-message.page.html',
  styleUrls: ['./compose-message.page.scss'],
})
export class ComposeMessagePage implements OnInit {

  email: Email

  data: any;
  private postData: FormGroup;
  private to: FormControl;
  private subject: FormControl;
  private body: FormControl;

  constructor(private router: Router,
    private http: HttpClient,
    private route: ActivatedRoute,
    private facadeService: FacadeService,) { }

  ngOnInit() {
    console.log("ngOnInit");
        this.postData = new FormGroup({
      to: new FormControl(""),
      subject: new FormControl(""),
      body: new FormControl(""),
    });

    if (this.route.snapshot.data.special) {
      
      this.data = this.route.snapshot.data.special;
      console.log("data: " + JSON.stringify(this.data));
      this.postData = new FormGroup({
        to: new FormControl(this.data["to"]),
        subject: new FormControl(this.data["subject"] + "___REPLY"),
        body: new FormControl(this.data["body"]),
      });
      
    }
  }

  createDraft() {
    console.log("createDraft");

      this.email = {
        to: this.postData.get("to").value,
        subject: this.postData.get("subject").value,
        body: this.postData.get("body").value
      }

        let url = urlComponent + "communication/draft";
      let response = this.http.post(url, {"to": this.email.to,
                                          "from": this.facadeService.getDataDataService("email"),
                                          "subject": this.email.subject,
                                          "body": this.email.body}
      ).subscribe(responseLamdba => { "" });
    this.router.navigateByUrl("communication/inbox");  
  }

  sendMessage() {
    console.log("sendMessage");

    this.email = {
      to: this.postData.get("to").value,
      subject: this.postData.get("subject").value,
      body: this.postData.get("body").value
    }

    let url = urlComponent + "communication/message";
    let response = this.http.post(url, {"to": this.email.to,
                                        "from": this.facadeService.getDataDataService("email"),
                                        "subject": this.email.subject,
                                        "body": this.email.body}
    ).subscribe(responseLamdba => { "" });
    this.router.navigateByUrl("communication/inbox");  
  }

}
