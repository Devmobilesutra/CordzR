package com.sapl.retailerorderingmsdpharma.models;

import java.util.Arrays;

/**
 * Created by MRUNAL on 07-Feb-18.
 */
public class OrderReviewModel {

    String product_name;
    byte[] product_img_path;
    String case_no;
    String boittle_no;

   String free_bottle_no;
    String price_of_product;
    String item_id;
    String free_case_no;
    float single_btl_price;
    float discounted_single_case_rate;
    String totalll_after_edit;

    String DiscountRate;

    public String getDiscountRate() {
        return DiscountRate;
    }

    public void setDiscountRate(String discountRate) {
        DiscountRate = discountRate;
    }

    public String getDiscountType() {
        return DiscountType;
    }

    public void setDiscountType(String discountType) {
        DiscountType = discountType;
    }

    String DiscountType;


    public float getDiscounted_single_case_rate() {
        return discounted_single_case_rate;
    }

    public void setDiscounted_single_case_rate(float discounted_single_case_rate) {
        this.discounted_single_case_rate = discounted_single_case_rate;
    }

    public float getSingle_btl_price() {
        return single_btl_price;
    }


    public void setSingle_btl_price(float single_btl_price) {
        this.single_btl_price = single_btl_price;
    }

    @Override
    public String toString() {
        return "OrderReviewModel{" +
                "product_name='" + product_name + '\'' +
                ", product_img_path=" + Arrays.toString(product_img_path) +
                ", case_no='" + case_no + '\'' +
                ", boittle_no='" + boittle_no + '\'' +
                ", free_bottle_no='" + free_bottle_no + '\'' +
                ", price_of_product='" + price_of_product + '\'' +
                ", item_id='" + item_id + '\'' +
                ", free_case_no='" + free_case_no + '\'' +
                ", single_btl_price=" + single_btl_price +
                ", discounted_single_case_rate=" + discounted_single_case_rate +
                ", totalll_after_edit='" + totalll_after_edit + '\'' +
                '}';
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public byte[] getProduct_img_path() {
        return product_img_path;
    }

    public void setProduct_img_path(byte[] product_img_path) {
        this.product_img_path = product_img_path;
    }

    public String getCase_no() {
        return case_no;
    }

    public void setCase_no(String case_no) {
        this.case_no = case_no;
    }

    public String getBoittle_no() {
        return boittle_no;
    }

    public void setBoittle_no(String boittle_no) {
        this.boittle_no = boittle_no;
    }

    public String getFree_case_no() {
        return free_case_no;
    }

    public void setFree_case_no(String free_case_no) {
        this.free_case_no = free_case_no;
    }

    public String getFree_bottle_no() {
        return free_bottle_no;
    }

    public void setFree_bottle_no(String free_bottle_no) {
        this.free_bottle_no = free_bottle_no;
    }

    public String getPrice_of_product() {
        return price_of_product;
    }

    public void setPrice_of_product(String price_of_product) {
        this.price_of_product = price_of_product;
    }


    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getTotalll_after_edit() {
        return totalll_after_edit;
    }

    public void setTotalll_after_edit(String totalll_after_edit) {
        this.totalll_after_edit = totalll_after_edit;
    }

    public OrderReviewModel(String item_id, String product_name, byte[] product_img_path, String case_no, String boittle_no, String free_case_no, String free_bottle_no, String price_of_product, float single_btl_price, float discounted_single_case_rate, String totalll_after_edit) {
        this.product_name = product_name;
        this.item_id = item_id;
        this.product_img_path = product_img_path;
        this.case_no = case_no;
        this.boittle_no = boittle_no;
        this.free_case_no = free_case_no;
        this.free_bottle_no = free_bottle_no;
        this.price_of_product = price_of_product;
        this.single_btl_price = single_btl_price;
        this.discounted_single_case_rate = discounted_single_case_rate;
        this.totalll_after_edit = totalll_after_edit;
    }

    public OrderReviewModel(String item_id, String product_name, byte[] product_img_path, String case_no, String boittle_no, String free_case_no, String free_bottle_no, String price_of_product, float single_btl_price, float discounted_single_case_rate, String totalll_after_edit,String DiscountRate,String DiscountType) {
        this.product_name = product_name;
        this.item_id = item_id;
        this.product_img_path = product_img_path;
        this.case_no = case_no;
        this.boittle_no = boittle_no;
        this.free_case_no = free_case_no;
        this.free_bottle_no = free_bottle_no;
        this.price_of_product = price_of_product;
        this.single_btl_price = single_btl_price;
        this.discounted_single_case_rate = discounted_single_case_rate;
        this.totalll_after_edit = totalll_after_edit;
        this.DiscountRate=DiscountRate;
        this.DiscountType=DiscountType;
    }
}
