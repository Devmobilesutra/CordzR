package com.sapl.retailerorderingmsdpharma.MyDatabase;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.sapl.retailerorderingmsdpharma.activities.MyApplication;
import com.sapl.retailerorderingmsdpharma.models.OrderDeliveryStatusModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by MRUNAL on 08/02/2018.
 */

public class TABLE_RETAILER_ORDER_MASTER {
    public static String LOG_TAG = "TABLE_RETAILER_ORDER_MASTER";
    public static String NAME = "RetailerOrderMaster";


    public static String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
            + NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, OrderId TEXT, DistributorID " +
            "INTEGER,RetailerID INTEGER,OrderDate DATE, " +
            "TotalAmount NUMERIC, OrderStatus INTEGER, OrderRemarks TEXT, OrderRating TINYINT, UserID INTEGER)";


    public static void insert_bulk_RetailerOrderMaster(JSONArray jsonArray) {
        try {
            MyApplication.logi(LOG_TAG, "in insert_bulk_RetailerOrderMaster->");
            SQLiteDatabase db = MyApplication.db.getWritableDatabase();
            db.beginTransaction();


            String insert_sql = "insert into " + NAME + " (" + "ID,OrderId,DistributorID,RetailerID,OrderDate" +
                    "TotalAmount,OrderStatus,OrderRemarks,OrderRating,UserID) VALUES(?,?,?,?,?,?,?,?,?,?)";
            MyApplication.logi(LOG_TAG, "insert_sql" + insert_sql);
            SQLiteStatement statement = db.compileStatement(insert_sql);
            JSONObject jsonObject = null;
            int count_array = jsonArray.length();
            MyApplication.logi(LOG_TAG, "COUNT ISS insert_bulk_RetailerOrderMaster->" + count_array);
            try {
                for (int i = 0; i < count_array; i++) {


                    jsonObject = jsonArray.getJSONObject(i);

                    statement.clearBindings();
                    statement.bindString(1, jsonObject.getString("ID"));
                    statement.bindString(2, jsonObject.getString("OrderId"));
                    statement.bindString(3, jsonObject.getString("DistributorID"));
                    statement.bindString(4, jsonObject.getString("RetailerID"));
                    statement.bindString(5, jsonObject.getString("OrderDate"));

                    statement.bindString(6, jsonObject.getString("TotalAmount"));
                    statement.bindString(7, jsonObject.getString("OrderStatus"));
                    statement.bindString(8, jsonObject.getString("OrderRemarks"));
                    statement.bindString(9, jsonObject.getString("OrderRating"));
                    statement.bindString(10, jsonObject.getString("UserID"));

                    statement.execute();
                }
                MyApplication.logi(LOG_TAG, "EndTime insert_bulk_RetailerOrderMaster->");
                db.setTransactionSuccessful();
                db.endTransaction();


            } catch (JSONException e) {
                MyApplication.logi(LOG_TAG, "JSONException insert_bulk_RetailerOrderMaster--->" + e.getMessage());

            }
        } catch (
                Exception e)

        {
            MyApplication.logi(LOG_TAG, " insert_bulk_RetailerOrderMaster exception--->" + e.getMessage());
        }
    }


    public static int insertOrderMaster(String orderId,
                                        String distributorID,
                                        String retailerID,
                                        String orderDate,
                                        String amount,
                                        String orderStatus,
                                        String orderRemarks,
                                        String orderRating
    ) {

        MyApplication.logi(LOG_TAG, "irn insert final Order Dta in Oder Master");
        SQLiteDatabase db = MyApplication.db.getReadableDatabase();
        String total_amt = TABLE_TEMP_ORDER_DETAILS.getSumOfAllItems(orderId);
        ContentValues initialValues1 = new ContentValues();

        initialValues1.put("OrderId", orderId);
        initialValues1.put("DistributorID", distributorID);
        initialValues1.put("RetailerID", retailerID);
        initialValues1.put("OrderDate", orderDate);
        initialValues1.put("TotalAmount", total_amt);
        initialValues1.put("OrderStatus", orderStatus);
        initialValues1.put("OrderRemarks", orderRemarks);
        initialValues1.put("OrderRating", orderRating);

        MyApplication.logi(LOG_TAG, "insertOrderMaster->" + initialValues1);


        long ret = db.insert(NAME, null, initialValues1);
        MyApplication.logi(LOG_TAG, "in insertOrderMaster ret->" + ret);
        if (ret > 0) {
            MyApplication.logi(LOG_TAG, "Successfull in insertOrderMaster ret ->" + ret);
            return 0;
        } else {
            MyApplication.logi(LOG_TAG, "Not successfiull in insertOrderMaster ret->" + ret);
            return 1;

        }

    }

