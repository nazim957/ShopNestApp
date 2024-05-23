import {  CanDeactivateFn } from '@angular/router';
import { SignupComponent } from '../components/signup/signup.component';

export const canDeactivateGuard: CanDeactivateFn<SignupComponent> = (component:SignupComponent, ):boolean => {
 // console.log("calling");
  return component.canExit();
};
