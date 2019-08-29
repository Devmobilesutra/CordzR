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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.sapl.retailerorderingmsdpharma.MyDatabase.TABLE_PITEM;
import com.sapl.retailerorderingmsdpharma.R;
import com.sapl.retailerorderingmsdpharma.adapter.DivisionAdapter;
import com.sapl.retailerorderingmsdpharma.customView.CustomEditTextMedium;
import com.sapl.retailerorderingmsdpharma.customView.CustomTextViewMedium;
import com.sapl.retailerorderingmsdpharma.models.DivisionModel;


import java.util.ArrayList;
import java.util.List;


public class GroupActivity extends AppCompatActivity {

    public static String LOG_TAG = "GroupActivity";
    public static CustomEditTextMedium edt_search_distributor;
    Context context = null;
    List<DivisionModel> groupList1 = null, searchList = null;
   public static Animation animBounce ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
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
        /*animBounce = AnimationUtils.loadAnimation(this,R.anim.fade_in_call);
        edt_search_distributor.setAnimation(animBounce);*/
    }

    public void initComponants() {
        edt_search_distributor = findViewById(R.id.edt_search_distributor);
        edt_search_distributor.setBackground(getResources().getDrawable(R.drawable.border_background));
        edt_search_distributor.setVisibility(View.GONE);
    }

    public void initComponantListner() {


       /* ActivitySelection.img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.logi(LOG_TAG,"img_search IS CLICKED IN Group Activity");
            }
        });*/




        edt_search_distributor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String enteredName = charSequence.toString().trim();
               // String enteredName = MyApplication.get_session("entered_value_for_division");
                if (!TextUtils.isEmpty(enteredName)) {
                    if (groupList1 != null && groupList1.size() > 0) {
                        searchList = new ArrayList<>();
                        for (int j = 0; j < groupList1.size(); j++) {
                            if (groupList1.get(j).getDivision().toLowerCase().contains(enteredName.toLowerCase())) {
                                searchList.add(groupList1.get(j));
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
       /* List<SelectionDataModel> groupList = new ArrayList<>();
        groupList = MyApplication.getSelectionList("group");*/

        String data = "";

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            data = extras.get("group").toString();
            MyApplication.logi(LOG_TAG, "DATAAAA->" + data);

        }
        // groupList1 = TABLE_PITEM.getDivision(MyApplication.get_session(MyApplication.SESSION_FILTER_DIVISION));
        groupList1 = TABLE_PITEM.getDivision(data);
        MyApplication.logi(LOG_TAG, "bindData() GROUPLIST111-->" + groupList1);
        MyApplication.logi(LOG_TAG, "bindData() groupList1. SIZE: " + groupList1.size());

        if (groupList1 != null && groupList1.size() > 0) {
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setItemViewCacheSize(200);
            recyclerView.setDrawingCacheEnabled(true);
            recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
            recyclerView.smoothScrollBy(0, 10);
            DivisionAdapter mAdapter = new DivisionAdapter(context, groupList1);

            /*RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);*/

            GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
            recyclerView.setLayoutManager(gridLayoutManager);

            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);
        }
    }

    public void bindSearchData(List<DivisionModel> searchList) {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setItemViewCacheSize(200);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recyclerView.smoothScrollBy(0, 10);
        DivisionAdapter mAdapter = new DivisionAdapter(context, searchList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(context, ActivityDistributorList.class);
        finish();
        overridePendingTransition(R.anim.fade_in_call, R.anim.fade_out_call);
        context.startActivity(intent);

    }

    public void onResume() {
        MyApplication.logi(LOG_TAG, "RESUME IN GROUP");
        super.onResume();

        context = this;
        initComponants();
        initComponantListner();
        bindData();
    }

}
