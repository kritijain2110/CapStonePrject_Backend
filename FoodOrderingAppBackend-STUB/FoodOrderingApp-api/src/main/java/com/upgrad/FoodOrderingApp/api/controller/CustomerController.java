package com.upgrad.FoodOrderingApp.api.controller;


import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import com.upgrad.FoodOrderingApp.api.config.Constants;
import com.upgrad.FoodOrderingApp.service.businness.CustomerAuthService;
import com.upgrad.FoodOrderingApp.service.businness.CustomerService;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Matcher;

@RestController
@RequestMapping("/customer")

public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerAuthService customerAuthService;


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


    //Method to login the user

    @PostMapping("/login")
    @CrossOrigin
    public ResponseEntity<?> login(@RequestParam String contactNumber, @RequestParam String password){
        String passwordByUser = String.valueOf(customerService.findCustomerPassword(contactNumber));
        String sha256hex = Hashing.sha256()
                .hashString(password, Charsets.US_ASCII)
                .toString();
        if(customerService.findCustomerPassword(contactNumber)==null) return new ResponseEntity<>("This contact number has not been registered!",HttpStatus.OK);
        else if (!(passwordByUser.equalsIgnoreCase(sha256hex))) {
            return new ResponseEntity<>("Invalid Credentials",HttpStatus.UNAUTHORIZED);
        }
        else{
            CustomerEntity customerEntity = customerService.findCustomer(contactNumber);
            String accessToken = UUID.randomUUID().toString();
            customerAuthService.addAccessToken(customerEntity.getId(),accessToken);
            HttpHeaders headers = new HttpHeaders();
            headers.add("access-token", accessToken);
            List<String> header = new ArrayList<>();
            header.add("access-token");
            headers.setAccessControlExposeHeaders(header);
            return new ResponseEntity<>(customerEntity,headers,HttpStatus.OK);
        }
    }

    //Method to logout the user
    @PutMapping("/logout")
    @CrossOrigin
    public ResponseEntity<String> logout(@RequestHeader String accessToken){
        if(customerAuthService.isCustomerLoggedIn(accessToken) == null){
            return new ResponseEntity<>("Please Login first to access this endpoint!", HttpStatus.UNAUTHORIZED);
        }
        else if(customerAuthService.isCustomerLoggedIn(accessToken).getLogoutAt()!=null){
            return new ResponseEntity<>("You have already logged out. Please Login first to access this endpoint!", HttpStatus.UNAUTHORIZED);
        }  else{
            customerAuthService.removeAccessToken(accessToken);
            return new ResponseEntity<>("You have logged out successfully!",HttpStatus.OK);}
    }

    //Method to update the user
    @PutMapping("/user")
    @CrossOrigin
    public ResponseEntity<String> updateuser(@RequestHeader String accessToken, @RequestParam(value = "First Name") String firstname, @RequestParam(value = "Last Name", required = false) String lastname){
        CustomerAuthEntity tokendetails = customerAuthService.isCustomerLoggedIn(accessToken);
        if(tokendetails == null){
            return new ResponseEntity<>("Please Login first to access this endpoint!", HttpStatus.UNAUTHORIZED);
        }
        else if(customerAuthService.isCustomerLoggedIn(accessToken).getLogoutAt()!=null){
            return new ResponseEntity<>("You have already logged out. Please Login first to access this endpoint!", HttpStatus.UNAUTHORIZED);
        }  else{
            CustomerEntity customerEntity = tokendetails.getCustomer();
            customerService.updateCustomerDetails(firstname,lastname,customerEntity);
            CustomerEntity updated = customerService.findCustomerById(customerEntity.getId());
            return new ResponseEntity(updated, HttpStatus.OK);
        }

    }

    //Method to change the password
    @PutMapping("/password")
    @CrossOrigin
    public ResponseEntity<?> changePassword(@RequestParam String oldPassword, @RequestParam String newPassword, @RequestHeader String accessToken) {
        CustomerAuthEntity customerAuthEntity = customerAuthService.isCustomerLoggedIn(accessToken);
        if (customerAuthEntity == null) {
            return new ResponseEntity<>("Please Login first to access this endpoint!", HttpStatus.UNAUTHORIZED);
        }

        if (customerAuthEntity.getLogoutAt() != null) {
            return new ResponseEntity<Object>("You have already logged out. Please Login first to access this endpoint!", HttpStatus.UNAUTHORIZED);
        }

        CustomerEntity customerEntity = customerAuthEntity.getCustomer();
        String oldPasswordHex = Hashing.sha256().hashString(oldPassword, Charsets.US_ASCII).toString();
        String oldPasswordDb = customerEntity.getPassword();
        if (!oldPasswordDb.equals(oldPasswordHex)) {
            return new ResponseEntity<>("Your password did not match your old password!", HttpStatus.BAD_REQUEST);
        }

        if (newPassword.length() < Constants.PASSWORD_MIN_LENGTH || newPassword.equals(newPassword.toLowerCase()) || !Constants.NUMBER_REGEX.matcher(newPassword).matches() || !Constants.SPECIAL_REGEX.matcher(newPassword).find()) {
            return new ResponseEntity<Object>("Weak password!", HttpStatus.BAD_REQUEST);
        }

        String newPasswordHex = Hashing.sha256().hashString(newPassword, Charsets.US_ASCII).toString();
        customerEntity.setPassword(newPasswordHex);
        customerService.updateCustomer(customerEntity);
        return new ResponseEntity<>("Password updated successfully!", HttpStatus.OK);
    }


}
