import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute } from '@angular/router';
import { CartItem } from 'src/app/common/cart-item';
import { Product } from 'src/app/common/product';
import { CartService } from 'src/app/services/cart.service';
import { ModeService } from 'src/app/services/mode.service';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit{

  currentMode = 'light';

  products:Product[]=[];
  currentCategoryName!:string;
  searchMode:boolean=false;

  constructor(private productService:ProductService, private route: ActivatedRoute,
     private cartService:CartService, private snack: MatSnackBar,  private modeService: ModeService)
  {}
  ngOnInit(): void {

     // Subscribe to mode changes
 this.modeService.currentMode$.subscribe((mode) => {
  this.currentMode = mode;
 // console.log("MODE Wishlist"+ this.currentMode);
  
});

    this.route.params.subscribe(()=>{
      // this.currentCategoryId=params['id']
      this.listProducts();
    })
     
  }

  listProducts(){
    this.searchMode=this.route.snapshot.paramMap.has('keyword')
    if(this.searchMode)
    {
      this.handleSearchProducts();
    }
    else{
   this.handleListProduct();
  }
}

  handleListProduct()
  {
     //check if "name" parameeter is available
     const hasCategoryName:boolean=this.route.snapshot.paramMap.has('name');

     if(hasCategoryName){
       //get the name
       this.currentCategoryName = this.route.snapshot.paramMap.get('name')!;
     }
     else{
       //not category name avalable .. default to category books
       this.currentCategoryName='books';
     }
     
     this.productService.getProductCategoriesbyName(this.currentCategoryName).subscribe(
       (data:Product[])=>{
         this.products=data;
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


  handleSearchProducts() {

    const theKeyword: string = this.route.snapshot.paramMap.get('keyword')!;

   // console.log(`keyword=${theKeyword}`);

    // now search for the products using keyword
    this.productService.searchProducts(theKeyword).subscribe(
      (data:Product[])=>{
        this.products=data;
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

  addToCart(theProduct:Product){
    //console.log(`Adding to Cart: ${theProduct.name}`);
    const theCartItem = new CartItem(theProduct)
    this.cartService.addToCart(theCartItem);
    this.snack.open(theProduct.name + ' Added to Cart!!', '', {
      duration: 1000,
    })
  }

  
}
