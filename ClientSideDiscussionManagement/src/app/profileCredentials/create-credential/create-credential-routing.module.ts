import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { CreateCredentialPage } from './create-credential.page';

const routes: Routes = [
  {
    path: '',
    component: CreateCredentialPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class CreateCredentialPageRoutingModule {}
