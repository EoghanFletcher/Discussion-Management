import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot } from '@angular/router';
import { FacadeService } from './facade.service';

@Injectable({
  providedIn: 'root'
})
export class DataResolverService {

  constructor(private facadeService: FacadeService) { }

  resolve(route : ActivatedRouteSnapshot) {
    const id = route.paramMap.get("id");
    return this.facadeService.getDataDataService(id);
  }
}