    //for aall status list i.e combined status
    public static List<OrderDeliveryStatusModel> getOrderStatusList() {
        List<OrderDeliveryStatusModel> orderStatusList = new ArrayList<>();
        SQLiteDatabase db = MyApplication.db.getWritableDatabase();
        MyApplication.logi(LOG_TAG, "in getOrderStatusList()");


        String query = "SELECT r.OrderId as OrderId,r.OrderStatus as status,r.TotalAmount as Amount,s.StatusDateTime as Date, " +
                "p.Distributor as Name ,p.OUTLETINFO from RetailerOrderMaster r ,PDistributor p ,OrderStatus s  " +
                " where p.DistributorId = r.DistributorId  and s.Statusid = r.OrderStatus and r.orderId = s.OrderId ORDER BY s.StatusDateTime desc";


        MyApplication.logi("FOR ALL STATUS ", "FOR ALL In tableHasData query :" + query);
        Cursor c = db.rawQuery(query, null);
        MyApplication.logi("getOrderStatusList() ", "COUNT:  " + c.getCount());
        if (c.getCount() > 0) {
            c.moveToFirst();
            do {

                // String distributorId = c.getString(c.getColumnIndexOrThrow("DistributorID"));
                String distributor_name = c.getString(c.getColumnIndexOrThrow("Name"));
                String OrderStatus = c.getString(c.getColumnIndexOrThrow("status"));
                String OrderDate = c.getString(c.getColumnIndexOrThrow("Date"));
                MyApplication.logi(LOG_TAG, "Order date-->" + OrderDate);


                String address = c.getString(c.getColumnIndexOrThrow("OUTLETINFO")).trim();
                String contctInfo = c.getString(c.getColumnIndexOrThrow("OUTLETINFO")).trim();


                String address1 = splitLocation(address);
                String contact_no_land = spiltContact(contctInfo);
                MyApplication.logi(LOG_TAG, "contact_no_land------>" + contact_no_land);
                String contact_no_mob = spiltContact_mob(contctInfo);
                MyApplication.logi(LOG_TAG, "contact_no_mob------>" + contact_no_mob);

                String info_address = spiltAddressFull(address);
                MyApplication.logi(LOG_TAG, "info_address------>" + info_address);


                String OrderID = c.getString(c.getColumnIndexOrThrow("OrderId"));
                String Amount = c.getString(c.getColumnIndexOrThrow("Amount"));
                String cart_count = "1";

                OrderDeliveryStatusModel model = new OrderDeliveryStatusModel(distributor_name, OrderStatus, OrderDate, OrderID, Amount, cart_count, address1, contact_no_land, contact_no_mob, address);
                orderStatusList.add(model);

            } while (c.moveToNext());
        }
        db.close();
        MyApplication.logi("JARVIS", " model orderStatusList -->>" + orderStatusList.toString());
        return orderStatusList;
    }


    //for specific status list
    public static List<OrderDeliveryStatusModel> getOrderStatusList_new(String status) {
        MyApplication.logi(LOG_TAG, "STATUS IS--->" + status);
        List<OrderDeliveryStatusModel> orderStatusList = new ArrayList<>();
        SQLiteDatabase db = MyApplication.db.getWritableDatabase();
        MyApplication.logi(LOG_TAG, "in getOrderStatusListPending()");


        String query = "SELECT r.OrderId as OrderId,r.OrderStatus as status,r.TotalAmount as Amount,r.OrderDate as Date, " +
                "p.Distributor as Name,p.OUTLETINFO from RetailerOrderMaster r ,PDistributor p ,OrderStatus s   where p.DistributorId = r.DistributorId and OrderStatus =" + status + " ORDER BY OrderDate asc";


        MyApplication.logi("getOrderStatusList ", "In tableHasData query individual :" + query);
        Cursor c = db.rawQuery(query, null);
        MyApplication.logi("getOrderStatusList() ", "COUNT:  " + c.getCount());
        if (c.getCount() > 0) {
            c.moveToFirst();
            do {

                // String distributorId = c.getString(c.getColumnIndexOrThrow("DistributorID"));
                String distributor_name = c.getString(c.getColumnIndexOrThrow("Name"));
                String OrderStatus = c.getString(c.getColumnIndexOrThrow("status"));
                String OrderDate = c.getString(c.getColumnIndexOrThrow("Date"));
                MyApplication.logi(LOG_TAG, "DATEE IN ROD-->" + OrderDate);


                String address = c.getString(c.getColumnIndexOrThrow("OUTLETINFO")).trim();
                String contctInfo = c.getString(c.getColumnIndexOrThrow("OUTLETINFO")).trim();


                String address1 = splitLocation(address);
                String contact_no_land = spiltContact(contctInfo);
                MyApplication.logi(LOG_TAG, "contact_no_land------>" + contact_no_land);
                String contact_no_mob = spiltContact_mob(contctInfo);
                MyApplication.logi(LOG_TAG, "contact_no_mob------>" + contact_no_mob);

                String info_address = spiltAddressFull(address);
                MyApplication.logi(LOG_TAG, "info_address------>" + info_address);


                String OrderID = c.getString(c.getColumnIndexOrThrow("OrderId"));
                String Amount = c.getString(c.getColumnIndexOrThrow("Amount"));
                String cart_count = "1";
                OrderDeliveryStatusModel model = new OrderDeliveryStatusModel(distributor_name, OrderStatus, OrderDate, OrderID, Amount, cart_count, address1, contact_no_land, contact_no_mob, address);
                orderStatusList.add(model);


            } while (c.moveToNext());
        }
        db.close();
        MyApplication.logi("JARVIS", " model getOrderStatusListPending -->>" + orderStatusList.toString());
        return orderStatusList;
    }


