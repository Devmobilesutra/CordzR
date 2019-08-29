package com.sapl.retailerorderingmsdpharma.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.sapl.retailerorderingmsdpharma.MyDatabase.TABLE_ORDER_DETAILS;
import com.sapl.retailerorderingmsdpharma.MyDatabase.TABLE_ORDER_STATUS;
import com.sapl.retailerorderingmsdpharma.MyDatabase.TABLE_PCUSTOMER;
import com.sapl.retailerorderingmsdpharma.MyDatabase.TABLE_PDISTRIBUTOR;
import com.sapl.retailerorderingmsdpharma.MyDatabase.TABLE_RETAILER_ORDER_MASTER;
import com.sapl.retailerorderingmsdpharma.MyDatabase.TABLE_TEMP_ORDER_DETAILS;
import com.sapl.retailerorderingmsdpharma.MyDatabase.TABLE_TEMP_RETAILER_ORDER_MASTER;
import com.sapl.retailerorderingmsdpharma.R;
import com.sapl.retailerorderingmsdpharma.ServerCall.HTTPVollyRequest;
import com.sapl.retailerorderingmsdpharma.ServerCall.MyListener;
import com.sapl.retailerorderingmsdpharma.adapter.DistributorDataAdapter;
import com.sapl.retailerorderingmsdpharma.adapter.OrderReviewAdapter;
import com.sapl.retailerorderingmsdpharma.customView.CustomButtonBold;
import com.sapl.retailerorderingmsdpharma.customView.CustomButtonRegular;
import com.sapl.retailerorderingmsdpharma.customView.CustomTextViewMedium;
import com.sapl.retailerorderingmsdpharma.customView.CustomTextViewRegular;
import com.sapl.retailerorderingmsdpharma.models.DistributorModel;
import com.sapl.retailerorderingmsdpharma.models.OrderReviewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.sapl.retailerorderingmsdpharma.activities.MyApplication.LOG_TAG;
import static com.sapl.retailerorderingmsdpharma.activities.MyApplication.SESSION_DISTRIBUTOR_ID;
import static com.sapl.retailerorderingmsdpharma.activities.MyApplication.jsonobject;

public class ActivityPreviewOrder extends AppCompatActivity {

    Context context;
    CustomTextViewMedium txt_distributor_name,txt_title;
    public static CustomTextViewMedium txt_total_price;
    ImageView img_distributor, img_back,img_editt_main;
    ;
    CustomButtonRegular btn_submit_order;
    RelativeLayout rel_toolbar_signing;
    static String LOG_TAG = "ActivityPreviewOrder";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_order);
        context = this;

        MyApplication.logi(LOG_TAG, "In Previ");

        initComponants();
        setDistributorDetails();
        initListnerComponants();
        bindData();
    }

    public void initComponants() {
        img_distributor = (ImageView) findViewById(R.id.img_distributor);
        img_distributor.setColorFilter((Color.parseColor(MyApplication.get_session(MyApplication.SESSION_THEME_DARK_COLOR))));
        txt_distributor_name = findViewById(R.id.txt_distributor_name);
        txt_distributor_name.setTextColor((Color.parseColor(MyApplication.get_session(MyApplication.SESSION_Text_Primary_Color))));
        txt_total_price = findViewById(R.id.txt_total_price);
        txt_total_price.setTextColor((Color.parseColor(MyApplication.get_session(MyApplication.SESSION_Text_Primary_Color))));

        txt_title = findViewById(R.id.txt_title);
        txt_title.setTextColor((Color.parseColor(MyApplication.get_session(MyApplication.SESSION_Text_Primary_Color))));
        btn_submit_order = findViewById(R.id.btn_submit_order);
        btn_submit_order.setTextColor((Color.parseColor(MyApplication.get_session(MyApplication.SESSION_Accent_Color))));
        btn_submit_order.setBackgroundColor((Color.parseColor(MyApplication.get_session(MyApplication.SESSION_THEME_DARK_COLOR))));
        img_back = findViewById(R.id.img_back);
        img_editt_main = findViewById(R.id.img_editt_main);
        img_editt_main.setVisibility(View.GONE);

        rel_toolbar_signing = findViewById(R.id.rel_toolbar_signing);
        //rel_toolbar_signing.setBackgroundColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR)));

    }

