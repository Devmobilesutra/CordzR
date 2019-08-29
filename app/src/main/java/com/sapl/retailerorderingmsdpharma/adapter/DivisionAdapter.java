package com.sapl.retailerorderingmsdpharma.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sapl.retailerorderingmsdpharma.R;
import com.sapl.retailerorderingmsdpharma.activities.ActivitySelection;
import com.sapl.retailerorderingmsdpharma.activities.MyApplication;
import com.sapl.retailerorderingmsdpharma.customView.CustomTextViewMedium;
import com.sapl.retailerorderingmsdpharma.models.DivisionModel;

import java.util.List;

import static com.sapl.retailerorderingmsdpharma.activities.ActivitySelection.tabHost;


/**
 * Created by MRUNAL on 07-Feb-18.
 */

public class DivisionAdapter extends RecyclerView.Adapter<DivisionAdapter.MyViewHolder> {
    private List<DivisionModel> selectionList;
    public String LOG_TAG = "DivisionAdapter ";
    Context context;

    public DivisionAdapter(Context context, List<DivisionModel> selectionList) {
        this.selectionList = selectionList;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public CustomTextViewMedium txt_selection_name, txt_selection_description, txt_selection_offer;

        public ImageView img_selection;
        LinearLayout Linear_Layout;

        public MyViewHolder(final View view) {
            super(view);

            txt_selection_name = view.findViewById(R.id.txt_selection_name);
            txt_selection_description = view.findViewById(R.id.txt_selection_description);
            txt_selection_name.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_Text_Primary_Color)));
            txt_selection_description.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_Text_Secondary_Color)));
            txt_selection_offer = view.findViewById(R.id.txt_selection_offer);
            img_selection = (ImageView) view.findViewById(R.id.img_selection);
            img_selection.setColorFilter(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_THEME_DARK_COLOR)));
            Linear_Layout = (LinearLayout)view.findViewById(R.id.Linear_Layout);
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

                        DivisionModel model = selectionList.get(pos);

                        MyApplication.set_session(MyApplication.SESSION_GROUP_ID, model.getDivision_id()+"");
                       // Toast.makeText(v.getContext(), "You clicked -->" + model.getDivision_id(), Toast.LENGTH_SHORT).show();
                        MyApplication.logi(LOG_TAG, "MyApplication.SESSION_GROUP_ID =->"+ model.getDivision_id()+"");


                      /*  ActivitySelection.edt_search_distributor.setVisibility(View.GONE);
                        ActivitySelection.edt_cancel_distributor.setVisibility(View.GONE);
                       ActivitySelection.edt_search_distributor.setText("");
*/
                        if (tabHost.getCurrentTab() < 3) {

                            TextView tv = (TextView) tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).findViewById(R.id.title);
                            tv.setText(model.getDivision());


                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                            tabHost.getCurrentTabView().setBackground(context.getResources().getDrawable(R.drawable.border_background_white));
                            else
                                tabHost.getCurrentTabView().setBackgroundResource(R.drawable.border_background_white);
                            tabHost.setCurrentTab(tabHost.getCurrentTab() + 1);




                            /*TextView tv = (TextView) tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).findViewById(R.id.title);
                            tv.setText(model.getDivision());
*/
                        } else {
                        //    ActivitySelection.txt_title_with_header.setText(MyApplication.get_session("sub_group_title"));

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
    public DivisionAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_selection, parent, false);
        return new DivisionAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final DivisionAdapter.MyViewHolder holder, final int position) {
        final DivisionModel model = selectionList.get(position);


        holder.txt_selection_name.setText(model.getDivision());
       /* holder.img_selection.clearAnimation();
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
        });*/





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