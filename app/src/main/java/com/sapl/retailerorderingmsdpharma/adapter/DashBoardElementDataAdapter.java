package com.sapl.retailerorderingmsdpharma.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sapl.retailerorderingmsdpharma.R;
import com.sapl.retailerorderingmsdpharma.activities.ActivityDistributorList;
import com.sapl.retailerorderingmsdpharma.activities.ActivityShowOrderList;
import com.sapl.retailerorderingmsdpharma.customView.CustomTextViewMedium;
import com.sapl.retailerorderingmsdpharma.models.DashBoardElementModel;


import java.util.List;


/**
 * Created by MRUNAL on 07-Feb-18.
 */




//not used yet
public class DashBoardElementDataAdapter extends RecyclerView.Adapter<DashBoardElementDataAdapter.MyViewHolder> {
    private List<DashBoardElementModel> dashboardElementList;
    Context context;

    public DashBoardElementDataAdapter(Context context, List<DashBoardElementModel> dashboardElementList) {
        this.dashboardElementList = dashboardElementList;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public CustomTextViewMedium txt_element_title;  //, balance; //address, contact
        public ImageView img_name;

        public MyViewHolder(View view) {
            super(view);

            txt_element_title = view.findViewById(R.id.txt_element_title);
            img_name = (ImageView) view.findViewById(R.id.img_name);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // get position
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {

                        DashBoardElementModel model = dashboardElementList.get(pos);
                        //Toast.makeText(v.getContext(), "You clicked " + model.getTitle(), Toast.LENGTH_SHORT).show();

                        if(model.getTitle().equals(context.getResources().getString(R.string.order_booking))) {
                            //MyApplication.set_session("color","#0000FF");
                            Intent intent = new Intent(context, ActivityDistributorList.class);
                            context.startActivity(intent);
                            // context.finish();
                        }
                        if(model.getTitle().equals(context.getResources().getString(R.string.order_status))) {
                            Intent intent = new Intent(context, ActivityShowOrderList.class);
                            context.startActivity(intent);
                            //overridePendingTransition(R.anim.fade_in_call, R.anim.fade_out_call);
                        }


                        if(model.getTitle().equals(context.getResources().getString(R.string.resource_page))) {
                            Intent intent = new Intent(context, ActivityShowOrderList.class);
                            context.startActivity(intent);
                        }
                        if(model.getTitle().equals(context.getResources().getString(R.string.update_profile))) {
                            Intent intent = new Intent(context, ActivityShowOrderList.class);
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
                .inflate(R.layout.adapter_dashboard_element, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final DashBoardElementModel model = dashboardElementList.get(position);
        holder.txt_element_title.setText(model.getTitle());
        // holder.img_name.setText(model.getImageName());

        Glide.with(context)
                .load(context.getResources().getIdentifier(model.getImageName(), "mipmap", context.getPackageName()))
                .fitCenter()
                .into(holder.img_name);


          /*  public int getImage(String imageName) {

                int drawableResourceId = this.getResources().getIdentifier(imageName, "drawable", this.getPackageName());

                return drawableResourceId;
            }*/
    }

    @Override
    public int getItemCount() {
        return dashboardElementList == null ? 0 : dashboardElementList.size();
    }

    /*@Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }*/

}


