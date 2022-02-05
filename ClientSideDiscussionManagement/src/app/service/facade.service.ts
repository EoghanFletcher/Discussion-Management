import { Injectable, Injector } from '@angular/core';
import { EmailPasswordProvider } from '../interface/email-password-provider';
import { ForgotPassword } from '../interface/forgot-password';
import { AuthenticationService } from './authentication.service';
import { DataService } from './data.service';

@Injectable({
  providedIn: 'root'
})
export class FacadeService {

  private _dataService: DataService;
  private _authenticationService: AuthenticationService;

  public get dataService(): DataService {
    if (!this._dataService) {this._dataService = this.injector.get(DataService);}return this._dataService
  }

  public get authenticationService(): AuthenticationService {
    if (!this._authenticationService) {this._authenticationService = this.injector.get(AuthenticationService);}return this._authenticationService
  }

  constructor(private injector: Injector) {

  }
    getDataDataService(id: string) {
      return this.dataService.getData(id);
    }

    setDataDataService(id: string, data: string) {
      return this.dataService.setData(id, data);
    }

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


}
