package com.sapl.retailerorderingmsdpharma.models;

import java.sql.Blob;

/**
 * Created by MRUNAL on 07-Feb-18.
 */

public class SubItemDataModel {
    String offer;
    float bottle_price;
    float case_value;

    String sub_item_name;
    String offer_tagline;
    String discount_type;


    String total_price;  ///not used any where
    String no_of_free_case;
    int item_id;
    String no_of_free_bottle;
    float item_total;
    String cases;
    String bottles;
    byte[] product_image_path;



    public float getItem_total() {
        return item_total;
    }

    public void setItem_total(float item_total) {
        this.item_total = item_total;
    }


    public String getSub_item_name() {
        return sub_item_name;
    }

    public void setSub_item_name(String sub_item_name) {
        this.sub_item_name = sub_item_name;
    }

    public byte[] getProduct_image_path() {
        return product_image_path;
    }

    public void setProduct_image_path(byte[] product_image_path) {
        this.product_image_path = product_image_path;
    }


    //no_of_case, no_of_bottle;

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }


    public float getCase_value() {
        return case_value;
    }

    public void setCase_value(float case_value) {
        this.case_value = case_value;
    }




    public String getDiscount_type() {
        return discount_type;
    }

    public void setDiscount_type(String discount_type) {
        this.discount_type = discount_type;
    }


    @Override
    public String toString() {
        return "SubItemDataModel{" +
                "offer='" + offer + '\'' +
                ", bottle_price=" + bottle_price +
                ", case_value=" + case_value +
                ", sub_item_name='" + sub_item_name + '\'' +
                ", offer_tagline='" + offer_tagline + '\'' +
                ", discount_type='" + discount_type + '\'' +
                ", total_price='" + total_price + '\'' +
                ", no_of_free_case='" + no_of_free_case + '\'' +
                ", item_id=" + item_id +
                ", no_of_free_bottle='" + no_of_free_bottle + '\'' +
                ", item_total=" + item_total +
                ", cases='" + cases + '\'' +
                ", bottles='" + bottles + '\'' +
                ", product_image_path='" + product_image_path + '\'' +
                '}';
    }

    public String getCases() {
        return cases;
    }

    public void setCases(String cases) {
        this.cases = cases;
    }

    public String getBottles() {
        return bottles;
    }

    public void setBottles(String bottles) {
        this.bottles = bottles;
    }

    public SubItemDataModel(String offer, String discount_type, float bottle_price, float case_value,
                            int item_id, String sub_item_name, String offer_tagline,
                            String total_price, String no_of_free_case, String no_of_free_bottle,
                            byte[] product_image_path, float item_total, String cases, String bottles) {
        this.offer = offer;
        this.bottle_price = bottle_price;
        this.case_value = case_value;
        this.discount_type = discount_type;
        this.item_id = item_id;



        this.sub_item_name = sub_item_name;
        this.offer_tagline = offer_tagline;
        this.total_price = total_price;

        this.no_of_free_case = no_of_free_case;
        this.no_of_free_bottle = no_of_free_bottle;
        this.product_image_path = product_image_path;
        this.cases = cases;
        this.bottles = bottles;
    }


    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    public float getBottle_price() {
        return bottle_price;
    }

    public void setBottle_price(float bottle_price) {
        this.bottle_price = bottle_price;
    }


    public String getOffer_tagline() {
        return offer_tagline;
    }

    public void setOffer_tagline(String offer_tagline) {
        this.offer_tagline = offer_tagline;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getNo_of_free_case() {
        return no_of_free_case;
    }

    public void setNo_of_free_case(String no_of_free_case) {
        this.no_of_free_case = no_of_free_case;
    }

    public String getNo_of_free_bottle() {
        return no_of_free_bottle;
    }

    public void setNo_of_free_bottle(String no_of_free_bottle) {
        this.no_of_free_bottle = no_of_free_bottle;
    }


}
