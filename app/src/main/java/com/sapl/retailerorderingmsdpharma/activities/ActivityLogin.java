package com.sapl.retailerorderingmsdpharma.activities;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Base64;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
;
//import com.google.android.gms.location.LocationRequest;
//import com.google.android.gms.location.LocationServices;
import com.google.firebase.iid.FirebaseInstanceId;

//LOGIN PAGE
import com.sapl.retailerorderingmsdpharma.MyDatabase.TABLE_COLOR_THEME;
import com.sapl.retailerorderingmsdpharma.MyDatabase.TABLE_ORDER_DETAILS;
import com.sapl.retailerorderingmsdpharma.MyDatabase.TABLE_ORDER_STATUS;
import com.sapl.retailerorderingmsdpharma.MyDatabase.TABLE_RETAILER_IMAGES;
import com.sapl.retailerorderingmsdpharma.MyDatabase.TABLE_RETAILER_ORDER_MASTER;
import com.sapl.retailerorderingmsdpharma.MyDatabase.TABLE_SETTINGS;
import com.sapl.retailerorderingmsdpharma.R;
import com.sapl.retailerorderingmsdpharma.ServerCall.HTTPVollyRequest;
import com.sapl.retailerorderingmsdpharma.ServerCall.MyListener;
import com.sapl.retailerorderingmsdpharma.customView.CircularTextView;
import com.sapl.retailerorderingmsdpharma.customView.CustomButtonRegular;
import com.sapl.retailerorderingmsdpharma.customView.CustomEditTextMedium;
import com.sapl.retailerorderingmsdpharma.customView.CustomTextViewMedium;
import com.sapl.retailerorderingmsdpharma.customView.CustomTextViewRegular;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;

import static com.sapl.retailerorderingmsdpharma.activities.MyApplication.LOG_TAG;
import static com.sapl.retailerorderingmsdpharma.activities.MyApplication.SESSION_ADDED_TO_CART_ITEM_BACKGROUND_1;
import static com.sapl.retailerorderingmsdpharma.activities.MyApplication.SESSION_ADD_TO_CART_BTN_BACKGROUND;
import static com.sapl.retailerorderingmsdpharma.activities.MyApplication.SESSION_Accent_Color;
import static com.sapl.retailerorderingmsdpharma.activities.MyApplication.SESSION_BLACK_COLOR;
import static com.sapl.retailerorderingmsdpharma.activities.MyApplication.SESSION_CART_CIRCLE_COLOR;
import static com.sapl.retailerorderingmsdpharma.activities.MyApplication.SESSION_HEADING_BACKGROUND_COLOR;

import static com.sapl.retailerorderingmsdpharma.activities.MyApplication.SESSION_HEADING_TEXT_COLOR;
import static com.sapl.retailerorderingmsdpharma.activities.MyApplication.SESSION_Highlight_Color;
import static com.sapl.retailerorderingmsdpharma.activities.MyApplication.SESSION_LOGIN_REQUIRED;
import static com.sapl.retailerorderingmsdpharma.activities.MyApplication.SESSION_PRIMARY_TEXT_COLOR_SERVER;
import static com.sapl.retailerorderingmsdpharma.activities.MyApplication.SESSION_THEME_DARK_COLOR;
import static com.sapl.retailerorderingmsdpharma.activities.MyApplication.SESSION_THEME_LIGHT_COLOR;
import static com.sapl.retailerorderingmsdpharma.activities.MyApplication.SESSION_Text_Primary_Color;
import static com.sapl.retailerorderingmsdpharma.activities.MyApplication.SESSION_Text_Secondary_Color;
import static com.sapl.retailerorderingmsdpharma.activities.MyApplication.SESSION_Theme_Primary_Text_Color;
import static com.sapl.retailerorderingmsdpharma.activities.MyApplication.SESSION_Theme_Secondary_Text_Color;

public class ActivityLogin extends AppCompatActivity/* implements LocationListener */ {

