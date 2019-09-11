package com.sapl.retailerorderingmsdpharma.MyDatabase;

/**
 * Created by Mrunal on 12/02/2018.
 */

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.sapl.retailerorderingmsdpharma.activities.MyApplication;
import com.sapl.retailerorderingmsdpharma.models.OrderReviewModel;
import com.sapl.retailerorderingmsdpharma.models.SubItemDataModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SplittableRandom;


/**
 * Created by Acer on 20/06/2016.
 */
public class TABLE_TEMP_ORDER_DETAILS {
    public static String LOG_TAG = "TABLE_TEMP_ORDER_DETAILS";
    public static String NAME = "TempOrderDetails";

    public static String
            COL_ID = "ID",
            COL_ORDER_ID = "OrderID",
            COL_ITEM_ID = "ItemID",
            COL_ITEM_NAME = "item_Name",
            COL_QUANTITY_ONE = "LargeUnit",
            COL_QUANTITY_TWO = "SmallUnit",
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


    public static void insert_temp_bulk_OrderDetails(JSONArray jsonArray) {
        try {
            MyApplication.logi(LOG_TAG, "in TABLE_TEMP_ORDER_DETAILS->");
            SQLiteDatabase db = MyApplication.db.getWritableDatabase();
            db.beginTransaction();

            String insert_sql = "insert into " + NAME + " ( "
                    + COL_ID + ", "
                    + COL_ORDER_ID + ", "
                    + COL_ITEM_ID + ", "
                    + COL_ITEM_NAME + ", "
                    + COL_QUANTITY_ONE + ", "
                    + COL_QUANTITY_TWO + ", "
                    + COL_RATE + ", "
                    + COL_DISCOUNTED_PRICE_OF_CASE + ", "
                    + COL_AMOUNT + ","
                    + COL_FREE_QUANTITY_ONE + ","
                    + COL_FREE_QUANTITY_TWO + ") VALUES(?,?,?,?,?,?,?,?,?,?,?)";


            MyApplication.logi(LOG_TAG, "insert_sql TABLE_TEMP_ORDER_DETAILS" + insert_sql);
            SQLiteStatement statement = db.compileStatement(insert_sql);
            JSONObject jsonObject = null;
            int count_array = jsonArray.length();
            MyApplication.logi(LOG_TAG, "COUNT ISS->" + count_array);
            try {
                for (int i = 0; i < count_array; i++) {


                    jsonObject = jsonArray.getJSONObject(i);
                    statement.clearBindings();
                    statement.bindString(1, jsonObject.getString(COL_ID));
                    statement.bindString(2, jsonObject.getString(COL_ORDER_ID));
                    statement.bindString(3, jsonObject.getString(COL_ITEM_ID));
                    statement.bindString(4, jsonObject.getString(COL_ITEM_NAME));
                    statement.bindString(5, jsonObject.getString(COL_QUANTITY_ONE));
                    statement.bindString(6, jsonObject.getString(COL_QUANTITY_TWO));
                    statement.bindString(7, jsonObject.getString(COL_RATE));
                    statement.bindString(8, jsonObject.getString(COL_DISCOUNTED_PRICE_OF_CASE));
                    statement.bindString(9, jsonObject.getString(COL_AMOUNT));
                    statement.bindString(10, jsonObject.getString(COL_FREE_QUANTITY_ONE));
                    statement.bindString(11, jsonObject.getString(COL_FREE_QUANTITY_TWO));


                    statement.execute();
                }
                MyApplication.logi(LOG_TAG, "EndTime->");
                db.setTransactionSuccessful();
                db.endTransaction();

            } catch (JSONException e) {
                MyApplication.logi(LOG_TAG, "JSONException TABLE_TEMP_ORDER_DETAILS--->" + e.getMessage());

            }
        } catch (Exception e) {
            MyApplication.logi(LOG_TAG, " TABLE_TEMP_ORDER_DETAILS exception--->" + e.getMessage());
        }
    }


