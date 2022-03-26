import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { InboxPageRoutingModule } from './inbox-routing.module';

import { InboxPage } from './inbox.page';
import { UserInterfaceComponent } from '../user-interface/user-interface.component';
import { ComposeBtnComponent } from '../components/compose-btn/compose-btn.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    InboxPageRoutingModule
  ],
  declarations: [InboxPage, UserInterfaceComponent, ComposeBtnComponent]
})
export class InboxPageModule {}
