import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { Country } from 'src/app/common/country';
import { Order } from 'src/app/common/order';
import { OrderItem } from 'src/app/common/order-item';
import { Purchase } from 'src/app/common/purchase';
import { State } from 'src/app/common/state';
import { CartService } from 'src/app/services/cart.service';
import { CheckoutService } from 'src/app/services/checkout.service';
import { DropdownFormService } from 'src/app/services/dropdown-form.service';
import { LoginService } from 'src/app/services/login.service';
import { CustomValidators } from 'src/app/validators/custom-validators';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})
export class CheckoutComponent implements OnInit{

  checkoutFormGroup!: FormGroup; //1 steps for formgroup

  totalPrice: number = 0;
  totalQuantity: number = 0;

  creditCardYears: number[] = [];
  creditCardMonths: number[] = [];

  countries: Country[] = [];

  shippingAddressStates: State[] = [];
  billingAddressStates: State[] = [];

  constructor(
    //step 2 ijectaddform control
    private formBuilder: FormBuilder, private cartService:CartService,
      private dropdownFormServce:DropdownFormService, private checkoutService: CheckoutService,
       private router: Router, private login:LoginService,  private snack: MatSnackBar,) 
      {}

    ngOnInit(): void {

      this.reviewCartDetails();

      //read the email address of logged in user by calling login service

      const email = this.login.getUserEmail();

      this.checkoutFormGroup = this.formBuilder.group({
        customer: this.formBuilder.group({
          firstName: new FormControl('', 
                                [Validators.required, 
                                 Validators.minLength(2), 
                                 CustomValidators.notOnlyWhitespace]),
  
          lastName:  new FormControl('', 
                                [Validators.required, 
                                 Validators.minLength(2), 
                                 CustomValidators.notOnlyWhitespace]),
                                 
          email: new FormControl(email,
                                [Validators.required, Validators.pattern('^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$') ,
                                  ])
        }),

        shippingAddress: this.formBuilder.group({
          street: new FormControl('', [Validators.required, Validators.minLength(2), 
                                         CustomValidators.notOnlyWhitespace]),
          city: new FormControl('', [Validators.required, Validators.minLength(2), 
                                          CustomValidators.notOnlyWhitespace]),
          state: new FormControl('', [Validators.required]),
          country: new FormControl('', [Validators.required]),
          zipCode: new FormControl('', [Validators.required, Validators.minLength(2), 
                                              CustomValidators.notOnlyWhitespace])
        }),

        billingAddress: this.formBuilder.group({
          street: new FormControl('', [Validators.required, Validators.minLength(2), 
                                          CustomValidators.notOnlyWhitespace]),
          city: new FormControl('', [Validators.required, Validators.minLength(2), 
                                          CustomValidators.notOnlyWhitespace]),
          state: new FormControl('', [Validators.required]),
          country: new FormControl('', [Validators.required]),
          zipCode: new FormControl('', [Validators.required, Validators.minLength(2), 
                                          CustomValidators.notOnlyWhitespace])
        }),
        creditCard: this.formBuilder.group({
          cardType: new FormControl('', [Validators.required]),
          nameOnCard:  new FormControl('', [Validators.required, Validators.minLength(2), 
                                          CustomValidators.notOnlyWhitespace]),
          cardNumber: new FormControl('', [Validators.required, Validators.pattern('[0-9]{16}')]),
          securityCode: new FormControl('', [Validators.required, Validators.pattern('[0-9]{3}')]),
          expirationMonth: [''],
          expirationYear: ['']
        })

     } )

     //populate credit card months
    const startMonth: number = new Date().getMonth() + 1; //+1 bcz js is 0 based 
   // console.log('startMonth:' + startMonth);
    this.dropdownFormServce.getCreditCardMonths(startMonth).subscribe((data) => {
     // console.log('Retrieve credit card months:' + JSON.stringify(data));
      this.creditCardMonths = data;
    });

    //populate credit card years
    this.dropdownFormServce.getCreditCardYears().subscribe((data) => {
    //  console.log('Retrieve credit card years:' + JSON.stringify(data));
      this.creditCardYears = data;
    });

    //popuate countries
    this.dropdownFormServce.getCountries().subscribe((data: Country[]) => {
      //console.log('Retrieved countries: ' + JSON.stringify(data));
      this.countries = data;
    });

    }

    onSubmit() {
      
      //console.log('Handling submit button');
      // console.log("checking:" + this.checkoutFormGroup.get('creditCard')?.value.cardType);
      
      if(this.checkoutFormGroup.invalid){
        this.checkoutFormGroup.markAllAsTouched();
        return;
      }
  
      // console.log(this.checkoutFormGroup.get('customer')?.value);
      // console.log(
      //   'The shipping address country is' +
      //     this.checkoutFormGroup.get('shippingAddress')?.value.country.name
      // );
      // console.log(
      //   'The shipping address state is' +
      //     this.checkoutFormGroup.get('shippingAddress')?.value.state.name
      // );
  
        //set up order
        let order=new Order();
        order.totalPrice=this.totalPrice
        order.totalQuantity=this.totalQuantity;
  
        //get the cart items
        const cartItems=this.cartService.cartItems
  
        //create orderItems from cartItems
        let orderItems:OrderItem[]=[]
        for(let i=0;i<cartItems.length;i++){
          orderItems[i]=new OrderItem(cartItems[i])
        }
  
  
        //set up purchase
        let purchase = new Purchase();
  
        //populate purchase - customer
        purchase.customer=this.checkoutFormGroup.controls['customer'].value;
  
        //populate purchase - shipping address
        purchase.shippingAddress=this.checkoutFormGroup.controls['shippingAddress'].value;
        const shippingState:State=JSON.parse(JSON.stringify(purchase.shippingAddress.state))
        const shippingCountry:Country=JSON.parse(JSON.stringify(purchase.shippingAddress.country))
        purchase.shippingAddress.state=shippingState.name
        purchase.shippingAddress.country=shippingCountry.name
  
        // populate purchase - billing address 
        purchase.billingAddress=this.checkoutFormGroup.controls['billingAddress'].value;
        const billingState:State=JSON.parse(JSON.stringify(purchase.billingAddress.state))
        const billingCountry:Country=JSON.parse(JSON.stringify(purchase.billingAddress.country))
        purchase.billingAddress.state=billingState.name
        purchase.billingAddress.country=billingCountry.name
  
        //populate purchase - order and order items
        purchase.order=order
        purchase.orderItems=orderItems
  
        //call REST API checkoutservice
        // error handing -> next: success and error: error is error
        this.checkoutService.placeOrder(purchase).subscribe({
          next: response=>{
           // alert(`Your order has been received. \n Order tracking number: ${response.orderTrackingNumber}`)
            Swal.fire(`Your order has been received. \n Order tracking number: ${response.orderTrackingNumber}`)
       
            this.resetCart();
          },
          error: err=>{
            Swal.fire(`There was an error: ${err.message}`)
          }
        })
    }

