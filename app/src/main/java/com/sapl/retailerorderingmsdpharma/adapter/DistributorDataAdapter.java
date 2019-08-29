package com.sapl.retailerorderingmsdpharma.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.sapl.retailerorderingmsdpharma.MyDatabase.TABLE_PCUSTOMER;
import com.sapl.retailerorderingmsdpharma.MyDatabase.TABLE_PDISTRIBUTOR;
import com.sapl.retailerorderingmsdpharma.MyDatabase.TABLE_TEMP_RETAILER_ORDER_MASTER;
import com.sapl.retailerorderingmsdpharma.R;
import com.sapl.retailerorderingmsdpharma.activities.ActivityPreviewOrder;
import com.sapl.retailerorderingmsdpharma.activities.ActivitySelection;
import com.sapl.retailerorderingmsdpharma.activities.MyApplication;
import com.sapl.retailerorderingmsdpharma.customView.CustomTextViewMedium;
import com.sapl.retailerorderingmsdpharma.models.DistributorModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Pattern;



public class DistributorDataAdapter extends RecyclerView.Adapter<DistributorDataAdapter.MyViewHolder> {
    private List<DistributorModel> dastributorList;
    Context context;
    private static final String LOG_TAG = "DistributorDataAdapter";
    String location = "";
    CharSequence current_date_old;
String DistributorName;
    String DistributorId;

    DistributorDataAdapterlistner listner = null;
    private int permission_count = 1;

    public DistributorDataAdapter(Context context, List<DistributorModel> dastributorList) {
        this.dastributorList = dastributorList;
        this.context = context;
        this.listner = listner;
    }


    public interface DistributorDataAdapterlistner {
        void onContactSelected(DistributorModel distributorModel);

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public CustomTextViewMedium txt_distributor_name, txt_rating, txt_distributor_subtitle;  //, balance; //address, contact
        public ImageView img_name, img_call, img_info, img_rating;

        public MyViewHolder(View view) {
            super(view);

            txt_distributor_name = view.findViewById(R.id.txt_distributor_name);
            txt_rating = view.findViewById(R.id.txt_rating);
            txt_distributor_subtitle = view.findViewById(R.id.txt_distributor_subtitle);
            img_rating = (ImageView) view.findViewById(R.id.img_rating);
            img_name = (ImageView) view.findViewById(R.id.img_name);
            img_name.setColorFilter(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_THEME_DARK_COLOR)));


