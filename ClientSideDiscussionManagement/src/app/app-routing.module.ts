import { NgModule } from '@angular/core';
import { PreloadAllModules, RouterModule, Routes } from '@angular/router';
import { DataResolverService } from './service/data-resolver.service';

const routes: Routes = [
  {
    path: 'home',
    loadChildren: () => import('./home/home.module').then( m => m.HomePageModule)
  },
  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full'
  },
  {
    path: 'login',
    loadChildren: () => import('./authenticate/login/login.module').then( m => m.LoginPageModule)
  },
  {
    path: 'profile',
    loadChildren: () => import('./profileCredentials/profile/profile.module').then( m => m.ProfilePageModule)
  },
  {
    path: 'register',
    loadChildren: () => import('./authenticate/register/register.module').then( m => m.RegisterPageModule)
  },
  {
    path: 'forgot-password',
    loadChildren: () => import('./authenticate/forgot-password/forgot-password.module').then( m => m.ForgotPasswordPageModule)
  },
  {
    path: 'profile-crud',
    loadChildren: () => import('./profileCredentials/profile-crud/profile-crud.module').then( m => m.ProfileCrudPageModule)
  },
  {
    path: 'profile-crud/:id',
    resolve: {
      special: DataResolverService,
    },
    loadChildren: () => import('./profileCredentials/profile-crud/profile-crud.module').then( m => m.ProfileCrudPageModule)
  },
  {
    path: 'create-credential',
    loadChildren: () => import('./profileCredentials/create-credential/create-credential.module').then( m => m.CreateCredentialPageModule)
  },
  {
    path: 'list-groups',
    loadChildren: () => import('./groupsAndTasks/list-groups/list-groups.module').then( m => m.ListGroupsPageModule)
  },
  {
    path: 'create-group',
    loadChildren: () => import('./groupsAndTasks/create-group/create-group.module').then( m => m.CreateGroupPageModule)
  },
  {
    path: 'group-task-details',
    loadChildren: () => import('./groupsAndTasks/group-task-details/group-task-details.module').then( m => m.GroupTaskDetailsPageModule)
  },
  {
    path: 'group-task-details/:id',
    resolve: {
      special: DataResolverService,
    },
    loadChildren: () => import('./groupsAndTasks/group-task-details/group-task-details.module').then( m => m.GroupTaskDetailsPageModule)
  },
  {
    path: 'create-task',
    loadChildren: () => import('./groupsAndTasks/create-task/create-task.module').then( m => m.CreateTaskPageModule)
  },
  {
    path: 'create-task/:id',
    resolve: {
      special: DataResolverService,
    },
    loadChildren: () => import('./groupsAndTasks/create-task/create-task.module').then( m => m.CreateTaskPageModule)
  },
  {
    path: 'communication',
    loadChildren: () => import('./communication/tabs/tabs.module').then( m => m.TabsPageModule)
  },
  {
    path: 'compose-message',
    loadChildren: () => import('./communication/compose-message/compose-message.module').then( m => m.ComposeMessagePageModule)
  },
  {
    path: 'compose-message/:id',
    resolve: {
      special: DataResolverService,
    },
    loadChildren: () => import('./communication/compose-message/compose-message.module').then( m => m.ComposeMessagePageModule)
  },
  {
    path: 'employee-attendance',
    loadChildren: () => import('./employeeAttendance/tabs/tabs.module').then( m => m.TabsPageModule)
  },

  






  






];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, { preloadingStrategy: PreloadAllModules })
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
