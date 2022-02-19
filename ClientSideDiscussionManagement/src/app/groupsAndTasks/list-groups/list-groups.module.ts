import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { ListGroupsPageRoutingModule } from './list-groups-routing.module';

import { ListGroupsPage } from './list-groups.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ListGroupsPageRoutingModule
  ],
  declarations: [ListGroupsPage]
})
export class ListGroupsPageModule {}
