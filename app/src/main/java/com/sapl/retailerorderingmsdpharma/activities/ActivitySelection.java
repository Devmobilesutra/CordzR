package com.sapl.retailerorderingmsdpharma.activities;

import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.oshi.libsearchtoolbar.SearchAnimationToolbar;
import com.sapl.retailerorderingmsdpharma.MyDatabase.TABLE_PDISTRIBUTOR;
import com.sapl.retailerorderingmsdpharma.MyDatabase.TABLE_PITEM;
import com.sapl.retailerorderingmsdpharma.MyDatabase.TABLE_PRICELIST_DETAILS;
import com.sapl.retailerorderingmsdpharma.MyDatabase.TABLE_PRICE_LIST;
import com.sapl.retailerorderingmsdpharma.MyDatabase.TABLE_SETTINGS;
import com.sapl.retailerorderingmsdpharma.R;
import com.sapl.retailerorderingmsdpharma.adapter.DivisionAdapter;
import com.sapl.retailerorderingmsdpharma.adapter.SubGroupAdapter;
import com.sapl.retailerorderingmsdpharma.customView.CircularTextView;
import com.sapl.retailerorderingmsdpharma.customView.CustomEditTextMedium;
import com.sapl.retailerorderingmsdpharma.customView.CustomTextViewMedium;
import com.sapl.retailerorderingmsdpharma.models.DivisionModel;
import com.sapl.retailerorderingmsdpharma.models.SubGroupModel;

import java.util.ArrayList;
import java.util.List;


public class ActivitySelection extends TabActivity {

