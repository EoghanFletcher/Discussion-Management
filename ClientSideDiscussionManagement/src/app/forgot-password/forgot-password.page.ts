import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { ForgotPassword } from '../interface/forgot-password';
import { AuthenticationService } from '../service/authentication.service';
import { DataService } from '../service/data.service';
import { FacadeService } from '../service/facade.service';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.page.html',
  styleUrls: ['./forgot-password.page.scss'],
})
export class ForgotPasswordPage implements OnInit {

  private postData: FormGroup;
  private email: FormControl;
  data: any;


  constructor(private router: Router,
    private route: ActivatedRoute,
    private facadeService: FacadeService) { }

  ngOnInit() {
    this.postData = new FormGroup({
      email: new FormControl(),
      password: new FormControl()
    });

    if (this.route.snapshot.data.special) {
      this.data = this.route.snapshot.data.special;
    }
  }

  async resetPassword() {
    console.log("resetPassword");
    this.email = this.postData.get("email").value;

    let emailString: string = this.postData.get("email").value

    let forPass:
    ForgotPassword = {
      emailAddress: emailString
    }

    await this.facadeService.resetPasswordAuthenticationService(forPass);
  }

  navigateToPage(page) {
    console.log("navigateToPage");
    console.log(page)
    this.router.navigateByUrl(page);
  }

}
