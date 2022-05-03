import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { ListAllEmployeesPageRoutingModule } from './list-all-employees-routing.module';

import { ListAllEmployeesPage } from './list-all-employees.page';
import { NavigationMenuBtnComponent } from 'src/app/NavigationMenuModal/components/navigation-menu-btn/navigation-menu-btn.component';
import { SignOutComponent } from 'src/app/sign-out/sign-out.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ListAllEmployeesPageRoutingModule
  ],
  declarations: [ListAllEmployeesPage,
    SignOutComponent,
    NavigationMenuBtnComponent]
})
export class ListAllEmployeesPageModule {}