    public static int insertOrderDetails(String order_id,
                                         String str_item_id,
                                         String str_item_name,
                                         String str_quantity1,
                                         String str_quantity2,
                                         String discounted_price_of_btl,
                                         String discounted_price_of_case,
                                         float str_amt,
                                         String free_btls,
                                         String free_cases,
                                         String flag) {

        MyApplication.logi(LOG_TAG, "str_quantity1------->" + str_quantity1);
        MyApplication.logi(LOG_TAG, "str_quantity2------->" + str_quantity2);
        MyApplication.logi(LOG_TAG,"AMTTTTT");
        MyApplication.logi(LOG_TAG, "in insertOrderDetails");
        SQLiteDatabase db = MyApplication.db.getReadableDatabase();
        ContentValues initialValues1 = new ContentValues();


        initialValues1.put(COL_ORDER_ID, order_id);
        initialValues1.put(COL_ITEM_ID, str_item_id);
        initialValues1.put(COL_ITEM_NAME, str_item_name);
        initialValues1.put(COL_QUANTITY_ONE, str_quantity1);
        initialValues1.put(COL_QUANTITY_TWO, str_quantity2);
        initialValues1.put(COL_RATE, discounted_price_of_btl); /////discounted_price_of_BTL
        initialValues1.put(COL_DISCOUNTED_PRICE_OF_CASE, discounted_price_of_case);  /////discounted_price_of_case

        initialValues1.put(COL_AMOUNT, str_amt);
        initialValues1.put(COL_FREE_QUANTITY_ONE, free_cases);
        initialValues1.put(COL_FREE_QUANTITY_TWO, free_btls);
        //initialValues1.put(COL_AMOUNT, str_amt);
        MyApplication.logi(LOG_TAG, "TempOrderDetails->" + initialValues1);


        if (flag == "insert_data") {
            MyApplication.logi(LOG_TAG, "IN INSERT");
            long ret = db.insert(NAME, null, initialValues1);

            MyApplication.logi(LOG_TAG, "in TempOrderDetails ret->" + ret);
            if (ret > 0) {
                MyApplication.logi(LOG_TAG, "Successfull in TempOrderDetails ret ->" + ret);
                return 0;
            } else {
                MyApplication.logi(LOG_TAG, "Not successfiull in TempOrderDetails ret->" + ret);
                return 1;

            }
        } else if (flag == "update_data") {
            MyApplication.logi(LOG_TAG, "IN UPDATE");
            long ret1 = db.update(NAME, initialValues1, "ItemID=? and OrderID=?", new String[]{str_item_id, order_id});
            MyApplication.logi(LOG_TAG, "in TempOrderDetails ret->" + ret1);
            if (ret1 > 0) {
                MyApplication.logi(LOG_TAG, "UPDATED Successfull in TempOrderDetails ret ->" + ret1);
                return 0;
            } else {
                MyApplication.logi(LOG_TAG, "UPDATED Not successfiull in TempOrderDetails ret->" + ret1);
                return 1;

            }


        }
        return -1;

    }


