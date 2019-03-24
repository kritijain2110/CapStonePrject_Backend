package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AddressDao extends CrudRepository <AddressEntity, Integer> {
    @Transactional
    @Modifying
    @Query(nativeQuery = true,value="INSERT INTO Address (flat_buil_number,locality,city,zipcode,state_id) VALUES (?1,?2,?3,?4,?5)")
    Integer addAddress(String flat_buil_number, String locality, String city,String zipcode, Integer state_id );


    @Transactional
    @Modifying
    @Query(nativeQuery = true,value="INSERT INTO Customer_address (type,customer_id,address_id) VALUES (?1,?2,?3)")
    Integer addCustomerAddress(String type, Integer user_id , Integer address_id);


    @Query(nativeQuery = true,value = "SELECT max(id) FROM ADDRESS ")
    Integer countAddress();

    @Query(nativeQuery = true, value = "SELECT * FROM address WHERE (id) = (?1)")
    AddressEntity getAddressById(Integer addressId);

    @Query(nativeQuery = true,value = "SELECT *  FROM ADDRESS where id = ?1 ")
    AddressEntity findAddressById(Integer id);


    @Transactional
    @Modifying
    @Query(nativeQuery = true,value="UPDATE ADDRESS SET flat_buil_number =?1 , locality=?2  , city=?3 , zipcode=?4, state_id=?5 WHERE id=?6")
    Integer updateAddressById( String flat_buil_number, String locality, String city , String zipcode, Integer state_id, Integer id);


    @Transactional
    @Modifying
    @Query(nativeQuery = true,value="DELETE FROM ADDRESS WHERE id =?1")
    Integer deleteAddressById( Integer id);


    @Transactional
    @Modifying
    @Query(nativeQuery = true,value="DELETE FROM Customer_Address WHERE address_id =?1")
    Integer deleteCustomerAddressById( Integer id);


    @Query(nativeQuery = true,value = "SELECT address_id  FROM USER_ADDRESS where type = 'perm' and user_id = ?1 ")
    Iterable<Integer> getPermAdd(Integer id);
}
