import { Component, OnInit } from '@angular/core';
import { FacadeService } from 'src/app/service/facade.service';

@Component({
  selector: 'app-navigation-menu-btn',
  templateUrl: './navigation-menu-btn.component.html',
  styleUrls: ['./navigation-menu-btn.component.scss'],
})
export class NavigationMenuBtnComponent implements OnInit {

  constructor(private facadeService: FacadeService) { }

  ngOnInit() {}

  async displayMenu() {
    console.log("displayMenu");
    
    await this.facadeService.displayModal();
  }
}
