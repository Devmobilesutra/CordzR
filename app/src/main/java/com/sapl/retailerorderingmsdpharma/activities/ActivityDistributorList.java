package com.sapl.retailerorderingmsdpharma.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.sapl.retailerorderingmsdpharma.MyDatabase.MyDataBaseD;
import com.sapl.retailerorderingmsdpharma.MyDatabase.TABLE_PDISTRIBUTOR;
import com.sapl.retailerorderingmsdpharma.MyDatabase.TABLE_RETAILER_ORDER_MASTER;
import com.sapl.retailerorderingmsdpharma.MyDatabase.TABLE_TEMP_ORDER_DETAILS;
import com.sapl.retailerorderingmsdpharma.R;
import com.sapl.retailerorderingmsdpharma.adapter.DistributorDataAdapter;
import com.sapl.retailerorderingmsdpharma.customView.CircularTextView;
import com.sapl.retailerorderingmsdpharma.customView.CustomEditTextMedium;
import com.sapl.retailerorderingmsdpharma.customView.CustomTextViewMedium;
import com.sapl.retailerorderingmsdpharma.models.DistributorModel;

import java.util.ArrayList;
import java.util.List;

import static com.sapl.retailerorderingmsdpharma.activities.MyApplication.LOG_TAG;
import static com.sapl.retailerorderingmsdpharma.activities.MyApplication.db;

public class ActivityDistributorList extends AppCompatActivity implements DistributorDataAdapter.DistributorDataAdapterlistner {

    Context context = null;
    CustomEditTextMedium edt_search_distributor;
    CustomTextViewMedium txt_title;
    List<DistributorModel> distributorList1 = null, searchList = null;
    CircularTextView txt_no_of_product_taken;
    ImageView img_cart, img_back;
    RelativeLayout dash_rr,rr2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distributor_list);
        context = this;
        MyApplication.set_session("preview_order", "");
        initComponants();
        initComponantListner();
        bindData();

    }

    public void initComponants() {
        edt_search_distributor = findViewById(R.id.edt_search_distributor);
        img_cart = findViewById(R.id.img_cart);

        txt_no_of_product_taken = findViewById(R.id.txt_no_of_product_taken);
        txt_title = findViewById(R.id.txt_title);
        txt_title.setTextColor((Color.parseColor(MyApplication.get_session(MyApplication.SESSION_Text_Primary_Color))));
        txt_title.setText("Distributor Selection");

       /* txt_no_of_product_taken.setStrokeColor(MyApplication.get_session(MyApplication.SESSION_HEADING_TEXT_COLOR));
        txt_no_of_product_taken.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR)));
*/


        txt_no_of_product_taken.setStrokeColor(MyApplication.get_session(MyApplication.SESSION_THEME_DARK_COLOR));

        txt_no_of_product_taken.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_Theme_Primary_Text_Color)));

        int main_cart_count = TABLE_PDISTRIBUTOR.countOfAddToCardItems();
        txt_no_of_product_taken.setText(main_cart_count + "");
        MyApplication.logi(LOG_TAG, "COUNT OF CARTS MAIN IS-->" + main_cart_count);
        dash_rr = findViewById(R.id.dash_rr);
       // dash_rr.setBackgroundColor((Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR))));
        dash_rr.setBackgroundColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_Accent_Color)));
        rr2 = findViewById(R.id.rr2);
       // rr2.setBackgroundColor((Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR))));
        //dash_rr.setBackgroundColor(getResources().getColor(R.color.white));
    }

    public void initComponantListner() {
        img_back = findViewById(R.id.img_back);
        img_back.setVisibility(View.VISIBLE);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(context, "This feature is coming soon", Toast.LENGTH_SHORT).show();
                MyApplication.displayMessage(context, "This feature is coming soon...");

            }
        });


        img_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    int countOfAddToCardItems=TABLE_PDISTRIBUTOR.countOfAddToCardItems();
                    if(countOfAddToCardItems==0){
                     MyApplication.displayMessage(context,"No items are added to cart yet..");
                    }else {
                    MyApplication.set_session("distributor_list", "cart");
                    bindData();
                }
            }
        });



        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
     Intent intent = new Intent(ActivityDistributorList.this, ActivityDashBoard.class);
                    startActivity(intent);

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
                    if (distributorList1 != null && distributorList1.size() > 0) {
                        searchList = new ArrayList<>();
                        for (int j = 0; j < distributorList1.size(); j++) {
                            if (distributorList1.get(j).getName().toLowerCase().contains(enteredName.toLowerCase())) {
                                searchList.add(distributorList1.get(j));
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

    }

    public void bindData() {

//CHECK FRST FROM WHERE THE DISTRIBUOTR LIST IS TO BE SHOWN
        if (MyApplication.get_session("distributor_list").equalsIgnoreCase("cart")) {
            MyApplication.logi(LOG_TAG, "session cart-->");
            distributorList1 = TABLE_PDISTRIBUTOR.getDistributorName();

        } else if (MyApplication.get_session("distributor_list").equalsIgnoreCase("card_order_booking")) {
            MyApplication.logi(LOG_TAG, "session card_order_booking-->");
            distributorList1 = TABLE_PDISTRIBUTOR.getDistributorName();
        }
        MyApplication.logi(LOG_TAG, "distributorList1-->" + distributorList1);

        /*if (distributorList1.isEmpty()) {
            MyApplication.displayMessage(context, "No Items are added to cart yet");

        } else {*/
            if (distributorList1 != null && distributorList1.size() > 0) {
                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
                recyclerView.setNestedScrollingEnabled(false);
                recyclerView.setItemViewCacheSize(200);
                recyclerView.setDrawingCacheEnabled(true);
                recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
                recyclerView.smoothScrollBy(0, 10);
                final DistributorDataAdapter mAdapter = new DistributorDataAdapter(context, distributorList1);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(mAdapter);

            }
       // }


    }

    //THIS FUNCTION IS USED TO SET THHE SEARCHED DATA
    public void bindSearchData(List<DistributorModel> searchList) {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setItemViewCacheSize(200);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recyclerView.smoothScrollBy(0, 10);
        DistributorDataAdapter mAdapter = new DistributorDataAdapter(context, searchList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
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


    @Override
    public void onContactSelected(DistributorModel distributorModel) {
        Toast.makeText(context, "Name" + distributorModel.getName(), Toast.LENGTH_SHORT);
    }
}
