package com.sapl.retailerorderingmsdpharma.models;


/**
 * Created by MRUNAL on 07-Feb-18.
 */

public class DashBoardElementModel {
    String title ="";
    String imageName="";

    public DashBoardElementModel(String title, String imageName) {
        this.title = title;
        this.imageName = imageName;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    @Override
    public String toString() {
        return "DashBoardElementModel{" +
                "title='" + title + '\'' +
                ", imageName='" + imageName + '\'' +
                '}';
    }

}
