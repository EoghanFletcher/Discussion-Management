import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { CreateGroupPageRoutingModule } from './create-group-routing.module';

import { CreateGroupPage } from './create-group.page';
import { SignOutComponent } from 'src/app/sign-out/sign-out.component';
import { CreateGroupBtnComponent } from '../create-group-btn/create-group-btn.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    IonicModule,
    CreateGroupPageRoutingModule
  ],
  declarations: [CreateGroupPage,
    SignOutComponent
  ]
})
export class CreateGroupPageModule {}
