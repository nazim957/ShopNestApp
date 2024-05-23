package com.shopnest.service;

import com.shopnest.dto.Purchase;
import com.shopnest.dto.PurchaseResponse;

public interface CheckoutService {

    PurchaseResponse placeOrder(Purchase purchase); //another option for final is we can use @NotNull on field
}
