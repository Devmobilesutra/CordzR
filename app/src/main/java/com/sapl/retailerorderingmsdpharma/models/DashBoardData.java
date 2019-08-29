package com.sapl.retailerorderingmsdpharma.models;

/**
 * Created by Sony on 07/02/2018.
 */

public class DashBoardData {

    public String label_name="";

    public String getMenu_key() {
        return menu_key;
    }

    public void setMenu_key(String menu_key) {
        this.menu_key = menu_key;
    }

    public String is_active="";
    public String menu_key="";


    public DashBoardData(String label_name, String is_active, String menu_key) {
        this.label_name = label_name;

        this.is_active = is_active;
        this.menu_key = menu_key;
    }

    public String getLabel_name() {
        return label_name;
    }

    public void setLabel_name(String label_name) {
        this.label_name = label_name;
    }

    public String getIs_active() {
        return is_active;
    }

    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }

    @Override
    public String toString() {
        return "DashBoardData{" +
                "label_name='" + label_name + '\'' +
                ", is_active='" + is_active + '\'' +
                ", menu_key='" + menu_key + '\'' +
                '}';
    }
}
