package com.onlineshoppy.service;

import com.onlineshoppy.dto.Purchase;
import com.onlineshoppy.dto.PurchaseResponse;

public interface CheckoutService {

    PurchaseResponse placeOrder(Purchase purchase); //another option for final is we can use @NotNull on field
}
