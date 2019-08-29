package com.sapl.retailerorderingmsdpharma.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.sapl.retailerorderingmsdpharma.R;
import com.sapl.retailerorderingmsdpharma.customView.CustomButtonRegular;
import com.sapl.retailerorderingmsdpharma.customView.CustomTextViewMedium;

public class ActivityForgotPassword extends AppCompatActivity {
    CustomButtonRegular btn_submit;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        context = this;
        initComponants();
        initComponantsListener();
    }

    private void initComponantsListener() {
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Toast.makeText(context, "This feature is coming soon..", Toast.LENGTH_SHORT).show();
                MyApplication.displayMessage(context,"This feature is coming soon...");
                Intent intent = new Intent(context,ActivityNewPassword.class);
                finish();
                overridePendingTransition(R.anim.fade_in_call, R.anim.fade_out_call);
                startActivity(intent);

            }
        });
    }


    public void initComponants() {
        CustomTextViewMedium txt_title = findViewById(R.id.txt_title);
        txt_title.setText(getResources().getString(R.string.forgot_password_title));
        btn_submit = findViewById(R.id.btn_submit);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(context,ActivityLogin.class);
        overridePendingTransition(R.anim.fade_in_return, R.anim.fade_out_return);
        startActivity(intent);
        finish();
    }
}
