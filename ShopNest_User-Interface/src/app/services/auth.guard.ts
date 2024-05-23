import { CanActivateFn } from '@angular/router';
import { LoginService } from './login.service';
import { Router } from '@angular/router';
import { inject } from '@angular/core';

export const authGuard: CanActivateFn = () => {
  const loginService = inject(LoginService);
  const router = inject(Router);

  if (loginService.isLockedIn()) {
    return true;
  } else {
    // Navigate to the login page and store the attempted URL for redirecting after login.
    router.navigate(['/login']);
    return false;
  }
};
