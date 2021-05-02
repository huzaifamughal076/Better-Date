package com.example.cupid.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.cupid.R;

public class EditProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);






    }

    public void photoedit(View view) {

        Intent i = new Intent(getApplicationContext(),EditImages.class);
        startActivity(i);

    }
}