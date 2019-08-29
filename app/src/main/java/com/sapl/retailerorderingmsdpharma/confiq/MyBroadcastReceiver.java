package com.sapl.retailerorderingmsdpharma.confiq;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.sapl.retailerorderingmsdpharma.FirebaseMessage.FirebaseService;
import com.sapl.retailerorderingmsdpharma.activities.MyApplication;

/**
 * Created by JARVIS on 10/30/2017.
 */

public class MyBroadcastReceiver extends BroadcastReceiver {
    public static String LOG_TAG = "MyBroadcastReceiver ";

    @Override
    public void onReceive(Context context, Intent intent) {
        MyApplication.logi(LOG_TAG+ " in ", "oRecieve()   ...");

        Bundle dataBundle = intent.getBundleExtra("data");
        // MyApplication.logi(LOG_TAG+ " in ", "oRecieve()  dataBundle: "+ dataBundle.toString());

        Intent intent1 = new Intent(Intent.ACTION_SYNC, null, context, FirebaseService.class);
        //intent1.putExtra(LOG_TAG+" Flag", "date_changed");
        intent1.putExtra("data", dataBundle);
        context.startService(intent1);
    }
}