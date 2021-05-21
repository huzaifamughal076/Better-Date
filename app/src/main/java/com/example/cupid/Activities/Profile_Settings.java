package com.example.cupid.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cupid.Fragments.ProfileFragment;
import com.example.cupid.R;
import com.google.gson.JsonArray;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Profile_Settings extends AppCompatActivity {

    RelativeLayout withdraw_coins;
    RelativeLayout getPremium;
    RelativeLayout signout;
    RelativeLayout disable_account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile__settings);

        withdraw_coins = findViewById(R.id.withdraw_coins);
        getPremium = findViewById(R.id.getpremium);
        signout = findViewById(R.id.signout);
        disable_account = findViewById(R.id.disable_account);


        SharedPreferences sharedPreferences = getSharedPreferences("Questoins", MODE_PRIVATE);
        String userId = sharedPreferences.getString("userid", "");

        disable_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "http://api.betterdate.info/endpoints/user.php";
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            String status = object.getString("status");
                            String message = object.getString("message");

                            if (status.equals("true")) {
                                Intent i = new Intent(Profile_Settings.this, LoginActivity.class);
                                startActivity(i);
                                finish();
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("delId", userId);
                        return params;
                    }
                };
                queue.add(request);


            }
        });

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Profile_Settings.this, LoginActivity.class);
                startActivity(i);
                finish();
            }

        });
        withdraw_coins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Profile_Settings.this, WithdrawCoins.class);
                startActivity(i);
            }
        });
        getPremium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Premium Services are not availabe at the moment", Toast.LENGTH_LONG).show();
            }
        });
    }
}