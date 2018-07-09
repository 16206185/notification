package com.example.bf6193.notificationdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import android.provider.Settings.Secure;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    RequestQueue queue;
    String Nid = "", Uid = "",Token ="",androidId="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        queue = Volley.newRequestQueue(this);
        androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        if(SaveSharedPreference.getPrefToken(MainActivity.this).length() == 0)
        {
            SaveSharedPreference.setPrefUserId(MainActivity.this,"U1001");
        }



        Bundle extras = getIntent().getExtras();
        if (extras != null) {
             Nid = (String)extras.get("Nid");
            if (Nid!=null) {
                sendClickedToServer(Nid);
            }
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        String msg = intent.getStringExtra("msg");
        if (msg!=null)
            Log.d("FCM", "msg:"+msg);
    }

    public void sendClickedToServer(String rNid) {
        Nid = rNid;
        Uid = SaveSharedPreference.getPrefUserId(MainActivity.this);

        String url = "http://192.9.204.143/returnNotifcChecked.php";
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
                params.put("Nid", Nid);
                params.put("Uid", Uid);
                return params;
            }
        };
        queue.add(postRequest);
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_U1:
                if (checked) {

                    //logoutDevice();
                    SaveSharedPreference.setPrefUserId(MainActivity.this,"U1001");
                    loginDevice();
                }
                break;
            case R.id.radio_U2:
                if (checked){
                   // logoutDevice();
                    SaveSharedPreference.setPrefUserId(MainActivity.this,"U1002");
                    loginDevice();
                }
                    break;
            case R.id.radio_U3:
                if (checked){
                   //  logoutDevice();
                    SaveSharedPreference.setPrefUserId(MainActivity.this,"U1003");
                    loginDevice();
                }

                    break;
        }
    }


    public void loginDevice() {
        Uid = SaveSharedPreference.getPrefUserId(MainActivity.this);

        String url = "http://192.9.204.143/loginDevice.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            Log.d("myTag", jsonObj.toString());
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
                params.put("UsingUid", Uid);
                return params;
            }
        };
        queue.add(postRequest);
    }

    public void logoutDevice() {

        Uid = SaveSharedPreference.getPrefUserId(MainActivity.this);

        String url = "http://192.9.204.143/logoutDevice.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            Log.d("myTag", jsonObj.toString());
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
                params.put("UsingUid", Uid);
                return params;
            }
        };
        queue.add(postRequest);
    }




}
