package com.sapl.retailerorderingmsdpharma.MyDatabase;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.sapl.retailerorderingmsdpharma.activities.MyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Shashi on 02-Feb-18.
 */

public class TABLE_PCUSTOMER {
    public static String LOG_TAG = "TABLE_PCUSTOMER";
    public static String NAME = "PCustomer";

    public static String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + NAME + " (Id TEXT, " +
            "CustomerId TEXT, " +
            "UserId TEXT, " +
            "Party TEXT, " +
            "LicenceNo TEXT, " +
            "ONOFF TEXT, " +
            "ContactPerson TEXT, " +
            "GroupFirmId TEXT, " +
            "IsActive TEXT, " +
            "ERPCode TEXT, " +
            "Transfer TEXT, " +
            "DefaultDistributorId TEXT, " +
            "OnConsignmentSale TEXT, " +
            "CreationDate DATE, " +
            "VisitFrequency INTEGER, " +
            "LASTEDITDATE DATE, " +
            "RouteId INTEGER, " +
            "RouteName TEXT, " +
            "MDMID TEXT, " +
            "AREAID TEXT, " +
            "AREA TEXT, " +
            "AREAALIAS TEXT, " +
            "BRANCHID TEXT, " +
            "BRANCH TEXT, " +
            "BRANCHALIAS TEXT, " +
            "CUSTOMERCLASSID TEXT, " +
            "CUSTOMERCLASS TEXT, " +
            "CUSTOMERCLASSALIAS TEXT, " +
            "CUSTOMERCLASS2ID TEXT, " +
            "CUSTOMERCLASS2 TEXT, " +
            "CUSTOMERCLASS2ALIAS TEXT, " +
            "CUSTOMERGROUPID TEXT, " +
            "CUSTOMERGROUP TEXT, " +
            "CUSTOMERGROUPALIAS TEXT, " +
            "CUSTOMERSEGMENTID TEXT, " +
            "CUSTOMERSEGMENT TEXT, " +
            "CUSTOMERSEGMENTALIAS TEXT, " +
            "CUSTOMERSUBSEGMENTID TEXT, " +
            "CUSTOMERSUBSEGMENT TEXT, " +
            "CUSTOMERSUBSEGMENTALIAS TEXT, " +
            "LICENCETYPEID TEXT, " +
            "LICENCETYPE TEXT, " +
            "LICENCETYPEALIAS TEXT, " +
            "OCTROIZONEID TEXT, " +
            "OCTROIZONE TEXT, " +
            "OCTROIZONEALIAS TEXT, " +
            "OCTROIZONESEQUENCE TEXT, " +
            "OCTROIZONEALIASSEQUENCE TEXT, " +
            "CUSTOMERCLASSSEQUENCE TEXT, " +
            "CUSTOMERCLASSALIASSEQUENCE TEXT, " +
            "FIRMNAME TEXT, " +
            "PARTYHISTORY TEXT);";


