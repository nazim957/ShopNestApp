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
import { ConfirmAccountComponent } from './components/confirm-account/confirm-account.component';

const routes: Routes = [

  { path:'order-history', component:OrderHistoryComponent,  canActivate: [authGuard] },
  { path: 'confirm-account', component: ConfirmAccountComponent },
  {path: 'login',component: LoginComponent, pathMatch: 'full'},
  {path: 'signup',component: SignupComponent,pathMatch: 'full',  canDeactivate:[canDeactivateGuard]},
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
