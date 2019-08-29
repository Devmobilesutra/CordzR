package com.sapl.retailerorderingmsdpharma.activities;

import android.app.Application;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;

import com.sapl.retailerorderingmsdpharma.MyDatabase.MyDataBaseD;
import com.sapl.retailerorderingmsdpharma.MyDatabase.TABLE_COLOR_THEME;
import com.sapl.retailerorderingmsdpharma.MyDatabase.TABLE_MENU_MASTER;
import com.sapl.retailerorderingmsdpharma.MyDatabase.TABLE_PCUSTOMER;
import com.sapl.retailerorderingmsdpharma.MyDatabase.TABLE_PDISTRIBUTOR;
import com.sapl.retailerorderingmsdpharma.MyDatabase.TABLE_PITEM;
import com.sapl.retailerorderingmsdpharma.MyDatabase.TABLE_PRICELIST_DETAILS;
import com.sapl.retailerorderingmsdpharma.MyDatabase.TABLE_PRICE_LIST;
import com.sapl.retailerorderingmsdpharma.MyDatabase.TABLE_SETTINGS;
import com.sapl.retailerorderingmsdpharma.MyDatabase.TABLE_UOMMASTER;
import com.sapl.retailerorderingmsdpharma.R;
import com.sapl.retailerorderingmsdpharma.confiq.AESAlgorithm;
import com.sapl.retailerorderingmsdpharma.customView.CustomButtonBold;
import com.sapl.retailerorderingmsdpharma.customView.CustomTextViewRegular;
import com.sapl.retailerorderingmsdpharma.models.DashBoardData;
import com.sapl.retailerorderingmsdpharma.models.DashBoardElementModel;
import com.sapl.retailerorderingmsdpharma.models.DistributorModel;
import com.sapl.retailerorderingmsdpharma.models.OrderReviewModel;
import com.sapl.retailerorderingmsdpharma.models.SelectionDataModel;
import com.sapl.retailerorderingmsdpharma.models.SubItemDataModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * Created by MRUNAL on 07-Feb-18.
 */

public class MyApplication extends Application {

    static String LOG_TAG = "ZyleminiApp ";
    static Context context = null;

    private static ProgressDialog proDialog = null;

    static JSONObject json;
    static JSONObject jsonobject;
    static JSONArray jsonarray;

    //Shared Preferences Variables
    public static SharedPreferences sharedPref;
    public static SharedPreferences.Editor editor;
    public static String SessionKey = "j5aD9uweHEAncWdj";//Must have 16 character session key
    public static AESAlgorithm aes;
    private String PREFS_NAME = "dsfwr34r334_r4#23e2e";
    public static MyDataBaseD db;


    //Color session
    public static String SESSION_BLACK_COLOR = "SESSION_BLACK_COLOR"; //this is nothing but theme color
    public static String SESSION_HEADING_TEXT_COLOR = "heading_text_color";
    public static String SESSION_CART_CIRCLE_COLOR = "cart_circle_color";
    public static String SESSION_ADD_TO_CART_BTN_BACKGROUND = "add_to_cart";
    public static String SESSION_ADDED_TO_CART_ITEM_BACKGROUND_1 = "after_ading_to_cart_";
    public static String SESSION_PRIMARY_TEXT_COLOR = "blck_clr"; ///text primary color
    public static String SESSION_PRIMARY_TEXT_COLOR_SERVER= "SESSION_PRIMARY_TEXT_COLOR_SERVER";
    public static String SESSION_HEADING_BACKGROUND_COLOR= "SESSION_HEADING_BACKGROUND_COLOR";

    //color values sent by server
    public static String SESSION_THEME_DARK_COLOR = "theme_Dark_Color"; //this is nothing but theme color
    public static String SESSION_THEME_LIGHT_COLOR = "theme_light_Color";
    public static String SESSION_Theme_Primary_Text_Color = "SESSION_Theme_Primary_Text_Color";
    public static String SESSION_Theme_Secondary_Text_Color = "SESSION_Theme_Secondary_Text_Color";
    public static String SESSION_Text_Primary_Color = "SESSION_Text_Primary_Color";
    public static String SESSION_Text_Secondary_Color = "SESSION_Text_Secondary_Color";
    public static String SESSION_Highlight_Color = "SESSION_Highlight_Color";
    public static String SESSION_Accent_Color = "SESSION_Accent_Color";


