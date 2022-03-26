import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Email } from 'src/app/interface/email';
import { FacadeService } from 'src/app/service/facade.service';

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
    this.postData = new FormGroup({
      to: new FormControl(""),
      subject: new FormControl(""),
      body: new FormControl(""),
    });
  }

  createDraft() {
    console.log("createDraft");

      this.email = {
        to: this.postData.get("to").value,
        subject: this.postData.get("subject").value,
        body: this.postData.get("body").value
      }

        let url = "http://localhost:8080/api/communication/draft";
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

    let url = "http://localhost:8080/api/communication/message";
    let response = this.http.post(url, {"to": this.email.to,
                                        "from": this.facadeService.getDataDataService("email"),
                                        "subject": this.email.subject,
                                        "body": this.email.body}
    ).subscribe(responseLamdba => { "" });
    this.router.navigateByUrl("communication/inbox");  
  }

}
