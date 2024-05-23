import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Order } from 'src/app/common/order';
import { OrderHistory } from 'src/app/common/order-history';
import { LoginService } from 'src/app/services/login.service';
import { OrderHistoryService } from 'src/app/services/order-history.service';

@Component({
  selector: 'app-order-history',
  templateUrl: './order-history.component.html',
  styleUrls: ['./order-history.component.css']
})
export class OrderHistoryComponent implements OnInit {

  orderHistoryList: OrderHistory[] = [];

  constructor(
    private orderHistoryService: OrderHistoryService,
    private loginService: LoginService,
    private snack: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.handleOrderHistory();
  }

  handleOrderHistory() {
   
      // Retrieve data from the service
      this.orderHistoryService.getOrderHistory().subscribe(
        (data: OrderHistory[]) => {
        //  console.log(data);
          this.orderHistoryList = data;
        },
        (error) => {
          //  console.log(error);
    
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
