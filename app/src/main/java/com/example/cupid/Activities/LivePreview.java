package com.example.cupid.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bambuser.broadcaster.BroadcastPlayer;
import com.bambuser.broadcaster.PlayerState;
import com.bambuser.broadcaster.SurfaceViewWithAutoAR;
import com.example.cupid.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LivePreview extends AppCompatActivity {

    SurfaceViewWithAutoAR mVideoSurface;
    TextView mPlayerStatusTextView;
    View mPlayerContentView;
    BroadcastPlayer mBroadcastPlayer;
    final OkHttpClient mOkHttpClient = new OkHttpClient();

    String broadid;
    TextView coins_avail;

    private static final String APPLICATION_ID = "9ciyWkedBvu6XlQyc1cT1w";
    private static final String API_KEY = "HJViE3CVwjLd872AhpgCWZ";

    BroadcastPlayer.Observer mBroadcastPlayerObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_preview);

        Intent i = getIntent();
        String userId = i.getStringExtra("userid");
        broadid = i.getStringExtra("broadid");

        coins_avail = findViewById(R.id.coins_avail);

        mVideoSurface = findViewById(R.id.VideoSurfaceView);
        mPlayerStatusTextView = findViewById(R.id.PlayerStatusTextView);
        mPlayerContentView = findViewById(R.id.PlayerContentView);

        SharedPreferences sharedPreferences = this.getSharedPreferences("Questoins", MODE_PRIVATE);
        String userid = sharedPreferences.getString("userid", "");

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://api.betterdate.info/endpoints/user.php";

        StringRequest request = new StringRequest(com.android.volley.Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject object = array.getJSONObject(i);

                        String coins = "Coins : " + object.getString("coins");

                        coins_avail.setText(coins);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userId", userid);
                return params;
            }
        };
        queue.add(request);

        BroadcastPlayer.Observer mBroadcastPlayerObserver = new BroadcastPlayer.Observer() {
            @Override
            public void onStateChange(PlayerState playerState) {
                if (mPlayerStatusTextView != null)
                    mPlayerStatusTextView.setText("Status: " + playerState);
            }

            @Override
            public void onBroadcastLoaded(boolean live, int width, int height) {
            }
        };
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mBroadcastPlayer != null)
            mBroadcastPlayer.close();
        mBroadcastPlayer = null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mVideoSurface = findViewById(R.id.VideoSurfaceView);
        mPlayerStatusTextView.setText("Loading live stream");
        getLatestResourceUri();
    }

    void getLatestResourceUri() {
        Request request = new Request.Builder()
                .url("https://api.bambuser.com/broadcasts")
                .addHeader("Accept", "application/vnd.bambuser.v1+json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + API_KEY)
                .get()
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mPlayerStatusTextView != null)
                            mPlayerStatusTextView.setText("Http exception: " + e);
                    }
                });
            }

            @Override
            public void onResponse(final Call call, final Response response) throws IOException {
                String body = response.body().string();
                String resourceUri = null;
                try {
                    JSONObject json = new JSONObject(body);
                    JSONArray results = json.getJSONArray("results");

                    for (int i = 0; i < results.length(); i++) {
                        JSONObject latestBroadcast = results.optJSONObject(i);

                        String broadcastId = latestBroadcast.getString("id");

                        if (broadcastId.equals(broadid)) {
                            resourceUri = latestBroadcast.optString("resourceUri");
                            break;
                        }


                    }

                    // JSONObject latestBroadcast = results.optJSONObject(0);
                    // resourceUri = latestBroadcast.optString("resourceUri");
                } catch (Exception ignored) {
                }
                final String uri = resourceUri;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initPlayer(uri);
                    }
                });
            }
        });
    }


    void initPlayer(String resourceUri) {
        // ...
        if (mBroadcastPlayer != null)
            mBroadcastPlayer.close();
        mBroadcastPlayer = new BroadcastPlayer(this, resourceUri, APPLICATION_ID, mBroadcastPlayerObserver);
        mBroadcastPlayer.setSurfaceView(mVideoSurface);
        mBroadcastPlayer.load();
    }

}