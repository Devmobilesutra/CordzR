package com.sapl.retailerorderingmsdpharma.MyDatabase;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.sapl.retailerorderingmsdpharma.activities.MyApplication;
import com.sapl.retailerorderingmsdpharma.models.DistributorModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * Created by Mrunal on 02/02/2018.
 */

public class TABLE_PDISTRIBUTOR {


    public static String NAME = "PDistributor";
    private static final String LOG_TAG = "TABLE_PDistributor";

    public static String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
            + NAME + "(Id INTEGER," +
            "DistributorId TEXT," +
            "UserId TEXT," +
            "Distributor TEXT," +
            "DistributorAlias TEXT," +
            "IsActive TINYINT," +
            "UPCFromTextFile TINYINT," +
            "UnitFromVolume TINYINT," +
            "StockNorm TINYINT," +
            "IsValidated TINYINT," +
            "ValidatedFromDate DATE," +
            "LASTEDITDATE DATE," +
            "UnkCustomerId TEXT," +
            "UnkItemId TEXT," +
            "ERPCode TEXT," +
            "EmailId TEXT," +
            "LastInvoiceDate TEXT," +
            "AREAID TEXT," +
            "AREA TEXT," +
            "AREAALIAS TEXT," +
            "BRANCHID TEXT," +
            "BRANCH TEXT," +
            "BRANCHALIAS TEXT," +
            "DISTRIBUTORGROUPID TEXT," +
            "DISTRIBUTORGROUP TEXT," +
            "DISTRIBUTORGROUPALIAS TEXT," +
            "DISTRIBUTORTYPEID TEXT," +
            "DISTRIBUTORTYPE TEXT," +
            "DISTRIBUTORTYPEALIAS TEXT," +
            "CITYID TEXT," +
            "CITY TEXT," +
            "STATEID TEXT," +
            "STATE TEXT," +
            "SignOffDate TEXT," +
            "SignOffTime TEXT," +
            "OUTLETINFO TEXT)";


