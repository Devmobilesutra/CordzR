package com.sapl.retailerorderingmsdpharma.FirebaseMessage;

import android.provider.Settings;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.sapl.retailerorderingmsdpharma.activities.MyApplication;

import org.json.JSONObject;

public class FCMMessaging extends FirebaseInstanceIdService {



    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        MyApplication.logi("mRUNAL","In token refreshed");
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
       MyApplication.logi("JARVIS", "Refreshed token FOR Zylemini Distributor: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String refreshedToken) {
        MyApplication.logi("JARVIS", " refreshedToken " + refreshedToken);
        JSONObject jsonObject = new JSONObject();
        String deviceId = "";
        deviceId = Settings.Secure.getString(getApplication().getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);

        MyApplication.logi("JARVIS","DEVICE IIIIIDDD-->"+deviceId);
        MyApplication.set_session(MyApplication.SESSION_FCM_ID, refreshedToken);
      //  MyApplication.set_session(MyApplication.SESSION_DEVICE_ID,deviceId);
        MyApplication.logi("JARVIS","FCM iD is -->"+refreshedToken);
        MyApplication.logi("JARVIS","DEevice iD is -->"+deviceId);

    }


}
