package com.example.cupid.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.cupid.Fragments.ProfileFragment;
import com.example.cupid.R;

public class Profile_Preferences extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile__preferences);







    }

    public void gender(View view) {
        Intent i = new Intent(getApplicationContext(),Preferences_Gender.class);
        startActivity(i);

    }

    public void bodytype(View view) {
        Intent i = new Intent(getApplicationContext(),BodyType.class);
        startActivity(i);


    }

    public void age(View view) {

        Intent i = new Intent(getApplicationContext(),PreferencesAge.class);
        startActivity(i);

    }

    public void distance(View view) {

        Intent i = new Intent(getApplicationContext(),PreferencesDistance.class);
        startActivity(i);

    }

    public void conncetions(View view) {
        Intent i = new Intent(getApplicationContext(),EmptyRequiredData.class);
        startActivity(i);
    }

    public void languages(View view) {
        Intent i = new Intent(getApplicationContext(),EmptyRequiredData.class);
        startActivity(i);

    }

    public void orientation(View view) {
        Intent i = new Intent(getApplicationContext(),EmptyRequiredData.class);
        startActivity(i);

    }

    public void ethnicity(View view) {
        Intent i = new Intent(getApplicationContext(),EmptyRequiredData.class);
        startActivity(i);

    }

    public void politicals(View view) {
        Intent i = new Intent(getApplicationContext(),EmptyRequiredData.class);
        startActivity(i);

    }

    public void education(View view) {
        Intent i = new Intent(getApplicationContext(),EmptyRequiredData.class);
        startActivity(i);

    }

    public void employment(View view) {
        Intent i = new Intent(getApplicationContext(),EmptyRequiredData.class);
        startActivity(i);

    }

    public void Astrlogical_Sign(View view) {
        Intent i = new Intent(getApplicationContext(),EmptyRequiredData.class);
        startActivity(i);

    }
}