    //accecpeted,rejected,pending as per shop
    public static List<OrderDeliveryStatusModel> getStatusListAsPerShop(String status) {
        MyApplication.logi(LOG_TAG, "getStatusListAsPerShop STATUS IS--->" + status);
        List<OrderDeliveryStatusModel> orderStatusList = new ArrayList<>();
        SQLiteDatabase db = MyApplication.db.getWritableDatabase();
        MyApplication.logi(LOG_TAG, "in getStatusListAsPerShop()");


        String query = "SELECT r.OrderId as OrderId,r.OrderStatus as status,r.TotalAmount as Amount,r.OrderDate as Date, " +
                "p.Distributor as Name,p.OUTLETINFO from RetailerOrderMaster r ,PDistributor p   " +
                "where p.DistributorId = r.DistributorId and OrderStatus =" + status + " ORDER BY  p.Distributor desc";


        MyApplication.logi("getStatusListAsPerShop ", "In tableHasData query individual :" + query);
        Cursor c = db.rawQuery(query, null);
        MyApplication.logi("getOrderStatusList() ", "COUNT:  " + c.getCount());
        if (c.getCount() > 0) {
            c.moveToFirst();
            do {

                // String distributorId = c.getString(c.getColumnIndexOrThrow("DistributorID"));
                String distributor_name = c.getString(c.getColumnIndexOrThrow("Name"));
                String OrderStatus = c.getString(c.getColumnIndexOrThrow("status"));
                String OrderDate = c.getString(c.getColumnIndexOrThrow("Date"));
                MyApplication.logi(LOG_TAG, "DATEE IN ROD-->" + OrderDate);


                String address = c.getString(c.getColumnIndexOrThrow("OUTLETINFO")).trim();
                String contctInfo = c.getString(c.getColumnIndexOrThrow("OUTLETINFO")).trim();


                String address1 = splitLocation(address);
                String contact_no_land = spiltContact(contctInfo);
                MyApplication.logi(LOG_TAG, "contact_no_land------>" + contact_no_land);
                String contact_no_mob = spiltContact_mob(contctInfo);
                MyApplication.logi(LOG_TAG, "contact_no_mob------>" + contact_no_mob);

                String info_address = spiltAddressFull(address);
                MyApplication.logi(LOG_TAG, "info_address------>" + info_address);


                String OrderID = c.getString(c.getColumnIndexOrThrow("OrderId"));
                String Amount = c.getString(c.getColumnIndexOrThrow("Amount"));
                String cart_count = "1";
                OrderDeliveryStatusModel model = new OrderDeliveryStatusModel(distributor_name, OrderStatus, OrderDate, OrderID, Amount, cart_count, address1, contact_no_land, contact_no_mob, address);
                orderStatusList.add(model);


            } while (c.moveToNext());
        }
        db.close();
        MyApplication.logi("JARVIS", " model getStatusListAsPerShop -->>" + orderStatusList.toString());
        return orderStatusList;
    }

//status list as per date
    public static List<OrderDeliveryStatusModel> getStatusListAsPerCalender(String status) {
        MyApplication.logi(LOG_TAG, "STATUS IS--->" + status);
        List<OrderDeliveryStatusModel> orderStatusList = new ArrayList<>();
        SQLiteDatabase db = MyApplication.db.getWritableDatabase();
        MyApplication.logi(LOG_TAG, "in getStatusListAsPerCalender()");


        String query = "SELECT r.OrderId as OrderId,r.OrderStatus as status,r.TotalAmount as Amount,r.OrderDate as Date, " +
                "p.Distributor as Name,p.OUTLETINFO from RetailerOrderMaster r ,PDistributor p   " +
                "where p.DistributorId = r.DistributorId and OrderStatus =" + status + " ORDER BY  r.OrderDate asc";


        MyApplication.logi("getStatusListAsPerCalender ", "In tableHasData query individual :" + query);
        Cursor c = db.rawQuery(query, null);
        MyApplication.logi("getStatusListAsPerCalender() ", "COUNT:  " + c.getCount());
        if (c.getCount() > 0) {
            c.moveToFirst();
            do {

                // String distributorId = c.getString(c.getColumnIndexOrThrow("DistributorID"));
                String distributor_name = c.getString(c.getColumnIndexOrThrow("Name"));
                String OrderStatus = c.getString(c.getColumnIndexOrThrow("status"));
                String OrderDate = c.getString(c.getColumnIndexOrThrow("Date"));
                MyApplication.logi(LOG_TAG, "DATEE IN ROD-->" + OrderDate);


                String address = c.getString(c.getColumnIndexOrThrow("OUTLETINFO")).trim();
                String contctInfo = c.getString(c.getColumnIndexOrThrow("OUTLETINFO")).trim();


                String address1 = splitLocation(address);
                String contact_no_land = spiltContact(contctInfo);
                MyApplication.logi(LOG_TAG, "contact_no_land------>" + contact_no_land);
                String contact_no_mob = spiltContact_mob(contctInfo);
                MyApplication.logi(LOG_TAG, "contact_no_mob------>" + contact_no_mob);

                String info_address = spiltAddressFull(address);
                MyApplication.logi(LOG_TAG, "info_address------>" + info_address);


                String OrderID = c.getString(c.getColumnIndexOrThrow("OrderId"));
                String Amount = c.getString(c.getColumnIndexOrThrow("Amount"));
                String cart_count = "1";
                OrderDeliveryStatusModel model = new OrderDeliveryStatusModel(distributor_name, OrderStatus, OrderDate, OrderID, Amount, cart_count, address1, contact_no_land, contact_no_mob, address);
                orderStatusList.add(model);


            } while (c.moveToNext());
        }
        db.close();
        MyApplication.logi("JARVIS", " model getStatusListAsPerCalender -->>" + orderStatusList.toString());
        return orderStatusList;
    }

//status list as per date and shop name
    public static List<OrderDeliveryStatusModel> getStatusListAsPerShopAndCalender(String status) {
        MyApplication.logi(LOG_TAG, "getStatusListAsPerShopAndCalender STATUS IS--->" + status);
        List<OrderDeliveryStatusModel> orderStatusList = new ArrayList<>();
        SQLiteDatabase db = MyApplication.db.getWritableDatabase();
        MyApplication.logi(LOG_TAG, "in getStatusListAsPerShopAndCalender()");


        String query = "SELECT r.OrderId as OrderId,r.OrderStatus as status,r.TotalAmount as Amount,r.OrderDate as Date, " +
                "p.Distributor as Name,p.OUTLETINFO from RetailerOrderMaster r ,PDistributor p   " +
                "where p.DistributorId = r.DistributorId and OrderStatus =" + status + "  ORDER BY  p.Distributor desc,r.OrderDate desc";


        MyApplication.logi("getStatusListAsPerShopAndCalender ", "In tableHasData query individual :" + query);
        Cursor c = db.rawQuery(query, null);
        MyApplication.logi("getStatusListAsPerShopAndCalender() ", "COUNT:  " + c.getCount());
        if (c.getCount() > 0) {
            c.moveToFirst();
            do {

                // String distributorId = c.getString(c.getColumnIndexOrThrow("DistributorID"));
                String distributor_name = c.getString(c.getColumnIndexOrThrow("Name"));
                String OrderStatus = c.getString(c.getColumnIndexOrThrow("status"));
                String OrderDate = c.getString(c.getColumnIndexOrThrow("Date"));
                MyApplication.logi(LOG_TAG, "DATEE IN ROD-->" + OrderDate);


                String address = c.getString(c.getColumnIndexOrThrow("OUTLETINFO")).trim();
                String contctInfo = c.getString(c.getColumnIndexOrThrow("OUTLETINFO")).trim();


                String address1 = splitLocation(address);
                String contact_no_land = spiltContact(contctInfo);
                MyApplication.logi(LOG_TAG, "contact_no_land------>" + contact_no_land);
                String contact_no_mob = spiltContact_mob(contctInfo);
                MyApplication.logi(LOG_TAG, "contact_no_mob------>" + contact_no_mob);

                String info_address = spiltAddressFull(address);
                MyApplication.logi(LOG_TAG, "info_address------>" + info_address);


                String OrderID = c.getString(c.getColumnIndexOrThrow("OrderId"));
                String Amount = c.getString(c.getColumnIndexOrThrow("Amount"));
                String cart_count = "1";
                OrderDeliveryStatusModel model = new OrderDeliveryStatusModel(distributor_name, OrderStatus, OrderDate, OrderID, Amount, cart_count, address1, contact_no_land, contact_no_mob, address);
                orderStatusList.add(model);


            } while (c.moveToNext());
        }
        db.close();
        MyApplication.logi("JARVIS", " model getStatusListAsPerShopAndCalender -->>" + orderStatusList.toString());
        return orderStatusList;
    }


