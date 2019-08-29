package com.sapl.retailerorderingmsdpharma.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sapl.retailerorderingmsdpharma.MyDatabase.TABLE_MENU_MASTER;
import com.sapl.retailerorderingmsdpharma.MyDatabase.TABLE_ORDER_DETAILS;
import com.sapl.retailerorderingmsdpharma.MyDatabase.TABLE_ORDER_STATUS;
import com.sapl.retailerorderingmsdpharma.MyDatabase.TABLE_PCUSTOMER;
import com.sapl.retailerorderingmsdpharma.MyDatabase.TABLE_PDISTRIBUTOR;
import com.sapl.retailerorderingmsdpharma.MyDatabase.TABLE_PITEM;
import com.sapl.retailerorderingmsdpharma.MyDatabase.TABLE_PRICELIST_DETAILS;
import com.sapl.retailerorderingmsdpharma.MyDatabase.TABLE_PRICE_LIST;
import com.sapl.retailerorderingmsdpharma.MyDatabase.TABLE_RETAILER_IMAGES;
import com.sapl.retailerorderingmsdpharma.MyDatabase.TABLE_RETAILER_ORDER_MASTER;
import com.sapl.retailerorderingmsdpharma.MyDatabase.TABLE_SETTINGS;
import com.sapl.retailerorderingmsdpharma.MyDatabase.TABLE_TEMP_ORDER_DETAILS;
import com.sapl.retailerorderingmsdpharma.MyDatabase.TABLE_TEMP_RETAILER_ORDER_MASTER;
import com.sapl.retailerorderingmsdpharma.MyDatabase.TABLE_UOMMASTER;
import com.sapl.retailerorderingmsdpharma.R;
import com.sapl.retailerorderingmsdpharma.ServerCall.HTTPVollyRequest;
import com.sapl.retailerorderingmsdpharma.ServerCall.MyListener;
import com.sapl.retailerorderingmsdpharma.customView.CircularTextView;
import com.sapl.retailerorderingmsdpharma.customView.CustomButtonBold;
import com.sapl.retailerorderingmsdpharma.customView.CustomTextViewMedium;
import com.sapl.retailerorderingmsdpharma.customView.CustomTextViewRegular;
import com.sapl.retailerorderingmsdpharma.models.DashBoardData;
import  com.sapl.retailerorderingmsdpharma.customView.CustomButtonRegular;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.System.exit;


