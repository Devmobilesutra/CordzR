package com.sapl.retailerorderingmsdpharma.activities;


import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.sapl.retailerorderingmsdpharma.R;

import com.sapl.retailerorderingmsdpharma.ServerCall.HTTPVollyRequest;
import com.sapl.retailerorderingmsdpharma.ServerCall.MyListener;
import com.sapl.retailerorderingmsdpharma.customView.CircularTextView;
import com.sapl.retailerorderingmsdpharma.customView.CustomButtonRegular;
import com.sapl.retailerorderingmsdpharma.customView.CustomEditTextMedium;
import com.sapl.retailerorderingmsdpharma.customView.CustomTextViewMedium;
import com.sapl.retailerorderingmsdpharma.customView.CustomTextViewRegular;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.lang.Character;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ActivityRegisterThree extends AppCompatActivity {
    CustomTextViewRegular txt_sign_up_link;
    CircularTextView txt_lable1, txt_lable2, txt_lable3,txt_no_of_product_taken;
    CustomEditTextMedium edt_retailer_reg_no, edt_retailer_gst_no, edt_retailer_licence_no, edt_password, edt_confirm_password;
    CustomButtonRegular btn_next;
    ImageButton btn_add_more_lic_no,show_password,show_Confirm_password;
    static String LOG_TAG = "ActivityRegisterThree";
    String otp_data = "";
    Context context;
    String str_reg_no, str_gst_no, str_license_no = "", str_password, str_confirm_password, deviceId;
    ImageView img_menu,img_cart;
    boolean isPasswordVisible=false;
   boolean isPasswordVisibleconfirm=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_three);
        context = this;
        initComponants();
        initComponantListner();

        String  firebaseToken = FirebaseInstanceId.getInstance().getToken();
        //Toast.makeText(getApplicationContext(),"1St\n  "+firebaseToken, Toast.LENGTH_LONG).show();
        if(TextUtils.isEmpty(firebaseToken)) {
            MyApplication.d(LOG_TAG+ "FCMTOKEN: "+firebaseToken);
            //MyApplication.set_session(MyApplication.SESSION_FCM_TOKEN, firebaseToken);
            //MyApplication.e(LOG_TAG+ "FCMTOKEN: "+MyApplication.get_session(MyApplication.SESSION_FCM_TOKEN));
        }

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



        txt_sign_up_link = findViewById(R.id.txt_sign_up_link);

        txt_lable1 = findViewById(R.id.txt_lable1);
        txt_lable2 = findViewById(R.id.txt_lable2);
        txt_lable3 = findViewById(R.id.txt_lable3);
        edt_retailer_reg_no = findViewById(R.id.edt_retailer_reg_no);
        edt_retailer_gst_no = findViewById(R.id.edt_retailer_gst_no);
        edt_retailer_licence_no = findViewById(R.id.edt_retailer_licence_no);
        edt_password = findViewById(R.id.edt_password);
        edt_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        edt_confirm_password = findViewById(R.id.edt_confirm_password);
        edt_confirm_password.setTransformationMethod(PasswordTransformationMethod.getInstance());

        //txt_lable1.setBackgroundColor(getResources().getColor(R.color.black));
        //txt_lable1.setStrokeColor("#808080");
        txt_lable1.setStrokeColor("#006e6e");
        txt_lable1.setTextColor(getResources().getColor(R.color.white));
        txt_lable2.setStrokeColor("#006e6e");
        txt_lable2.setTextColor(getResources().getColor(R.color.white));
        txt_lable3.setStrokeColor("#006e6e");
        txt_lable3.setTextColor(getResources().getColor(R.color.white));

        CustomTextViewMedium txt_cmpl_mob = findViewById(R.id.txt_cmpl_mob);
        txt_cmpl_mob.setTextColor(getResources().getColor(R.color.red));

        CustomTextViewMedium txt_cmpl_lic = findViewById(R.id.txt_cmpl_lic);
        txt_cmpl_lic.setTextColor(getResources().getColor(R.color.red));
        CustomTextViewMedium txt_cmpl_pass = findViewById(R.id.txt_cmpl_pass);
        txt_cmpl_pass.setTextColor(getResources().getColor(R.color.red));
        CustomTextViewMedium txt_cmpl_conf_pass = findViewById(R.id.txt_cmpl_conf_pass);
        txt_cmpl_conf_pass.setTextColor(getResources().getColor(R.color.red));


      /*  edt_password = findViewById(R.id.edt_password);
        edt_confirm_password = findViewById(R.id.edt_confirm_password);

        edt_confirm_password.setTransformationMethod(PasswordTransformationMethod.getInstance());*/
        btn_add_more_lic_no = findViewById(R.id.btn_add_more_lic_no);

        show_password = findViewById(R.id.show_password);
        show_Confirm_password = findViewById(R.id.show_Confirm_password);
        btn_next = findViewById(R.id.btn_next);

      /*  TelephonyManager TelephonyMgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)
            return;
        deviceId = TelephonyMgr.getDeviceId();*/

        deviceId =  Settings.Secure.getString(getApplication().getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);

        MyApplication.set_session(MyApplication.SESSION_DEVICE_ID, deviceId);

        String  firebaseToken = FirebaseInstanceId.getInstance().getToken();
        //MyApplication.set_session(MyApplication.SESSION_FCM_TOKEN, firebaseToken);
        //Toast.makeText(getApplicationContext(),"1St\n  "+firebaseToken, Toast.LENGTH_LONG).show();
        //if(TextUtils.isEmpty(firebaseToken))
           // MyApplication.set_session(MyApplication.SESSION_FCM_TOKEN, firebaseToken );


       /* if(!TextUtils.isEmpty(MyApplication.get_session(MyApplication.SESSION_REGISTRATION_NO))){
            edt_retailer_reg_no.setText(MyApplication.get_session(MyApplication.SESSION_REGISTRATION_NO));
        }
        if(!TextUtils.isEmpty(MyApplication.get_session(MyApplication.SESSION_GST_NO))){
            edt_retailer_gst_no.setText(MyApplication.get_session(MyApplication.SESSION_GST_NO));
        }
        if(!TextUtils.isEmpty(MyApplication.get_session(MyApplication.SESSION_LICENSE_NO))){
            edt_retailer_licence_no.setText(MyApplication.get_session(MyApplication.SESSION_LICENSE_NO));
        }*/

    }


    public void initComponantListner() {
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                str_confirm_password = edt_confirm_password.getText() + "".trim();
                str_password = edt_password.getText() + "".trim();
                str_gst_no = edt_retailer_gst_no.getText().toString();
                str_license_no = edt_retailer_licence_no.getText().toString();
                str_reg_no = edt_retailer_reg_no.getText().toString().trim();

                if(str_reg_no.length() != 10){
                    edt_retailer_reg_no.setError("Please Enrer Valid Mobile No.");
                }else if(!isValidPassword(edt_retailer_gst_no.getText().toString())){
                    edt_retailer_gst_no.setError("Please Enrer Valid GST No.");
                //}
//                else{
//                    System.out.println("Valid");

                 }else if (TextUtils.isEmpty(str_license_no)) {
                    edt_retailer_licence_no.setError("Enter Licence No.");
                    return;
                }else if(str_password.length() < 7){
                edt_password.setError("Passwords must be at least 8 characters in length");
                }


             /*   if (TextUtils.isEmpty(str_password)) {
                    edt_password.setError("Enter password.");
                    return;
                }

                if(!hasCapitals(str_password)) {
                    MyApplication.displayMessage(context,"Password must be contains a single character of upper case in it.");
                    return;
                }

                if (str_password.length() != 8  ) {
                    //edt_password.setError("Password must be exactly 6 digits and must be 1 upper case character in it");
                    MyApplication.displayMessage(context,"Password must be exactly of 8 digits");
                    return;
                }


                if (TextUtils.isEmpty(str_confirm_password)) {
                    edt_confirm_password.setError("Enter password again.");
                    return;
                }

                if (TextUtils.isEmpty(str_reg_no)) {
                    edt_retailer_reg_no.setError("Enter Mob No. ");
                    return;
                }
                if (TextUtils.isEmpty(str_license_no)) {
                    edt_retailer_licence_no.setError("Enter Licence No.");
                    return;
                }
                if (edt_retailer_gst_no.length()!=10) {
                    edt_retailer_gst_no.setError("Enter Valid GST No.");
                    return;
                }*/



                else if (str_password.equals(str_confirm_password)) {

                    MyApplication.set_session(MyApplication.SESSION_REGISTRATION_NO, str_reg_no);
                    MyApplication.set_session(MyApplication.SESSION_GST_NO, str_gst_no);
                    MyApplication.set_session(MyApplication.SESSION_LICENSE_NO, str_license_no);
                    MyApplication.set_session(MyApplication.SESSION_PASSWORD, str_password);

                    /*Intent i = new Intent(getApplicationContext(), ActivityVarificationCode.class);
                    finish();
                    overridePendingTransition(R.anim.fade_in_call, R.anim.fade_out_call);
                    startActivity(i);*/

                    MyApplication.logi(LOG_TAG, "brfore call to the OTP");
                    //OTP CALL

/*
                     new HTTPVollyRequest(2, null, 0, "Getting OTP ..Please wait", context,
                        MyApplication.urlGetOtp, getRespListner, null);*/


                    if (MyApplication.isOnline(context)) {

                        btn_next.setActivated(false);
                        btn_next.setClickable(false);

                        MyApplication.logi(LOG_TAG, "INTERNET AVAILABLE");

                        //?user=8551020064&password=&deviceid=1fd1148b7a8d6a44
                        String url = MyApplication.urlGetOtp+ "?user=" + MyApplication.get_session(MyApplication.SESSION_REGISTRATION_NO) + "&password=" + "&deviceid=" +
                                MyApplication.get_session(MyApplication.SESSION_DEVICE_ID);
                        /*String url = MyApplication.urlGetOtp + "?user=" + MyApplication.get_session(MyApplication.SESSION_REGISTRATION_NO) + "&password=" + "&deviceid=" +
                                MyApplication.get_session(MyApplication.SESSION_DEVICE_ID);*/
                        HashMap<String, String> hm = new HashMap<>();
                        hm.put("user", MyApplication.get_session(MyApplication.SESSION_REGISTRATION_NO));
                        //hm.put("password", str_password);
                        hm.put("password", str_password);
                        hm.put("deviceid", MyApplication.get_session(MyApplication.SESSION_DEVICE_ID));


                        new HTTPVollyRequest(1, null, 0, "Please wait.", context,
                                url, getRespListner, hm);

                        MyApplication.logi(LOG_TAG, "after call to the OTP");



                    } else {
                        MyApplication.logi("JARVIS", "in btnclick listern else");
                        showDialogOK(context);
                    }
                } else
                   // Toast.makeText(getApplicationContext(), "Sorry, Passwords are not matching.", Toast.LENGTH_LONG).show();
               MyApplication.displayMessage(context,"Please enter correct password");
           }

        });

        txt_sign_up_link.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ActivityLogin.class);
                finish();
                overridePendingTransition(R.anim.fade_in_call, R.anim.fade_out_call);
                startActivity(i);
            }
        });

        btn_add_more_lic_no.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                str_license_no = edt_retailer_licence_no.getText() + "";
                if (!TextUtils.isEmpty(str_license_no)) {
                    addMoreLicenceNumber(str_license_no.trim());
                } else
                    edt_retailer_licence_no.setError("Enter licence number.");
            }
        });

        show_password.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                togglePassVisability();
                /*Toast.makeText(getApplicationContext(),"RAjani ", Toast.LENGTH_LONG).show();
               edt_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
              //
                edt_password.setInputType(InputType.TYPE_TEXT_VARIATION_NORMAL );*/
            }
        });

        show_Confirm_password.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                togglePassVisabilityConfirm();
             /*   Toast.makeText(getApplicationContext(),"aaa ", Toast.LENGTH_LONG).show();
                edt_confirm_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());*/
            }
        });


    }
    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^([0][1-9]|[1-2][0-9]|[3][0-5])([a-zA-Z]{5}[0-9]{4}[a-zA-Z]{1}[1-9a-zA-Z]{1}[zZ]{1}[0-9a-zA-Z]{1})+$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

    public  void   togglePassVisability() {
        if (isPasswordVisible) {
            edt_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            edt_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
          /*  edt_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            edt_password.setInputType(InputType.TYPE_TEXT_VARIATION_NORMAL);*/
            isPasswordVisible=true;
        }
        else{
            edt_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            edt_password.setInputType(InputType.TYPE_CLASS_TEXT);
        }
        isPasswordVisible= !isPasswordVisible;
    }

    public  void   togglePassVisabilityConfirm() {
        if (isPasswordVisibleconfirm) {
            edt_confirm_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            edt_confirm_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
          /*  edt_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            edt_password.setInputType(InputType.TYPE_TEXT_VARIATION_NORMAL);*/
            isPasswordVisibleconfirm=true;
        }
        else{
            edt_confirm_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            edt_confirm_password.setInputType(InputType.TYPE_CLASS_TEXT);
        }
        isPasswordVisibleconfirm= !isPasswordVisibleconfirm;


    }

    MyListener getRespListner = new MyListener() {

        @Override
        public void success(Object obj) {

            try {
                MyApplication.logi("JARVIS", "getRespListner");
                JSONObject resObj = new JSONObject(obj.toString());
                if (resObj != null && resObj.has("success")) {
                    MyApplication.logi(LOG_TAG, "resp otp is " + resObj.toString());
                    String status = resObj.getString("success");
                    if (status.equalsIgnoreCase("True")) {
                        MyApplication.logi(LOG_TAG, "OTP DATA IS-->" + resObj.getString("data"));
                        otp_data = resObj.getString("data");


                        MyApplication.set_session(MyApplication.SESSION_DATA_OTP, otp_data);

                        MyApplication.logi(LOG_TAG, "Message " + resObj.getString("message"));
                        MyApplication.logi(LOG_TAG, "successss" + status);
                        // showDialogOK("Successfully Register", context, resObj.getString("response_status"));

                        Toast.makeText(getApplicationContext(),"Varification code is successfully sent to "+
                                MyApplication.get_session(MyApplication.SESSION_REGISTRATION_NO), Toast.LENGTH_LONG).show();

                        Intent i = new Intent(getApplicationContext(), ActivityVarificationCode.class);
                       // finish();
                        overridePendingTransition(R.anim.fade_in_call, R.anim.fade_out_call);
                        startActivity(i);


                    } else if (status.equalsIgnoreCase("False")) {
                        MyApplication.logi(LOG_TAG, "unsuccesss" + status);
                        // showDialogOK(resObj.getString("response_message"), context, resObj.getString("response_status"));
                    //    showMessage(context,resObj.getString("message"));
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void failure(Object obj) {
            MyApplication.logi(LOG_TAG, "getRespListner failure ");

           /* final AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);

            dlgAlert.setMessage("Some problem has occured..Please try after 15-20 minuts");


            dlgAlert.setTitle("Zylemini");
            dlgAlert.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            //dismiss the dialog

                        }


                    });
            dlgAlert.setCancelable(false);
            dlgAlert.show();
*/

        }
    };



    public void showMessage(final Context context, String msg) {

        final AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);

        dlgAlert.setMessage(msg);


        dlgAlert.setTitle("Retailer");
        dlgAlert.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //dismiss the dialog
                    }
                });
        dlgAlert.setCancelable(false);
        dlgAlert.show();


    }



    MyListener getUserSaltListener = new MyListener() {

        @Override
        public void success(Object obj) {

            try {
                MyApplication.logi(LOG_TAG, "In getUserSaltListener");
                JSONObject resObj = new JSONObject(obj.toString());
                if (resObj != null && resObj.has("success")) {
                    MyApplication.logi(LOG_TAG, "in successs");
                    MyApplication.logi(LOG_TAG, "resp " + resObj.toString());
                    String status = resObj.getString("success");
                    MyApplication.logi(LOG_TAG, "in status is-->" + status);
                    if (status.equalsIgnoreCase("true")) {
                        MyApplication.logi(LOG_TAG, "Message " + resObj.getString("message"));


                        JSONArray jsonArray = resObj.getJSONArray("RegistraionMaster"); //distributor_Details


                        if (jsonArray.length() > 0) {
                            MyApplication.logi(LOG_TAG, " length > 0  " + jsonArray.length());

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                               /* TABLE_DISTRIBUTER_LIST.insertDistributer(jsonObject.getString("id"),
                                        jsonObject.getString("DistributerName"),
                                        jsonObject.getString("Address"),
                                        jsonObject.getString("ContactNo"),
                                        jsonObject.getString("distributor_id"));*/

/*

                                TABLE_REGISTRATION.insertRegistrationData(jsonObject.getString("Mrunal"),
                                        jsonObject.getString("95555555"), jsonObject.getString("own Mrunal"), jsonObject.getString("000000000"),
                                        jsonObject.getString("ABC PUNE"), jsonObject.getString("411012"), jsonObject.getString("22.12"),
                                        jsonObject.getString("mk@g.com"), jsonObject.getString("GST123"), jsonObject.getString("LI89999"),
                                        jsonObject.getString("IME222"), jsonObject.getString("FC9999"), jsonObject.getString("1234"));

*/

                            }


                        }

                    } else {
                        MyApplication.logi(LOG_TAG, "NO RESPONSE");
                    }

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void failure(Object obj) {

            final AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);

            dlgAlert.setMessage("Please check your internet connection and try again..");


            dlgAlert.setTitle("Retailer");
            dlgAlert.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            //dismiss the dialog

                        }


                    });
            dlgAlert.setCancelable(false);
            dlgAlert.show();


        }
    };


    public void showDialogOK(final Context context) {
        MyApplication.logi(LOG_TAG, "NO INTERNET");
        // MyApplication.log(LOG_TAG, "in showDDOK()message=" + message);
        final AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);

        dlgAlert.setMessage("Please check Your Internet connection and then try...");


        dlgAlert.setTitle("Retailer");
        dlgAlert.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //dismiss the dialog

                    }
                });
        dlgAlert.setCancelable(false);
        dlgAlert.show();


    }


    public void addMoreLicenceNumber(final String str_license_no) {


        LayoutInflater layoutInflater = (LayoutInflater) getBaseContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.popup_add_lic_no, null);
        final PopupWindow popupWindow = new PopupWindow(popupView,
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);

        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);

        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

        CustomTextViewMedium txt_title = popupView.findViewById(R.id.txt_title);
        txt_title.setText(getResources().getString(R.string.add_more_lic_no));

        CustomTextViewMedium txt_cmpl_lic_no = popupView.findViewById(R.id.txt_cmpl_lic_no);
        txt_cmpl_lic_no.setTextColor(getResources().getColor(R.color.red));

        final CustomEditTextMedium edt_licence_no = popupView.findViewById(R.id.edt_licence_no);
        CustomButtonRegular btn_submit = popupView.findViewById(R.id.btn_submit);
        final CustomButtonRegular btn_cancel = popupView.findViewById(R.id.btn_cancel);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String lic_no = edt_licence_no.getText() + "";
                if (!TextUtils.isEmpty(lic_no)) {
                    edt_retailer_licence_no.setText(str_license_no + "," + lic_no.trim());
                    Toast.makeText(getApplicationContext(), edt_licence_no.getText() + "", Toast.LENGTH_LONG).show();
                    popupWindow.dismiss();
                } else
                    edt_licence_no.setError("Enter Licence Number.");
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });

    }

    public boolean hasCapitals(String text) {
        //  only a single character must be Capital
        boolean res = false;
        for(int i=0; i< text.length(); i++){
            char c = text.charAt(i);
            if(c >= 'A' && c <= 'Z') {
                if(res == false)
                  res = true;
                else
                  return false;
            }
        }
        return res;
    }

}




