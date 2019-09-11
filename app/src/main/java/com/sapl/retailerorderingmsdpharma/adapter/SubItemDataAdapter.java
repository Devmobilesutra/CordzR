package com.sapl.retailerorderingmsdpharma.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.sapl.retailerorderingmsdpharma.MyDatabase.TABLE_ORDER_DETAILS;
import com.sapl.retailerorderingmsdpharma.MyDatabase.TABLE_PDISTRIBUTOR;
import com.sapl.retailerorderingmsdpharma.MyDatabase.TABLE_PRICELIST_DETAILS;
import com.sapl.retailerorderingmsdpharma.MyDatabase.TABLE_RETAILER_IMAGES;
import com.sapl.retailerorderingmsdpharma.MyDatabase.TABLE_SETTINGS;
import com.sapl.retailerorderingmsdpharma.MyDatabase.TABLE_TEMP_ORDER_DETAILS;
import com.sapl.retailerorderingmsdpharma.MyDatabase.TABLE_TEMP_RETAILER_ORDER_MASTER;
import com.sapl.retailerorderingmsdpharma.R;
import com.sapl.retailerorderingmsdpharma.activities.ActivityItem;
import com.sapl.retailerorderingmsdpharma.activities.ActivitySelection;
import com.sapl.retailerorderingmsdpharma.activities.MyApplication;
import com.sapl.retailerorderingmsdpharma.confiq.CircleTransform;
import com.sapl.retailerorderingmsdpharma.customView.CustomButtonBold;
import com.sapl.retailerorderingmsdpharma.customView.CustomButtonRegular;
import com.sapl.retailerorderingmsdpharma.customView.CustomEditTextMedium;
import com.sapl.retailerorderingmsdpharma.customView.CustomTextViewMedium;
import com.sapl.retailerorderingmsdpharma.models.OrderReviewModel;
import com.sapl.retailerorderingmsdpharma.models.SubItemDataModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;


/**
 * Created by MRUNAL on 07-Feb-18.
 */

public class SubItemDataAdapter extends RecyclerView.Adapter<SubItemDataAdapter.MyViewHolder> {
    private List<SubItemDataModel> subItemList;
    private List<OrderReviewModel> subItemList1;
    Context context;
    public static String LOG_TAG = "SubItemDataAdapter";
    String single_case_price, sinle_bottle_price;
    String label_rps_price_value;
    float total_lbl;
    float item_total;
    ArrayList<SubItemDataModel> res = new ArrayList<>();
    String  ORDBOOKUOMLABEL = "",biguom = "",smalluom="";
boolean istrue;


