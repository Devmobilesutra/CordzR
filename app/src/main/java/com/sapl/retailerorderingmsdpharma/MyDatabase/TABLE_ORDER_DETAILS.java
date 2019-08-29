package com.sapl.retailerorderingmsdpharma.MyDatabase;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.sapl.retailerorderingmsdpharma.activities.MyApplication;
import com.sapl.retailerorderingmsdpharma.models.DivisionModel;
import com.sapl.retailerorderingmsdpharma.models.OrderDeliveryStatusModel;
import com.sapl.retailerorderingmsdpharma.models.OrderDetailModel;
import com.sapl.retailerorderingmsdpharma.models.OrderReviewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

;import java.util.ArrayList;
import java.util.List;


/**
 * Created by Mrunal on 20/06/2016.
 */
public class TABLE_ORDER_DETAILS {
    public static String LOG_TAG = "TABLE_ORDER_DETAILS";
    public static String NAME = "OrderDetails";

    public static String
            COL_ID = "ID",
            COL_ORDER_ID = "OrderId",
            COL_ITEM_ID = "ItemID",
            COL_ITEM_NAME = "item_Name",
            COL_QUANTITY_ONE = "LargeUnitQty",
            COL_QUANTITY_TWO = "SmallUnitQty",
            COL_RATE = "Rate", /// ---->discounted_price_of_btl
            COL_DISCOUNTED_PRICE_OF_CASE = "discounted_price_cases",
            COL_AMOUNT = "Amount",
            COL_FREE_QUANTITY_ONE = "FreeLargeUnitQty",
            COL_FREE_QUANTITY_TWO = "FreeSmallUnitQty";


    public static String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + NAME + " ("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COL_ORDER_ID + " TEXT,"
            + COL_ITEM_ID + " TEXT,"
            + COL_ITEM_NAME + " TEXT,"
            + COL_QUANTITY_ONE + " TEXT,"
            + COL_QUANTITY_TWO + " TEXT,"
            + COL_RATE + " TEXT ,"
            + COL_DISCOUNTED_PRICE_OF_CASE + " TEXT ,"
            + COL_AMOUNT + " TEXT,"
            + COL_FREE_QUANTITY_ONE + " TEXT ,"
            + COL_FREE_QUANTITY_TWO + " TEXT )";


    public static void insert_bulk_OrderDetails(JSONArray jsonArray) {
        try {
            MyApplication.logi(LOG_TAG, "in insert_bulk_Orderdetails>");
            SQLiteDatabase db = MyApplication.db.getWritableDatabase();
            db.beginTransaction();

            String insert_sql = "insert into " + NAME + " ( "

                    + COL_ORDER_ID + ", "
                    + COL_ITEM_ID + ", "
                 /*   + COL_ITEM_NAME + ", "*/
                    + COL_QUANTITY_ONE + ", "
                    + COL_QUANTITY_TWO + ", "
                    + COL_FREE_QUANTITY_ONE + ", "
                    + COL_FREE_QUANTITY_TWO + ", "
                    + COL_RATE + ", "
                    + COL_AMOUNT + ") VALUES(?,?,?,?,?,?,?,?)";


            MyApplication.logi(LOG_TAG, "insert_sql insert_bulk_Orderdetails" + insert_sql);
            SQLiteStatement statement = db.compileStatement(insert_sql);
            JSONObject jsonObject = null;
            int count_array = jsonArray.length();
            MyApplication.logi(LOG_TAG, "COUNT ISS order details->" + count_array);
            try {
                for (int i = 0; i < count_array; i++) {


                    jsonObject = jsonArray.getJSONObject(i);
                    statement.clearBindings();
                   /* statement.bindString(1, jsonObject.getString(COL_ID));*/
                    statement.bindString(1, jsonObject.getString(COL_ORDER_ID));
                    statement.bindString(2, jsonObject.getString(COL_ITEM_ID));
                   /* statement.bindString(4, jsonObject.getString(COL_ITEM_NAME));*/
                    statement.bindString(3, jsonObject.getString(COL_QUANTITY_ONE));
                    MyApplication.logi(LOG_TAG, " Rj1=" +  jsonObject.getString(COL_QUANTITY_ONE));
                    statement.bindString(4, jsonObject.getString(COL_QUANTITY_TWO));
                    MyApplication.logi(LOG_TAG, "Rj2=" + jsonObject.getString(COL_QUANTITY_TWO));
                    statement.bindString(5, jsonObject.getString(COL_FREE_QUANTITY_ONE));
                    statement.bindString(6, jsonObject.getString(COL_FREE_QUANTITY_TWO));
                    statement.bindString(7, jsonObject.getString(COL_RATE));
                    statement.bindString(8, jsonObject.getString(COL_AMOUNT));


                    statement.execute();
                }
                MyApplication.logi(LOG_TAG, "EndTime->");
                db.setTransactionSuccessful();
                db.endTransaction();

            } catch (JSONException e) {
                MyApplication.logi(LOG_TAG, "JSONException insert_bulk_OrderDetails--->" + e.getMessage());

            }
        } catch (Exception e) {
            MyApplication.logi(LOG_TAG, " insert_bulk_OrderDetails exception--->" + e.getMessage());
        }
    }


