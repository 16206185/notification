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
    String Nid = "", Uid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        queue = Volley.newRequestQueue(this);


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
        Uid = ((UserInformation) this.getApplication()).getUid();
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
                    ((UserInformation) this.getApplication()).setUid("U1");
                    Log.d("Uid",((UserInformation) this.getApplication()).getUid());
                }
                break;
            case R.id.radio_U2:
                if (checked){
                    ((UserInformation) this.getApplication()).setUid("U2");
                    Log.d("Uid",((UserInformation) this.getApplication()).getUid());
                }

                    break;
            case R.id.radio_U3:
                if (checked){
                    ((UserInformation) this.getApplication()).setUid("U3");
                    Log.d("Uid",((UserInformation) this.getApplication()).getUid());
                }

                    break;
        }
    }



}
