package com.example.cupid.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cupid.Activities.EditProfile;
import com.example.cupid.Activities.Profile_Preferences;
import com.example.cupid.Activities.Profile_Settings;
import com.example.cupid.Adapter.ViewPagerAdaper;
import com.example.cupid.R;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    ImageView editprofile;
    CircleImageView profile_pic;
    TextView username,userAge;
    ViewPager viewPager;
    ViewPagerAdaper viewPagerAdapter;


    LinearLayout preferences;
    LinearLayout settings;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        TabLayout tabLayout;

        profile_pic = v.findViewById(R.id.profile_Pic);
        userAge=v.findViewById(R.id.userAge);
        username=v.findViewById(R.id.username);

        RequestQueue queue = Volley.newRequestQueue(getContext());
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("Questoins", MODE_PRIVATE);
        String userId = sharedPreferences.getString("userid", "");

        String url = "http://api.betterdate.info/endpoints/user.php";

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject object = array.getJSONObject(i);
                        String name = object.getString("userName");
                        String dob = object.getString("userDob");
                        String userDp = object.getString("userDp");

                        //  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                        String[] dateofBirth = dob.split("/");
                        String age = "Age : " + getAge(Integer.parseInt(dateofBirth[2]), Integer.parseInt(dateofBirth[1]), Integer.parseInt(dateofBirth[0]));
                        String dp_url = "http://api.betterdate.info/gallery/" + userDp;

                        username.setText(name);
                        userAge.setText(age);

                        Picasso.get().load(dp_url).placeholder(R.drawable.download).into(profile_pic);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
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
                return params;
            }
        };
        queue.add(request);

        preferences = v.findViewById(R.id.preferences);
        settings = v.findViewById(R.id.settings);


        viewPagerAdapter = new ViewPagerAdaper(this, getChildFragmentManager());

        viewPager = v.findViewById(R.id.viewPager);
        viewPager.setAdapter(viewPagerAdapter);

        tabLayout = (TabLayout) v.findViewById(R.id.tabing);
        tabLayout.setupWithViewPager(viewPager, true);


        preferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getActivity(), Profile_Preferences.class);
                startActivity(i);
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getActivity(), Profile_Settings.class);
                startActivity(i);
            }
        });


        editprofile = v.findViewById(R.id.edit_profile);

        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getActivity(), EditProfile.class);
                startActivity(i);

            }
        });


        return v;
    }

    private String getAge(int year, int month, int day) {

        Calendar now = Calendar.getInstance();
        int year1 = now.get(Calendar.YEAR);
        int age = year1 - year;

        return String.valueOf(age);
    }
}