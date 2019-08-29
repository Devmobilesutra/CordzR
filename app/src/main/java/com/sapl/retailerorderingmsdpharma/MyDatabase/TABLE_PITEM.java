package com.sapl.retailerorderingmsdpharma.MyDatabase;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.widget.Toast;

import com.sapl.retailerorderingmsdpharma.activities.MyApplication;
import com.sapl.retailerorderingmsdpharma.models.BrandModel;
import com.sapl.retailerorderingmsdpharma.models.DivisionModel;
import com.sapl.retailerorderingmsdpharma.models.SubGroupModel;
import com.sapl.retailerorderingmsdpharma.models.SubItemDataModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Blob;
import java.util.ArrayList;

/**
 * Created by Mrunal on 02/02/2018.
 */

public class TABLE_PITEM {

    public static String NAME = "PItem";
    private static final String LOG_TAG = "TABLE_PItem";

    public static String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
            + NAME + "(Id INTEGER,ItemId TEXT, Item TEXT, ItemAlias TEXT, BPC TEXT,BPC1 TEXT,BPC2 TEXT,IsActive TINYINT,RateApplicability TINYINT,ErpCode TEXT," +
            "Volume NUMERIC,SchemeBPC NUMERIC,TSMRelevance TINYINT,ReportingQuantity NUMERIC,StockNorm TINYINT,MRP NUMERIC," +
            "PTS NUMERIC,PTR NUMERIC,Rate NUMERIC,NetRate NUMERIC,WeightInGrams NUMERIC,ReorderLevel SMALLINT,IsDashboard TINYINT," +
            "ReportingUOMID SMALLINT,ReportingUOM TEXT,HierarchyID TEXT,CompanyID TINYINT,LASTEDITDATE DATE,BRANDID TEXT,BRAND TEXT," +
            "BRANDALIAS TEXT,DIVISIONID TEXT,DIVISION TEXT,DIVISIONALIAS TEXT,FLAVOURID TEXT,FLAVOUR TEXT,FLAVOURALIAS TEXT," +
            "ITEMCLASSID TEXT,ITEMCLASS TEXT,ITEMCLASSALIAS TEXT,ITEMGROUPID TEXT,ITEMGROUP TEXT,ITEMGROUPALIAS TEXT,ITEMSIZEID TEXT," +
            "ITEMSIZE TEXT,ITEMSIZEALIAS TEXT,ITEMSUBGROUPID TEXT,ITEMSUBGROUP TEXT, ITEMSUBGROUPALIAS TEXT,ITEMTYPEID TEXT," +
            "ITEMTYPE TEXT,ITEMTYPEALIAS TEXT,SIZESEQUENCE NUMERIC,ITEMSIZESEQUENCE TEXT,ITEMSIZEALIASSEQUENCE TEXT,ITEMSEQUENCE TEXT," +
            "ITEMALIASSEQUENCE TEXT,BRANDSEQUENCE TEXT,BRANDALIASSEQUENCE TEXT)";


