package com.sapl.retailerorderingmsdpharma.activities;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import com.sapl.retailerorderingmsdpharma.R;
import com.sapl.retailerorderingmsdpharma.ServerCall.HTTPVollyRequest;
import com.sapl.retailerorderingmsdpharma.ServerCall.MyListener;
import com.sapl.retailerorderingmsdpharma.customView.CustomButtonBold;
import com.sapl.retailerorderingmsdpharma.customView.CustomButtonRegular;
import com.sapl.retailerorderingmsdpharma.customView.CustomEditTextMedium;
import com.sapl.retailerorderingmsdpharma.customView.CustomTextViewMedium;
import com.sapl.retailerorderingmsdpharma.customView.CustomTextViewRegular;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class ActivityVarificationCode extends AppCompatActivity {

    CustomTextViewRegular txt_varification_warning, txt_resend;
    CustomEditTextMedium edt_varification_code;
    CustomButtonRegular btn_submit;
    Context context;

    String veri_code = "", firebaseToken = "";
    static String LOG_TAG = "ActivityVarificationCode";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_varification_code);
        context = this;

        firebaseToken = FirebaseInstanceId.getInstance().getToken();
        //Toast.makeText(getApplicationContext(),"1St\n  "+firebaseToken, Toast.LENGTH_LONG).show();
        if (TextUtils.isEmpty(firebaseToken)) {
            MyApplication.d(LOG_TAG + "FCMTOKEN: " + firebaseToken);
            //MyApplication.set_session(MyApplication.SESSION_FCM_TOKEN, firebaseToken);
            //MyApplication.e(LOG_TAG+ "FCMTOKEN: "+MyApplication.get_session(MyApplication.SESSION_FCM_TOKEN));
        }

        initComponants();
        initComponantListner();

    }


    public void initComponants() {
        CustomTextViewMedium txt_title = findViewById(R.id.txt_title);
        txt_title.setText(getResources().getString(R.string.sign_up));

        txt_varification_warning = findViewById(R.id.txt_varification_warning);
        txt_varification_warning.setText(getResources().getString(R.string.varification_warning)
                + "\n" + MyApplication.get_session(MyApplication.SESSION_REGISTRATION_NO));

        txt_resend = findViewById(R.id.txt_resend);

        edt_varification_code = findViewById(R.id.edt_varification_code);
        btn_submit = findViewById(R.id.btn_submit);
    }


    public void initComponantListner() {

        txt_resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MyApplication.isOnline(context)) {

                    //?user=8551020064&password=&deviceid=1fd1148b7a8d6a44
                    String url = MyApplication.urlGetOtp + "?user=" + MyApplication.get_session(MyApplication.SESSION_REGISTRATION_NO) + "&password=" + "&deviceid=" +
                            MyApplication.get_session(MyApplication.SESSION_DEVICE_ID);
                        /*String url = MyApplication.urlGetOtp + "?user=" + MyApplication.get_session(MyApplication.SESSION_REGISTRATION_NO) + "&password=" + "&deviceid=" +
                                MyApplication.get_session(MyApplication.SESSION_DEVICE_ID);*/
                    HashMap<String, String> hm = new HashMap<>();
                    hm.put("user", MyApplication.get_session(MyApplication.SESSION_REGISTRATION_NO));
                    //hm.put("password", str_password);
                    hm.put("password", "");
                    hm.put("deviceid", MyApplication.get_session(MyApplication.SESSION_DEVICE_ID));


                    new HTTPVollyRequest(1, null, 0, "Please wait.", context,
                            url, getOTPListner, hm);
                } else
                    MyApplication.noInterNetDialog(context);

            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!TextUtils.isEmpty(firebaseToken) && !("" + firebaseToken).equals("")) {

                    veri_code = edt_varification_code.getText().toString();
                    MyApplication.logi(LOG_TAG, "veri_code ID-->" + veri_code);
                    String veri_code_converted = MD5(veri_code);

                    MyApplication.logi(LOG_TAG, "veri_code_converted ID-->" + veri_code_converted);
                    MyApplication.logi("otp from session -->>>" + (MyApplication.get_session(MyApplication.SESSION_DATA_OTP)), "");
                    MyApplication.logi("otp from md5 function -->>>" + veri_code_converted, "");
                    if (veri_code_converted.equalsIgnoreCase(MyApplication.get_session(MyApplication.SESSION_DATA_OTP))) {

                        MyApplication.logi(LOG_TAG, "veri_code_converted ID is correct -->");
                        //CALL THE REGISTRATION WEB SERVICE HERE

                        MyApplication.logi(LOG_TAG, "brfore call to the urlRegister");

                        JSONObject jsonobj1 = new JSONObject();

                        JSONArray jsonarr = new JSONArray();

                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("Name", MyApplication.get_session(MyApplication.SESSION_SHOP_NAME));
                            jsonObject.put("RegMobileNo", MyApplication.get_session(MyApplication.SESSION_REGISTRATION_NO));
                            jsonObject.put("OwnerName", MyApplication.get_session(MyApplication.SESSION_RETAILER_OWNER_NAME));
                            jsonObject.put("ContactNo", MyApplication.get_session(MyApplication.SESSION_CONTACT_NO));
                            jsonObject.put("Address", MyApplication.get_session(MyApplication.SESSION_AREA));
                            jsonObject.put("AreaPinCode", MyApplication.get_session(MyApplication.SESSION_PIN));
                            jsonObject.put("GeoTagging", MyApplication.get_session(MyApplication.SESSION_LOCATION));
                            MyApplication.logi(LOG_TAG,"GEO TAGGINF IS--->"+MyApplication.get_session(MyApplication.SESSION_LOCATION));
                            jsonObject.put("EmailID", MyApplication.get_session(MyApplication.SESSION_MAIL_ID));
                            jsonObject.put("GSTNo", MyApplication.get_session(MyApplication.SESSION_GST_NO));
                            jsonObject.put("LicenceNo", MyApplication.get_session(MyApplication.SESSION_LICENSE_NO));
                            jsonObject.put("IMEINo", MyApplication.get_session(MyApplication.SESSION_DEVICE_ID));
                            //jsonObject.put("FcmToken", MyApplication.get_session(MyApplication.SESSION_FCM_TOKEN));


                            jsonObject.put("FcmToken", firebaseToken);
                            MyApplication.e(LOG_TAG + "FCMTOKEN: " + firebaseToken);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }




                        jsonarr.put(jsonObject);
                        try {
                            jsonobj1.put("RegistraionMaster", jsonarr);
                            jsonarr = new JSONArray();
                            jsonObject = new JSONObject();
                            jsonObject.put("clientId", 1);
                            jsonarr.put(jsonObject);
                            jsonobj1.put("data", jsonarr);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        MyApplication.logi(LOG_TAG, "jsonObject FOR REGISTRATION IS 1 is---->" + jsonobj1);
                        new HTTPVollyRequest(2, jsonobj1, 0, "Please wait.", context, MyApplication.urlRegisterRetailer,
                                getRegisterRetailerResponse, null);


                        MyApplication.logi(LOG_TAG, "jsonObject FOR REGISTRATION IS---->" + jsonObject);
                        MyApplication.logi(LOG_TAG, "getRegisterRetailerResponse--->" + getRegisterRetailerResponse.toString());
                        MyApplication.logi(LOG_TAG, "after call to the urlRegister");


                    } else {
                        MyApplication.logi(LOG_TAG, "INCORRECT veri_code_converted ID is correct -->");
                        Toast.makeText(getApplicationContext(), "Please enter valid OTP..", Toast.LENGTH_SHORT).show();

                    }
                } else
                    MyApplication.displayMessage(context, "Please check your internet connection and try again..");

            }
        });

    }


    MyListener getRegisterRetailerResponse = new MyListener() {

        @Override
        public void success(Object obj) {

            try {
                MyApplication.logi(LOG_TAG, "In getRegisterRetailerResponse");
                JSONObject resObj = new JSONObject(obj.toString());
                if (resObj != null && resObj.has("success")) {
                    MyApplication.logi(LOG_TAG, "in successs");
                    MyApplication.logi(LOG_TAG, "resp " + resObj.toString());
                    String status = resObj.getString("success");
                    MyApplication.logi(LOG_TAG, "in status is-->" + status);
                    if (status.equalsIgnoreCase("true")) {
                        MyApplication.logi(LOG_TAG, "Message " + resObj.getString("message"));
                        MyApplication.logi(LOG_TAG, "EREEOR " + resObj.getString("error"));

                        showMessage(context, resObj.getString("message"));

                     /*   MyApplication.set_session(MyApplication.SESSION_SHOP_NAME,"");
                        MyApplication.set_session(MyApplication.SESSION_REGISTRATION_NO,"");
                        MyApplication.set_session(MyApplication.SESSION_RETAILER_OWNER_NAME,"");
                        MyApplication.set_session(MyApplication.SESSION_CONTACT_NO,"");
                        MyApplication.set_session(MyApplication.SESSION_AREA,"");
                        MyApplication.set_session(MyApplication.SESSION_PIN,"");
                        MyApplication.set_session(MyApplication.SESSION_LOCATION,"");
                        MyApplication.set_session(MyApplication.SESSION_MAIL_ID,"");
                        MyApplication.set_session(MyApplication.SESSION_GST_NO,"");
                        MyApplication.set_session(MyApplication.SESSION_LICENSE_NO,"");
                        MyApplication.set_session(MyApplication.SESSION_DEVICE_ID,"");*/


                    } else {
                        MyApplication.logi(LOG_TAG, "NO RESPONSE");
                        showMessage(context, resObj.getString("message"));

                        /*MyApplication.set_session(MyApplication.SESSION_SHOP_NAME,"");
                        MyApplication.set_session(MyApplication.SESSION_REGISTRATION_NO,"");
                        MyApplication.set_session(MyApplication.SESSION_RETAILER_OWNER_NAME,"");
                        MyApplication.set_session(MyApplication.SESSION_CONTACT_NO,"");
                        MyApplication.set_session(MyApplication.SESSION_AREA,"");
                        MyApplication.set_session(MyApplication.SESSION_PIN,"");
                        MyApplication.set_session(MyApplication.SESSION_LOCATION,"");
                        MyApplication.set_session(MyApplication.SESSION_MAIL_ID,"");
                        MyApplication.set_session(MyApplication.SESSION_GST_NO,"");
                        MyApplication.set_session(MyApplication.SESSION_LICENSE_NO,"");
                        MyApplication.set_session(MyApplication.SESSION_DEVICE_ID,"");*/
                    }

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void failure(Object obj) {

            MyApplication.displayMessage(context, "Please check your internet connection and try again..");
/*

            final AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);

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


    @Override
    public void onBackPressed() {
        MyApplication.displayMessage(context, "Sorry, You can not go back.");
       /* Intent i = new Intent(getApplicationContext(), ActivityRegisterThree.class);
        finish();
        overridePendingTransition(R.anim.fade_in_return, R.anim.fade_out_return);
        startActivity(i);
        */

    }

//code used for convertion
    public String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

    MyListener getOTPListner = new MyListener() {

        @Override
        public void success(Object obj) {

            try {
                MyApplication.logi(LOG_TAG, "getRespListner");
                JSONObject resObj = new JSONObject(obj.toString());
                if (resObj != null && resObj.has("success")) {
                    MyApplication.logi(LOG_TAG, "resp otp is " + resObj.toString());
                    String status = resObj.getString("success");
                    if (status.equalsIgnoreCase("True")) {
                        MyApplication.logi(LOG_TAG, "OTP DATA IS-->" + resObj.getString("data"));
                        String otp_data = resObj.getString("data");

                        MyApplication.set_session(MyApplication.SESSION_DATA_OTP, otp_data);

                        MyApplication.logi(LOG_TAG, "Message " + resObj.getString("message"));
                        MyApplication.logi(LOG_TAG, "successss" + status);
                        // showDialogOK("Successfully Register", context, resObj.getString("response_status"));

                        Toast.makeText(getApplicationContext(), "Varification code is successfully sent to " +
                                MyApplication.get_session(MyApplication.SESSION_REGISTRATION_NO), Toast.LENGTH_LONG).show();

                        /*Intent i = new Intent(getApplicationContext(), ActivityVarificationCode.class);
                        finish();
                        overridePendingTransition(R.anim.fade_in_call, R.anim.fade_out_call);
                        startActivity(i);*/


                    } else if (status.equalsIgnoreCase("False")) {
                        MyApplication.logi(LOG_TAG, "unsuccesss" + status);
                        // showDialogOK(resObj.getString("response_message"), context, resObj.getString("response_status"));
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void failure(Object obj) {
            MyApplication.logi(LOG_TAG, "getRespListner failure ");
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
                        Intent i = new Intent(getApplicationContext(), ActivityLogin.class);

                         /*MyApplication.set_session(MyApplication.SESSION_SHOP_NAME,"");
                        MyApplication.set_session(MyApplication.SESSION_REGISTRATION_NO,"");
                        MyApplication.set_session(MyApplication.SESSION_RETAILER_OWNER_NAME,"");
                        MyApplication.set_session(MyApplication.SESSION_CONTACT_NO,"");
                        MyApplication.set_session(MyApplication.SESSION_AREA,"");
                        MyApplication.set_session(MyApplication.SESSION_PIN,"");
                        MyApplication.set_session(MyApplication.SESSION_LOCATION,"");
                        MyApplication.set_session(MyApplication.SESSION_MAIL_ID,"");
                        MyApplication.set_session(MyApplication.SESSION_GST_NO,"");
                        MyApplication.set_session(MyApplication.SESSION_LICENSE_NO,"");
                        MyApplication.set_session(MyApplication.SESSION_DEVICE_ID,"");*/


                        finish();
                        overridePendingTransition(R.anim.fade_in_call, R.anim.fade_out_call);
                        startActivity(i);

                    }
                });
        dlgAlert.setCancelable(false);
        dlgAlert.show();


    }

}















