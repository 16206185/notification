package com.example.bf6193.notificationdemo;

import android.provider.Settings;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class MyInstanceIDService extends FirebaseInstanceIdService {
    RequestQueue queue;
    String NotificationToken = "";
    String androidId = "";
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        queue = Volley.newRequestQueue(this);
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        SaveSharedPreference.setPrefUserId(this,refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);
    }

    public void sendRegistrationToServer(String refreshedToken) {
        NotificationToken = refreshedToken;
        androidId = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);

        String url = "http://192.9.204.143/returnNotifcToken.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            Log.d("myTag", jsonObj.toString());

                            if (jsonObj.getString("success").equals("1")) {
                                Log.d("Yse","1111111111");
                            } else {
                                Log.d("No","22222222222");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", "ERROR");
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("DeviceId", androidId);
                params.put("NotificationToken", NotificationToken);
                return params;
            }
        };
        queue.add(postRequest);
    }
}
