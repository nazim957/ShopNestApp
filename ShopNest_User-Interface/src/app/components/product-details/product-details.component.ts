import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute } from '@angular/router';
import { CartItem } from 'src/app/common/cart-item';
import { Product } from 'src/app/common/product';
import { CartService } from 'src/app/services/cart.service';
import { ModeService } from 'src/app/services/mode.service';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-product-details',
  templateUrl: './product-details.component.html',
  styleUrls: ['./product-details.component.css']
})
export class ProductDetailsComponent implements OnInit{

  currentMode = 'light';

  product:Product= new Product()

  constructor(private productService: ProductService,
    private route: ActivatedRoute, private cartService: CartService,
     private snack: MatSnackBar, private modeService: ModeService) 
     { }

  ngOnInit(): void {

     // Subscribe to mode changes
 this.modeService.currentMode$.subscribe((mode) => {
  this.currentMode = mode;
 // console.log("MODE Wishlist"+ this.currentMode);
  
});

    this.route.paramMap.subscribe(() => {
      this.handleProductDetails();
    })
  }

  handleProductDetails() {

    // get the "id" param string. convert string to a number using the "+" symbol
    const theProductId: number = +this.route.snapshot.paramMap.get('id')!;

    this.productService.getProduct(theProductId).subscribe(
      (data:Product) => {
        console.log(data);
        
        this.product = data;
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
    )
  }

  addToCart()
  {
   // console.log(`Adding to cart: ${this.product.name}, ${this.product.unitPrice}`);
    const theCartItem = new CartItem(this.product)
    this.cartService.addToCart(theCartItem)
    this.snack.open('Added to Cart!!', '', {
      duration: 1000,
    })
  }

}