    public static void updateData(JSONArray jsonArray) {
        try {
            MyApplication.logi(LOG_TAG, "in updateData->" + jsonArray);
            SQLiteDatabase db = MyApplication.db.getWritableDatabase();
            db.beginTransaction();


            String insert_sql = "insert into " + NAME + " (" + "OrderId,DistributorID,OrderDate," +
                    "TotalAmount,OrderStatus,OrderRemarks) VALUES(?,?,?,?,?,?)";
            MyApplication.logi(LOG_TAG, "updateData--" + insert_sql);
            SQLiteStatement statement = db.compileStatement(insert_sql);
            JSONObject jsonObject = null;
            int count_array = jsonArray.length();
            MyApplication.logi(LOG_TAG, "COUNT ISS updateData RetailerOrderMaster->" + count_array);
            try {
                MyApplication.logi(LOG_TAG, "updateData insert_sql" + insert_sql);
                MyApplication.logi(LOG_TAG, "updateData COUNT ISS->" + count_array);

                for (int i = 0; i < count_array; i++) {
                    jsonObject = jsonArray.getJSONObject(i);

                    statement.clearBindings();

                    statement.bindString(1, jsonObject.getString("OrderId"));
                    statement.bindString(2, jsonObject.getString("DistributorID"));
                    statement.bindString(3, jsonObject.getString("datetime"));
                    statement.bindString(4, jsonObject.getString("TotalAmount"));
                    statement.bindString(5, jsonObject.getString("OrderStatus"));
                    statement.bindString(6, jsonObject.getString("OrderRemarks"));

                    statement.execute();
                }
                MyApplication.logi(LOG_TAG, "EndTime updateData_RetailerOrderMaster->");
                db.setTransactionSuccessful();
                db.endTransaction();


            } catch (JSONException e) {
                MyApplication.logi(LOG_TAG, "JSONException updateData_RetailerOrderMaster--->" + e.getMessage());

            }
        } catch (
                Exception e)

        {
            MyApplication.logi(LOG_TAG, " updateData_RetailerOrderMaster exception--->" + e.getMessage());
        }

    }


