package com.sapl.retailerorderingmsdpharma.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.sapl.retailerorderingmsdpharma.MyDatabase.TABLE_PITEM;
import com.sapl.retailerorderingmsdpharma.R;
import com.sapl.retailerorderingmsdpharma.adapter.BrandAdapter;
import com.sapl.retailerorderingmsdpharma.adapter.DistributorDataAdapter;
import com.sapl.retailerorderingmsdpharma.adapter.SelectionDataAdapter;
import com.sapl.retailerorderingmsdpharma.customView.CustomEditTextMedium;
import com.sapl.retailerorderingmsdpharma.models.BrandModel;
import com.sapl.retailerorderingmsdpharma.models.DistributorModel;
import com.sapl.retailerorderingmsdpharma.models.SelectionDataModel;
import com.sapl.retailerorderingmsdpharma.models.SubGroupModel;

import java.util.ArrayList;
import java.util.List;

public class ActivityBrand extends AppCompatActivity {

    public static String LOG_TAG = "ActivityBrand";
    public static CustomEditTextMedium edt_search_distributor;
    Context context = null;
    List<BrandModel> brand = null, searchList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand);

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
    }


    public void initComponants() {
        edt_search_distributor = findViewById(R.id.edt_search_distributor);
        edt_search_distributor.setBackground(context.getResources().getDrawable(R.drawable.border_background));
        edt_search_distributor.setVisibility(View.GONE);

       // ActivitySelection.txt_title_with_header.setText(MyApplication.get_session("brand_title"));
    }

    public void initComponantListner() {


     /*   ActivitySelection.img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.logi(LOG_TAG,"img_search IS CLICKED IN  Activity");
            }
        });

        */

        edt_search_distributor.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String enteredName = charSequence.toString().trim();
                if (!TextUtils.isEmpty(enteredName)) {
                    if (brand != null && brand.size() > 0) {
                        searchList = new ArrayList<>();
                        for (int j = 0; j < brand.size(); j++) {
                            if (brand.get(j).getDivision().toLowerCase().contains(enteredName.toLowerCase())) {
                                searchList.add(brand.get(j));
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


        String data = "";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            data = extras.get("brand").toString();
            MyApplication.logi(LOG_TAG, "DATAAAA->" + data);

        }


        brand = TABLE_PITEM.getBrand(data, MyApplication.get_session(MyApplication.SESSION_GROUP_ID), MyApplication.get_session(MyApplication.SESSION_SUB_GROUP_ID));

        if (brand != null && brand.size() > 0) {
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setItemViewCacheSize(200);
            recyclerView.setDrawingCacheEnabled(true);
            recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
            recyclerView.smoothScrollBy(0, 10);
            BrandAdapter mAdapter = new BrandAdapter(context, brand);

            GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
            recyclerView.setLayoutManager(gridLayoutManager);


            recyclerView.setItemViewCacheSize(200);
            recyclerView.setDrawingCacheEnabled(true);
            recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
            recyclerView.smoothScrollBy(0, 10);

            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);
        }
    }

    public void bindSearchData(List<BrandModel> searchList) {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setItemViewCacheSize(200);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recyclerView.smoothScrollBy(0, 10);
        BrandAdapter mAdapter = new BrandAdapter(context, searchList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ActivityBrand.this, ActivitySelection.class);
        intent.putExtra("MOVE_TO_SUB_GROUP", "YES");
        startActivity(intent);
    }
    public void onResume() {
        MyApplication.logi(LOG_TAG, "RESUME IN BARND");
        super.onResume();
        context = this;
        initComponants();
        initComponantListner();
        bindData();

    }

}