    resetCart(){
      //reset cart data
      this.cartService.cartItems=[];
      //sending 0 so all subscribers reset themselves
      this.cartService.totalPrice.next(0);
      this.cartService.totalQuantity.next(0);
  
      //reset the form
      this.checkoutFormGroup.reset()
  
      //navigate back to the products page
      this.router.navigateByUrl("/products")
  
    }

    // Getter Methods for Fields - These are used by HTML template 
      // to get access for form control and check status of form control and so on
  get firstName()  { return this.checkoutFormGroup.get('customer.firstName')  }
  get lastName()  { return this.checkoutFormGroup.get('customer.lastName')  }
  get email()  { return this.checkoutFormGroup.get('customer.email')  }

  get shippingAddressStreet()  { return this.checkoutFormGroup.get('shippingAddress.street')  }
  get shippingAddressCity()  { return this.checkoutFormGroup.get('shippingAddress.city')  }
  get shippingAddressState()  { return this.checkoutFormGroup.get('shippingAddress.state')  }
  get shippingAddressCountry()  { return this.checkoutFormGroup.get('shippingAddress.country')  }
  get shippingAddressZipCode()  { return this.checkoutFormGroup.get('shippingAddress.zipCode')  }

  get billingAddressStreet()  { return this.checkoutFormGroup.get('billingAddress.street')  }
  get billingAddressCity()  { return this.checkoutFormGroup.get('billingAddress.city')  }
  get billingAddressState()  { return this.checkoutFormGroup.get('billingAddress.state')  }
  get billingAddressCountry()  { return this.checkoutFormGroup.get('billingAddress.country')  }
  get billingAddressZipCode()  { return this.checkoutFormGroup.get('billingAddress.zipCode')  }

  get creditCardType()  { return this.checkoutFormGroup.get('creditCard.cardType')  }
  get creditNameOnCard()  { return this.checkoutFormGroup.get('creditCard.nameOnCard')  }
  get creditCardNumber()  { return this.checkoutFormGroup.get('creditCard.cardNumber')  }
  get creditCardSecurityCode()  { return this.checkoutFormGroup.get('creditCard.securityCode')  }


  reviewCartDetails() {
    //subscribe to cart service -> total quantity and total price

    this.cartService.totalQuantity.subscribe(
      totalQuantity=>this.totalQuantity=totalQuantity
    )

    this.cartService.totalPrice.subscribe(
      totalPrice=>this.totalPrice=totalPrice
    )
  }

    copyShippingAddressToBillingAddress(event: any) {
      if (event.target.checked) {
        this.checkoutFormGroup.controls['billingAddress'].setValue(
          this.checkoutFormGroup.controls['shippingAddress'].value
        );
  
        //fix for check states
         this.billingAddressStates = this.shippingAddressStates;
      } else {
        this.checkoutFormGroup.controls['billingAddress'].reset();
  
      //   fix
         this.billingAddressStates = [];
      }
    }

    handleMonthsAndYears() {
      const creditCardFormGroup = this.checkoutFormGroup.get('creditCard');
      const currentYear: number = new Date().getFullYear();
      const selectedYear: number = Number(
        creditCardFormGroup?.value.expirationYear
      );
  
      //check currrnt year is equal to selected year then start with the current month
      let startMonth: number;

      if (currentYear === selectedYear) {
        startMonth = new Date().getMonth() + 1; //+1 bcz to get the current month bcz js is 0 based
      } else {
        startMonth = 1;
      }
      this.dropdownFormServce.getCreditCardMonths(startMonth).subscribe((data) => {
       // console.log('Retrieve credit card Month' + JSON.stringify(data));
        this.creditCardMonths = data;
      });
    }

    getStates(formGroupName: string) {
      const formGroup = this.checkoutFormGroup.get(formGroupName);
      const countryCode = formGroup?.value.country.code;
      const countryName = formGroup?.value.country.name;
  
    //  console.log(`${formGroupName} country code: ${countryCode} `);
    //  console.log(`${formGroupName} country name: ${countryName} `);
      this.dropdownFormServce.getStates(countryCode).subscribe((data: State[]) => {
        //console.log(JSON.stringify(data));
        if (formGroupName === 'shippingAddress') {
          this.shippingAddressStates = data;
        } else {
          this.billingAddressStates = data;
        }
  
        //select first item by default
        formGroup?.get('state')?.setValue(data[0]);
      });
    }
}



