package com.example.cupid.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bambuser.broadcaster.BroadcastStatus;
import com.bambuser.broadcaster.Broadcaster;
import com.bambuser.broadcaster.CameraError;
import com.bambuser.broadcaster.ConnectionError;
import com.example.cupid.R;

import java.util.HashMap;
import java.util.Map;

public class LiveActivity extends AppCompatActivity {

    SurfaceView mPreviewSurface;
    private static final String APPLICATION_ID = "9ciyWkedBvu6XlQyc1cT1w";
    Broadcaster mBroadcaster;
    Button mBroadcastButton;
    String userId;
    ImageView camRotate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live);

        mPreviewSurface = findViewById(R.id.PreviewSurfaceView);

        mBroadcaster = new Broadcaster(this, APPLICATION_ID, mBroadcasterObserver);
        mBroadcaster.setRotation(getWindowManager().getDefaultDisplay().getRotation());

        camRotate = findViewById(R.id.camRotate);

        SharedPreferences sharedPreferences = getSharedPreferences("Questoins", MODE_PRIVATE);
        userId = sharedPreferences.getString("userid", "");

        camRotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBroadcaster.switchCamera();
            }
        });

        mBroadcastButton = findViewById(R.id.BroadcastButton);
        mBroadcastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBroadcaster.canStartBroadcasting()) {
                    mBroadcaster.setTitle("UserId : " + userId + "'s stream");
                    mBroadcaster.startBroadcast();

                } else {
                    mBroadcaster.stopBroadcast();
                }
            }
        });


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBroadcaster.onActivityDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        mBroadcaster.onActivityPause();
    }

    private final Broadcaster.Observer mBroadcasterObserver = new Broadcaster.Observer() {
        @Override
        public void onConnectionStatusChange(BroadcastStatus broadcastStatus) {
            mBroadcastButton.setText(broadcastStatus == BroadcastStatus.IDLE ? "Start" : "Stop");
        }

        @Override
        public void onStreamHealthUpdate(int i) {
        }

        @Override
        public void onConnectionError(ConnectionError connectionError, String s) {
        }

        @Override
        public void onCameraError(CameraError cameraError) {
        }

        @Override
        public void onChatMessage(String s) {
        }

        @Override
        public void onResolutionsScanned() {
        }

        @Override
        public void onCameraPreviewStateChanged() {
        }

        @Override
        public void onBroadcastInfoAvailable(String s, String s1) {
        }

        @Override
        public void onBroadcastIdAvailable(String s) {
            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();

            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            String url = "http://api.betterdate.info/endpoints/broadcast.php";

            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

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
                    params.put("broadId", s);
                    return params;
                }
            };
            queue.add(request);

        }
    };

    @Override
    public void onResume() {
        super.onResume();
        if (!hasPermission(Manifest.permission.CAMERA)
                && !hasPermission(Manifest.permission.RECORD_AUDIO))
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA,
                    Manifest.permission.RECORD_AUDIO}, 1);
        else if (!hasPermission(Manifest.permission.RECORD_AUDIO))
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 1);
        else if (!hasPermission(Manifest.permission.CAMERA))
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);

        mBroadcaster.setCameraSurface(mPreviewSurface);
        mBroadcaster.onActivityResume();
    }

    private boolean hasPermission(String permission) {
        return ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }
}