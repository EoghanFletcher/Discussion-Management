import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { ViewEmployeePageRoutingModule } from './view-employee-routing.module';

import { ViewEmployeePage } from './view-employee.page';
import { SignOutComponent } from 'src/app/sign-out/sign-out.component';
import { NavigationMenuBtnComponent } from 'src/app/NavigationMenuModal/components/navigation-menu-btn/navigation-menu-btn.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ViewEmployeePageRoutingModule
  ],
  declarations: [ViewEmployeePage,
  SignOutComponent,
  NavigationMenuBtnComponent]
})
export class ViewEmployeePageModule {}
