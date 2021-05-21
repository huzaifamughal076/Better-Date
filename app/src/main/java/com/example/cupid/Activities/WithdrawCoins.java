package com.example.cupid.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cupid.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class WithdrawCoins extends AppCompatActivity {

    EditText enter_amount, paypal_email;
    TextView total_amount, coins_available;
    String email;
    Button withdraw_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw_coins);

        enter_amount = findViewById(R.id.enteramount);
        total_amount = findViewById(R.id.convertedamount);
        withdraw_button = findViewById(R.id.withdraw_button);
        paypal_email = findViewById(R.id.paypal_email);
        coins_available = findViewById(R.id.coins_available);

        SharedPreferences sharedPreferences = getSharedPreferences("Questoins", MODE_PRIVATE);
        String userId = sharedPreferences.getString("userid", "");
        ProgressDialog dialog = ProgressDialog.show(WithdrawCoins.this, "",
                "Loading. Please wait...", true);

        String url = "http://api.betterdate.info/endpoints/user.php";
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    dialog.dismiss();
                    JSONArray array = new JSONArray(response);

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject object = array.getJSONObject(i);
                        String coins = object.getString("coins");

                        //  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                        coins_available.setText(coins);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
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
                params.put("userId", userId);
                return params;
            }
        };
        queue.add(request);


        enter_amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() >= 1) {
                    String amount = s.toString();
                    Float enteredamount = Float.parseFloat(amount);

                    Float convertedamount = enteredamount / 2;
                    String total_converted_amount = convertedamount.toString();

                    total_amount.setText(total_converted_amount);
                } else {
                    total_amount.setText("Total Amount");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        withdraw_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = paypal_email.getText().toString().trim();
                String entered_amount = enter_amount.getText().toString().trim();
                if (email.isEmpty()) {
                    paypal_email.setError("Email is required");
                    return;
                } else if (entered_amount.isEmpty()) {
                    enter_amount.setError("Amount required");
                    return;
                } else {
                    ProgressDialog dialog1 = ProgressDialog.show(WithdrawCoins.this, "",
                            "Sending Request. Please wait...", true);
                    String url1 = "http://api.betterdate.info/endpoints/withdraw.php";
                    RequestQueue queue1 = Volley.newRequestQueue(getApplicationContext());
                    StringRequest request1 = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            dialog1.dismiss();
                            try {
                                JSONObject object = new JSONObject(response);
                                String status = object.getString("status");
                                String message = object.getString("message");
                                if (status.equals("true")) {
                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            dialog1.dismiss();
                            Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("userId", userId);
                            params.put("drawPrice", entered_amount);
                            params.put("drawEmail", email);

                            return params;
                        }
                    };
                    queue1.add(request1);


                }
            }
        });
    }
}