//DASHBOARD PAGE
public class ActivityDashBoard extends AppCompatActivity implements
        SwipeRefreshLayout.OnRefreshListener ,NavigationView.OnNavigationItemSelectedListener {


    Context context;
    CircularTextView txt_order_cart_no, txt_delivered_no, txt_delivery_pending_no, txt_order_rejected_no,
            txt_resource_product_no, txt_resource_video_no, txt_resource_new_product_no;
    CardView card_order_booking, card_order_status, card_resource_page, card_update_profile;
    CustomTextViewMedium txt_order_booking, txt_order_status, txt_resource_page, txt_update_profile,txt_accept,txt_pendingg,txt_rejected;
    CustomTextViewMedium txt_order_booking_date, txt_order_status_date, txt_resource_date, txt_profile_update_date, txt_last_update;
    CustomTextViewMedium txt_order_rs;
    TextView txt_title;
    ImageView img_order_booking, img_order_status, img_resource, img_menu,img_profile_update;
    ImageView img_cart;
    static String LOG_TAG = "ActivityDashBoard ";
    SwipeRefreshLayout mSwipeRefreshLayout;
    RelativeLayout dash_rr;
    public static CircularTextView txt_no_of_product_taken;
 //   TextView txt_no_of_product_taken;
    Animation animMove ;
    TextView txt_ret_name1;
    String retailername;
    String retailernamenew;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        context = this;

        initComponants();
        initComponantListner();
        getdata();
        getRetailername();

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swifeRefresh);

        mSwipeRefreshLayout.setOnRefreshListener(this);

   //     mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {



        int main_cart_count = TABLE_PDISTRIBUTOR.countOfAddToCardItems();
        txt_no_of_product_taken.setText(main_cart_count + "");


        MyApplication.logi(LOG_TAG, "COUNT OF CARTS MAIN IS-->" + main_cart_count);
        TABLE_SETTINGS.getUomLabels();
        TABLE_SETTINGS.getUomOrderBookCal();
    }
    private void getRetailername() {
        MyApplication.logi(LOG_TAG, " Inn distributor_Name=");

        retailername =TABLE_PCUSTOMER.getRetailerNamenew();
        MyApplication.logi(LOG_TAG, "distributor_Name=" + retailername);
        retailernamenew= MyApplication.get_session(MyApplication.SESSION_RETAILER_NAME);
        MyApplication.logi(LOG_TAG, "distributor_Name sess=" + retailernamenew);
       /* for(DistributorModel_new dist:distributorname){
          distributor_Name=  dist.getName();
            MyApplication.logi(LOG_TAG, " distributor_Name=" + distributor_Name);
        }*/
    }

    MyListener getdataRespListner = new MyListener() {

        @Override
        public void success(Object obj) {

            try {
                MyApplication.logi("JARVIS", "in response of swipe getdataRespListner");
                JSONObject resObj = new JSONObject(obj.toString());
                if (resObj != null && resObj.has("success")) {
                    MyApplication.logi("JARVIS", "getdataRespListner " + resObj.toString());
                    String status = resObj.getString("success");
                    if (status.equalsIgnoreCase("true")) {
                        // MyApplication.logi(LOG_TAG, "OTP DATA IS-->" + resObj.getString("data"));
                        // otp_data = resObj.getString("data");


                        ///MyApplication.logi(LOG_TAG, "Message " + resObj.getString("message"));
                        MyApplication.logi(LOG_TAG, "getdataRespListner successss" + status);
                        MyApplication.deleteAllDatabase();
                        MyApplication.insertServerData(resObj.getString("data"));
                        // showDialogOK("Successfully Register", context, resObj.getString("response_status"));


                        //String url = "http://zylemdemo.com/RetailerOrdering/api/GetOrders/RetailerOrder?user=8551020051&password=default&deviceid=1fd1148b7a8d6a44&clientid=1&token=Token1&userType=4";
                      //  String url = "http://zylemdemo.com/RetailerOrdering/api/GetOrders/RetailerOrder?user=" + MyApplication.get_session(MyApplication.SESSION_USER_NAME_LOGIN) + "&password=" + MyApplication.get_session(MyApplication.SESSION_PASSWORD_LOGIN) + "&deviceid="+MyApplication.get_session(MyApplication.SESSION_DEVICE_ID)+"&clientid="+MyApplication.get_session(MyApplication.SESSION_CLIENT_ID_LOGIN)+"&token=" + MyApplication.get_session(MyApplication.SESSION_FCM_ID) + "&userType=4";

                        //from direct login page
                        String url = "http://zylemdemo.com/RetailerOrdering/api/GetOrders/RetailerOrder?user="+MyApplication.get_session(MyApplication.SESSION_USER_NAME_LOGIN)+"&password="+MyApplication.get_session(MyApplication.SESSION_PASSWORD_LOGIN)+"&deviceid=1fd1148b7a8d6a44&clientid="+MyApplication.get_session(MyApplication.SESSION_CLIENT_ID_LOGIN)+"&token=" + MyApplication.get_session(MyApplication.SESSION_FCM_ID) + "&userType=4";


                        HashMap<String, String> hashmap = new HashMap<String, String>();

                        new HTTPVollyRequest(1, null, 1, "", context,
                                url, getRetailerOrderResp, hashmap);

                        MyApplication.createDataBase();


                    } else if (status.equalsIgnoreCase("false")) {
                        MyApplication.logi(LOG_TAG, "getdataRespListner unsuccesss" + status);
                        String error = resObj.get("error").toString();
                        MyApplication.logi(LOG_TAG, "getdataRespListner error isss->>>>" + error);
                        // showDialogOK(resObj.getString("response_message"), context, resObj.getString("response_status"));
                    }
                }
                mSwipeRefreshLayout.setRefreshing(false);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        @Override
        public void failure(Object obj) {
            MyApplication.logi(LOG_TAG, "getRespListner failure ");

            final android.support.v7.app.AlertDialog.Builder dlgAlert = new android.support.v7.app.AlertDialog.Builder(context);

            dlgAlert.setMessage("Some problem occured..Please try after some time..");


            dlgAlert.setTitle("Retailer");
            dlgAlert.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            //dismiss the dialog

                        }


                    });
            mSwipeRefreshLayout.setRefreshing(false);
            dlgAlert.setCancelable(false);

            if(!((Activity) context).isFinishing()) {
                dlgAlert.show();
            }
        }
    };


    public void initComponants() {


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);


        View headerView = navigationView.getHeaderView(0);
        txt_ret_name1 = (TextView) headerView.findViewById(R.id.txt_dist_name1);
        txt_ret_name1.setText(MyApplication.get_session(MyApplication.SESSION_RETAILER_NAME));


        img_order_booking = findViewById(R.id.img_order_booking);
        img_order_booking.setColorFilter(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_THEME_DARK_COLOR)));;

        img_order_status = findViewById(R.id.img_order_status);
        img_order_status.setColorFilter(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_THEME_DARK_COLOR)));;

        img_resource = findViewById(R.id.img_resource);
        img_resource.setColorFilter(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_THEME_DARK_COLOR)));;


        img_profile_update = findViewById(R.id.img_profile_update);
        img_profile_update.setColorFilter(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_THEME_DARK_COLOR)));;




        animMove = AnimationUtils.loadAnimation(context,R.anim.move);
       img_cart = findViewById(R.id.img_cart);
       img_cart.setAnimation(animMove);
        dash_rr = findViewById(R.id.dash_rr);
        card_order_booking = findViewById(R.id.card_order_booking);
        card_order_status = findViewById(R.id.card_order_status);
        card_resource_page = findViewById(R.id.card_resource_page);
        card_resource_page.setBackgroundColor(context.getResources().getColor(R.color.teal_100));
        card_update_profile = findViewById(R.id.card_update_profile);
        card_update_profile.setBackgroundColor(context.getResources().getColor(R.color.teal_100));
        txt_title  = findViewById(R.id.txt_title);

       txt_title.setText("Dashboard");

      /*  card_order_booking.setAnimation(animMove);
        card_order_status.setAnimation(animMove);
        card_resource_page.setAnimation(animMove);
        card_update_profile.setAnimation(animMove);*/

      //  dash_rr.setBackgroundColor((Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR))));

       // dash_rr.setBackgroundColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_Accent_Color)));

        txt_no_of_product_taken = findViewById(R.id.txt_no_of_product_taken);
      /*  txt_no_of_product_taken.setStrokeColor(MyApplication.get_session(MyApplication.SESSION_HEADING_TEXT_COLOR));
        txt_no_of_product_taken.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR)));
*/

     //   txt_no_of_product_taken.setStrokeColor(MyApplication.get_session(MyApplication.SESSION_THEME_DARK_COLOR));

    //    txt_no_of_product_taken.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_Theme_Primary_Text_Color)));

        txt_order_booking = findViewById(R.id.txt_order_booking);
        txt_order_booking_date = findViewById(R.id.txt_order_booking_date);
        txt_order_rs = findViewById(R.id.txt_order_rs);
        txt_order_cart_no = findViewById(R.id.txt_order_cart_no);

        txt_order_booking.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_Text_Primary_Color)));
        txt_order_booking_date.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_Highlight_Color)));
        txt_order_rs.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_Text_Secondary_Color)));
        txt_order_cart_no.setStrokeColor(MyApplication.get_session(MyApplication.SESSION_THEME_DARK_COLOR));
        txt_order_cart_no.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_Theme_Primary_Text_Color)));

        txt_order_status = findViewById(R.id.txt_order_status);
        txt_delivered_no = findViewById(R.id.txt_delivered_no);
        txt_delivery_pending_no = findViewById(R.id.txt_delivery_pending_no);
        txt_order_rejected_no = findViewById(R.id.txt_order_rejected_no);
        txt_order_status_date = findViewById(R.id.txt_order_status_date);

        txt_order_status.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_Text_Primary_Color)));
     //   txt_order_status_date.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR)));
        txt_delivered_no.setStrokeColor("#008000");//set  this values in session for dynamic clrs
        txt_delivered_no.setTextColor(getResources().getColor(R.color.white));
        txt_delivery_pending_no.setStrokeColor("#f57c00");
        txt_delivery_pending_no.setTextColor(getResources().getColor(R.color.white));
        txt_order_rejected_no.setStrokeColor("#e84e40");
        txt_order_rejected_no.setTextColor(getResources().getColor(R.color.white));


        txt_resource_page = findViewById(R.id.txt_resource_page);
        txt_resource_page.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_Text_Primary_Color)));
        txt_resource_date = findViewById(R.id.txt_resource_date);
        txt_resource_date.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR)));

        txt_resource_product_no = findViewById(R.id.txt_resource_product_no);
        txt_resource_video_no = findViewById(R.id.txt_resource_video_no);
        txt_resource_new_product_no = findViewById(R.id.txt_resource_new_product_no);

        txt_resource_product_no.setStrokeColor(MyApplication.get_session(MyApplication.SESSION_THEME_DARK_COLOR));
        txt_resource_product_no.setTextColor(getResources().getColor(R.color.white));
        txt_resource_video_no.setStrokeColor(MyApplication.get_session(MyApplication.SESSION_THEME_DARK_COLOR));
        txt_resource_video_no.setTextColor(getResources().getColor(R.color.white));
        txt_resource_new_product_no.setStrokeColor(MyApplication.get_session(MyApplication.SESSION_THEME_DARK_COLOR));
        txt_resource_new_product_no.setTextColor(getResources().getColor(R.color.white));

        txt_update_profile = findViewById(R.id.txt_update_profile);
        txt_update_profile.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_Text_Primary_Color)));

        txt_profile_update_date = findViewById(R.id.txt_profile_update_date);
        txt_profile_update_date.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR)));

        txt_last_update = findViewById(R.id.txt_last_update);
        txt_last_update.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_Text_Secondary_Color)));



        txt_accept = findViewById(R.id.txt_accept);


        txt_pendingg = findViewById(R.id.txt_pendingg);

        txt_rejected = findViewById(R.id.txt_rejected);


        //dashboard values :---------
        if (MyApplication.get_session(MyApplication.SESSION_TOTAL_RPS_FOR_DASHBOARD_VALUE).equalsIgnoreCase("")) {
            txt_order_rs.setText("0.00");
        } else {
            txt_order_rs.setText(MyApplication.get_session(MyApplication.SESSION_TOTAL_RPS_FOR_DASHBOARD_VALUE));
        }

        if (MyApplication.get_session(MyApplication.SESSION_SUBMIT_ORDER_CARTS_COUNT).equalsIgnoreCase("")) {
            txt_order_cart_no.setText("0");
        } else {
            txt_order_cart_no.setText(MyApplication.get_session(MyApplication.SESSION_SUBMIT_ORDER_CARTS_COUNT));
        }

        if (MyApplication.get_session(MyApplication.SESSION_DATE_FOR_LAST_ORDER_PLACED).equalsIgnoreCase("")) {
            //txt_order_booking_date.setText("00-00-0000");
            txt_order_booking_date.setText("Date");
        } else {
            txt_order_booking_date.setText(MyApplication.get_session(MyApplication.SESSION_DATE_FOR_LAST_ORDER_PLACED));
        }


        //  int delivered_no = TABLE_ORDER_STATUS.getDeliveryStatusCount();
        int delivered_no = TABLE_RETAILER_ORDER_MASTER.getDeliveryStatusCount_retailer_order_master();
        MyApplication.logi(LOG_TAG, "ON CREATE HAS ORDER STATUSdelivered_no" + delivered_no);
        txt_delivered_no.setText(delivered_no + "");


        // int delivery_pending_no = TABLE_ORDER_STATUS.getPendingCount();
        int delivery_pending_no = TABLE_RETAILER_ORDER_MASTER.getPendingCount_retailer_order_master();
        MyApplication.logi(LOG_TAG, "ON CREATE HAS ORDER STATUS delivery_pending_no" + delivery_pending_no);
        txt_delivery_pending_no.setText(delivery_pending_no + "");


        //  int order_rejected_no = TABLE_ORDER_STATUS.getRejectedCount();
        int order_rejected_no = TABLE_RETAILER_ORDER_MASTER.getRejectedCount_retailer_order_master();
        MyApplication.logi(LOG_TAG, "ON CREATE  HAS ORDER STATUS order_rejected_no" + order_rejected_no);
        txt_order_rejected_no.setText(order_rejected_no + "");

        txt_title.setTextColor((Color.parseColor(MyApplication.get_session(MyApplication.SESSION_Text_Primary_Color))));
        txt_rejected.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_Text_Secondary_Color)));
        txt_pendingg.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_Text_Secondary_Color)));
        txt_accept.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_Text_Secondary_Color)));

    }

    public void initComponantListner() {

        img_menu = findViewById(R.id.img_menu);

      /*  img_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(context, "This feature is coming soon...", Toast.LENGTH_SHORT).show();
               // MyApplication.displayMessage(context,"This feature is coming soon...");

            }
        });*/

        //GOES THE THE PAGE WHERE ITEMS ARE ADDED TO CART BUT NOT YET SUBMITED
        img_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int countOfAddToCardItems=TABLE_PDISTRIBUTOR.countOfAddToCardItems();
                if(countOfAddToCardItems==0){
                    MyApplication.displayMessage(context,"No items are added to cart yet..");
                }else {

                    MyApplication.set_session("distributor_list", "cart");
                    Intent intent = new Intent(ActivityDashBoard.this, ActivityDistributorList.class);
                    finish();
                    overridePendingTransition(R.anim.fade_in_call, R.anim.fade_out_call);
                    startActivity(intent);
                }
            }
        });


        card_order_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyApplication.set_session("distributor_list", "card_order_booking");
                Intent intent = new Intent(getApplicationContext(), ActivityDistributorList.class);
                finish();
                overridePendingTransition(R.anim.fade_in_call, R.anim.fade_out_call);
                startActivity(intent);
            }
        });
        txt_order_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toast.makeText(getApplicationContext(), " card_order_booking is Pressed", Toast.LENGTH_SHORT).show();
            }
        });