    public static void insert_bulk_PItem(JSONArray jsonArray) {
        try {
            MyApplication.logi(LOG_TAG, "in insert_bulk_PItem->");

            SQLiteDatabase db = MyApplication.db.getWritableDatabase();
            db.beginTransaction();

            String insert_sql = "insert into " + NAME + " (" + "Id,ItemId,Item,ItemAlias,BPC,IsActive,RateApplicability,ErpCode,Volume,SchemeBPC,TSMRelevance," +
                    "ReportingQuantity,StockNorm,MRP,PTS,PTR,Rate,NetRate,WeightInGrams,ReorderLevel, " +
                    "IsDashboard,ReportingUOMID,ReportingUOM,HierarchyID,CompanyID,LASTEDITDATE,BRANDID,BRAND,BRANDALIAS, " +
                    "DIVISIONID,DIVISION,DIVISIONALIAS,FLAVOURID,FLAVOUR,FLAVOURALIAS,ITEMCLASSID,ITEMCLASS,ITEMCLASSALIAS," +
                    "ITEMGROUPID,ITEMGROUP,ITEMGROUPALIAS,ITEMSIZEID,ITEMSIZE,ITEMSIZEALIAS,ITEMSUBGROUPID,ITEMSUBGROUP," +
                    "ITEMSUBGROUPALIAS,ITEMTYPEID,ITEMTYPE,ITEMTYPEALIAS,SIZESEQUENCE,ITEMSIZESEQUENCE,ITEMSIZEALIASSEQUENCE," +
                    "ITEMSEQUENCE,ITEMALIASSEQUENCE,BRANDSEQUENCE,BRANDALIASSEQUENCE,BPC1,BPC2) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            MyApplication.logi(LOG_TAG, "insert_sql" + insert_sql);

            SQLiteStatement statement = db.compileStatement(insert_sql);
            JSONObject jsonObject = null;
            int count_array = jsonArray.length();
            MyApplication.logi(LOG_TAG, "COUNT ISS->" + count_array);

            for (int i = 0; i < count_array; i++) {


                jsonObject = jsonArray.getJSONObject(i);

                statement.clearBindings();


                statement.bindString(1, jsonObject.getString("Id"));
                statement.bindString(2, jsonObject.getString("ItemId"));
                statement.bindString(3, jsonObject.getString("Item"));
                statement.bindString(4, jsonObject.getString("ItemAlias"));
                statement.bindString(5, jsonObject.getString("BPC"));

                statement.bindString(6, jsonObject.getString("IsActive"));
                statement.bindString(7, jsonObject.getString("RateApplicability"));
                statement.bindString(8, jsonObject.getString("ErpCode"));
                statement.bindString(9, jsonObject.getString("Volume"));
                statement.bindString(10, jsonObject.getString("SchemeBPC"));

                statement.bindString(11, jsonObject.getString("TSMRelevance"));
                statement.bindString(12, jsonObject.getString("ReportingQuantity"));
                statement.bindString(13, jsonObject.getString("StockNorm"));
                statement.bindString(14, jsonObject.getString("MRP"));
                statement.bindString(15, jsonObject.getString("PTS"));

                statement.bindString(16, jsonObject.getString("PTR"));
                statement.bindString(17, jsonObject.getString("Rate"));
                statement.bindString(18, jsonObject.getString("NetRate"));
                statement.bindString(19, jsonObject.getString("WeightInGrams"));
                statement.bindString(20, jsonObject.getString("ReorderLevel"));

                statement.bindString(21, jsonObject.getString("IsDashboard"));
                statement.bindString(22, jsonObject.getString("ReportingUOMID"));
                statement.bindString(23, jsonObject.getString("ReportingUOM"));
                statement.bindString(24, jsonObject.getString("HierarchyID"));
                statement.bindString(25, jsonObject.getString("CompanyID"));

                statement.bindString(26, jsonObject.getString("LASTEDITDATE"));
                statement.bindString(27, jsonObject.getString("BRANDID"));
                statement.bindString(28, jsonObject.getString("BRAND"));
                statement.bindString(29, jsonObject.getString("BRANDALIAS"));
                statement.bindString(30, jsonObject.getString("DIVISIONID"));

                statement.bindString(31, jsonObject.getString("DIVISION"));
                statement.bindString(32, jsonObject.getString("DIVISIONALIAS"));
                statement.bindString(33, jsonObject.getString("FLAVOURID"));
                statement.bindString(34, jsonObject.getString("FLAVOUR"));
                statement.bindString(35, jsonObject.getString("FLAVOURALIAS"));

                statement.bindString(36, jsonObject.getString("ITEMCLASSID"));
                statement.bindString(37, jsonObject.getString("ITEMCLASS"));
                statement.bindString(38, jsonObject.getString("ITEMCLASSALIAS"));
                statement.bindString(39, jsonObject.getString("ITEMGROUPID"));
                statement.bindString(40, jsonObject.getString("ITEMGROUP"));

                statement.bindString(41, jsonObject.getString("ITEMGROUPALIAS"));
                statement.bindString(42, jsonObject.getString("ITEMSIZEID"));
                statement.bindString(43, jsonObject.getString("ITEMSIZE"));
                statement.bindString(44, jsonObject.getString("ITEMSIZEALIAS"));
                statement.bindString(45, jsonObject.getString("ITEMSUBGROUPID"));

                statement.bindString(46, jsonObject.getString("ITEMSUBGROUP"));
                statement.bindString(47, jsonObject.getString("ITEMSUBGROUPALIAS"));
                statement.bindString(48, jsonObject.getString("ITEMTYPEID"));
                statement.bindString(49, jsonObject.getString("ITEMTYPE"));
                statement.bindString(50, jsonObject.getString("ITEMTYPEALIAS"));

                statement.bindString(51, jsonObject.getString("SIZESEQUENCE"));
                statement.bindString(52, jsonObject.getString("ITEMSIZESEQUENCE"));
                statement.bindString(53, jsonObject.getString("ITEMSIZEALIASSEQUENCE"));
                statement.bindString(54, jsonObject.getString("ITEMSEQUENCE"));
                statement.bindString(55, jsonObject.getString("ITEMALIASSEQUENCE"));

                statement.bindString(56, jsonObject.getString("BRANDSEQUENCE"));
                statement.bindString(57, jsonObject.getString("BRANDALIASSEQUENCE"));

                statement.bindString(58, jsonObject.getString("BPC1"));
                statement.bindString(59, jsonObject.getString("BPC2"));

                statement.execute();
            }
            MyApplication.logi(LOG_TAG, "EndTime->");
            db.setTransactionSuccessful();
            db.endTransaction();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //function to get divisions
    public static ArrayList<DivisionModel> getDivision(String groupFilter) {
        MyApplication.logi(LOG_TAG, "FILTR-->" + groupFilter);
        MyApplication.logi(LOG_TAG, "in getDivision");
        String division = "";
        int division_id;
        SQLiteDatabase db = MyApplication.db.getReadableDatabase();
        ArrayList<DivisionModel> res = new ArrayList<>();
        String query = "SELECT " + groupFilter + " as GROUPNAME," + groupFilter + "ID as GROUPID FROM PItem GROUP BY " + groupFilter;

        MyApplication.logi(LOG_TAG, "QUERY FOR getDivision IS-->" + query);
        try {
            Cursor c = db.rawQuery(query, null);
            int count = c.getCount();
            if (count > 0) {
                c.moveToFirst();
                do {
                    division = c.getString(c.getColumnIndexOrThrow("GROUPNAME"));
                    String divid = c.getString(c.getColumnIndexOrThrow("GROUPID"));
                    division_id = Integer.parseInt(divid);
                    //  MyApplication.logi(LOG_TAG, "division_id in Exception" +  c.getInt(c.getColumnIndexOrThrow("GROUPID")));
                    DivisionModel selectionDataModel = new DivisionModel(division, division_id);
                    res.add(selectionDataModel);
                } while (c.moveToNext());


            }
            c.close();
        } catch (Exception e) {
            MyApplication.logi(LOG_TAG, "getDivision in Exception" + e.getMessage());
        }

        return res;
    }

    //function to get subDivision...i.e itemGroup
    public static ArrayList<SubGroupModel> getSubGroup(String subgroupfilter, String divisionId) {
        MyApplication.logi(LOG_TAG, "FILTR-->" + subgroupfilter + "SESSION_GROUP_ID--->" + divisionId);
        MyApplication.logi(LOG_TAG, "in getSubGroup");
        String division = "";
        int division_id;
        String query = "";
        SQLiteDatabase db = MyApplication.db.getReadableDatabase();
        ArrayList<SubGroupModel> res = new ArrayList<>();


        if (divisionId.equalsIgnoreCase("")) {
            MyApplication.logi(LOG_TAG, "getSubGroup NOTHINGGGGGG DIVISION IS NOT SELECTED");

            query = "SELECT " + subgroupfilter + " as SUBGROUPNAME," +
                    subgroupfilter + "ID as SUBGROUPID FROM PItem GROUP BY " + subgroupfilter + "";


        } else {
            MyApplication.logi(LOG_TAG, "getSubGroup YES DIVISION IS  SELECTED");
            query = "SELECT " + subgroupfilter + " as SUBGROUPNAME," +
                    subgroupfilter + "ID as SUBGROUPID FROM PItem  where DIVISIONID = cast('" + divisionId + "' as int) GROUP BY " + subgroupfilter + "";

        }
        MyApplication.logi(LOG_TAG, "QUERY FOR getSubGroup IS-->" + query);
        try {
            Cursor c = db.rawQuery(query, null);
            int count = c.getCount();
            MyApplication.logi(LOG_TAG, "count   IS->" + count);
            if (count > 0) {
                MyApplication.logi(LOG_TAG, "in if count  > IS->" + count);
                c.moveToFirst();
                do {
                    division = c.getString(c.getColumnIndexOrThrow("SUBGROUPNAME"));

                    String divid = c.getString(c.getColumnIndexOrThrow("SUBGROUPID"));
                    division_id = Integer.parseInt(divid);

                    SubGroupModel subgroupmodel = new SubGroupModel(division, division_id);
                    res.add(subgroupmodel);
                    MyApplication.logi(LOG_TAG, "MODELLL IS->" + subgroupmodel);
                } while (c.moveToNext());


            }
            c.close();
        } catch (Exception e) {
            MyApplication.logi(LOG_TAG, "getSubGroup in Exception" + e.getMessage());
        }

        return res;
    }

    //function to get brand
    public static ArrayList<BrandModel> getBrand(String brandFilter, String divisionId, String sub_groupId) {
        MyApplication.logi(LOG_TAG, "getBrand FILTR-->" + brandFilter + " getBrand SESSION_GROUP_ID--->" + divisionId + "sub_groupId-->" + sub_groupId);
        MyApplication.logi(LOG_TAG, "in getBrand");
        String division = "";
        int division_id;
        SQLiteDatabase db = MyApplication.db.getReadableDatabase();
        ArrayList<BrandModel> res = new ArrayList<>();
        String query = "";


        if (divisionId.equalsIgnoreCase("") && !sub_groupId.equalsIgnoreCase("")) {
            MyApplication.logi(LOG_TAG, " DIVISION  is empty");
            query = "SELECT " + brandFilter + " as BRAND," + brandFilter + "ID as BRANDID FROM PItem where ITEMGROUPID =  cast('" + sub_groupId + "' as int) GROUP BY " + brandFilter + "";

        } else if (sub_groupId.equalsIgnoreCase("") && !divisionId.equalsIgnoreCase("")) {
            MyApplication.logi(LOG_TAG, "yes  subgroup IS empty");
            query = "SELECT " + brandFilter + " as BRAND," + brandFilter + "ID as BRANDID FROM PItem where  DIVISIONID = cast('" + divisionId + "' as int) GROUP BY " + brandFilter + "";

        } else if (divisionId.equalsIgnoreCase("") && sub_groupId.equalsIgnoreCase("")) {
            MyApplication.logi(LOG_TAG, "mk both DIVISION and subgroup is empty");

            query = "SELECT " + brandFilter + " as BRAND," + brandFilter + "ID as BRANDID FROM PItem GROUP BY " + brandFilter + "";

        } else if (divisionId.equalsIgnoreCase("") || sub_groupId.equalsIgnoreCase("")) {
            MyApplication.logi(LOG_TAG, "mk both DIVISION OR subgroup is empty");

            query = "SELECT " + brandFilter + " as BRAND," + brandFilter + "ID as BRANDID FROM PItem where  DIVISIONID = cast('" + divisionId + "' as int) AND ITEMGROUPID =  cast('" + sub_groupId + "' as int) GROUP BY " + brandFilter + "";

        } else {
            MyApplication.logi(LOG_TAG, "mk both yes DIVISION subgroup IS  SELECTED");
            query = "SELECT " + brandFilter + " as BRAND," + brandFilter + "ID as BRANDID FROM PItem where  DIVISIONID = cast('" + divisionId + "' as int) AND ITEMGROUPID =  cast('" + sub_groupId + "' as int) GROUP BY " + brandFilter + "";

        }
        MyApplication.logi(LOG_TAG, "QUERY FOR getBrand IS-->" + query);
        try {
            Cursor c = db.rawQuery(query, null);
            int count = c.getCount();
            if (count > 0) {
                c.moveToFirst();
                do {
                    division = c.getString(c.getColumnIndexOrThrow("BRAND"));
                    String divId = c.getString(c.getColumnIndexOrThrow("BRANDID"));
                    division_id = Integer.parseInt(divId);
                    BrandModel brandmodel = new BrandModel(division, division_id);
                    res.add(brandmodel);
                } while (c.moveToNext());


            }
            c.close();
        } catch (Exception e) {
            MyApplication.logi(LOG_TAG, "getBrand in Exception" + e.getMessage());
        }

        return res;
    }

    //function to get final items
    public static ArrayList<SubItemDataModel> getSubItems(String itemFilter, String divisionId, String sub_groupId, String brandId, String dist_price_list_Id, String uomDescp) {


        MyApplication.logi(LOG_TAG, "getITEm FILTR-->" + itemFilter + " getBrand SESSION_GROUP_ID--->" + divisionId + "sub_groupId-->" + sub_groupId + "brand id----->" + brandId + "uom is------>" + uomDescp + "PLD--->" + dist_price_list_Id);
        MyApplication.logi(LOG_TAG, "in getITEm");
        String item = "", disc_type = "", discount_rate = "", remarks = "";
        int item_id;
        byte[] imageDetails;
        String conversionFormula = "";
        SQLiteDatabase db = MyApplication.db.getReadableDatabase();
        float case_value, sales_rate, bpc;

        ArrayList<SubItemDataModel> res = new ArrayList<>();
        // String query1 = "SELECT ConvToBase from UomMaster where UOMDescription='" + uomDescp + "'";

        String query1 = "SELECT ConversionFormula from UomMaster where UOMDescription='" + uomDescp + "' collate NOCASE";

        MyApplication.logi(LOG_TAG, "query1-->" + query1);
        Cursor c1 = db.rawQuery(query1, null);
        int count1 = c1.getCount();
        String query = "";
        if (count1 > 0) {
            c1.moveToFirst();

            conversionFormula = c1.getString(c1.getColumnIndexOrThrow("ConversionFormula"));
            MyApplication.logi(LOG_TAG, "getConvertToBase DATA IS-->" + conversionFormula); //PriceListDetails.SalesRate*Pitem.BPC

        }


        if (sub_groupId.equalsIgnoreCase("") && !brandId.equalsIgnoreCase("") && !divisionId.equalsIgnoreCase("")) {

            MyApplication.logi(LOG_TAG, "sub group is empty and brand is not empty");
            query = "SELECT  RetailerImages.ImageDetails as ImagePath,PItem." + itemFilter + " as ITEM," +
                    " PItem." + itemFilter + "ID as ITEMID , PriceListDetails.DiscoutType as DiscoutType," +
                    "PriceListDetails.SalesRate as SalesRate, " +
                    "PriceListDetails.DiscountRate as DiscountRate, PriceListDetails.Remarks as Remarks," +
                    conversionFormula + " as Conversion  FROM PItem " +
                    "LEFT JOIN RetailerImages on cast (PItem.ItemId as int)= RetailerImages.ImageId " +
                    "INNER JOIN PriceListDetails on  cast(PItem.ItemId as int)  = PriceListDetails.ItemID and PriceListDetails.PriceListID = " + dist_price_list_Id +
                    "AND DIVISIONID = cast('" + divisionId + "' as int) AND BRANDID = cast('" + brandId + "' as int)";


        } else if (!divisionId.equalsIgnoreCase("") && !sub_groupId.equalsIgnoreCase("") && brandId.equalsIgnoreCase("")) {
            MyApplication.logi(LOG_TAG, "only brand is emoty");
            query = "SELECT  RetailerImages.ImageDetails as ImagePath,PItem." + itemFilter + " as ITEM," +
                    " PItem." + itemFilter + "ID as ITEMID , PriceListDetails.DiscoutType as DiscoutType," +
                    "PriceListDetails.SalesRate as SalesRate, " +
                    "PriceListDetails.DiscountRate as DiscountRate, PriceListDetails.Remarks as Remarks," +
                    conversionFormula + " as Conversion  FROM PItem " +
                    "LEFT JOIN RetailerImages on cast (PItem.ItemId as int)= RetailerImages.ImageId " +
                    "INNER JOIN PriceListDetails on  cast(PItem.ItemId as int)  = PriceListDetails.ItemID and PriceListDetails.PriceListID = " + dist_price_list_Id +
                    " AND DIVISIONID = cast('" + divisionId + "' as int) AND" +
                    " ITEMGROUPID =  cast('" + sub_groupId + "' as int)";


        } else if (!divisionId.equalsIgnoreCase("") && sub_groupId.equalsIgnoreCase("") && !brandId.equalsIgnoreCase("")) {
            MyApplication.logi(LOG_TAG, "div and brnad is not empty");
            query = "SELECT  RetailerImages.ImageDetails as ImagePath,PItem." + itemFilter + " as ITEM," +
                    " PItem." + itemFilter + "ID as ITEMID , PriceListDetails.DiscoutType as DiscoutType," +
                    "PriceListDetails.SalesRate as SalesRate, " +
                    "PriceListDetails.DiscountRate as DiscountRate, PriceListDetails.Remarks as Remarks," +
                    conversionFormula + " as Conversion  FROM PItem " +
                    "LEFT JOIN RetailerImages on cast (PItem.ItemId as int)= RetailerImages.ImageId " +
                    "INNER JOIN PriceListDetails on  cast(PItem.ItemId as int)  = PriceListDetails.ItemID and PriceListDetails.PriceListID = " + dist_price_list_Id +
                    " AND DIVISIONID = cast('" + divisionId + "' as int) AND BRANDID = cast('" + brandId + "' as int)";


        } else if (!divisionId.equalsIgnoreCase("") && sub_groupId.equalsIgnoreCase("") && brandId.equalsIgnoreCase("")) {

            MyApplication.logi(LOG_TAG, "subgroup id and brand id is empty");
            query = "SELECT  RetailerImages.ImageDetails as ImagePath,PItem." + itemFilter + " as ITEM," +
                    " PItem." + itemFilter + "ID as ITEMID , PriceListDetails.DiscoutType as DiscoutType," +
                    "PriceListDetails.SalesRate as SalesRate, " +
                    "PriceListDetails.DiscountRate as DiscountRate, PriceListDetails.Remarks as Remarks," +
                    conversionFormula + " as Conversion  FROM PItem " +
                    "LEFT JOIN RetailerImages on cast (PItem.ItemId as int)= RetailerImages.ImageId " +
                    "INNER JOIN PriceListDetails on  cast(PItem.ItemId as int)  = PriceListDetails.ItemID and PriceListDetails.PriceListID = " + dist_price_list_Id +
                    " AND DIVISIONID = cast('" + divisionId + "' as int) ";
        } else if (divisionId.equalsIgnoreCase("") && !sub_groupId.equalsIgnoreCase("") && brandId.equalsIgnoreCase("")) {


            MyApplication.logi(LOG_TAG, "div is empty subgroup is not empty brand is empty");
            query = "SELECT  RetailerImages.ImageDetails as ImagePath,PItem." + itemFilter + " as ITEM," +
                    " PItem." + itemFilter + "ID as ITEMID , PriceListDetails.DiscoutType as DiscoutType," +
                    "PriceListDetails.SalesRate as SalesRate, " +
                    "PriceListDetails.DiscountRate as DiscountRate, PriceListDetails.Remarks as Remarks," +
                    conversionFormula + " as Conversion  FROM PItem " +
                    "LEFT JOIN RetailerImages on cast (PItem.ItemId as int)= RetailerImages.ImageId " +
                    "INNER JOIN PriceListDetails on  cast(PItem.ItemId as int)  = PriceListDetails.ItemID and PriceListDetails.PriceListID = " + dist_price_list_Id +
                    " AND ITEMGROUPID =  cast('" + sub_groupId + "' as int)";

        } else if (divisionId.equalsIgnoreCase("") && sub_groupId.equalsIgnoreCase("") && !brandId.equalsIgnoreCase("")) {

            MyApplication.logi(LOG_TAG, "division,sub_groupId, is empty but brand is not empty ");
            query = "SELECT  RetailerImages.ImageDetails as ImagePath,PItem." + itemFilter + " as ITEM," +
                    " PItem." + itemFilter + "ID as ITEMID , PriceListDetails.DiscoutType as DiscoutType," +
                    "PriceListDetails.SalesRate as SalesRate, " +
                    "PriceListDetails.DiscountRate as DiscountRate, PriceListDetails.Remarks as Remarks," +
                    conversionFormula + " as Conversion  FROM PItem " +
                    "LEFT JOIN RetailerImages on cast (PItem.ItemId as int)= RetailerImages.ImageId " +
                    "INNER JOIN PriceListDetails on  cast(PItem.ItemId as int)  = PriceListDetails.ItemID and PriceListDetails.PriceListID = " + dist_price_list_Id +
                    " AND BRANDID = cast('" + brandId + "' as int)";


            MyApplication.logi(LOG_TAG, "QUERY FOR getITEm IS-->" + query);
        } else if (divisionId.equalsIgnoreCase("") && !sub_groupId.equalsIgnoreCase("") && !brandId.equalsIgnoreCase("")) {
            MyApplication.logi(LOG_TAG, "only div is is empty");
            query = "SELECT  RetailerImages.ImageDetails as ImagePath,PItem." + itemFilter + " as ITEM," +
                    " PItem." + itemFilter + "ID as ITEMID , PriceListDetails.DiscoutType as DiscoutType," +
                    "PriceListDetails.SalesRate as SalesRate, " +
                    "PriceListDetails.DiscountRate as DiscountRate, PriceListDetails.Remarks as Remarks," +
                    conversionFormula + " as Conversion  FROM PItem " +
                    "LEFT JOIN RetailerImages on cast (PItem.ItemId as int)= RetailerImages.ImageId " +
                    "INNER JOIN PriceListDetails on  cast(PItem.ItemId as int)  = PriceListDetails.ItemID and PriceListDetails.PriceListID = " + dist_price_list_Id +
                    " AND ITEMGROUPID =  cast('" + sub_groupId + "' as int) AND BRANDID = cast('" + brandId + "' as int)";

        } else if (divisionId.equalsIgnoreCase("") && sub_groupId.equalsIgnoreCase("") && brandId.equalsIgnoreCase("")) {
            MyApplication.logi(LOG_TAG, "NOTHINGGGGGG DIVISION subgroup brandId IS NOT SELECTED");


            query = "SELECT  RetailerImages.ImageDetails as ImagePath,PItem." + itemFilter + " as ITEM," +
                    " PItem." + itemFilter + "ID as ITEMID , PriceListDetails.DiscoutType as DiscoutType," +
                    "PriceListDetails.SalesRate as SalesRate, " +
                    "PriceListDetails.DiscountRate as DiscountRate, PriceListDetails.Remarks as Remarks," +
                    conversionFormula + " as Conversion  FROM PItem " +
                    "LEFT JOIN RetailerImages on cast (PItem.ItemId as int)= RetailerImages.ImageId " +
                    "INNER JOIN PriceListDetails on  cast(PItem.ItemId as int)  = PriceListDetails.ItemID and PriceListDetails.PriceListID = " + dist_price_list_Id;



                    //executed query original
                    /*SELECT  RetailerImages.ImageDetails as ImagePath,PItem.ITEM as ITEM, PItem.ITEMID as ITEMID ,
             PriceListDetails.DiscoutType as DiscoutType,PriceListDetails.SalesRate as SalesRate,
             PriceListDetails.DiscountRate as DiscountRate, PriceListDetails.Remarks as Remarks,
             PriceListDetails.SalesRate*Pitem.BPC as Conversion  FROM PItem LEFT JOIN RetailerImages on cast
             (PItem.ItemId as int)= RetailerImages.ImageId INNER JOIN PriceListDetails on
              cast(PItem.ItemId as int)  = PriceListDetails.ItemID and PriceListDetails.PriceListID = 2*/



/*

            query = "SELECT  RetailerImages.ImageDetails as ImagePath,PItem." + itemFilter + " as ITEM," +
                    " PItem." + itemFilter + "ID as ITEMID , PriceListDetails.DiscoutType as DiscoutType," +
                    "PriceListDetails.SalesRate as SalesRate, " +
                    "PriceListDetails.DiscountRate as DiscountRate, PriceListDetails.Remarks as Remarks," +
                    conversionFormula + " as Conversion  FROM PItem " +
                    "LEFT JOIN RetailerImages on cast (PItem.ItemId as int)= RetailerImages.ImageId , " +
                    " PriceListDetails where  cast(PItem.ItemId as int)  = PriceListDetails.ItemID and PriceListDetails.PriceListID = " + dist_price_list_Id;



*/



        } else if (divisionId.equalsIgnoreCase("") && sub_groupId.equalsIgnoreCase("") && !brandId.equalsIgnoreCase("")) {
            MyApplication.logi(LOG_TAG, "div id and  sub gropu is empty++++++++++" + brandId);


            query = "SELECT  RetailerImages.ImageDetails as ImagePath,PItem." + itemFilter + " as ITEM," +
                    " PItem." + itemFilter + "ID as ITEMID , PriceListDetails.DiscoutType as DiscoutType," +
                    "PriceListDetails.SalesRate as SalesRate, " +
                    "PriceListDetails.DiscountRate as DiscountRate, PriceListDetails.Remarks as Remarks," +
                    conversionFormula + " as Conversion  FROM PItem " +
                    "LEFT JOIN RetailerImages on cast (PItem.ItemId as int)= RetailerImages.ImageId " +
                    "INNER JOIN PriceListDetails on  cast(PItem.ItemId as int)  = PriceListDetails.ItemID and PriceListDetails.PriceListID = " + dist_price_list_Id +
                    " AND BRANDID = cast('" + brandId + "' as int)";
            /* " AND DIVISIONID = cast('" + divisionId + "' as int) AND" +
                    " ITEMGROUPID =  cast('" + sub_groupId + "' as int) AND BRANDID = cast('" + brandId + "' as int)";*/
        } else if (divisionId.equalsIgnoreCase("") && !brandId.equalsIgnoreCase("") && sub_groupId.equalsIgnoreCase("")) {
            MyApplication.logi(LOG_TAG, "div id is empty++++++++++ and sub grp is  empty" + brandId);


            query = "SELECT  RetailerImages.ImageDetails as ImagePath,PItem." + itemFilter + " as ITEM," +
                    " PItem." + itemFilter + "ID as ITEMID , PriceListDetails.DiscoutType as DiscoutType," +
                    "PriceListDetails.SalesRate as SalesRate, " +
                    "PriceListDetails.DiscountRate as DiscountRate, PriceListDetails.Remarks as Remarks," +
                    conversionFormula + " as Conversion  FROM PItem " +
                    "LEFT JOIN RetailerImages on cast (PItem.ItemId as int)= RetailerImages.ImageId " +
                    "INNER JOIN PriceListDetails on  cast(PItem.ItemId as int)  = PriceListDetails.ItemID and PriceListDetails.PriceListID = " + dist_price_list_Id +
                    "  BRANDID = cast('" + brandId + "' as int)";
            /* " AND DIVISIONID = cast('" + divisionId + "' as int) AND" +
                    " ITEMGROUPID =  cast('" + sub_groupId + "' as int) AND BRANDID = cast('" + brandId + "' as int)";*/
        } else {

            MyApplication.logi(LOG_TAG, "yes everything is selected DIVISION subgroup brandId IS  SELECTED");
            query = "SELECT  RetailerImages.ImageDetails as ImagePath,PItem." + itemFilter + " as ITEM," +
                    " PItem." + itemFilter + "ID as ITEMID , PriceListDetails.DiscoutType as DiscoutType," +
                    "PriceListDetails.SalesRate as SalesRate, " +
                    "PriceListDetails.DiscountRate as DiscountRate, PriceListDetails.Remarks as Remarks," +
                    conversionFormula + " as Conversion  FROM PItem " +
                    "LEFT JOIN RetailerImages on cast (PItem.ItemId as int)= RetailerImages.ImageId " +
                    "INNER JOIN PriceListDetails on  cast(PItem.ItemId as int)  = PriceListDetails.ItemID and PriceListDetails.PriceListID = " + dist_price_list_Id +
                    " AND DIVISIONID = cast('" + divisionId + "' as int) AND" +
                    " ITEMGROUPID =  cast('" + sub_groupId + "' as int) AND BRANDID = cast('" + brandId + "' as int)";

        }
        MyApplication.logi(LOG_TAG, "QUERY FOR getITEm IS-->" + query);
        try {
            Cursor c = db.rawQuery(query, null);
            int count = c.getCount();
            MyApplication.logi(LOG_TAG, "COUNT IN GET ITEMS IS--->" + count);
            if (count > 0) {
                MyApplication.logi(LOG_TAG, "COUNT IN GET ITEMS IS--->" + count);
                c.moveToFirst();
                do {
                    item = c.getString(c.getColumnIndexOrThrow("ITEM"));
                    String divId = c.getString(c.getColumnIndexOrThrow("ITEMID"));
                    // MyApplication.logi(LOG_TAG, "divId-->" + divId);
                    item_id = Integer.parseInt(divId);
                    MyApplication.set_session(MyApplication.SESSION_ITEM_ID, divId);
                    disc_type = c.getString(c.getColumnIndexOrThrow("DiscoutType"));
                    discount_rate = c.getString(c.getColumnIndexOrThrow("DiscountRate"));
                    MyApplication.logi(LOG_TAG, "Rajani" + discount_rate);

                    remarks = c.getString(c.getColumnIndexOrThrow("Remarks"));
                    sales_rate = c.getFloat(c.getColumnIndexOrThrow("SalesRate")); //for per bottle before discount
                    bpc = c.getFloat(c.getColumnIndexOrThrow("Conversion"));
                    imageDetails = c.getBlob(c.getColumnIndexOrThrow("ImagePath"));


                    //case_value = (sales_rate * bpc);
                  /*  MyApplication.logi(LOG_TAG, "imageDetails>" + imageDetails);
                    MyApplication.logi(LOG_TAG, "ITEMM IS-->" + item + "idd isss->" + item_id);
                    MyApplication.logi(LOG_TAG, "disc_type IS-->" + disc_type + "disc _rate isss->" + discount_rate + "Remarks-->" + remarks + "BPC--->" + bpc + "SALESSS RATE--->" + sales_rate);
*/

                    SubItemDataModel subItemsModel = new SubItemDataModel(discount_rate, disc_type, sales_rate, bpc, item_id, item, remarks, "0", "2", "3", imageDetails, 0.f, "", "");
                    res.add(subItemsModel);
                } while (c.moveToNext());

            }

            c.close();
        } catch (Exception e) {
            MyApplication.logi(LOG_TAG, "getITEm in Exception" + e.getMessage());
        }


        return res;
    }


    public static int getSalesbottle(String item_id) {

        MyApplication.logi(LOG_TAG, "in getSalesRate");
        int count;
        int resp = 0;
        MyApplication.logi(LOG_TAG, "in getSalesRate()" + item_id);
        SQLiteDatabase db = MyApplication.db.getWritableDatabase();
        //String query = "SELECT * from "+NAME+" where "+"PriceListID" +" =" + dist_id+"";
        String query = "SELECT DISTINCT SalesRate from PriceListDetails where ItemId =" + item_id;
        MyApplication.logi("", "In getSalesRate query :" + query);
        Cursor c = db.rawQuery(query, null);
        count = c.getCount();
        MyApplication.logi(LOG_TAG, "COUNT OF getSalesRate IS-->" + count);
        if (count > 0) {
            c.moveToFirst();
            do {
                try {
                    resp = Integer.parseInt(c.getString(c.getColumnIndexOrThrow("SalesRate")));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                MyApplication.logi(LOG_TAG, "RESPPPPP-->" + resp);
            } while (c.moveToNext());
        }

        return resp;

    }

    public static int getSalescase(String item_id) {

        MyApplication.logi(LOG_TAG, "in getSalesRate");
        int count;
        int resp = 0;
        MyApplication.logi(LOG_TAG, "in getSalesRate()" + item_id);
        SQLiteDatabase db = MyApplication.db.getWritableDatabase();
        //String query = "SELECT * from "+NAME+" where "+"PriceListID" +" =" + dist_id+"";
        String query = "SELECT DISTINCT SalesRate from PriceListDetails where ItemId =" + item_id;
        MyApplication.logi("", "In getSalesRate query :" + query);
        Cursor c = db.rawQuery(query, null);
        count = c.getCount();
        MyApplication.logi(LOG_TAG, "COUNT OF getSalesRate IS-->" + count);
        if (count > 0) {
            c.moveToFirst();
            do {
                try {
                    resp = Integer.parseInt(c.getString(c.getColumnIndexOrThrow("SalesRate")));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                MyApplication.logi(LOG_TAG, "RESPPPPP-->" + resp);
            } while (c.moveToNext());
        }

        return resp;

    }

    public static void deleteTableData() {
        MyApplication.logi(LOG_TAG, "in delete pitem");
        SQLiteDatabase db = MyApplication.db.getWritableDatabase();


        db.delete(NAME, null, null);


    }

    public static String getItemName(String item_id) {

        MyApplication.logi(LOG_TAG, "in getItemName" + item_id);
        String itemName = "";

        SQLiteDatabase db = MyApplication.db.getReadableDatabase();
        String qurry2 = "SELECT Item as itemName from PItem where cast(PItem.ItemId as int) =" + item_id;
        MyApplication.logi(LOG_TAG, "QUERY FOR getItemName IS-->" + qurry2);
        try {
            Cursor c = db.rawQuery(qurry2, null);
            int count = c.getCount();
            if (count > 0) {
                c.moveToFirst();
                do {
                    itemName = c.getString(c.getColumnIndexOrThrow("itemName"));
                    MyApplication.logi(LOG_TAG, "itemName FOR getItemName IS-->" + itemName);
                } while (c.moveToNext());


            }
            c.close();
        } catch (Exception e) {
            MyApplication.logi(LOG_TAG, "getBrand in Exception" + e.getMessage());
        }

        return itemName;
    }


    public static boolean tableHasData() {
        int num = -1;

        MyApplication.logi(LOG_TAG, "PItem in tableHasData()");
        SQLiteDatabase db = MyApplication.db.getWritableDatabase();
        String query = "SELECT * from PItem";
        MyApplication.log("In PItem tableHasData query :" + query);
        Cursor c = db.rawQuery(query, null);
        num = c.getCount();
        MyApplication.log("PItem has_tbl_data num is count : " + num);
        if (num > 0) {
            MyApplication.log("PItem data is there : " + num);
            c.close();
            return true;
        } else {
            MyApplication.log("PItem data is not there : " + num);
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