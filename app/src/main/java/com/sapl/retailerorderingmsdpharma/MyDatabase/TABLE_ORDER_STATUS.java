package com.sapl.retailerorderingmsdpharma.MyDatabase;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.sapl.retailerorderingmsdpharma.activities.MyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by MRUNAL on 08/02/2018.
 */

public class TABLE_ORDER_STATUS {
    public static String LOG_TAG = "TABLE_ORDER_STATUS";
    public static String NAME = "OrderStatus";


    public static String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
            + NAME + "(OrderId TEXT, StatusDateTime DATE,StatusID INTEGER, Remarks TEXT)";


    public static void insert_bulk_OrderStatus(JSONArray jsonArray) {
        try {
            MyApplication.logi(LOG_TAG, "in insert_bulk_OrderStatus->");
            SQLiteDatabase db = MyApplication.db.getWritableDatabase();
            db.beginTransaction();


            String insert_sql = "insert into " + NAME + " (" + "OrderId,StatusDateTime,StatusID,Remarks) VALUES(?,?,?,?)";
            MyApplication.logi(LOG_TAG, "insert_sql insert_bulk_OrderStatus" + insert_sql);
            SQLiteStatement statement = db.compileStatement(insert_sql);
            JSONObject jsonObject = null;
            int count_array = jsonArray.length();
            MyApplication.logi(LOG_TAG, "COUNT ISS insert_bulk_OrderStatus->" + count_array);
            try {
                for (int i = 0; i < count_array; i++) {


                    jsonObject = jsonArray.getJSONObject(i);

                    statement.clearBindings();
                    statement.bindString(1, jsonObject.getString("OrderId"));
                    statement.bindString(2, jsonObject.getString("datetime"));
                    statement.bindString(3, jsonObject.getString("StatusID"));
                    statement.bindString(4, jsonObject.getString("Remarks"));

                    statement.execute();
                }
                MyApplication.logi(LOG_TAG, "EndTime insert_bulk_OrderStatus->");
                db.setTransactionSuccessful();
                db.endTransaction();
            } catch (JSONException e) {
                MyApplication.logi(LOG_TAG, "JSONException insert_bulk_OrderStatus--->" + e.getMessage());

            }
        } catch (Exception e) {
            MyApplication.logi(LOG_TAG, " insert_bulk_OrderStatus exception--->" + e.getMessage());
        }
    }


    public static void deleteOrderStatusData() {
        MyApplication.logi(LOG_TAG, "in deleteOrderStatusData");
        SQLiteDatabase db = MyApplication.db.getWritableDatabase();


        db.delete(NAME, null, null);


    }


    public static int getPendingCount() {
        MyApplication.logi(LOG_TAG, "IN getdelivery_pending_no");
        int count1 = 0;
        SQLiteDatabase db = MyApplication.db.getReadableDatabase();


        String query = "SELECT * from OrderStatus WHERE StatusID = 1";
        MyApplication.log("In getdelivery_pending_no query :" + query);
        Cursor c = db.rawQuery(query, null);
        count1 = c.getCount();
        int pending = count1 - getDeliveryStatusCount() - getRejectedCount();
        MyApplication.logi(LOG_TAG, "abc*******" + pending);
        MyApplication.logi(LOG_TAG, "COUNT OF FILTER getdelivery_pending_no>" + count1);

        return pending;
        //return count1;
    }


    public static int getDeliveryStatusCount() {
        MyApplication.logi(LOG_TAG, "IN getDeliveryStatusCount");
        int count1 = 0;
        SQLiteDatabase db = MyApplication.db.getReadableDatabase();


        String query = "SELECT * from OrderStatus WHERE StatusID = 2";
        MyApplication.log("In getDeliveryStatusCount query :" + query);
        Cursor c = db.rawQuery(query, null);
        count1 = c.getCount();
        MyApplication.logi(LOG_TAG, "COUNT OF FILTER getDeliveryStatusCount>" + count1);


        return count1;
    }


    public static int getRejectedCount() {
        MyApplication.logi(LOG_TAG, "IN getRejectedCount");
        int count1 = 0;
        SQLiteDatabase db = MyApplication.db.getReadableDatabase();


        String query = "SELECT * from OrderStatus WHERE StatusID = 3";
        MyApplication.log("In getRejectedCount query :" + query);
        Cursor c = db.rawQuery(query, null);
        count1 = c.getCount();

        return count1;
    }


    public static int insertDataBeforeSync(String orderId,
                                           String orderDate,
                                           String orderStatus,
                                           String orderRemarks
    ) {

        MyApplication.logi(LOG_TAG, "insertDataBeforeSync STATUS");
        SQLiteDatabase db = MyApplication.db.getReadableDatabase();
        ContentValues initialValues1 = new ContentValues();


        initialValues1.put("OrderId", orderId);
        initialValues1.put("StatusDateTime", orderDate);
        initialValues1.put("StatusID", orderStatus);
        initialValues1.put("Remarks", orderRemarks);


        MyApplication.logi(LOG_TAG, "insertDataBeforeSync->" + initialValues1);


        long ret = db.insert(NAME, null, initialValues1);
        MyApplication.logi(LOG_TAG, "in insertDataBeforeSync ret->" + ret);
        if (ret > 0) {
            MyApplication.logi(LOG_TAG, "Successfull in insertDataBeforeSync ret ->" + ret);
            return 0;
        } else {
            MyApplication.logi(LOG_TAG, "Not successfiull in insertDataBeforeSync ret->" + ret);
            return 1;

        }

    }




    public static void insert_bulk_OrderStatus_new(JSONArray jsonArray) {
        try {
            MyApplication.logi(LOG_TAG, "in insert_bulk_OrderStatus_new->");
            SQLiteDatabase db = MyApplication.db.getWritableDatabase();
            db.beginTransaction();


            String insert_sql = "insert into " + NAME + " (" + "OrderId,StatusDateTime,StatusID,Remarks) VALUES(?,?,?,?)";
            MyApplication.logi(LOG_TAG, "insert_sql insert_bulk_OrderStatus_new" + insert_sql);
            SQLiteStatement statement = db.compileStatement(insert_sql);
                JSONObject jsonObject = null;
            int count_array = jsonArray.length();
            MyApplication.logi(LOG_TAG, "COUNT ISS insert_bulk_OrderStatus_new->" + count_array);
            try {
                for (int i = 0; i < count_array; i++) {


                    jsonObject = jsonArray.getJSONObject(i);

                    statement.clearBindings();
                    statement.bindString(1, jsonObject.getString("AppOrderId"));
                    statement.bindString(2, jsonObject.getString("StatusDateTime"));
                    statement.bindString(3, jsonObject.getString("StatusId"));
                    statement.bindString(4, jsonObject.getString("Remarks"));

                    statement.execute();
                }
                MyApplication.logi(LOG_TAG, "EndTime insert_bulk_OrderStatus_new->");
                db.setTransactionSuccessful();
                db.endTransaction();
            } catch (JSONException e) {
                MyApplication.logi(LOG_TAG, "JSONException insert_bulk_OrderStatus_new--->" + e.getMessage());

            }
        } catch (Exception e) {
            MyApplication.logi(LOG_TAG, " insert_bulk_OrderStatus_new exception--->" + e.getMessage());
        }
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
