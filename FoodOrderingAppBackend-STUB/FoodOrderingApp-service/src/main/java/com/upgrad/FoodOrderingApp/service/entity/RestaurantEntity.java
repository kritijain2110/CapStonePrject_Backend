package com.upgrad.FoodOrderingApp.service.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Table(name = "restaurant")
@Entity
public class RestaurantEntity implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "uuid")
    @Size(max = 200)
    private String uuid;


    @Column(name = "restaurant_name")
    @Size(max = 50)
    @NotNull
    private String restaurant_name;

    @Column(name = "photo_url")
    @Size(max = 255)
    @NotNull
    private String restaurant_name;

    @Column(name = "customer_rating")
    @NotNull
    private int customer_rating;

    @Column(name = "average_price_for_two")
    @NotNull
    private int average_price_for_two;

    @Column(name = "number_of_customers_rated")
    @NotNull
    private int number_of_customers_rated;

    @Column(name = "address_id")
    @NotNull
    private int address_id;


    @OnetoOne
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private AddressEntity address;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getRestaurantName() {
        return restaurant_name;
    }

    public void setRestaurantName(String restaurant_name) {
        this.restaurant_name = restaurant_name;
    }

    public AddressEntity getAddress() {
        return address;
    }

    public void setAddressId(AddressEntity address) {
        this.address = address;
    }

    public void setCustomer_rating(int customer_rating)
    {
        this.customer_rating = customer_rating;
    }

    public int getCustomer_rating()
    {
        return customer_rating;
    }

    public void setAverage_price_for_two(int average_price_for_two)
    {
        this.average_price_for_two = average_price_for_two;
    }

    public int getAverage_price_for_two()
    {
        return average_price_for_two;
    }

    public void setNumber_of_customers_rated(int number_of_customers_rated)
    {
        this.number_of_customers_rated = number_of_customers_rated;
    }

    public int getNumber_of_customers_rated()
    {
        return number_of_customers_rated;
    }

    @Override
    public boolean equals(Object obj) {
        return new EqualsBuilder().append(this, obj).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this).hashCode();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
