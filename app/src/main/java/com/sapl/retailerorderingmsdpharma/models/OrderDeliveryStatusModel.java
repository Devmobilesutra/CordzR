package com.sapl.retailerorderingmsdpharma.models;

/**
 * Created by Sony on 13/02/2018.
 */

public class OrderDeliveryStatusModel {
    String distributorId;
    String OrderStatus;
    String OrderDate;
    String OrderID;
    String Amount;
    String cart_count;
    String address;
    String contact_no_land;
    String contact_no_mob;
    String address_outlet_info;

    @Override
    public String toString() {
        return "OrderDeliveryStatusModel{" +
                "distributorId='" + distributorId + '\'' +
                ", OrderStatus='" + OrderStatus + '\'' +
                ", OrderDate='" + OrderDate + '\'' +
                ", OrderID='" + OrderID + '\'' +
                ", Amount='" + Amount + '\'' +
                ", cart_count='" + cart_count + '\'' +
                ", address='" + address + '\'' +
                ", contact_no_land='" + contact_no_land + '\'' +
                ", contact_no_mob='" + contact_no_mob + '\'' +
                '}';
    }

    public String getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(String distributorId) {
        this.distributorId = distributorId;
    }

    public String getOrderStatus() {
        return OrderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        OrderStatus = orderStatus;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(String orderDate) {
        OrderDate = orderDate;
    }

    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String orderID) {
        OrderID = orderID;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getCart_count() {
        return cart_count;
    }

    public void setCart_count(String cart_count) {
        this.cart_count = cart_count;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact_no_land() {
        return contact_no_land;
    }

    public void setContact_no_land(String contact_no_land) {
        this.contact_no_land = contact_no_land;
    }

    public String getContact_no_mob() {
        return contact_no_mob;
    }

    public void setContact_no_mob(String contact_no_mob) {
        this.contact_no_mob = contact_no_mob;
    }

    public String getAddress_outlet_info() {
        return address_outlet_info;
    }

    public void setAddress_outlet_info(String address_outlet_info) {
        this.address_outlet_info = address_outlet_info;
    }

    public OrderDeliveryStatusModel(String distributorId, String OrderStatus, String OrderDate, String OrderID, String Amount, String cart_count, String address, String contact_no_land, String contact_no_mob, String address_outlet_info) {
        this.distributorId = distributorId;
        this.OrderStatus = OrderStatus;

        this.OrderDate = OrderDate;
        this.OrderID = OrderID;
        this.Amount = Amount;
        this.cart_count = cart_count;

        this.address =address;
        this.contact_no_land = contact_no_land;
        this.contact_no_mob = contact_no_mob;
        this.address_outlet_info = address_outlet_info;
    }


}