/*package com.sapl.retailerorderingmsdpharma.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;

import com.sapl.retailerorderingmsdpharma.R;

import com.sapl.retailerorderingmsdpharma.customView.CircularTextView;
import com.sapl.retailerorderingmsdpharma.customView.CustomButtonRegular;
import com.sapl.retailerorderingmsdpharma.customView.CustomEditTextMedium;
import com.sapl.retailerorderingmsdpharma.customView.CustomTextViewMedium;
import com.sapl.retailerorderingmsdpharma.customView.CustomTextViewRegular;

public class ActivityRegisterThree extends AppCompatActivity {
    CustomTextViewRegular txt_sign_up_link;
    CircularTextView txt_lable1, txt_lable2, txt_lable3;
    CustomEditTextMedium edt_retailer_reg_no, edt_retailer_gst_no, edt_retailer_licence_no, edt_password, edt_confirm_password;
    CustomButtonRegular btn_next;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_three);

        initComponants();
        initComponantListner();

    }

    public void initComponants() {

        CustomTextViewMedium txt_title = findViewById(R.id.txt_title);
        txt_title.setText(getResources().getString(R.string.sign_up));

        txt_sign_up_link = findViewById(R.id.txt_sign_up_link);

        txt_lable1 = findViewById(R.id.txt_lable1);
        txt_lable2 = findViewById(R.id.txt_lable2);
        txt_lable3 = findViewById(R.id.txt_lable3);

        //txt_lable1.setBackgroundColor(getResources().getColor(R.color.black));
        //txt_lable1.setStrokeColor("#808080");
        txt_lable1.setStrokeColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR));
        txt_lable1.setTextColor(getResources().getColor(R.color.white));
        txt_lable2.setStrokeColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR));
        txt_lable2.setTextColor(getResources().getColor(R.color.white));
        txt_lable3.setStrokeColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR));
        txt_lable3.setTextColor(getResources().getColor(R.color.white));


        edt_password = findViewById(R.id.edt_password);
        edt_confirm_password = findViewById(R.id.edt_confirm_password);
        edt_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        edt_confirm_password.setTransformationMethod(PasswordTransformationMethod.getInstance());

        btn_next = findViewById(R.id.btn_next);

    }

    public void initComponantListner() {
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ActivityVarificationCode.class);
                finish();
                overridePendingTransition(R.anim.fade_in_call, R.anim.fade_out_call);
                startActivity(i);
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

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), ActivityRegisterTwo.class);
        finish();
        overridePendingTransition(R.anim.fade_in_return, R.anim.fade_out_return);
        startActivity(i);
    }
}*/
