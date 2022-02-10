import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FacadeService } from '../service/facade.service';

@Component({
  selector: 'app-sign-out',
  templateUrl: './sign-out.component.html',
  styleUrls: ['./sign-out.component.scss'],
})
export class SignOutComponent implements OnInit {

  constructor(public facadeService: FacadeService,
              public router: Router) { }



  ngOnInit() {}

  signOut() {
    console.log("signOut");
    this.facadeService.setDataDataService("signedIn", null)
    this.router.navigateByUrl("login");
  }

}
