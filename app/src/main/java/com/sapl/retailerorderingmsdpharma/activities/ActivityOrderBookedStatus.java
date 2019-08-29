/*

NOTE:::::------->
IF UI IS CHANGED SO KEPT THIS ACTIVITY WHICH IS AS PER OLD REQUIREMENT

package com.sapl.retailerorderingmsdpharma.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sapl.retailerorderingmsdpharma.MyDatabase.TABLE_ORDER_DETAILS;
import com.sapl.retailerorderingmsdpharma.MyDatabase.TABLE_ORDER_STATUS;
import com.sapl.retailerorderingmsdpharma.MyDatabase.TABLE_RETAILER_ORDER_MASTER;
import com.sapl.retailerorderingmsdpharma.R;
import com.sapl.retailerorderingmsdpharma.ServerCall.HTTPVollyRequest;
import com.sapl.retailerorderingmsdpharma.ServerCall.MyListener;
import com.sapl.retailerorderingmsdpharma.customView.CustomTextViewMedium;
import com.sapl.retailerorderingmsdpharma.customView.CustomUnderLine;
import com.sapl.retailerorderingmsdpharma.models.OrderDeliveryStatusModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.sapl.retailerorderingmsdpharma.activities.MyApplication.LOG_TAG;


//this activity is not called anywhere


public class ActivityOrderBookedStatus extends AppCompatActivity {
    Context context = null;
    CustomTextViewMedium no_orders_booked_yet;
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_order_booked_status);


        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swifeRefresh_order_booked_status);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                String url = "http://zylemdemo.com/RetailerOrdering/api/GetOrders/RetailerOrder?user=8551020051&password=default&deviceid=1fd1148b7a8d6a44&clientid=1&token=Token1&userType=4";
                //String url = "http://zylemdemo.com/RetailerOrdering/api/GetOrders/RetailerOrder?user=" + MyApplication.get_session(MyApplication.SESSION_USER_NAME_LOGIN) + "&password=" + MyApplication.get_session(MyApplication.SESSION_PASSWORD_LOGIN) + "&deviceid=1fd1148b7a8d6a44&clientid="+MyApplication.get_session(MyApplication.SESSION_CLIENT_ID_LOGIN)+"&token=" + MyApplication.get_session(MyApplication.SESSION_FCM_ID) + "&userType=4";

                MyApplication.logi(LOG_TAG, "url isss for_get_ordres->" + url);
                MyApplication.logi(LOG_TAG, "URL_GET_DATA DEVICE ID---->" + MyApplication.get_session(MyApplication.SESSION_DEVICE_ID));
                MyApplication.logi(LOG_TAG, "FCM ID-->" + "FCM ID-->" + MyApplication.get_session(MyApplication.SESSION_FCM_ID));
                HashMap<String, String> hashmap = new HashMap<String, String>();

                new HTTPVollyRequest(1, null, 1, "", context,
                        url, getRetailerOrderResp, hashmap);
            }
        });


        initComponents();
        initComponentListner();
        bindData();
    }


    MyListener getRetailerOrderResp = new MyListener()
    {

        @Override
        public void success(Object obj) {

            try {
                MyApplication.logi("JARVIS", "in response of swipe getRetailerOrderResp");
                JSONObject resObj = new JSONObject(obj.toString());
                if (resObj != null && resObj.has("success"))
                {
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
                            //   TABLE_ORDER_DETAILS.updateData(jsonObject.getJSONArray("OrderDetails"));

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
                        bindData();

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
            MyApplication.logi(LOG_TAG, "getRetailerOrderResp failure ");

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


    private void initComponentListner() {
    }

    private void initComponents() {

        no_orders_booked_yet = findViewById(R.id.no_orders_booked_yet);
        no_orders_booked_yet.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR)));

    }

    private void bindData() {
        MyApplication.logi("bindData() ", "***************");
        List<OrderDeliveryStatusModel> orderStatusList = new ArrayList<>();
        //orderStatusList = TABLE_ORDER_DETAILS.getOrderStatusList();
        orderStatusList = TABLE_RETAILER_ORDER_MASTER.getOrderStatusList();
        MyApplication.logi(LOG_TAG, "before count orderStatusList");
        if (orderStatusList.size() == 0) {
            MyApplication.logi(LOG_TAG, "IN CPOUNT IS 0");
            no_orders_booked_yet.setVisibility(View.GONE);
        }
        MyApplication.logi("bindData() ", orderStatusList.toString());

        //orderStatusList = MyApplication.getOrderStatusList();

        if (orderStatusList != null && orderStatusList.size() > 0) {
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView_booked_status);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(context);

            mLayoutManager.setReverseLayout(true);
            mLayoutManager.setStackFromEnd(true);

            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setItemViewCacheSize(200);
            recyclerView.setDrawingCacheEnabled(true);
            recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
           // recyclerView.smoothScrollToPosition(0);

            recyclerView.smoothScrollBy(0, 10);


            recyclerView.setItemAnimator(new DefaultItemAnimator());

            RecyclerAdapter mAdapter = new RecyclerAdapter(context, orderStatusList);
            recyclerView.setAdapter(mAdapter);

            int count = recyclerView.getAdapter().getItemCount();
            MyApplication.logi(LOG_TAG, "CHICLDCOUNT IS-->" + count);


        }
    }

    @Override
    public void onBackPressed() {

        MyApplication.logi(LOG_TAG, "in back pressed");
        Intent intent = new Intent(getApplicationContext(), ActivityDashBoard.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.fade_in_call, R.anim.fade_out_call);
        startActivity(intent);

    }

    public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private List<OrderDeliveryStatusModel> selectionList;
        public String LOG_TAG = "OrderBookedStatusAdapter ";
        Context context;

        public RecyclerAdapter(Context context, List<OrderDeliveryStatusModel> selectionList) {
            this.selectionList = selectionList;
            this.context = context;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder vh = null;

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_order_booked_status_list, parent, false);
            vh = new MyViewHolder(view);
            return vh;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            final OrderDeliveryStatusModel model = selectionList.get(position);


            ((MyViewHolder) holder).txt_name.setText(model.getDistributorId());


            if (model.getOrderStatus().contains("1")) {
                ((MyViewHolder) holder).txt_booked_status.setText("Pending");
                ((MyViewHolder) holder).txt_booked_status.setBackgroundColor(getResources().getColor(R.color.orange_700));
                ((MyViewHolder) holder).underLine.setBackgroundColor(getResources().getColor(R.color.orange_700));
            } else if (model.getOrderStatus().contains("2")) {
                ((MyViewHolder) holder).txt_booked_status.setText("Delivered");
                ((MyViewHolder) holder).txt_booked_status.setBackgroundColor(getResources().getColor(R.color.green));
                ((MyViewHolder) holder).underLine.setBackgroundColor(getResources().getColor(R.color.green));
            } else if (model.getOrderStatus().contains("3")) {
                ((MyViewHolder) holder).txt_booked_status.setText("Rejected");
                ((MyViewHolder) holder).txt_booked_status.setBackgroundColor(getResources().getColor(R.color.red_400));
                ((MyViewHolder) holder).underLine.setBackgroundColor(getResources().getColor(R.color.red_400));
            } else if (model.getOrderStatus().contains("4")) {
                ((MyViewHolder) holder).txt_booked_status.setText("Partial Acceptance");
                ((MyViewHolder) holder).txt_booked_status.setBackgroundColor(getResources().getColor(R.color.orange_700));
                ((MyViewHolder) holder).underLine.setBackgroundColor(getResources().getColor(R.color.orange_700));

            }

            //((MyViewHolder) holder).txt_booked_status.setText(model.getOrderStatus());

            String date = model.getOrderDate();
            String[] array = date.split(" ");
            String date1 = array[0];
            ((MyViewHolder) holder).txt_date.setText(date1);
            ((MyViewHolder) holder).txt_order_id.setText("Order Id: " + model.getOrderID());
            ((MyViewHolder) holder).txt_rupees.setText(model.getAmount());
            // ((MyViewHolder) holder).txt_card_number.setText(model.getCart_count());

            int count_of_cart = TABLE_ORDER_DETAILS.getCountForSpecificOrderID(model.getOrderID());
            ((MyViewHolder) holder).txt_card_number.setText(count_of_cart + "");

        }

        @Override
        public int getItemCount() {
            return selectionList == null ? 0 : selectionList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public CustomTextViewMedium txt_shop_name, txt_card_number, txt_rupees;
            public CustomTextViewMedium txt_date, txt_order_status; //, balance; //address, contact
            public ImageView img_name, img_vechle;
            public CustomTextViewMedium processing, delivered;
            CustomTextViewMedium txt_name, txt_booked_status, txt_order_id;
            CustomUnderLine underLine;

            public MyViewHolder(View view) {
                super(view);

                underLine = view.findViewById(R.id.underline);

                txt_order_id = view.findViewById(R.id.txt_order_id);
                txt_order_id.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR)));


                txt_name = view.findViewById(R.id.txt_shop_name);
                txt_name.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR)));

                txt_shop_name = view.findViewById(R.id.txt_shop_name);
                txt_shop_name.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR)));

                txt_date = view.findViewById(R.id.txt_date);
                txt_date.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR)));

                txt_booked_status = view.findViewById(R.id.txt_booked_status);
                txt_booked_status.setTextColor(getResources().getColor(R.color.black));

                txt_card_number = view.findViewById(R.id.txt_cart_number);
                txt_card_number.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR)));

                txt_rupees = view.findViewById(R.id.txt_rupees);
                txt_rupees.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR)));

                processing = view.findViewById(R.id.processing);
                processing.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_TEXT_COLOR)));

                /// processing.setBackground(context.getResources().getDrawable(R.drawable.border_background_processing));


                delivered = view.findViewById(R.id.delivered);
                delivered.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_TEXT_COLOR)));

                /// processing.setBackground(context.getResources().getDrawable(R.drawable.border_background_processing));

                img_name = (ImageView) view.findViewById(R.id.img_name);
                img_vechle = (ImageView) view.findViewById(R.id.img_vechle);


            }
        }
    }
}
*/
