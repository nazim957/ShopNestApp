import { Component, OnInit } from '@angular/core';
import { CartService } from 'src/app/services/cart.service';
import { ModeService } from 'src/app/services/mode.service';

@Component({
  selector: 'app-cart-status',
  templateUrl: './cart-status.component.html',
  styleUrls: ['./cart-status.component.css']
})
export class CartStatusComponent implements OnInit {

  currentMode = 'light';

  totalPrice: number=0.00;
  totalQuantity: number=0;

  constructor(private cartService:CartService,private modeService: ModeService){}
  
  ngOnInit(): void {

      // Subscribe to mode changes
    this.modeService.currentMode$.subscribe((mode) => {
      this.currentMode = mode;
    // console.log("MODE Wishlist"+ this.currentMode);
      
    });
    
   this.updateCartStatus();
  }

  updateCartStatus(){

    //suscribe to the cart total price anf totalquantity
    this.cartService.totalPrice.subscribe(
      data=>this.totalPrice=data
    );

    this.cartService.totalQuantity.subscribe(
      data=>this.totalQuantity=data
    )
  }

}
