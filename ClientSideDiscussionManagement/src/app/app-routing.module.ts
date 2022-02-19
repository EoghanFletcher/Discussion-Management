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
  },  {
    path: 'list-groups',
    loadChildren: () => import('./groupsAndTasks/list-groups/list-groups.module').then( m => m.ListGroupsPageModule)
  },
  {
    path: 'create-group',
    loadChildren: () => import('./groupsAndTasks/create-group/create-group.module').then( m => m.CreateGroupPageModule)
  },



];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, { preloadingStrategy: PreloadAllModules })
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
