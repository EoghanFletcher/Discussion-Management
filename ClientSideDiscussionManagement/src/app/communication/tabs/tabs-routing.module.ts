import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { TabsPage } from './tabs.page';

const routes: Routes = [
  {
    path: '',
    component: TabsPage,
    children: [
      {
        path: 'inbox',
        loadChildren: () => import('../inbox/inbox.module').then( m => m.InboxPageModule)
      },
      {
        path: 'sent',
        loadChildren: () => import('../sent/sent.module').then( m => m.SentPageModule)
      },
      {
        path: 'drafts',
        loadChildren: () => import('../drafts/drafts.module').then( m => m.DraftsPageModule)
      },
      // {
      //   path: 'compose-message',
      //   loadChildren: () => import('../compose-message/compose-message.module').then( m => m.ComposeMessagePageModule)
      // },
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class TabsPageRoutingModule {}