    public static int insertOrderDetailsfinal(String order_id) {
        MyApplication.logi(LOG_TAG, "in insertOrderDetailsfinal");
        MyApplication.logi(LOG_TAG, "order_id------->" + order_id);

        SQLiteDatabase db = MyApplication.db.getReadableDatabase();
        ContentValues initialValues1 = new ContentValues();


       /* String query = "INSERT INTO OrderDetails SELECT * FROM TempOrderDetails WHERE OrderId = " + order_id;*/

        String query = "INSERT INTO OrderDetails (orderId,ItemId,item_name,LargeUnitQty,SmallUnitQty,Rate," +
                "                discounted_price_cases,Amount,FreeLargeUnitQty,FreeSmallUnitQty) SELECT " +
                " orderid,itemID,item_name,LargeUnit,SmallUnit," +
                "                rate,discounted_price_cases,Amount,FreeLargeUnitQty," +
                "        FreeSmallUnitQty FROM TempOrderDetails WHERE OrderId = " + order_id;


        MyApplication.logi(LOG_TAG, "query for final is---->" + query);
        Cursor c = db.rawQuery(query, null);
          MyApplication.logi(LOG_TAG, "insertOrderDetailsfinal count---->" + c.getCount());
        return 0;

    }


    public static int getCountForSpecificOrderID(String orderId) {
        MyApplication.logi(LOG_TAG, "IN getCountForOrderId-->" + orderId);
        SQLiteDatabase db = MyApplication.db.getReadableDatabase();
        ContentValues cv = new ContentValues();

        String query = "SELECT * from OrderDetails where OrderDetails.OrderID = " + orderId;
        MyApplication.logi(LOG_TAG, "QUERY FOR COUNT IS-->" + query);
        Cursor c = db.rawQuery(query, null);
        int count = c.getCount();
        MyApplication.logi(LOG_TAG, "count  QUERY FOR COUNT IS-->" + count);
        return count;
    }


