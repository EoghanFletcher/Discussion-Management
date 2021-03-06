import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { GroupTaskDetailsPageRoutingModule } from './group-task-details-routing.module';

import { GroupTaskDetailsPage } from './group-task-details.page';
import { SignOutComponent } from 'src/app/sign-out/sign-out.component';
import { LeaveGroupBtnComponent } from '../leave-group-btn/leave-group-btn.component';
import { CreateTaskBtnComponent } from '../create-task-btn/create-task-btn.component';
import { NavigationMenuBtnComponent } from 'src/app/NavigationMenuModal/components/navigation-menu-btn/navigation-menu-btn.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    IonicModule,
    GroupTaskDetailsPageRoutingModule
  ],
  declarations: [GroupTaskDetailsPage,
                LeaveGroupBtnComponent,
                CreateTaskBtnComponent,
                NavigationMenuBtnComponent,
                SignOutComponent]
})
export class GroupTaskDetailsPageModule {}
