import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AbsentEmployeesPage } from './absent-employees.page';

const routes: Routes = [
  {
    path: '',
    component: AbsentEmployeesPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AbsentEmployeesPageRoutingModule {}
