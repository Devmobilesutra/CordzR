package com.sapl.retailerorderingmsdpharma.models;


/**
 * Created by MRUNAL on 07-Feb-18.
 */

public class DistributorModel {
    int dist_id;
    String name = "";
    String address = "";
    String contactNo = "";

    public String getContactNo_mob() {
        return contactNo_mob;
    }

    public void setContactNo_mob(String contactNo_mob) {
        this.contactNo_mob = contactNo_mob;
    }

    String rating = "";
    String distImagePath = "";
    String info = "";
    String contactNo_mob = "";
    String isActive = "";

    public int getDist_id() {
        return dist_id;
    }

    public void setDist_id(int dist_id) {
        this.dist_id = dist_id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public DistributorModel(int dist_id, String name, String address, String contactNo, String rating, String info, String distImagePath, String contactNo_mob, String isActive) {
        this.dist_id = dist_id;
        this.name = name;
        this.address = address;
        this.contactNo = contactNo;
        this.rating = rating;
        this.info = info;
        this.distImagePath = distImagePath;
        this.contactNo_mob = contactNo_mob;
        this.isActive = isActive;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "DistributorModel{" +
                "dist_id=" + dist_id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", contactNo='" + contactNo + '\'' +
                ", rating='" + rating + '\'' +
                ", distImagePath='" + distImagePath + '\'' +
                ", info='" + info + '\'' +
                ", contactNo_mob='" + contactNo_mob + '\'' +
                ", isActive='" + isActive + '\'' +
                '}';
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDistImagePath() {
        return distImagePath;
    }

    public void setDistImagePath(String distImagePath) {
        this.distImagePath = distImagePath;
    }


}
