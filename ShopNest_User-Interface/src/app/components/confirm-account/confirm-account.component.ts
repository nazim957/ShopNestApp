import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-confirm-account',
  templateUrl: './confirm-account.component.html',
  styleUrls: ['./confirm-account.component.css']
})
export class ConfirmAccountComponent {

  message: string = 'Verifying your email...';

  constructor(private route: ActivatedRoute, private http: HttpClient, private router: Router) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      const token = params['token'];
      if (token) {
        this.http.get(`http://localhost:8081/api/v1/confirm-account?token=${token}`, { responseType: 'text' }).subscribe(
          response => {
            this.message = response;
            setTimeout(() => {
              this.router.navigate(['/login']);
            }, 3000);  // Redirect after 3 seconds
          },
          error => {
            this.message = 'Error verifying email.';
          }
        );
      } else {
        this.message = 'No token found.';
      }
    });
  }
}

