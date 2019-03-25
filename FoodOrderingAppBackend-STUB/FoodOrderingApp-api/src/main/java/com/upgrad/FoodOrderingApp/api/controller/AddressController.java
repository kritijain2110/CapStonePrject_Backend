package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.service.businness.AddressService;
import com.upgrad.FoodOrderingApp.service.businness.CustomerAuthService;
import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.entity.StateEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private CustomerAuthService customerAuthService;

    @PostMapping("/address")
    @CrossOrigin
    public ResponseEntity<?> addaddress(@RequestParam String flatBuildnumber, @RequestParam String locality, @RequestParam String city, @RequestParam Integer stateid, @RequestParam String zipcode, @RequestParam(required = false) String type, @RequestHeader String accessToken){
        String msg = "";
        HttpStatus httpcode = HttpStatus.OK;
        if(customerAuthService.isCustomerLoggedIn(accessToken) == null){
            return new ResponseEntity<>("Please Login first to access this endpoint!", HttpStatus.UNAUTHORIZED);
        }
        else if(customerAuthService.isCustomerLoggedIn(accessToken).getLogoutAt()!=null){
            return new ResponseEntity<>("You have already logged out. Please Login first to access this endpoint!", HttpStatus.UNAUTHORIZED);
        }
        else
        {
            Integer customerId = customerAuthService.getCustomerId(accessToken);
            if(zipcode.length() == 6 && zipcode.matches("[0-9]+"))
            {
                String addrtype="temp";
                StateEntity stateEntity = addressService.checkValidState(stateid);

                int addressid = addressService.countAddress()+1;


                AddressEntity addressEntity = new AddressEntity(flatBuildnumber,locality,city,zipcode,stateEntity);

                if(type==null || type.equalsIgnoreCase("temp") ){
                    addrtype = "temp";
                }
                else if (type.equalsIgnoreCase("perm"))
                {
                    addrtype = "perm";
                }
                else{
                    msg = "Address type can be perm or temp. If nothing is entered, default is temp.";
                    httpcode = HttpStatus.BAD_REQUEST;
                    return  new ResponseEntity<>(msg,httpcode);
                }

                addressService.addAddress(addressEntity);
                addressService.addCustomerAddress(addrtype,customerId,addressid);
                httpcode = HttpStatus.CREATED;
                msg = "Address has been saved successfully!";

            } else{
                msg = "Invalid zipcode!";
                httpcode = HttpStatus.BAD_REQUEST;
            }
            return  new ResponseEntity<>(msg,httpcode);

        }
    }



    @GetMapping("/address/user")
    @CrossOrigin
    public ResponseEntity<?> getAllPermanentAddress(@RequestHeader String accessToken) {

        String msg = "" ;
        HttpStatus httpStatus = HttpStatus.OK ;

        CustomerAuthEntity customerAuthEntity = customerAuthService.isCustomerLoggedIn(accessToken);

        if (customerAuthEntity == null) {
            msg = "Please Login first to access this endpoint!" ;
            httpStatus =  HttpStatus.UNAUTHORIZED ;
        }
        else if (customerAuthService.isCustomerLoggedIn(accessToken).getLogoutAt() != null ) {
            msg = "You have already logged out. Please Login first to access this endpoint!" ;
            httpStatus =  HttpStatus.UNAUTHORIZED ;
        } else {
            Integer customerId = customerAuthEntity.getCustomer().getId();
            System.out.println("User logged in for whom address request is made is "+customerId);

            if ( addressService.getPermAddress(customerId)  == null )
            {
                msg = "No permanent address found!" ;
                httpStatus = HttpStatus.BAD_REQUEST ;
            }
            else
            {
                return new ResponseEntity<>(addressService.getPermAddress(customerId), HttpStatus.OK);

            }
        }
        return new ResponseEntity<>(msg , httpStatus);
    }



    @PutMapping("/address/{addressId}")
    @CrossOrigin
    public ResponseEntity<?> updateAddressById(@PathVariable Integer addressId , @RequestParam(required = false) String flatBuildNo , @RequestParam(required = false) String locality , @RequestParam(required = false) String city , @RequestParam(required = false) String zipcode , @RequestParam(required = false) Integer stateId , @RequestHeader String accessToken) {

        String message = "" ;
        HttpStatus httpStatus = HttpStatus.OK ;

        CustomerAuthEntity customerAuthEntity = customerAuthService.isCustomerLoggedIn(accessToken);


        if (customerAuthEntity == null) {
            message = "Please Login first to access this endpoint!" ;
            httpStatus =  HttpStatus.UNAUTHORIZED ;
        }
        else if (customerAuthService.isCustomerLoggedIn(accessToken).getLogoutAt() != null ) {
            message = "You have already logged out. Please Login first to access this endpoint!" ;
            httpStatus =  HttpStatus.UNAUTHORIZED ;
        } else {
            Integer customerId = customerAuthEntity.getCustomer().getId();


            if (zipcode != null ){
                if (! ( zipcode.length() == 6 && zipcode.matches("[0-9]+") )) {
                    return new ResponseEntity<>("Invalid zipcode!" , HttpStatus.BAD_REQUEST);
                }
            }



            AddressEntity add = addressService.getaddressById(addressId);

            if (addressService.getAddress(addressId) == false) {
                message = "No address with this address id!";
                httpStatus = HttpStatus.BAD_REQUEST;
            } else {
                /*
                Get the current details if nothing is supplied
                 */
                if (flatBuildNo == null)
                    flatBuildNo = add.getFlatbuildNumber();

                if (locality == null)
                    locality = add.getLocality();

                if (city == null)
                    city = add.getCity();

                if (zipcode == null)
                    zipcode = add.getZipcode();

                if (stateId == null)
                    stateId = add.getStateEntity().getId() ;


                addressService.updateAddressById(flatBuildNo, locality, city, zipcode, stateId, addressId);

                message = "Address has been updated successfully!";
                httpStatus = HttpStatus.OK ;
            }
        }
        return new ResponseEntity<>(message , httpStatus);
    }


    @DeleteMapping("/address/{addressId}")
    @CrossOrigin
    public ResponseEntity<?> deleteAddressById(@PathVariable Integer addressId , @RequestHeader String accessToken) {

        String msg = "";
        HttpStatus httpStatus = HttpStatus.OK;

        CustomerAuthEntity customerAuthEntity = customerAuthService.isCustomerLoggedIn(accessToken);


        if (customerAuthEntity == null) {
            msg = "Please Login first to access this endpoint!";
            httpStatus = HttpStatus.UNAUTHORIZED;
        }
        else if (customerAuthService.isCustomerLoggedIn(accessToken).getLogoutAt()!= null ) {
            msg = "You have already logged out. Please Login first to access this endpoint!";
            httpStatus = HttpStatus.UNAUTHORIZED;
        } else {

            boolean add = addressService.getAddress(addressId);

            if (add == false) {
                msg = "No address with this address id!";
                httpStatus = HttpStatus.BAD_REQUEST;
            } else {


                addressService.deleteAddressById(addressId) ;
                addressService.deleteCustomerAddressById(addressId);

                msg = "Address has been deleted successfully!" ;
                httpStatus = HttpStatus.OK ;
            }
        }
        return new ResponseEntity<>(msg , httpStatus);
    }



    @GetMapping("/states")
    public ResponseEntity<?> getAllStateDetails() {
        return new ResponseEntity<>( addressService.getAllStates() , HttpStatus.OK);
    }


}