//FUNCTION IS USED TO SET THE DETAILS OF DISTRIBUTR ON FINAL PAGE AFTER ORDERS ARE ADDED TO  CART
    public void setDistributorDetails() {
        Glide.with(context).load(context.getResources().getIdentifier(
                "arvind1",
                "drawable", context.getPackageName()))
                .asBitmap().centerCrop().into(new BitmapImageViewTarget(img_distributor) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                img_distributor.setImageDrawable(circularBitmapDrawable);
            }
        });

        txt_distributor_name.setText(MyApplication.get_session(MyApplication.SESSION_DISTRIBUTER_NAME));
        // txt_total_price.setText(getResources().getString(R.string.rupee_sign) + "00");


        String total = TABLE_TEMP_ORDER_DETAILS.getSumOfAllItemsnew(MyApplication.get_session(MyApplication.SESSION_ORDER_ID));
        if (total == null) {
            txt_total_price.setText("₹" + " 0.0 ");

        } else {
            txt_total_price.setText("₹ " + total);

        }

    }

    public void initListnerComponants() {

        btn_submit_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  Toast.makeText(context, "This feature coming soon", Toast.LENGTH_SHORT).show();

                String date = MyApplication.dateFormat();
                String[] array = date.split(" ");
                String date1 = array[0];
                MyApplication.set_session(MyApplication.SESSION_DATE_FOR_LAST_ORDER_PLACED, date1);

                JSONObject json_main = new JSONObject();

                JSONObject jsonobj_data = new JSONObject();
                JSONArray jsonarr_data = new JSONArray();


                try {
                   /* jsonobj_data.put("LoginID", "8551020051");
                    jsonobj_data.put("Password", "default");
                    jsonobj_data.put("DeviceId", "1fd1148b7a8d6a44");
                    jsonobj_data.put("ClientId", "1"); //1
*/
                    jsonobj_data.put("LoginID", MyApplication.get_session(MyApplication.SESSION_USER_NAME_LOGIN));
                    jsonobj_data.put("Password",  MyApplication.get_session(MyApplication.SESSION_PASSWORD_LOGIN));
                    jsonobj_data.put("DeviceId",  "1fd1148b7a8d6a44");
                    jsonobj_data.put("ClientId", MyApplication.get_session(MyApplication.SESSION_CLIENT_ID_LOGIN));

                    jsonarr_data.put(jsonobj_data);
                    json_main.put("data", jsonarr_data);


                } catch (JSONException e) {
                    e.printStackTrace();
                }




                json_main = TABLE_TEMP_RETAILER_ORDER_MASTER.getData(json_main);


                MyApplication.logi(LOG_TAG, "FINALLLL JSON MASTER--->" + json_main.toString());

                new HTTPVollyRequest(2, json_main, 0, "Please wait.", context, MyApplication.urlPlaceOrder,
                        postOrderData, null);


            }
        });


        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (MyApplication.get_session("preview_order").equalsIgnoreCase("from_cart")) {
                    MyApplication.logi(LOG_TAG,"IN  BACK FROM CART");

                    Intent intent = new Intent(ActivityPreviewOrder.this, ActivityDistributorList.class);
                    startActivity(intent);
                }else {
                    MyApplication.logi(LOG_TAG,"ELSE BACK FROM CART");
                    Intent intent = new Intent(ActivityPreviewOrder.this, ActivitySelection.class);

                    intent.putExtra("MOVE_TO_ITEM", "YES");
                    startActivity(intent);
                    // finish();
                }
            }
        });

        img_editt_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.logi(LOG_TAG,"IN img_editt_main");

                MyApplication.set_session("edit_delete_visibility","true");
               /* OrderReviewAdapter.img_edt.setVisibility(View.VISIBLE);
                OrderReviewAdapter.img_delete.setVisibility(View.VISIBLE);*/
            }
        });

    }

    public void bindData() {
        List<OrderReviewModel> orderReviewList = new ArrayList<>();

      /*  if (MyApplication.get_session("preview_order").equalsIgnoreCase("from_cart")) {
            MyApplication.logi(LOG_TAG, "FROM CART SESSION");

           // orderReviewList = TABLE_ORDER_DETAILS.getItemsForDistributorFromCart(MyApplication.get_session(MyApplication.SESSION_ORDER_ID_FROM_CART));
           // MyApplication.logi(LOG_TAG, "ORDER PREVIEW LIST FROM CART SESSION---->"+orderReviewList);

        } else {
*/
        orderReviewList = TABLE_TEMP_ORDER_DETAILS.getOrdersnew(MyApplication.get_session(MyApplication.SESSION_ORDER_ID),MyApplication.get_session(MyApplication.SESSION_DISTRIBUTER_ID));
        //}

        if (orderReviewList != null && orderReviewList.size() > 0) {
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setItemViewCacheSize(200);
            recyclerView.setDrawingCacheEnabled(true);
            recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
            recyclerView.smoothScrollBy(0, 10);
            OrderReviewAdapter mAdapter = new OrderReviewAdapter(context, orderReviewList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);

        }
    }


    MyListener postOrderData = new MyListener() {

        @Override
        public void success(Object obj) {

            try {
                MyApplication.logi(LOG_TAG, "In postOrderData");
                JSONObject resObj = new JSONObject(obj.toString());
                MyApplication.logi(LOG_TAG, "resObj-->" + resObj);
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
                               MyApplication.logi(LOG_TAG, "DistName-- " + jsonObject_success.getString("DistributorName"));
                                MyApplication.logi(LOG_TAG, "EREEOR --" + jsonObject_success.getString("error"));
                                showMessage_new(context, jsonObject_success.getString("message"), "success_flag",jsonObject_success.getString("AppOrderID"),jsonObject_success.getString("DistributorName"));

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
            }
        }

        @Override
        public void failure(Object obj) {

            MyApplication.logi(LOG_TAG, "IN FAILURE");
/*

            final AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);

            dlgAlert.setMessage("Some problem has occured..Please try after 15-20 minuts");


            dlgAlert.setTitle("Zylemini");
            dlgAlert.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            //dismiss the dialog

                        }


                    });
            dlgAlert.setCancelable(false);
            dlgAlert.show();

*/

        }
    };



    public void showMessage_new(final Context context, String msg, final String flag,String orderId,String DistName) {

        CustomButtonBold btn_ok = null;
        CustomTextViewRegular tv_text = null,tv_order_no = null,tv_Dist_name=null;

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.show_dialog_order);
        dialog.getWindow().setLayout(ActionBar.LayoutParams.FILL_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        btn_ok = (CustomButtonBold) dialog.findViewById(R.id.btn_ok);
        tv_text = (CustomTextViewRegular) dialog.findViewById(R.id.tv_text);
        tv_Dist_name = (CustomTextViewRegular) dialog.findViewById(R.id.tv_Dist_name);
        tv_Dist_name.setText(DistName);
        MyApplication.logi(LOG_TAG, "DistName="+DistName);
        //tv_text.setText(msg);
        tv_order_no = (CustomTextViewRegular) dialog.findViewById(R.id.tv_order_no);
        tv_order_no.setText(orderId);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                //dismiss the dialog


                if (flag.equalsIgnoreCase("success_flag")) {
                    MyApplication.logi(LOG_TAG, "SUCCESS IS TRUE FOR PLACING ORDER");
                    MyApplication.set_session("SESSION_INSERT_IN_FINAL_MASTER_TABLE", "true");


                    String retailerId = TABLE_PCUSTOMER.getCustId();
                    //insert data in final master and details table
                    int ret = TABLE_RETAILER_ORDER_MASTER.insertOrderMaster(MyApplication.get_session(MyApplication.SESSION_ORDER_ID),
                            MyApplication.get_session(MyApplication.SESSION_DISTRIBUTOR_ID), retailerId, MyApplication.get_session(MyApplication.SESSION_ORDER_DATE)
                            , "200003", "1", "", "0");

                    // int ret = TABLE_RETAILER_ORDER_MASTER.insertOrderMasterfinal(MyApplication.get_session(MyApplication.SESSION_ORDER_ID));

                  //IF DATA IS SAVED SUCCESSFULLY IN TABLE_RETAILER_ORDER_MASTER THEN INSERT IN TABLE_ORDER_DETAILS
                    if (ret == 0) {
                        MyApplication.logi(LOG_TAG, "successfulllyy inserted in Master Table");
                        //on success save all the TempOrderDetails data in OrderDetails
                        int ret_details = TABLE_ORDER_DETAILS.insertOrderDetailsfinal(MyApplication.get_session(MyApplication.SESSION_ORDER_ID));
                        if (ret_details == 0) {

                            //delete data from old temp table on success
                            TABLE_TEMP_RETAILER_ORDER_MASTER.deleteAllRecords(MyApplication.get_session(MyApplication.SESSION_ORDER_ID));
                            TABLE_TEMP_ORDER_DETAILS.deleteAllRecords(MyApplication.get_session(MyApplication.SESSION_ORDER_ID));

                            TABLE_ORDER_STATUS.insertDataBeforeSync(MyApplication.get_session(MyApplication.SESSION_ORDER_ID),
                                    MyApplication.get_session(MyApplication.SESSION_ORDER_DATE), "1", "");

                            MyApplication.logi(LOG_TAG, "successfulllyy inserted in Master DETAILS Table");
                        } else if (ret_details == 1) {
                            MyApplication.logi(LOG_TAG, "Not successfulllyy inserted in Master DETAILS Table");
                        }
                    } else if (ret == 1) {
                        MyApplication.logi(LOG_TAG, "Not successfulllyy inserted in Master Table");

                    }

                } else {

                    MyApplication.logi(LOG_TAG, "SUCCESS IS FALSE FOR PLACING ORDER");
                }

                if(MyApplication.get_session("preview_order").equalsIgnoreCase("from_cart"))
                {

                    Intent i = new Intent(getApplicationContext(), ActivityDashBoard.class);
                    finish();
                    overridePendingTransition(R.anim.fade_in_call, R.anim.fade_out_call);
                    startActivity(i);
                }else {
                    Intent i = new Intent(getApplicationContext(), ActivityDistributorList.class);
                    finish();
                    overridePendingTransition(R.anim.fade_in_call, R.anim.fade_out_call);
                    startActivity(i);
                }
            }
        });

        dialog.setCancelable(false);
        dialog.show();
        dialog.closeOptionsMenu();
    }












