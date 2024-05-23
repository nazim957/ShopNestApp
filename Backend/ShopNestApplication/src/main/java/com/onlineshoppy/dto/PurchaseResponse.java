package com.onlineshoppy.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PurchaseResponse {

    private final String orderTrackingNumber;
}
