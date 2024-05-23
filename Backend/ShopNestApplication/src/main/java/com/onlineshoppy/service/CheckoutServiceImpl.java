package com.onlineshoppy.service;

import com.onlineshoppy.dto.Purchase;
import com.onlineshoppy.dto.PurchaseResponse;
import com.onlineshoppy.model.Customer;
import com.onlineshoppy.model.Order;
import com.onlineshoppy.model.OrderItem;
import com.onlineshoppy.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    private CustomerRepository customerRepository;

    @Autowired
    public CheckoutServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {

        //retrieve the order info from dto
        Order order = purchase.getOrder();

        //generate tracking number
        String orderTrackingNumber=generateOrderTrackingNumber();
        order.setOrderTrackingNumber(orderTrackingNumber);

        //populate order with orderitems
        Set<OrderItem> orderItems = purchase.getOrderItems();
        orderItems.forEach(item->order.add(item));

        //populate order with billing and shipping address
        order.setShippingAddress(purchase.getShippingAddress());
        order.setBillingAddress(purchase.getBillingAddress());


        //populate customer with order
        Customer customer =purchase.getCustomer();

        //check if this is an existing customer
        String email=customer.getEmail();
        Customer customerFromDB=customerRepository.findByEmail(email);
        if(customerFromDB!=null){
            //we found them..
            customer=customerFromDB;
        }
        customer.add(order);

        //save to the database
        customerRepository.saveAndFlush(customer);

//        System.out.println("%%%%%%%%%%%%%");
//        System.out.println(customer.toString());

        //return a response
        return new PurchaseResponse(orderTrackingNumber);
    }

    private String generateOrderTrackingNumber() {
        //want to give a unique id that is hard to guess and random
        //generate a random UUID number(UUID version-4) universally unique identifier
        return UUID.randomUUID().toString();

    }
}
