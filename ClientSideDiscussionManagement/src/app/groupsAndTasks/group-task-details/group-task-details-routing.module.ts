import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { GroupTaskDetailsPage } from './group-task-details.page';

const routes: Routes = [
  {
    path: '',
    component: GroupTaskDetailsPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class GroupTaskDetailsPageRoutingModule {}
