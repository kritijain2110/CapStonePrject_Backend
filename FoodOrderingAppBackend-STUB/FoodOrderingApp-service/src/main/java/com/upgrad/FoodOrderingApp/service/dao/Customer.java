package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface Customer extends CrudRepository <CustomerEntity, Integer> {
    @Query(nativeQuery = true,value="SELECT PASSWORD FROM CUSTOMER WHERE contact_number=?1")
    String findCustomerPassword(String contactNumber);

    @Query(nativeQuery = true,value = "SELECT * FROM CUSTOMER WHERE contact_number=?1")
    CustomerEntity findCustomer(String contactNumber);

    @Query(nativeQuery = true,value = "SELECT * FROM CUSTOMER WHERE contact_number=?1")
    void addCustomer(CustomerEntity newcustomer);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE CUSTOMER SET firstname = ?1, lastname=?2 where id=?3")
    void updateddetails(String firstname, String lastname,long id);

    @Query(nativeQuery = true,value = "SELECT * FROM CUSTOMER WHERE id=?1")
    CustomerEntity findCustomerByID(long userid);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value="UPDATE CUSTOMER SET password =?1  WHERE id=?2")
    Integer updatePassword(String password, Integer id);


    @Query(nativeQuery = true,value="SELECT * FROM CUSTOMER WHERE id=?1")
    CustomerEntity findPasswordById(long id);
}
