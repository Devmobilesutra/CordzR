package com.sapl.retailerorderingmsdpharma.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.sapl.retailerorderingmsdpharma.R;
import com.sapl.retailerorderingmsdpharma.confiq.GPSTracker;
import com.sapl.retailerorderingmsdpharma.customView.CircularTextView;
import com.sapl.retailerorderingmsdpharma.customView.CustomButtonRegular;
import com.sapl.retailerorderingmsdpharma.customView.CustomEditTextMedium;
import com.sapl.retailerorderingmsdpharma.customView.CustomTextViewMedium;
import com.sapl.retailerorderingmsdpharma.customView.CustomTextViewRegular;


public class ActivityRegisterTwo extends AppCompatActivity {

    public static String LOG_TAG = "ActivityRegisterTwo";
    CircularTextView txt_lable1, txt_lable2, txt_lable3,txt_no_of_product_taken;
    CustomTextViewRegular txt_sign_up_link;
    CustomEditTextMedium edt_retailer_area, edt_retailer_location, edt_pin_code;
    //edt_retailer_address edt_retailer_city, edt_retailer_district, edt_retailer_state''
    CustomButtonRegular btn_next;
    final Context context = this;
    SwitchCompat actionbar_switch;
    GPSTracker gps;
    ImageView img_menu,img_cart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_two);

        initComponants();
        initComponantListner();

    }

    public void initComponants() {


        img_cart = findViewById(R.id.img_cart);
        img_cart.setVisibility(View.GONE);
        img_menu = findViewById(R.id.img_menu);
        img_menu.setVisibility(View.GONE);

        txt_no_of_product_taken = findViewById(R.id.txt_no_of_product_taken);
        txt_no_of_product_taken.setVisibility(View.GONE);


        CustomTextViewMedium txt_title = findViewById(R.id.txt_title);
        txt_title.setText(getResources().getString(R.string.sign_up));
        txt_title.setTextColor((Color.parseColor(MyApplication.get_session(MyApplication.SESSION_PRIMARY_TEXT_COLOR))));



        CustomTextViewMedium txt_cmpl_area = findViewById(R.id.txt_cmpl_area);
        txt_cmpl_area.setTextColor(getResources().getColor(R.color.red));
        CustomTextViewMedium txt_cmpl_pin_code = findViewById(R.id.txt_cmpl_pin_code);
        txt_cmpl_pin_code.setTextColor(getResources().getColor(R.color.red));

        CustomTextViewMedium txt_cmpl_loc = findViewById(R.id.txt_cmpl_loc);
        txt_cmpl_loc.setTextColor(getResources().getColor(R.color.red));

        edt_retailer_area = findViewById(R.id.edt_retailer_area);
        edt_pin_code = findViewById(R.id.edt_pin_code);
        edt_retailer_location = findViewById(R.id.edt_retailer_location);
        edt_retailer_location.setClickable(false);
        edt_retailer_location.setActivated(false);

        txt_sign_up_link = findViewById(R.id.txt_sign_up_link);

        txt_lable1 = findViewById(R.id.txt_lable1);
        txt_lable2 = findViewById(R.id.txt_lable2);
        txt_lable3 = findViewById(R.id.txt_lable3);

        txt_lable1.setStrokeColor("#006e6e");
        txt_lable1.setTextColor(getResources().getColor(R.color.white));
        txt_lable2.setStrokeColor("#006e6e");
        txt_lable2.setTextColor(getResources().getColor(R.color.white));

        txt_lable3.setStrokeColor("#C0C0C0");
        txt_lable3.setTextColor(getResources().getColor(R.color.white));

        btn_next = findViewById(R.id.btn_next);
        // actionbar_switch = findViewById(R.id.actionbar_switch);


       /* if (!TextUtils.isEmpty(MyApplication.get_session(MyApplication.SESSION_AREA))) {
            edt_retailer_area.setText(MyApplication.get_session(MyApplication.SESSION_AREA));
        }
        if (!TextUtils.isEmpty(MyApplication.get_session(MyApplication.SESSION_PIN))) {
            edt_pin_code.setText(MyApplication.get_session(MyApplication.SESSION_PIN));
        }
        if (!TextUtils.isEmpty(MyApplication.get_session(MyApplication.SESSION_LOCATION))) {
            //           edt_retailer_location.setText(MyApplication.get_session(MyApplication.SESSION_LOCATION));
        }*/


    }

    public void initComponantListner() {
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pinCode = edt_pin_code.getText() + "";
                String area = edt_retailer_area.getText() + "";
                String shop_location = edt_retailer_location.getText().toString().trim();


                gps = new GPSTracker(ActivityRegisterTwo.this);
                if (gps.canGetLocation()) {

                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();
                    MyApplication.logi(LOG_TAG, "lattt is-->" + latitude);
                    MyApplication.logi(LOG_TAG, "longitute---->" + longitude);

                    String location = latitude + "," + longitude;
                    MyApplication.set_session(MyApplication.SESSION_LOCATION, location);
                    MyApplication.logi(LOG_TAG, "GEO TAGGINF IS--->" + MyApplication.get_session(MyApplication.SESSION_LOCATION));


                    // \n is for new line
                    // Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                } else {
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                    gps.showSettingsAlert();
                }


                if (TextUtils.isEmpty(shop_location)) {
                    edt_retailer_location.setError("Enter shop location");
                    return;
                }


                if (TextUtils.isEmpty(pinCode)) {
                    edt_pin_code.setError("Enter pin code");
                    return;
                }
                if (TextUtils.isEmpty(area)) {
                    edt_retailer_area.setError("Enter area");
                    return;
                }

                if (!TextUtils.isEmpty(area)) {
                    if (!TextUtils.isEmpty(pinCode) && pinCode.length() == 6) {

                        MyApplication.set_session(MyApplication.SESSION_AREA, area);
                        MyApplication.set_session(MyApplication.SESSION_PIN, pinCode);


                        //  MyApplication.set_session(MyApplication.SESSION_LOCATION, "23.56, 72.767" );

                        Intent i = new Intent(getApplicationContext(), ActivityRegisterThree.class);
                     //   finish();
                        overridePendingTransition(R.anim.fade_in_call, R.anim.fade_out_call);
                        startActivity(i);
                    } else
                        edt_pin_code.setError("Enter valid pin code.");
                } else
                    edt_retailer_area.setError("Enter area");
            }
        });

        txt_sign_up_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent i = new Intent(getApplicationContext(), ActivityLogin.class);
                finish();
                overridePendingTransition(R.anim.fade_in_call, R.anim.fade_out_call);
                startActivity(i);
            }
        });
    }


}
