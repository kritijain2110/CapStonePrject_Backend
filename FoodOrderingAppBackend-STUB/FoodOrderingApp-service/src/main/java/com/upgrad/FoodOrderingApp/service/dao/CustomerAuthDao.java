package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface CustomerAuthDao extends CrudRepository <CustomerAuthEntity, Integer> {

    @Query(nativeQuery = true,value = "SELECT * FROM CUSTOMER_AUTH WHERE access_token=?1")
    CustomerAuthEntity isCustomerLoggedIn(String accessToken);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value="UPDATE CUSTOMER_AUTH SET logout_at=NOW() WHERE access_token=?1")
    void removeAuthToken( String accessToken);
}
