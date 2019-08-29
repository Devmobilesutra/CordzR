package com.sapl.retailerorderingmsdpharma.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sapl.retailerorderingmsdpharma.R;

//not used yet
public class SplashScreen extends AppCompatActivity {


    private final int splashTime = 3000; // time to display the splash screen in ms
    private Context context = null;
    private boolean active = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        context = this;

        bindComponentData();

    }

    private void bindComponentData() {

        Thread splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while (active && (waited < splashTime)) {
                        sleep(100);
                        if (active) {
                            waited += 100;
                        }
                    }
                } catch (InterruptedException e) {
                    // do nothing
                } finally {

                    Intent intent = new Intent( getApplicationContext(), ActivityDashBoard.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.fade_in_call, R.anim.fade_out_call);
                }
            }
        };
        splashTread.start();
    }

}
