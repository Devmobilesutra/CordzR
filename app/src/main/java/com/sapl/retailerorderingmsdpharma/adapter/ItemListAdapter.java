package com.sapl.retailerorderingmsdpharma.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.sapl.retailerorderingmsdpharma.MyDatabase.TABLE_ORDER_DETAILS;
import com.sapl.retailerorderingmsdpharma.MyDatabase.TABLE_TEMP_ORDER_DETAILS;
import com.sapl.retailerorderingmsdpharma.R;
import com.sapl.retailerorderingmsdpharma.activities.ActivityPreviewOrder;
import com.sapl.retailerorderingmsdpharma.activities.ActivitySelection;
import com.sapl.retailerorderingmsdpharma.activities.MyApplication;
import com.sapl.retailerorderingmsdpharma.customView.CustomButtonRegular;
import com.sapl.retailerorderingmsdpharma.customView.CustomEditTextMedium;
import com.sapl.retailerorderingmsdpharma.customView.CustomTextViewMedium;
import com.sapl.retailerorderingmsdpharma.models.OrderReviewModel;

import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;


/**
 * Created by MRUNAL on 07-Feb-18.
 */

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.MyViewHolder> {
    private List<OrderReviewModel> orderReviewList;
    Context context;
    private static final String LOG_TAG = "ItemListAdapter";
    String location = "";

    public ItemListAdapter(Context context, List<OrderReviewModel> orderReviewList) {
        this.orderReviewList = orderReviewList;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public CustomTextViewMedium txt_product_name, txt_price_of_product, txt_case_no, txt_bottle_no,
                txt_free_case_no, txt_free_bottle_no;
        public CustomTextViewMedium txt_case_label, txt_bottle_label, txt_free_case_label, txt_free_bottle_label;
        public ImageView img_product, img_edt, img_delete;


        public MyViewHolder(View view) {
            super(view);

            txt_product_name = view.findViewById(R.id.txt_product_name);
            txt_price_of_product = view.findViewById(R.id.txt_price_of_product);
            txt_case_no = view.findViewById(R.id.txt_case_no);
            txt_bottle_no = view.findViewById(R.id.txt_bottle_no);
            txt_free_case_no = view.findViewById(R.id.txt_free_case_no);
            txt_free_bottle_no = view.findViewById(R.id.txt_free_bottle_no);

            img_product = (ImageView) view.findViewById(R.id.img_product);
            img_edt = (ImageView) view.findViewById(R.id.img_edt);
            img_delete = (ImageView) view.findViewById(R.id.img_delete);

            txt_product_name.setTextColor(context.getResources().getColor(R.color.heading_background));
            txt_case_no.setTextColor(context.getResources().getColor(R.color.heading_background));
            txt_free_case_no.setTextColor(context.getResources().getColor(R.color.heading_background));
            txt_bottle_no.setTextColor(context.getResources().getColor(R.color.heading_background));
            txt_free_bottle_no.setTextColor(context.getResources().getColor(R.color.heading_background));
            txt_price_of_product.setTextColor(context.getResources().getColor(R.color.heading_background));

            txt_case_label = view.findViewById(R.id.txt_case_label);
            txt_bottle_label = view.findViewById(R.id.txt_bottle_label);
            txt_free_case_label = view.findViewById(R.id.txt_free_case_label);
            txt_free_bottle_label = view.findViewById(R.id.txt_free_bottle_label);

            txt_case_label.setTextColor(context.getResources().getColor(R.color.heading_background));
            txt_bottle_label.setTextColor(context.getResources().getColor(R.color.heading_background));
            txt_free_case_label.setTextColor(context.getResources().getColor(R.color.heading_background));
            txt_free_bottle_label.setTextColor(context.getResources().getColor(R.color.heading_background));


            img_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {


                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Confirm Delete")
                                .setMessage("Do you want to delete order?")
                                .setIcon(android.R.drawable.ic_delete)
                                .setPositiveButton("Yes",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,
                                                                int which) {
                                                OrderReviewModel model = orderReviewList.get(pos);
                                             //   long ret = TABLE_TEMP_ORDER_DETAILS.deletePreviewOrder(model.getItem_id(), MyApplication.get_session(MyApplication.SESSION_ORDER_ID));


                                                MyApplication.logi(LOG_TAG, "in delete-->" + pos);

                                                orderReviewList.remove(pos);
                                                ItemListAdapter.this.notifyItemRemoved(pos);
                                                MyApplication.displayMessage(context, "Order deleted successfully.");

                                            }
                                        }).setNegativeButton("Cancel", null) // Do nothing on no
                                .show();


                    }


                }
            });


            img_edt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        OrderReviewModel model = orderReviewList.get(pos);
                        // editOrder(model.getCase_no(), model.getFree_case_no(),
                        //       model.getBoittle_no(), model.getFree_bottle_no(), model.getPrice_of_product());
                        MyApplication.logi(LOG_TAG, "modellll->>" + model.toString());
                        editOrder(model, pos);
                    }
                }
            });


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // get position
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {

                        OrderReviewModel model = orderReviewList.get(pos);
                       // ActivitySelection.txt_title_with_header.setText(MyApplication.get_session("item"));

                    }
                }
            });
        }
    }

    @Override
    public ItemListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_item_list, parent, false);
        return new ItemListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ItemListAdapter.MyViewHolder holder, final int position) {
        final OrderReviewModel model = orderReviewList.get(position);
        MyApplication.logi(LOG_TAG, "model--->" + model);
        //  MyApplication.set_session("model_item_id",model.getItem_id());


        holder.img_delete.setTag(position);
        holder.img_product.clearAnimation();
        holder.img_product.clearFocus();
        holder.img_product.clearColorFilter();

        // MyApplication.log("NAME: ---------->  "+model.getName()+" ****************");
        holder.txt_bottle_no.setText(model.getBoittle_no());
        MyApplication.logi(LOG_TAG, "id ----->" + model.getItem_id());
        MyApplication.logi(LOG_TAG, "getBoittle_no ----->" + model.getBoittle_no());
        holder.txt_case_no.setText(model.getCase_no());
        MyApplication.logi(LOG_TAG, "getBoittle_no ----->" + model.getCase_no());
        holder.txt_free_case_no.setText(model.getFree_case_no());
        holder.txt_free_bottle_no.setText(model.getFree_bottle_no());
        MyApplication.logi(LOG_TAG, "PRODUCT NAME ----->" + model.getProduct_name());
        holder.txt_product_name.setText(model.getProduct_name());


        holder.txt_case_label.setText(MyApplication.get_session(MyApplication.SESSION_UOM_VALUE_FIRST));
        holder.txt_bottle_label.setText(MyApplication.get_session(MyApplication.SESSION_UOM_VALUE_SECOND));
        holder.txt_free_case_label.setText("Free " + MyApplication.get_session(MyApplication.SESSION_UOM_VALUE_FIRST));
        holder.txt_free_bottle_label.setText("Free " + MyApplication.get_session(MyApplication.SESSION_UOM_VALUE_SECOND));


        holder.txt_price_of_product.setText("₹ " + model.getPrice_of_product());


        if (model.getProduct_img_path() == null) {
            Glide.with(context)
                    .load(R.mipmap.msd_logo_1)
                    .asBitmap()
                    .error(R.mipmap.msd_logo_1)
                    .placeholder(R.mipmap.msd_logo_1)
                    .into(holder.img_product);
        } else {
            byte[] decodeString = Base64.decode(model.getProduct_img_path(), Base64.DEFAULT);
            Bitmap decodebitmap = BitmapFactory.decodeByteArray(decodeString, 0, decodeString.length);
            Glide.with(context)
                    .load(decodeString)
                    .asBitmap()
                    .placeholder(R.drawable.arvind1)
                    .into(holder.img_product);
        }
       /* Glide.with(context).load(context.getResources().getIdentifier(model.getProduct_img_path(),
                "drawable", context.getPackageName()))
                .asBitmap().centerCrop().into(new BitmapImageViewTarget(holder.img_product) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                holder.img_product.setImageDrawable(circularBitmapDrawable);
            }
        });*/

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
    }

    @Override
    public int getItemCount() {
        return orderReviewList == null ? 0 : orderReviewList.size();
    }


    public void editOrder(final OrderReviewModel model, final int pos
                          /*String caseNo, String freeCase, String bottleNo, String freeBottleNo, String price*/) {

        MyApplication.logi(LOG_TAG, "IN EDIT ORDER");
        // LayoutInflater layoutInflater =LayoutInflater.from(context);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        //(LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.dialog_edit_order, null);
        final PopupWindow popupWindow = new PopupWindow(popupView,
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);

        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);

        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

        final CustomEditTextMedium edt_case_no, edt_bottle_no, txt_free_case_no, txt_free_bottle_no;
        final CustomTextViewMedium txt_title, txt_product_name, txt_case_lable, txt_free_case_lable;
        final CustomTextViewMedium txt_bottle_lable, txt_free_bottle_lable;

        edt_case_no = popupView.findViewById(R.id.edt_case_no);
        edt_bottle_no = popupView.findViewById(R.id.edt_bottle_no);

        txt_title = popupView.findViewById(R.id.txt_title);
        txt_product_name = popupView.findViewById(R.id.txt_product_name);
        txt_case_lable = popupView.findViewById(R.id.txt_case_lable);
        txt_free_case_lable = popupView.findViewById(R.id.txt_free_case_lable);
        txt_free_case_no = popupView.findViewById(R.id.txt_free_case_no);

        txt_bottle_lable = popupView.findViewById(R.id.txt_bottle_lable);
        txt_free_bottle_lable = popupView.findViewById(R.id.txt_free_bottle_lable);
        txt_free_bottle_no = popupView.findViewById(R.id.txt_free_bottle_no);

        edt_case_no.setText(model.getCase_no());
        edt_bottle_no.setText(model.getBoittle_no());
        txt_free_case_no.setText(model.getFree_case_no());
        txt_free_bottle_no.setText(model.getFree_bottle_no());
        txt_product_name.setText(model.getProduct_name());

        txt_case_lable.setTextColor(context.getResources().getColor(R.color.heading_background));
        txt_bottle_lable.setTextColor(context.getResources().getColor(R.color.heading_background));
        txt_free_bottle_lable.setTextColor(context.getResources().getColor(R.color.heading_background));
        txt_free_bottle_no.setTextColor(context.getResources().getColor(R.color.heading_background));

        txt_free_case_no.setTextColor(context.getResources().getColor(R.color.heading_background));
        txt_free_case_lable.setTextColor(context.getResources().getColor(R.color.heading_background));

        ImageView img_back = (ImageView) popupView.findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });


        CustomButtonRegular btn_update = popupView.findViewById(R.id.btn_update);
        // if button is clicked, close the custom dialog

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.setBoittle_no("" + edt_bottle_no.getText().toString().trim());
                model.setCase_no("" + edt_case_no.getText().toString().trim());
                model.setFree_bottle_no("" + txt_free_bottle_no.getText().toString().trim());
                model.setFree_case_no("" + txt_free_case_no.getText().toString().trim());


                double single_case_value_after_discount = model.getDiscounted_single_case_rate();
                double single_btl_value_after_discount = model.getSingle_btl_price();
                int edited_case_value = 0, edited_btl_value = 0;

                if (!edt_case_no.getText().toString().trim().equalsIgnoreCase("")) {
                    edited_case_value = Integer.parseInt(edt_case_no.getText().toString().trim());

                    model.setCase_no(edited_case_value + "");
                }


                if (!edt_bottle_no.getText().toString().trim().equalsIgnoreCase("")) {
                    edited_btl_value = Integer.parseInt(edt_bottle_no.getText().toString().trim());
                    model.setBoittle_no(edited_btl_value + "");

                }
                double final_case_val = single_case_value_after_discount * edited_case_value;
                double final_btl_val = single_btl_value_after_discount * edited_btl_value;
                MyApplication.logi(LOG_TAG, "final_case_val" + final_case_val);
                MyApplication.logi(LOG_TAG, "final_btl_val" + final_btl_val);
                double total_price_of_product = Double.parseDouble(String.valueOf(final_btl_val + final_case_val));
                MyApplication.logi(LOG_TAG, "total_price_of_product--------" + total_price_of_product);
                String amt1 = String.format("%.2f", total_price_of_product);
                MyApplication.logi(LOG_TAG, "amt1 total_price_of_product--------" + amt1);
                model.setPrice_of_product(amt1);
                //model.setPrice_of_product(String.valueOf(final_btl_val + final_case_val));


                long ret1 = TABLE_TEMP_ORDER_DETAILS.insertOrderDetails(MyApplication.get_session(MyApplication.SESSION_ORDER_ID), model.getItem_id(), model.getProduct_name(), String.valueOf(edited_case_value), String.valueOf(edited_btl_value),
                        String.valueOf(single_btl_value_after_discount), String.valueOf(single_case_value_after_discount), Float.parseFloat(model.getPrice_of_product()), "0", "0", "update_data");

                if (ret1 == 0) {
                    MyApplication.logi(LOG_TAG, "TABLE_TEMP_ORDER_DETAILS successfully inserted " + ret1);
                    Toast.makeText(context, "Updated data successfully...", Toast.LENGTH_SHORT).show();


                } else {
                    MyApplication.logi(LOG_TAG, "TABLE_TEMP_ORDER_DETAILS not successfully inserted " + ret1);
                }

                orderReviewList.remove(pos);
                ItemListAdapter.this.notifyItemRemoved(pos);
                orderReviewList.add(pos, model);
                ItemListAdapter.this.notifyDataSetChanged();
                ItemListAdapter.this.notifyItemInserted(pos);
                popupWindow.dismiss();
                MyApplication.displayMessage(context, "Order is successfully updated.");


                String sum = TABLE_TEMP_ORDER_DETAILS.getSumOfAllItems(MyApplication.get_session(MyApplication.SESSION_ORDER_ID));
                ActivityPreviewOrder.txt_total_price.setText("₹" + sum);
            }
        });


        edt_case_no.setMinWidth(20);
        edt_bottle_no.setMinWidth(20);

        txt_free_case_no.setMinWidth(20);
        txt_free_bottle_no.setMinWidth(20);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            edt_case_no.setBackground(context.getResources().getDrawable(R.drawable.border_background));
            edt_bottle_no.setBackground(context.getResources().getDrawable(R.drawable.border_background));

            txt_free_case_no.setBackground(context.getResources().getDrawable(R.drawable.border_background));
            txt_free_bottle_no.setBackground(context.getResources().getDrawable(R.drawable.border_background));
        } else {
            txt_free_case_no.setBackgroundResource(R.drawable.border_background);
            txt_free_bottle_no.setBackgroundResource(R.drawable.border_background);
        }

    }


}