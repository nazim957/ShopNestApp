import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from 'src/app/services/login.service';
import { ModeService } from 'src/app/services/mode.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {

  mode = 'light'

  constructor(
    public login: LoginService,
    private router: Router,
    private modeService: ModeService
  ){}

  public logout() {
    this.login.logout();
    this.router.navigate(['']);
  }

  changeMode() {
    this.mode = this.mode === 'light' ? 'dark' : 'light';
    this.modeService.toggleMode();
  }

  sidenavWidth = '0';

  toggleNav() {
    this.sidenavWidth = this.sidenavWidth === '0' ? '250px' : '0';
  }

  closeNav() {
    this.sidenavWidth = '0';
  }
  
}