    public static ArrayList<OrderReviewModel> getItemDetails(String orderId) {
        MyApplication.logi(LOG_TAG, "FILTR-->" + orderId);
        MyApplication.logi(LOG_TAG, "in getItemDetails");


        ArrayList<OrderReviewModel> res = new ArrayList<>();
        String product_name;
        byte[] product_img_path = new byte[0];
        String no_of_cases;
        String no_of_bottels;
        String no_of_cases_free;
        String no_of_btls_free;
        String item_id;
        double amt;
        float discounted_single_btl_rate = 0.f, discounted_single_case_rate;
        SQLiteDatabase db = MyApplication.db.getReadableDatabase();


        String query = "SELECT * FROM OrderDetails WHERE OrderId = " + orderId;
        MyApplication.logi(LOG_TAG, "QUERY FOR getItemDetails IS-->" + query);
        try {
            Cursor c = db.rawQuery(query, null);
            int count = c.getCount();
            if (count > 0) {
                c.moveToFirst();
                do {
                    //  product_name = c.getString(c.getColumnIndexOrThrow("item_Name"));
                    //  product_img_path = c.getBlob(c.getColumnIndexOrThrow("iImagePath"));
                    no_of_cases = c.getString(c.getColumnIndexOrThrow("LargeUnitQty"));

                    no_of_bottels = c.getString(c.getColumnIndexOrThrow("SmallUnitQty"));
                   /* no_of_cases_free = c.getString(c.getColumnIndexOrThrow("SmallUnit"));
                    no_of_btls_free = c.getString(c.getColumnIndexOrThrow("SmallUnit"));*/
                    item_id = c.getString(c.getColumnIndexOrThrow("ItemID"));

                 /*   String qurry2 = "select item_Name from PItem where ItemID ="+item_id;
                    MyApplication.logi(LOG_TAG,"NAME AND ID----."+item_id+);*/

                    product_name = TABLE_PITEM.getItemName(item_id);

                    amt = c.getDouble(c.getColumnIndexOrThrow("Amount"));
                    String amt1 = String.format("%.2f", amt);

                    discounted_single_btl_rate = c.getFloat(c.getColumnIndexOrThrow("Rate"));
                    discounted_single_case_rate = c.getFloat(c.getColumnIndexOrThrow("discounted_price_cases"));


                    String total_after_edit = "0";

                    MyApplication.logi(LOG_TAG, "product_name---->" + product_name);
                    MyApplication.logi(LOG_TAG, "no_of_cases---->" + no_of_cases);
                    MyApplication.logi(LOG_TAG, "no_of_bottels---->" + no_of_bottels);
                    MyApplication.logi(LOG_TAG, "item_id---->" + item_id);
                    MyApplication.logi(LOG_TAG, "Amount---->" + amt1);
                    MyApplication.logi(LOG_TAG, "single_btl_rate---->" + discounted_single_btl_rate);
                    MyApplication.logi(LOG_TAG, "discounted_single_case_rate---->" + discounted_single_case_rate);


                        OrderReviewModel previewModel = new OrderReviewModel(item_id, product_name, product_img_path, no_of_cases, no_of_bottels, "0", "0", amt1, discounted_single_btl_rate, discounted_single_case_rate, total_after_edit);

                    res.add(previewModel);
                } while (c.moveToNext());


            }
            c.close();
        } catch (Exception e) {
            MyApplication.logi(LOG_TAG, "getDivision in Exception" + e.getMessage());
        }

        return res;
    }
////////////////////////////////////////Rj.////////////////////////////////////////


