import { Injectable, Injector } from '@angular/core';
import { ModalController } from '@ionic/angular';
import { EmailPasswordProvider } from '../interface/email-password-provider';
import { ForgotPassword } from '../interface/forgot-password';
import { AuthenticationService } from './authentication.service';
import { DataResolverService } from './data-resolver.service';
import { DataService } from './data.service';
import { ModalNavigationComponentComponent } from '../NavigationMenuModal/modal-navigation-component/modal-navigation-component.component'
import { ProfileService } from './profile.service';

@Injectable({
  providedIn: 'root'
})
export class FacadeService {

  private _dataService: DataService;
  private _authenticationService: AuthenticationService;
  private _resolverService: DataResolverService;
  private _profileService: ProfileService;

  
  public get dataService(): DataService {
    if (!this._dataService) {this._dataService = this.injector.get(DataService);}return this._dataService
  }

  public get resolverService(): DataResolverService {
    if (!this._resolverService) {this._resolverService = this.injector.get(DataResolverService);}return this._resolverService
  }

  public get authenticationService(): AuthenticationService {
    if (!this._authenticationService) {this._authenticationService = this.injector.get(AuthenticationService);}return this._authenticationService
  }

  public get profileService(): ProfileService {
    if (!this._profileService) {this._profileService = this.injector.get(ProfileService);}return this._profileService
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
      let test = await this.authenticationService.login(credentials);
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

    async getUseInformation() {
      console.log("getUseInformation");

      return console.log("here2: " + await JSON.stringify(this.profileService.getUserData()));
      // return await this.profileService.getUserData();
    }


}
