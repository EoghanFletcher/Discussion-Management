import { Injectable, Injector } from '@angular/core';
import { EmailPasswordProvider } from '../interface/email-password-provider';
import { ForgotPassword } from '../interface/forgot-password';
import { AuthenticationService } from './authentication.service';
import { DataResolverService } from './data-resolver.service';
import { DataService } from './data.service';

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

  constructor(private injector: Injector) {

  }
    getDataDataService(id: any) {
      return this.dataService.getData(id);
    }

    setDataDataService(id: string, data: any) {
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


}
