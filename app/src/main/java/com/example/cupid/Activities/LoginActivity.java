package com.example.cupid.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cupid.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText password, username;
    CheckBox checkBox;
    TextView click_here;
    ImageView img;
    Button Login;

    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        img = findViewById(R.id.logocup);
        password = findViewById(R.id.password);
        username = findViewById(R.id.username);
        checkBox = findViewById(R.id.showpass);
        click_here = findViewById(R.id.move_to_signup);
        Login = findViewById(R.id.login);

        queue = Volley.newRequestQueue(this);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email, passwor;

                email = username.getText().toString().trim();
                passwor = password.getText().toString().trim();

                if (email.isEmpty()) {
                    username.setError("Enter email/username");
                    return;
                } else if (passwor.isEmpty()) {
                    password.setError("Password required");
                    return;
                } else {

                    login(email, passwor);

                }
            }
        });


        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()) {
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        click_here.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it = new Intent(getApplicationContext(), SignUpActivity.class);


                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    Pair[] pairs = new Pair[1];
                    pairs[0] = new Pair<View, String>(img, "ok");
                    ActivityOptions anim = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this, pairs);
                    startActivity(it, anim.toBundle());
                    finish();
                }
            }
        });
    }

    public void login(String email, String Password) {

        String login_url = "http://api.betterdate.info/endpoints/signin.php";

        StringRequest request = new StringRequest(Request.Method.POST, login_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject object = new JSONObject(response);
                    String status = object.getString("status");
                    String message = object.getString("message");

                    if (status.equals("false") && message.equals("No User Exists")) {
                        username.setError("No such user Found");
                        return;
                    }
                    if (status.equals("false") && message.equals("Wrong Password")) {
                        password.setError("Wrong Password");
                        return;
                    }
                    if (status.equals("true") && message.equals("SignIn Successfull")) {
                        String respons = object.getString("response");

                        JSONObject object1 = new JSONObject(respons);
                        String userId = object1.getString("userid");

                        SharedPreferences.Editor editor = getSharedPreferences("Questoins", MODE_PRIVATE).edit();
                        editor.putString("userid", userId);
                        editor.apply();



                        Intent i = new Intent(LoginActivity.this, HomeScreen.class);
                        startActivity(i);
                        finish();

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
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
                params.put("email", email);
                params.put("password", Password);
                return params;
            }
        };
        queue.add(request);

    }
}