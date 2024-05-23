package com.onlineshoppy.dto;


import com.onlineshoppy.model.Address;
import com.onlineshoppy.model.Customer;
import com.onlineshoppy.model.Order;
import com.onlineshoppy.model.OrderItem;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class Purchase {

    private Customer customer;
    private Address shippingAddress;
    private Address billingAddress;
    private Order order;
    private Set<OrderItem> orderItems;
}