    public SubItemDataAdapter(Context context, List<SubItemDataModel> subItemList, float item_total) {
        this.subItemList = subItemList;
        MyApplication.logi(LOG_TAG, "SUB_ITEM--->" + subItemList.toString());
        this.context = context;
        this.item_total = item_total;


        res = TABLE_TEMP_ORDER_DETAILS.getListOfAlreadySavedToCartItems(MyApplication.get_session(MyApplication.SESSION_ORDER_ID));

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public CustomTextViewMedium txt_offer, txt_offer_lable, txt_bottle_label, txt_bottle_price, txt_case_label, txt_case_price,
                txt_sub_item_name, txt_offer_tagline, txt_total_price, txt_total_price_old, txt_btl_label_discount, txt_case_dicounted_price, txt_btl_dicounted_price;

        public CustomTextViewMedium txt_label_edt_case, txt_label_edt_bottle, txt_label_free_case, txt_label_free_bottle,
                txt_Total, txt_total_rps_without_disc_with_dash, txt_save, txt_save_rps_value;
        public CustomEditTextMedium edt_no_of_case, edt_no_of_bottle, txt_no_of_free_case, txt_no_of_free_bottle;
        Button btn_save_to_cart;
        public ImageView img_product, img_rupee;
        public LinearLayout layout_border;
        int toDelete = 3;

        public String itemID;


        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        public MyViewHolder(View view) {
            super(view);

            getUomLabels();
            txt_offer = view.findViewById(R.id.txt_offer);
            txt_offer.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_Accent_Color)));
            // txt_offer.setBackgroundColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_THEME_DARK_COLOR)));
            txt_offer.setBackgroundTintList((ColorStateList.valueOf((Color.parseColor(MyApplication.get_session(MyApplication.SESSION_Highlight_Color))))));


            txt_bottle_price = view.findViewById(R.id.txt_bottle_price);
            txt_bottle_price.setPaintFlags(txt_bottle_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            txt_bottle_price.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_Theme_Primary_Text_Color)));

            txt_case_price = view.findViewById(R.id.txt_case_price);
            txt_case_price.setPaintFlags(txt_case_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            txt_case_price.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_Theme_Primary_Text_Color)));


            txt_sub_item_name = view.findViewById(R.id.txt_sub_item_name);
            txt_sub_item_name.setTextColor(context.getResources().getColor(R.color.black));

            txt_offer_tagline = view.findViewById(R.id.txt_offer_tagline);
            txt_offer_tagline.setTextColor(context.getResources().getColor(R.color.gray));

            txt_total_price = view.findViewById(R.id.txt_total_price);
            txt_total_price.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_Theme_Primary_Text_Color)));

            txt_total_price.setVisibility(View.VISIBLE);


            txt_total_price_old = view.findViewById(R.id.txt_total_price_old);
            txt_total_price_old.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_Theme_Primary_Text_Color)));

            txt_total_price_old.setVisibility(View.VISIBLE);

           /* img_rupee = view.findViewById(R.id.img_rupee);
            img_rupee.setVisibility(View.VISIBLE);
*/
            txt_no_of_free_case = view.findViewById(R.id.txt_no_of_free_case);
            txt_no_of_free_case.setBackground(context.getResources().getDrawable(R.drawable.border_background));

            txt_no_of_free_bottle = view.findViewById(R.id.txt_no_of_free_bottle);
            txt_no_of_free_bottle.setBackground(context.getResources().getDrawable(R.drawable.border_background));
            img_product = (ImageView) view.findViewById(R.id.img_product);
            img_product.setColorFilter(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_THEME_DARK_COLOR)));

            btn_save_to_cart = view.findViewById(R.id.btn_save_to_cart);
            //btn_save_to_cart.setBackgroundColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR)));
            btn_save_to_cart.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_Theme_Primary_Text_Color)));
            btn_save_to_cart.setBackgroundColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_THEME_DARK_COLOR)));

            edt_no_of_case = view.findViewById(R.id.edt_no_of_case);
            edt_no_of_case.setBackground(context.getResources().getDrawable(R.drawable.border_background));

            edt_no_of_bottle = view.findViewById(R.id.edt_no_of_bottle);
            edt_no_of_bottle.setBackground(context.getResources().getDrawable(R.drawable.border_background));




            if (biguom.equalsIgnoreCase("")) {
                MyApplication.logi(LOG_TAG, "Big uom is empty");
                edt_no_of_case.setBackgroundColor(context.getResources().getColor(R.color.grey_300));
                edt_no_of_case.setClickable(false);
                edt_no_of_case.setEnabled(false);
            } else if (smalluom.equalsIgnoreCase("")) {
                MyApplication.logi(LOG_TAG, "small uom is empty");
             //   edt_no_of_bottle.setBackgroundColor(context.getResources().getColor(R.color.grey_300));
               /* edt_no_of_bottle.setClickable(false);
                edt_no_of_bottle.setEnabled(false);*/
            } else {
                MyApplication.logi(LOG_TAG, "both are there not is empty");
            }


            // COLORS TO FIXED LABELS
            txt_offer_lable = view.findViewById(R.id.txt_offer_lable);
            txt_offer_lable.setTextColor(context.getResources().getColor(R.color.black));
           /* txt_case_label = view.findViewById(R.id.txt_case_label);
            txt_case_label.setTextColor(context.getResources().getColor(R.color.heading_background));
            txt_bottle_label = view.findViewById(R.id.txt_bottle_label);
            txt_bottle_label.setTextColor(context.getResources().getColor(R.color.heading_background));*/
            txt_label_edt_case = view.findViewById(R.id.txt_label_edt_case);
            txt_label_edt_case.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_Theme_Primary_Text_Color)));

            txt_label_edt_bottle = view.findViewById(R.id.txt_label_edt_bottle);
            txt_label_edt_bottle.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_Theme_Primary_Text_Color)));

            txt_label_free_case = view.findViewById(R.id.txt_label_free_case);
            txt_label_free_case.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_Theme_Primary_Text_Color)));

            txt_label_free_bottle = view.findViewById(R.id.txt_label_free_bottle);
            txt_label_free_bottle.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_Theme_Primary_Text_Color)));

            layout_border = view.findViewById(R.id.layout_border);
