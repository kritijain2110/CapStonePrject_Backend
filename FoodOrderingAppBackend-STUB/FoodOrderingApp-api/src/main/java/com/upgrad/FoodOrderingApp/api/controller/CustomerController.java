package com.upgrad.FoodOrderingApp.api.controller;


import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import com.upgrad.FoodOrderingApp.api.config.Constants;
import com.upgrad.FoodOrderingApp.service.businness.CustomerService;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.regex.Matcher;

@RestController
@RequestMapping("/customer")

public class CustomerController {

    @Autowired
    private CustomerService customerService;


    //Method to sign up the user
    @PostMapping("/signup")
    @CrossOrigin
    public ResponseEntity<?> signout(@RequestParam String firstName, @RequestParam(value = "lastName", required = false) String lastName, @RequestParam String email, @RequestParam String contactNumber, @RequestParam String password) {

        CustomerEntity customerEntity = customerService.findCustomer(contactNumber);
        if (Objects.nonNull(customerEntity)) {
            return new ResponseEntity<Object>("Try any other contact number, this contact number has already been registered!", HttpStatus.CONFLICT);
        }

        Matcher emailMatcher = Constants.VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        if (!emailMatcher.matches()) {
            return new ResponseEntity<Object>("Invalid email-id format!", HttpStatus.BAD_REQUEST);
        }

        Matcher contactMatcher = Constants.VALID_CONTACT_NUMBER_REGEX.matcher(contactNumber);
        if (!contactMatcher.matches()) {
            return new ResponseEntity<Object>("Invalid contact number!", HttpStatus.BAD_REQUEST);
        }

        if(password.length() < Constants.PASSWORD_MIN_LENGTH || password.equals(password.toLowerCase()) || !Constants.NUMBER_REGEX.matcher(password).matches() || !Constants.SPECIAL_REGEX.matcher(password).find()){
            return new ResponseEntity<Object>("Weak password!", HttpStatus.BAD_REQUEST);
        }

        String sha256hex = Hashing.sha256()
                .hashString(password, Charsets.US_ASCII)
                .toString();
        customerService.addCustomer(firstName, lastName, email, contactNumber, sha256hex);

        return new ResponseEntity<Object>("User with contact number "+ contactNumber +" successfully registered!", HttpStatus.CREATED);
    }


}
