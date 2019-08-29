package com.sapl.retailerorderingmsdpharma.ServerCall;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sapl.retailerorderingmsdpharma.activities.MyApplication;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by JARVIS on 10/31/2017.
 */

public class HTTPVollyRequest {

    private String auth;
    Context mContext;
    HashMap<String, String> map;
    MyListener listener;
    //LTFoodApp app;
    JSONObject jsonRequest;
    private RequestQueue mRequestQueue;
    String requestUrl;
    int showMessage = 0; // 0 = show Progress Dialog,// 1 = dont show;
    private final int initTimeout = 300 * 1000;
    String strMessage = "";
    JSONObject jsonObject;
    static String LOG_TAG = "HTTPVollyRequest";

    HashMap<String, String> hashMap = null;
    int flag = 0; //1 = String Request, 2 = JsonObject Request

    public HTTPVollyRequest(int flag, JSONObject jsonObject, int showMessage, String strMessage, Context context, String req_url,
                            MyListener listener, HashMap<String,String> hashMap) {
        Log.i(LOG_TAG, "in HTTPVollyRequest");
        this.mContext = context;
        MyApplication.logi("HTTPVollyRequest","conntext->"+context);
        MyApplication.logi("HTTPVollyRequest","mContext->"+mContext);
        this.requestUrl = req_url;
        this.strMessage = strMessage;
        this.listener = listener;
        this.jsonObject = jsonObject;
        this.flag = flag;
        this.hashMap = hashMap;
        this.showMessage = showMessage;
            doPostOperation(showMessage, strMessage);
      /*  if(showMessage == 0){
            doPostOperation();
        }else if(showMessage == 1) {
            doPostOperation();
        }*/
    }


    public void doPostOperation(int showMessage, String message) {
        if (showMessage == 0) {
            MyApplication.showDialog(mContext, message);
        }
        MyApplication.logi("JARVIS doPostOperation 2", "");
        MyApplication.logi(LOG_TAG,"requestUrl"+requestUrl);
        MyApplication.logi(LOG_TAG,"listenerSuccess"+listenerSuccess);
        MyApplication.logi(LOG_TAG,"listenerError"+listenerError);
        if (flag == 1) {   //FOR GET
            MyApplication.logi(LOG_TAG,"IN FLAG == 1");
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                    requestUrl,
                    null,
                    listenerSuccessJson,
                    listenerError)
            {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    HashMap<String, String> headers = new HashMap<String, String>();
                    //Log.e("Auth", auth);
                    headers.put("Content-Type", "application/json; charset=utf-8");

                 //   MyApplication.logi("JARVIS"," parameters:--->"+hashMap.toString());

                   // if(hashMap != null)
                        return headers;
                }
            };
            request.setTag("get");
            request.setRetryPolicy(new DefaultRetryPolicy(initTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            getRequestQueue().add(request);
        } else if (flag == 2) {  //FOR POST
            MyApplication.logi(LOG_TAG,"IN FLAG == 2");
            MyApplication.logi(LOG_TAG, "MRUNAL    obj is" + jsonObject.toString());
            MyApplication.logi("MRUNAL","URL ISS-->"+requestUrl);
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, requestUrl,
                    jsonObject,
                    listenerSuccessJson,
                    listenerError);
            MyApplication.logi(LOG_TAG, "request is" + request.toString());
            //HttpsTrustManager.allowAllSSL();
            request.setTag("post");
            request.setRetryPolicy(new DefaultRetryPolicy(initTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            getRequestQueue().add(request);

        }


    }


    public RequestQueue getRequestQueue() {
        Log.i(LOG_TAG, "in after getRequestQueue");
        if (mRequestQueue == null) {
            MyApplication.logi("HTTPVollyRequest","getRequestQueue()->"+mContext);
            Log.i(LOG_TAG, "in after getRequestQueue in if");
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        Log.i(LOG_TAG, "in after getRequestQueue mRequestQueue " + mRequestQueue);
        return mRequestQueue;
    }




    Response.Listener<String> listenerSuccess = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            MyApplication.stopLoading();
            listener.success(response);
        }


    };

    Response.Listener<JSONObject> listenerSuccessJson = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            MyApplication.stopLoading();
            listener.success(response);
        }


    };

    Response.ErrorListener listenerError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError e) {
            MyApplication.stopLoading();
            MyApplication.logi(LOG_TAG,"ERROR OF VOLLEY------>>"+e.networkResponse+" E IS---->"+e.toString());
            listener.failure(e.networkResponse);

        }
    };

}
