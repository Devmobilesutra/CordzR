package com.sapl.retailerorderingmsdpharma.models;


/**
 * Created by MRUNAL on 07-Feb-18.
 */

public class SelectionDataModel {

    String selectionImg;
    String selectionName;
    String selectionDescription;
    String selectionOffer;

    public SelectionDataModel(String selectionImg, String selectionName, String selectionDescription, String selectionOffer) {
        this.selectionImg = selectionImg;
        this.selectionName = selectionName;
        this.selectionDescription = selectionDescription;
        this.selectionOffer = selectionOffer;
    }

    public String getSelectionImg() {
        return selectionImg;
    }

    public void setSelectionImg(String selectionImg) {
        this.selectionImg = selectionImg;
    }

    public String getSelectionName() {
        return selectionName;
    }

    public void setSelectionName(String selectionName) {
        this.selectionName = selectionName;
    }

    public String getSelectionDescription() {
        return selectionDescription;
    }

    public void setSelectionDescription(String selectionDescription) {
        this.selectionDescription = selectionDescription;
    }

    public String getSelectionOffer() {
        return selectionOffer;
    }

    public void setSelectionOffer(String selectionOffer) {
        this.selectionOffer = selectionOffer;
    }

    @Override
    public String toString() {
        return "SelectionDataModel{" +
                "selectionImg='" + selectionImg + '\'' +
                ", selectionName='" + selectionName + '\'' +
                ", selectionDescription='" + selectionDescription + '\'' +
                ", selectionOffer='" + selectionOffer + '\'' +
                '}';
    }

}
