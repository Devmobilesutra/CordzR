package com.sapl.retailerorderingmsdpharma.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.sapl.retailerorderingmsdpharma.MyDatabase.TABLE_ORDER_DETAILS;
import com.sapl.retailerorderingmsdpharma.MyDatabase.TABLE_ORDER_STATUS;
import com.sapl.retailerorderingmsdpharma.R;
import com.sapl.retailerorderingmsdpharma.adapter.BrandAdapter;
import com.sapl.retailerorderingmsdpharma.adapter.ItemListAdapter;
import com.sapl.retailerorderingmsdpharma.adapter.OrderReviewAdapter;
import com.sapl.retailerorderingmsdpharma.customView.CircularTextView;
import com.sapl.retailerorderingmsdpharma.customView.CustomButtonRegular;
import com.sapl.retailerorderingmsdpharma.models.OrderReviewModel;

import java.util.ArrayList;
import java.util.List;

public class ActivityItemDetailsNew extends AppCompatActivity {
    public static String LOG_TAG = "ActivityItemDetails";

    Context context = null;
    CustomButtonRegular btn_delivered;
    ImageView img_cart,img_menu;
    public static CircularTextView txt_no_of_product_taken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details_new);
        context = this;
        initComponants();
        initComponantListner();
        bindData();
    }

    private void initComponantListner() {
        btn_delivered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.logi(LOG_TAG,"IN delivered");
                Intent intent  = new Intent(context,ActivityStatusTabs.class);
                finish();
                overridePendingTransition(R.anim.fade_in_call, R.anim.fade_out_call);
                startActivity(intent);


            }
        });


        img_menu = findViewById(R.id.img_menu);
        img_menu.setVisibility(View.GONE);
        img_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "This feature is coming soon", Toast.LENGTH_SHORT).show();
                MyApplication.displayMessage(context,"This feature is coming soon...");

            }
        });

    }

    private void initComponants() {
        img_cart = findViewById(R.id.img_cart);
        img_cart.setVisibility(View.GONE);
        txt_no_of_product_taken = findViewById(R.id.txt_no_of_product_taken);
        txt_no_of_product_taken.setVisibility(View.GONE);
        btn_delivered = findViewById(R.id.btn_delivered);
    }


    public void bindData() {

        List<OrderReviewModel> items = new ArrayList<>();
        items = TABLE_ORDER_DETAILS.getItemDetails(MyApplication.get_session("SESSION_SELECTED_ORDERID"));

        MyApplication.logi(LOG_TAG,"ORDER ID----->"+MyApplication.get_session("SESSION_SELECTED_ORDERID"));



        if (items != null && items.size() > 0) {
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setItemViewCacheSize(200);
            recyclerView.setDrawingCacheEnabled(true);
            recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
            recyclerView.smoothScrollBy(0, 10);
            ItemListAdapter mAdapter = new ItemListAdapter(context, items);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);

        }
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ActivityItemDetailsNew.this, ActivityStatusTabs.class);
        intent.putExtra("MOVE_TO_SUB_GROUP", "YES");
        startActivity(intent);
    }
}





