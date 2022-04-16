import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { SentPageRoutingModule } from './sent-routing.module';

import { SentPage } from './sent.page';
import { ComposeBtnComponent } from '../components/compose-btn/compose-btn.component';
import { NavigationMenuBtnComponent } from 'src/app/NavigationMenuModal/components/navigation-menu-btn/navigation-menu-btn.component';
import { SignOutComponent } from 'src/app/sign-out/sign-out.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    SentPageRoutingModule
  ],
  declarations: [SentPage, ComposeBtnComponent, NavigationMenuBtnComponent, SignOutComponent]
})
export class SentPageModule {}