    //SESSION VARIABLES
    public static String SESSION_SHOP_NAME = "shop_name";
    public static String SESSION_RETAILER_OWNER_NAME = "ret_owner_name";
    public static String SESSION_CONTACT_NO = "contact_no";
    public static String SESSION_OWNER_CONTACT_NO = "owner_contactct_no";
    public static String SESSION_AREA = "area";
    public static String SESSION_PIN = "pin";
    public static String SESSION_LOCATION = "location";
    public static String SESSION_REGISTRATION_NO = "reg_no";
    public static String SESSION_GST_NO = "gst_no";
    public static String SESSION_LICENSE_NO = "license_no";
    public static String SESSION_PASSWORD = "password";
    public static String SESSION_MAIL_ID = "mail_id";
    public static String SESSION_DEVICE_ID = "device_id";
    public static String SESSION_FCM_ID = "fcm_id";
    public static String SESSION_FCM_TOKEN = "fcm_token";
    public static String SESSION_DATA_OTP = "data_otp";


    //dashboard sessions
    public static String SESSION_ORDER_BOOKING = "session_order_booking";
    public static String SESSION_ORDER_STATUS = "session_booking_status";
    public static String SESSION_RESOURSE_PAGE = "session_resourse_page";
    public static String SESSION_UPDATE_PPROFILE = "sssion_update_profile";

    public static String SESSION_FLAG_USER_TYPE = "user_type";
    public static String SESSION_VALUE_FROM_DB = "db_value";
    public static String SESSION_DISTRIBUTOR_ID = "session_distributor_id";
    public static String SESSION_FILTER_DIVISION = "session_filter_division";
    public static String SESSION_GROUP_ID = "session_grp_id";
    public static String SESSION_SUB_GROUP_ID = "session_sub_grp";
    public static String SESSION_BRAND_ID = "sessio_brand_id";
    public static String SESSION_UOM_VALUE_FIRST = "session_uom_val_1";
    public static String SESSION_UOM_VALUE_SECOND = "session_uom_val_2";
    public static String SESSION_DIST_PRICE_LIST_ID = "session_price_list_id";
    public static String SESSION_ORDER_ID = "session_order_id";
    public static String SESSION_UOM_ORDER_1 = "SESSION_UOM_ORDER_1";
    public static String SESSION_UOM_ORDER_2 = "SESSION_UOM_ORDER_2";
    public static String SESSION_ITEM_ID = "SESSION_ITEM_ID";
    public static String SESSION_DISTRIBUTER_NAME = "session_dist_name";
    public static String SESSION_DISTRIBUTER_NAME1 = "session_dist_name1";
    public static String SESSION_DISTRIBUTER_ID= "session_dist_id";
    public static String SESSION_RETAILER_NAME = "session_ret_name";
    public static String SESSION_ORDER_DATE = "SESSION_ORDER_DATE";
    public static String SESSION_LOGIN="session_login";
    public static String SESSION_USER_NAME_LOGIN = "session_user_name_login";
    public static String SESSION_PASSWORD_LOGIN = "session_pwd_login";
    public static String SESSION_CLIENT_ID_LOGIN = "clientId_login";
    public static String SESSION_DIVISION_ID = "division__id";

    public static String SESSION_ORDER_ID_FROM_CART = "order_id_from_cart";
public  static  String SESSION_LOGIN_REQUIRED = "login_required";

    //FOR DASHBOARD VALUES
    public static String SESSION_TOTAL_RPS_FOR_DASHBOARD_VALUE = "SESSION_TOTAL_RPS_FOR_DASHBOARD_VALUE";
    public static String SESSION_SUBMIT_ORDER_CARTS_COUNT = "SESSION_SUBMIT_ORDER_CARTS_COUNT";
    public static String SESSION_DATE_FOR_LAST_ORDER_PLACED = "SESSION_DATE_FOR_LAST_ORDER_PLACED";


