package com.example.cupid.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Struct;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    Button Have_Account;
    Button SignUp;
    TextView dob;
    DatePickerDialog datePickerDialog;

    RequestQueue queue;

    RadioGroup radioGroup;
    RadioButton radioButton;
    TextInputEditText full_name, username, email, phone, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Have_Account = findViewById(R.id.Have_Account);
        SignUp = findViewById(R.id.signup);

        full_name = findViewById(R.id.full_name);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);

        radioGroup = findViewById(R.id.radiogroup);

        queue = Volley.newRequestQueue(this);

        dob = findViewById(R.id.dob);
//        dob_layout=findViewById(R.id.dob_layout);
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(SignUpActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                dob.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullname, usernam, emailadress, phonenum, pass, gender, dateofbirth;
                fullname = full_name.getText().toString().trim();
                usernam = username.getText().toString().trim();
                emailadress = email.getText().toString().trim();
                phonenum = phone.getText().toString().trim();
                pass = password.getText().toString().trim();
                dateofbirth = dob.getText().toString().trim();

                int selectedgender = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(selectedgender);

                gender = radioButton.getText().toString().trim();

                if (gender.equals("Male")){
                    gender="M";
                }
                else if (gender.equals("Female")){
                    gender="F";
                }
                else if(gender.equals("Other")){
                    gender="O";
                }

                if (fullname.isEmpty()) {
                    full_name.setError("Name required");
                    return;
                } else if (usernam.isEmpty()) {
                    username.setError("Username required");
                    return;
                } else if (emailadress.isEmpty()) {
                    email.setError("Email required");
                    return;
                } else if (phonenum.isEmpty()) {
                    phone.setError("Phone required");
                    return;
                } else if (pass.isEmpty()) {
                    password.setError("Password required");
                    return;
                } else if (dateofbirth.equals("Date of Birth")) {
                    dob.setError("Select a date from calender");
                    return;
                } else {
                    signup(fullname,usernam,emailadress,phonenum,pass,dateofbirth,gender);

                }
            }
        });
        Have_Account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(in);
                finish();
            }
        });

    }
    public void signup(String fullname,String usernam,String emailadress,String phonenum,String pass,String dateofbirth,String gender){

        String url= "http://api.betterdate.info/endpoints/signup.php";

        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject object=new JSONObject(response);

                    String status=object.getString("status");
                    String message= object.getString("message");
                    String userId= object.getString("userId");


                    if (status.equals("true")){

                        SharedPreferences.Editor editor = getSharedPreferences("Questoins", MODE_PRIVATE).edit();
                        editor.putString("userid", userId);
                        editor.apply();

                    //    Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                        Intent i=new Intent(SignUpActivity.this,SignupSuccessfullActivity.class);
                       // i.putExtra("userid",userId);
                        startActivity(i);

                    }
                    if (status.equals("false")){
                        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                        return;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", emailadress);
                params.put("phone", phonenum);
                params.put("name", fullname);
                params.put("username", usernam);
                params.put("gender", gender);
                params.put("dob", dateofbirth);
                params.put("password", pass);
                return params;
            }
        };
        queue.add(request);

    }
}