package com.shopnest.dto;


import com.shopnest.model.Address;
import com.shopnest.model.Customer;
import com.shopnest.model.Order;
import com.shopnest.model.OrderItem;
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
