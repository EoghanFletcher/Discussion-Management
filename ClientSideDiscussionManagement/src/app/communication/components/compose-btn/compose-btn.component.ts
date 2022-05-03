import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-compose-btn',
  templateUrl: './compose-btn.component.html',
  styleUrls: ['./compose-btn.component.scss'],
})
export class ComposeBtnComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit() {}

  composeMessage() {
    this.router.navigateByUrl("compose-message");
  }

}