// txt_delivered_no, txt_delivery_pending_no, txt_order_rejected_no,

       /* txt_order_rejected_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ActivityStatusTabs.class);
                intent.putExtra("tab_index", "2");
                finish();
                overridePendingTransition(R.anim.fade_in_call, R.anim.fade_out_call);
                startActivity(intent);

            }
        });

        txt_delivered_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ActivityStatusTabs.class);
                intent.putExtra("tab_index", "1");
                finish();
                overridePendingTransition(R.anim.fade_in_call, R.anim.fade_out_call);
                startActivity(intent);

            }
        });
        //txt_accept,txt_pendingg,txt_rejected
        txt_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ActivityStatusTabs.class);
                intent.putExtra("tab_index", "1");
                finish();
                overridePendingTransition(R.anim.fade_in_call, R.anim.fade_out_call);
                startActivity(intent);

            }
        });



        txt_rejected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ActivityStatusTabs.class);
                intent.putExtra("tab_index", "2");
                finish();
                overridePendingTransition(R.anim.fade_in_call, R.anim.fade_out_call);
                startActivity(intent);

            }
        });


*/


        card_order_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Intent intent = new Intent(getApplicationContext(), ActivityOrderBookedStatus.class);
                finish();
                overridePendingTransition(R.anim.fade_in_call, R.anim.fade_out_call);
                startActivity(intent);
                */


                Intent intent1 = new Intent(getApplicationContext(), ActivityStatusTabs.class);
                finish();
                overridePendingTransition(R.anim.fade_in_call, R.anim.fade_out_call);
                startActivity(intent1);

            }
        });
        txt_order_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getApplicationContext(), ActivityStatusTabs.class);
                finish();
                overridePendingTransition(R.anim.fade_in_call, R.anim.fade_out_call);
                startActivity(intent1);
            }
        });

        card_resource_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 //Toast.makeText(getApplicationContext(), " This feature is coming soon..", Toast.LENGTH_SHORT).show();
                MyApplication.displayMessage(context,"This feature is coming soon...");
            }
        });
        txt_resource_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyApplication.displayMessage(context,"This feature is coming soon...");
            }
        });

        card_update_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 //Toast.makeText(getApplicationContext(), " This feature is coming soon..", Toast.LENGTH_SHORT).show();
                MyApplication.displayMessage(context,"This feature is coming soon...");
            }
        });
        txt_update_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyApplication.displayMessage(context,"This feature is coming soon...");
            }
        });
    }


    private void getdata() {
        MyApplication.logi(LOG_TAG, "in getdata");
        if (!MyApplication.get_session(MyApplication.SESSION_VALUE_FROM_DB).equalsIgnoreCase("Y")) {
            MyApplication.logi(LOG_TAG, "in IF OF getdata");
            ArrayList<DashBoardData> values = MyApplication.getValuesFromDb();
            for (DashBoardData data : values) {

                if (data.getMenu_key().equalsIgnoreCase("ORDERBOOKING")) {
                    MyApplication.set_session(MyApplication.SESSION_ORDER_BOOKING, data.getLabel_name());
                    if (data.getIs_active().equalsIgnoreCase("false")) {
                        MyApplication.set_session("orderbooking", "false");
                        MyApplication.logi(LOG_TAG, "IS ACTIVE IS TRUE");
                        //card_order_booking.setClickable(false);
                        card_order_booking.setVisibility(View.GONE);
                    }
                }
                if (data.getMenu_key().equalsIgnoreCase("ORDERSTATUS")) {
                    MyApplication.set_session(MyApplication.SESSION_ORDER_STATUS, data.getLabel_name());
                    if (data.getIs_active().equalsIgnoreCase("false")) {
                        MyApplication.set_session("orderstatus", "false");
                        MyApplication.logi(LOG_TAG, "IS ACTIVE IS TRUE");
                        //card_order_booking.setClickable(false);
                        card_order_status.setVisibility(View.GONE);
                    }
                }
                if (data.getMenu_key().equalsIgnoreCase("RESOURCEPAGE")) {
                    MyApplication.set_session(MyApplication.SESSION_RESOURSE_PAGE, data.getLabel_name());
                    if (data.getIs_active().equalsIgnoreCase("false")) {
                        MyApplication.set_session("resourcepage", "false");
                        MyApplication.logi(LOG_TAG, "IS ACTIVE IS TRUE");
                        //card_order_booking.setClickable(false);
                        card_resource_page.setVisibility(View.GONE);
                    }
                }
                if (data.getMenu_key().equalsIgnoreCase("PROFILE")) {
                    MyApplication.set_session(MyApplication.SESSION_UPDATE_PPROFILE, data.getLabel_name());
                    if (data.getIs_active().equalsIgnoreCase("false")) {
                        MyApplication.set_session("profile", "false");
                        MyApplication.logi(LOG_TAG, "IS ACTIVE IS TRUE");
                        //card_order_booking.setClickable(false);
                        card_update_profile.setVisibility(View.GONE);
                    }
                }

            }
            MyApplication.logi("data", "data is->" + values);
            setLabelName();
        } else {
            MyApplication.logi(LOG_TAG, "in ELSE OF getdata");

            ArrayList<DashBoardData> values = MyApplication.getValuesFromDb();
            for (DashBoardData data : values) {
                if (data.getIs_active().equalsIgnoreCase("false")) {
                    MyApplication.logi(LOG_TAG, "else  IS ACTIVE IS TRUE");
                    //  card_order_booking.setClickable(false);

                    if (MyApplication.get_session("orderbooking").equalsIgnoreCase("false")) {
                        card_order_booking.setVisibility(View.GONE);
                    }


                    if (MyApplication.get_session("orderstatus").equalsIgnoreCase("false")) {
                        card_order_status.setVisibility(View.GONE);
                    }

                    if (MyApplication.get_session("resourcepage").equalsIgnoreCase("false")) {
                        card_resource_page.setVisibility(View.GONE);
                    }

                    if (MyApplication.get_session("profile").equalsIgnoreCase("false")) {
                        card_update_profile.setVisibility(View.GONE);
                    }


                }

            }
            setLabelName();

        }
    }

    public void setLabelName() {
        MyApplication.logi(LOG_TAG, "in setLabelName");
        txt_order_booking.setText(MyApplication.get_session(MyApplication.SESSION_ORDER_BOOKING));
        txt_order_status.setText(MyApplication.get_session(MyApplication.SESSION_ORDER_STATUS));
        txt_resource_page.setText(MyApplication.get_session(MyApplication.SESSION_RESOURSE_PAGE));
        txt_update_profile.setText(MyApplication.get_session(MyApplication.SESSION_UPDATE_PPROFILE));
    }

    @Override
    public void onBackPressed() {
        MyApplication.logi(LOG_TAG, "IN FINAL BAK PRESSED");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.Close_Application))
                .setMessage(getResources().getString(R.string.do_you_want_exit))
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(getResources().getString(R.string.Exit),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {


                                Intent intent = new Intent(Intent.ACTION_MAIN);
                                intent.addCategory(Intent.CATEGORY_HOME);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                                System.exit(0);

                               /* finish();
                               exit(0);*/

                            }
                        }).setNegativeButton(getResources().getString(R.string.cancel), null) // Do nothing on no
                .show();
    }

    @Override
    public void onRefresh() {


        //String url = "http://zylemdemo.com/RetailerOrdering/api/Login/Retailer?user=8551020051&password=default&deviceid=1fd1148b7a8d6a44&clientid=1&token=" + MyApplication.get_session(MyApplication.SESSION_FCM_ID) + "&userType=4";
        //  String url = "http://zylemdemo.com/RetailerOrdering/api/Login/Retailer?user=" + MyApplication.get_session(MyApplication.SESSION_USER_NAME_LOGIN) + "&password=" + MyApplication.get_session(MyApplication.SESSION_PASSWORD_LOGIN) + "&deviceid="+MyApplication.get_session(MyApplication.SESSION_DEVICE_ID)+"&clientid="+MyApplication.get_session(MyApplication.SESSION_CLIENT_ID_LOGIN)+"&token=" + MyApplication.get_session(MyApplication.SESSION_FCM_ID) + "&userType=4";

        //from direct login page
        String url = "http://zylemdemo.com/RetailerOrdering/api/Login/Retailer?user="+MyApplication.get_session(MyApplication.SESSION_USER_NAME_LOGIN)+"&password="+MyApplication.get_session(MyApplication.SESSION_PASSWORD_LOGIN)+"&deviceid=1fd1148b7a8d6a44&clientid="+MyApplication.get_session(MyApplication.SESSION_CLIENT_ID_LOGIN)+"&token=" + MyApplication.get_session(MyApplication.SESSION_FCM_ID) + "&userType=4";




        MyApplication.logi(LOG_TAG, "pass isss->" + MyApplication.get_session(MyApplication.SESSION_PASSWORD_LOGIN));
        MyApplication.logi(LOG_TAG, "USER isss->" + MyApplication.get_session(MyApplication.SESSION_USER_NAME_LOGIN));

        MyApplication.logi(LOG_TAG, "url isss->" + url);
        MyApplication.logi(LOG_TAG, "URL_GET_DATA DEVICE ID---->" + MyApplication.get_session(MyApplication.SESSION_DEVICE_ID));
        MyApplication.logi(LOG_TAG, "FCM ID-->" + "FCM ID-->" + MyApplication.get_session(MyApplication.SESSION_FCM_ID));
        HashMap<String, String> hashmap = new HashMap<String, String>();

        new HTTPVollyRequest(1, null, 1, "", context,
                url, getdataRespListner, hashmap);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

      @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //  int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_settings) {
           // R.id.action_settings.setVisibility(View.VISIBLE);
            return true;
        }
