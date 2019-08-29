package com.sapl.retailerorderingmsdpharma.FirebaseMessage;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.sapl.retailerorderingmsdpharma.MyDatabase.TABLE_ORDER_STATUS;
import com.sapl.retailerorderingmsdpharma.MyDatabase.TABLE_RETAILER_ORDER_MASTER;
import com.sapl.retailerorderingmsdpharma.R;
import com.sapl.retailerorderingmsdpharma.activities.ActivityDashBoard;
import com.sapl.retailerorderingmsdpharma.activities.MyApplication;
import com.sapl.retailerorderingmsdpharma.confiq.NotificationUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.R.id.message;


public class FirebaseService extends FirebaseMessagingService {
    Context context = this;
    public String LOG_TAG = "RETAILERFirebaseService ";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

//        context = this;
        Log.d("JARVIS", "onMessageReceived data payload: ");
        // TODO(developer): Handle FCM messages here.
        // Check if message contains a data payload.
        MyApplication.logi("RETAILRER_NOTIFICATION", "onMessageReceived() From:--> " + remoteMessage.getFrom());
        if (remoteMessage == null)
            return;
        if (remoteMessage.getData().size() > 0) {
            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                MyApplication.logi("RETAILRER_NOTIFICATION", "RETAILRER_NOTIFICATION responce is  ->>>>>>>>>>>>>" + remoteMessage.getData().toString());

                handleDataMessage(json);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {


        }
    }

    private void handleDataMessage(JSONObject json) {
        MyApplication.logi("RETAILRER_NOTIFICATION", "RETAILRER_NOTIFICATION push json: " + json.toString());
        String notification_flag = "";
        Intent resultIntent = null;
        String message_event = "";
        String title = "";
        String message = "";
        String data_new = "";
        try {
            JSONObject root = json.getJSONObject("JSONResult");
            MyApplication.logi(LOG_TAG, "handleDataMessage(),  root-->" + root.toString() + "\n.");
            notification_flag = root.getString("Notification_flag");
            message = root.getString("message");
            MyApplication.logi(LOG_TAG, "handleDataMessage(),  message-->" + message);
            MyApplication.logi(LOG_TAG, "handleDataMessage(), notification_flag: " + notification_flag);

            if (notification_flag.equalsIgnoreCase("Order_Status")) {
                MyApplication.logi(LOG_TAG, "handleDataMessage() CHECKING FLAGS: Order_Status --->  TRUE");

                JSONObject jsonOrderData = root.getJSONObject("data");
                MyApplication.logi(LOG_TAG, "handleDataMessage(),  jsonOrderData-->" + jsonOrderData.toString() + "\n.");


                JSONArray orderStatus = jsonOrderData.getJSONArray("OrderStatus");


                MyApplication.logi(LOG_TAG, "handleDataMessage(),  OrderStatus-->" + orderStatus.toString());


                TABLE_RETAILER_ORDER_MASTER.updateStatusId(orderStatus);
                TABLE_ORDER_STATUS.insert_bulk_OrderStatus_new(orderStatus);
            }

            generateNotification(message, notification_flag);


        } catch (JSONException e) {
            MyApplication.logi(LOG_TAG, "handleDataMessage() JSONException ERROR  " + e.getMessage());

        } catch (Exception e) {
            MyApplication.logi(LOG_TAG, "handleDataMessage() Exception ERROR  " + e.getMessage());
        }

    }


    private void generateNotification(String str_message, String titleFlag) {
        MyApplication.logi(LOG_TAG + " in generateNotification()\n", "titl->" + titleFlag + ",  messahe " + message);
        String subtitle = str_message;
        Intent intent;
        NotificationManager mNotificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        android.support.v4.app.NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setContentTitle(titleFlag).setAutoCancel(true)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI);


        //check here the notification flag

        intent = new Intent(getApplicationContext(), ActivityDashBoard.class);
        intent.putExtra("message", message);
        intent.putExtra("title", titleFlag);
        intent.setFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
        mBuilder.setTicker(subtitle);

        mBuilder.setContentText(subtitle);
        mBuilder.setSmallIcon(R.mipmap.msd_logo);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 3, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(3, mBuilder.build());
    }


    private void showNotificationMessage(Context context, String title, String message, int timeStamp, Intent intent) {
        MyApplication.logi("RETAILRER_NOTIFICATION", "RETAILRER_NOTIFICATION WALLET In showNotificationMessage()-->" + intent.getExtras().getString("title"));
        intent.getExtras().getString("title");
        NotificationUtils notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
    }

    static void broadcast(Context context, String flag, Bundle bundle) {
        MyApplication.logi("RETAILRER_NOTIFICATION", "RETAILRER_NOTIFICATION in update activity->" + flag);
        Intent intent = new Intent(flag);
        intent.putExtras(bundle);
        context.sendBroadcast(intent);

    }

    private void generateSendOrderNotification(String str_message, String title) {
        MyApplication.logi("RETAILRER_NOTIFICATION" + " RETAILRER_NOTIFICATION in generateSendOrderNotification()", "*************titl->" + title + " messahe " + message);
        // String title = str_message;
        String subtitle = str_message;
        Intent intent;
        NotificationManager mNotificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        android.support.v4.app.NotificationCompat.Builder mBuilder = new android.support.v4.app.NotificationCompat.Builder(context)
                .setContentTitle(title).setAutoCancel(true)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI);

        //intent = new Intent(context, ActivityListRow.class);
        intent = new Intent(context, ActivityDashBoard.class);
        intent.putExtra("message", message);
        intent.putExtra("title", title);
        intent.setFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
        mBuilder.setTicker(subtitle);

        mBuilder.setContentText(subtitle);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 3, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(3, mBuilder.build());
    }

}
