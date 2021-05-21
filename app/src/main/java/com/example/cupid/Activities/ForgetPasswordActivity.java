package com.example.cupid.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cupid.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ForgetPasswordActivity extends AppCompatActivity {

    EditText email;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

    email=findViewById(R.id.forget_email);
    submit=findViewById(R.id.forget_submit);

    RequestQueue queue= Volley.newRequestQueue(this);

    submit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String Email=email.getText().toString().trim();
            String url="http://api.betterdate.info/endpoints/forget.php";

            if (Email.isEmpty()){
                email.setError("Email Required");
                return;
            }else {
                StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject object=new JSONObject(response);
                            String status=object.getString("status");
                            String message=object.getString("message");

                            if (status.equals("true")){
                                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                                Intent i=new Intent(ForgetPasswordActivity.this,LoginActivity.class);
                                startActivity(i);
                                finish();
                            }
                            else {
                                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("email", Email);
                        return params;
                    }
                };
                queue.add(request);
            }
        }
    });
    }
}