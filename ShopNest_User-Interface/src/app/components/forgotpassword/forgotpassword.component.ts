import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import {  Router } from '@angular/router';
import { ForgotPassword } from 'src/app/common/forgot-password';

import { ModeService } from 'src/app/services/mode.service';
import { SignUpService } from 'src/app/services/sign-up.service';


@Component({
  selector: 'app-forgotpassword',
  templateUrl: './forgotpassword.component.html',
  styleUrls: ['./forgotpassword.component.css']
})
export class ForgotpasswordComponent implements OnInit{

  currentMode = 'light';

  email = ''; // Specify the type for email
  confirmPassword = ''; // Specify a more specific type if possible
  forgotData: ForgotPassword={
    securityQuestion:'',
    securityQuestionAnswer:'',
    newPassword:''
  }

  constructor(private user: SignUpService, private snack :MatSnackBar, private router: Router, private modeService: ModeService){}

  ngOnInit(): void {

    // Subscribe to mode changes
    this.modeService.currentMode$.subscribe((mode) => {
     this.currentMode = mode;
    // console.log("MODE Forgot"+ this.currentMode);
     
   });
 }

  passwordChange() {

   // console.log("Forgot Data: "+ " "+ this.forgotData.securityQuestion  +this.forgotData.newPassword+ "  ", this.forgotData.securityQuestionAnswer);
    this.user.updatePassword(this.email,this.forgotData).subscribe(
      ()=>{
     //   console.log(data);
    this.snack.open('Password Updated Successfully!!', '', { duration: 3000 });
    this.router.navigate(['/login']);
      },
      (error) => {
       // console.log(error);

        let errorMessage = 'Something went wrong';

        if (error && error.error && error.error.message) {
          // Check if there is a custom error message from the server
          errorMessage = error.error.message;
        }

        this.snack.open(errorMessage, '', { duration: 3000 });
      }
    );
  }

}
