package com.sapl.retailerorderingmsdpharma.MyDatabase;

/**
 * Created by Mrunal on 12/02/2018.
 */

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

public class TABLE_TEMP_ORDER_STATUS {
    public static String LOG_TAG = "TABLE_TEMP_ORDER_STATUS";
    public static String NAME = "TempOrderStatus";


    public static String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
            + NAME + "(ID TEXT, RetailerOrderMasterID TEXT,StatusID INTEGER, StatusDateTime DATE, Remarks TEXT)";

    public static void insert_bulk_OrderStatus_temp(JSONArray jsonArray) {
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
                    statement.bindString(2, jsonObject.getString("StatusDateTime"));
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