    public static void deleteOrderMaster() {
        MyApplication.logi(LOG_TAG, "in deleteOrderMaster");
        SQLiteDatabase db = MyApplication.db.getWritableDatabase();


        db.delete(NAME, null, null);


    }

    private static String splitLocation(String outlet_info) {
        String location = "";
        if (outlet_info.length() != 0) {
            String[] split = outlet_info.split(Pattern.quote("||"));


            String arrLocation[] = split[0].split("Area:");
            MyApplication.logi(LOG_TAG, "Area:->" + (arrLocation[1]));

            location = arrLocation[1];

            ///here set only frst form address


            MyApplication.logi(LOG_TAG, "SPILTED IS-->" + split.toString());
            int len = split.length;
            String contactinfo2 = "";
            for (int i = 0; i < len; i++) {
                if (i == 0) {
                    contactinfo2 = split[i];
                } else if (i == len - 1) {

                    String OriginalString = split[i];
                    MyApplication.logi(LOG_TAG, "OriginalString" + OriginalString);
                    OriginalString = OriginalString.replaceAll(" ", "");
                    String arrCom[] = OriginalString.split(",");
                    MyApplication.logi(LOG_TAG, "OriginalString" + Arrays.deepToString(arrCom));

                    String arrMob[] = arrCom[0].split("Mobile-");
                    MyApplication.logi(LOG_TAG, "arrMob->" + Arrays.deepToString(arrMob));
                    String arLand[] = arrCom[1].split("Landline:");
                    MyApplication.logi(LOG_TAG, "arLand->" + Arrays.deepToString(arLand));

                    break;

                } else {
                    contactinfo2 = contactinfo2 + "|" + split[i];
                }
            }
        }
        return location;
    }

    private static String spiltAddressFull(String outlet_info) {
        String address = "";
        if (outlet_info.length() != 0) {
            String[] split = outlet_info.split(Pattern.quote("||"));
            MyApplication.logi(LOG_TAG, "SPIYTT-->" + split.toString());

        }
        return address;
    }


