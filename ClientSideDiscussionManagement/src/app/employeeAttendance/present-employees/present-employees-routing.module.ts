import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { PresentEmployeesPage } from './present-employees.page';

const routes: Routes = [
  {
    path: '',
    component: PresentEmployeesPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class PresentEmployeesPageRoutingModule {}