    public static void insert_bulk_PDistributor(JSONArray jsonArray) {
        try {

            MyApplication.logi(LOG_TAG, "in insert_bulk_PDistributor->");
            SQLiteDatabase db = MyApplication.db.getWritableDatabase();
            db.beginTransaction();

            String insert_sql = "insert into " + NAME + " ( "
                    + "Id,DistributorId,UserId,Distributor,DistributorAlias,IsActive,UPCFromTextFile,UnitFromVolume,StockNorm,IsValidated,ValidatedFromDate," +
                    "LASTEDITDATE,UnkCustomerId,UnkItemId,ERPCode,EmailId,LastInvoiceDate,AREAID,AREA,AREAALIAS,BRANCHID,BRANCH,BRANCHALIAS,DISTRIBUTORGROUPID," +
                    "DISTRIBUTORGROUP,DISTRIBUTORGROUPALIAS,DISTRIBUTORTYPEID,DISTRIBUTORTYPE,DISTRIBUTORTYPEALIAS" +
                    ",CITYID,CITY,STATEID,STATE,SignOffDate,SignOffTime,OUTLETINFO) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";


            MyApplication.logi(LOG_TAG, "insert_sql" + insert_sql);
            SQLiteStatement statement = db.compileStatement(insert_sql);
            JSONObject jsonObject = null;
            int count_array = jsonArray.length();
            MyApplication.logi(LOG_TAG, "COUNT ISS->" + count_array);

            for (int i = 0; i < count_array; i++) {


                jsonObject = jsonArray.getJSONObject(i);

                statement.clearBindings();
                statement.bindString(1, jsonObject.getString("Id"));
                statement.bindString(2, jsonObject.getString("DistributorId"));
                statement.bindString(3, jsonObject.getString("UserId"));
                statement.bindString(4, jsonObject.getString("Distributor"));
                statement.bindString(5, jsonObject.getString("DistributorAlias"));

                statement.bindString(6, jsonObject.getString("IsActive"));
                statement.bindString(7, jsonObject.getString("UPCFromTextFile"));
                statement.bindString(8, jsonObject.getString("UnitFromVolume"));
                statement.bindString(9, jsonObject.getString("StockNorm"));
                statement.bindString(10, jsonObject.getString("IsValidated"));

                statement.bindString(11, jsonObject.getString("ValidatedFromDate"));
                statement.bindString(12, jsonObject.getString("LASTEDITDATE"));
                statement.bindString(13, jsonObject.getString("UnkCustomerId"));
                statement.bindString(14, jsonObject.getString("UnkItemId"));
                statement.bindString(15, jsonObject.getString("ERPCode"));

                statement.bindString(16, jsonObject.getString("EmailId"));
                statement.bindString(17, jsonObject.getString("LastInvoiceDate"));
                statement.bindString(18, jsonObject.getString("AREAID"));
                statement.bindString(19, jsonObject.getString("AREA"));
                statement.bindString(20, jsonObject.getString("AREAALIAS"));

                statement.bindString(21, jsonObject.getString("BRANCHID"));
                statement.bindString(22, jsonObject.getString("BRANCH"));
                statement.bindString(23, jsonObject.getString("BRANCHALIAS"));
                statement.bindString(24, jsonObject.getString("DISTRIBUTORGROUPID"));
                statement.bindString(25, jsonObject.getString("DISTRIBUTORGROUP"));

                statement.bindString(26, jsonObject.getString("DISTRIBUTORGROUPALIAS"));
                statement.bindString(27, jsonObject.getString("DISTRIBUTORTYPEID"));
                statement.bindString(28, jsonObject.getString("DISTRIBUTORTYPE"));
                statement.bindString(29, jsonObject.getString("DISTRIBUTORTYPEALIAS"));
                statement.bindString(30, jsonObject.getString("CITYID"));

                statement.bindString(31, jsonObject.getString("CITY"));
                statement.bindString(32, jsonObject.getString("STATEID"));
                statement.bindString(33, jsonObject.getString("STATE"));
                statement.bindString(34, jsonObject.getString("SignOffDate"));
                statement.bindString(35, jsonObject.getString("SignOffTime"));
                statement.bindString(36, jsonObject.getString("OUTLETINFO"));


                statement.execute();
            }
            MyApplication.logi(LOG_TAG, "EndTime->");
            db.setTransactionSuccessful();
            db.endTransaction();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

//function to get distributor name
    public static ArrayList<DistributorModel> getDistributorName() {
        MyApplication.logi(LOG_TAG, "in getDistributorName");
        int dist_id;
        String name = "", rating = "", contctInfo = "", address = "", info = "", image_path = "";
        SQLiteDatabase db = MyApplication.db.getReadableDatabase();
        ArrayList<DistributorModel> res = new ArrayList<>();
        String query = null;

        if (MyApplication.get_session("distributor_list").equalsIgnoreCase("cart")) {
            MyApplication.logi(LOG_TAG, "IN FROM CART");
          //  query = "SELECT * FROM TempRetailerOrderMaster ,PDistributor where TempRetailerOrderMaster.DistributorID = PDistributor.Id  group by TempRetailerOrderMaster.DistributorID";
                query = "SELECT * FROM TempRetailerOrderMaster ,PDistributor,TempOrderDetails where TempRetailerOrderMaster.DistributorID = PDistributor.Id  AND TempRetailerOrderMaster.OrderID = TempOrderDetails.OrderID group by TempRetailerOrderMaster.DistributorID";


        } else if (MyApplication.get_session("distributor_list").equalsIgnoreCase("card_order_booking")) {
            MyApplication.logi(LOG_TAG, "IN FROM card_order_booking");
            query = "SELECT * FROM PDistributor  WHERE ISACTIVE = 'true'";
        }


        MyApplication.logi(LOG_TAG, "QUERY FOR getDistributorName IS-->" + query);
        try {
            Cursor c = db.rawQuery(query, null);
            int count = c.getCount();
            if (count > 0) {
                c.moveToFirst();
                do {
                    dist_id = c.getInt(c.getColumnIndexOrThrow("DistributorId"));
                    MyApplication.logi(LOG_TAG, "DISTTT ID IS-->" + dist_id);
                    name = c.getString(c.getColumnIndexOrThrow("Distributor")).trim();
                    rating = "4.2";
                    contctInfo = c.getString(c.getColumnIndexOrThrow("OUTLETINFO")).trim();
                    address = c.getString(c.getColumnIndexOrThrow("OUTLETINFO")).trim();
                    info = c.getString(c.getColumnIndexOrThrow("OUTLETINFO")).trim();
                    image_path = c.getString(c.getColumnIndexOrThrow("OUTLETINFO")).trim();

                    String address1 = splitLocation(address);
                    String contact_no_land = spiltContact(contctInfo);
                    MyApplication.logi(LOG_TAG, "contact_no_land------>" + contact_no_land);
                    String contact_no_mob = spiltContact_mob(contctInfo);
                    MyApplication.logi(LOG_TAG, "contact_no_mob------>" + contact_no_mob);

                    String info_address = spiltAddressFull(address);
                    MyApplication.logi(LOG_TAG, "info_address------>" + info_address);

                    String isActive = c.getString(c.getColumnIndexOrThrow("IsActive"));
                    MyApplication.logi(LOG_TAG, "ISACTIVE----->" + isActive);

                    DistributorModel distributorModel = new DistributorModel(dist_id, name, address1, contact_no_land, rating, info, image_path, contact_no_mob, isActive);
                    res.add(distributorModel);
                } while (c.moveToNext());

            }
            c.close();
        } catch (Exception e) {
            MyApplication.logi(LOG_TAG, "getDistributorName in Exception" + e.getMessage());
        }

        return res;
    }






    public static int countOfAddToCardItems() {
        MyApplication.logi(LOG_TAG, "IN count countOfAddToCardItems");
        String query = "";
        int resp = 0;
        int count = 0;
        SQLiteDatabase db = MyApplication.db.getWritableDatabase();
        query = "SELECT * FROM  TempRetailerOrderMaster ,PDistributor,TempOrderDetails where TempRetailerOrderMaster.DistributorID = PDistributor.Id  AND TempRetailerOrderMaster.OrderID = TempOrderDetails.OrderID group by TempRetailerOrderMaster.DistributorID";


        MyApplication.logi(LOG_TAG, "QUERY FOR countOfAddToCardItems IS-->" + query);
        try {
            Cursor c = db.rawQuery(query, null);
            count= c.getCount();
        }catch (Exception e) {
            MyApplication.logi(LOG_TAG, "countOfAddToCardItems in Exception" + e.getMessage());
        }
        return count;
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


    public static void deleteTableData() {
        MyApplication.logi(LOG_TAG, "in delete pdistributor");
        SQLiteDatabase db = MyApplication.db.getWritableDatabase();


        db.delete(NAME, null, null);


    }



    public static boolean tableHasData() {
        int num = -1;

        MyApplication.logi(LOG_TAG, "TABLE_PDISTRIBUTOR in tableHasData()");
        SQLiteDatabase db = MyApplication.db.getWritableDatabase();
        String query = "SELECT * from PDistributor";
        MyApplication.log("In PDistributor tableHasData query :" + query);
        Cursor c = db.rawQuery(query, null);
        num = c.getCount();
        MyApplication.log("PDistributor has_tbl_data num is count : " + num);
        if (num > 0) {
            MyApplication.log("PDistributor data is there : " + num);
            c.close();
            return true;
        } else {
            MyApplication.log("PDistributor data is not there : " + num);
            c.close();
            return false;
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
