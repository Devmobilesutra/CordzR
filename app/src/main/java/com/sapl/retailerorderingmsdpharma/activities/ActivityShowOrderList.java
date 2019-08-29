package com.sapl.retailerorderingmsdpharma.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.sapl.retailerorderingmsdpharma.R;

//this activity is not used anywhere yet
public class ActivityShowOrderList extends AppCompatActivity {

    ImageView img_sync, img_cart;
    Context context;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_order_list);
        context = this;

        initComponants();
        initComponantListner();
        initBindData();
    }

    public void initComponants(){
        img_sync = (ImageView) findViewById(R.id.img_sync);
        img_sync.setVisibility(View.VISIBLE);
        img_cart = (ImageView) findViewById(R.id.img_cart);
        img_cart.setVisibility(View.VISIBLE);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
    }

    public void initComponantListner(){
        img_sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(context, "You Clicked img_sync",Toast.LENGTH_SHORT).show();
            }
        });
        img_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(context, "You Clicked img_cart ",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void initBindData(){


        /*List<OrderModel> orderList = new ArrayList<>();
        orderList = MyApplication.getOrderList();

        if (distributorList != null && distributorList.size() > 0) {
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setItemViewCacheSize(200);
            recyclerView.setDrawingCacheEnabled(true);
            recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
            recyclerView.smoothScrollBy(0,10);
            DistributorDataAdapter mAdapter = new DistributorDataAdapter(context, distributorList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);
        }*/
    }
}
