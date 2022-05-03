import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ListAllEmployeesPage } from './list-all-employees.page';

const routes: Routes = [
  {
    path: '',
    component: ListAllEmployeesPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ListAllEmployeesPageRoutingModule {}
