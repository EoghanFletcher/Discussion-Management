import { Injectable, Injector } from '@angular/core';
import { ModalController } from '@ionic/angular';
import { EmailPasswordProvider } from '../interface/email-password-provider';
import { ForgotPassword } from '../interface/forgot-password';
import { AuthenticationService } from './authentication.service';
import { DataResolverService } from './data-resolver.service';
import { DataService } from './data.service';
import { ModalNavigationComponentComponent } from '../NavigationMenuModal/modal-navigation-component/modal-navigation-component.component'

@Injectable({
  providedIn: 'root'
})
export class FacadeService {

  private _dataService: DataService;
  private _authenticationService: AuthenticationService;
  private _resolverService: DataResolverService

  
  public get dataService(): DataService {
    if (!this._dataService) {this._dataService = this.injector.get(DataService);}return this._dataService
  }

  public get resolverService(): DataResolverService {
    if (!this._resolverService) {this._resolverService = this.injector.get(DataResolverService);}return this._resolverService
  }

  public get authenticationService(): AuthenticationService {
    if (!this._authenticationService) {this._authenticationService = this.injector.get(AuthenticationService);}return this._authenticationService
  }

  constructor(private injector: Injector,
              private modalController: ModalController) {

  }
    getDataDataService(id: any) {
      return this.dataService.getData(id);
    }

    setDataDataService(id: string, data: any) {
      this.dataService.setData(id, data);
      return this.dataService.setData(id, data);
    }

    getResolverService

    async loginAuthenticationService(credentials: EmailPasswordProvider) {
      console.log("authenticationServiceLogin");
      return await this.authenticationService.login(credentials);
    }
    
    async logoutAuthenticationService() {
      console.log("authenticationServiceLogout");
      return await this.authenticationService.logout();
    }

    async registerAuthenticationService(credentials: EmailPasswordProvider) {
      console.log("authenticationServiceRegister");
      return await this.authenticationService.signUp(credentials)
    }

    async resetPasswordAuthenticationService(forPass: ForgotPassword) {
      console.log("authenticationServiceResetPassword");
      return await this.authenticationService.resetPassword(forPass);
    }

    async displayModal() {
      console.log("Facade service: displayModal")
      const modal = await this.modalController.create({
        component: ModalNavigationComponentComponent
      });
      return await modal.present();
    }

    async closeModal() {
      console.log("closeModal");
      return await this.modalController.dismiss();
    }


    async getExistingGoogleCredentials() {
      console.log("getExistingGoogleCredentials");
      return await this.authenticationService.googleSigninExistingAccount();
    }

    async createAccountWithGoogleSignin() {
      console.log("createAccountWithGoogleSignin");
      return await this.authenticationService.googleSigninNewAccount();
    }


}
