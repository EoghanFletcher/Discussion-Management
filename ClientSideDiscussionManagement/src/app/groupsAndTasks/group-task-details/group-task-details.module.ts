import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { GroupTaskDetailsPageRoutingModule } from './group-task-details-routing.module';

import { GroupTaskDetailsPage } from './group-task-details.page';
import { SignOutComponent } from 'src/app/sign-out/sign-out.component';
import { LeaveGroupBtnComponent } from '../leave-group-btn/leave-group-btn.component';
import { CreateTaskBtnComponent } from '../create-task-btn/create-task-btn.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    GroupTaskDetailsPageRoutingModule
  ],
  declarations: [GroupTaskDetailsPage,
                LeaveGroupBtnComponent,
                CreateTaskBtnComponent,
                SignOutComponent]
})
export class GroupTaskDetailsPageModule {}
