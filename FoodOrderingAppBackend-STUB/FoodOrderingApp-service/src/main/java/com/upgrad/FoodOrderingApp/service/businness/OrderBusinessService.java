package com.upgrad.FoodOrderingApp.service.business;

import com.upgrad.FoodOrderingApp.service.dao.OrderDao;
import com.upgrad.FoodOrderingApp.service.dao.CouponDao;
import com.upgrad.FoodOrderingApp.service.dao.AddressDao;
import com.upgrad.FoodOrderingApp.service.dao.ResturantDao;
import com.upgrad.FoodOrderingApp.service.dao.PaymentDao;
import com.upgrad.FoodOrderingApp.service.dao.CustomerDao;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.exception.CouponNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.PaymentNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.AddressNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.ResturantNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.CustomerNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.InvalidOrderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;

@Service
public class OrderBusinessService {


    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private OrderDao orderDao;

    /**
     * The method implements the business logic for getAllOrdersToCustomer endpoint.
     */
    public TypedQuery<OrderEntity> getOrdersByCustomer(String customerId, String authorization) throws AuthorizationFailedException, InvalidOrderException {
        CustomerAuthEntity customerAuthEntity = customerDao.getCustomerAuthByAccesstoken(authorization);

        if (customerAuthEntity == null) {
            throw new AuthorizationFailedException("ATHR-001", "Customer is not Logged in.");
        }


        if (customerAuthEntity.getLogoutAt() != null) {
            throw new AuthorizationFailedException("ATHR-002", "Customer is logged out. Log in again to access this endpoint.");
        }

        if (customerAuthEntity == null && customerAuthEntity.getLogoutAt() == null) {
            throw new AuthorizationFailedException("ATHR-003", "Your session is expired. Log in again to access this endpoint");
        }

        return orderDao.getOrdersByCustomer(customerEntity);
    }

    /**
     *    The method implements the business logic for postSaveOrdersToCustomer endpoint.
     */

    public TypedQuery<CouponEntity> getCouponByCustomer(String customerId, String authorization) throws AuthorizationFailedException, InvalidCouponException {
        CouponAuthEntity couponAuthEntity = userDao.getCustomerAuthByAccesstoken(authorization);

        if (couponAuthEntity == null) {
            throw new AuthorizationFailedException("ATHR-001", "Customer is not Logged in.");
        }


        if (couponAuthEntity.getLogoutAt() != null) {
            throw new AuthorizationFailedException("ATHR-002", "Customer is logged out. Log in again to access this endpoint.");
        }


        if (couponAuthEntity.getLogoutAt() != null && couponAuthEntity == null) {
            throw new AuthorizationFailedException("ATHR-003", "Your session is expired. Log in again to access this endpoint.");
        }

        CouponEntity couponEntity = couponDao.getCouponByUuid(couponId);

        if (CouponEntity == null) {
            throw new CouponNotFoundException("CPF-002", "No coupon by this id");
        }
        return couponDao.getCouponByUuid(couponEntity);

        AddressEntity addressEntity = addressDao.getAddressByUuid(addressId);

        if (AddressEntity == null) {
            throw new AddressNotFoundException("ANF-003", "No address by this id");
        }
        return addressDao.getAddressByUuid(addressEntity);

        if (customerAuthEntity.getCustomer() == orderEntity1.getCustomer()) {

            return orderDao.editOrder(orderEntity);

        } else
            throw new AuthorizationFailedException("ATHR-004", "You are not authorized to view/update/delete any one else's addres");

        PaymentEntity paymentEntity = paymentDao.getPaymentByUuid(paymentId);

        if (PaymentEntity == null) {
            throw new PaymentNotFoundException("PNF-002", "No payment method found by this id");
        }
        return paymentDao.getPaymentById(paymentEntity);


        ResturantEntity resturantEntity = resturantDao.getResturantByUuid(resturantId);

        if (ResturantEntity == null) {
            throw new RestaurantNotFoundException("RNF-001", "No restaurant by this id");
        }
        return resturantDao.getResturantById(resturantEntity);

    }
}
