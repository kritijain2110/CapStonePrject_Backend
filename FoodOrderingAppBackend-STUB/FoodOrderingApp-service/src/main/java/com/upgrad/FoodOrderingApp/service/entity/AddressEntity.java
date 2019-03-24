package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.*;

@Entity
@Table(name = "address")
public class AddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "flat_buil_number")
    private String flatbuildNumber;

    @Column(name = "locality")
    private String locality;

    @Column(name = "city")
    private String city;

    @Column(name = "zipcode")
    private String zipcode;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private StateEntity stateEntity;

    public AddressEntity() {
    }

    public AddressEntity(String flatbuildNumber, String locality, String city, String zipcode, StateEntity stateEntity) {
        this.flatbuildNumber = flatbuildNumber;
        this.locality = locality;
        this.city = city;
        this.zipcode = zipcode;
        this.stateEntity = stateEntity;
    }

    public StateEntity getStateEntity() {
        return stateEntity;
    }

    public void setState(StateEntity stateEntity) {
        this.stateEntity = stateEntity;
    }

    public AddressEntity(Integer id, String flat_build_number, String locality, String city, String zipcode, StateEntity stateEntity) {
        this.id = id ;
        this.flatbuildNumber = flat_build_number ;
        this.city = city ;
        this.locality = locality ;
        this.zipcode = zipcode ;
        this.stateEntity = stateEntity ;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFlatbuildNumber() {
        return flatbuildNumber;
    }

    public void setFlatbuildNumber(String flatbuildNumber) {
        this.flatbuildNumber = flatbuildNumber;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
}
