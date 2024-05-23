import { Injectable } from '@angular/core';
import { User } from '../common/user';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ForgotPassword } from '../common/forgot-password';

@Injectable({
  providedIn: 'root'
})
export class SignUpService {

  private prodUrl = 'https://shopnestuserservice.onrender.com'
  private devUrl=''

  constructor(private httpClient: HttpClient) { }

  public addUser(user: User): Observable<User> {
     return this.httpClient.post<User>(`${this.prodUrl}/api/v1/register`,user)
  
  }

  public updatePassword(email:string, forgotData:ForgotPassword): Observable<string> {
    return this.httpClient.put<string>(`${this.prodUrl}/api/v1/forgot/${email}`, forgotData)
  }
}