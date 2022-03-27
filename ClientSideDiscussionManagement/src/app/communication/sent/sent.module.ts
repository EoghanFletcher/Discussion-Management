import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { SentPageRoutingModule } from './sent-routing.module';

import { SentPage } from './sent.page';
import { ComposeBtnComponent } from '../components/compose-btn/compose-btn.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    SentPageRoutingModule
  ],
  declarations: [SentPage, ComposeBtnComponent]
})
export class SentPageModule {}