    private static String spiltContact(String outlet_info) {
        String land = "";
        if (outlet_info.length() != 0) {
            String[] split = outlet_info.split(Pattern.quote("||"));

            MyApplication.logi(LOG_TAG, "SPILTED IS-->" + split.toString());
            int len = split.length;
            String contactinfo2 = "";
            for (int i = 0; i < len; i++) {
                if (i == 0) {
                    contactinfo2 = split[i];
                } else if (i == len - 1) {

                    String OriginalString = split[i];
                    MyApplication.logi(LOG_TAG, "OriginalString" + OriginalString);
                    OriginalString = OriginalString.replaceAll(" ", "");
                    String arrCom[] = OriginalString.split(",");
                    MyApplication.logi(LOG_TAG, "OriginalString" + Arrays.deepToString(arrCom));
                    String arrMob[] = arrCom[0].split("Mobile-");
                    MyApplication.logi(LOG_TAG, "arrMob->" + Arrays.deepToString(arrMob));
                    //MyApplication.set_session("phone", arrMob[1]);
                    String arLand[] = arrCom[1].split("Landline:");
                    land = arLand[1];
                    //  MyApplication.set_session("land", arLand[1]);
                    MyApplication.logi(LOG_TAG, "arLand->" + Arrays.deepToString(arLand));
                    break;

                } else {
                    contactinfo2 = contactinfo2 + "|" + split[i];
                }
            }
        }
        return land;
    }


    private static String spiltContact_mob(String outlet_info) {
        String mobile_no = "";
        if (outlet_info.length() != 0) {
            String[] split = outlet_info.split(Pattern.quote("||"));

            MyApplication.logi(LOG_TAG, "SPILTED IS-->" + split.toString());
            int len = split.length;
            String contactinfo2 = "";
            for (int i = 0; i < len; i++) {
                if (i == 0) {
                    contactinfo2 = split[i];
                } else if (i == len - 1) {

                    String OriginalString = split[i];
                    MyApplication.logi(LOG_TAG, "OriginalString" + OriginalString);
                    OriginalString = OriginalString.replaceAll(" ", "");
                    String arrCom[] = OriginalString.split(",");
                    MyApplication.logi(LOG_TAG, "OriginalString" + Arrays.deepToString(arrCom));
                    String arrMob[] = arrCom[0].split("Mobile-");
                    MyApplication.logi(LOG_TAG, "arrMob->" + Arrays.deepToString(arrMob));
                    mobile_no = arrMob[1];
                    break;

                } else {
                    contactinfo2 = contactinfo2 + "|" + split[i];
                }
            }
        }
        return mobile_no;
    }

    //all status shop name wise
    public static List<OrderDeliveryStatusModel> getOrderStatusListAsPerShop() {
        List<OrderDeliveryStatusModel> orderStatusList = new ArrayList<>();
        SQLiteDatabase db = MyApplication.db.getWritableDatabase();
        MyApplication.logi(LOG_TAG, "in getOrderStatusListAsPerShop()");


        String query = "SELECT r.OrderId as OrderId,r.OrderStatus as status,r.TotalAmount as Amount,s.StatusDateTime as Date, " +
                "p.Distributor as Name ,p.OUTLETINFO from RetailerOrderMaster r ,PDistributor p ,OrderStatus s  " +
                " where p.DistributorId = r.DistributorId  and s.Statusid = r.OrderStatus and r.orderId = s.OrderId  ORDER BY  p.Distributor desc";


        MyApplication.logi("getOrderStatusListAsPerShopS ", "getOrderStatusListAsPerShop FOR ALL In tableHasData query :" + query);
        Cursor c = db.rawQuery(query, null);
        MyApplication.logi("getOrderStatusList() ", "COUNT:  " + c.getCount());
        if (c.getCount() > 0) {
            c.moveToFirst();
            do {

                // String distributorId = c.getString(c.getColumnIndexOrThrow("DistributorID"));
                String distributor_name = c.getString(c.getColumnIndexOrThrow("Name"));
                String OrderStatus = c.getString(c.getColumnIndexOrThrow("status"));
                String OrderDate = c.getString(c.getColumnIndexOrThrow("Date"));
                MyApplication.logi(LOG_TAG, "Order date-->" + OrderDate);


                String address = c.getString(c.getColumnIndexOrThrow("OUTLETINFO")).trim();
                String contctInfo = c.getString(c.getColumnIndexOrThrow("OUTLETINFO")).trim();


                String address1 = splitLocation(address);
                String contact_no_land = spiltContact(contctInfo);
                MyApplication.logi(LOG_TAG, "contact_no_land------>" + contact_no_land);
                String contact_no_mob = spiltContact_mob(contctInfo);
                MyApplication.logi(LOG_TAG, "contact_no_mob------>" + contact_no_mob);

                String info_address = spiltAddressFull(address);
                MyApplication.logi(LOG_TAG, "info_address------>" + info_address);


                String OrderID = c.getString(c.getColumnIndexOrThrow("OrderId"));
                String Amount = c.getString(c.getColumnIndexOrThrow("Amount"));
                String cart_count = "1";

                OrderDeliveryStatusModel model = new OrderDeliveryStatusModel(distributor_name, OrderStatus, OrderDate, OrderID, Amount, cart_count, address1, contact_no_land, contact_no_mob, address);
                orderStatusList.add(model);

            } while (c.moveToNext());
        }
        db.close();
        MyApplication.logi("JARVIS", " getOrderStatusListAsPerShop model orderStatusList -->>" + orderStatusList.toString());
        return orderStatusList;
    }

