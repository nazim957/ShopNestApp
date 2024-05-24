import { Component, OnInit } from '@angular/core';
import { CartItem } from 'src/app/common/cart-item';
import { CartService } from 'src/app/services/cart.service';
import { ModeService } from 'src/app/services/mode.service';

@Component({
  selector: 'app-cart-details',
  templateUrl: './cart-details.component.html',
  styleUrls: ['./cart-details.component.css']
})
export class CartDetailsComponent implements OnInit {

   currentMode = 'light';

  cartItems: CartItem[]=[]
  totalPrice:number=0;
  totalQuantity:number=0

  constructor(private cartService:CartService, private modeService: ModeService)
  {}

  ngOnInit(): void {

     // Subscribe to mode changes
     this.modeService.currentMode$.subscribe((mode) => {
      this.currentMode = mode;
    // console.log("MODE Wishlist"+ this.currentMode);
      
    });
    
    this.listCartDetails();
  }

  listCartDetails()
  {
    //get a handle to the cart items
    this.cartItems = this.cartService.cartItems;

    //subscribe to cart totalprice
    this.cartService.totalPrice.subscribe(
      data=>this.totalPrice=data
    )

    //subscribe to cart totalQuantity
    this.cartService.totalQuantity.subscribe(
      data=>this.totalQuantity=data
    )

    //compute cart total price and quantity
      this.cartService.computeCartTotals()
  }

  incrementQuantity(theCartItem:CartItem)
  {
      this.cartService.addToCart(theCartItem)
  }
  decrementQuantity(theCartItem:CartItem)
  {
    this.cartService.decrementQuantity(theCartItem);
  } 
  remove(theCartItem:CartItem)
  {
    this.cartService.remove(theCartItem);
  }
}

