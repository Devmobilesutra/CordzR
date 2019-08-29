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
import com.sapl.retailerorderingmsdpharma.adapter.DistributorDataAdapter;
import com.sapl.retailerorderingmsdpharma.adapter.SelectionDataAdapter;
import com.sapl.retailerorderingmsdpharma.adapter.SubGroupAdapter;
import com.sapl.retailerorderingmsdpharma.customView.CustomEditTextMedium;
import com.sapl.retailerorderingmsdpharma.models.DistributorModel;
import com.sapl.retailerorderingmsdpharma.models.DivisionModel;
import com.sapl.retailerorderingmsdpharma.models.SelectionDataModel;
import com.sapl.retailerorderingmsdpharma.models.SubGroupModel;

import java.util.ArrayList;
import java.util.List;


public class ActivitySubGroup extends AppCompatActivity {

    public static String LOG_TAG = "ActivitySubGroup"; //THIS IS ACTIVITY ITEMFROUP
    public  static CustomEditTextMedium edt_search_distributor;
    Context context = null;
    List<SubGroupModel> sub_group = null, searchList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_group);

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

    }

    public static void showSearchEdit() {
        edt_search_distributor.setVisibility(View.VISIBLE);
    }





    public void initComponants() {
        edt_search_distributor = findViewById(R.id.edt_search_distributor);
        edt_search_distributor.setBackground(context.getResources().getDrawable(R.drawable.border_background));
        edt_search_distributor.setVisibility(View.GONE);


      //  ActivitySelection.txt_title_with_header.setText(MyApplication.get_session("sub_group_title"));

    }

    public void initComponantListner() {


      /*  ActivitySelection.img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.logi(LOG_TAG,"img_search IS CLICKED IN subGroup Activity");
            }
        });
*/


        edt_search_distributor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String enteredName = charSequence.toString().trim();
                if (!TextUtils.isEmpty(enteredName)) {
                    if (sub_group != null && sub_group.size() > 0) {
                        searchList = new ArrayList<>();
                        for (int j = 0; j < sub_group.size(); j++) {
                            if (sub_group.get(j).getDivision().toLowerCase().contains(enteredName.toLowerCase())) {
                                searchList.add(sub_group.get(j));
                            }
                        }
                    }
                    if (searchList != null) {
                        bindSearchData(searchList);
                    } else {
                        //Toast.makeText(context, "No data found", Toast.LENGTH_LONG).show();
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
            data = extras.get("sub_group").toString();
            MyApplication.logi(LOG_TAG, "DATAAAA->" + data);
            MyApplication.set_session(MyApplication.SESSION_DIVISION_ID,data);

        }

      /*  List<SelectionDataModel> groupList = new ArrayList<>();
        groupList = MyApplication.getSelectionList("subgroup");*/

        sub_group = TABLE_PITEM.getSubGroup(MyApplication.get_session(MyApplication.SESSION_DIVISION_ID), MyApplication.get_session(MyApplication.SESSION_GROUP_ID));
        MyApplication.logi(LOG_TAG, "itemGroup issssssss-->" + sub_group);
        MyApplication.logi(LOG_TAG, "bindData(), Size -->" + sub_group.size());

        if (sub_group != null && sub_group.size() > 0) {
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setItemViewCacheSize(200);
            recyclerView.setDrawingCacheEnabled(true);
            recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
            recyclerView.smoothScrollBy(0, 10);
            SubGroupAdapter mAdapter = new SubGroupAdapter(context, sub_group);

           /* RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
*/
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
            recyclerView.setLayoutManager(gridLayoutManager);

            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);
        }
    }

    public void bindSearchData(List<SubGroupModel> searchList) {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setItemViewCacheSize(200);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recyclerView.smoothScrollBy(0, 10);
        SubGroupAdapter mAdapter = new SubGroupAdapter(context, searchList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ActivitySubGroup.this, ActivitySelection.class);
        intent.putExtra("MOVE_TO_GROUP", "YES");
        startActivity(intent);
    }



    public void onResume() {
        MyApplication.logi(LOG_TAG, "RESUME IN SUB GROUP");
        super.onResume();

        context = this;
        initComponants();
        initComponantListner();
        bindData();
    }

}

