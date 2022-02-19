import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { FacadeService } from 'src/app/service/facade.service';

@Component({
  selector: 'app-create-group',
  templateUrl: './create-group.page.html',
  styleUrls: ['./create-group.page.scss'],
})
export class CreateGroupPage implements OnInit {

  data: any;
  private formData: FormGroup;
  private groupName: FormControl;
  private groupDescription: FormControl;

  constructor(private route: ActivatedRoute,
    private facadeService: FacadeService,
    private router: Router,
    private http: HttpClient) { }

  ngOnInit() {
    this.formData = new FormGroup({
      groupName: new FormControl(),
      groupDescription: new FormControl()
    });
  }

  create() {
    console.log("create")
    let groupName: string = this.formData.get("groupName").value;
    let groupDescription: string = this.formData.get("groupDescription").value;

    let url = "http://localhost:8080/api/groupAndTask/createGroup";
    let response = this.http.post(url, {"uId": this.facadeService.getDataDataService("uid"),
                                        "groupName": groupName, "groupDescription": groupDescription}
    ).subscribe(responseLamdba => { this.data = responseLamdba });
    // this.router.navigateByUrl("profile"); 
  }

}
