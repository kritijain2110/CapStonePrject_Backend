package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;

public interface CustomerService {
    String findCustomerPassword(String contactNumber);

    CustomerEntity findCustomer(String contactNumber);

    void addCustomer(String firstName, String lastName, String email, String contactNumber, String password);

    void updateCustomer (CustomerEntity customerEntity);
}
