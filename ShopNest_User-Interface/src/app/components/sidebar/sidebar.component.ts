import { Component, OnInit } from '@angular/core';

import { Router } from '@angular/router';
import { ProductCategory } from 'src/app/common/product-category';
import { LoginService } from 'src/app/services/login.service';
import { ModeService } from 'src/app/services/mode.service';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit{

  currentMode = 'light';
  
  productCategories: ProductCategory[] =[];
  
  constructor(private productService: ProductService, private router: Router,
     public login: LoginService, private modeService: ModeService) { }

  ngOnInit() {

   // Subscribe to mode changes
   this.modeService.currentMode$.subscribe((mode) => {
    this.currentMode = mode;
   // console.log("MODE Wishlist"+ this.currentMode);
    
  });  

    this.listProductCategories();
  }

  listProductCategories() {

    this.productService.getProductCategories().subscribe(
      (data:ProductCategory[]) => {        
       // console.log('Product Categories=' + JSON.stringify(data));
        this.productCategories = data;
      }
    );
  }


  dosearch(value:string)
  {
    //console.log(`value=${value}`)
    this.router.navigateByUrl(`/search/${value}`)
  }

  public logout() {
    this.login.logout();
    this.router.navigate(['']);
  }

}


