import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProductListComponent } from './components/product-list/product-list.component';
import { ProductDetailsComponent } from './components/product-details/product-details.component';
import { CartDetailsComponent } from './components/cart-details/cart-details.component';
import { CheckoutComponent } from './components/checkout/checkout.component';
import { SignupComponent } from './components/signup/signup.component';
import { LoginComponent } from './components/login/login.component';
import { OrderHistoryComponent } from './components/order-history/order-history.component';
import { canDeactivateGuard } from './services/can-deactivate.guard';
import { authGuard } from './services/auth.guard';
import { ContactUsComponent } from './components/contact-us/contact-us.component';
import { AboutUsComponent } from './components/about-us/about-us.component';
import { ForgotpasswordComponent } from './components/forgotpassword/forgotpassword.component';
const routes: Routes = [

  {path: 'aboutus',component: AboutUsComponent,pathMatch: 'full' },
  {path: 'contactus',component: ContactUsComponent,pathMatch: 'full' },
  {path:'order-history', component:OrderHistoryComponent,  canActivate: [authGuard] },
  {path: 'login',component: LoginComponent, pathMatch: 'full'},
  {path: 'signup',component: SignupComponent,pathMatch: 'full',  canDeactivate:[canDeactivateGuard]},
  {path:'forgot',component:ForgotpasswordComponent,pathMatch:'full'},
  {path: 'checkout', component: CheckoutComponent, canActivate: [authGuard] },
  {path: 'cart-details', component: CartDetailsComponent},
  {path: 'products/:id', component: ProductDetailsComponent},
  {path: 'search/:keyword', component: ProductListComponent},
  {path : 'category/:name', component:ProductListComponent},
  {path: 'category', component: ProductListComponent},
  {path: 'products', component: ProductListComponent},
  {path: '', redirectTo: '/products', pathMatch: 'full'},
  {path: '**', redirectTo: '/products', pathMatch: 'full'}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