    public static ArrayList<OrderReviewModel> getOrders(String order_id) {
        MyApplication.logi(LOG_TAG, "in getOrders");
        String product_name;
        byte[] product_img_path;
        String no_of_cases;
        String no_of_bottels;
        String no_of_cases_free;
        String no_of_btls_free;
        String item_id;

        double amt;
        float discounted_single_btl_rate = 0.f, discounted_single_case_rate;

        SQLiteDatabase db = MyApplication.db.getReadableDatabase();
        ArrayList<OrderReviewModel> res = new ArrayList<>();


        String query = "SELECT RetailerImages.ImageDetails AS iImagePath, tod.Amount as Amountd,* FROM TempOrderDetails tod" +
                " ,TempRetailerOrderMaster tom LEFT  JOIN RetailerImages" +
                "  on cast(tod.ItemID as int) = RetailerImages.ImageId where tom.OrderID = tod.OrderID AND tom.OrderId ='" + order_id + "'";

       /* String query = "SELECT RetailerImages.ImageDetails AS iImagePath, tod.Amount as Amountd,* FROM TempOrderDetails tod" +
                " ,TempRetailerOrderMaster tom LEFT  JOIN RetailerImages" +
                "  on cast(tod.ItemID as int) = RetailerImages.ImageId where tom.OrderID = tod.OrderID AND tom.OrderId ='" + order_id + "' AND tom.DistributorID = '" + Dist_id + "'";
*/
        //tom.DistributorID=Dist_id

       /* String query = "SELECT RetailerImages.ImageDetails AS iImagePath, tod.Amount as Amountd,* FROM TempOrderDetails tod ,TempRetailerOrderMaster tom,RetailerImages" +
                " where tom.OrderID = tod.OrderID AND tom.OrderId ='" + order_id + "' AND tod.ItemID = RetailerImages.ImageId";
*/

        MyApplication.logi(LOG_TAG, "QUERY FOR getOrders IS-->" + query);
        try {
            Cursor c = db.rawQuery(query, null);
            int count = c.getCount();
            if (count > 0) {
                c.moveToFirst();
                do {
                    product_name = c.getString(c.getColumnIndexOrThrow("item_Name"));
                    product_img_path = c.getBlob(c.getColumnIndexOrThrow("iImagePath"));
                    no_of_cases = c.getString(c.getColumnIndexOrThrow("LargeUnit"));

                    no_of_bottels = c.getString(c.getColumnIndexOrThrow("SmallUnit"));
                   /* no_of_cases_free = c.getString(c.getColumnIndexOrThrow("SmallUnit"));
                    no_of_btls_free = c.getString(c.getColumnIndexOrThrow("SmallUnit"));*/
                    item_id = c.getString(c.getColumnIndexOrThrow("ItemID"));
                    amt = c.getDouble(c.getColumnIndexOrThrow("Amountd"));
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
                    MyApplication.logi(LOG_TAG, "PREVIEW Model---->" + previewModel);
                    res.add(previewModel);
                } while (c.moveToNext());

            }
            c.close();
        } catch (Exception e) {
            MyApplication.logi(LOG_TAG, "getOrders in Exception" + e.getMessage());
        }

        return res;
    }






