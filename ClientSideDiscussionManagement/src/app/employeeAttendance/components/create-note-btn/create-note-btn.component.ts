import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-create-note-btn',
  templateUrl: './create-note-btn.component.html',
  styleUrls: ['./create-note-btn.component.scss'],
})
export class CreateNoteBtnComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit() {}

  createNote() {
    this.router.navigateByUrl("create-note");
  }

}
