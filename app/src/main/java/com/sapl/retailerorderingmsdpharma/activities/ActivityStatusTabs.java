package com.sapl.retailerorderingmsdpharma.activities;

import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.sapl.retailerorderingmsdpharma.MyDatabase.TABLE_ORDER_DETAILS;
import com.sapl.retailerorderingmsdpharma.MyDatabase.TABLE_ORDER_STATUS;
import com.sapl.retailerorderingmsdpharma.MyDatabase.TABLE_PDISTRIBUTOR;
import com.sapl.retailerorderingmsdpharma.MyDatabase.TABLE_RETAILER_ORDER_MASTER;
import com.sapl.retailerorderingmsdpharma.MyDatabase.TABLE_TEMP_ORDER_DETAILS;
import com.sapl.retailerorderingmsdpharma.R;
import com.sapl.retailerorderingmsdpharma.ServerCall.HTTPVollyRequest;
import com.sapl.retailerorderingmsdpharma.ServerCall.MyListener;
import com.sapl.retailerorderingmsdpharma.customView.CircularTextView;
import com.sapl.retailerorderingmsdpharma.customView.CustomTextViewMedium;
import com.sapl.retailerorderingmsdpharma.observer.MilkLtrsObservable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static com.sapl.retailerorderingmsdpharma.activities.MyApplication.LOG_TAG;


