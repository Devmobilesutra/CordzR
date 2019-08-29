package com.sapl.retailerorderingmsdpharma.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.iid.FirebaseInstanceId;
import com.sapl.retailerorderingmsdpharma.R;

import com.sapl.retailerorderingmsdpharma.customView.CircularTextView;
import com.sapl.retailerorderingmsdpharma.customView.CustomButtonRegular;
import com.sapl.retailerorderingmsdpharma.customView.CustomEditTextMedium;
import com.sapl.retailerorderingmsdpharma.customView.CustomTextViewMedium;
import com.sapl.retailerorderingmsdpharma.customView.CustomTextViewRegular;

import org.w3c.dom.Text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;




public class ActivityRegisterOne extends AppCompatActivity {

    String LOG_TAG = "ActivityRegisterOne ";
    CircularTextView txt_lable1, txt_lable2, txt_lable3,txt_no_of_product_taken;
    CustomTextViewRegular txt_sign_up_link;
    CustomEditTextMedium edt_shop_name, edt_retailer_owner_name, edt_contact_no, edt_ret_email_id;
    CustomButtonRegular btn_next;
    Context context;
    ImageView img_menu,img_cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_one);
        context = this;

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


        CustomTextViewMedium txt_cmpl_userName = findViewById(R.id.txt_cmpl_userName);
        txt_cmpl_userName.setTextColor(getResources().getColor(R.color.red));
        CustomTextViewMedium txt_cmpl_contact = findViewById(R.id.txt_cmpl_contact);
        txt_cmpl_contact.setTextColor(getResources().getColor(R.color.red));
        CustomTextViewMedium txt_cmpl_ownerName = findViewById(R.id.txt_cmpl_ownerName);

        txt_cmpl_ownerName.setTextColor(getResources().getColor(R.color.red));
        CustomTextViewMedium txt_cmpl_mail = findViewById(R.id.txt_cmpl_mail);
        txt_cmpl_mail.setTextColor(getResources().getColor(R.color.red));

        txt_sign_up_link = findViewById(R.id.txt_sign_up_link);

        txt_lable1 = findViewById(R.id.txt_lable1);
        txt_lable2 = findViewById(R.id.txt_lable2);
        txt_lable3 = findViewById(R.id.txt_lable3);

        //txt_lable1.setBackgroundColor(getResources().getColor(R.color.black));
        //txt_lable1.setStrokeColor("#808080");
        //txt_lable1.setStrokeColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR));
        txt_lable1.setStrokeColor("#006e6e");
        txt_lable1.setTextColor(getResources().getColor(R.color.white));

        txt_lable2.setStrokeColor("#C0C0C0");
        txt_lable2.setTextColor(getResources().getColor(R.color.white));
        txt_lable3.setStrokeColor("#C0C0C0");
        txt_lable3.setTextColor(getResources().getColor(R.color.white));

        edt_shop_name = findViewById(R.id.edt_shop_name);
        edt_contact_no = findViewById(R.id.edt_contact_no);
        edt_ret_email_id = findViewById(R.id.edt_ret_email_id);
        edt_retailer_owner_name = findViewById(R.id.edt_retailer_owner_name);

        btn_next = findViewById(R.id.btn_next);

        String  firebaseToken = FirebaseInstanceId.getInstance().getToken();
        //Toast.makeText(getApplicationContext(),"1St\n  "+firebaseToken, Toast.LENGTH_LONG).show();
        if(TextUtils.isEmpty(firebaseToken)) {
            MyApplication.d(LOG_TAG+ "FCMTOKEN: "+firebaseToken);
            //MyApplication.set_session(MyApplication.SESSION_FCM_TOKEN, firebaseToken);
            //MyApplication.e(LOG_TAG+ "FCMTOKEN: "+MyApplication.get_session(MyApplication.SESSION_FCM_TOKEN));
        }


      /*  if(!TextUtils.isEmpty(MyApplication.get_session(MyApplication.SESSION_SHOP_NAME))){
            edt_shop_name.setText(MyApplication.get_session(MyApplication.SESSION_SHOP_NAME));
        }
        if(!TextUtils.isEmpty(MyApplication.get_session(MyApplication.SESSION_RETAILER_OWNER_NAME))){
            edt_retailer_owner_name.setText(MyApplication.get_session(MyApplication.SESSION_RETAILER_OWNER_NAME));
        }
        if(!TextUtils.isEmpty(MyApplication.get_session(MyApplication.SESSION_CONTACT_NO))){
            edt_contact_no.setText(MyApplication.get_session(MyApplication.SESSION_CONTACT_NO));
        }
        if(!TextUtils.isEmpty(MyApplication.get_session(MyApplication.SESSION_MAIL_ID))){
            edt_ret_email_id.setText(MyApplication.get_session(MyApplication.SESSION_MAIL_ID));
        }*/

    }

    public void initComponantListner() {

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String contactNo = edt_contact_no.getText() + "".trim();
                String userName = edt_shop_name.getText() + "".trim();
                String ownerName = edt_retailer_owner_name.getText() + "".trim();
                String mailId = edt_ret_email_id.getText().toString().trim();

                if(TextUtils.isEmpty(mailId)){
                    edt_ret_email_id.setError("Enter mail address.");
                    //MyApplication.displayMessage(context, "Enter mail address.");
                    return;
                }
                if (TextUtils.isEmpty(ownerName)) {
                    edt_retailer_owner_name.setError("Enter owner name");
                    return;
                }
                if (TextUtils.isEmpty(userName)) {
                    edt_shop_name.setError("Enter shop name");
                    return;
                }

                if(isEmailValid(mailId)) {

                    if (!TextUtils.isEmpty(contactNo) && contactNo.length() == 10) {
                        MyApplication.set_session(MyApplication.SESSION_SHOP_NAME, userName);
                        MyApplication.set_session(MyApplication.SESSION_RETAILER_OWNER_NAME, ownerName);
                        MyApplication.set_session(MyApplication.SESSION_CONTACT_NO, contactNo);
                        MyApplication.set_session(MyApplication.SESSION_MAIL_ID, mailId);

                        Intent i = new Intent(getApplicationContext(), ActivityRegisterTwo.class);
                      //  finish();
                        overridePendingTransition(R.anim.fade_in_call, R.anim.fade_out_call);
                        startActivity(i);
                    } else
                        edt_contact_no.setError("Please enter valid number");
                }
                else
                    MyApplication.displayMessage(context, "Please enter valid mail address");
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
        Intent i = new Intent(getApplicationContext(), ActivityLogin.class);
      //  finish();
        overridePendingTransition(R.anim.fade_in_return, R.anim.fade_out_return);
        startActivity(i);
    }


    public static boolean isEmailValid(String email)
    {
            /*regular expression*/
        String EMAIL_REGEX =
                "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                        "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
       final Pattern EMAIL_PATTERN =  Pattern.compile(EMAIL_REGEX);
        if (email == null)
            return false;

        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }
}