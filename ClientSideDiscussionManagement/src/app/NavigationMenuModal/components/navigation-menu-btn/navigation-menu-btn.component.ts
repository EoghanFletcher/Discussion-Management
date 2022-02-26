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

    // const modal = await this.modalController.create({
    //   component: ModalNavigationComponentComponent
    // });
    // return await modal.present();
    
    
    await this.facadeService.displayModal();
  }

}
