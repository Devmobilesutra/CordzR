package com.sapl.retailerorderingmsdpharma.MyDatabase;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.sapl.retailerorderingmsdpharma.activities.MyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

/**
 * Created by Mrunal on 02/02/2018.
 */

public class TABLE_SETTINGS {


    public static String NAME = "Settings";
    private static final String LOG_TAG = "TABLE_Settings";

    public static String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
            + NAME + "(KeyID TEXT,KeyValue TEXT)";


    public static void insert_bulk_Settings(JSONArray jsonArray) {
        try {
            MyApplication.logi(LOG_TAG, "in insert_bulk_Settings->");

            SQLiteDatabase db = MyApplication.db.getWritableDatabase();
            db.beginTransaction();

            String insert_sql = "insert into " + NAME + " (" + "KeyID,KeyValue) VALUES(?,?)";
            MyApplication.logi(LOG_TAG, "insert_sql" + insert_sql);

            SQLiteStatement statement = db.compileStatement(insert_sql);
            JSONObject jsonObject = null;
            int count_array = jsonArray.length();
            MyApplication.logi(LOG_TAG, "COUNT ISS->" + count_array);

            for (int i = 0; i < count_array; i++) {


                jsonObject = jsonArray.getJSONObject(i);

                statement.clearBindings();


                statement.bindString(1, jsonObject.getString("KeyID"));
                statement.bindString(2, jsonObject.getString("KeyValue"));


                statement.execute();
            }
            MyApplication.logi(LOG_TAG, "EndTime->");
            db.setTransactionSuccessful();
            db.endTransaction();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public static boolean tableHasData() {
        int num = -1;

        MyApplication.logi(LOG_TAG, "TABLE_Settings in tableHasData()");
        SQLiteDatabase db = MyApplication.db.getWritableDatabase();
        String query = "SELECT * from Settings";
        MyApplication.log("In TABLE_Settings tableHasData query :" + query);
        Cursor c = db.rawQuery(query, null);
        num = c.getCount();
        MyApplication.log("InTABLE_Settings has_tbl_data num is count : " + num);
        if (num > 0) {
            MyApplication.log("TABLE_Settings data is there : " + num);
            c.close();
            return true;
        } else {
            MyApplication.log("TABLE_Settings data is not there : " + num);
            c.close();
            return false;
        }

    }

    public static String getItemFilterValues() {
        MyApplication.logi(LOG_TAG, "in getItemFilterValues");
        int count;
        String resp = "";
        MyApplication.logi(LOG_TAG, "in getItemFilterValues()");
        SQLiteDatabase db = MyApplication.db.getWritableDatabase();
        String query = "SELECT * from Settings where KeyID = 'ITEMFILTER'";
        MyApplication.log("In getItemFilterValues query :" + query);
        Cursor c = db.rawQuery(query, null);
        count = c.getCount();
        MyApplication.logi(LOG_TAG, "COUNT OF FILTER IS-->" + count);
        if (count > 0) {
            c.moveToFirst();
            do {
                resp = c.getString(c.getColumnIndexOrThrow("KeyValue"));
                MyApplication.logi(LOG_TAG, "DATA IS-->" + resp);
            } while (c.moveToNext());

        }
        return resp;


    }


    public static String getUomLabels() {
        MyApplication.logi(LOG_TAG, "in getUomLabels");
        int count;
        String resp = "";
        MyApplication.logi(LOG_TAG, "in getUomLabels()");
        SQLiteDatabase db = MyApplication.db.getWritableDatabase();
        String query = "SELECT * from Settings where KeyID = 'ORDBOOKUOMLABEL'";
        MyApplication.log("In getUomLabels query :" + query);
        Cursor c = db.rawQuery(query, null);
        count = c.getCount();
        MyApplication.logi(LOG_TAG, "COUNT OF FILTER IS getUomLabels-->" + count);
        if (count > 0) {
            c.moveToFirst();
            do {
                resp = c.getString(c.getColumnIndexOrThrow("KeyValue"));

                String[] split = resp.split(Pattern.quote("/"));
                String uom_val1 = split[0];
                MyApplication.set_session(MyApplication.SESSION_UOM_VALUE_FIRST, uom_val1);
                String uom_val2 = split[1];
                MyApplication.set_session(MyApplication.SESSION_UOM_VALUE_SECOND, uom_val2);
                MyApplication.logi(LOG_TAG, "upm val 1-->" + uom_val1 + "uom val2  ->" + uom_val2);

                MyApplication.logi(LOG_TAG, "getUomLabels DATA IS-->" + resp);
            } while (c.moveToNext());

        }
        return resp;


    }


    public static String getUomOrderBookCal() {
        MyApplication.logi(LOG_TAG, "in getUomOrderBookCal");
        int count;
        String resp = "";
        MyApplication.logi(LOG_TAG, "in getUomOrderBookCal()");
        SQLiteDatabase db = MyApplication.db.getWritableDatabase();
        String query = "SELECT * from Settings where KeyID = 'ORDBOOKCAL'";
        MyApplication.log("In getUomOrderBookCal query :" + query);
        Cursor c = db.rawQuery(query, null);
        count = c.getCount();
        MyApplication.logi(LOG_TAG, "getUomOrderBookCal COUNT OF FILTER IS-->" + count);
        if (count > 0) {
            c.moveToFirst();
            do {
                resp = c.getString(c.getColumnIndexOrThrow("KeyValue"));
                MyApplication.logi(LOG_TAG, "ORDBOOKCAL DATA IS-->" + resp);


                String[] split = resp.split(Pattern.quote("/"));
                String uom_val = split[0];
                MyApplication.set_session(MyApplication.SESSION_UOM_ORDER_1, uom_val);
                String uom_val_2 = split[1];
                MyApplication.set_session(MyApplication.SESSION_UOM_ORDER_2, uom_val_2);
                MyApplication.logi(LOG_TAG, "ORDBOOKCAL upm val 1-->" + uom_val + "ORDBOOKCAL uom val2  ->" + uom_val_2);

            } while (c.moveToNext());

        }
        return resp;


    }


    public static void deleteTableData() {
        MyApplication.logi(LOG_TAG, "in delete settings");
        SQLiteDatabase db = MyApplication.db.getWritableDatabase();


        db.delete(NAME, null, null);


    }



    public static String get_value_from_setting(String key) {
        MyApplication.logi(LOG_TAG, "get_value_from_setting " + key);
        SQLiteDatabase db = MyApplication.db.getWritableDatabase();

        String resp = "";
        String querytogetuserdata = "SELECT * FROM " + NAME
                + " where KeyID ='" + key + "' limit 1";

        Cursor c = db.rawQuery(querytogetuserdata, null);
        int count = c.getCount();
        if (count > 0) {
            c.moveToFirst();
            do {
                resp = c.getString(c.getColumnIndexOrThrow("KeyValue"));

            } while (c.moveToNext());
        }
        if (c != null) {
            c.close();
        }
        MyApplication.logi(LOG_TAG, "res " + resp);
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
