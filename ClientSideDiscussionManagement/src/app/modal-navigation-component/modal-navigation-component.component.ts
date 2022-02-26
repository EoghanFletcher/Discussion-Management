import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FacadeService } from '../service/facade.service';

@Component({
  selector: 'app-modal-navigation-component',
  templateUrl: './modal-navigation-component.component.html',
  styleUrls: ['./modal-navigation-component.component.scss'],
})
export class ModalNavigationComponentComponent implements OnInit {

  constructor(private router: Router,
    private facadeService: FacadeService) { }

  ngOnInit() {
    
  }

  async closeMenu() {
    console.log("displayMenu");

    // const modal = await this.modalController.create({
    //   component: ModalNavigationComponentComponent
    // });
    // return await modal.present();
    
    
    await this.facadeService.closeModal();
  }

  navigateToPage(location) {
    console.log("navigateToPage");
    this.router.navigateByUrl(location);
  }

}