//Order Booked status activity
public class ActivityStatusTabs extends TabActivity {
    Context context;
    String LOG_TAG = "ActivityStatusTabs";
    public static CircularTextView txt_no_of_product_taken;
    ImageView img_cart, img_sync,img_menu;
    public static TabHost tabHost;
    SwipeRefreshLayout mSwipeRefreshLayout;
    LinearLayout linearlayout;
    RelativeLayout rl1;
    CustomTextViewMedium txt_order_rs,txt_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_tabs);
        context = this;
        initTabHost();
        initComponents();
        initComponentListner();

    }

    private void initComponentListner() {
        MyApplication.logi(LOG_TAG,"IN  initComponentListner OF TABS");

        img_menu = findViewById(R.id.img_menu);
        img_menu.setVisibility(View.GONE);
        txt_title  = findViewById(R.id.txt_title);
        txt_title.setText("Order Booked Status");
        txt_title.setTextColor((Color.parseColor(MyApplication.get_session(MyApplication.SESSION_Text_Primary_Color))));
        img_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.displayMessage(context,"This feature is coming soon...");
                //Toast.makeText(context, "This feature is coming soon", Toast.LENGTH_SHORT).show();

            }
        });



        img_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyApplication.set_session("distributor_list", "cart");
                Intent intent = new Intent(ActivityStatusTabs.this, ActivityDistributorList.class);
                finish();
                overridePendingTransition(R.anim.fade_in_call, R.anim.fade_out_call);
                startActivity(intent);
            }
        });


        img_sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.logi(LOG_TAG, "IN img_sync clicked");

                //  String url = "http://zylemdemo.com/RetailerOrdering/api/GetOrders/RetailerOrder?user=8551020051&password=default&deviceid=1fd1148b7a8d6a44&clientid=1&token=Token1&userType=4";
                //  String url = "http://zylemdemo.com/RetailerOrdering/api/GetOrders/RetailerOrder?user=" + MyApplication.get_session(MyApplication.SESSION_USER_NAME_LOGIN) + "&password=" + MyApplication.get_session(MyApplication.SESSION_PASSWORD_LOGIN) + "&deviceid="+MyApplication.get_session(MyApplication.SESSION_DEVICE_ID)+"&clientid="+MyApplication.get_session(MyApplication.SESSION_CLIENT_ID_LOGIN)+"&token=" + MyApplication.get_session(MyApplication.SESSION_FCM_ID) + "&userType=4";




                //from direct login page
                String url = "http://zylemdemo.com/RetailerOrdering/api/GetOrders/RetailerOrder?user="+MyApplication.get_session(MyApplication.SESSION_USER_NAME_LOGIN)+"&password="+MyApplication.get_session(MyApplication.SESSION_PASSWORD_LOGIN)+"&deviceid=1fd1148b7a8d6a44&clientid="+MyApplication.get_session(MyApplication.SESSION_CLIENT_ID_LOGIN)+"&token=" + MyApplication.get_session(MyApplication.SESSION_FCM_ID) + "&userType=4";



                MyApplication.logi(LOG_TAG, "IN img_sync clicked url--->" + url);

                new HTTPVollyRequest(1, null, 0, "Please wait loading orders..", context,
                        url, getRetailerOrderResp, null);


            }
        });


    }

    private void initComponents() {
        MyApplication.logi(LOG_TAG,"IN  INIinitComponents OF TABS");
        img_cart = findViewById(R.id.img_cart);
        rl1 = findViewById(R.id.rl1);
        rl1.setBackgroundColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_Accent_Color)));

        img_sync = findViewById(R.id.img_sync);
        linearlayout = findViewById(R.id.relativeLayout);
        txt_no_of_product_taken = findViewById(R.id.txt_no_of_product_taken);
        txt_no_of_product_taken.setStrokeColor(MyApplication.get_session(MyApplication.SESSION_THEME_DARK_COLOR));
        txt_no_of_product_taken.setTextColor(getResources().getColor(R.color.heading_background));
        int main_cart_count = TABLE_PDISTRIBUTOR.countOfAddToCardItems();
        txt_no_of_product_taken.setText(main_cart_count + "");
        MyApplication.logi(LOG_TAG, "COUNT OF CARTS MAIN IS-->" + main_cart_count);
    }


    MyListener getRetailerOrderResp = new MyListener() {

        @Override
        public void success(Object obj) {

            try {
                MyApplication.logi("JARVIS", "img_sync in response of swipe getRetailerOrderResp fom login--->");
                JSONObject resObj = new JSONObject(obj.toString());
                if (resObj != null && resObj.has("success")) {
                    MyApplication.logi("JARVIS", "img_sync getRetailerOrderResp " + resObj.toString());
                    String status = resObj.getString("success");
                    if (status.equalsIgnoreCase("true")) {
                        MyApplication.logi(LOG_TAG, "img_sync getRetailerOrderResp successss" + status);
                        MyApplication.logi(LOG_TAG, "img_sync RESP DATA---->" + resObj.getString("data"));

                        JSONObject jsonObject = new JSONObject(resObj.getString("data"));

                        if (jsonObject.has("OrderMaster")) {
                            //update the TABLE ORDER MASTER
                            MyApplication.logi(LOG_TAG, "img_sync HAS OrderMaster");

                            TABLE_RETAILER_ORDER_MASTER.deleteOrderMaster();
                            TABLE_RETAILER_ORDER_MASTER.updateData(jsonObject.getJSONArray("OrderMaster"));


                        }


                        if (jsonObject.has("OrderDetails")) {
                            //update the TABLR ORDER DETAILS
                            MyApplication.logi(LOG_TAG, "img_sync HAS OrderDetails");
                            //TABLE_ORDER_DETAILS.updateData(jsonObject.getJSONArray("OrderDetails"));
                            TABLE_ORDER_DETAILS.deletedOrderDetails();
                            TABLE_ORDER_DETAILS.insert_bulk_OrderDetails(jsonObject.getJSONArray("OrderDetails"));

                        }


                        if (jsonObject.has("OrderStatus")) ;
                        {
                            JSONArray orderStatusArray = new JSONArray();
                            MyApplication.logi(LOG_TAG, "img_sync HAS ORDER STATUS");
                            MyApplication.logi(LOG_TAG, "img_sync ORDERSTATUS------->" + jsonObject.getJSONArray("OrderStatus"));

                            TABLE_ORDER_STATUS.deleteOrderStatusData();
                            TABLE_ORDER_STATUS.insert_bulk_OrderStatus(jsonObject.getJSONArray("OrderStatus"));

                            int delivered_no = TABLE_ORDER_STATUS.getDeliveryStatusCount();
                            MyApplication.logi(LOG_TAG, "img_sync HAS ORDER STATUSdelivered_no" + delivered_no);


                            int delivery_pending_no = TABLE_ORDER_STATUS.getPendingCount();
                            MyApplication.logi(LOG_TAG, "img_sync HAS ORDER STATUS delivery_pending_no" + delivery_pending_no);

                            int order_rejected_no = TABLE_ORDER_STATUS.getRejectedCount();
                            MyApplication.logi(LOG_TAG, "img_sync HAS ORDER STATUS order_rejected_no" + order_rejected_no);

                            MilkLtrsObservable milkLtrsObservable = new MilkLtrsObservable();
                            milkLtrsObservable.notifysetValues();

                            /*Intent i = new Intent(getApplicationContext(), ActivityDashBoard.class);
                            finish();
                            overridePendingTransition(R.anim.fade_in_call, R.anim.fade_out_call);
                            startActivity(i);*/
                        }

                    } else if (status.equalsIgnoreCase("false")) {
                        MyApplication.logi(LOG_TAG, "img_sync getRetailerOrderResp unsuccesss" + status);
                        String error = resObj.get("error").toString();
                        MyApplication.logi(LOG_TAG, "img_sync getRetailerOrderResp error isss->>>>" + error);
                        // showDialogOK(resObj.getString("response_message"), context, resObj.getString("response_status"));
                    }
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        @Override
        public void failure(Object obj) {
            MyApplication.logi(LOG_TAG, "img_sync getRetailerOrderResp failure ");

            final android.support.v7.app.AlertDialog.Builder dlgAlert = new android.support.v7.app.AlertDialog.Builder(context);

            // dlgAlert.setMessage("Please check your internet connection and try again..");

            dlgAlert.setMessage("Some problem occured..Please try after some time");

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


    private void initTabHost() {
        MyApplication.logi(LOG_TAG,"IN  INITTAB HOST");
        tabHost = (TabHost) findViewById(android.R.id.tabhost); // initiate TabHost
        TabHost.TabSpec spec;
        Intent intent;

        spec = tabHost.newTabSpec("0");
        // Create a new TabSpec using tab host
        spec.setIndicator("ALL");
        intent = new Intent(getApplicationContext(), AcitivityAllStatusList.class);
        spec.setContent(intent);

        tabHost.addTab(spec);


        // Do the same for the other tabs
        spec = tabHost.newTabSpec("1"); // Create a new TabSpec using tab host
        spec.setIndicator("PROCESSING");
        intent = new Intent(getApplicationContext(), ActivityPendingStatus.class);
        spec.setContent(intent);

        tabHost.addTab(spec);


        spec = tabHost.newTabSpec("2");
        spec.setIndicator("ACCEPTED");
        intent = new Intent(getApplicationContext(), ActivityAcceptedStatus.class);
        spec.setContent(intent);
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("3");
        spec.setIndicator("REJECTED");
        intent = new Intent(getApplicationContext(), ActivityRejectedStatus.class);
        spec.setContent(intent);
        tabHost.addTab(spec);


        tabHost.getTabWidget().setDividerDrawable(null);
        tabHost.setPadding(0, 0, 0, 0);
        tabHost.setCurrentTab(0);
        // tabHost.getTabWidget().getChildAt(0).setBackgroundColor(Color.parseColor("#ffffff"));
        tabHost.getTabWidget().setStripEnabled(false);

        for (int j = 0; j < tabHost.getTabWidget().getChildCount(); j++) {

            TextView tv = (TextView) tabHost.getTabWidget().getChildAt(j).findViewById(android.R.id.title);
            tv.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_Text_Secondary_Color)));
            tv.setSingleLine();
        }

        TextView tv = (TextView) tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).findViewById(android.R.id.title);
        tv.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_THEME_DARK_COLOR)));

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {

            @Override
            public void onTabChanged(String arg0) {

                int tab = tabHost.getCurrentTab();

                for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
//            TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);

                    TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
                    //  tabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#0074C1"));
                    tv.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_Text_Secondary_Color)));
                }

                // When tab is selected
                TextView tv = (TextView) tabHost.getTabWidget().getChildAt(tab).findViewById(android.R.id.title);
                tv.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_THEME_DARK_COLOR)));


            }
        });


    }


    @Override
    public void onBackPressed() {

        MyApplication.logi(LOG_TAG, "in back pressed");
        Intent intent = new Intent(getApplicationContext(), ActivityDashBoard.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        //startActivity(intent);
        finish();
        overridePendingTransition(R.anim.fade_in_call, R.anim.fade_out_call);
        startActivity(intent);

    }


}