    //all status calemder wise
    public static List<OrderDeliveryStatusModel> getOrderStatusListAsPerCalender() {
        List<OrderDeliveryStatusModel> orderStatusList = new ArrayList<>();
        SQLiteDatabase db = MyApplication.db.getWritableDatabase();
        MyApplication.logi(LOG_TAG, "in getOrderStatusListAsPerCalender()");


        String query = "SELECT r.OrderId as OrderId,r.OrderStatus as status,r.TotalAmount as Amount,s.StatusDateTime as Date, " +
                "p.Distributor as Name ,p.OUTLETINFO from RetailerOrderMaster r ,PDistributor p ,OrderStatus s  " +
                " where p.DistributorId = r.DistributorId  and s.Statusid = r.OrderStatus and r.orderId = s.OrderId  ORDER BY  s.StatusDateTime desc";


        MyApplication.logi("getOrderStatusListAsPerCalender ", "getOrderStatusListAsPerCalender FOR ALL In tableHasData query :" + query);
        Cursor c = db.rawQuery(query, null);
        MyApplication.logi("getOrderStatusList() ", "COUNT:  " + c.getCount());
        if (c.getCount() > 0) {
            c.moveToFirst();
            do {

                // String distributorId = c.getString(c.getColumnIndexOrThrow("DistributorID"));
                String distributor_name = c.getString(c.getColumnIndexOrThrow("Name"));
                String OrderStatus = c.getString(c.getColumnIndexOrThrow("status"));
                String OrderDate = c.getString(c.getColumnIndexOrThrow("Date"));
                MyApplication.logi(LOG_TAG, "Order date-->" + OrderDate);


                String address = c.getString(c.getColumnIndexOrThrow("OUTLETINFO")).trim();
                String contctInfo = c.getString(c.getColumnIndexOrThrow("OUTLETINFO")).trim();


                String address1 = splitLocation(address);
                String contact_no_land = spiltContact(contctInfo);
                MyApplication.logi(LOG_TAG, "contact_no_land------>" + contact_no_land);
                String contact_no_mob = spiltContact_mob(contctInfo);
                MyApplication.logi(LOG_TAG, "contact_no_mob------>" + contact_no_mob);

                String info_address = spiltAddressFull(address);
                MyApplication.logi(LOG_TAG, "info_address------>" + info_address);


                String OrderID = c.getString(c.getColumnIndexOrThrow("OrderId"));
                String Amount = c.getString(c.getColumnIndexOrThrow("Amount"));
                String cart_count = "1";

                OrderDeliveryStatusModel model = new OrderDeliveryStatusModel(distributor_name, OrderStatus, OrderDate, OrderID, Amount, cart_count, address1, contact_no_land, contact_no_mob, address);
                orderStatusList.add(model);

            } while (c.moveToNext());
        }
        db.close();
        MyApplication.logi("JARVIS", " getOrderStatusListAsPerCalender model orderStatusList -->>" + orderStatusList.toString());
        return orderStatusList;
    }


