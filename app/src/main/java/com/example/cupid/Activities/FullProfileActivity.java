package com.example.cupid.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class FullProfileActivity extends AppCompatActivity {

    ImageView image;
    TextView match_name, match_age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_profile);

        image = findViewById(R.id.edition_Profile);
        match_age = findViewById(R.id.match_age);
        match_name = findViewById(R.id.match_name);

        Intent i = getIntent();
        String userId = i.getStringExtra("userId");

      //  Toast.makeText(getApplicationContext(), userId, Toast.LENGTH_LONG).show();

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        String url = "http://api.betterdate.info/endpoints/user.php";

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

               // Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();

                try {
                    JSONArray array=new JSONArray(response);

                    for (int i=0;i<array.length();i++) {

                        JSONObject object=array.getJSONObject(i);
                        String name = object.getString("userName");
                        String dob = object.getString("userDob");
                        String userDp = object.getString("userDp");

                      //  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                        String[] dateofBirth = dob.split("/");
                        String age = getAge(Integer.parseInt(dateofBirth[2]), Integer.parseInt(dateofBirth[1]), Integer.parseInt(dateofBirth[0]));
                        String dp_url = "http://api.betterdate.info/gallery/" + userDp;

                        match_name.setText(name);
                        match_age.setText("Age : " + age);

                        Picasso.get().load(dp_url).placeholder(R.drawable.download).into(image);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
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
    }

    private String getAge(int year, int month, int day) {

        Calendar now = Calendar.getInstance();
        int year1 = now.get(Calendar.YEAR);
        int age = year1 - year;

        return String.valueOf(age);
    }
}