    public static TabHost tabHost;
    Context context;
    String LOG_TAG = "ActivitySelection ";
    public static CircularTextView txt_no_of_product_taken;
    ImageView img_cart, img_back, img_clear_filter;
    public static ImageView img_search;
    public static CustomTextViewMedium txt_title_with_header, txt_dist_name;
    TabLayout tabLayout;
    ViewPager viewPager;
    RelativeLayout rel_edt;
    private SearchAnimationToolbar toolbar;
    Animation animBounce;
    public static CustomEditTextMedium edt_search_distributor;
    public static ImageView edt_cancel_distributor;
    List<DivisionModel> groupList1 = null, searchList = null;
    List<SubGroupModel> sub_group = null, searchList_group = null;
    FloatingActionButton clearFilter;
    public ProgressDialog dialog;
    RelativeLayout rr2;
    String DistributorName;
    String DistributorId;
    String tab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);
        context = this;
        Bundle extras1 = getIntent().getExtras();
        if (extras1 != null){
            DistributorName= extras1.getString("DistributorName");
            MyApplication.logi(LOG_TAG, "DistributorName-->" + DistributorName);
            DistributorId= extras1.getString("DistributorId");
            MyApplication.logi(LOG_TAG, "DistributorId-->" + DistributorId);
            try {
                MyApplication.set_session(MyApplication.SESSION_DISTRIBUTER_NAME1, DistributorName);
                MyApplication.set_session(MyApplication.SESSION_DISTRIBUTER_ID, DistributorId);
            }catch(Exception e){e.printStackTrace();}


            Bundle extras = getIntent().getExtras();
            if (extras != null){
                tab= extras.getString("tab");
            }
        }
        initTabHost();
        initComponants();
        initComponantsListner();


        String dist_price_id = TABLE_PRICELIST_DETAILS.getPriceListIDOfDistributor(MyApplication.get_session(MyApplication.SESSION_DISTRIBUTOR_ID));
     //   String dist_price_id = TABLE_PRICE_LIST.getPriceListIDOfDistributor(MyApplication.get_session(MyApplication.SESSION_DISTRIBUTOR_ID));
        MyApplication.logi(LOG_TAG, "dist_price_id-->" + dist_price_id);
        MyApplication.set_session(MyApplication.SESSION_DIST_PRICE_LIST_ID, dist_price_id);
        //set tab which one you want to open first time 0 or 1 or 2
        //CODE FOR SCROLLING THE TITTLE
        for (int j = 0; j < tabHost.getTabWidget().getChildCount(); j++) {

            TextView tv = (TextView) tabHost.getTabWidget().getChildAt(j).findViewById(android.R.id.title);
//            ImageView iv  = (ImageView)tabHost.getTabWidget().getChildAt(j).findViewById(android.R.id.icon);
            // tv.setSingleLine();
        }

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {

            @Override
            public void onTabChanged(String tabId) {

                for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
                    MyApplication.logi(LOG_TAG, "TAB IS CHANGED---->" + tabId);


                    View tabIndicator = LayoutInflater.from(context).inflate(R.layout.tab_indicator, getTabWidget(), false);
                    // TextView tv = ((TextView) tabIndicator.findViewById(R.id.title));

                    //before selection
                    TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(R.id.title);
                    tv.setTextColor(context.getResources().getColor(R.color.grey_400));
                    ImageView icon = (ImageView) tabHost.getTabWidget().getChildAt(i).findViewById(R.id.icon);
                    icon.setBackgroundColor(getResources().getColor(R.color.white));
                    //     icon.setBackground(getResources().getDrawable(R.drawable.circle));
                    //icon.setBackground(getResources().getDrawable(R.drawable.circle_background_white));

                }

                View tabIndicator = LayoutInflater.from(context).inflate(R.layout.tab_indicator, getTabWidget(), false);


                //on selection
                ImageView icon = (ImageView) tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).findViewById(R.id.icon);
                icon.setBackgroundColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_THEME_DARK_COLOR)));
                TextView tv = (TextView) tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).findViewById(R.id.title);
                tv.setTextColor(context.getResources().getColor(R.color.black));


            }
        });


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (getIntent().getExtras().containsKey("MOVE_TO_ITEM")) {
                MyApplication.logi(LOG_TAG, "in MOVE_TO_ITEM get extras yes");
                tabHost.setCurrentTab(3);
            } else if (getIntent().getExtras().containsKey("MOVE_TO_BRAND")) {
                // ActivitySelection.txt_title_with_header.setText(MyApplication.get_session("brand_title"));
                tabHost.setCurrentTab(2);
            } else if (getIntent().getExtras().containsKey("MOVE_TO_SUB_GROUP")) {
                // ActivitySelection.txt_title_with_header.setText(MyApplication.get_session("sub_group_title"));
                tabHost.setCurrentTab(1);
            } else if (getIntent().getExtras().containsKey("MOVE_TO_GROUP")) {
                // ActivitySelection.txt_title_with_header.setText(MyApplication.get_session("group_title"));
                tabHost.setCurrentTab(0);
            }

        }


    }

    private void initComponantsListner() {


        img_back = findViewById(R.id.img_back);
        img_back.setVisibility(View.VISIBLE);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ActivityDistributorList.class);
                finish();
                overridePendingTransition(R.anim.fade_in_call, R.anim.fade_out_call);
                context.startActivity(intent);

            }
        });


        img_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.logi(LOG_TAG, "ON CLICK OF img_cart");
                int countOfAddToCardItems = TABLE_PDISTRIBUTOR.countOfAddToCardItems();
                if (countOfAddToCardItems == 0) {
                    MyApplication.displayMessage(context, "No items are added to cart yet..");
                } else {
                    MyApplication.set_session("distributor_list", "cart");
                    Intent intent = new Intent(ActivitySelection.this, ActivityDistributorList.class);
                    finish();
                    overridePendingTransition(R.anim.fade_in_call, R.anim.fade_out_call);
                    startActivity(intent);
                }
            }
        });


        img_clear_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.logi(LOG_TAG, "ON CLICK OF IMG_CLEAR_FILTER");
                MyApplication.set_session(MyApplication.SESSION_GROUP_ID, "");
                MyApplication.set_session(MyApplication.SESSION_SUB_GROUP_ID, "");
                MyApplication.set_session(MyApplication.SESSION_BRAND_ID, "");
                Toast.makeText(context, "Filter applied successfully..", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ActivitySelection.this, ActivitySelection.class);
                finish();
                overridePendingTransition(R.anim.fade_in_call, R.anim.fade_out_call);
                startActivity(intent);
            }
        });


        clearFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.logi(LOG_TAG, "ON CLICK OF IMG_CLEAR_FILTER");
                MyApplication.set_session(MyApplication.SESSION_GROUP_ID, "");
                MyApplication.set_session(MyApplication.SESSION_SUB_GROUP_ID, "");
                MyApplication.set_session(MyApplication.SESSION_BRAND_ID, "");
                Toast.makeText(context, "Filter applied successfully..", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ActivitySelection.this, ActivitySelection.class);
                finish();
                overridePendingTransition(R.anim.fade_in_call, R.anim.fade_out_call);
                startActivity(intent);
            }
        });


        img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyApplication.logi(LOG_TAG, "ON CLICK OF img_search");
                edt_search_distributor.setFocusable(true);
                edt_search_distributor.setPadding(10,0,0,0);
            /* int abc = tabHost.getCurrentTab();
                MyApplication.logi(LOG_TAG, "ON CLICK OF img_search tabshos===" + abc);
                if (edt_search_distributor.getVisibility() == View.VISIBLE) {
                    MyApplication.logi(LOG_TAG, "ON CLICK OF img_search  == visiblr");
                    edt_search_distributor.setVisibility(View.GONE);
                    edt_cancel_distributor.setVisibility(View.GONE);


                }else {
                    MyApplication.logi(LOG_TAG, "ON CLICK OF img_search  nt visiblr");
                    animBounce = AnimationUtils.loadAnimation(context,R.anim.fade_in_call);
                    edt_search_distributor.setAnimation(animBounce);
                    edt_cancel_distributor.setAnimation(animBounce);
                    edt_search_distributor.setVisibility(View.VISIBLE);
                    edt_cancel_distributor.setVisibility(View.VISIBLE);



                    if(abc==0){
                        MyApplication.logi(LOG_TAG,"IN ANC IS 1->");
                        searchGroupData();

                    }
                    else if(abc==1){
                        MyApplication.logi(LOG_TAG,"IN ANC IS 2-->");
                        searchSubGroupData();
                    }

                }
                //NOTE------------->SET VISISIBILITY OF ALL RECYCLER VIRE TO GONE  IN ALL FOUR ACTIVITIES--

*/


                MyApplication.logi(LOG_TAG, "ON CLICK OF img_search");

                int getCurrentTab = tabHost.getCurrentTab();
                MyApplication.logi(LOG_TAG, "ON CLICK OF img_search tabshos===" + getCurrentTab);
                if (getCurrentTab == 0) {
                    if (GroupActivity.isSearchEditIsVisible()) {
                        GroupActivity.hideSearchEdit();

                        MyApplication.logi(LOG_TAG, "EDT SEARCH DIST IS-->" + edt_search_distributor.getText().toString());
                    } else {
                        GroupActivity.showSearchEdit();
                        edt_search_distributor.setFocusable(true);

                    }
                } else if (getCurrentTab == 1) {
                    if (ActivitySubGroup.isSearchEditIsVisible()) {
                        ActivitySubGroup.hideSearchEdit();
                    } else {
                        ActivitySubGroup.showSearchEdit();
                        edt_search_distributor.setFocusable(true);

                    }
                } else if (getCurrentTab == 2) {
                    if (ActivityBrand.isSearchEditIsVisible()) {
                        ActivityBrand.hideSearchEdit();

                    } else {
                        ActivityBrand.showSearchEdit();
                        edt_search_distributor.setFocusable(true);

                    }
                } else if (getCurrentTab == 3) {
                    if (ActivityItem.isSearchEditIsVisible()) {
                        ActivityItem.hideSearchEdit();
                    } else {
                        ActivityItem.showSearchEdit();
                        edt_search_distributor.setFocusable(true);
                    }
                }
            }
        });

        edt_cancel_distributor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt_search_distributor.setVisibility(View.GONE);
                edt_cancel_distributor.setVisibility(View.GONE);
                edt_search_distributor.setText("");
                animBounce = AnimationUtils.loadAnimation(context, R.anim.fade_in_return);
                edt_search_distributor.setAnimation(animBounce);
                edt_cancel_distributor.setAnimation(animBounce);
                bindGroupData();
                bindSubGroupData();

                // MyApplication.set_session("data_group", "");

            }
        });
    }

    private void searchGroupData() {
        edt_search_distributor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String enteredName = charSequence.toString().trim();


                MyApplication.logi(LOG_TAG, "enteredName----->" + enteredName);
                if (!TextUtils.isEmpty(enteredName)) {
                    MyApplication.logi(LOG_TAG, "enteredName IS NEMPTY----->" + groupList1);

                    if (groupList1 != null && groupList1.size() > 0) {
                        MyApplication.logi(LOG_TAG, "GROUP LIST1 IS NOT NULL" + groupList1);
                        searchList = new ArrayList<>();
                        for (int j = 0; j < groupList1.size(); j++) {
                            MyApplication.logi(LOG_TAG, "IN FOR OF GROUP LIST1 IS NOT NULL" + groupList1);
                            if (groupList1.get(j).getDivision().toLowerCase().contains(enteredName.toLowerCase())) {
                                MyApplication.logi(LOG_TAG, "IN IF CONTAINS searchList IS---->" + searchList);
                                searchList.add(groupList1.get(j));
                                MyApplication.logi(LOG_TAG, "searchList IS---->" + searchList);
                            }
                        }
                    }
                    if (searchList != null) {
                        MyApplication.logi(LOG_TAG, "searchList IS  NIT NULLL---->" + searchList);
                        bindGroupSearchData(searchList);
                    } else {
                        MyApplication.logi(LOG_TAG, "searchList IS  NULLL---->" + searchList);
                        Toast.makeText(context, "No data found", Toast.LENGTH_LONG).show();
                    }
                } else {
                    bindGroupData();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void bindGroupSearchData(List<DivisionModel> searchList) {
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

    public void bindGroupData() {
       /* List<SelectionDataModel> groupList = new ArrayList<>();
        groupList = MyApplication.getSelectionList("group");*/

        String data = "";

       /* Bundle extras = getIntent().getExtras();
        if (extras != null) {
            data = extras.get("group").toString();
            MyApplication.logi(LOG_TAG, "DATAAAA->" + data);

        }*/


        data = MyApplication.get_session("data_group");
        MyApplication.logi(LOG_TAG, "value_data_group" + data);


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


    public void searchSubGroupData() {


        edt_search_distributor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String enteredName = charSequence.toString().trim();
                MyApplication.logi(LOG_TAG, "ITEMGROUP enteredName----->" + enteredName);
                if (!TextUtils.isEmpty(enteredName)) {
                    if (sub_group != null && sub_group.size() > 0) {
                        MyApplication.logi(LOG_TAG, "ITEMGROUP  if sub_group is not null----->" + sub_group);
                        searchList_group = new ArrayList<>();
                        for (int j = 0; j < sub_group.size(); j++) {
                            MyApplication.logi(LOG_TAG, "ITEMGROUP in for sub_group is not null----->" + sub_group);
                            if (sub_group.get(j).getDivision().toLowerCase().contains(enteredName.toLowerCase())) {
                                searchList_group.add(sub_group.get(j));
                            }
                        }
                    }
                    if (searchList_group != null) {
                        MyApplication.logi(LOG_TAG, "ITEMGROUP searchlist is not null----->" + searchList_group);
                        bindSubGroupSearchData(searchList_group);
                    } else {
                        Toast.makeText(context, "No data found", Toast.LENGTH_LONG).show();
                    }
                } else {
                    bindSubGroupData();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void bindSubGroupSearchData(List<SubGroupModel> searchList) {
        MyApplication.logi(LOG_TAG, "ITEMGROUP IN bindSubGroupSearchData");
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

    public void bindSubGroupData() {
        MyApplication.logi(LOG_TAG, "ITEMGROUP  IN BINDSUBGROIP DATA");
        // MyApplication.set_session(MyApplication.SESSION_DIVISION_ID,"");
        String data = "";
        Bundle extras = getIntent().getExtras();
        /*if (extras != null) {
            data = extras.get("sub_group").toString();
            MyApplication.logi(LOG_TAG, "DATAAAA->" + data);
            MyApplication.set_session(MyApplication.SESSION_DIVISION_ID,data);

        }*/
        data = MyApplication.get_session("data_sub_group");
        MyApplication.logi(LOG_TAG, "ITEMGROUP  IN BINDSUBGROIP DATA IS--" + data);
        // MyApplication.set_session(MyApplication.SESSION_DIVISION_ID,data);

        sub_group = TABLE_PITEM.getSubGroup(MyApplication.get_session(MyApplication.SESSION_DIVISION_ID), MyApplication.get_session(MyApplication.SESSION_GROUP_ID));
        MyApplication.logi(LOG_TAG, "ITEMGROUP itemGroup issssssss-->" + sub_group);
        MyApplication.logi(LOG_TAG, "ITEMGROUP bindData(), Size -->" + sub_group.size());

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

    public void initComponants() {

        //   bindGroupData();
        //bindSubGroupData();
        edt_search_distributor = findViewById(R.id.edt_search_distributor);

        edt_search_distributor.setBackground(context.getResources().getDrawable(R.drawable.border_background));
        edt_search_distributor.setVisibility(View.GONE);


        edt_cancel_distributor = findViewById(R.id.edt_cancel_distributor);
      /*  toolbar = (SearchAnimationToolbar) findViewById(R.id.toolbar);
        toolbar.setSupportActionBar(ActivitySelection.this);
        toolbar.setOnSearchQueryChangedListener(this);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.background_border);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
*/
        rel_edt = findViewById(R.id.rel_edt);
        txt_title_with_header = findViewById(R.id.txt_title_with_header);
        txt_title_with_header.setTextColor((Color.parseColor(MyApplication.get_session(MyApplication.SESSION_Text_Primary_Color))));


        txt_dist_name = findViewById(R.id.txt_dist_name);
        txt_dist_name.setTextColor(context.getResources().getColor(R.color.gray));
        txt_dist_name.setTextColor((Color.parseColor(MyApplication.get_session(MyApplication.SESSION_Text_Secondary_Color))));

        animBounce = AnimationUtils.loadAnimation(context, R.anim.move);


        txt_no_of_product_taken = findViewById(R.id.txt_no_of_product_taken);
        txt_no_of_product_taken.setStrokeColor(MyApplication.get_session(MyApplication.SESSION_THEME_DARK_COLOR));

        txt_no_of_product_taken.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_Theme_Primary_Text_Color)));

        int main_cart_count = TABLE_PDISTRIBUTOR.countOfAddToCardItems();
        txt_no_of_product_taken.setText(main_cart_count + "");
        MyApplication.logi(LOG_TAG, "COUNT OF CARTS MAIN IS-->" + main_cart_count);

        txt_dist_name.setText(MyApplication.get_session(MyApplication.SESSION_DISTRIBUTER_NAME1));
        MyApplication.logi(LOG_TAG, "DistributorNameRJ" + DistributorName);

        img_cart = findViewById(R.id.img_cart);
        img_search = findViewById(R.id.img_search);
        img_clear_filter = findViewById(R.id.img_clear_filters);
        rr2 = findViewById(R.id.rr2);


        clearFilter = findViewById(R.id.clearFilter);

        // clearFilter.setBackgroundColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR)));
        //clearFilter.setBackgroundTintList(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR)));
        clearFilter.setRippleColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_THEME_DARK_COLOR)));
        clearFilter.setBackgroundTintList((ColorStateList.valueOf((Color.parseColor(MyApplication.get_session(MyApplication.SESSION_THEME_DARK_COLOR))))));


        //   clearFilter.setColorFilter(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR)));

        rr2.setBackgroundColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_Accent_Color)));


    }

    //INITIALIZATION OF TABS
    public void initTabHost() {
        tabHost = (TabHost) findViewById(android.R.id.tabhost); // initiate TabHost
        TabHost.TabSpec spec;
        Intent intent;

        spec = tabHost.newTabSpec("0");
        String group = "", sub_group = "", brand = "", item = "";
        //how many tabs to set
        String itemFiltervalues = TABLE_SETTINGS.getItemFilterValues();//DIVISION,ITEMGROUP,BRAND,ITEM

        // String itemFiltervalues = "DIVISION,ITEMGROUP,ITEM,";
        MyApplication.logi(LOG_TAG, "itemFiltervalues-->" + itemFiltervalues);

        // try {
        String[] array = itemFiltervalues.split(",");

        MyApplication.logi(LOG_TAG, "array size" + array.length);


        if (array.length == 4) {
            MyApplication.logi(LOG_TAG, "required 4 tabs");

            for (int i = 0; i <= array.length; i++) {
                MyApplication.logi(LOG_TAG, "IN FOR");
                if (i == 0) {

                    group = array[0];
                    MyApplication.set_session("group_title", group);
                    MyApplication.logi(LOG_TAG, "GROUPPPPP-->" + group);
                }
                if (i == 1) {
                    sub_group = array[1];
                    MyApplication.set_session("sub_group_title", sub_group);
                    MyApplication.logi(LOG_TAG, "sub_group-->" + sub_group);
                }
                if (i == 2) {
                    brand = array[2];
                    MyApplication.set_session("brand_title", brand);
                    MyApplication.logi(LOG_TAG, "brand-->" + brand);
                }
                if (i == 3) {
                    item = array[3];
                    MyApplication.set_session("item_title", item);
                    MyApplication.logi(LOG_TAG, "item-->" + item);
                }
            }
            // Create a new TabSpec using tab host
            intent = new Intent(getApplicationContext(), GroupActivity.class);
            intent.putExtra("group", group);
            MyApplication.set_session("data_group", group);
            View tabIndicator = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
            ((TextView) tabIndicator.findViewById(R.id.title)).setText(group);
            ((ImageView) tabIndicator.findViewById(R.id.icon)).setImageResource((R.drawable.division));
            spec.setIndicator(tabIndicator);
            spec.setContent(intent);
            tabHost.addTab(spec);


            // Do the same for the other tabs
            spec = tabHost.newTabSpec("1");
            intent = new Intent(getApplicationContext(), ActivitySubGroup.class);
            intent.putExtra("sub_group", sub_group);
            MyApplication.set_session("data_sub_group", sub_group);
            tabIndicator = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
            ((TextView) tabIndicator.findViewById(R.id.title)).setText(sub_group);
            ((ImageView) tabIndicator.findViewById(R.id.icon)).setImageResource((R.drawable.sub_group));
            spec.setIndicator(tabIndicator);
            spec.setContent(intent);
            tabHost.addTab(spec);


            spec = tabHost.newTabSpec("2");
            intent = new Intent(getApplicationContext(), ActivityBrand.class);
            intent.putExtra("brand", brand);
            tabIndicator = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
            ((TextView) tabIndicator.findViewById(R.id.title)).setText(brand);
            ((ImageView) tabIndicator.findViewById(R.id.icon)).setImageResource((R.drawable.product));
            spec.setIndicator(tabIndicator);
            spec.setContent(intent);
            tabHost.addTab(spec);
            spec = tabHost.newTabSpec("3");
           /* intent = new Intent(getApplicationContext(), ActivityBrand.class);
            intent.putExtra("brand", item);*/


            intent = new Intent(getApplicationContext(), ActivityItem.class);
            MyApplication.set_session("direct_item", "yes");
            intent.putExtra("item", item);
            tabIndicator = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
            ((TextView) tabIndicator.findViewById(R.id.title)).setText(item);
            ((ImageView) tabIndicator.findViewById(R.id.icon)).setImageResource((R.drawable.item));
            spec.setIndicator(tabIndicator);
            spec.setContent(intent);
            tabHost.addTab(spec);


            //  MyApplication.set_session(MyApplication.SESSION_FILTER_DIVISION, division);
            //String itemGroup = array [1];
            //String brand1 = array[2];
            //String item1 = array[3];
            //MyApplication.logi(LOG_TAG, "SPILTED VALUES ARE->" + division + itemGroup + brand1 + item1);
        } else if (array.length == 3) {
            MyApplication.logi(LOG_TAG, "required 3 tabs");
            for (int i = 0; i <= array.length; i++) {
                MyApplication.logi(LOG_TAG, "IN FOR 3");
                if (i == 0) {
                    group = array[0];
                    MyApplication.set_session("group_title", group);
                    MyApplication.logi(LOG_TAG, "GROUPPPPP-->" + group);
                }
                if (i == 1) {
                    sub_group = array[1];
                    MyApplication.set_session("sub_group_title", sub_group);
                    MyApplication.logi(LOG_TAG, "sub_group-->" + sub_group);
                }
                if (i == 2) {
                    brand = array[2];
                    MyApplication.set_session("brand_title", brand);
                    MyApplication.logi(LOG_TAG, "brand-->" + brand);
                }

            }
            // Create a new TabSpec using tab host

            intent = new Intent(getApplicationContext(), GroupActivity.class);
            intent.putExtra("group", group);
            View tabIndicator = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
            ((TextView) tabIndicator.findViewById(R.id.title)).setText(group);
            ((ImageView) tabIndicator.findViewById(R.id.icon)).setImageResource((R.drawable.division));
            spec.setIndicator(tabIndicator);
            spec.setContent(intent);
            tabHost.addTab(spec);


            // Do the same for the other tabs
            spec = tabHost.newTabSpec("1");
            intent = new Intent(getApplicationContext(), ActivitySubGroup.class);
            intent.putExtra("sub_group", sub_group);

            tabIndicator = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
            ((TextView) tabIndicator.findViewById(R.id.title)).setText(sub_group);
            ((ImageView) tabIndicator.findViewById(R.id.icon)).setImageResource((R.drawable.sub_group));
            spec.setIndicator(tabIndicator);
            spec.setContent(intent);
            tabHost.addTab(spec);


            spec = tabHost.newTabSpec("2");
            spec.setIndicator(brand);
            if (brand.equalsIgnoreCase("ITEM")) {
                MyApplication.logi(LOG_TAG, "IN ITEMMMMMMMM");
                intent = new Intent(getApplicationContext(), ActivityItem.class);
                MyApplication.set_session("direct_item", "yes");
            } else {
                MyApplication.logi(LOG_TAG, "NOT IN  ITEMMMMMMMM");
                intent = new Intent(getApplicationContext(), ActivityBrand.class);
            }


            tabIndicator = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
            ((TextView) tabIndicator.findViewById(R.id.title)).setText(brand);
            ((ImageView) tabIndicator.findViewById(R.id.icon)).setImageResource((R.drawable.item));
            intent.putExtra("item", brand);
            spec.setIndicator(tabIndicator);
            spec.setContent(intent);
            tabHost.addTab(spec);


        } else if (array.length == 2) {

            MyApplication.logi(LOG_TAG, "required 2 tabs");

            for (int i = 0; i <= array.length; i++) {

                MyApplication.logi(LOG_TAG, "IN FOR");
                if (i == 0) {

                    group = array[0];
                    MyApplication.set_session("group_title", group);
                    MyApplication.logi(LOG_TAG, "GROUPPPPP-->" + group);
                }
                if (i == 1) {
                    sub_group = array[1];
                    MyApplication.set_session("sub_group_title", sub_group);
                    MyApplication.logi(LOG_TAG, "sub_group-->" + sub_group);
                }

            }


            spec.setIndicator(group);
            intent = new Intent(getApplicationContext(), ActivityBrand.class);
            View tabIndicator = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
            ((TextView) tabIndicator.findViewById(R.id.title)).setText(group);
            ((ImageView) tabIndicator.findViewById(R.id.icon)).setImageResource((R.drawable.division));
            spec.setIndicator(tabIndicator);
            intent.putExtra("brand", group);
            spec.setContent(intent);
            tabHost.addTab(spec);


            // Do the same for the other tabs
            spec = tabHost.newTabSpec("1");
            spec.setIndicator(sub_group);

            tabIndicator = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
            ((TextView) tabIndicator.findViewById(R.id.title)).setText(sub_group);
            ((ImageView) tabIndicator.findViewById(R.id.icon)).setImageResource((R.drawable.item));
            spec.setIndicator(tabIndicator);

            intent = new Intent(getApplicationContext(), ActivityItem.class);
            MyApplication.set_session("direct_item", "yes");
            intent.putExtra("item", sub_group);
            spec.setContent(intent);
            tabHost.addTab(spec);


        }
        String division = array[0];
        MyApplication.set_session(MyApplication.SESSION_FILTER_DIVISION, division);
       /* } catch (Exception e) {
            MyApplication.logi(LOG_TAG, "EXCEPTION in spilted data-->" + e.getMessage());
        }*/


        tabHost.getTabWidget().setDividerDrawable(null);
        tabHost.setPadding(0, 0, 0, 0);
      tabHost.setCurrentTab(0);




        View tabIndicator = LayoutInflater.from(context).inflate(R.layout.tab_indicator, getTabWidget(), false);
        ImageView icon = (ImageView) tabHost.getTabWidget().getChildAt(0).findViewById(R.id.icon);
        //   icon.setBackground(getResources().getDrawable(R.drawable.circle_background_color));
        icon.setBackgroundColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_THEME_DARK_COLOR)));
        TextView tv = (TextView) tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).findViewById(R.id.title);
        tv.setTextColor(context.getResources().getColor(R.color.black));

    }


    @Override
    public void onBackPressed() {

        Intent intent = new Intent(context, ActivityDistributorList.class);
        finish();
        overridePendingTransition(R.anim.fade_in_call, R.anim.fade_out_call);
        context.startActivity(intent);
    }


}