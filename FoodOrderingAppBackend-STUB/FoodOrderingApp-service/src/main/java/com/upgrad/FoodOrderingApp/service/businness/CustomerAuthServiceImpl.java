package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.Customer;
import com.upgrad.FoodOrderingApp.service.dao.CustomerAuthDao;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;

@Service
@Transactional
public class CustomerAuthServiceImpl implements CustomerAuthService {

    private final CustomerAuthDao customerAuthDao;
    private final Customer customer;

    public CustomerAuthServiceImpl(CustomerAuthDao customerAuthDao, Customer customer) {
        this.customerAuthDao = customerAuthDao;
        this.customer = customer;
    }

    // add access token details to the database
    @Override
    public void addAccessToken(Integer userId, String accessToken) {
        Optional<CustomerEntity> customerEntity = customer.findById(userId);
        Date date = new Date();
        CustomerAuthEntity customerAuthEntity = new CustomerAuthEntity(customerEntity.get(),accessToken,date);
        customerAuthDao.save(customerAuthEntity);
    }

    // remove access token from the database. Thus the access token is no longer viable
    @Override
    public void removeAccessToken(String accessToken) {
        customerAuthDao.removeAuthToken(accessToken);
    }

    // This method is used to check whether the user is logged in or not
    @Override
    public CustomerAuthEntity isCustomerLoggedIn(String accessToken) {
        return customerAuthDao.isCustomerLoggedIn(accessToken);
    }
}
