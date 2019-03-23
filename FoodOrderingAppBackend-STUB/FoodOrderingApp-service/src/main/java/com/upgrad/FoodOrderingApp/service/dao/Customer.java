package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface Customer extends CrudRepository <CustomerEntity, Integer> {
    @Query(nativeQuery = true,value="SELECT PASSWORD FROM CUSTOMER WHERE contact_number=?1")
    String findCustomerPassword(String contactNumber);

    @Query(nativeQuery = true,value = "SELECT * FROM CUSTOMER WHERE contact_number=?1")
    CustomerEntity findCustomer(String contactNumber);
}
