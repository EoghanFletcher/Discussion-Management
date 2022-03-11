import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { ProfilePageRoutingModule } from './profile-routing.module';

import { ProfilePage } from './profile.page';
import { SignOutComponent } from '../../sign-out/sign-out.component';
import { NavigationMenuBtnComponent } from 'src/app/NavigationMenuModal/components/navigation-menu-btn/navigation-menu-btn.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ProfilePageRoutingModule
  ],
  declarations: [ProfilePage, 
    SignOutComponent,
    NavigationMenuBtnComponent
  ]
})
export class ProfilePageModule {}
