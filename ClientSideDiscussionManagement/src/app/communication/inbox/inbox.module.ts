import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { InboxPageRoutingModule } from './inbox-routing.module';

import { InboxPage } from './inbox.page';
import { UserInterfaceComponent } from '../user-interface/user-interface.component';
import { ComposeBtnComponent } from '../components/compose-btn/compose-btn.component';
import { NavigationMenuBtnComponent } from 'src/app/NavigationMenuModal/components/navigation-menu-btn/navigation-menu-btn.component';
import { SignOutComponent } from 'src/app/sign-out/sign-out.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    InboxPageRoutingModule
  ],
  declarations: [InboxPage, UserInterfaceComponent, ComposeBtnComponent, NavigationMenuBtnComponent, SignOutComponent]
})
export class InboxPageModule {}
