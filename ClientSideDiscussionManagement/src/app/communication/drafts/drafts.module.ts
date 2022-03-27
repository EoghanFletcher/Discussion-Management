import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { DraftsPageRoutingModule } from './drafts-routing.module';

import { DraftsPage } from './drafts.page';
import { ComposeBtnComponent } from '../components/compose-btn/compose-btn.component';
import { NavigationMenuBtnComponent } from 'src/app/NavigationMenuModal/components/navigation-menu-btn/navigation-menu-btn.component';
import { SignOutComponent } from 'src/app/sign-out/sign-out.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    DraftsPageRoutingModule
  ],
  declarations: [DraftsPage, ComposeBtnComponent, NavigationMenuBtnComponent, SignOutComponent]
})
export class DraftsPageModule {}
