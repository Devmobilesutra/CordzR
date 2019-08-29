package com.sapl.retailerorderingmsdpharma.MyDatabase;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.sapl.retailerorderingmsdpharma.activities.MyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Mrunal on 02/02/2018.
 */

public class TABLE_PRICELIST_DETAILS {


    public static String NAME = "PriceListDetails";
    private static final String LOG_TAG = "TABLE_PriceList_Details";

    public static String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
            + NAME + "(Id INTEGER, PriceListID SMALLINT, ItemID INTEGER, DiscoutType TEXT, DiscountRate INTEGER, " +
            "SalesRate INTEGER, FromDate DATETIME, ToDate DATETIME, Remarks TEXT, CreatedDate DATETIME, CreatedBy DATETIME, UpdatedDate DATETIME, UpdatedBy DATETIME)";





    public static void insert_bulk_PriceListDetails(JSONArray jsonArray) {
        try {
            MyApplication.logi(LOG_TAG, "in insert_bulk_PriceListDetails->");

            SQLiteDatabase db = MyApplication.db.getWritableDatabase();
            db.beginTransaction();


            String insert_sql = "insert into " + NAME + " (" + "ID,PriceListID,ItemID,DiscoutType,DiscountRate,SalesRate,FromDate,ToDate,Remarks,CreatedDate,CreatedBy,UpdatedDate,UpdatedBy) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
            MyApplication.logi(LOG_TAG, "insert_sql" + insert_sql);
            SQLiteStatement statement = db.compileStatement(insert_sql);
            JSONObject jsonObject = null;
            int count_array = jsonArray.length();
            MyApplication.logi(LOG_TAG, "COUNT ISS->" + count_array);

            for (int i = 0; i < count_array; i++) {


                jsonObject = jsonArray.getJSONObject(i);

                statement.clearBindings();


                statement.bindString(1, jsonObject.getString("ID"));
                statement.bindString(2, jsonObject.getString("DistributorId"));
                statement.bindString(3, jsonObject.getString("ItemID"));
                statement.bindString(4, jsonObject.getString("DiscoutType"));
                statement.bindString(5, jsonObject.getString("DiscountRate"));
                MyApplication.logi(LOG_TAG, "Rajani2" + jsonObject.getString("DiscountRate"));


                statement.bindString(6, jsonObject.getString("SalesRate"));
                statement.bindString(7, jsonObject.getString("FromDate"));
                statement.bindString(8, jsonObject.getString("ToDate"));
                statement.bindString(9, jsonObject.getString("Remarks"));
                statement.bindString(10, jsonObject.getString("CreatedDate"));

                statement.bindString(11, jsonObject.getString("CreatedBy"));
                statement.bindString(12, jsonObject.getString("UpdatedDate"));
                statement.bindString(13, jsonObject.getString("UpdatedBy"));


                statement.execute();
            }
            MyApplication.logi(LOG_TAG, "EndTime->");
            MyApplication.logi(LOG_TAG, "Rajani3" + jsonObject.getString("DiscountRate"));

            db.setTransactionSuccessful();
            db.endTransaction();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    public static void deleteTableData() {
        MyApplication.logi(LOG_TAG, "in delete price list DETAILS");
        SQLiteDatabase db = MyApplication.db.getWritableDatabase();


        db.delete(NAME, null, null);


    }
    public static String getPriceListIDOfDistributor(String dist_id) {

        MyApplication.logi(LOG_TAG, "in getPriceListOfDistributor");
        int count;
        String resp = "";
        MyApplication.logi(LOG_TAG, "in getPriceListOfDistributor()" + dist_id);
        SQLiteDatabase db = MyApplication.db.getWritableDatabase();
        //String query = "SELECT * from "+NAME+" where "+"PriceListID" +" =" + dist_id+"";
        String query = "SELECT * from PriceListDetails where PriceListID =" + dist_id;
        MyApplication.logi("mrunal", "In getPriceListOfDistributor query :" + query);
        Cursor c = db.rawQuery(query, null);
        count = c.getCount();
        MyApplication.logi(LOG_TAG, "COUNT OF getPriceListOfDistributor IS-->" + count);
        if (count > 0) {
            c.moveToFirst();
            do {
                resp = c.getString(c.getColumnIndexOrThrow("PriceListID"));
                MyApplication.logi(LOG_TAG, "RESPPPPP-->" + resp);
            } while (c.moveToNext());
        }

        return resp;

    }


    public static int getSalesRate(String item_id,String dist_id) {

        MyApplication.logi(LOG_TAG, "in getSalesRate");
        int count;
        int resp = 0;
        MyApplication.logi(LOG_TAG, "in getSalesRate()" + item_id);
        SQLiteDatabase db = MyApplication.db.getWritableDatabase();
        //String query = "SELECT * from "+NAME+" where "+"PriceListID" +" =" + dist_id+"";
       // String query = "SELECT DISTINCT SalesRate from PriceListDetails where ItemID =" + item_id;
       // String query =   "select SalesRate from PriceListDetails where ItemID=item_id and PriceListID=dist_id";

        String query = "SELECT DISTINCT SalesRate from PriceListDetails where ItemID ='" + item_id + "'  AND PriceListID ='" + dist_id + "'";
        MyApplication.logi("", "In getSalesRate query :" + query);
        Cursor c = db.rawQuery(query, null);
        count = c.getCount();
        MyApplication.logi(LOG_TAG, "COUNT OF getSalesRate IS-->" + count);
        if (count > 0) {
            c.moveToFirst();
            do {
                try {
                    resp = Integer.parseInt(c.getString(c.getColumnIndexOrThrow("SalesRate")));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                MyApplication.logi(LOG_TAG, "RESPPPPP-->" + resp);
            } while (c.moveToNext());
        }

        return resp;

    }


    public static String getDiscount_type(String item_id,String dist_id) {

        MyApplication.logi(LOG_TAG, "in getDiscount_type");
        int count;
        String resp = "";
        MyApplication.logi(LOG_TAG, "in getDiscount_type()" + item_id);
        SQLiteDatabase db = MyApplication.db.getWritableDatabase();
        //String query = "SELECT * from "+NAME+" where "+"PriceListID" +" =" + dist_id+"";
        // String query = "SELECT DISTINCT SalesRate from PriceListDetails where ItemID =" + item_id;
        // String query =   "select SalesRate from PriceListDetails where ItemID=item_id and PriceListID=dist_id";

        String query = "SELECT DiscoutType from PriceListDetails where ItemID ='" + item_id + "'  AND PriceListID ='" + dist_id + "'";
        MyApplication.logi("", "In getDiscount_type query :" + query);
        Cursor c = db.rawQuery(query, null);
        count = c.getCount();
        MyApplication.logi(LOG_TAG, "COUNT OF getDiscount_type IS-->" + count);
        if (count > 0) {
            c.moveToFirst();
            do {
                try {
                    resp = c.getString(c.getColumnIndexOrThrow("DiscoutType"));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                MyApplication.logi(LOG_TAG, "getDiscount_typeRESPPPPP-->" + resp);
            } while (c.moveToNext());
        }

        return resp;


    }



    public static int getDiscount_rate(String item_id,String dist_id) {

        MyApplication.logi(LOG_TAG, "in DiscountRate");
        int count;
        int resp = 0;
        MyApplication.logi(LOG_TAG, "in DiscountRate()" + item_id);
        SQLiteDatabase db = MyApplication.db.getWritableDatabase();
        //String query = "SELECT * from "+NAME+" where "+"PriceListID" +" =" + dist_id+"";
        // String query = "SELECT DISTINCT SalesRate from PriceListDetails where ItemID =" + item_id;
        // String query =   "select SalesRate from PriceListDetails where ItemID=item_id and PriceListID=dist_id";

        String query = "SELECT DISTINCT DiscountRate from PriceListDetails where ItemID ='" + item_id + "'  AND PriceListID ='" + dist_id + "'";
        MyApplication.logi("", "In DiscountRate query :" + query);
        Cursor c = db.rawQuery(query, null);
        count = c.getCount();
        MyApplication.logi(LOG_TAG, "COUNT OF DiscountRate IS-->" + count);
        if (count > 0) {
            c.moveToFirst();
            do {
                try {
                    resp = Integer.parseInt(c.getString(c.getColumnIndexOrThrow("DiscountRate")));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                MyApplication.logi(LOG_TAG, "getDiscount_rateRESPPPPP-->" + resp);
            } while (c.moveToNext());
        }

        return resp;


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
