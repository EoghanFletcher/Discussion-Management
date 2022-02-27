import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { ListGroupsPageRoutingModule } from './list-groups-routing.module';

import { ListGroupsPage } from './list-groups.page';
import { SignOutComponent } from 'src/app/sign-out/sign-out.component';
import { CreateGroupBtnComponent } from '../create-group-btn/create-group-btn.component';
import { NavigationMenuBtnComponent } from 'src/app/NavigationMenuModal/components/navigation-menu-btn/navigation-menu-btn.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    IonicModule,
    ListGroupsPageRoutingModule
  ],
  declarations: [
    ListGroupsPage,
    SignOutComponent,
    CreateGroupBtnComponent,
    NavigationMenuBtnComponent
  ]
})
export class ListGroupsPageModule {}
