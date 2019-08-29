package com.sapl.retailerorderingmsdpharma.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.sapl.retailerorderingmsdpharma.R;
import com.sapl.retailerorderingmsdpharma.activities.ActivitySelection;
import com.sapl.retailerorderingmsdpharma.activities.MyApplication;
import com.sapl.retailerorderingmsdpharma.customView.CustomTextViewMedium;
import com.sapl.retailerorderingmsdpharma.models.DivisionModel;
import com.sapl.retailerorderingmsdpharma.models.OrderDeliveryStatusModel;

import java.util.List;



public class OrderBookedStatusAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<OrderDeliveryStatusModel> selectionList;
    public String LOG_TAG = "OrderBookedStatusAdapter ";
    Context context;

    public OrderBookedStatusAdapter(Context context, List<OrderDeliveryStatusModel> selectionList) {
        this.selectionList = selectionList;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public CustomTextViewMedium txt_shop_name, txt_card_number, txt_rupees;
        public CustomTextViewMedium txt_date, txt_order_status; //, balance; //address, contact
        public ImageView img_name, img_vechle;
        public CustomTextViewMedium processing, delivered;

        public MyViewHolder(View view) {
            super(view);

            txt_shop_name = view.findViewById(R.id.txt_shop_name);
            txt_shop_name.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_THEME_DARK_COLOR)));

            txt_date = view.findViewById(R.id.txt_date);
            txt_date.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_THEME_DARK_COLOR)));

            txt_order_status = view.findViewById(R.id.txt_order_status);
            txt_order_status.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_THEME_DARK_COLOR)));

            txt_card_number = view.findViewById(R.id.txt_cart_number);
            txt_card_number.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_THEME_DARK_COLOR)));

            txt_rupees = view.findViewById(R.id.txt_rupees);
            txt_rupees.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_THEME_DARK_COLOR)));

           /* processing = view.findViewById(R.id.processing);
            processing.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_TEXT_COLOR)));

            /// processing.setBackground(context.getResources().getDrawable(R.drawable.border_background_processing));


            delivered = view.findViewById(R.id.delivered);
            delivered.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_TEXT_COLOR)));

            /// processing.setBackground(context.getResources().getDrawable(R.drawable.border_background_processing));

            img_name = (ImageView) view.findViewById(R.id.img_name);
            img_vechle = (ImageView) view.findViewById(R.id.img_vechle);
*/
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // get position
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {

                       /* OrderDeliveryStatusModel model = dastributorList.get(pos);
                        //  Toast.makeText(v.getContext(), "You clicked " + model.getName(), Toast.LENGTH_SHORT).show();


                        //MyApplication.set_session("color","#0000FF");
                        Intent intent = new Intent(context, ActivityAproveOrder.class);*/
                        //context.startActivity(intent);

                        // context.finish();

                    }
                }
            });
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder vh;
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.adapter_order_booked_status_list, parent, false);
        vh = new MyViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final OrderDeliveryStatusModel model = selectionList.get(position);

        ((MyViewHolder) holder).txt_shop_name.setText(model.getDistributorId());
        ((MyViewHolder) holder).txt_card_number.setText(model.getCart_count());
        ((MyViewHolder) holder).txt_date.setText(model.getOrderDate());
        ((MyViewHolder) holder).txt_order_status.setText(model.getOrderStatus());
        ((MyViewHolder) holder).txt_rupees.setText(model.getAmount());

       /* if (model.getOrderStatus().contains("1")) {
            holder.txt_order_status.setText("Pending");
            holder.img_vechle.setColorFilter(ContextCompat.getColor(context, R.color.yellow), android.graphics.PorterDuff.Mode.MULTIPLY);
        } else if (model.getOrderStatus().contains("2")) {
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN)
                holder.delivered.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.border_background_processing));
            else
                holder.delivered.setBackground(context.getResources().getDrawable(R.drawable.border_background_processing));
            holder.txt_order_status.setText("Delivered");
            holder.img_vechle.setColorFilter(ContextCompat.getColor(context, R.color.green), android.graphics.PorterDuff.Mode.MULTIPLY);
        } else {
            holder.txt_order_status.setText("Rejected");
            holder.img_vechle.setColorFilter(ContextCompat.getColor(context, R.color.red), android.graphics.PorterDuff.Mode.MULTIPLY);
        }*/


        // holder.img_name.setText(model.getImageName());

     /*   Glide.with(context).load(context.getResources().getIdentifier(model.getDistImagePath(),
                "drawable", context.getPackageName()))
                .asBitmap().centerCrop().into(new BitmapImageViewTarget(holder.img_name) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                holder.img_name.setImageDrawable(circularBitmapDrawable);
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
        return selectionList == null ? 0 : selectionList.size();
    }

    /*public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }*/

}