    CustomTextViewRegular txt_sign_up_link, txt_forgot_password;
    Button btn_login;
    CustomEditTextMedium edt_username, edt_password, edt_client_ID;
    CustomTextViewMedium txt_title;
    Context context;
    CircularTextView txt_no_of_product_taken;
    ImageView img_shop_logo,img_back,img_cart;
    static String LOG_TAG = "ActivityLogin";

    private double fusedLatitude = 0.0;
    private double fusedLongitude = 0.0;
    String firebaseToken = "";
    private int permission_count = 5;
    String deviceId = "";
    String username = "";
    String password = "";
    String clientId = "";
    Animation animBounce ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;

        MyApplication.logi(LOG_TAG, "ON CRATE OF LOGIN");
        if (MyApplication.isMarshMellow()) {
            if (getPermissionCount() > 0)
                check_app_persmission();

        }
        /*TelephonyManager TelephonyMgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)
            return;
        deviceId = TelephonyMgr.getDeviceId();*/


        deviceId = Settings.Secure.getString(getApplication().getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);

        MyApplication.logi(LOG_TAG, "DEVICE ID--->" + deviceId);


        initComponants();
        initComponantListner();


    }

    private void setColorValues() {

        String theme_Dark_Color = TABLE_COLOR_THEME.get_color_value_from_tbl("1");
        String theme_Light_Color = TABLE_COLOR_THEME.get_color_value_from_tbl("2");
        String theme_Primary_Text_Color = TABLE_COLOR_THEME.get_color_value_from_tbl("3");
        String theme_Secondary_Text_Color = TABLE_COLOR_THEME.get_color_value_from_tbl("4");
        String text_Primary_Color = TABLE_COLOR_THEME.get_color_value_from_tbl("5");
        String text_Secondary_Color = TABLE_COLOR_THEME.get_color_value_from_tbl("6");
        String highlight_Color = TABLE_COLOR_THEME.get_color_value_from_tbl("7");
        String accent_Color = TABLE_COLOR_THEME.get_color_value_from_tbl("8");

        MyApplication.logi(LOG_TAG, "theme_Dark_Color-->" + theme_Dark_Color);

        MyApplication.set_session(SESSION_THEME_DARK_COLOR, theme_Dark_Color);///---->theme_Dark_Color
        MyApplication.set_session(SESSION_HEADING_TEXT_COLOR, accent_Color);///-->ACCENT COLOR
        MyApplication.set_session(SESSION_Accent_Color, accent_Color);///-->ACCENT COLOR
        MyApplication.set_session(SESSION_PRIMARY_TEXT_COLOR_SERVER, theme_Primary_Text_Color);


        MyApplication.set_session(SESSION_THEME_LIGHT_COLOR, theme_Light_Color);
        MyApplication.set_session(SESSION_Theme_Primary_Text_Color, theme_Primary_Text_Color);
        MyApplication.set_session(SESSION_Theme_Secondary_Text_Color, theme_Secondary_Text_Color);
        MyApplication.set_session(SESSION_Text_Primary_Color, text_Primary_Color);
        MyApplication. set_session(SESSION_Text_Secondary_Color, text_Secondary_Color);
        MyApplication.set_session(SESSION_Highlight_Color, highlight_Color);


      //  MyApplication.set_session(SESSION_HEADING_BACKGROUND_COLOR, "#019998");
        MyApplication.set_session(SESSION_HEADING_TEXT_COLOR, "#FFFFFF");
        MyApplication.set_session(SESSION_CART_CIRCLE_COLOR, "#b0bec5");
        MyApplication.set_session(SESSION_ADD_TO_CART_BTN_BACKGROUND, "#f8bbd0");
        MyApplication.set_session(SESSION_ADDED_TO_CART_ITEM_BACKGROUND_1, "#f8bbd0");
        MyApplication.set_session(SESSION_BLACK_COLOR, "#000000");

    }


    public void initComponants() {
        img_back = findViewById(R.id.img_back);
        img_back.setVisibility(View.GONE);

        img_cart = findViewById(R.id.img_cart);
        img_cart.setVisibility(View.GONE);
        txt_no_of_product_taken = findViewById(R.id.txt_no_of_product_taken);
        txt_no_of_product_taken.setVisibility(View.GONE);

        // findViewById(R.id.rel_toolbar).setBackgroundColor(getResources().getColor(R.color.white));

        edt_username = findViewById(R.id.edt_username);
        edt_username.setText("1111111111");
        edt_client_ID = findViewById(R.id.edt_client_ID);
        edt_client_ID.setText("1");
        edt_password = findViewById(R.id.edt_password);
        edt_password.setText("default");
        img_shop_logo = findViewById(R.id.img_shop_logo);
        edt_password.setTransformationMethod(PasswordTransformationMethod.getInstance());

        btn_login = findViewById(R.id.btn_login);

     //   btn_login.setTextColor((Color.parseColor(MyApplication.get_session(MyApplication.SESSION_Accent_Color))));
//        btn_login.setBackgroundColor((Color.parseColor(MyApplication.get_session(MyApplication.SESSION_THEME_DARK_COLOR))));

        txt_sign_up_link = findViewById(R.id.txt_sign_up_link);
        txt_forgot_password = findViewById(R.id.txt_forgot_password);
        animBounce = AnimationUtils.loadAnimation(context,R.anim.bounce);
        // txt_forgot_password.setAnimation(animBounce);
        // txt_sign_up_link.setAnimation(animBounce);
        txt_title  = findViewById(R.id.txt_title);
        txt_title.setTextColor((Color.parseColor(MyApplication.get_session(MyApplication.SESSION_PRIMARY_TEXT_COLOR))));


//for client id 7
 /* edt_password.setText("jnjretailer");
        edt_username.setText("2222222222");
        edt_client_ID.setText("7");

*/


//for client id 1
       /* edt_password.setText("default");
        edt_username.setText("1111111111");
        edt_client_ID.setText("1");*/
    }

    public void initComponantListner() {
   /*     if(MyApplication.get_session(SESSION_LOGIN_REQUIRED).equalsIgnoreCase("NO")){
            MyApplication.logi(LOG_TAG, "SESSION_LOGIN_REQUIRED is no");
            Intent intent = new Intent(ActivityLogin.this,ActivityDashBoard.class);
            startActivity(intent);
            finish();

        }else{
            MyApplication.logi(LOG_TAG, "SESSION_LOGIN_REQUIRED is yes");
        }
*/
    //    animBounce = AnimationUtils.loadAnimation(context,R.anim.bounce);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MyApplication.logi(LOG_TAG, "in button login->");

                if (MyApplication.isOnline(context)) {

                    boolean has_data = TABLE_SETTINGS.tableHasData();

                    if (has_data == false) {

                        username = edt_username.getText().toString().trim();
                        password = edt_password.getText().toString().trim();
                        clientId = edt_client_ID.getText().toString().trim();

                        MyApplication.set_session(MyApplication.SESSION_USER_NAME_LOGIN, username);
                        MyApplication.set_session(MyApplication.SESSION_PASSWORD_LOGIN, password);
                        MyApplication.set_session(MyApplication.SESSION_CLIENT_ID_LOGIN, clientId);

                        MyApplication.logi(LOG_TAG, "username---->" + username);
                        MyApplication.logi(LOG_TAG, "password---->" + password);
                        MyApplication.logi(LOG_TAG, "clientId---->" + clientId);
                        //for dynamic login.ie after register-->
                        //   String url = "http://zylemdemo.com/RetailerOrdering/api/Login/Retailer?user=" + username + "&password=" + password + "&deviceid="+MyApplication.get_session(MyApplication.SESSION_DEVICE_ID)+"&clientid=" + clientId + "&token=" + MyApplication.get_session(MyApplication.SESSION_FCM_ID) + "&userType=4";

                        //static login-->
                        //  String url = "http://zylemdemo.com/RetailerOrdering/api/Login/Retailer?user=8551020051&password=default&deviceid=1fd1148b7a8d6a44&clientid=1&token=" + MyApplication.get_session(MyApplication.SESSION_FCM_ID) + "&userType=4";

                        //from direct login page
                        String url = "http://zylemdemo.com/RetailerOrdering/api/Login/Retailer?user="+MyApplication.get_session(MyApplication.SESSION_USER_NAME_LOGIN)+"&password="+MyApplication.get_session(MyApplication.SESSION_PASSWORD_LOGIN)+"&deviceid=1fd1148b7a8d6a44&clientid="+MyApplication.get_session(MyApplication.SESSION_CLIENT_ID_LOGIN)+"&token=" + MyApplication.get_session(MyApplication.SESSION_FCM_ID) + "&userType=4";


                        MyApplication.logi(LOG_TAG, "url isss->" + url);
                        MyApplication.logi(LOG_TAG, "URL_GET_DATA DEVICE ID---->" + MyApplication.get_session(MyApplication.SESSION_DEVICE_ID));
                        MyApplication.logi(LOG_TAG, "FCM ID-->" + "FCM ID-->" + MyApplication.get_session(MyApplication.SESSION_FCM_ID));
                        HashMap<String, String> hashmap = new HashMap<String, String>();

                        new HTTPVollyRequest(1, null, 0, "Please wait loading data..", context,
                                url, getdataRespListner, hashmap);


                    } else {
                        Intent i = new Intent(getApplicationContext(), ActivityDashBoard.class);
                        finish();
                        overridePendingTransition(R.anim.fade_in_call, R.anim.fade_out_call);
                        startActivity(i);

                    }
                } else {

                    MyApplication.displayMessage(context, "Please check your internet connection and try again.");
                }
            }
        });

        //SIGN UP ACTIVITY GOT TO REGRISTRATION
        txt_sign_up_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MyApplication.isOnline(context)) {
                    Intent i = new Intent(getApplicationContext(), ActivityRegisterOne.class);
                    finish();
                    overridePendingTransition(R.anim.fade_in_call, R.anim.fade_out_call);
                    startActivity(i);
                } else
                    MyApplication.displayMessage(context, "Please start your internet connection and try again.");


            }
        });
        txt_forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (MyApplication.isOnline(context)) {
                    MyApplication.displayMessage(context,"This feature is coming soon...");
                   /* Intent i = new Intent(getApplicationContext(), ActivityForgotPassword.class);
                    finish();
                    overridePendingTransition(R.anim.fade_in_call, R.anim.fade_out_call);
                    startActivity(i);*/
                } else
                    MyApplication.displayMessage(context, "Please start your internet connection and try again.");

            }
        });
    }


    MyListener getdataRespListner = new MyListener() {

        @Override
        public void success(Object obj) {

            try {
                MyApplication.logi("JARVIS", "getdataRespListner");
                JSONObject resObj = new JSONObject(obj.toString());
                if (resObj != null && resObj.has("success")) {
                    MyApplication.logi("JARVIS", "getdataRespListner " + resObj.toString());
                    String status = resObj.getString("success");
                    if (status.equalsIgnoreCase("true")) {

                        MyApplication.logi(LOG_TAG, "getdataRespListner successss" + status);
                        MyApplication.insertServerData(resObj.getString("data"));
                        // showDialogOK("Successfully Register", context, resObj.getString("response_status"));
                        MyApplication.createDataBase();


                        setColorValues();
                        // String url = "http://zylemdemo.com/RetailerOrdering/api/Login/GetImages?user=8551020051&password=default&deviceid=1fd1148b7a8d6a44&clientid=1&token=Token1&userType=4";
                        //String url = "http://zylemdemo.com/RetailerOrdering/api/Login/GetImages?user=" + username + "&password=" + password + "&deviceid="+MyApplication.get_session(MyApplication.SESSION_DEVICE_ID)+"&clientid=" + clientId + "&token=Token1&userType=4";


                        //from direct login page
                        String url = "http://zylemdemo.com/RetailerOrdering/api/Login/GetImages?user="+MyApplication.get_session(MyApplication.SESSION_USER_NAME_LOGIN)+"&password="+MyApplication.get_session(MyApplication.SESSION_PASSWORD_LOGIN)+"&deviceid=1fd1148b7a8d6a44&clientid="+MyApplication.get_session(MyApplication.SESSION_CLIENT_ID_LOGIN)+"&token=" + MyApplication.get_session(MyApplication.SESSION_FCM_ID) + "&userType=4";

                        MyApplication.logi(LOG_TAG, "url for images  isss->" + url);
                        HashMap<String, String> hashmap = new HashMap<String, String>();

                        new HTTPVollyRequest(1, null, 0, "Please wait loading images..", context,
                                url, getImageResponse, hashmap);


                    } else if (status.equalsIgnoreCase("false")) {
                        MyApplication.logi(LOG_TAG, "getdataRespListner unsuccesss" + status);
                        String error = resObj.get("error").toString();
                        MyApplication.logi(LOG_TAG, "getdataRespListner error isss->>>>" + error);
                        // showDialogOK(resObj.getString("response_message"), context, resObj.getString("response_status"));
                        showMessage(context, resObj.getString("message"));

                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        @Override
        public void failure(Object obj) {
            MyApplication.logi(LOG_TAG, "getRespListner failure ");

            final AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);

            dlgAlert.setMessage("Please enter valid credentials..");

            //dlgAlert.setMessage("Please check your internet connection and try again..");


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
    };


    MyListener getImageResponse = new MyListener() {

        @Override
        public void success(Object obj) {

            try {
                MyApplication.logi("JARVIS", "getImageResponse");
                JSONObject resObj = new JSONObject(obj.toString());
                if (resObj != null && resObj.has("success")) {
                    MyApplication.logi("JARVIS", "getImageResponse resp otp is " + resObj.toString());
                    String status = resObj.getString("success");
                    if (status.equalsIgnoreCase("true")) {
                        MyApplication.logi(LOG_TAG, "image DATA IS-->" + resObj.getString("data"));


                        JSONArray jsonArray = new JSONArray(resObj.getString("data"));
                        MyApplication.logi(LOG_TAG, "image DATA IS jsonArray-->" + jsonArray);
                        TABLE_RETAILER_IMAGES.insertImageData(jsonArray);
                        ///MyApplication.logi(LOG_TAG, "Message " + resObj.getString("message"));
                        MyApplication.logi(LOG_TAG, "getImageResponse successss" + status);

                        //call the get orders api

                        // String url = "http://zylemdemo.com/RetailerOrdering/api/GetOrders/RetailerOrder?user=8551020051&password=default&deviceid=1fd1148b7a8d6a44&clientid=1&token=Token1&userType=4";
                        //   String url = "http://zylemdemo.com/RetailerOrdering/api/GetOrders/RetailerOrder?user=" + MyApplication.get_session(MyApplication.SESSION_USER_NAME_LOGIN) + "&password=" + MyApplication.get_session(MyApplication.SESSION_PASSWORD_LOGIN) + "&deviceid="+MyApplication.get_session(MyApplication.SESSION_DEVICE_ID)+"&clientid="+MyApplication.get_session(MyApplication.SESSION_CLIENT_ID_LOGIN)+"&token=" + MyApplication.get_session(MyApplication.SESSION_FCM_ID) + "&userType=4";


                        //from direct login page
                        String url = "http://zylemdemo.com/RetailerOrdering/api/GetOrders/RetailerOrder?user="+MyApplication.get_session(MyApplication.SESSION_USER_NAME_LOGIN)+"&password="+MyApplication.get_session(MyApplication.SESSION_PASSWORD_LOGIN)+"&deviceid=1fd1148b7a8d6a44&clientid="+MyApplication.get_session(MyApplication.SESSION_CLIENT_ID_LOGIN)+"&token=" + MyApplication.get_session(MyApplication.SESSION_FCM_ID) + "&userType=4";
                        MyApplication.logi(LOG_TAG, "url for orders  isss->" + url);

                        new HTTPVollyRequest(1, null, 0, "Please wait loading orders..", context,
                                url, getRetailerOrderResp, null);


                    } else if (status.equalsIgnoreCase("false")) {
                        MyApplication.logi(LOG_TAG, "getImageResponse unsuccesss" + status);
                        String error = resObj.get("error").toString();
                        MyApplication.logi(LOG_TAG, "getImageResponse error isss->>>>" + error);
                        // showDialogOK(resObj.getString("response_message"), context, resObj.getString("response_status"));
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        @Override
        public void failure(Object obj) {
            MyApplication.logi(LOG_TAG, "getRespListner failure ");

            final AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);

            dlgAlert.setMessage("Please check your internet connection and try again..");
            //    dlgAlert.setMessage("Please enter valid credentials..");

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
    };

    MyListener getRetailerOrderResp = new MyListener() {

        @Override
        public void success(Object obj) {

            try {
                MyApplication.logi("JARVIS", "in response of swipe getRetailerOrderResp fom login--->");
                JSONObject resObj = new JSONObject(obj.toString());
                if (resObj != null && resObj.has("success")) {
                    MyApplication.logi("JARVIS", "getRetailerOrderResp " + resObj.toString());
                    String status = resObj.getString("success");
                    if (status.equalsIgnoreCase("true")) {
                        MyApplication.logi(LOG_TAG, "getRetailerOrderResp successss" + status);
                        MyApplication.logi(LOG_TAG, "RESP DATA---->" + resObj.getString("data"));

                        JSONObject jsonObject = new JSONObject(resObj.getString("data"));

                        if (jsonObject.has("OrderMaster")) {
                            //update the TABLE ORDER MASTER
                            MyApplication.logi(LOG_TAG, "HAS OrderMaster");

                            TABLE_RETAILER_ORDER_MASTER.deleteOrderMaster();
                            TABLE_RETAILER_ORDER_MASTER.updateData(jsonObject.getJSONArray("OrderMaster"));


                        }


                        if (jsonObject.has("OrderDetails")) {
                            //update the TABLR ORDER DETAILS
                            MyApplication.logi(LOG_TAG, "HAS OrderDetails");
                            //TABLE_ORDER_DETAILS.updateData(jsonObject.getJSONArray("OrderDetails"));
                            TABLE_ORDER_DETAILS.deletedOrderDetails();
                            TABLE_ORDER_DETAILS.insert_bulk_OrderDetails(jsonObject.getJSONArray("OrderDetails"));

                        }


                        if (jsonObject.has("OrderStatus")) ;
                        {
                            JSONArray orderStatusArray = new JSONArray();
                            MyApplication.logi(LOG_TAG, "HAS ORDER STATUS");
                            MyApplication.logi(LOG_TAG, "ORDERSTATUS------->" + jsonObject.getJSONArray("OrderStatus"));

                            TABLE_ORDER_STATUS.deleteOrderStatusData();
                            TABLE_ORDER_STATUS.insert_bulk_OrderStatus(jsonObject.getJSONArray("OrderStatus"));

                            int delivered_no = TABLE_ORDER_STATUS.getDeliveryStatusCount();
                            MyApplication.logi(LOG_TAG, "HAS ORDER STATUSdelivered_no" + delivered_no);


                            int delivery_pending_no = TABLE_ORDER_STATUS.getPendingCount();
                            MyApplication.logi(LOG_TAG, "HAS ORDER STATUS delivery_pending_no" + delivery_pending_no);

                            int order_rejected_no = TABLE_ORDER_STATUS.getRejectedCount();
                            MyApplication.logi(LOG_TAG, "HAS ORDER STATUS order_rejected_no" + order_rejected_no);

                            Intent i = new Intent(getApplicationContext(), ActivityDashBoard.class);
                            finish();
                            overridePendingTransition(R.anim.fade_in_call, R.anim.fade_out_call);
                            startActivity(i);
                        }
                            MyApplication.set_session(MyApplication.SESSION_LOGIN_REQUIRED,"NO");
                    } else if (status.equalsIgnoreCase("false")) {
                        MyApplication.logi(LOG_TAG, "getRetailerOrderResp unsuccesss" + status);
                        String error = resObj.get("error").toString();
                        MyApplication.logi(LOG_TAG, "getRetailerOrderResp error isss->>>>" + error);
                        // showDialogOK(resObj.getString("response_message"), context, resObj.getString("response_status"));
                    }
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        @Override
        public void failure(Object obj) {
            MyApplication.logi(LOG_TAG, "getRetailerOrderResp failure ");

            final android.support.v7.app.AlertDialog.Builder dlgAlert = new android.support.v7.app.AlertDialog.Builder(context);

            dlgAlert.setMessage("Please check your internet connection and try again..");
            //dlgAlert.setMessage("Please enter valid credentials..");

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
    };

    public int getPermissionCount() {
        int count = 5;
        int permission_granted = PackageManager.PERMISSION_GRANTED;
        int camera_permission = ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA);
        if (camera_permission == permission_granted)
            count -= 1;
        int storage_permission = ContextCompat.checkSelfPermission(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (storage_permission == permission_granted)
            count -= 1;
        int call_permission = ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE);
        if (call_permission == permission_granted)
            count -= 1;
        int coarse_permission = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION);
        if (coarse_permission == permission_granted)
            count -= 1;
        int fine_permission = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);
        if (fine_permission == permission_granted)
            count -= 1;
        return count;
    }

    @TargetApi(23)
    private void check_app_persmission() {
        permission_count = 5;
        int permission_granted = PackageManager.PERMISSION_GRANTED;
        MyApplication.logi(LOG_TAG, "PermissionGrantedCode->" + permission_granted);

        int storage_permission = ContextCompat.checkSelfPermission(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        MyApplication.logi(LOG_TAG, "StoragePermission->" + storage_permission);
        if (storage_permission == permission_granted)
            permission_count -= 1;

        int camera_permission = ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA);
        MyApplication.logi(LOG_TAG, "CameraPermission->" + camera_permission);
        if (camera_permission == permission_granted)
            permission_count -= 1;

        int location_permission = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);
        MyApplication.logi(LOG_TAG, "location_permission->" + location_permission);
        if (location_permission == permission_granted)
            permission_count -= 1;

        int location2_permission = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION);
        MyApplication.logi(LOG_TAG, "location_permission->" + location_permission);
        if (location2_permission == permission_granted)
            permission_count -= 1;

        int call_permission = ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE);
        MyApplication.logi(LOG_TAG, "call_permission->" + call_permission);
        if (call_permission == permission_granted)
            permission_count -= 1;

        MyApplication.logi(LOG_TAG, "PermissionCount->" + permission_count);

        if (permission_count > 0) {
            String permissionArray[] = new String[permission_count];

            for (int i = 0; i < permission_count; i++) {
                MyApplication.logi(LOG_TAG, "i->" + i);

                if (storage_permission != permission_granted) {
                    if (!Arrays.asList(permissionArray).contains(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        permissionArray[i] = android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
                        MyApplication.logi(LOG_TAG, "i->WRITE_EXTERNAL_STORAGE");
                        // break;
                    }
                }

                if (camera_permission != permission_granted) {
                    if (!Arrays.asList(permissionArray).contains(android.Manifest.permission.CAMERA)) {
                        permissionArray[i] = Manifest.permission.CAMERA;
                        MyApplication.logi(LOG_TAG, "i->CAMERA");
                        //break;
                    }
                }
                if (location_permission != permission_granted) {
                    if (!Arrays.asList(permissionArray).contains(Manifest.permission.ACCESS_FINE_LOCATION)) {
                        permissionArray[i] = Manifest.permission.ACCESS_FINE_LOCATION;
                        MyApplication.logi(LOG_TAG, "i->ACCESS_FINE_LOCATION");
                        //break;
                    }
                }
                if (location2_permission != permission_granted) {
                    if (!Arrays.asList(permissionArray).contains(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                        permissionArray[i] = Manifest.permission.ACCESS_COARSE_LOCATION;
                        MyApplication.logi(LOG_TAG, "i->ACCESS_COARSE_LOCATION");
                        //break;
                    }
                }
                if (call_permission != permission_granted) {
                    if (!Arrays.asList(permissionArray).contains(Manifest.permission.CALL_PHONE)) {
                        permissionArray[i] = Manifest.permission.CALL_PHONE;
                        MyApplication.logi(LOG_TAG, "i->CALL_PHONE");
                        //break;
                    }
                }

            }
            MyApplication.logi(LOG_TAG, "PermissionArray->" + Arrays.deepToString(permissionArray));

            requestPermissions(permissionArray, permission_count);
        }
    }

    public void showMessage(final Context context, String msg) {

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

}