    public static ArrayList<OrderReviewModel> getOrdersnew(String order_id,String Dist_id) {
        MyApplication.logi(LOG_TAG, "in getOrders");
        String product_name;
        byte[] product_img_path;
        String no_of_cases;
        String no_of_bottels;
        String no_of_cases_free;
        String no_of_btls_free;
        String item_id;
        String DiscountType;
        String DiscountRate;

        double amt;
        float discounted_single_btl_rate = 0.f, discounted_single_case_rate;

        SQLiteDatabase db = MyApplication.db.getReadableDatabase();
        ArrayList<OrderReviewModel> res = new ArrayList<>();


       /* String query = "SELECT RetailerImages.ImageDetails AS iImagePath, tod.Amount as Amountd,* FROM TempOrderDetails tod" +
                " ,TempRetailerOrderMaster tom LEFT  JOIN RetailerImages" +
                "  on cast(tod.ItemID as int) = RetailerImages.ImageId where tom.OrderID = tod.OrderID AND tom.OrderId ='" + order_id + "'";
*/

    /*    String query = "SELECT RetailerImages.ImageDetails AS iImagePath, tod.Amount as Amountd,* FROM TempOrderDetails tod" +
                " ,TempRetailerOrderMaster tom LEFT  JOIN RetailerImages" +
                "  on cast(tod.ItemID as int) = RetailerImages.ImageId where tom.OrderID = tod.OrderID AND tom.OrderId ='" + order_id + "' AND tom.DistributorID = '" + Dist_id + "'";
*/
      String query = "SELECT RetailerImages.ImageDetails AS iImagePath, tod.Amount as Amountd,p.SalesRate,p.DiscoutType,p.DiscountRate,* FROM TempOrderDetails tod ," +
              "TempRetailerOrderMaster tom,PriceListDetails p LEFT  JOIN RetailerImages  on cast(tod.ItemID as int) = RetailerImages.ImageId " +
              "where tom.OrderID = tod.OrderID AND tom.OrderId ='"+ order_id +"' " +
              "and p.PriceListID = '"+ Dist_id +"' and p.ItemID =  tod.ItemID ";

        //tom.DistributorID=Dist_id

       /* String query = "SELECT RetailerImages.ImageDetails AS iImagePath, tod.Amount as Amountd,* FROM TempOrderDetails tod ,TempRetailerOrderMaster tom,RetailerImages" +
                " where tom.OrderID = tod.OrderID AND tom.OrderId ='" + order_id + "' AND tod.ItemID = RetailerImages.ImageId";
*/

        MyApplication.logi(LOG_TAG, "QUERY FOR getOrders IS-->" + query);
        try {
            Cursor c = db.rawQuery(query, null);
            int count = c.getCount();
            if (count > 0) {
                c.moveToFirst();
                do {
                    product_name = c.getString(c.getColumnIndexOrThrow("item_Name"));
                    product_img_path = c.getBlob(c.getColumnIndexOrThrow("iImagePath"));
                    no_of_cases = c.getString(c.getColumnIndexOrThrow("LargeUnit"));

                    no_of_bottels = c.getString(c.getColumnIndexOrThrow("SmallUnit"));
                   /* no_of_cases_free = c.getString(c.getColumnIndexOrThrow("SmallUnit"));
                    no_of_btls_free = c.getString(c.getColumnIndexOrThrow("SmallUnit"));*/
                    item_id = c.getString(c.getColumnIndexOrThrow("ItemID"));
                    amt = c.getDouble(c.getColumnIndexOrThrow("Amountd"));
                    String amt1 = String.format("%.2f", amt);

                    discounted_single_btl_rate = c.getFloat(c.getColumnIndexOrThrow("SalesRate"));
                    discounted_single_case_rate = c.getFloat(c.getColumnIndexOrThrow("SalesRate"));
                   DiscountRate = c.getString(c.getColumnIndexOrThrow("DiscountRate"));
                   DiscountType = c.getString(c.getColumnIndexOrThrow("DiscoutType"));


                    String total_after_edit = "0";

                    MyApplication.logi(LOG_TAG, "product_name---->" + product_name);
                    MyApplication.logi(LOG_TAG, "no_of_cases---->" + no_of_cases);
                    MyApplication.logi(LOG_TAG, "no_of_bottels---->" + no_of_bottels);
                    MyApplication.logi(LOG_TAG, "item_id---->" + item_id);
                    MyApplication.logi(LOG_TAG, "Amount---->" + amt1);
                    MyApplication.logi(LOG_TAG, "single_btl_rate---->" + discounted_single_btl_rate);
                    MyApplication.logi(LOG_TAG, "discounted_single_case_rate---->" + discounted_single_case_rate);

                    OrderReviewModel previewModel = new OrderReviewModel(item_id, product_name, product_img_path, no_of_cases, no_of_bottels, "0", "0", amt1, discounted_single_btl_rate, discounted_single_case_rate, total_after_edit,DiscountRate,DiscountType);
                    MyApplication.logi(LOG_TAG, "PREVIEW Model---->" + previewModel);
                    res.add(previewModel);
                } while (c.moveToNext());

            }
            c.close();
        } catch (Exception e) {
            MyApplication.logi(LOG_TAG, "getOrders in Exception" + e.getMessage());
        }

        return res;
    }



