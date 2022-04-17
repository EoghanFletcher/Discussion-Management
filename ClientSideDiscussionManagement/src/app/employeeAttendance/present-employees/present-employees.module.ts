import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { PresentEmployeesPageRoutingModule } from './present-employees-routing.module';

import { PresentEmployeesPage } from './present-employees.page';
import { SignOutComponent } from 'src/app/sign-out/sign-out.component';
import { NavigationMenuBtnComponent } from 'src/app/NavigationMenuModal/components/navigation-menu-btn/navigation-menu-btn.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    PresentEmployeesPageRoutingModule
  ],
  declarations: [PresentEmployeesPage,
  SignOutComponent,
NavigationMenuBtnComponent,
]
})
export class PresentEmployeesPageModule {}
