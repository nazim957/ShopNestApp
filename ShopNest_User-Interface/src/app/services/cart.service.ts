import { Injectable } from '@angular/core';
import { CartItem } from '../common/cart-item';
import { BehaviorSubject, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class CartService {
  cartItems: CartItem[] = [];

  totalPrice: Subject<number> =  new BehaviorSubject<number>(0);
  totalQuantity: Subject<number> = new BehaviorSubject<number>(0);

  //storage:Storage=sessionStorage;
  storage:Storage=localStorage;

  constructor() {
     //read data from storage
     let data=JSON.parse(this.storage.getItem('cartItems')!) //cart items is key
     if(data!=null){
       this.cartItems=data;

       //compute totals based on data that is read from storage
       this.computeCartTotals();
     }
  }

  addToCart(theCartItem: CartItem) {
    //check if we have item in our cart already
    let alreadyExistsInCart: boolean = false;
    let existingCartItem!: CartItem;

    if (this.cartItems.length > 0) {
      //find the item in cart based on id

      for (let tempCartItem of this.cartItems) {
        if (tempCartItem.id === theCartItem.id) {
          existingCartItem = tempCartItem;
          break;
        }
      }

      //if found
      alreadyExistsInCart = existingCartItem != undefined;
    }

    if (alreadyExistsInCart) {
      //incremet the quantity
      existingCartItem.quantity++;
    } else {
      //add the item to arrry
      this.cartItems.push(theCartItem);
    }

    //compute cart totals
    this.computeCartTotals();
  }

  computeCartTotals() {
    let totalPriceValue: number = 0;
    let totalQuantityValue: number = 0;

    for (let currentCartItem of this.cartItems) {
      totalPriceValue += currentCartItem.quantity * currentCartItem.unitPrice;
      totalQuantityValue += currentCartItem.quantity;
    }

    //pubish new values..all subscribers wiil receive the new data
    this.totalPrice.next(totalPriceValue);
    this.totalQuantity.next(totalQuantityValue);

    // this.logCartData(totalPriceValue, totalQuantityValue);

    //persist cart data
     this.persistCartItems();
  }
  persistCartItems(){
    this.storage.setItem('cartItems',JSON.stringify(this.cartItems))
  }

  

  logCartData(totalPriceValue: number, totalQuantityValue: number) {
   // console.log('Contetns of cart');
    for (let temp of this.cartItems) {
      const subTotalPrice = temp.quantity * temp.unitPrice;
      console.log(
        `name: ${temp.name}, quantity=${temp.quantity}, unitPrice=${temp.unitPrice}, subTotal=${subTotalPrice}`
      );
    }
    console.log(
      `totalPrice: ${totalPriceValue.toFixed(
        2
      )}, totalQuantity: ${totalQuantityValue}`
    );
  }

  decrementQuantity(theCartItem:CartItem)
  {
    theCartItem.quantity--;
    if(theCartItem.quantity===0)
    {
      this.remove(theCartItem)
    }
    else{
      this.computeCartTotals();
    }
  }
  remove(theCartItem: CartItem) {
   //get index of item from array
   const itemIndex = this.cartItems.findIndex(tempCartItem=> tempCartItem.id === theCartItem.id)
   
   //if found remove item from array at given index
   if(itemIndex > -1)
   {
    this.cartItems.splice(itemIndex,1)
    this.computeCartTotals();
   }

  }
}
