import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { ComposeMessagePageRoutingModule } from './compose-message-routing.module';

import { ComposeMessagePage } from './compose-message.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    IonicModule,
    ComposeMessagePageRoutingModule
  ],
  declarations: [ComposeMessagePage]
})
export class ComposeMessagePageModule {}