/*
            txt_case_label_discount = view.findViewById(R.id.txt_case_label_discount);
            txt_case_label_discount.setTextColor(context.getResources().getColor(R.color.heading_background));

            txt_btl_label_discount = view.findViewById(R.id.txt_btl_label_discount);
            txt_btl_label_discount.setTextColor(context.getResources().getColor(R.color.heading_background));*/

            txt_case_dicounted_price = view.findViewById(R.id.txt_case_dicounted_price);
            txt_case_dicounted_price.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_Theme_Primary_Text_Color)));


            txt_btl_dicounted_price = view.findViewById(R.id.txt_btl_dicounted_price);
            txt_btl_dicounted_price.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_Theme_Primary_Text_Color)));


            txt_Total = view.findViewById(R.id.txt_Total);
            txt_Total.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_Text_Primary_Color)));

            txt_total_rps_without_disc_with_dash = view.findViewById(R.id.txt_total_rps_without_disc_with_dash);
            txt_total_rps_without_disc_with_dash.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_Text_Primary_Color)));

            txt_total_rps_without_disc_with_dash.setPaintFlags(txt_total_rps_without_disc_with_dash.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            txt_save = view.findViewById(R.id.txt_save);
            txt_save.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_Text_Primary_Color)));

            txt_save_rps_value = view.findViewById(R.id.txt_save_rps_value);
            txt_save_rps_value.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_Text_Primary_Color)));


            //mrunal to set the gradient color in java file instead of using drawable in xml file .
            int[] colors = {Color.parseColor(MyApplication.get_session(MyApplication.SESSION_THEME_DARK_COLOR)),
                    Color.parseColor(MyApplication.get_session(MyApplication.SESSION_THEME_DARK_COLOR))};

            //create a new gradient color
            GradientDrawable gd = new GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM, colors);

            gd.setCornerRadius(0f);
            gd.setStroke(2, Color.parseColor(MyApplication.get_session(MyApplication.SESSION_THEME_DARK_COLOR)));
            //apply the button background to newly created drawable gradient
            btn_save_to_cart.setBackground(gd);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // get position
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {


                    }
                }
            });
        }
    }

    private void getUomLabels() {


        ORDBOOKUOMLABEL = TABLE_SETTINGS.get_value_from_setting("ORDBOOKUOMLABEL"); //"CS/BTL"
        MyApplication.logi(LOG_TAG,"ORDBOOKUOMLABEL----->"+ORDBOOKUOMLABEL);

        String ORDBOOKUOMLABEL_3 = "CS/";
        String ORDBOOKUOMLABEL_2 = "/BTL";
        String ORDBOOKUOMLABEL_1 = "CS/BTL";

        try {
            MyApplication.logi(LOG_TAG, "ORDBOOKUOMLABEL---->" + ORDBOOKUOMLABEL);
            String[] split = ORDBOOKUOMLABEL_3.split(Pattern.quote("/"));

            if (split.length == 1) {
                MyApplication.logi(LOG_TAG, "spilt length is 1");
                biguom = split[0];
            } else {
                biguom = split[0];
                MyApplication.logi(LOG_TAG, "ORDBOOKUOMLABEL biguom---->" + biguom);
                smalluom = split[1];
                MyApplication.logi(LOG_TAG, "ORDBOOKUOMLABEL smalluom---->" + smalluom);

            }
        } catch (Exception e) {
            MyApplication.logi(LOG_TAG, "ORDBOOKUOMLABEL exception---->" + e.getMessage());
        }

    }

    @Override
    public SubItemDataAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_sub_item_data, parent, false);
        return new SubItemDataAdapter.MyViewHolder(itemView);
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(final SubItemDataAdapter.MyViewHolder holder, final int position) {
        int itemID_from_db;
        String itemId_from_position = "";

        final SubItemDataModel model = subItemList.get(position);

        MyApplication.logi(LOG_TAG, "res-->" + res);
        int size = res.size();


        MyApplication.logi(LOG_TAG, "modelmodelmodel->" + model);
        holder.img_product.clearAnimation();
        holder.img_product.clearFocus();


        double discounted_price_of_btl = 0.0;
        double discounted_price_of_case = 0.0;


        if (model.getProduct_image_path() == null) {

            Glide.with(context)
                    .load(R.mipmap.msd_logo_1)
                    .asBitmap()
                    .error(R.mipmap.msd_logo_1)
                    .placeholder(R.mipmap.msd_logo_1)
                    .into(holder.img_product);

        } else {
            holder.img_product.clearColorFilter();
            byte[] decodeString = Base64.decode(model.getProduct_image_path(), Base64.DEFAULT);
            Bitmap decodebitmap = BitmapFactory.decodeByteArray(decodeString, 0, decodeString.length);
            Glide.with(context)
                    .load(decodeString)
                    //  .transform(new CircleTransform(context))
                    //.asBitmap()
                    .error(R.mipmap.msd_logo_1)
                    .placeholder(R.mipmap.msd_logo_1)
                    .into(holder.img_product)

            ;


        }
        if (model.getDiscount_type().equalsIgnoreCase("Rs")) {

            holder.txt_offer.setText(model.getOffer() + "Rs");

        } else if (model.getDiscount_type().equalsIgnoreCase("%")) {
            holder.txt_offer.setText(model.getOffer() + "%");
        }

        //  MyApplication.set_session("value_after_disc",model.getBottle_price()+"");



        holder.txt_bottle_price.setText("₹ " + String.valueOf(model.getBottle_price()));
        holder.txt_case_price.setText("₹ " + String.valueOf(model.getCase_value()));
        MyApplication.logi(LOG_TAG, "model.getItem_id()=1" + model.getItem_id());
       // MyApplication.logi(LOG_TAG, "model.getSalesRate=1" +TABLE_PRICELIST_DETAILS.getSalesRate(String.valueOf(model.getItem_id())));
     /*   float actual_case_value=  TABLE_PRICELIST_DETAILS.getSalesRate(String.valueOf(model.getItem_id()));
        float actual_price_value= TABLE_PRICELIST_DETAILS.getSalesRate(String.valueOf(model.getItem_id()));*/
        float actual_case_value = model.getCase_value();
        float actual_price_value = model.getBottle_price();
        MyApplication.logi(LOG_TAG, "ACTUAL CASE---->" + actual_case_value);
        MyApplication.logi(LOG_TAG, "ACTUAL BTL---->" + actual_price_value);
        //TO SEND THE SINGLE BTL AND CASE VALUE ON NEXT PAGE  FOR EDITING OPTION
        if (model.getDiscount_type().equalsIgnoreCase("Rs")) {

            double discount = Double.parseDouble(model.getOffer());
            discounted_price_of_case = model.getCase_value();
            double val_of_case_after_discount = (discounted_price_of_case * (discount / 100));
            //  val_1 -= val_of_case_after_discount;
            discounted_price_of_case -= discount;

            MyApplication.set_session("discounted_price_of_case", discounted_price_of_case + "");
            MyApplication.logi(LOG_TAG, "val_of_case_after_discount if rps -->" + discounted_price_of_case);
            // holder.txt_case_label_discount.setText("Case Disc.");
            holder.txt_case_dicounted_price.setText("₹ " + String.format("%.2f", discounted_price_of_case));


            discounted_price_of_btl = model.getBottle_price();
            double val_of_botl_after_discount = (discounted_price_of_btl * (discount / 100));
            // val_2 -= val_of_botl_after_discount;
            discounted_price_of_btl -= discount;

            MyApplication.set_session("discounted_price_of_btl", discounted_price_of_btl + "");

            // holder.txt_btl_label_discount.setText("Btl Disc.");

            holder.txt_btl_dicounted_price.setText("₹ " + String.format("%.2f", discounted_price_of_btl));
            MyApplication.logi(LOG_TAG, "val_of_botl_after_discount if rps-->" + discounted_price_of_btl);


        } else if (model.getDiscount_type().equalsIgnoreCase("%")) {
            double discount = Double.parseDouble(model.getOffer());
            discounted_price_of_case = model.getCase_value();
            double val_of_case_after_discount = (discounted_price_of_case * (discount / 100));
            discounted_price_of_case -= val_of_case_after_discount;
            MyApplication.set_session("discounted_price_of_case", discounted_price_of_case + "");
            MyApplication.logi(LOG_TAG, "val_of_case_after_discount-->" + discounted_price_of_case);
            // holder.txt_case_label_discount.setText("Case Disc.");
            holder.txt_case_dicounted_price.setText("₹ " + String.format("%.2f", discounted_price_of_case));


            discounted_price_of_btl = model.getBottle_price();
            double val_of_botl_after_discount = (discounted_price_of_btl * (discount / 100));
            discounted_price_of_btl -= val_of_botl_after_discount;

            MyApplication.set_session("discounted_price_of_btl", discounted_price_of_btl + "");

            // holder.txt_btl_label_discount.setText("Btl Disc.");
            holder.txt_btl_dicounted_price.setText("₹ " + String.format("%.2f", discounted_price_of_btl));
            MyApplication.logi(LOG_TAG, "val_of_botl_after_discount-->" + discounted_price_of_btl);
        }


        single_case_price = String.valueOf(holder.txt_case_price.getText());
        MyApplication.logi(LOG_TAG, " In single_case_price-->" + single_case_price);

        // single_case_vale = Float.parseFloat(single_case_price);

        sinle_bottle_price = String.valueOf(holder.txt_bottle_price.getText());
        MyApplication.logi(LOG_TAG, " In sinle_bottle_price-->" + sinle_bottle_price);
        //single_btl_val = Float.parseFloat(sinle_bottle_price);

        holder.txt_sub_item_name.setText(model.getSub_item_name());
        holder.txt_offer_tagline.setText(model.getOffer_tagline());

        holder.txt_total_price.setText("₹ 00");
        holder.txt_total_price_old.setText("₹ 00");
        holder.txt_total_rps_without_disc_with_dash.setText("₹ 00 ");

        holder.txt_save_rps_value.setText("₹ 00");
        //  holder.txt_case_label.setText(MyApplication.get_session(MyApplication.SESSION_UOM_VALUE_FIRST));
        //  holder.txt_bottle_label.setText(MyApplication.get_session(MyApplication.SESSION_UOM_VALUE_SECOND));
        holder.txt_label_edt_case.setText(MyApplication.get_session(MyApplication.SESSION_UOM_VALUE_FIRST));
        holder.txt_label_edt_bottle.setText(MyApplication.get_session(MyApplication.SESSION_UOM_VALUE_SECOND));
        holder.txt_label_free_case.setText("Free " + MyApplication.get_session(MyApplication.SESSION_UOM_VALUE_FIRST));
        holder.txt_label_free_bottle.setText("Free " + MyApplication.get_session(MyApplication.SESSION_UOM_VALUE_SECOND));


        holder.edt_no_of_case.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MyApplication.logi(LOG_TAG, "onTextChanged " + s);
                try {


                    int no_1 = 0, no_2 = 0;
                    if (!TextUtils.isEmpty(s.toString())) {
                        no_1 = Integer.parseInt(s.toString());
                    }

                    if (!TextUtils.isEmpty(holder.edt_no_of_bottle.getText().toString().trim())) {
                        no_2 = Integer.parseInt(holder.edt_no_of_bottle.getText().toString().trim());
                    }

                    ////////////////IF RS
                    if (model.getDiscount_type().equalsIgnoreCase("Rs")) {


                        double discount = Double.parseDouble(model.getOffer());
                        double val_1 = model.getCase_value();
                        double val_of_case_after_discount = (val_1 * (discount / 100));

                        val_1 -= discount;
                        MyApplication.logi(LOG_TAG, "val_of_case_after_discount if rps -->" + val_1);

                        double val_2 = model.getBottle_price();
                        double val_of_botl_after_discount = (val_2 * (discount / 100));

                        val_2 -= discount;
                        MyApplication.logi(LOG_TAG, "val_of_botl_after_discount if rps-->" + val_2);


                        double value = ((no_1 * val_1) + (no_2 * val_2));


                        float actual_case_value = model.getCase_value();
                        float actual_btl_value = model.getBottle_price();
                        double final_total_without_discount = ((no_1 * actual_case_value) + (no_2 * actual_btl_value));
                        MyApplication.logi(LOG_TAG, "final_total_without_discount--->" + final_total_without_discount);
                        holder.txt_total_rps_without_disc_with_dash.setText("₹ " + String.format("%.2f", final_total_without_discount) + " ");


                        holder.txt_total_price.setText("₹ " + String.format("%.2f", value));
                        holder.txt_total_price_old.setText("₹ " + String.format("%.2f", value));
                        double txt_save_rps_value = final_total_without_discount - value;
                        holder.txt_save_rps_value.setText("₹ " + String.format("%.2f", txt_save_rps_value));


                        /////////IF %
                    } else if (model.getDiscount_type().equalsIgnoreCase("%")) {


                        double discount = Double.parseDouble(model.getOffer());
                        double val_1 = model.getCase_value();
                        double val_of_case_after_discount = (val_1 * (discount / 100));
                        val_1 -= val_of_case_after_discount;
                        MyApplication.logi(LOG_TAG, "val_of_case_after_discount-->" + val_1);

                        double val_2 = model.getBottle_price();
                        double val_of_botl_after_discount = (val_2 * (discount / 100));
                        val_2 -= val_of_botl_after_discount;
                        MyApplication.logi(LOG_TAG, "val_of_botl_after_discount-->" + val_2);


                        double value = ((no_1 * val_1) + (no_2 * val_2));


                        float actual_case_value_new = model.getCase_value();
                        float actual_btl_value_new = model.getBottle_price();


                        double final_total_without_discount = ((no_1 * actual_case_value_new) + (no_2 * actual_btl_value_new));

                        MyApplication.logi(LOG_TAG, "final_total_without_discount--->" + final_total_without_discount);
                        holder.txt_total_rps_without_disc_with_dash.setText("₹ " + String.format("%.2f", final_total_without_discount) + " ");


                        holder.txt_total_price.setText("₹ " + String.format("%.2f", value));
                        holder.txt_total_price_old.setText("₹ " + String.format("%.2f", value));
                        double txt_save_rps_value = final_total_without_discount - value;
                        holder.txt_save_rps_value.setText("₹ " + String.format("%.2f", txt_save_rps_value));

                    }

                } catch (Exception e) {
                    MyApplication.logi(LOG_TAG, "EXCEPTION EE2->" + e.getMessage());
                }

            }


            @Override
            public void afterTextChanged(Editable s) {
                MyApplication.logi(LOG_TAG, "afterTextChanged ");
            }


        });

        holder.edt_no_of_bottle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                MyApplication.logi(LOG_TAG, "TbeforeTextChanged ");

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MyApplication.logi(LOG_TAG, "onTextChanged " + s);
                try {

                    int no_1 = 0, no_2 = 0;
                    if (!TextUtils.isEmpty(s.toString())) {
                        no_2 = Integer.parseInt(s.toString());
                    }

                    if (!TextUtils.isEmpty(holder.edt_no_of_case.getText().toString().trim())) {
                        no_1 = Integer.parseInt(holder.edt_no_of_case.getText().toString().trim());
                    }


                    ////////////////IF RS
                    if (model.getDiscount_type().equalsIgnoreCase("Rs")) {


                        double discount = Double.parseDouble(model.getOffer());
                        double val_1 = model.getCase_value();
                        double val_of_case_after_discount = (val_1 * (discount / 100));
                        //  val_1 -= val_of_case_after_discount;
                        val_1 -= discount;
                        MyApplication.logi(LOG_TAG, "val_of_case_after_discount if rps -->" + val_1);

                        double val_2 = model.getBottle_price();
                        double val_of_botl_after_discount = (val_2 * (discount / 100));
                        // val_2 -= val_of_botl_after_discount;
                        val_2 -= discount;
                        MyApplication.logi(LOG_TAG, "val_of_botl_after_discount if rps-->" + val_2);


                        double value = ((no_1 * val_1) + (no_2 * val_2));
                        //double value = ((no_1 * val_1) + (no_2 * val_2));
                        holder.txt_total_price.setText("₹ " + String.format("%.2f", value));
                        holder.txt_total_price_old.setText("₹ " + String.format("%.2f", value));


                        float actual_case_value = model.getCase_value();
                        float actual_btl_value = model.getBottle_price();
                        double final_total_without_discount = ((no_1 * actual_case_value) + (no_2 * actual_btl_value));
                        MyApplication.logi(LOG_TAG, "final_total_without_discount--->" + final_total_without_discount);
                        holder.txt_total_rps_without_disc_with_dash.setText("₹ " + String.format("%.2f", final_total_without_discount) + " ");

                        double txt_save_rps_value = final_total_without_discount - value;
                        holder.txt_save_rps_value.setText("₹ " + String.format("%.2f", txt_save_rps_value));

/////////IF %
                    } else if (model.getDiscount_type().equalsIgnoreCase("%")) {


                        double discount = Double.parseDouble(model.getOffer());
                        double val_1 = model.getCase_value();
                        double val_of_case_after_discount = (val_1 * (discount / 100));
                        val_1 -= val_of_case_after_discount;
                        MyApplication.logi(LOG_TAG, "val_of_case_after_discount-->" + val_1);

                        double val_2 = model.getBottle_price();
                        double val_of_botl_after_discount = (val_2 * (discount / 100));
                        val_2 -= val_of_botl_after_discount;
                        MyApplication.logi(LOG_TAG, "val_of_botl_after_discount-->" + val_2);


                        double value = ((no_1 * val_1) + (no_2 * val_2));
                        holder.txt_total_price.setText("₹ " + String.format("%.2f", value));
                        holder.txt_total_price_old.setText("₹ " + String.format("%.2f", value));


                        float actual_case_value_new = model.getCase_value();
                        float actual_btl_value_new = model.getBottle_price();


                        double final_total_without_discount = ((no_1 * actual_case_value_new) + (no_2 * actual_btl_value_new));

                        MyApplication.logi(LOG_TAG, "final_total_without_discount--->" + final_total_without_discount);
                        holder.txt_total_rps_without_disc_with_dash.setText("₹ " + String.format("%.2f", final_total_without_discount) + " ");


                        double txt_save_rps_value = final_total_without_discount - value;
                        holder.txt_save_rps_value.setText("₹ " + String.format("%.2f", txt_save_rps_value));

                    }

                } catch (Exception e) {
                    MyApplication.logi(LOG_TAG, "EXCEPTION EE2->" + e.getMessage());
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        holder.btn_save_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                istrue=TABLE_TEMP_ORDER_DETAILS.istrue(MyApplication.get_session(MyApplication.SESSION_ORDER_ID));

                MyApplication.logi(LOG_TAG, "IN istrue="+istrue);
/*
                int btl = Integer.parseInt(holder.edt_no_of_bottle.getText().toString().trim());
                int cases = Integer.parseInt(holder.edt_no_of_case.getText().toString().trim());
                int freeCse = Integer.parseInt(holder.txt_no_of_free_case.getText().toString().trim());
                int free_btl = Integer.parseInt(holder.txt_no_of_free_bottle.getText().toString().trim());*/

                holder.edt_no_of_bottle.setEnabled(false);
                holder.edt_no_of_case.setEnabled(false);
                holder.txt_no_of_free_case.setEnabled(false);
                holder.txt_no_of_free_bottle.setEnabled(false);
                holder.btn_save_to_cart.setEnabled(false);


                if (holder.edt_no_of_case.getText().toString().equals("") && holder.edt_no_of_bottle.getText().toString().equals("")) {
                    MyApplication.logi(LOG_TAG, "IN IFFFFFFFFFFFFFFFFFFF");
                    Toast.makeText(context, "Please enter quantity", Toast.LENGTH_SHORT).show();
                    holder.edt_no_of_bottle.setEnabled(true);
                    holder.edt_no_of_case.setEnabled(true);
                    holder.txt_no_of_free_case.setEnabled(true);
                    holder.txt_no_of_free_bottle.setEnabled(true);
                    holder.btn_save_to_cart.setEnabled(true);
                } else {
                    MyApplication.logi(LOG_TAG, " In btn_save_to_cart-->" + model.getItem_id());


                    label_rps_price_value = String.valueOf(holder.txt_total_price.getText());
                    MyApplication.logi(LOG_TAG, "label_rps_price_value--->" + label_rps_price_value);

                    String rps_price_val = spilt(label_rps_price_value);
                    total_lbl = Float.parseFloat(rps_price_val);
                    MyApplication.logi(LOG_TAG, "total_lbl --->" + total_lbl);

                    String str_date = MyApplication.getCurrentDate();

                    String no_of_case = holder.edt_no_of_case.getText().toString().trim();
                    String no_of_btls = holder.edt_no_of_bottle.getText().toString().trim();

                    if (no_of_case.equalsIgnoreCase("") || no_of_case == null) {

                        no_of_case = "0";
                    }
                    if (no_of_btls.equalsIgnoreCase("") || no_of_btls == null) {
                        MyApplication.logi(LOG_TAG, "in emptuy ");
                        no_of_btls = "0";
                    }


                    model.setCases(no_of_case);
                    model.setBottles(no_of_btls);

                    String no_of_fress_cases_val = holder.txt_no_of_free_case.getText().toString().trim();
                    String no_of_freee_btls_val = holder.txt_no_of_free_bottle.getText().toString().trim();

                    String itemId = String.valueOf(model.getItem_id());


                    //  MyApplication.set_session("CASESE", no_of_case);


                    //UPDATE TEMPORDERMASTER
                    //   TABLE_TEMP_RETAILER_ORDER_MASTER.updateAmt(MyApplication.get_session(MyApplication.SESSION_ORDER_ID),total_lbl);


                    //check if TABLE_TEMP_ORDER_DETAILS already has the itemid and orderId
                    int ret_count = TABLE_TEMP_ORDER_DETAILS.check_Item_is_Already_Present(MyApplication.get_session(MyApplication.SESSION_ORDER_ID), itemId);
                    MyApplication.logi(LOG_TAG, "ret count-->" + ret_count);


                    if (ret_count == 1) {
                        //update flag
                        int ret1 = TABLE_TEMP_ORDER_DETAILS.insertOrderDetails(
                                MyApplication.get_session(MyApplication.SESSION_ORDER_ID), itemId, model.getSub_item_name(),
                                no_of_case, no_of_btls, MyApplication.get_session("discounted_price_of_btl"), MyApplication.get_session("discounted_price_of_case"), total_lbl, no_of_freee_btls_val, no_of_fress_cases_val, "update_data");


                        //80cbc4 FFFFFF


                        int[] colors = {Color.parseColor(MyApplication.get_session(MyApplication.SESSION_Theme_Secondary_Text_Color)),
                                Color.parseColor(MyApplication.get_session(MyApplication.SESSION_Accent_Color))};

                        //create a new gradient color
                        GradientDrawable gd = new GradientDrawable(
                                GradientDrawable.Orientation.TOP_BOTTOM, colors);

                        gd.setCornerRadius(0f);
                        // gd.setStroke(2, Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR)));
                        //apply the button background to newly created drawable gradient
                        holder.layout_border.setBackground(gd);


                        //  holder.layout_border.setBackground(context.getResources().getDrawable(R.drawable.added_to_cart_background));


                    } else {
                        int ret1 = TABLE_TEMP_ORDER_DETAILS.insertOrderDetails(
                                MyApplication.get_session(MyApplication.SESSION_ORDER_ID), itemId, model.getSub_item_name(),
                                no_of_case, no_of_btls, MyApplication.get_session("discounted_price_of_btl"), MyApplication.get_session("discounted_price_of_case"), total_lbl, no_of_freee_btls_val, no_of_fress_cases_val, "insert_data");

                        if (ret1 == 0) {
                            MyApplication.logi(LOG_TAG, "TABLE_TEMP_ORDER_DETAILS successfully inserted " + ret1);
                            //  Toast.makeText(context, "TABLE_TEMP_ORDER_DETAILS Saved successfully in cart", Toast.LENGTH_SHORT).show();


                            int[] colors = {Color.parseColor(MyApplication.get_session(MyApplication.SESSION_Theme_Secondary_Text_Color)),
                                    Color.parseColor(MyApplication.get_session(MyApplication.SESSION_Accent_Color))};

                            //create a new gradient color
                            GradientDrawable gd = new GradientDrawable(
                                    GradientDrawable.Orientation.TOP_BOTTOM, colors);

                            gd.setCornerRadius(0f);
                            // gd.setStroke(2, Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR)));
                            //apply the button background to newly created drawable gradient
                            holder.layout_border.setBackground(gd);

                            //holder.layout_border.setBackground(context.getResources().getDrawable(R.drawable.added_to_cart_background));

                            int main_cart_count = TABLE_PDISTRIBUTOR.countOfAddToCardItems();
                            ActivitySelection.txt_no_of_product_taken.setText(main_cart_count + "");


                            MyApplication.logi(LOG_TAG, "COUNT OF CARTS MAIN IS-->" + main_cart_count);
                        } else {
                            MyApplication.logi(LOG_TAG, "TABLE_TEMP_ORDER_DETAILS not successfully inserted " + ret1);
                        }
                    }

               /* holder.edt_no_of_bottle.setEnabled(false);
                holder.edt_no_of_case.setEnabled(false);
                holder.btn_save_to_cart.setEnabled(false);*/
                    MyApplication.createDataBase();


                    String total_final_amt = TABLE_TEMP_ORDER_DETAILS.getSumOfAllItems(MyApplication.get_session(MyApplication.SESSION_ORDER_ID));
                    if (total_final_amt == null) {
                        ActivityItem.txt_bill_price.setText("Rs " + "0.0");
                        // ActivityItem.txt_bill_price.setText((R.string.rupee_sign) + "0.0");
                    } else {
                        ActivityItem.txt_bill_price.setText("Rs " + total_final_amt + "");
                        //  ActivityItem.txt_bill_price.setText((R.string.rupee_sign) + total_final_amt + "");

                    }

                }
            }
        });





        /*Glide.with(context)
                .load(context.getResources().getIdentifier(model.getDistImagePath(), "drawable", context.getPackageName()))
                .fitCenter()
                .into(holder.img_name);*/

        // if (f.exists() && f.isFile()) {
         /* Glide.with(context).load(context.getResources().getIdentifier(model.getDistImagePath(), "drawable", context.getPackageName()))
                // .placeholder(R.drawable.ic_action_name)
                //.error(R.drawable.ic_action_name)
                .transform(new RoundedTransformation(70, 0, context.getResources().getIdentifier(model.getDistImagePath(), "drawable", context.getPackageName()))
                        .resizeDimen(R.dimen.dp60, R.dimen.dp60)
                        .centerCrop()
                        .into(holder.img_name));*/
        //}


          /*  public int getImage(String imageName) {

                int drawableResourceId = this.getResources().getIdentifier(imageName, "drawable", this.getPackageName());

                return drawableResourceId;
            }*/


        for (int j = 0; j < size; j++)

        {
            SubItemDataModel subItemDataModel = res.get(j);
            MyApplication.logi(LOG_TAG, "in for 1");
            System.out.println(res.get(j));
            itemID_from_db = subItemDataModel.getItem_id();


            for (int i = 0; i < subItemList.size(); i++) {
                MyApplication.logi(LOG_TAG, "in for 2");
                itemId_from_position = String.valueOf(subItemList.get(position).getItem_id());
                MyApplication.logi(LOG_TAG, "ITEMMIDD FORM POS-->" + itemId_from_position);


            }

            if (itemId_from_position.equalsIgnoreCase(String.valueOf(itemID_from_db))) {
                MyApplication.logi(LOG_TAG, "both are equal--->" + itemID_from_db + itemId_from_position);


                // holder.layout_border.setBackgroundColor(context.getResources().getColor(R.color.teal_100));


                int[] colors = {Color.parseColor(MyApplication.get_session(MyApplication.SESSION_Theme_Secondary_Text_Color)),
                        Color.parseColor(MyApplication.get_session(MyApplication.SESSION_Accent_Color))};

                //create a new gradient color
                GradientDrawable gd = new GradientDrawable(
                        GradientDrawable.Orientation.TOP_BOTTOM, colors);

                gd.setCornerRadius(0f);
                // gd.setStroke(2, Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR)));
                //apply the button background to newly created drawable gradient
                holder.layout_border.setBackground(gd);


                // holder.layout_border.setBackground(context.getResources().getDrawable(R.drawable.added_to_cart_background));


                //set here the data
                MyApplication.logi(LOG_TAG, "subItemDataModel.NAME()---->" + subItemDataModel.getSub_item_name());

                MyApplication.logi(LOG_TAG, "subItemDataModel.getCases()---->" + subItemDataModel.getCases());
                holder.edt_no_of_case.setText(subItemDataModel.getCases());
                holder.txt_total_price.setText(subItemDataModel.getTotal_price());
                holder.txt_total_price_old.setText(subItemDataModel.getTotal_price());
                holder.edt_no_of_bottle.setText(subItemDataModel.getBottles());
                holder.txt_no_of_free_bottle.setText(subItemDataModel.getNo_of_free_bottle());
                holder.txt_no_of_free_case.setText(subItemDataModel.getNo_of_free_case());


                holder.edt_no_of_bottle.setEnabled(false);
                holder.edt_no_of_case.setEnabled(false);
                holder.txt_no_of_free_case.setEnabled(false);
                holder.txt_no_of_free_bottle.setEnabled(false);
                holder.btn_save_to_cart.setEnabled(false);


            }
            MyApplication.logi(LOG_TAG, "itemID iiiss from database ---->" + itemID_from_db);

            String total_final_amt = TABLE_TEMP_ORDER_DETAILS.getSumOfAllItems(MyApplication.get_session(MyApplication.SESSION_ORDER_ID));
            ActivityItem.txt_bill_price.setText("Rs " + total_final_amt + "");


        }


    }

    private String spilt(String label_rps_price_value) {
        String str = label_rps_price_value;
        String[] splited = str.split(" ");

        String split_one = splited[0];
        String split_second = splited[1];
        MyApplication.logi(LOG_TAG, "split_second-->" + split_second);
        return split_second;

    }

    @Override
    public int getItemCount() {
        return subItemList == null ? 0 : subItemList.size();
    }

}