    public static void insert_bulk_PCustomer(JSONArray jsonArray) {
        try {

            MyApplication.logi(LOG_TAG, "in insert_bulk_PCustomer->");
            SQLiteDatabase db = MyApplication.db.getWritableDatabase();
            db.beginTransaction();

            String insert_sql = "insert into " + NAME + " ( "
                    + " Id,CustomerId,UserId,Party,LicenceNo,ONOFF,ContactPerson,GroupFirmId,IsActive,ERPCode,Transfer," +
                    "DefaultDistributorId,OnConsignmentSale,CreationDate,VisitFrequency,LASTEDITDATE,RouteId,RouteName," +
                    "MDMID,AREAID,AREA,AREAALIAS,BRANCHID,BRANCH,BRANCHALIAS,CUSTOMERCLASSID,CUSTOMERCLASS,CUSTOMERCLASSALIAS," +
                    "CUSTOMERCLASS2ID,CUSTOMERCLASS2,CUSTOMERCLASS2ALIAS,CUSTOMERGROUPID,CUSTOMERGROUP,CUSTOMERGROUPALIAS," +
                    "CUSTOMERSEGMENTID,CUSTOMERSEGMENT,CUSTOMERSEGMENTALIAS,CUSTOMERSUBSEGMENTID,CUSTOMERSUBSEGMENT," +
                    "CUSTOMERSUBSEGMENTALIAS,LICENCETYPEID,LICENCETYPE,LICENCETYPEALIAS,OCTROIZONEID,OCTROIZONE,OCTROIZONEALIAS," +
                    "OCTROIZONESEQUENCE,OCTROIZONEALIASSEQUENCE,CUSTOMERCLASSSEQUENCE,CUSTOMERCLASSALIASSEQUENCE,FIRMNAME," +
                    "PARTYHISTORY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            MyApplication.logi(LOG_TAG, "insert_sql" + insert_sql);
            SQLiteStatement statement = db.compileStatement(insert_sql);
            JSONObject jsonObject = null;
            int count_array = jsonArray.length();
            MyApplication.logi(LOG_TAG, "COUNT ISS->" + count_array);

            for (int i = 0; i < count_array; i++) {


                jsonObject = jsonArray.getJSONObject(i);
                statement.clearBindings();
                statement.bindString(1, jsonObject.getString("Id"));
                statement.bindString(2, jsonObject.getString("CustomerId"));
                statement.bindString(3, jsonObject.getString("UserId"));
                statement.bindString(4, jsonObject.getString("Party"));
                statement.bindString(5, jsonObject.getString("LicenceNo"));

                statement.bindString(6, jsonObject.getString("ONOFF"));
                statement.bindString(7, jsonObject.getString("ContactPerson"));
                statement.bindString(8, jsonObject.getString("GroupFirmId"));
                statement.bindString(9, jsonObject.getString("IsActive"));
                statement.bindString(10, jsonObject.getString("ERPCode"));

                statement.bindString(11, jsonObject.getString("Transfer"));
                statement.bindString(12, jsonObject.getString("DefaultDistributorId"));
                statement.bindString(13, jsonObject.getString("OnConsignmentSale"));
                statement.bindString(14, jsonObject.getString("CreationDate"));
                statement.bindString(15, jsonObject.getString("VisitFrequency"));

                statement.bindString(16, jsonObject.getString("LASTEDITDATE"));
                statement.bindString(17, jsonObject.getString("RouteId"));
                statement.bindString(18, jsonObject.getString("RouteName"));
                statement.bindString(19, jsonObject.getString("MDMID"));
                statement.bindString(20, jsonObject.getString("AREAID"));

                statement.bindString(21, jsonObject.getString("AREA"));
                statement.bindString(22, jsonObject.getString("AREAALIAS"));
                statement.bindString(23, jsonObject.getString("BRANCHID"));
                statement.bindString(24, jsonObject.getString("BRANCH"));
                statement.bindString(25, jsonObject.getString("BRANCHALIAS"));

                statement.bindString(26, jsonObject.getString("CUSTOMERCLASSID"));
                statement.bindString(27, jsonObject.getString("CUSTOMERCLASS"));
                statement.bindString(28, jsonObject.getString("CUSTOMERCLASSALIAS"));
                statement.bindString(29, jsonObject.getString("CUSTOMERCLASS2ID"));
                statement.bindString(30, jsonObject.getString("CUSTOMERCLASS2"));

                statement.bindString(31, jsonObject.getString("CUSTOMERCLASS2ALIAS"));
                statement.bindString(32, jsonObject.getString("CUSTOMERGROUPID"));
                statement.bindString(33, jsonObject.getString("CUSTOMERGROUP"));
                statement.bindString(34, jsonObject.getString("CUSTOMERGROUPALIAS"));
                statement.bindString(35, jsonObject.getString("CUSTOMERSEGMENTID"));

                statement.bindString(36, jsonObject.getString("CUSTOMERSEGMENT"));
                statement.bindString(37, jsonObject.getString("CUSTOMERSEGMENTALIAS"));
                statement.bindString(38, jsonObject.getString("CUSTOMERSUBSEGMENTID"));
                statement.bindString(39, jsonObject.getString("CUSTOMERSUBSEGMENT"));
                statement.bindString(40, jsonObject.getString("CUSTOMERSUBSEGMENTALIAS"));

                statement.bindString(41, jsonObject.getString("LICENCETYPEID"));
                statement.bindString(42, jsonObject.getString("LICENCETYPE"));
                statement.bindString(43, jsonObject.getString("LICENCETYPEALIAS"));
                statement.bindString(44, jsonObject.getString("OCTROIZONEID"));
                statement.bindString(45, jsonObject.getString("OCTROIZONE"));

                statement.bindString(46, jsonObject.getString("OCTROIZONEALIAS"));
                statement.bindString(47, jsonObject.getString("OCTROIZONESEQUENCE"));
                statement.bindString(48, jsonObject.getString("OCTROIZONEALIASSEQUENCE"));
                statement.bindString(49, jsonObject.getString("CUSTOMERCLASSSEQUENCE"));
                statement.bindString(50, jsonObject.getString("CUSTOMERCLASSALIASSEQUENCE"));

                statement.bindString(51, jsonObject.getString("FIRMNAME"));
                statement.bindString(52, jsonObject.getString("PARTYHISTORY"));


                statement.execute();
            }
            MyApplication.logi(LOG_TAG, "EndTime->");
            db.setTransactionSuccessful();
            db.endTransaction();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static String getRetailerNamenew() {
        MyApplication.logi(LOG_TAG, "in getDistributorId");
        int count;
        String resp = "";
        MyApplication.logi(LOG_TAG, "in getDistributorId()");
        SQLiteDatabase db = MyApplication.db.getWritableDatabase();
        String query = "SELECT Party from PCustomer";
        MyApplication.logi("mrunal","In getDistributorId query :" + query);
        Cursor c = db.rawQuery(query, null);
        count = c.getCount();
        MyApplication.logi(LOG_TAG, "COUNT OF getDistributorId IS-->" + count);
        if (count > 0) {
            c.moveToFirst();
            do {
                resp = c.getString(c.getColumnIndexOrThrow("Party"));
                MyApplication.set_session(MyApplication.SESSION_RETAILER_NAME,resp);
                MyApplication.logi(LOG_TAG, "vgetDistributorId RESPPPPP-->" + resp);
            } while (c.moveToNext());
        }

        return resp;

    }

    public static String getCustId() {
        MyApplication.logi(LOG_TAG, "IN CUCST ID");
        String custId = "";
        SQLiteDatabase db = MyApplication.db.getReadableDatabase();
        String query = "SELECT  cast(CustomerId as int) as CustomerId  FROM " + NAME;
        MyApplication.logi(LOG_TAG,"querrryyyy--->"+query);
        Cursor c = db.rawQuery(query, null);
        if (c.getCount() > 0) {
            c.moveToFirst();
           custId = c.getString(c.getColumnIndexOrThrow("CustomerId"));

        }
        MyApplication.logi(LOG_TAG, "IN CUCST ID custId--"+custId);
        return custId;
    }

    public static void deleteTableData() {
        MyApplication.logi(LOG_TAG, "in delete pcustomer");
        SQLiteDatabase db = MyApplication.db.getWritableDatabase();


        db.delete(NAME, null, null);


    }

    public static void delete_table() {

        SQLiteDatabase db = MyApplication.db.getWritableDatabase();
        String str1 = "select * from " + NAME;
        Cursor c = db.rawQuery(str1, null);
        int count= c.getCount();
        MyApplication.logi(LOG_TAG, "Count"+count);
        int numRows = db.delete(NAME, null, null);
        MyApplication.logi(LOG_TAG, "In delete_tbl_timetable_sync");

        MyApplication.logi(LOG_TAG, "DeletedRows:->" + numRows);
    }
}