    public static List<OrderDeliveryStatusModel> getOrderStatusListAsPerShopAndCalender() {
        List<OrderDeliveryStatusModel> orderStatusList = new ArrayList<>();
        SQLiteDatabase db = MyApplication.db.getWritableDatabase();
        MyApplication.logi(LOG_TAG, "in getOrderStatusListAsPerShopAndCalender()");


        String query = "SELECT r.OrderId as OrderId,r.OrderStatus as status,r.TotalAmount as Amount,s.StatusDateTime as Date, " +
                "p.Distributor as Name ,p.OUTLETINFO from RetailerOrderMaster r ,PDistributor p ,OrderStatus s  " +
                " where p.DistributorId = r.DistributorId  and s.Statusid = r.OrderStatus and r.orderId = s.OrderId  ORDER BY  p.Distributor desc,s.StatusDateTime desc";

        MyApplication.logi("getOrderStatusListAsPerShopAndCalender ", "getOrderStatusListAsPerShopAndCalender FOR ALL In tableHasData query :" + query);
        Cursor c = db.rawQuery(query, null);
        MyApplication.logi("getOrderStatusList() ", "COUNT:  " + c.getCount());
        if (c.getCount() > 0) {
            c.moveToFirst();
            do {

                // String distributorId = c.getString(c.getColumnIndexOrThrow("DistributorID"));
                String distributor_name = c.getString(c.getColumnIndexOrThrow("Name"));
                String OrderStatus = c.getString(c.getColumnIndexOrThrow("status"));
                String OrderDate = c.getString(c.getColumnIndexOrThrow("Date"));
                MyApplication.logi(LOG_TAG, "Order date-->" + OrderDate);


                String address = c.getString(c.getColumnIndexOrThrow("OUTLETINFO")).trim();
                String contctInfo = c.getString(c.getColumnIndexOrThrow("OUTLETINFO")).trim();


                String address1 = splitLocation(address);
                String contact_no_land = spiltContact(contctInfo);
                MyApplication.logi(LOG_TAG, "contact_no_land------>" + contact_no_land);
                String contact_no_mob = spiltContact_mob(contctInfo);
                MyApplication.logi(LOG_TAG, "contact_no_mob------>" + contact_no_mob);

                String info_address = spiltAddressFull(address);
                MyApplication.logi(LOG_TAG, "info_address------>" + info_address);


                String OrderID = c.getString(c.getColumnIndexOrThrow("OrderId"));
                String Amount = c.getString(c.getColumnIndexOrThrow("Amount"));
                String cart_count = "1";

                OrderDeliveryStatusModel model = new OrderDeliveryStatusModel(distributor_name, OrderStatus, OrderDate, OrderID, Amount, cart_count, address1, contact_no_land, contact_no_mob, address);
                orderStatusList.add(model);

            } while (c.moveToNext());
        }
        db.close();
        MyApplication.logi("JARVIS", " getOrderStatusListAsPerShopAndCalender model orderStatusList -->>" + orderStatusList.toString());
        return orderStatusList;
    }


    public static int getPendingCount_retailer_order_master() {
        MyApplication.logi(LOG_TAG, "_retailer_order_master IN getdelivery_pending_no");
        int count1 = 0;
        SQLiteDatabase db = MyApplication.db.getReadableDatabase();


        String query = "SELECT * from RetailerOrderMaster WHERE OrderStatus = 1";
        MyApplication.log("In _retailer_order_master getdelivery_pending_no query :" + query);
        Cursor c = db.rawQuery(query, null);
        count1 = c.getCount();
        //  int pending = count1 - getDeliveryStatusCount() - getRejectedCount();
        //  MyApplication.logi(LOG_TAG, "abc*******" + pending);
        MyApplication.logi(LOG_TAG, "_retailer_order_master COUNT OF FILTER getdelivery_pending_no>" + count1);

        //   return pending;
        return count1;
    }


    public static int getDeliveryStatusCount_retailer_order_master() {
        MyApplication.logi(LOG_TAG, "IN getDeliveryStatusCount_retailer_order_master");
        int count1 = 0;
        SQLiteDatabase db = MyApplication.db.getReadableDatabase();


        String query = "SELECT * from RetailerOrderMaster WHERE OrderStatus = 2";
        MyApplication.log("In getDeliveryStatusCount query :" + query);
        Cursor c = db.rawQuery(query, null);
        count1 = c.getCount();
        MyApplication.logi(LOG_TAG, "COUNT OF FILTER getDeliveryStatusCount_retailer_order_master>" + count1);


        return count1;
    }


    public static int getRejectedCount_retailer_order_master() {
        MyApplication.logi(LOG_TAG, "IN getRejectedCount_retailer_order_master");
        int count1 = 0;
        SQLiteDatabase db = MyApplication.db.getReadableDatabase();


        String query = "SELECT * from RetailerOrderMaster WHERE OrderStatus = 3";
        MyApplication.log("In getRejectedCount query_retailer_order_master :" + query);
        Cursor c = db.rawQuery(query, null);
        count1 = c.getCount();

        return count1;
    }


    public static void updateStatusId(JSONArray jsonArray) {
        MyApplication.logi(LOG_TAG, " in updateStatusId-->");
        int count_array = jsonArray.length();
        JSONObject jsonObject = null;
        SQLiteDatabase db = MyApplication.db.getWritableDatabase();
        try {


            for (int i = 0; i < count_array; i++) {


                jsonObject = jsonArray.getJSONObject(i);

                String orderID = jsonObject.getString("AppOrderId");

                String statusID = jsonObject.getString("StatusId");
                MyApplication.logi(LOG_TAG, "orderID-->" + orderID + "statusID-->" + statusID);

                ContentValues initialValues1 = new ContentValues();
                initialValues1.put("OrderStatus", statusID);
                long ret1 = db.update(NAME, initialValues1, "OrderId=?", new String[]{orderID});

                MyApplication.logi(LOG_TAG, "update status ret1-->" + ret1);
            }


            MyApplication.logi(LOG_TAG, "EndTime updateStatusId>");

        } catch (JSONException e) {
            MyApplication.logi(LOG_TAG, "JSONException updateStatusId--->" + e.getMessage());

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




