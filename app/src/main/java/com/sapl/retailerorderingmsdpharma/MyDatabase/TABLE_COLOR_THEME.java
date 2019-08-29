package com.sapl.retailerorderingmsdpharma.MyDatabase;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.sapl.retailerorderingmsdpharma.activities.MyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Mrunal on 10/07/2018.
 */

public class TABLE_COLOR_THEME {


    public static String NAME = "colortheme";
    private static final String LOG_TAG = "TABLE_COLOR_THEME";

    public static String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
            + NAME + "(colorId TEXT,type TEXT,hashcode TEXT)";



    public static void insert_bulk_theme_color(JSONArray jsonArray) {
        try {
            MyApplication.logi(LOG_TAG, "in insert_bulk_theme_color->");

            SQLiteDatabase db = MyApplication.db.getWritableDatabase();
            db.beginTransaction();

            String insert_sql = "insert into " + NAME + " (" + "colorId,type,hashcode) VALUES(?,?,?)";
            MyApplication.logi(LOG_TAG, "insert_bulk_theme_color insert_sql" + insert_sql);

            SQLiteStatement statement = db.compileStatement(insert_sql);
            JSONObject jsonObject = null;
            int count_array = jsonArray.length();
            MyApplication.logi(LOG_TAG, "insert_bulk_theme_color COUNT ISS->" + count_array);

            for (int i = 0; i < count_array; i++) {


                jsonObject = jsonArray.getJSONObject(i);

                statement.clearBindings();


                statement.bindString(1, jsonObject.getString("colorId"));
                statement.bindString(2, jsonObject.getString("type"));
                statement.bindString(3, jsonObject.getString("hashcode"));


                statement.execute();
            }
            MyApplication.logi(LOG_TAG, "insert_bulk_theme_color EndTime->");
            db.setTransactionSuccessful();
            db.endTransaction();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    public static String get_color_value_from_tbl(String colorId) {
        MyApplication.logi(LOG_TAG, "get_color_value_from_tbl EndTime->"+colorId);
        SQLiteDatabase db = MyApplication.db.getReadableDatabase();

        String resp = "";
        String querytogetuserdata = "SELECT * from colortheme where colorId = '" + colorId + "'";
        MyApplication.logi(LOG_TAG, "get_color_value_from_tbl querytogetuserdata->"+querytogetuserdata);
        Cursor c = db.rawQuery(querytogetuserdata, null);
        int count = c.getCount();
        if (count > 0) {
            c.moveToFirst();
            do {
                resp = c.getString(c.getColumnIndexOrThrow("hashcode"));
                MyApplication.logi(LOG_TAG, "res hashcode IS " + resp);
            } while (c.moveToNext());
        }
        MyApplication.logi(LOG_TAG, "res IS " + resp);
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