/*



    public void showMessage(final Context context, String msg, final String flag) {
        MyApplication.logi(LOG_TAG, "in show msg ");
        final AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);

        dlgAlert.setMessage(msg);
        dlgAlert.setTitle("Retailer");
        dlgAlert.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //dismiss the dialog


                        if (flag.equalsIgnoreCase("success_flag")) {
                            MyApplication.logi(LOG_TAG, "SUCCESS IS TRUE FOR PLACING ORDER");
                            MyApplication.set_session("SESSION_INSERT_IN_FINAL_MASTER_TABLE", "true");


                            String retailerId = TABLE_PCUSTOMER.getCustId();
                            //insert data in final master and details table
                            int ret = TABLE_RETAILER_ORDER_MASTER.insertOrderMaster(MyApplication.get_session(MyApplication.SESSION_ORDER_ID),
                                    MyApplication.get_session(MyApplication.SESSION_DISTRIBUTOR_ID), retailerId, MyApplication.get_session(MyApplication.SESSION_ORDER_DATE)
                                    , "200003", "1", "", "0");

                            // int ret = TABLE_RETAILER_ORDER_MASTER.insertOrderMasterfinal(MyApplication.get_session(MyApplication.SESSION_ORDER_ID));
                            if (ret == 0) {
                                MyApplication.logi(LOG_TAG, "successfulllyy inserted in Master Table");
                                //on success save all the TempOrderDetails data in OrderDetails
                                int ret_details = TABLE_ORDER_DETAILS.insertOrderDetailsfinal(MyApplication.get_session(MyApplication.SESSION_ORDER_ID));
                                if (ret_details == 0) {

                                    //delete data from old temp table
                                    TABLE_TEMP_RETAILER_ORDER_MASTER.deleteAllRecords(MyApplication.get_session(MyApplication.SESSION_ORDER_ID));
                                    TABLE_TEMP_ORDER_DETAILS.deleteAllRecords(MyApplication.get_session(MyApplication.SESSION_ORDER_ID));

                                    TABLE_ORDER_STATUS.insertDataBeforeSync(MyApplication.get_session(MyApplication.SESSION_ORDER_ID),
                                            MyApplication.get_session(MyApplication.SESSION_ORDER_DATE), "1", "");

                                    MyApplication.logi(LOG_TAG, "successfulllyy inserted in Master DETAILS Table");
                                } else if (ret_details == 1) {
                                    MyApplication.logi(LOG_TAG, "Not successfulllyy inserted in Master DETAILS Table");
                                }
                            } else if (ret == 1) {
                                MyApplication.logi(LOG_TAG, "Not successfulllyy inserted in Master Table");

                            }

                        } else {

                            MyApplication.logi(LOG_TAG, "SUCCESS IS FALSE FOR PLACING ORDER");
                        }

                        if(MyApplication.get_session("preview_order").equalsIgnoreCase("from_cart")){

                            Intent i = new Intent(getApplicationContext(), ActivityDashBoard.class);
                            finish();
                            overridePendingTransition(R.anim.fade_in_call, R.anim.fade_out_call);
                            startActivity(i);
                        }else {
                            Intent i = new Intent(getApplicationContext(), ActivityDistributorList.class);
                            finish();
                            overridePendingTransition(R.anim.fade_in_call, R.anim.fade_out_call);
                            startActivity(i);
                        }

                    }
                });
        dlgAlert.setCancelable(false);
        dlgAlert.show();

    }

*/

    @Override
    public void onBackPressed() {
        if (MyApplication.get_session("preview_order").equalsIgnoreCase("from_cart")) {
            Intent intent = new Intent(ActivityPreviewOrder.this, ActivityDistributorList.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(ActivityPreviewOrder.this, ActivitySelection.class);
            intent.putExtra("MOVE_TO_ITEM", "YES");
            startActivity(intent);
        }

    }

}
