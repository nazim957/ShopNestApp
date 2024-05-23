import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/common/user';
import { ModeService } from 'src/app/services/mode.service';
import { SignUpService } from 'src/app/services/sign-up.service';
import Swal from 'sweetalert2';


@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent {

  currentMode = 'light';
  termsAccepted = false;

  public user: User = {
    email: '',
    userName: '',
    password: '',
    phoneNumber: '',
    securityQuestion:'',
    securityAnswer:'',
    role: 'USER'
  };
  

  constructor(private userService: SignUpService, private router: Router, private modeService: ModeService) {}

  ngOnInit(): void {

    // Subscribe to mode changes
    this.modeService.currentMode$.subscribe((mode) => {
     this.currentMode = mode;
   //  console.log("MODE SignUp"+ this.currentMode);
     
   });
 }

  formSubmit() {
    // if (!this.user.email || !this.user.email.trim()) {
    //   this.snack.open('Email is required!!', '', { duration: 3000 });
    //   return;
    // }
    // console.log("user", this.user.email,"  ", this.user.password,"  ", this.user.phone,"  ", this.user.role, " ", this.user.username);
    

    this.userService.addUser(this.user).subscribe(
      () => {
        //console.log(data);
       // console.log('User Registered Successfully');
       Swal.fire('User Registered Successfully! Please check your email to activate your account.' , '')
       // this.snack.open('User Registered Successfully!!', '', { duration: 4000 });
        this.router.navigate(['/login']);
      },
      (error) => {
        //console.log(error);

        let errorMessage = 'Something went wrong';

        if (error && error.error && error.error.message) {
          // Check if there is a custom error message from the server
          errorMessage = error.error.message;
        }

      //  this.snack.open(errorMessage, '', { duration: 3000 });
      Swal.fire('Error', errorMessage, 'error')
      }
    );
  }

  canExit():boolean{
    if(this.user.email.trim()=='' || this.user.password.trim()=='' || this.user.securityAnswer.trim()==''
       || this.user.securityQuestion.trim()=='' || this.user.userName.trim()=='')
    {
      return confirm('you have unsaved changes');
    }
    else
    {
      return true;
    }
  }
}

