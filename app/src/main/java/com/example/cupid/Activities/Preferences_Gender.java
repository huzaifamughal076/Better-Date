package com.example.cupid.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.example.cupid.R;

import java.util.ArrayList;

public class Preferences_Gender extends AppCompatActivity {

    ImageView backtogender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences__gender);


        backtogender = findViewById(R.id.backtogender);

        backtogender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(),Profile_Preferences.class);
                startActivity(i);
                finish();
            }
        });


    }
}