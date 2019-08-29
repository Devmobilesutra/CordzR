package com.sapl.retailerorderingmsdpharma.MyDatabase;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.sapl.retailerorderingmsdpharma.activities.MyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class TABLE_TEMP_RETAILER_ORDER_MASTER {
    public static String LOG_TAG = "TABLE_TEMP_RETAILER_ORDER_MASTER";
    public static String NAME = "TempRetailerOrderMaster";


    public static String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
            + NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, OrderID TEXT, DistributorID INTEGER,RetailerID INTEGER,OrderDate TEXT, " +
            "Amount NUMERIC, OrderStatus INTEGER, OrderRemarks TEXT, OrderRating TINYINT, UserID INTEGER)";


    public static void insert_bulk_TempRetailerOrderMaster(JSONArray jsonArray) {
        try {
            MyApplication.logi(LOG_TAG, "in TABLE_TEMP_RETAILER_ORDER_MASTER->");
            SQLiteDatabase db = MyApplication.db.getWritableDatabase();
            db.beginTransaction();


            String insert_sql = "insert into " + NAME + " (" + "ID,OrderID,DistributorID,RetailerID,OrderDate" +
                    "Amount,OrderStatus,OrderRemarks,OrderRating,UserID) VALUES(?,?,?,?,?,?,?,?,?,?)";
            MyApplication.logi(LOG_TAG, "insert_sql" + insert_sql);
            SQLiteStatement statement = db.compileStatement(insert_sql);
            JSONObject jsonObject = null;
            int count_array = jsonArray.length();
            MyApplication.logi(LOG_TAG, "COUNT ISS TABLE_TEMP_RETAILER_ORDER_MASTER->" + count_array);
            try {
                for (int i = 0; i < count_array; i++) {


                    jsonObject = jsonArray.getJSONObject(i);

                    statement.clearBindings();
                    statement.bindString(1, jsonObject.getString("ID"));
                    statement.bindString(2, jsonObject.getString("OrderID"));
                    statement.bindString(3, jsonObject.getString("DistributorID"));
                    statement.bindString(4, jsonObject.getString("RetailerID"));
                    statement.bindString(5, jsonObject.getString("OrderDate"));

                    statement.bindString(6, jsonObject.getString("Amount"));
                    statement.bindString(7, jsonObject.getString("OrderStatus"));
                    statement.bindString(8, jsonObject.getString("OrderRemarks"));
                    statement.bindString(9, jsonObject.getString("OrderRating"));
                    statement.bindString(10, jsonObject.getString("UserID"));

                    statement.execute();
                }
                MyApplication.logi(LOG_TAG, "EndTime TABLE_TEMP_RETAILER_ORDER_MASTER->");
                db.setTransactionSuccessful();
                db.endTransaction();


            } catch (JSONException e) {
                MyApplication.logi(LOG_TAG, "JSONException TABLE_TEMP_RETAILER_ORDER_MASTER--->" + e.getMessage());

            }
        } catch (
                Exception e)

        {
            MyApplication.logi(LOG_TAG, " TABLE_TEMP_RETAILER_ORDER_MASTER exception--->" + e.getMessage());
        }
    }


    public static int checkDistPresentInTempOrderMaster(String dist_Id) {

        MyApplication.logi(LOG_TAG, "in checkDistPresentInTempOrderMaster");
        SQLiteDatabase db = MyApplication.db.getReadableDatabase();
        ContentValues initialValues1 = new ContentValues();
        String querytogetuserdata = "SELECT * FROM  TempRetailerOrderMaster where DistributorID = " + dist_Id;

        MyApplication.logi(LOG_TAG, "in querytogetuserdata checkDistPresentInTempOrderMaster" + querytogetuserdata);

        Cursor c = db.rawQuery(querytogetuserdata, null);

        int count = c.getCount();
        if (count > 0) {
            c.moveToFirst();

            MyApplication.logi(LOG_TAG, "in checkDistPresentInTempOrderMaster count > 0");
            String order_id = c.getString(c.getColumnIndexOrThrow("OrderID"));
            MyApplication.set_session(MyApplication.SESSION_ORDER_ID, order_id);
            return 1;

        } else {
            MyApplication.logi(LOG_TAG, "in checkDistPresentInTempOrderMaster count < 0");
            return 0;
        }


    }


    public static int insertTempOrderMaster(String orderId,
                                            String distributorID,
                                            String retailerID,
                                            String orderDate,
                                            String amount,
                                            String orderStatus,
                                            String orderRemarks,
                                            String orderRating,
                                            String userId) {

        MyApplication.logi(LOG_TAG, "in insertTempOrderMaster");
        SQLiteDatabase db = MyApplication.db.getReadableDatabase();
        String total_amt = TABLE_TEMP_ORDER_DETAILS.getSumOfAllItems(orderId);
        MyApplication.logi(LOG_TAG,"TOTALL AMY ---->"+total_amt);
        ContentValues initialValues1 = new ContentValues();

        initialValues1.put("OrderID", orderId);
        initialValues1.put("DistributorID", distributorID);
        initialValues1.put("RetailerID", retailerID);
        initialValues1.put("OrderDate", orderDate);
        initialValues1.put("Amount", total_amt);
        initialValues1.put("OrderStatus", orderStatus);
        initialValues1.put("OrderRemarks", orderRemarks);
        initialValues1.put("OrderRating", orderRating);
        initialValues1.put("UserID", userId);
        MyApplication.logi(LOG_TAG, "insertTempOrderMaster->" + initialValues1);


        long ret = db.insert(NAME, null, initialValues1);
        MyApplication.logi(LOG_TAG, "in insertTempOrderMaster ret->" + ret);
        if (ret > 0) {
            MyApplication.logi(LOG_TAG, "Successfull in insertTempOrderMaster ret ->" + ret);
            return 0;
        } else {
            MyApplication.logi(LOG_TAG, "Not successfiull in insertTempOrderMaster ret->" + ret);
            return 1;

        }

    }


    public static JSONObject getData(JSONObject jsonObject) {
        JSONArray jsonArray_master = new JSONArray();
        MyApplication.logi(LOG_TAG, "IN JSONObject getDat ");
        SQLiteDatabase db = MyApplication.db.getReadableDatabase();
        String query = "SELECT * FROM " + NAME+ " where OrderID = '"+MyApplication.get_session(MyApplication.SESSION_ORDER_ID)+"'";

        MyApplication.logi(LOG_TAG, "query getData-------->" + query);
        Cursor c = db.rawQuery(query, null);
        String retailerId = TABLE_PCUSTOMER.getCustId();
        MyApplication.logi(LOG_TAG, "retailerId-------->" + retailerId);
        JSONArray jsonArray_details_master;
        JSONArray jsonArray_details = new JSONArray();
        int count = c.getCount();
        MyApplication.logi(LOG_TAG, "count="+count);

        try {
            if (c.getCount() > 0) {
                c.moveToFirst();
                do {
                    JSONObject jsonObject1 = new JSONObject();
                    String order_Id = c.getString(c.getColumnIndexOrThrow("OrderID"));
                    jsonObject1.put("OrderId", order_Id);
                    jsonObject1.put("DistributorId", c.getInt(c.getColumnIndexOrThrow("DistributorID")));
                    jsonObject1.put("RetailerId", retailerId);
                    jsonObject1.put("OrderDate", c.getString(c.getColumnIndexOrThrow("OrderDate")));


                    String total_amt = TABLE_TEMP_ORDER_DETAILS.getSumOfAllItems(order_Id);
                    if (total_amt == null) {
                        jsonObject1.put("TotalAmount", "0");
                        MyApplication.set_session(MyApplication.SESSION_TOTAL_RPS_FOR_DASHBOARD_VALUE, "0.0");
                    } else {
                        jsonObject1.put("TotalAmount", total_amt);
                        MyApplication.set_session(MyApplication.SESSION_TOTAL_RPS_FOR_DASHBOARD_VALUE, total_amt);
                    }


                    jsonObject1.put("OrderStatus", "1");
                    jsonObject1.put("OrderRemarks", "");
                    jsonObject1.put("OrderRating", "0");

                    if (total_amt == null) {
                        MyApplication.logi(LOG_TAG, "AMT IS 0");

                    } else {
                        jsonArray_master.put(jsonObject1);
                    }
                    jsonArray_details = TABLE_TEMP_ORDER_DETAILS.getDetailsData(order_Id, jsonArray_details);

                } while (c.moveToNext());

                jsonObject.put("OrderMaster", jsonArray_master);
                jsonObject.put("OrderDetails", jsonArray_details);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        MyApplication.logi(LOG_TAG, "IN JSONObject jsonObject " + jsonObject);
        return jsonObject;
    }

    public static JSONObject getDatasync(JSONObject jsonObject) {
        JSONArray jsonArray_master = new JSONArray();
        MyApplication.logi(LOG_TAG, "IN JSONObject getDat ");
        SQLiteDatabase db = MyApplication.db.getReadableDatabase();
        String query = "SELECT * FROM TempRetailerOrderMaster";
        MyApplication.logi(LOG_TAG, "query getData-------->" + query);
        Cursor c = db.rawQuery(query, null);
        String retailerId = TABLE_PCUSTOMER.getCustId();
        MyApplication.logi(LOG_TAG, "retailerId-------->" + retailerId);
        JSONArray jsonArray_details_master;
        JSONArray jsonArray_details = new JSONArray();
        int count = c.getCount();
        MyApplication.logi(LOG_TAG, "count="+count);

        try {
            if (c.getCount() > 0) {
                c.moveToFirst();
                do {
                    JSONObject jsonObject1 = new JSONObject();
                    String order_Id = c.getString(c.getColumnIndexOrThrow("OrderID"));
                    jsonObject1.put("OrderId", order_Id);
                    jsonObject1.put("DistributorId", c.getInt(c.getColumnIndexOrThrow("DistributorID")));
                    jsonObject1.put("RetailerId", retailerId);
                    jsonObject1.put("OrderDate", c.getString(c.getColumnIndexOrThrow("OrderDate")));


                    String total_amt = TABLE_TEMP_ORDER_DETAILS.getSumOfAllItems(order_Id);
                    if (total_amt == null) {
                        jsonObject1.put("TotalAmount", "0");
                        MyApplication.set_session(MyApplication.SESSION_TOTAL_RPS_FOR_DASHBOARD_VALUE, "0.0");
                    } else {
                        jsonObject1.put("TotalAmount", total_amt);
                        MyApplication.set_session(MyApplication.SESSION_TOTAL_RPS_FOR_DASHBOARD_VALUE, total_amt);
                    }


                    jsonObject1.put("OrderStatus", "1");
                    jsonObject1.put("OrderRemarks", "");
                    jsonObject1.put("OrderRating", "0");

                    if (total_amt == null) {
                        MyApplication.logi(LOG_TAG, "AMT IS 0");

                    } else {
                        jsonArray_master.put(jsonObject1);
                    }
                    jsonArray_details = TABLE_TEMP_ORDER_DETAILS.getDetailsData(order_Id, jsonArray_details);

                } while (c.moveToNext());

                jsonObject.put("OrderMaster", jsonArray_master);
                jsonObject.put("OrderDetails", jsonArray_details);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        MyApplication.logi(LOG_TAG, "IN JSONObject jsonObject " + jsonObject);
        return jsonObject;
    }


    public static void deleteAllRecords(String orderId) {
        MyApplication.logi(LOG_TAG, "deleteAllRecords for ordermaster");
        SQLiteDatabase db = MyApplication.db.getWritableDatabase();
        int ret = db.delete(NAME, "OrderID =?", new String[]{orderId});


        if (ret > 0) {
            MyApplication.logi(LOG_TAG, "DELETED SUCCESSFULLY" + ret);

        } else {
            MyApplication.logi(LOG_TAG, "DELETED UNSUCCESSFULLY" + ret);
        }
    }

    public static void updateAmt(String order_id, float total_lbl) {

        MyApplication.logi(LOG_TAG,"SESSIOM--->"+order_id);
        MyApplication.logi(LOG_TAG,"total_lbl--->"+total_lbl);
        SQLiteDatabase db = MyApplication.db.getReadableDatabase();
        ContentValues initialValues1 = new ContentValues();
         String ret1 = "UPDATE TempRetailerOrderMaster SET Amount = "+total_lbl +"' WHERE OrderID = "+order_id;
       MyApplication.logi(LOG_TAG,"IN UPDATE AMT --->"+ret1);
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
