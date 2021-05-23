package com.example.cupid.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.cupid.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FullscreenVideo extends AppCompatActivity {

    VideoView fullscreenVideoView;
    ProgressBar progressBar;
    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_video);

        fullscreenVideoView = findViewById(R.id.fullscreenVideoView);
        progressBar=findViewById(R.id.progresss);

        String url = "http://api.betterdate.info/endpoints/video.php";

        RequestQueue queue = Volley.newRequestQueue(this);


        ArrayList<String> videonames = new ArrayList<String>();

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {


                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
//                        Toast.makeText(getApplicationContext(), object.getString("videoName"), Toast.LENGTH_LONG).show();
//                        Toast.makeText(getApplicationContext(), object.getString("videoId"), Toast.LENGTH_LONG).show();

                        String vidname = object.getString("videoName");

                        videonames.add(vidname);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


                int size = (videonames.size()) - 1;
                fullscreenVideoView.requestFocus();
                String videourl = "http://admin.betterdate.info/video/" + videonames.get(index);
                Uri uri = Uri.parse(videourl);
                fullscreenVideoView.setVideoURI(uri);
                fullscreenVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        progressBar.setVisibility(View.GONE);
                        fullscreenVideoView.start();
                    }
                });

                fullscreenVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        if (index == size) {
                            index = -1;
                            Intent i=new Intent(getApplicationContext(),HomeScreen.class);
                            startActivity(i);
                            finish();
                        }
                        index++;
                        String videourl = "http://admin.betterdate.info/video/" + videonames.get(index);
                        Uri uri = Uri.parse(videourl);
                        fullscreenVideoView.setVideoURI(uri);
                    }
                });

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        request.setShouldCache(false);
        queue.add(request);

    }
}