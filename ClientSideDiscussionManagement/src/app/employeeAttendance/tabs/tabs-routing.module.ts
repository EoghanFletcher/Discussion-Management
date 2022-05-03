import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { TabsPage } from './tabs.page';

const routes: Routes = [
  {
    path: '',
    component: TabsPage,
    children: [

      {
        path: 'present-employees',
        loadChildren: () => import('../present-employees/present-employees.module').then( m => m.PresentEmployeesPageModule)
      },
      {
        path: 'absent-employees',
        loadChildren: () => import('../absent-employees/absent-employees.module').then( m => m.AbsentEmployeesPageModule)
      },
      {
        path: 'list-all-employees',
        loadChildren: () => import('../list-all-employees/list-all-employees.module').then( m => m.ListAllEmployeesPageModule)
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class TabsPageRoutingModule {}
