package com.sapl.retailerorderingmsdpharma.models;

public class OrderDetailModel {
   String orderId;
    String ItemId;
    String item_name;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getItemId() {
        return ItemId;
    }

    public void setItemId(String itemId) {
        ItemId = itemId;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getLargeUnitQty() {
        return LargeUnitQty;
    }

    public void setLargeUnitQty(String largeUnitQty) {
        LargeUnitQty = largeUnitQty;
    }

    public String getSmallUnitQty() {
        return SmallUnitQty;
    }

    public void setSmallUnitQty(String smallUnitQty) {
        SmallUnitQty = smallUnitQty;
    }

    public String getRate() {
        return Rate;
    }

    public void setRate(String rate) {
        Rate = rate;
    }

    public String getDiscounted_price_cases() {
        return discounted_price_cases;
    }

    public void setDiscounted_price_cases(String discounted_price_cases) {
        this.discounted_price_cases = discounted_price_cases;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getFreeLargeUnitQty() {
        return FreeLargeUnitQty;
    }

    public void setFreeLargeUnitQty(String freeLargeUnitQty) {
        FreeLargeUnitQty = freeLargeUnitQty;
    }

    public String getFreeSmallUnitQty() {
        return FreeSmallUnitQty;
    }

    public void setFreeSmallUnitQty(String freeSmallUnitQty) {
        FreeSmallUnitQty = freeSmallUnitQty;
    }

    String LargeUnitQty;
    String SmallUnitQty;
    String Rate;
    String discounted_price_cases;
    String Amount;

    public OrderDetailModel(String orderId, String itemId, String item_name, String largeUnitQty, String smallUnitQty, String rate, String discounted_price_cases, String amount, String freeLargeUnitQty, String freeSmallUnitQty) {
        this.orderId = orderId;
        ItemId = itemId;
        this.item_name = item_name;
        LargeUnitQty = largeUnitQty;
        SmallUnitQty = smallUnitQty;
        Rate = rate;
        this.discounted_price_cases = discounted_price_cases;
        Amount = amount;
        FreeLargeUnitQty = freeLargeUnitQty;
        FreeSmallUnitQty = freeSmallUnitQty;
    }

    String FreeLargeUnitQty;
    String FreeSmallUnitQty;
}
