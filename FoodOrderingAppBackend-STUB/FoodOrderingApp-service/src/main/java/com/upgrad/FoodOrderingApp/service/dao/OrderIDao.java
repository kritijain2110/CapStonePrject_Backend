package com.upgrad.FoodOrderingApp.service.entity;


import com.upgrad.FoodOrderingApp.service.entity.OrderEntity;
import com.upgrad.FoodOrderingApp.service.entity.PaymentEntity;
import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.entity.Item_quantitiesEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Repository
public class OrderDao {

    @PersistenceContext
    private EntityManager entityManager;


    public TypedQuery<OrderEntity> getOrdersByCustomer(CustomerEntity customerEntity) {
        try {
            return entityManager.createNamedQuery("getAllOrdersByCustomer", OrderEntity.class).setParameter("customer", customerEntity);
        } catch (NoResultException nre) {
            return null;
        }
    }
    public OrderEntity editOrder(OrderEntity orderEntity) {
        return entityManager.merge(orderEntity);
    }

}