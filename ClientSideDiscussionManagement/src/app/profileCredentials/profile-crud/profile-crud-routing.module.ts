import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ProfileCrudPage } from './profile-crud.page';

const routes: Routes = [
  {
    path: '',
    component: ProfileCrudPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ProfileCrudPageRoutingModule {}