    public static JSONObject getData(JSONObject jsonObject) {
        JSONArray jsonArray_master = new JSONArray();
        MyApplication.logi(LOG_TAG, "IN JSONObject getDat ");
        SQLiteDatabase db = MyApplication.db.getReadableDatabase();
            //  String query = "SELECT * FROM " + NAME+ " where OrderID = '"+MyApplication.get_session(MyApplication.SESSION_ORDER_ID)+"'";
        String query = "SELECT * FROM TempOrderDetails ";

        MyApplication.logi(LOG_TAG, "query getData-------->" + query);
        Cursor c = db.rawQuery(query, null);
        String retailerId = TABLE_PCUSTOMER.getCustId();
        MyApplication.logi(LOG_TAG, "retailerId-------->" + retailerId);
        JSONArray jsonArray_details_master;
        JSONArray jsonArray_details = new JSONArray();
        int count = c.getCount();
        MyApplication.logi(LOG_TAG, "count="+count);
//String DistributorId=TABLE_TEMP_RETAILER_ORDER_MASTER.getDistId();
        try {
            if (c.getCount() > 0) {
                c.moveToFirst();
                do {
                    JSONObject jsonObject1 = new JSONObject();
                    String order_Id = c.getString(c.getColumnIndexOrThrow("OrderID"));
                    jsonObject1.put("OrderId", order_Id);
                  //  jsonObject1.put("DistributorId", DistributorId);
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


    public static long deletePreviewOrder(String itemId, String orderId) {
        MyApplication.logi(LOG_TAG, "deletePreviewOrder-->itemit-->" + itemId + "ordeerid-->" + orderId);
        SQLiteDatabase db = MyApplication.db.getReadableDatabase();
        long ret = db.delete(NAME, COL_ITEM_ID + " =? AND "
                + COL_ORDER_ID + " =? ", new String[]{"" + itemId, "" + orderId});
        MyApplication.logi(LOG_TAG, "deletePreviewOrder" + ret);


        return ret;
    }

    public static JSONArray getDetailsData(String order_ID, JSONArray jsonArray_details) {

        // JSONArray jsonArray_details = new JSONArray();
        MyApplication.logi(LOG_TAG, "IN JSONObject getDetailsData ");
        double amt, rate;

        SQLiteDatabase db = MyApplication.db.getReadableDatabase();
        String query = "SELECT * FROM " + NAME + " WHERE " + COL_ORDER_ID + "='" + order_ID + "'";
        Cursor c = db.rawQuery(query, null);
        int countt = c.getCount();
        MyApplication.logi(LOG_TAG, "COUNT FOR CARTS-->" + countt);
        MyApplication.set_session(MyApplication.SESSION_SUBMIT_ORDER_CARTS_COUNT, countt + "");
        try {
            if (c.getCount() > 0) {
                c.moveToFirst();
                do {
                    JSONObject jsonObject = new JSONObject();

                    jsonObject.put("OrderId", c.getString(c.getColumnIndexOrThrow(COL_ORDER_ID)));
                    jsonObject.put("ItemID", c.getString(c.getColumnIndexOrThrow(COL_ITEM_ID)));
                    jsonObject.put("LargeUnitQty", c.getString(c.getColumnIndexOrThrow(COL_QUANTITY_ONE)));
                    jsonObject.put("SmallUnitQty", c.getString(c.getColumnIndexOrThrow(COL_QUANTITY_TWO)));
                    /*jsonObject.put("FreeLargeUnitQty", c.getString(c.getColumnIndexOrThrow(COL_FREE_QUANTITY_ONE)));
                    jsonObject.put("FreeSmallUnitQty", c.getString(c.getColumnIndexOrThrow(COL_FREE_QUANTITY_TWO)));*/


                    jsonObject.put("FreeLargeUnitQty", "0");
                    jsonObject.put("FreeSmallUnitQty", "0");


                    amt = c.getDouble(c.getColumnIndexOrThrow(COL_AMOUNT));
                    String amt1 = String.format("%.2f", amt);


                    rate = c.getDouble(c.getColumnIndexOrThrow(COL_RATE));
                    String rate1 = String.format("%.2f", rate);


                    jsonObject.put("Rate", rate1);
                    jsonObject.put("Amount", amt1);


/*
                    jsonObject.put("Rate", c.getString(c.getColumnIndexOrThrow(COL_RATE)));
                    jsonObject.put("Amount", c.getString(c.getColumnIndexOrThrow(COL_AMOUNT)));*/


                    jsonArray_details.put(jsonObject);

                } while (c.moveToNext());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonArray_details;
    }



    public static String getSumOfAllItemsnew(String orderId) {
        String total = "0.0";
        String queery;
        SQLiteDatabase db = MyApplication.db.getReadableDatabase();
        //queery = "SELECT SUM(TempOrderDetails.Amount) as TOTAL FROM " + NAME + " where OrderID = " + orderId;
        queery = "SELECT SUM(TempOrderDetails.Amount) as TOTAL FROM " + NAME ; //+ " where OrderID = " + orderId;

        Cursor cur = db.rawQuery(queery, null);
        MyApplication.logi(LOG_TAG, "getSumOfAllItems(), queery" + queery);
        if (cur.getCount() > 0) {
            cur.moveToFirst();
            total = cur.getString(cur.getColumnIndexOrThrow("TOTAL"));
        }
        MyApplication.logi(LOG_TAG, "getSumOfAllItems(), total------------>" + total);
        return total;
    }


    public static String getSumOfAllItems(String orderId) {
        MyApplication.logi(LOG_TAG, "IN getSumOfAllItems ");
        String total = null;
        String queery;
        SQLiteDatabase db = MyApplication.db.getReadableDatabase();
        queery = "SELECT SUM(TempOrderDetails.Amount) as TOTAL FROM " + NAME + " where OrderID = " + orderId;


        Cursor cur = db.rawQuery(queery, null);
        MyApplication.logi(LOG_TAG, "queery" + queery);
        if (cur.getCount() > 0) {
            cur.moveToFirst();
            total = cur.getString(cur.getColumnIndexOrThrow("TOTAL"));
            MyApplication.logi(LOG_TAG, "IN total------------>" + total);


        }
        return total;
    }


    public static int check_Item_is_Already_Present(String orderId, String itemId) {

        MyApplication.logi(LOG_TAG, "in check_Item_is_Already_Present");
        SQLiteDatabase db = MyApplication.db.getReadableDatabase();
        ContentValues initialValues1 = new ContentValues();
        String query = "SELECT * FROM  TempOrderDetails where OrderID = " + orderId + " and ItemID = " + itemId;
        Cursor c = db.rawQuery(query, null);


        int count = c.getCount();
        if (count > 0) {
            c.moveToFirst();

            MyApplication.logi(LOG_TAG, "item is present I.E already saved to cart before");
            return 1;

        } else {
            MyApplication.logi(LOG_TAG, "NEW save to cart ");
            return 0;
        }


    }


    public static ArrayList<SubItemDataModel> getListOfAlreadySavedToCartItems(String OrderId) {
        SQLiteDatabase db = MyApplication.db.getReadableDatabase();
        ArrayList<SubItemDataModel> res = new ArrayList<>();

        String itemId = "";
       // String qury = "SELECT * FROM TempOrderDetails WHERE OrderID";

        String qury = "SELECT * FROM TempOrderDetails WHERE OrderID ="+OrderId;         //ORDER ID CHECKED ON 10/04 BCZ THE SAME ITEMS WHICH ARE ADDED TO CART ARE GETTING FOR ALL THE RETAILERS AS THOSE RETAILERS HAVE THOSE ITEMS
        Cursor c = db.rawQuery(qury, null);
        if (c.getCount() > 0) {

            c.moveToFirst();
            do {

               /* (String offer, String discount_type, float bottle_price, float case_value,
                int item_id, String sub_item_name, String offer_tagline,
                        String total_price, String no_of_free_case, String no_of_free_bottle,
                        String product_image_path, float item_total, String cases, String bottles)*/


               /* itemId = c.getString(c.getColumnIndexOrThrow("ItemID"));*/
                res.add(new SubItemDataModel("", "", 0.f, 0.f,
                        c.getInt(c.getColumnIndexOrThrow("ItemID")),
                        c.getString(c.getColumnIndexOrThrow("item_Name")),
                        "",
                        c.getString(c.getColumnIndexOrThrow("Amount")),
                        c.getString(c.getColumnIndexOrThrow("FreeLargeUnitQty")),
                        c.getString(c.getColumnIndexOrThrow("FreeSmallUnitQty")),
                        null,
                        0.f,
                        c.getString(c.getColumnIndexOrThrow("LargeUnit")),
                        c.getString(c.getColumnIndexOrThrow("SmallUnit"))));


                MyApplication.logi(LOG_TAG, "resss isssssss------------>" + res);
            }
            while (c.moveToNext());
        }
        if (c != null) {
            c.close();
        }
        MyApplication.logi(LOG_TAG, "resppppp of getListOfAlreadySavedToCartItems----> " + res);
        return res;


    }


    public static void deleteAllRecords(String orderId) {

        MyApplication.logi(LOG_TAG, "deleteAllRecords for orderdetails");
        SQLiteDatabase db = MyApplication.db.getWritableDatabase();
        int ret = db.delete(NAME, "OrderID =?", new String[]{orderId});


        if (ret > 0) {
            MyApplication.logi(LOG_TAG, "orderdetails DELETED SUCCESSFULLY" + ret);

        } else {
            MyApplication.logi(LOG_TAG, "orderdetails DELETED UNSUCCESSFULLY" + ret);
        }
    }


    public static int countOfAddToCardItems(String OrderID) {
        MyApplication.logi(LOG_TAG, "IN count countOfAddToCardItems");
        String query = "";
        int resp = 0;
        SQLiteDatabase db = MyApplication.db.getWritableDatabase();
        query = "SELECT COUNT(ID) as count FROM TempOrderDetails where OrderID ="+OrderID;
        MyApplication.logi(LOG_TAG, "IN count countOfAddToCardItems query--->"+query+"OrderID-->"+OrderID);
        Cursor c = db.rawQuery(query, null);
        if (c.getCount() > 0) {

            c.moveToFirst();
            do {
                resp = c.getInt(c.getColumnIndexOrThrow("count"));
                MyApplication.logi(LOG_TAG, "IN count countOfAddToCardItems resp-->>"+resp);
            } while (c.moveToNext());

        }
        return resp;
    }



    public static boolean istrue(String orderId) {

        MyApplication.logi(LOG_TAG, "in istrue");
        int count;
        boolean resp =false ;
        MyApplication.logi(LOG_TAG, "in istrue()" + orderId);
        SQLiteDatabase db = MyApplication.db.getWritableDatabase();
        //String query = "SELECT * from "+NAME+" where "+"PriceListID" +" =" + dist_id+"";
        // String query = "SELECT DISTINCT SalesRate from PriceListDetails where ItemID =" + item_id;
        // String query =   "select SalesRate from PriceListDetails where ItemID=item_id and PriceListID=dist_id";

        String query = "select * from TempOrderDetails where TempOrderDetails.OrderID = 'orderId'and (TempOrderDetails.LargeUnit > 0 or TempOrderDetails.SmallUnit > 0)";
        MyApplication.logi("", "In getSalesRate query :" + query);
        Cursor c = db.rawQuery(query, null);
        count = c.getCount();
        MyApplication.logi(LOG_TAG, "COUNT OF istrue IS-->" + count);
        if (count > 0) {
            resp= true;
         //   c.moveToFirst();
            /*do {
                try {
                    resp = Integer.parseInt(c.getString(c.getColumnIndexOrThrow("SalesRate")));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                MyApplication.logi(LOG_TAG, "RESPPPPP-->" + resp);
            } while (c.moveToNext());*/
        }
        else{
            resp=false;
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