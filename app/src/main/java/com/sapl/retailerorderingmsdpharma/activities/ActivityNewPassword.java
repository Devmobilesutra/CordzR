package com.sapl.retailerorderingmsdpharma.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.sapl.retailerorderingmsdpharma.R;
import com.sapl.retailerorderingmsdpharma.customView.CustomButtonRegular;
import com.sapl.retailerorderingmsdpharma.customView.CustomTextViewMedium;

public class ActivityNewPassword extends AppCompatActivity {
    CustomButtonRegular btn_update;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);
        context = this;
        initComponants();
        initComponantsListener();
    }


    private void initComponants() {
        CustomTextViewMedium txt_title = findViewById(R.id.txt_title);
        txt_title.setText("New Password");
        btn_update = findViewById(R.id.btn_update);
    }

    private void initComponantsListener() {

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Toast.makeText(context, "This feature is coming soon..", Toast.LENGTH_SHORT).show();
                MyApplication.displayMessage(context, "This feature is coming soon...");

            }
        });
    }
}
