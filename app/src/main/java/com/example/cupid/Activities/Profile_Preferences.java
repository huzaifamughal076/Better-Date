package com.example.cupid.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.cupid.Fragments.ProfileFragment;
import com.example.cupid.R;

public class Profile_Preferences extends AppCompatActivity {

ImageView back_arrow;
TextView from_age , to_age;

RelativeLayout height_layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile__preferences);

        back_arrow = findViewById(R.id.back_arrow);
        height_layout = findViewById(R.id.height);

//        from_age = findViewById(R.id.from_ages);
//        to_age = findViewById(R.id.to_ages);
//
//        String st = getIntent().getStringExtra("start_age");
//        String en = getIntent().getStringExtra("end_age");
//
//        from_age.setText(st);
//        to_age.setText(en);


        height_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Height.class);
                startActivity(i);
                finish();
            }
        });

        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    public void gender(View view) {
        Intent i = new Intent(getApplicationContext(),Preferences_Gender.class);
        startActivity(i);
        finish();

    }

    public void bodytype(View view) {
        Intent i = new Intent(getApplicationContext(),BodyType.class);
        startActivity(i);
        finish();


    }

    public void age(View view) {

        Intent i = new Intent(getApplicationContext(),PreferencesAge.class);
        startActivity(i);
        finish();

    }

    public void distance(View view) {

        Intent i = new Intent(getApplicationContext(),PreferencesDistance.class);
        startActivity(i);
        finish();

    }

    public void conncetions(View view) {
        Intent i = new Intent(getApplicationContext(),EmptyRequiredData.class);
        startActivity(i);
        finish();
    }

    public void languages(View view) {
        Intent i = new Intent(getApplicationContext(),EmptyRequiredData.class);
        startActivity(i);
        finish();

    }

    public void orientation(View view) {
        Intent i = new Intent(getApplicationContext(),EmptyRequiredData.class);
        startActivity(i);
        finish();

    }

    public void ethnicity(View view) {
        Intent i = new Intent(getApplicationContext(),EmptyRequiredData.class);
        startActivity(i);
        finish();

    }

    public void politicals(View view) {
        Intent i = new Intent(getApplicationContext(),EmptyRequiredData.class);
        startActivity(i);
        finish();
    }

    public void education(View view) {
        Intent i = new Intent(getApplicationContext(),EmptyRequiredData.class);
        startActivity(i);
        finish();
    }

    public void employment(View view) {
        Intent i = new Intent(getApplicationContext(),EmptyRequiredData.class);
        startActivity(i);
        finish();

    }

    public void Astrlogical_Sign(View view) {
        Intent i = new Intent(getApplicationContext(),EmptyRequiredData.class);
        startActivity(i);
        finish();

    }
}