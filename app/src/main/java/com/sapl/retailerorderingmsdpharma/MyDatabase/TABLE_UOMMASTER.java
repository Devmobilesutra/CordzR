package com.sapl.retailerorderingmsdpharma.MyDatabase;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.sapl.retailerorderingmsdpharma.activities.MyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.channels.MulticastChannel;
import java.util.regex.Pattern;

/**
 * Created by Shashi on 02-Feb-18.
 */

public class TABLE_UOMMASTER {

    public static String LOG_TAG = "TABLE_UOMMASTER";
    public static String NAME = "UomMaster";

    public static String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
            + NAME + "(ID TEXT, UOMDescription TEXT, ConvToBase TEXT, Formula TEXT, UOMKey TEXT, IsQuantity TEXT, ConversionFormula TEXT, ConversionUomFormula TEXT)";


    public static void insert_bulk_UomMaster(JSONArray jsonArray) {
        try {
            MyApplication.logi(LOG_TAG, "in UomMaster->"+jsonArray);
            SQLiteDatabase db = MyApplication.db.getWritableDatabase();
            db.beginTransaction();


            String insert_sql = "insert into " + NAME + " (" + "ID,UOMDescription,ConvToBase,Formula,UOMKey,IsQuantity,ConversionFormula,ConversionUomFormula) VALUES(?,?,?,?,?,?,?,?)";
            MyApplication.logi(LOG_TAG, "UomMaster insert_sql" + insert_sql);
            SQLiteStatement statement = db.compileStatement(insert_sql);
            JSONObject jsonObject = null;
            int count_array = jsonArray.length();
            MyApplication.logi(LOG_TAG, "UomMaster COUNT ISS->" + count_array);

            for (int i = 0; i < count_array; i++) {


                jsonObject = jsonArray.getJSONObject(i);

                statement.clearBindings();
                statement.bindString(1, jsonObject.getString("ID"));
                statement.bindString(2, jsonObject.getString("UOMDescription"));
                statement.bindString(3, jsonObject.getString("ConvToBase"));
                statement.bindString(4, jsonObject.getString("Formula"));
                statement.bindString(5, jsonObject.getString("UOMKey"));
                statement.bindString(6, jsonObject.getString("IsQuantity"));
                statement.bindString(7, jsonObject.getString("ConversionFormula"));
                statement.bindString(8, jsonObject.getString("ConversionUomFormula"));

                statement.execute();
            }
            MyApplication.logi(LOG_TAG, "UomMaster EndTime->");
            db.setTransactionSuccessful();
            db.endTransaction();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static String getConvertToBase(String uomDescp,String ItemId) {


        MyApplication.logi(LOG_TAG,"uomDescp-->"+uomDescp+"Itemid uom--->"+ItemId);
        MyApplication.logi(LOG_TAG, "in getConvertToBase");
        int count;
        String resp = "";
        MyApplication.logi(LOG_TAG, "in getConvertToBase()");
        SQLiteDatabase db = MyApplication.db.getWritableDatabase();
        String query = "SELECT ConvToBase from UomMaster where UOMDescription='"+uomDescp+"'";
        MyApplication.log("In getConvertToBase query :" + query);
        Cursor c = db.rawQuery(query, null);
        count = c.getCount();
        MyApplication.logi(LOG_TAG, "COUNT OF FILTER IS getConvertToBase-->" + count);
        if (count > 0) {
            c.moveToFirst();
            do {
                resp = c.getString(c.getColumnIndexOrThrow("ConvToBase"));
                MyApplication.logi(LOG_TAG, "getConvertToBase DATA IS-->" + resp);
            } while (c.moveToNext());

        }
        return resp;


    }


    public static void deleteTableData() {
        MyApplication.logi(LOG_TAG, "in delete uom master");
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



