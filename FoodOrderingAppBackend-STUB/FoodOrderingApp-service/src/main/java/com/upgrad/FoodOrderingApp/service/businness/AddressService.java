package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.StateEntity;

public interface AddressService {
    Iterable<StateEntity> getAllStates() ;



    StateEntity checkValidState(Integer id);


    Integer addAddress(AddressEntity addressEntity);


    Integer countAddress();


    Integer addCustomerAddress(String temp, Integer user_id, Integer address_id) ;

    AddressEntity getaddressById( Integer addressId);

    AddressEntity getAddressById(Integer addressId);


    Boolean getAddress( Integer addressId);


    Integer updateAddressById (String flat_build_num , String locality, String city, String zipcode , Integer state_id , Integer id);


    Integer deleteAddressById (Integer id );


    Integer deleteCustomerAddressById(Integer id);


    Iterable<AddressEntity> getPermAddress(Integer userId) ;
}
