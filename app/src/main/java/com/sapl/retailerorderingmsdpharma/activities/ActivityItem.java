package com.sapl.retailerorderingmsdpharma.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.sapl.retailerorderingmsdpharma.MyDatabase.TABLE_PITEM;
import com.sapl.retailerorderingmsdpharma.MyDatabase.TABLE_TEMP_ORDER_DETAILS;
import com.sapl.retailerorderingmsdpharma.R;
import com.sapl.retailerorderingmsdpharma.adapter.SubItemDataAdapter;
import com.sapl.retailerorderingmsdpharma.customView.CustomButtonRegular;
import com.sapl.retailerorderingmsdpharma.customView.CustomEditTextMedium;
import com.sapl.retailerorderingmsdpharma.customView.CustomTextViewMedium;
import com.sapl.retailerorderingmsdpharma.models.SubItemDataModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ActivityItem extends AppCompatActivity {

    public static String LOG_TAG = "ActivityItem";
    public static CustomEditTextMedium edt_search_distributor;
    Context context = null;
    public CustomButtonRegular txt_order_preview, txt_bill_offer;
    public static CustomTextViewMedium txt_bill_price;
    List<SubItemDataModel> subItemList = null, searchList = null;
    ProgressDialog progressDialog;
    String data = "";
    LinearLayout btn_lin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
       /* if (MyApplication.get_session("direct_item").equalsIgnoreCase("yes")) {
            MyApplication.set_session("direct_item", "");
            if (MyApplication.get_session("bind_data_for_first_time").equalsIgnoreCase("no")) {

            } else {
                MyApplication.set_session("bind_data_for_first_time", "yes");
            }
        }
*/
        context = this;
        initComponants();
        initComponantListner();
        bindData();

    }


    public static boolean isSearchEditIsVisible() {
        if (edt_search_distributor.getVisibility() == View.VISIBLE)
            return true;
        else
            return false;

    }

    public static void hideSearchEdit() {
        edt_search_distributor.setVisibility(View.GONE);
        edt_search_distributor.setText("");

    }

    public static void showSearchEdit() {
        edt_search_distributor.setVisibility(View.VISIBLE);
        edt_search_distributor.setPadding(10,0,0,0);
        edt_search_distributor.requestFocus();

    }


    @SuppressLint("WrongViewCast")
    public void initComponants() {
        edt_search_distributor = findViewById(R.id.edt_search_distributor);
        edt_search_distributor.setBackground(context.getResources().getDrawable(R.drawable.border_background));
        edt_search_distributor.setVisibility(View.GONE);


        txt_order_preview = findViewById(R.id.txt_order_preview);
        edt_search_distributor.setVisibility(View.GONE);
        txt_bill_price = findViewById(R.id.txt_bill_price);
        txt_bill_price.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_Accent_Color)));
        txt_order_preview.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_Accent_Color)));

        txt_order_preview.setBackgroundColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_THEME_DARK_COLOR)));

        // ActivitySelection.txt_title_with_header.setText(MyApplication.get_session("item_title"));
        btn_lin = findViewById(R.id.btn_lin);
        btn_lin.setBackgroundColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_THEME_DARK_COLOR)));

    }


    public void initComponantListner() {


      /*  ActivitySelection.img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.logi(LOG_TAG,"img_search IS CLICKED IN item Activity");
            }
        });*/
        txt_bill_offer = findViewById(R.id.txt_bill_offer);
        txt_bill_offer.setBackgroundColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_THEME_DARK_COLOR)));
        txt_bill_offer.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_Accent_Color)));
        txt_bill_offer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(context, "This feature is coming soon", Toast.LENGTH_SHORT).show();
                MyApplication.displayMessage(context, "This feature is coming soon...");

            }
        });


        edt_search_distributor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String enteredName = charSequence.toString().trim();
                if (!TextUtils.isEmpty(enteredName)) {
                    if (subItemList != null && subItemList.size() > 0) {
                        searchList = new ArrayList<>();
                        for (int j = 0; j < subItemList.size(); j++) {
                            if (subItemList.get(j).getSub_item_name().toLowerCase().contains(enteredName.toLowerCase())) {
                                searchList.add(subItemList.get(j));
                            }
                        }
                    }
                    if (searchList != null) {
                        bindSearchData(searchList);
                    } else {
                        Toast.makeText(context, "No data found", Toast.LENGTH_LONG).show();
                    }
                } else {
                    bindData();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        //ORDER PREVIEW CLICK
        txt_order_preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int count_added_to_cart = TABLE_TEMP_ORDER_DETAILS.countOfAddToCardItems(MyApplication.get_session(MyApplication.SESSION_ORDER_ID));
                MyApplication.logi(LOG_TAG, "count in carts are-->" + count_added_to_cart);

                if (count_added_to_cart == 0) {
                    MyApplication.displayMessage(context, "No Items are added to cart yet");
                } else {
                    Intent i = new Intent(getApplicationContext(), ActivityPreviewOrder.class);
                    finish();
                    overridePendingTransition(R.anim.fade_in_call, R.anim.fade_out_call);
                    startActivity(i);
                }
            }
        });
    }

    public void bindData() {
        MyApplication.logi(LOG_TAG, "  bindData() ");

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            data = extras.get("item").toString();
            MyApplication.logi(LOG_TAG, "DATAAAA for item->" + data);

        }
        Date currentTime = Calendar.getInstance().getTime();
        MyApplication.logi(LOG_TAG, "mk  begore bringing all items-->" + currentTime);


        /*if (MyApplication.get_session("direct_item").equalsIgnoreCase("yes")) {
            MyApplication.set_session("direct_item","");
            MyApplication.logi(LOG_TAG, "in if of bind_data_for_first_time is yes");
            new LoginAsyncTask().execute();
        } else {*/
            MyApplication.logi(LOG_TAG, "in else of bind_data_for_first_time is yes");
            subItemList = TABLE_PITEM.getSubItems(data, MyApplication.get_session(MyApplication.SESSION_GROUP_ID),
                    MyApplication.get_session(MyApplication.SESSION_SUB_GROUP_ID),
                    MyApplication.get_session(MyApplication.SESSION_BRAND_ID),
                    MyApplication.get_session(MyApplication.SESSION_DIST_PRICE_LIST_ID), MyApplication.get_session(MyApplication.SESSION_UOM_ORDER_1)); //WE ARE SENDING ONLY SESSION_UOM_ORDER_1 BECAUSE FOR BOTTLE WE ARE TAKING FROM PRICE_LIST_DETAILS-->SALES_RATE

     //   }
            Date currentTime1 = Calendar.getInstance().getTime();
            MyApplication.logi(LOG_TAG, "mk  aftre bringing all items-->" + currentTime1);
            MyApplication.logi(LOG_TAG, "subItemList------->" + subItemList);

            if (subItemList != null && subItemList.size() > 0) {
                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
                recyclerView.setNestedScrollingEnabled(false);
                recyclerView.setItemViewCacheSize(200);
                recyclerView.setDrawingCacheEnabled(true);
                recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
                recyclerView.smoothScrollBy(0, 10);
                SubItemDataAdapter mAdapter = new SubItemDataAdapter(context, subItemList, 0.f);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);

               /* GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
                recyclerView.setLayoutManager(gridLayoutManager);
*/
                //for smooth recycler
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setItemViewCacheSize(200);
                recyclerView.setDrawingCacheEnabled(true);
                recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
                recyclerView.smoothScrollBy(0, 10);
                recyclerView.setAdapter(mAdapter);


            }

        }


        private class LoginAsyncTask extends AsyncTask<Void, Void, List<SubItemDataModel>> {
            @Override
            protected void onPreExecute() {
                progressDialog = new ProgressDialog(ActivityItem.this);
                progressDialog.setMessage("Please wait loding data...");
                progressDialog.show();
                super.onPreExecute();
            }

            protected List<SubItemDataModel> doInBackground(Void... args) {
                // Parsse response data


                subItemList = TABLE_PITEM.getSubItems(data, MyApplication.get_session(MyApplication.SESSION_GROUP_ID),
                        MyApplication.get_session(MyApplication.SESSION_SUB_GROUP_ID),
                        MyApplication.get_session(MyApplication.SESSION_BRAND_ID),
                        MyApplication.get_session(MyApplication.SESSION_DIST_PRICE_LIST_ID), MyApplication.get_session(MyApplication.SESSION_UOM_ORDER_1)); //WE ARE SENDING ONLY SESSION_UOM_ORDER_1 BECAUSE FOR BOTTLE WE ARE TAKING FROM PRICE_LIST_DETAILS-->SALES_RATE


                MyApplication.logi(LOG_TAG, "SUBITMES ARE-->" + subItemList);
                return subItemList;
            }

            protected void onPostExecute(List<SubItemDataModel> result) {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();


                subItemList = TABLE_PITEM.getSubItems(data, MyApplication.get_session(MyApplication.SESSION_GROUP_ID),
                        MyApplication.get_session(MyApplication.SESSION_SUB_GROUP_ID),
                        MyApplication.get_session(MyApplication.SESSION_BRAND_ID),
                        MyApplication.get_session(MyApplication.SESSION_DIST_PRICE_LIST_ID), MyApplication.get_session(MyApplication.SESSION_UOM_ORDER_1)); //WE ARE SENDING ONLY SESSION_UOM_ORDER_1 BECAUSE FOR BOTTLE WE ARE TAKING FROM PRICE_LIST_DETAILS-->SALES_RATE


                Date currentTime1 = Calendar.getInstance().getTime();
                MyApplication.logi(LOG_TAG, "mk  aftre bringing all items-->" + currentTime1);
                MyApplication.logi(LOG_TAG, "subItemList------->" + subItemList);

                if (subItemList != null && subItemList.size() > 0) {
                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
                    recyclerView.setNestedScrollingEnabled(false);
                    recyclerView.setItemViewCacheSize(200);
                    recyclerView.setDrawingCacheEnabled(true);
                    recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
                    recyclerView.smoothScrollBy(0, 10);
                    SubItemDataAdapter mAdapter = new SubItemDataAdapter(context, subItemList, 0.f);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(mLayoutManager);

               /* GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
                recyclerView.setLayoutManager(gridLayoutManager);
*/
                    //for smooth recycler
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setItemViewCacheSize(200);
                    recyclerView.setDrawingCacheEnabled(true);
                    recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
                    recyclerView.smoothScrollBy(0, 10);
                    recyclerView.setAdapter(mAdapter);


                    super.onPostExecute(result);
                }
            }
        }


    public void bindSearchData(List<SubItemDataModel> searchList) {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setHasFixedSize(true);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recyclerView.smoothScrollBy(0, 10);
        SubItemDataAdapter mAdapter = new SubItemDataAdapter(context, searchList, 0.f);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ActivityItem.this, ActivitySelection.class);
        intent.putExtra("MOVE_TO_BRAND", "YES");
        startActivity(intent);


    }


    public void onResume() {
        MyApplication.logi(LOG_TAG, "RESUME IN ITEM");
        super.onResume();
        context = this;

        if (MyApplication.get_session("new_brand_is_selected").equalsIgnoreCase("yes")) {
            MyApplication.set_session("new_brand_is_selected", "");
            MyApplication.logi(LOG_TAG, "itemsss NEW DATA TO BE LOAD");
            initComponants();
            initComponantListner();
            bindData();
        } else {
            MyApplication.logi(LOG_TAG, "itemsss Fresh selection of tab");
        }
    }


}