*/
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_syncs) {
            MyApplication.logi(LOG_TAG, "In id function................");
           sendAllDataToServer();
        } else if (id == R.id.nav_about) {

        } else if (id == R.id.nav_Logout) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getResources().getString(R.string.Close_Application))
                    .setMessage(getResources().getString(R.string.do_you_want_logout))
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(getResources().getString(R.string.Logout), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //System.exit(0);
                            logout();
                        }
                    }).setNegativeButton(getResources().getString(R.string.cancel), null)
                    .show();




        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void logout() {



        Intent intent = null;
       MyApplication.set_session(MyApplication.SESSION_LOGIN, "N");
        intent = new Intent(context, ActivityLogin.class);

        TABLE_RETAILER_IMAGES.delete_table();
        TABLE_MENU_MASTER.delete_table();
        TABLE_ORDER_DETAILS.delete_table();
        TABLE_ORDER_STATUS.delete_table();
        TABLE_PCUSTOMER.delete_table();
        TABLE_PDISTRIBUTOR.delete_table();
        TABLE_PITEM.delete_table();
        TABLE_PRICE_LIST.delete_table();
        TABLE_PRICELIST_DETAILS.delete_table();
       TABLE_RETAILER_ORDER_MASTER.delete_table();
        TABLE_SETTINGS.delete_table();
        TABLE_TEMP_ORDER_DETAILS.delete_table();
        TABLE_TEMP_RETAILER_ORDER_MASTER.delete_table();
        TABLE_UOMMASTER.delete_table();


        startActivity(intent);
        finish();



    }




    MyListener getRetailerOrderResp = new MyListener() {

        @Override
        public void success(Object obj) {

            try {
                MyApplication.logi("JARVIS", "in response of swipe getRetailerOrderResp");
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
                        }


                        //save data in table
                        initComponants();

                    } else if (status.equalsIgnoreCase("false")) {
                        MyApplication.logi(LOG_TAG, "getRetailerOrderResp unsuccesss" + status);
                        String error = resObj.get("error").toString();
                        MyApplication.logi(LOG_TAG, "getRetailerOrderResp error isss->>>>" + error);
                        // showDialogOK(resObj.getString("response_message"), context, resObj.getString("response_status"));
                    }
                }
                mSwipeRefreshLayout.setRefreshing(false);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        @Override
        public void failure(Object obj) {
         //   MyApplication.logi(LOG_TAG, "getRetailerOrderResp failure getorders"+obj.toString());

            final android.support.v7.app.AlertDialog.Builder dlgAlert = new android.support.v7.app.AlertDialog.Builder(context);

            dlgAlert.setMessage("Please check your internet connection and try again..");


            dlgAlert.setTitle("Retailer");
            dlgAlert.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            //dismiss the dialog

                        }


                    });
            mSwipeRefreshLayout.setRefreshing(false);
            dlgAlert.setCancelable(false);
            dlgAlert.show();

        }
    };




    public void sendAllDataToServer() {
        MyApplication.logi(LOG_TAG, "In sync function................");

        //   OrderAcceptReject();
        //PriceListData();
        OrderBooking();
    }

        private void OrderBooking() {
            MyApplication.logi(LOG_TAG, "In Rajani orderBooking............. ");
            String date = MyApplication.dateFormat();
            String[] array = date.split(" ");
            String date1 = array[0];
            MyApplication.set_session(MyApplication.SESSION_DATE_FOR_LAST_ORDER_PLACED, date1);

            JSONObject json_main = new JSONObject();
            JSONObject jsonobj_data = new JSONObject();
            JSONArray jsonarr_data = new JSONArray();


            try {
                jsonobj_data.put("LoginID", MyApplication.get_session(MyApplication.SESSION_USER_NAME_LOGIN));
                jsonobj_data.put("Password", MyApplication.get_session(MyApplication.SESSION_PASSWORD_LOGIN));
                jsonobj_data.put("DeviceId", MyApplication.get_session(MyApplication.SESSION_DEVICE_ID));
                jsonobj_data.put("ClientId", MyApplication.get_session(MyApplication.SESSION_CLIENT_ID_LOGIN));

                jsonarr_data.put(jsonobj_data);
                json_main.put("data", jsonarr_data);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            json_main = TABLE_TEMP_RETAILER_ORDER_MASTER.getDatasync(json_main);
            MyApplication.logi(LOG_TAG, "json_main.length()---> " + json_main.length());


            MyApplication.logi(LOG_TAG, "Order12---> " + json_main.toString());

            new HTTPVollyRequest(2, json_main, 0, "Please wait.", context, MyApplication.urlPlaceOrder,
                    postOrderData, null);


        }

        MyListener postOrderData = new MyListener() {

            @Override
            public void success(Object obj) {

                try {
                    JSONObject resObj = new JSONObject(obj.toString());
                    MyApplication.logi(LOG_TAG, "postOrderData, resObj-->" + resObj);
                    if (resObj != null && resObj.has("OrderSuccess")) {

                        JSONArray jsonArray = resObj.getJSONArray("OrderSuccess");
                        MyApplication.logi(LOG_TAG, "in OrderSuccess");
                        int length_arr = jsonArray.length();
                        for (int i = 0; i < length_arr; i++) {
                            JSONObject jsonObject_success = jsonArray.getJSONObject(i);
                            if (jsonObject_success != null && jsonObject_success.has("success")) {
                                MyApplication.logi(LOG_TAG, "in successs");
                                MyApplication.logi(LOG_TAG, "resp " + jsonObject_success.toString());
                                String status = jsonObject_success.getString("success");
                                MyApplication.logi(LOG_TAG, "in status is-->" + status);
                                if (status.equalsIgnoreCase("true")) {
                                    MyApplication.logi(LOG_TAG, "Message --" + jsonObject_success.getString("message"));
                                    MyApplication.logi(LOG_TAG, "AppOrderID-- " + jsonObject_success.getString("AppOrderID"));
                                    // MyApplication.logi(LOG_TAG, "EREEOR --" + jsonObject_success.getString("error"));
                                showMessage_new(context, jsonObject_success.getString("message"), "success_flag", jsonObject_success.getString("AppOrderID"));


                                   /* int ret = 0;
                                    if (ret == 0) {
                                        MyApplication.logi(LOG_TAG, "successfulllyy inserted in Master Table");
                                        //on success save all the TempOrderDetails data in OrderDetails
                                        int ret_details = TABLE_ORDER_DETAILS.insertOrderDetailsfinal( jsonObject_success.getString("AppOrderID"));
                                        if (ret_details == 0) {
                                            //delete data from old temp table on success
                                            TABLE_ORDER_STATUS.insertDataBeforeSync( jsonObject_success.getString("AppOrderID"),
                                                    MyApplication.get_session(MyApplication.SESSION_ORDER_DATE), "1", "");
                                            TABLE_TEMP_RETAILER_ORDER_MASTER.deleteAllRecords( jsonObject_success.getString("AppOrderID"));
                                            TABLE_TEMP_ORDER_DETAILS.deleteAllRecords( jsonObject_success.getString("AppOrderID"));

                                        } else {
                                            MyApplication.logi(LOG_TAG, "Not successfulllyy inserted in Master DETAILS Table");
                                        }
                                        Intent ii = new Intent(getApplicationContext(), ActivityDashBoard.class);
                                        finish();
                                        overridePendingTransition(R.anim.fade_in_call, R.anim.fade_out_call);
                                        startActivity(ii);
                                    }*/

                                    MyApplication.set_session("SESSION_BOOK_ORDER_LAST_SYNC", MyApplication.getCurrentDate());

                                } /*else {
                            MyApplication.logi(LOG_TAG, "NO RESPONSE");
                            showMessage(context, resObj.getString("message"));
                        }
*/
                            }
                            if (jsonObject_success != null && jsonObject_success.has("OrderFailuer")) {
                                MyApplication.logi(LOG_TAG, "in OrderFailuer");
                            }
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    // OrderAcceptReject();
                }
            }

            @Override
            public void failure(Object obj) {
                MyApplication.logi(LOG_TAG, "IN FAILURE");
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage(getResources().getString(R.string.cart))
                        .setIcon(R.drawable.app_icon)
                      /*  .setPositiveButton(getResources().getString(R.string.Exit),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int which) {

                                        Intent intent = new Intent(Intent.ACTION_MAIN);
                                        intent.addCategory(Intent.CATEGORY_HOME);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();
                                        System.exit(0);

                               *//* finish();
                               exit(0);*//*

                                    }
                                })*/
                        .setPositiveButton(getResources().getString(R.string.ok), null) // Do nothing on no
                        .show();

            }
        };



    public void showMessage_new(final Context context, String msg, final String flag, final String orderId) {
        CustomButtonBold btn_ok = null;
        CustomTextViewRegular tv_text = null, tv_order_no = null;

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.show_dialog_order_new);
        dialog.getWindow().setLayout(ActionBar.LayoutParams.FILL_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        btn_ok = (CustomButtonBold) dialog.findViewById(R.id.btn_ok);
        tv_text = (CustomTextViewRegular) dialog.findViewById(R.id.tv_text);
        //tv_text.setText(msg);
        tv_order_no = (CustomTextViewRegular) dialog.findViewById(R.id.tv_order_no);
        tv_order_no.setText(orderId);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                if (flag.equalsIgnoreCase("success_flag")) {
                    MyApplication.logi(LOG_TAG, "SUCCESS IS TRUE FOR PLACING ORDER");
                    MyApplication.set_session("SESSION_INSERT_IN_FINAL_MASTER_TABLE", "true");

                    int ret = 0;
                    if (ret == 0) {
                        MyApplication.logi(LOG_TAG, "successfulllyy inserted in Master Table");
                        //on success save all the TempOrderDetails data in OrderDetails
                        int ret_details = TABLE_ORDER_DETAILS.insertOrderDetailsfinal(orderId);
                        if (ret_details == 0) {
                            //delete data from old temp table on success
                            TABLE_ORDER_STATUS.insertDataBeforeSync(orderId,
                                    MyApplication.get_session(MyApplication.SESSION_ORDER_DATE), "1", "");
                            TABLE_TEMP_RETAILER_ORDER_MASTER.deleteAllRecords(orderId);
                            TABLE_TEMP_ORDER_DETAILS.deleteAllRecords(orderId);

                        } else {
                            MyApplication.logi(LOG_TAG, "Not successfulllyy inserted in Master DETAILS Table");
                        }
                    }
                } else {
                    MyApplication.logi(LOG_TAG, "SUCCESS IS FALSE FOR PLACING ORDER");
                }

                MyApplication.set_session(MyApplication.SESSION_ORDER_ID, "");
                Intent i = new Intent(getApplicationContext(), ActivityDashBoard.class);
                finish();
                overridePendingTransition(R.anim.fade_in_call, R.anim.fade_out_call);
                startActivity(i);


            }
        });

        dialog.setCancelable(false);
        dialog.show();
        dialog.closeOptionsMenu();
    }





}
