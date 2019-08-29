package com.sapl.retailerorderingmsdpharma.activities;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.sapl.retailerorderingmsdpharma.MyDatabase.TABLE_ORDER_DETAILS;
import com.sapl.retailerorderingmsdpharma.MyDatabase.TABLE_ORDER_STATUS;
import com.sapl.retailerorderingmsdpharma.MyDatabase.TABLE_RETAILER_ORDER_MASTER;
import com.sapl.retailerorderingmsdpharma.R;
import com.sapl.retailerorderingmsdpharma.ServerCall.MyListener;
import com.sapl.retailerorderingmsdpharma.customView.CustomTextViewMedium;
import com.sapl.retailerorderingmsdpharma.customView.CustomUnderLine;
import com.sapl.retailerorderingmsdpharma.models.OrderDeliveryStatusModel;
import com.sapl.retailerorderingmsdpharma.observer.MilkLtrsObservable;
import com.sapl.retailerorderingmsdpharma.observer.MilkLtrsObserver;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ActivityPendingStatus extends AppCompatActivity implements MilkLtrsObserver {


    public static String LOG_TAG = "AcitivityAllStatusList";
    Context context = null;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private MilkLtrsObservable milkLtrsObservable;
    CustomTextViewMedium txt_filter_lable;
    ImageView img_filter;
    List<OrderDeliveryStatusModel> orderStatusList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_status);
        context = this;
        registerObservers();

        initComponants();
        initComponantListner();
        bindData();

    }

    private void initComponants() {
        img_filter = (ImageView) findViewById(R.id.img_filter);
        txt_filter_lable = findViewById(R.id.txt_filter_lable);
        txt_filter_lable.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_THEME_DARK_COLOR)));
        //  recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
    }


    public void initComponantListner() {
        img_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View v1 = img_filter;
                PopupMenu popup = new PopupMenu(ActivityPendingStatus.this, v1);
                popup.getMenuInflater().inflate(R.menu.menu_filter, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        //orderFilterList = new ArrayList<>();

                        if (item.toString().equals(getResources().getString(R.string.shop))) {
                            Toast.makeText(getApplicationContext(), "U SELECTES " + item.toString(), Toast.LENGTH_LONG).show();

                            orderStatusList = TABLE_RETAILER_ORDER_MASTER.getStatusListAsPerShop("1");
                            setSelectedData();


                            //orderFilterList = orderShopList;
                            //  initBindData(orderShopList);
                        }
                        if (item.toString().equals(getResources().getString(R.string.calender))) {
                            Toast.makeText(getApplicationContext(), "U SELECTES " + item.toString(), Toast.LENGTH_LONG).show();

                            orderStatusList = TABLE_RETAILER_ORDER_MASTER.getStatusListAsPerCalender("1");
                            setSelectedData();

                        }
                        if (item.toString().equals(getResources().getString(R.string.shop_calender))) {
                            Toast.makeText(getApplicationContext(), "U SELECTES " + item.toString(), Toast.LENGTH_LONG).show();

                            orderStatusList = TABLE_RETAILER_ORDER_MASTER.getStatusListAsPerShopAndCalender("1");
                            setSelectedData();

                        }
                        txt_filter_lable.setText(item.toString() + "");
                        return true;
                    }
                });
                popup.show();
            }
        });
    }


    public void bindData() {

        MyApplication.logi("bindData() ", "***************");

        orderStatusList = TABLE_RETAILER_ORDER_MASTER.getOrderStatusList_new("1");
        MyApplication.logi(LOG_TAG, "before count orderStatusList");
        setSelectedData();

    }


    public void setSelectedData() {
        if (orderStatusList != null && orderStatusList.size() > 0) {
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(context);

            mLayoutManager.setReverseLayout(true);
            mLayoutManager.setStackFromEnd(true);

            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setItemViewCacheSize(200);
            recyclerView.setDrawingCacheEnabled(true);
            recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

            recyclerView.smoothScrollBy(0, 10);


            recyclerView.setItemAnimator(new DefaultItemAnimator());

            RecyclerAdapter adapter = new RecyclerAdapter(context, orderStatusList);
            recyclerView.setAdapter(adapter);

        }
    }

    @Override
    public void setMilkLtrs(String cowLtrs, String buffellowLtrs) {

    }

    @Override
    public void setValues() {
        bindData();
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
            RecyclerAdapter.MyViewHolder vh = null;

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_order_booked_status_list, parent, false);
            vh = new RecyclerAdapter.MyViewHolder(view);
            return vh;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            final OrderDeliveryStatusModel model = selectionList.get(position);
            ((RecyclerAdapter.MyViewHolder) holder).txt_name.setText(model.getDistributorId());


            ((MyViewHolder) holder).img_info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String info = model.getAddress_outlet_info();
                    MyApplication.logi(LOG_TAG, "INFO IS-->" + info);
                    String OriginalString = info.replaceAll("\\||", "");
                    MyApplication.logi(LOG_TAG, "OriginalString-->" + OriginalString);
                    display_information_alert(OriginalString);
                }
            });


            ((MyViewHolder) holder).img_call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    display_calling_alert(model.getContact_no_land(), model.getContact_no_mob());
                }
            });





           /* if (model.getOrderStatus().contains("1")) {
                ((RecyclerAdapter.MyViewHolder) holder).txt_booked_status.setText("Pending");
                ((RecyclerAdapter.MyViewHolder) holder).txt_booked_status.setBackgroundColor(getResources().getColor(R.color.orange_700));
                ((RecyclerAdapter.MyViewHolder) holder).underLine.setBackgroundColor(getResources().getColor(R.color.orange_700));
            } else if (model.getOrderStatus().contains("2")) {
                ((RecyclerAdapter.MyViewHolder) holder).txt_booked_status.setText("Delivered");
                ((RecyclerAdapter.MyViewHolder) holder).txt_booked_status.setBackgroundColor(getResources().getColor(R.color.green));
                ((RecyclerAdapter.MyViewHolder) holder).underLine.setBackgroundColor(getResources().getColor(R.color.green));
            } else if (model.getOrderStatus().contains("3")) {
                ((RecyclerAdapter.MyViewHolder) holder).txt_booked_status.setText("Rejected");
                ((RecyclerAdapter.MyViewHolder) holder).txt_booked_status.setBackgroundColor(getResources().getColor(R.color.red_400));
                ((RecyclerAdapter.MyViewHolder) holder).underLine.setBackgroundColor(getResources().getColor(R.color.red_400));
            } else if (model.getOrderStatus().contains("4")) {
                ((RecyclerAdapter.MyViewHolder) holder).txt_booked_status.setText("Partial Acceptance");
                ((RecyclerAdapter.MyViewHolder) holder).txt_booked_status.setBackgroundColor(getResources().getColor(R.color.orange_700));
                ((RecyclerAdapter.MyViewHolder) holder).underLine.setBackgroundColor(getResources().getColor(R.color.orange_700));

            }*/

            //((MyViewHolder) holder).txt_booked_status.setText(model.getOrderStatus());

            String date = model.getOrderDate();
            String[] array = date.split("T");
            String date1 = array[0];
            MyApplication.logi(LOG_TAG, "DATE-------" + date1);
            String time = array[1];

            ((RecyclerAdapter.MyViewHolder) holder).txt_date.setText(date1);
            ((RecyclerAdapter.MyViewHolder) holder).txt_status_with_time.setText("Processing " + time);
            ((RecyclerAdapter.MyViewHolder) holder).txt_rupees.setText(model.getAmount());
            // ((MyViewHolder) holder).txt_card_number.setText(model.getCart_count());

            int count_of_cart = TABLE_ORDER_DETAILS.getCountForSpecificOrderID(model.getOrderID());
            ((RecyclerAdapter.MyViewHolder) holder).txt_card_number.setText(count_of_cart + "");


        }

        @Override
        public int getItemCount() {
            return selectionList == null ? 0 : selectionList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public CustomTextViewMedium txt_shop_name, txt_card_number, txt_rupees;
            public CustomTextViewMedium txt_date, txt_order_status; //, balance; //address, contact
            public ImageView img_vechle;
            public CustomTextViewMedium processing, delivered;
            CustomTextViewMedium txt_name, txt_booked_status, txt_status_with_time;
            CustomUnderLine underLine;
            public ImageView img_name, img_call, img_info;

            public MyViewHolder(View view) {
                super(view);

                underLine = view.findViewById(R.id.underline);
                img_call = (ImageView) view.findViewById(R.id.img_call);
                img_info = (ImageView) view.findViewById(R.id.img_info);
                txt_status_with_time = view.findViewById(R.id.txt_status_with_time);
                txt_status_with_time.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_THEME_DARK_COLOR)));


                txt_name = view.findViewById(R.id.txt_shop_name);
                txt_name.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_THEME_DARK_COLOR)));

                txt_shop_name = view.findViewById(R.id.txt_shop_name);
                txt_shop_name.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_THEME_DARK_COLOR)));

                txt_date = view.findViewById(R.id.txt_date);
                txt_date.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_THEME_DARK_COLOR)));

               /* txt_booked_status = view.findViewById(R.id.txt_booked_status);
                txt_booked_status.setTextColor(getResources().getColor(R.color.black));
*/
                txt_card_number = view.findViewById(R.id.txt_cart_number);
                txt_card_number.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_THEME_DARK_COLOR)));

                txt_rupees = view.findViewById(R.id.txt_rupees);
                txt_rupees.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_THEME_DARK_COLOR)));

                processing = view.findViewById(R.id.processing);
                processing.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_TEXT_COLOR)));

                /// processing.setBackground(context.getResources().getDrawable(R.drawable.border_background_processing));


                delivered = view.findViewById(R.id.delivered);
                delivered.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_TEXT_COLOR)));

                /// processing.setBackground(context.getResources().getDrawable(R.drawable.border_background_processing));

                img_name = (ImageView) view.findViewById(R.id.img_name);
                img_vechle = (ImageView) view.findViewById(R.id.img_vechle);

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // get position
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION) {

                            OrderDeliveryStatusModel model = selectionList.get(pos);
                            MyApplication.logi(LOG_TAG, "VIEW CLICKED IS-->" + model.getDistributorId() + "ORDER ID-->" + model.getOrderID());
                            MyApplication.set_session("SESSION_SELECTED_ORDERID", model.getOrderID());
                            Intent intent = new Intent(ActivityPendingStatus.this, ActivityItemDetails.class);
                            startActivity(intent);
                            /// finish();
                            overridePendingTransition(R.anim.fade_in_return, R.anim.fade_out_return);
                            startActivity(intent);
                        }
                    }
                });
            }
        }
    }


    @Override
    public void onBackPressed() {

        MyApplication.logi(LOG_TAG, "in back pressed");
        Intent intent = new Intent(getApplicationContext(), ActivityDashBoard.class);

        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.fade_in_call, R.anim.fade_out_call);
        startActivity(intent);

    }

    private void registerObservers() {
        milkLtrsObservable = new MilkLtrsObservable();
        milkLtrsObservable.register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        milkLtrsObservable.unregister(this);
    }

    public void display_calling_alert(final String landline, final String mobile) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder
                //  .setTitle(name.toString())
                .setMessage("Mobile : " + mobile.toString() + "\nLandline : " + landline.toString())
                .setIcon(R.drawable.distributor_call)
                .setPositiveButton(mobile.toString(), new DialogInterface.OnClickListener() {
                    @SuppressLint("MissingPermission")
                    public void onClick(DialogInterface dialog, int which) {
                        if (!mobile.trim().equals("")) {

                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse("tel:" + mobile));
                            //startActivity(callIntent);
                            context.startActivity(callIntent);


                        } else {
                            Toast.makeText(context, "Mobile Number Not Available", Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .setNegativeButton(landline.toString(), new DialogInterface.OnClickListener() {
                    @SuppressLint("MissingPermission")
                    public void onClick(DialogInterface dialog, int which) {
                        if (!landline.trim().equals("")) {

                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse("tel:" + landline));
                            context.startActivity(callIntent);

                        } else {
                            Toast.makeText(context, "Landline Number Not Available", Toast.LENGTH_LONG).show();
                        }
                    }
                })                        //Do nothing on no
                .show();
    }


    public void display_information_alert(String info) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder
                //  .setTitle(name.toString())
                .setMessage("Information : " + info)
                .setIcon(R.drawable.distributor_call)
                .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })                        //Do nothing on no
                .show();
    }

}