    private static final String server_url = "http://zylemdemo.com/RetailerOrdering/api/";
    //  public static String device_id_2 = "1fd1148b7a8d6a44";

    //    public static final String login_url = server_url + "Login/Retailer?user=" + MyApplication.get_session(MyApplication.SESSION_USER_NAME_LOGIN) + "&password=" + MyApplication.get_session(MyApplication.SESSION_PASSWORD_LOGIN) + "&deviceid=1fd1148b7a8d6a44&clientid=" + MyApplication.get_session(MyApplication.SESSION_CLIENT_ID_LOGIN) + "&token=" + MyApplication.get_session(MyApplication.SESSION_FCM_ID) + "&userType=4";
    public static final String urlRegisterRetailer = server_url + "RetailerRegistraion/RegisterRetailer";
    public static final String urlGetOtp = server_url + "RetailerRegistraion/GetOtpData";
    public static final String urlPlaceOrder = server_url + "RetailerRegistraion/OrderPlacing";


    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        sharedPref = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        aes = new AESAlgorithm();

        if (db == null) {
            db = new MyDataBaseD(context);
            db.getReadableDatabase();

            sharedPref = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            editor = sharedPref.edit();
            aes = new AESAlgorithm();

        }


        setColor();
        set_session(SESSION_PRIMARY_TEXT_COLOR, "#000000");
        set_session(SESSION_HEADING_BACKGROUND_COLOR, "#424242");
    }


    //this color is used over all the project as theme clr
    public void setColor() {


        // String abc = "019998";
        /*String theme_Dark_Color = TABLE_COLOR_THEME.get_color_value_from_tbl("1");
        String theme_Light_Color = TABLE_COLOR_THEME.get_color_value_from_tbl("2");
        String theme_Primary_Text_Color = TABLE_COLOR_THEME.get_color_value_from_tbl("3");
        String theme_Secondary_Text_Color = TABLE_COLOR_THEME.get_color_value_from_tbl("4");
        String text_Primary_Color = TABLE_COLOR_THEME.get_color_value_from_tbl("5");
        String text_Secondary_Color = TABLE_COLOR_THEME.get_color_value_from_tbl("6");
        String highlight_Color = TABLE_COLOR_THEME.get_color_value_from_tbl("7");
        String accent_Color = TABLE_COLOR_THEME.get_color_value_from_tbl("8");

        MyApplication.logi(LOG_TAG, "theme_Dark_Color-->" + theme_Dark_Color);

        set_session(SESSION_HEADING_BACKGROUND_COLOR_SERVER, theme_Dark_Color);///---->theme_Dark_Color
        set_session(SESSION_HEADING_TEXT_COLOR, accent_Color);///-->ACCENT COLOR
        set_session(SESSION_PRIMARY_TEXT_COLOR_SERVER, theme_Primary_Text_Color);


        set_session(SESSION_THEME_LIGHT_COLOR, theme_Light_Color);
        // set_session(SESSION_Theme_Primary_Text_Color, theme_Primary_Text_Color);
        set_session(SESSION_Theme_Secondary_Text_Color, theme_Secondary_Text_Color);
            set_session(SESSION_Text_Primary_Color, text_Primary_Color);
        set_session(SESSION_Text_Secondary_Color, text_Secondary_Color);
        set_session(SESSION_Highlight_Color, highlight_Color);


         set_session(SESSION_HEADING_BACKGROUND_COLOR, "#019998");
        set_session(SESSION_HEADING_TEXT_COLOR, "#FFFFFF");
        set_session(SESSION_CART_CIRCLE_COLOR, "#b0bec5");
            set_session(SESSION_ADD_TO_CART_BTN_BACKGROUND, "#f8bbd0");
        set_session(SESSION_ADDED_TO_CART_ITEM_BACKGROUND_1, "#f8bbd0");
        set_session(SESSION_BLACK_COLOR, "#000000");
*/

    }


    public static void set_session(String key, String value) {
        // Log.i(LOG_TAG, "Key=" + key + ", value=" + value);
        String temp_key = aes.Encrypt(key);
        String temp_value = aes.Encrypt(value);
        MyApplication.editor.putString(temp_key, temp_value);
        MyApplication.editor.commit();
    }

    public static String get_session(String key) {
        String temp_key = aes.Encrypt(key);
        if (sharedPref.contains(temp_key)) {
            //d(TAG+", get_session() "+aes.Decrypt(sharedPref.getString(temp_key, "")));
            return aes.Decrypt(sharedPref.getString(temp_key, ""));
        } else
            return "";
    }


    public static void e(String msg) {
        if (msg.length() > 4000) {
            Log.e(LOG_TAG, msg);
            e(msg.substring(4000));
        } else {
            Log.e(LOG_TAG, msg);
        }
    }

    /**
     * print debug message
     */
    public static void d(String msg) {
        if (msg.length() > 4000) {
            Log.d(LOG_TAG, msg);
            d(msg.substring(4000));
        } else {
            Log.d(LOG_TAG, msg);
        }
    }


    public static void logi(String TAG, String str) {
        if (str.length() > 10000000) {
            Log.i(LOG_TAG, TAG + "->" + str.substring(0, 10000000));
            logi(TAG, str.substring(10000000));
        } else
            Log.i(LOG_TAG, TAG + "->" + str);
    }

    public static void log(String str) {
        if (str.length() > 10000000) {
            Log.i(LOG_TAG, str.substring(0, 10000000));
            logi(LOG_TAG, str.substring(10000000));
        } else
            Log.i(LOG_TAG, "->" + str);
    }

    public static void showDialog(Context mContext, String message) {
        MyApplication.logi(LOG_TAG, " in showDialog() message-" + message);

        try {

            if (mContext != null) {
                if (proDialog == null)
                    proDialog = ProgressDialog.show(mContext, null, message);
                proDialog.setCancelable(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stopLoading() {
        try {
            if (proDialog != null)
                proDialog.dismiss();
            proDialog = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //check internet connection
    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        android.net.NetworkInfo networkinfo = cm.getActiveNetworkInfo();
        if (networkinfo != null && networkinfo.isConnected()) {
            return true;
        }
        return false;
    }


    public static void displayMessage(final Context context, String msg) {

        CustomButtonBold btn_ok = null;
        CustomTextViewRegular tv_text = null;
        RelativeLayout rrlayout;
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.show_dialog_order_for_all);
        dialog.getWindow().setLayout(ActionBar.LayoutParams.FILL_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        btn_ok = (CustomButtonBold) dialog.findViewById(R.id.btn_ok);
        btn_ok.setBackgroundColor((Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR))));
        rrlayout = (RelativeLayout) dialog.findViewById(R.id.rrlayout);
        tv_text = (CustomTextViewRegular) dialog.findViewById(R.id.tv_text);

        rrlayout.setBackgroundColor((Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR))));


        tv_text.setText(msg);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(context.getApplicationContext(), ActivitySelection.class);
                //overridePendingTransition(R.anim.fade_in_call, R.anim.fade_out_call);
                context.startActivity(intent);

                // onBackPressed();
                /*Intent intent = new Intent(context, ActivityDashBoard.class);
                finish();
                overridePendingTransition(R.anim.fade_in_call, R.anim.fade_out_call);
                startActivity(intent);*/
            }
        });

        dialog.setCancelable(false);
        dialog.show();
        dialog.closeOptionsMenu();
    }


    public static void displayMessage_old(final Context context, String msg) {
        // MyApplication.log(LOG_TAG, "in showDDOK()message=" + message);
        final AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);

        dlgAlert.setMessage(msg);


        dlgAlert.setTitle("Retailer");
        dlgAlert.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //dismiss the dialog

                    }


                });
        dlgAlert.setCancelable(false);
        dlgAlert.show();
    }


    public static void noInterNetDialog(final Context context) {
        MyApplication.logi(LOG_TAG, "NO INTERNET");
        // MyApplication.log(LOG_TAG, "in showDDOK()message=" + message);
        final AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);

        dlgAlert.setMessage("Please check Your Internet connection and then try...");


        dlgAlert.setTitle("Retailer");
        dlgAlert.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //dismiss the dialog

                    }
                });
        dlgAlert.setCancelable(false);
        dlgAlert.show();
    }


    public static void createDataBase() {
        MyApplication.logi(LOG_TAG, "in createDatabase");

        File sd = Environment.getExternalStorageDirectory();
        boolean success = true;
        if (!sd.exists()) {
            success = sd.mkdir();
        }
        if (success) {

            // File data = Environment.getDataDirectory();
            FileChannel source = null;
            FileChannel destination = null;
            //     String currentDBPath = "/data/user/0/com.example.r_b_k.recyclerview/databases/DB3";
            String currentDBPath = "/data/data/com.sapl.retailerorderingmsdpharma/databases/db_zylemini_retailer.db";
            // log("current path--->"+currentDBPath);
            //   = "/data/"+ getApplicationContext().getPackageName() +"/"+"DB1";
            String backupDBPath = "ZyleminiRetailer";
                    /* File tmpDir = new File(backupDBPath);
                     boolean exists = tmpDir.exists();
                     if(!exists){
                          sd = new File(sd,backupDBPath);
                     }*/
            File currentDB = new File(currentDBPath);
            File backupDB = new File(sd, backupDBPath);
            try {

                source = new FileInputStream(currentDB).getChannel();
                // PrintWriter out = new PrintWriter( source+".db");
                destination = new FileOutputStream(backupDB + ".db").getChannel();
                destination.transferFrom(source, 0, source.size());

                source.close();
                destination.close();
                //       Toast.makeText(this, "Please wait", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    public static void deleteAllDatabase() {
        TABLE_PDISTRIBUTOR.deleteTableData();
        TABLE_PITEM.deleteTableData();
        TABLE_SETTINGS.deleteTableData();
        TABLE_MENU_MASTER.deleteTableData();
        TABLE_PRICE_LIST.deleteTableData();
        TABLE_PRICELIST_DETAILS.deleteTableData();
        TABLE_PCUSTOMER.deleteTableData();
        TABLE_UOMMASTER.deleteTableData();
    }


    public static int insertServerData(String Response) {
        MyApplication.logi(LOG_TAG, "Retailer IN insertServerDta---->" + Response.toString());
        //  MyApplication.logi(LOG_TAG, "LENGTH IS-->" + Response.length());

        if (Response.length() != 0) {

            try {
                json = new JSONObject(Response);
                // ResponseSuccessString = json.getString("success");
                //   MyApplication.logi(LOG_TAG, "ResponseSuccessString-->" + ResponseSuccessString);

            /*    if (ResponseSuccessString.equals("false")) {
                    String erros = json.getString("error");
                    MyApplication.set_session("ERROR", erros);
                    return 0;
                } else {*/
                //write a function to delete if user is present
                /***********Parsing data start from here ***************/


                /***********PDistributor***********/

                if (json.has("PDistributor")) {

                    jsonarray = json.getJSONArray("PDistributor");

                    if (jsonarray.length() != 0) {
                        TABLE_PDISTRIBUTOR.insert_bulk_PDistributor(jsonarray);
                    }
                }

                /**************PItem***********/

                if (json.has("PItem")) {
                    jsonarray = json.getJSONArray("PItem");
                    if (jsonarray.length() != 0) {
                        TABLE_PITEM.insert_bulk_PItem(jsonarray);
                    }

                }
                /***********Settings*************/

                if (json.has("Settings")) {
                    jsonarray = json.getJSONArray("Settings");
                    if (jsonarray.length() != 0) {
                        TABLE_SETTINGS.insert_bulk_Settings(jsonarray);
                    }
                }

                /*********MenuMaster***********/
                if (json.has("MenuMaster")) {
                    jsonarray = json.getJSONArray("MenuMaster");
                    if (jsonarray.length() != 0) {
                        TABLE_MENU_MASTER.insert_bulk_MenuMaster(jsonarray);
                    }
                }

                /**********PriceList************/
                if (json.has("PriceList")) {
                    jsonarray = json.getJSONArray("PriceListDetails");
                    if (jsonarray.length() != 0) {
                        TABLE_PRICE_LIST.insert_bulk_PriceList(jsonarray);
                    }
                }

                /*********PriceListDetails***********/
                if (json.has("PriceListDetails")) {
                    jsonarray = json.getJSONArray("PriceListDetails");
                    if (jsonarray.length() != 0) {
                        TABLE_PRICELIST_DETAILS.insert_bulk_PriceListDetails(jsonarray);
                    }
                }

                /*************PCustomer*******************/
                if (json.has("PCustomer")) {
                    jsonarray = json.getJSONArray("PCustomer");
                    if (jsonarray.length() != 0) {
                        TABLE_PCUSTOMER.insert_bulk_PCustomer(jsonarray);
                    }
                }


                /**************UOMMaster*************/

                if (json.has("UomMaster")) {
                    jsonarray = json.getJSONArray("UomMaster");
                    if (jsonarray.length() != 0) {
                        TABLE_UOMMASTER.insert_bulk_UomMaster(jsonarray);
                    }
                }


                if (json.has("colortheme")) {
                    jsonarray = json.getJSONArray("colortheme");
                    if (jsonarray.length() != 0) {
                        TABLE_COLOR_THEME.insert_bulk_theme_color(jsonarray);
                    }
                }


                return 1;

                //  }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                logi(LOG_TAG, "Errors->" + e.getMessage());
                return 0;
            } catch (Exception e) {
                e.printStackTrace();
                logi(LOG_TAG, "Errors->" + e.getMessage());
                return 0;
            }
        } else {
            return 0;
        }
    }


    public static boolean isMarshMellow() {
        return android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    public static ArrayList getValuesFromDb() {
        MyApplication.logi(LOG_TAG, "in getValuesFromDb");
        String resp = "";
        SQLiteDatabase db = MyApplication.db.getReadableDatabase();
        ArrayList<DashBoardData> data = new ArrayList<>();
        // User user=new User();
        String querytogetuserdata = "SELECT * FROM " + TABLE_MENU_MASTER.NAME
                + " where UserType ='1'";
        try {
            Cursor c = db.rawQuery(querytogetuserdata, null);
            int count = c.getCount();

            if (count > 0) {
                c.moveToFirst();
                do {
                    data.add(new DashBoardData(c.getString(c.getColumnIndexOrThrow("LabelName")), c.getString(c.getColumnIndexOrThrow("IsActive")), c.getString(c.getColumnIndexOrThrow("MenuKey"))));

                } while (c.moveToNext());
            }
            MyApplication.logi(LOG_TAG, "RESP ARE-->" + data.toString());
            if (data.size() != 0) {
                MyApplication.set_session(MyApplication.SESSION_VALUE_FROM_DB, "Y");
            }
            c.close();
        } catch (Exception e) {
            MyApplication.logi(LOG_TAG, "in Exception" + e.getMessage());
        }

        return data;
    }

    public static String getCurrentDate() {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);
        MyApplication.logi(LOG_TAG, "formattedDate-->" + formattedDate);
        return formattedDate;
    }

    public static String dateFormat() {

        Date d = new Date();
        DateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss") {
        };

        String formated_date = df.format(new Date());
        MyApplication.logi(LOG_TAG, "formated_date-->" + formated_date);
        return formated_date;
    }


    public static String dateFormatwithT() {

        Date d = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss") {
        };

        String formated_date = df.format(new Date());
        MyApplication.logi(LOG_TAG, "formated_date-->" + formated_date);
        return formated_date;
    }
}
