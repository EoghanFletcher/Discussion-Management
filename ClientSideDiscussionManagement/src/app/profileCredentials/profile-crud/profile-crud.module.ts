import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { ProfileCrudPageRoutingModule } from './profile-crud-routing.module';

import { ProfileCrudPage } from './profile-crud.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    IonicModule,
    ProfileCrudPageRoutingModule
  ],
  declarations: [ProfileCrudPage]
})
export class ProfileCrudPageModule {}
