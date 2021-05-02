package com.example.cupid.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cupid.Fragments.DiscoverFragment;
import com.example.cupid.Fragments.LikesFragment;
import com.example.cupid.Fragments.MessagesFragment;
import com.example.cupid.Fragments.ProfileFragment;
import com.example.cupid.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HomeScreen extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    FrameLayout frame;

    DiscoverFragment discoverFragment;
    MessagesFragment messagesFragment;
    ProfileFragment profileFragment;

    LikesFragment likesFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        frame = findViewById(R.id.frame);

        discoverFragment=new DiscoverFragment();
        messagesFragment=new MessagesFragment();
        profileFragment=new ProfileFragment();

        likesFragment = new LikesFragment();

        setfragment(discoverFragment);

        String question_chech_url = "http://api.betterdate.info/endpoints/question.php";
        RequestQueue queue1 = Volley.newRequestQueue(getApplicationContext());

        SharedPreferences sharedPreferences = getSharedPreferences("Questoins", MODE_PRIVATE);
        String userId = sharedPreferences.getString("userid", "");

       // Toast.makeText(getApplicationContext(),userId,Toast.LENGTH_LONG).show();

        StringRequest request1 = new StringRequest(Request.Method.POST, question_chech_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object2 = new JSONObject(response);
                    String stats = object2.getString("status");
                    String msg = object2.getString("message");

                  //  Toast.makeText(getApplicationContext(),stats,Toast.LENGTH_LONG).show();

                    if (stats.equals("true")) {
//                        Intent i = new Intent(HomeScreen.this, HomeScreen.class);
//                        startActivity(i);
//                        finish();
                    } else {
                        Intent i = new Intent(HomeScreen.this, SignupSuccessfullActivity.class);
                        startActivity(i);
                        finish();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("checkUserId", userId);
                return params;
            }


        };
        queue1.add(request1);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.discover:
                        setfragment(discoverFragment);
                        return true;
                    case R.id.likes:
                        //Intent i = new Intent(getApplicationContext(),LikesActivity.class);
                      //  startActivity(i);
                        setfragment(likesFragment);
                        return true;
                    case R.id.messages:
                        setfragment(messagesFragment);
                        return true;
                    case R.id.profile:
                        setfragment(profileFragment);
                        return true;
                    default:
                        return false;
                }

            }

        });

        }
    private void setfragment(Fragment page) {

        FragmentTransaction fragmentTransaction =getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame,page);
        fragmentTransaction.commit();

    }
}