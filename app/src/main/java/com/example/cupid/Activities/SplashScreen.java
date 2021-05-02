package com.example.cupid.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.cupid.R;

public class SplashScreen extends AppCompatActivity {

    ImageView img;
    Animation anim;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        img = findViewById(R.id.logocupid);
        anim = AnimationUtils.loadAnimation(this,R.anim.fadein);
        img.setAnimation(anim);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            final WindowInsetsController insetsController = getWindow().getInsetsController();
            if (insetsController != null) {
                insetsController.hide(WindowInsets.Type.statusBars());
            }
        } else {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent in=new Intent(getApplicationContext(),LoginActivity.class);

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    Pair[] pairs = new Pair[1];
                    pairs[0]=new Pair<View,String>(img,"ok");
                    ActivityOptions anim= ActivityOptions.makeSceneTransitionAnimation(SplashScreen.this,pairs);
                    startActivity(in, anim.toBundle());
                    finish();
                }

            }
        },3000);


    }
}