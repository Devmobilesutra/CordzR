package com.sapl.retailerorderingmsdpharma.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.sapl.retailerorderingmsdpharma.MyDatabase.TABLE_ORDER_DETAILS;
import com.sapl.retailerorderingmsdpharma.MyDatabase.TABLE_ORDER_STATUS;
import com.sapl.retailerorderingmsdpharma.R;
import com.sapl.retailerorderingmsdpharma.adapter.BrandAdapter;
import com.sapl.retailerorderingmsdpharma.adapter.ItemListAdapter;
import com.sapl.retailerorderingmsdpharma.adapter.OrderReviewAdapter;
import com.sapl.retailerorderingmsdpharma.customView.CircularTextView;
import com.sapl.retailerorderingmsdpharma.customView.CustomButtonRegular;
import com.sapl.retailerorderingmsdpharma.customView.CustomTextViewMedium;
import com.sapl.retailerorderingmsdpharma.models.OrderReviewModel;

import java.util.ArrayList;
import java.util.List;

import static com.sapl.retailerorderingmsdpharma.R.id.toolbars;

public class ActivityItemDetails extends AppCompatActivity {
    public static String LOG_TAG = "ActivityItemDetails";

    Context context = null;
    Button btn_delivered;
    ImageView img_cart,img_back;
    public static CircularTextView txt_no_of_product_taken;
  public static CustomTextViewMedium txt_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);





    context = this;
        initComponants();
        initComponantListner();
        bindData();
    }

    private void initComponantListner() {
        img_back = findViewById(R.id.img_back);
        btn_delivered= findViewById(R.id.btn_delivered);
        btn_delivered.setTextColor((Color.parseColor(MyApplication.get_session(MyApplication.SESSION_Accent_Color))));
        btn_delivered.setBackgroundColor((Color.parseColor(MyApplication.get_session(MyApplication.SESSION_THEME_DARK_COLOR))));
        img_back.setVisibility(View.VISIBLE);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(context, "This feature is coming soon", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ActivityItemDetails.this, ActivityStatusTabs.class);
               
                startActivity(intent);

            }
        });
    }

    private void initComponants() {
        img_cart = findViewById(R.id.img_cart);
        txt_title=findViewById(R.id.txt_title);
        txt_title.setText("Item Details");
        txt_title.setVisibility(View.VISIBLE);
        txt_title.setTextColor(Color.parseColor("#000000"));
        img_cart.setVisibility(View.GONE);
        txt_no_of_product_taken = findViewById(R.id.txt_no_of_product_taken);
        txt_no_of_product_taken.setVisibility(View.GONE);
    }


    public void bindData() {

        List<OrderReviewModel> items = new ArrayList<>();
        items = TABLE_ORDER_DETAILS.getItemDetails(MyApplication.get_session("SESSION_SELECTED_ORDERID"));

        MyApplication.logi(LOG_TAG,"ORDER ID----->"+MyApplication.get_session("SESSION_SELECTED_ORDERID"));
        MyApplication.logi(LOG_TAG,"ITEMMMMMMSSSS FOR ORDER ID ISS--->"+items);


        if (items != null && items.size() > 0) {
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setItemViewCacheSize(200);
            recyclerView.setDrawingCacheEnabled(true);
            recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
            recyclerView.smoothScrollBy(0, 20);
            ItemListAdapter mAdapter = new ItemListAdapter(context, items);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);

        }
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ActivityItemDetails.this, ActivityStatusTabs.class);
        intent.putExtra("MOVE_TO_SUB_GROUP", "YES");
        startActivity(intent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}





