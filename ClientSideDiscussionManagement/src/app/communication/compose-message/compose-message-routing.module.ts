import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ComposeMessagePage } from './compose-message.page';

const routes: Routes = [
  {
    path: '',
    component: ComposeMessagePage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ComposeMessagePageRoutingModule {}