    //for specific status list
    public static List<OrderReviewModel> getOrderStatusList1(String status) {
        MyApplication.logi(LOG_TAG, "In rAJANI");
        MyApplication.logi(LOG_TAG, "FILTR-->" + status);
        MyApplication.logi(LOG_TAG, "in getItemDetails");


        ArrayList<OrderReviewModel> res = new ArrayList<>();
        String product_name;
        byte[] product_img_path = new byte[0];
        String no_of_cases;
        String no_of_bottels;
        String no_of_cases_free;
        String no_of_btls_free;
        String item_id;
        double amt;
        float discounted_single_btl_rate = 0.f, discounted_single_case_rate;
        SQLiteDatabase db = MyApplication.db.getReadableDatabase();


        String query =" SELECT r.OrderId as OrderId,r.OrderStatus as status,r.TotalAmount as Amount,r.OrderDate as Date, " +
        "p.orderId as ORDER_ID,p.LargeUnitQty as Box,p.SmallUnitQty as Each,p.Amount as AMOUNT from RetailerOrderMaster r ,OrderDetails p ,OrderStatus s  " +
                " where p.orderId = r.OrderId  and s.Statusid = r.OrderStatus and r.orderId = s.OrderId ORDER BY s.StatusDateTime desc";


      /*  (orderId,ItemId,item_name,LargeUnitQty,SmallUnitQty,Rate," +
        "                discounted_price_cases,Amount,FreeLargeUnitQty,FreeSmallUnitQty) SELECT " +
                " orderid,itemID,item_name,LargeUnit,SmallUnit," +
                "                rate,discounted_price_cases,Amount,FreeLargeUnitQty," +
                "        FreeSmallUnitQty FROM TempOrderDetails WHERE OrderId = " + order_id;*/

        MyApplication.logi(LOG_TAG, "QUERY FOR getItemDetails IS-->" + query);
        try {
            Cursor c = db.rawQuery(query, null);
            int count = c.getCount();
            if (count > 0) {
                c.moveToFirst();
                do {

                    no_of_cases = c.getString(c.getColumnIndexOrThrow("LargeUnitQty"));
                    no_of_bottels = c.getString(c.getColumnIndexOrThrow("SmallUnitQty"));
                    item_id = c.getString(c.getColumnIndexOrThrow("ItemID"));

                    product_name = TABLE_PITEM.getItemName(item_id);
                    amt = c.getDouble(c.getColumnIndexOrThrow("Amount"));
                    String amt1 = String.format("%.2f", amt);

                    discounted_single_btl_rate = c.getFloat(c.getColumnIndexOrThrow("Rate"));
                    discounted_single_case_rate = c.getFloat(c.getColumnIndexOrThrow("discounted_price_cases"));


                    String total_after_edit = "0";

                    MyApplication.logi(LOG_TAG, "product_name---->" + product_name);
                    MyApplication.logi(LOG_TAG, "no_of_cases---->" + no_of_cases);
                    MyApplication.logi(LOG_TAG, "no_of_bottels---->" + no_of_bottels);
                    MyApplication.logi(LOG_TAG, "item_id---->" + item_id);
                    MyApplication.logi(LOG_TAG, "Amount---->" + amt1);
                    MyApplication.logi(LOG_TAG, "single_btl_rate---->" + discounted_single_btl_rate);
                    MyApplication.logi(LOG_TAG, "discounted_single_case_rate---->" + discounted_single_case_rate);


                    OrderReviewModel previewModel = new OrderReviewModel(item_id, product_name, product_img_path, no_of_cases, no_of_bottels, "0", "0", amt1, discounted_single_btl_rate, discounted_single_case_rate, total_after_edit);

                    res.add(previewModel);
                } while (c.moveToNext());


            }
            c.close();
        } catch (Exception e) {
            MyApplication.logi(LOG_TAG, "getDivision in Exception" + e.getMessage());
        }

        return res;
    }

    //////////////////////////////////////////////////////////////////////////////
    public static void updateData(JSONArray jsonArray) {
        try {
            MyApplication.logi(LOG_TAG, "in updateData order details->" + jsonArray);
            SQLiteDatabase db = MyApplication.db.getWritableDatabase();
            db.beginTransaction();


            String insert_sql = "insert into " + NAME + " (" + "OrderId,DistributorID,OrderDate," +
                    "TotalAmount,OrderStatus,OrderRemarks) VALUES(?,?,?,?,?,?)";
            MyApplication.logi(LOG_TAG, "updateData order details->--" + insert_sql);
            SQLiteStatement statement = db.compileStatement(insert_sql);
            JSONObject jsonObject = null;
            int count_array = jsonArray.length();
            MyApplication.logi(LOG_TAG, "COUNT IS updateData order details->->" + count_array);
            try {
                MyApplication.logi(LOG_TAG, "updateData order details-> insert_sql" + insert_sql);
                MyApplication.logi(LOG_TAG, "updateData order details-> COUNT ISS->" + count_array);

                for (int i = 0; i < count_array; i++) {
                    jsonObject = jsonArray.getJSONObject(i);

                    statement.clearBindings();

                    statement.bindString(1, jsonObject.getString("OrderId"));
                    statement.bindString(2, jsonObject.getString("DistributorID"));
                    statement.bindString(3, jsonObject.getString("OrderDate"));
                    statement.bindString(4, jsonObject.getString("TotalAmount"));
                    statement.bindString(5, jsonObject.getString("OrderStatus"));
                    statement.bindString(6, jsonObject.getString("OrderRemarks"));

                    statement.execute();
                }
                MyApplication.logi(LOG_TAG, "EndTime updateData order details->->");
                db.setTransactionSuccessful();
                db.endTransaction();


            } catch (JSONException e) {
                MyApplication.logi(LOG_TAG, "JSONException updateData order details->--->" + e.getMessage());

            }
        } catch (
                Exception e)

        {
            MyApplication.logi(LOG_TAG, " updateData order details-> exception--->" + e.getMessage());
        }

    }

    public static void deletedOrderDetails() {
        MyApplication.logi(LOG_TAG, "in deletedOrderDetails");
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
