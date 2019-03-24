package com.upgrad.FoodOrderingApp.service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * CustomerEntity class contains all the attributes to be mapped to all the fields in customer table in the database.
 * All the annotations which are used to specify all the constraints to the columns in the database must be correctly implemented.
 */

@Entity
@Table(name = "customer")

public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "UUID", nullable = false)
    @Size(max = 64)
    @NotNull
    private String uuid;

    @Column(name = "firstname", nullable = false)
    @NotNull
    private String firstName;

    @Column(name = "lastname")
    @NotNull
    private String lastName;

    @Column(name = "email", nullable = false)
    @NotNull
    private String email;

    @Column(name = "contact_number", nullable = false, unique = true)
    private String contactNumber;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Column(name = "SALT")
    @NotNull
    @Size(max = 200)
    private String salt;

    public CustomerEntity(){
    }

    public CustomerEntity(String firstName, String lastName, String email, String contactNumber, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.contactNumber = contactNumber;
        this.password = password;
        this.salt = salt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}
