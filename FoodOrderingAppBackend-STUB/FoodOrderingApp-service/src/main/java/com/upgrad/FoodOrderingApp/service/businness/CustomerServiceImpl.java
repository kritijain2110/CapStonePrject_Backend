package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.Customer;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@Transactional
@SpringBootApplication
@ComponentScan (basePackages = {"com.upgrad.FoodOrderingApp.service"})
@EntityScan ("com.upgrad.FoodOrderingApp.service.entity")
@EnableJpaRepositories ("com.upgrad.FoodOrderingApp.service.dao")
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private final Customer customer;

    public CustomerServiceImpl(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String findCustomerPassword(String contactNumber) {
        return customer.findCustomerPassword(contactNumber);
    }

    @Override
    public CustomerEntity findCustomer(String contactNumber) {
        return customer.findCustomer(contactNumber);
    }

    @Override
    public void addCustomer(String firstName, String lastName, String email, String contactNumber, String password) {
        CustomerEntity customerEntity = new CustomerEntity(firstName, lastName, email, contactNumber, password);
        customer.save(customerEntity);
    }

    @Override
    public void updateCustomer(CustomerEntity customerEntity) {
        customer.save(customerEntity);
    }

}
