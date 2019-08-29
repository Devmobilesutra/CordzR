

package com.sapl.retailerorderingmsdpharma.models;

/**
 * Created by Mrunal on 30-Jan-18.
 */

public class DivisionModel {


    String division;
    int division_id;

    public DivisionModel(String division, int divisionid) {
        this.division = division;
        this.division_id = divisionid;

    }

    public String getDivision() {
        return division;
    }

    @Override
    public String toString() {
        return "SelectionDataModel{" +
                "division='" + division + '\'' +
                ", division_id='" + division_id + '\'' +
                '}';
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public int getDivision_id() {
        return division_id;
    }

    public void setDivision_id(int division_id) {
        this.division_id = division_id;
    }
}
