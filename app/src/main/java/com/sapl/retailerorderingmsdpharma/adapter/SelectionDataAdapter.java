package com.sapl.retailerorderingmsdpharma.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.sapl.retailerorderingmsdpharma.R;
import com.sapl.retailerorderingmsdpharma.activities.ActivityBrand;
import com.sapl.retailerorderingmsdpharma.activities.ActivityDistributorList;
import com.sapl.retailerorderingmsdpharma.activities.ActivityItem;
import com.sapl.retailerorderingmsdpharma.activities.ActivitySelection;
import com.sapl.retailerorderingmsdpharma.activities.ActivitySubGroup;
import com.sapl.retailerorderingmsdpharma.activities.GroupActivity;
import com.sapl.retailerorderingmsdpharma.activities.MyApplication;
import com.sapl.retailerorderingmsdpharma.customView.CustomTextViewMedium;
import com.sapl.retailerorderingmsdpharma.models.DistributorModel;
import com.sapl.retailerorderingmsdpharma.models.SelectionDataModel;

import java.util.List;


/**
 * Created by MRUNAL on 07-Feb-18.
 */

public class SelectionDataAdapter extends RecyclerView.Adapter<SelectionDataAdapter.MyViewHolder> {
    private List<SelectionDataModel> selectionList;
    Context context;

    public SelectionDataAdapter(Context context, List<SelectionDataModel> selectionList) {
        this.selectionList = selectionList;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public CustomTextViewMedium txt_selection_name , txt_selection_description, txt_selection_offer;

        public ImageView img_selection ;

        public MyViewHolder(View view) {
            super(view);

            txt_selection_name = view.findViewById(R.id.txt_selection_name);
            txt_selection_description = view.findViewById(R.id.txt_selection_description);
            txt_selection_name.setTextColor(context.getResources().getColor(R.color.heading_background));
            txt_selection_description.setTextColor(context.getResources().getColor(R.color.heading_background));
            txt_selection_offer = view.findViewById(R.id.txt_selection_offer);
            img_selection = (ImageView) view.findViewById(R.id.img_selection);

            /*txt_selection_name2 = view.findViewById(R.id.txt_selection_name2);
            txt_selection_description2 = view.findViewById(R.id.txt_selection_description2);
            txt_selection_offer2 = view.findViewById(R.id.txt_selection_offer2);
            img_selection2 = (ImageView) view.findViewById(R.id.img_selection2);*/


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // get position
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {

                        SelectionDataModel model = selectionList.get(pos);
                        if (ActivitySelection.tabHost.getCurrentTab() < 3) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                ActivitySelection.tabHost.getCurrentTabView().setBackground(context.getResources().getDrawable(R.drawable.border_background));
                                ActivitySelection.tabHost.setCurrentTab(ActivitySelection.tabHost.getCurrentTab() + 1);
                            }
                        }
                        else
                        {

                            /*Intent intent = new Intent(context, ActivityDistributorList.class);
                            //context.finish();
                            //context.overridePendingTransition(R.anim.fade_in_call, R.anim.fade_out_call);
                            context.startActivity(intent);*/
                        }

                    }
                }
            });
        }
    }

    @Override
    public SelectionDataAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_selection, parent, false);
        return new SelectionDataAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SelectionDataAdapter.MyViewHolder holder, final int position) {
        final SelectionDataModel model = selectionList.get(position);
            holder.img_selection.clearAnimation();
            holder.img_selection.clearFocus();
            holder.img_selection.clearColorFilter();

            holder.txt_selection_name.setText(model.getSelectionName());
            holder.txt_selection_description.setText(model.getSelectionDescription());
            holder.txt_selection_offer.setText(model.getSelectionOffer());;

            Glide.with(context).load(context.getResources().getIdentifier(model.getSelectionImg(),
                    "drawable", context.getPackageName()))
                    .asBitmap().centerCrop().into(new BitmapImageViewTarget(holder.img_selection) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    holder.img_selection.setImageDrawable(circularBitmapDrawable);
                }
            });





        /*else {
            holder.img_selection2.clearAnimation();
            holder.img_selection2.clearFocus();
            holder.img_selection2.clearColorFilter();

            holder.txt_selection_name2.setText(model.getSelectionName());
            holder.txt_selection_description2.setText(model.getSelectionDescription());
            holder.txt_selection_offer2.setText(model.getSelectionOffer());;

            Glide.with(context).load(context.getResources().getIdentifier(model.getSelectionImg(),
                    "drawable", context.getPackageName()))
                    .asBitmap().centerCrop().into(new BitmapImageViewTarget(holder.img_selection2) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    holder.img_selection2.setImageDrawable(circularBitmapDrawable);
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

    @Override
    public int getItemCount() {
        return selectionList == null ? 0 : selectionList.size();
    }
}