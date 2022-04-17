import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { AbsentEmployeesPageRoutingModule } from './absent-employees-routing.module';

import { AbsentEmployeesPage } from './absent-employees.page';
import { NavigationMenuBtnComponent } from 'src/app/NavigationMenuModal/components/navigation-menu-btn/navigation-menu-btn.component';
import { SignOutComponent } from 'src/app/sign-out/sign-out.component';
import { CreateNoteBtnComponent } from '../components/create-note-btn/create-note-btn.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    AbsentEmployeesPageRoutingModule
  ],
  declarations: [AbsentEmployeesPage,
    SignOutComponent,
  NavigationMenuBtnComponent,
  CreateNoteBtnComponent]
})
export class AbsentEmployeesPageModule {}