            img_call = (ImageView) view.findViewById(R.id.img_call);
            img_info = (ImageView) view.findViewById(R.id.img_info);
            img_call.setColorFilter(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_THEME_DARK_COLOR)));
            img_info.setColorFilter(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_THEME_DARK_COLOR)));
            img_rating.setColorFilter(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_THEME_DARK_COLOR)));

            txt_distributor_name.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_Text_Primary_Color)));


            txt_distributor_subtitle.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_Text_Secondary_Color)));


            txt_rating.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_THEME_DARK_COLOR)));


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (MyApplication.get_session("distributor_list").equalsIgnoreCase("card_order_booking")) {
                        // get position
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION) {
                            String str_date = MyApplication.getCurrentDate();
                            DistributorModel model = dastributorList.get(pos);
                            // Toast.makeText(v.getContext(), "You clicked " + model.getName(), Toast.LENGTH_SHORT).show();
                            MyApplication.set_session(MyApplication.SESSION_DISTRIBUTER_NAME, model.getName());
                            //Toast.makeText(v.getContext(), "You clicked" + model.getDist_id(), Toast.LENGTH_SHORT).show();
                            MyApplication.set_session(MyApplication.SESSION_DISTRIBUTOR_ID, model.getDist_id() + "");
                            DistributorName=model.getName();
                            DistributorId= String.valueOf(model.getDist_id());
                            MyApplication.logi(LOG_TAG, "DistributorName==" + DistributorName);
                            MyApplication.logi(LOG_TAG, "DistributorId==" + DistributorId);
                            MyApplication.logi(LOG_TAG, "model.conct--->" + model.getContactNo());
                            MyApplication.logi(LOG_TAG, "model.MOB--->" + model.getContactNo_mob());

                            int ret = TABLE_TEMP_RETAILER_ORDER_MASTER.checkDistPresentInTempOrderMaster(String.valueOf(model.getDist_id()));

                            if (ret != 1) {
                                MyApplication.logi(LOG_TAG, "IN RET == 0");
                                Date d = new Date();
                                current_date_old = DateFormat.format("yyMMddHHmmss", d.getTime());
                                MyApplication.logi(LOG_TAG, "Currentdatenew-->" + current_date_old);
                                MyApplication.set_session(MyApplication.SESSION_ORDER_ID, current_date_old + "");


                                // str_date = MyApplication.dateFormat();
                                str_date = MyApplication.dateFormatwithT();
                                MyApplication.logi(LOG_TAG, "DATE in T FORMAT---->" + str_date);

                                MyApplication.set_session(MyApplication.SESSION_ORDER_DATE, str_date);

                                String retailerId = TABLE_PCUSTOMER.getCustId();

                                int ret_order_master = TABLE_TEMP_RETAILER_ORDER_MASTER.insertTempOrderMaster(MyApplication.get_session(MyApplication.SESSION_ORDER_ID),
                                        MyApplication.get_session(MyApplication.SESSION_DISTRIBUTOR_ID),
                                        retailerId,
                                        str_date,
                                        "0", "Pending",
                                        "NA",
                                        "NA", "");

                                if (ret_order_master == 0) {

                                    MyApplication.logi(LOG_TAG, "ret_order_master == 0");
                                } else {
                                    MyApplication.logi(LOG_TAG, "in else ret_order_master == 0");
                                }


                            } else {
                                MyApplication.logi(LOG_TAG, "alredy presetn");
                            }


                            if (MyApplication.get_session("SESSION_INSERT_IN_FINAL_MASTER_TABLE").equalsIgnoreCase("true")) {

                                MyApplication.logi(LOG_TAG, "FINAL INSERT IN FINAL MASTER TABLE");
                            }

/////CALL TO DIVISION,BRAND,ITEM,SUBITEM ..I.E SELECTION
                            Intent intent = new Intent(context, ActivitySelection.class);
                            intent.putExtra("DistributorName", DistributorName);
                            intent.putExtra("DistributorId", DistributorId);
                            context.startActivity(intent);
                            // context.finish();

                        }
                    } else if (MyApplication.get_session("distributor_list").equalsIgnoreCase("cart")) {
                        MyApplication.logi(LOG_TAG, "IN CARTTTTTT");


                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION) {

                            DistributorModel model = dastributorList.get(pos);
                            MyApplication.logi(LOG_TAG, "model.ORDERID->" + model.getDist_id());
                            TABLE_TEMP_RETAILER_ORDER_MASTER.checkDistPresentInTempOrderMaster(String.valueOf(model.getDist_id()));

                            MyApplication.logi(LOG_TAG, "model.name clicked--->" + model.getName());
                            MyApplication.set_session(MyApplication.SESSION_ORDER_ID_FROM_CART, model.getName());
                            MyApplication.set_session("preview_order", "from_cart");
                            Intent intent = new Intent(context, ActivityPreviewOrder.class);
                            //finish();
                            ///overridePendingTransition(R.anim.fade_in_call, R.anim.fade_out_call);
                            context.startActivity(intent);

                        }
                    }
                }
            });

        }


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_distributor_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final DistributorModel model = dastributorList.get(position);
        /*holder.img_name.clearAnimation();
        holder.img_name.clearFocus();
        holder.img_name.clearColorFilter();*/

        MyApplication.log("NAME: ---------->  " + model.getName() + " ****************");
        holder.txt_distributor_name.setText(model.getName());
        holder.txt_rating.setText(model.getRating());
        holder.txt_distributor_subtitle.setText(model.getAddress());


        holder.img_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                   /* if (MyApplication.isMarshMellow()) {
                        if (getPermissionCount() > 0)
                            check_app_persmission();
                        else
                            display_calling_alert(model.getContactNo(),model.getContactNo_mob());
                    } else {*/

                display_calling_alert(model.getContactNo(), model.getContactNo_mob());
                // }
            }
        });


        holder.img_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // display_information_alert(model.getName(),model.getAddress());


                String info = model.getInfo();
                MyApplication.logi(LOG_TAG, "INFO IS-->" + info);
                String OriginalString = info.replaceAll("\\||", "");
                MyApplication.logi(LOG_TAG, "OriginalString-->" + OriginalString);
                display_information_alert(OriginalString);
            }
        });

        // holder.img_name.setText(model.getImageName());


        /*Glide.with(context).load(context.getResources().getIdentifier(model.getDistImagePath(),
                "drawable", context.getPackageName()))
                .asBitmap().centerCrop().into(new BitmapImageViewTarget(holder.img_name) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                holder.img_name.setImageDrawable(circularBitmapDrawable);
            }
        });
*/

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


    public void display_calling_alert(final String landline, final String mobile) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder
                //  .setTitle(name.toString())
                .setMessage("Mobile : " + mobile.toString() + "\nLandline : " + landline.toString())
                .setIcon(R.drawable.distributor_call)
                .setPositiveButton(mobile.toString(), new DialogInterface.OnClickListener() {
                    @SuppressLint("MissingPermission")
                    public void onClick(DialogInterface dialog, int which) {
                        if (!mobile.trim().equals("")) {

                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse("tel:" + mobile));
                            //startActivity(callIntent);
                            context.startActivity(callIntent);


                        } else {
                            Toast.makeText(context, "Mobile Number Not Available", Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .setNegativeButton(landline.toString(), new DialogInterface.OnClickListener() {
                    @SuppressLint("MissingPermission")
                    public void onClick(DialogInterface dialog, int which) {
                        if (!landline.trim().equals("")) {

                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse("tel:" + landline));
                            context.startActivity(callIntent);

                        } else {
                            Toast.makeText(context, "Landline Number Not Available", Toast.LENGTH_LONG).show();
                        }
                    }
                })                        //Do nothing on no
                .show();
    }


    public void display_information_alert(String info) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder
                //  .setTitle(name.toString())
                .setMessage("Information : " + info)
                .setIcon(R.drawable.distributor_call)

                .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })                        //Do nothing on no
                .show();
    }


    @Override
    public int getItemCount() {
        return dastributorList == null ? 0 : dastributorList.size();
    }
}