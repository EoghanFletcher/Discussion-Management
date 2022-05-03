import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { Note } from 'src/app/interface/note';
import { FacadeService } from 'src/app/service/facade.service';
import {urlComponent} from '../../GlobalVariables/global-variables';

@Component({
  selector: 'app-create-note',
  templateUrl: './create-note.page.html',
  styleUrls: ['./create-note.page.scss'],
})
export class CreateNotePage implements OnInit {

  note: Note;

  data: any;
  private postData: FormGroup;
  private title: FormControl;
  private message: FormControl;

  constructor(private router: Router,
    private http: HttpClient,
    private route: ActivatedRoute,
    private facadeService: FacadeService,) { }

  ngOnInit() {
    console.log("ngOnInit");
        this.postData = new FormGroup({
      title: new FormControl(""),
      message: new FormControl(""),
    });

    if (this.route.snapshot.data.special) {
      
      this.data = this.route.snapshot.data.special;
      console.log("data: " + JSON.stringify(this.data));
      
    }
  }

  async createNote() {
    console.log("createNote");
    console.log("username: " + this.facadeService.getDataDataService("username"));

    this.note = {
      username: this.facadeService.getDataDataService("username"),
      title: this.postData.get("title").value,
      message: this.postData.get("message").value
    }

    let url = urlComponent + "employeeAttendance/createNote";
    let response = this.http.post(url, {"username": this.note.username,
                                        "title": this.note.title,
                                        "message": this.note.message
                                        }).subscribe(responseLamdba => { 
      console.log(JSON.stringify(responseLamdba))
   });    
  }

}
