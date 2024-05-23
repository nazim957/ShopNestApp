import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { Login } from 'src/app/common/login';

import { LoginService } from 'src/app/services/login.service';
import { ModeService } from 'src/app/services/mode.service';

export interface LoginResponse {
  message: string;
  Admin?: string;
  User?: string;
  userName: string;
  role: string;
  email: string;
  token: string;
}

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit{

  currentMode = 'light';

  loginData: Login={
    email:'',
    password:'',
  };

  constructor(private snack:MatSnackBar, private login:LoginService,private router:Router,private modeService: ModeService) { }


  ngOnInit(): void {

    // Subscribe to mode changes
    this.modeService.currentMode$.subscribe((mode) => {
     this.currentMode = mode;
    // console.log("MODE Login"+ this.currentMode);
     
   });
 }


  formSubmit(){
    // console.log("Form Submitted")
    // console.log("email:"+ this.loginData.email);
    // console.log("password:"+ this.loginData.password);
    if(this.loginData.email.trim()=='' || this.loginData.email==null)
    {
        this.snack.open('User Name is Required !! ', '' ,{
          duration: 3000,
        } );
        return
    }

    if(this.loginData.password.trim()=='' || this.loginData.password==null)
    {
        this.snack.open('Password is Required !! ', '' ,{
          duration: 3000,
        } );
        return
  }

    //request server to generate token
    this.login.generateToken(this.loginData).subscribe(
      (data:LoginResponse)=>{
        // console.log('success');
        // console.log(data);
          //login
      this.login.loginUser(data.token)
      this.login.setUserName(data.userName)
      this.login.setRole(data.role)
      this.login.setEmail(data.email)
     // console.log("UserName: ",data.userName);
      
    

        //redirect .... ADMIN: admin dashboard
        //redirect .... USER user dashboard
        if(this.login.getUserRole()==='ADMIN')
        {
          //admin dashboard
          // window.location.href='/admin'
          this.router.navigate([''])
        //  this.login.loginStatusSubject.next(true)
        }
        else if(this.login.getUserRole()=='USER')
        {
          //user dashboard
          // window.location.href='/user-dashboard'
          this.router.navigate(['products'])
        //  this.login.loginStatusSubject.next(true)
        }
        else{
          this.login.logout();
           
        }
        
      },
      (error) => {
        //console.log(error);

        let errorMessage = 'Something went wrong';

        if (error && error.error && error.error.message) {
          // Check if there is a custom error message from the server
          errorMessage = error.error.message;
        }

        this.snack.open(errorMessage, '', { duration: 3000 });
      }
    )
  }

}
