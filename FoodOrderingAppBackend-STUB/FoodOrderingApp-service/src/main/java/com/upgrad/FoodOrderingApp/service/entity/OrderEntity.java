package com.upgrad.FoodOrderingApp.service.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.ZonedDateTime;

@Entity
@Table(name = "order")

public class OrderEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "UUID")
    @Size(max = 64)
    @NotNull
    private String uuid;


    @Column
    @NotNull
    private String bill;

    @ManyToOne
    @JoinColumn(name = "coupon" referencedColumnName = "id")

    private CouponEntity coupon;

    @Column
    private String discount;

    @Column
    @NotNull
    private ZonedDateTime date;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "payment" referencedColumnName = "id")
    private PaymentEntity payment;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "customer" referencedColumnName = "id")
    private CustomerEntity customer;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "address" referencedColumnName = "id")
    private AddressEntity address;


    @ManyToOne
    @NotNull
    @JoinColumn(name = "item_quantities" referencedColumnName = "id")
    private Item_quantitiesEntity item_quantities;



    public OrderEntity(String bill,CouponEntity coupon,String discount, ZonedDateTime date,PaymentEntity payment, CustomerEntity customer,AddressEntity address, Item_quantitiesEntity item_quantities)
    {
        this.bill = bill;
        this.coupon = coupon;
        this.discount = discount;
        this.date = date;
        this.payment = payment;
        this.customer = costumer;
        this.address = address;
        this.item_quantities = item_quantities;
    }

    public String getBill() {
        return bill;
    }

    public void setBill(String bill) {
        this.bill = bill;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }


    public PaymentEntity  getPayment() {
        return payment;
    }

    public void setPaymnt(PaymentEntity payment) {
        this.payment = payment;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public CouponEntity getCoupon() {
        return coupon;
    }

    public void setCoupon(CouponEntity coupon) {
        this.coupon = coupon;
    }
    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity coupon) {
        this.customer = customer;
    }
    public AddressEntity getAddress() {
        return address;
    }

    public void setAddress(AddressEntity address) {
        this.address = address;
    }

    public Item_quantitiesEntity getItem_quantities() {
        return item_quantities;
    }

    public void setItem_quantities(Item_quantitiesEntity address) {
        this.item_quantities = item_quantities;
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

