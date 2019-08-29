package com.sapl.retailerorderingmsdpharma.MyDatabase;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.sapl.retailerorderingmsdpharma.activities.MyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Sony on 05/03/2018.
 */

public class TABLE_RETAILER_IMAGES {

    public static String NAME = "RetailerImages";
    private static final String LOG_TAG = "TABLE_RETAILER_IMAGES";

    public static String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
            + NAME + "(ImageId TEXT,ImageDetails BLOB)";


    public static void insertImageData(JSONArray jsonArray) {
        try {
            MyApplication.logi(LOG_TAG, "in insertImageData->");

            SQLiteDatabase db = MyApplication.db.getWritableDatabase();
            db.beginTransaction();

            String insert_sql = "insert into " + NAME + " (" + "ImageId,ImageDetails) VALUES(?,?)";
            MyApplication.logi(LOG_TAG, "insertImageData insert_sql" + insert_sql);

            SQLiteStatement statement = db.compileStatement(insert_sql);
            JSONObject jsonObject = null;
            int count_array = jsonArray.length();
            MyApplication.logi(LOG_TAG, "insertImageData COUNT ISS->" + count_array);

            for (int i = 0; i < count_array; i++) {


                jsonObject = jsonArray.getJSONObject(i);

                statement.clearBindings();


                statement.bindString(1, jsonObject.getString("ImageId"));
                statement.bindString(2, jsonObject.getString("ImageDetails"));


                statement.execute();
            }
            MyApplication.logi(LOG_TAG, "insertImageData EndTime->");
            db.setTransactionSuccessful();
            db.endTransaction();

        } catch (JSONException e) {
            e.printStackTrace();
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


    /*public static boolean RetailerImages_tableHasData() {
        int num = -1;

        MyApplication.logi(LOG_TAG, "in RetailerImagestableHasData()");
        SQLiteDatabase db = MyApplication.db.getWritableDatabase();
        String query = "SELECT * from RetailerImages";
        MyApplication.log("In RetailerImages tableHasData query :" + query);
        Cursor c = db.rawQuery(query, null);
        num = c.getCount();
        MyApplication.log("In RetailerImages has_tbl_data num is count : " + num);
        if (num > 0) {
            MyApplication.log("RetailerImages data is there : " + num);
            c.close();
            return true;
        } else {
            MyApplication.log("RetailerImages data is not there : " + num);
            c.close();
            return false;
        }

    }*/


}
