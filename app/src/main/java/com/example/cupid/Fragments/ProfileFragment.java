package com.example.cupid.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
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
import com.example.cupid.Activities.HomeScreen;
import com.example.cupid.Activities.Profile_Preferences;
import com.example.cupid.Activities.Profile_Settings;
import com.example.cupid.Adapter.ViewPagerAdaper;
import com.example.cupid.R;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
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
    TextView username, userAge, available_coins;
    ViewPager viewPager;
    ViewPagerAdaper viewPagerAdapter;
    Button getBoost;
    Button buyCoins;
    WebView webView;


    LinearLayout preferences;
    LinearLayout settings;
    LinearLayout main_profile_layout;

    private static final String AD_UNIT_ID = "ca-app-pub-3940256099942544/1033173712";
    private static final String TAG = "MyActivity";
    private InterstitialAd interstitialAd;

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
        userAge = v.findViewById(R.id.userAge);
        username = v.findViewById(R.id.username);
        getBoost = v.findViewById(R.id.getboost);
        buyCoins = v.findViewById(R.id.buycoins);
        webView = v.findViewById(R.id.webView);
        available_coins = v.findViewById(R.id.availablecoins);

        main_profile_layout = v.findViewById(R.id.main_profile_layout);
        MobileAds.initialize(getContext(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        loadAd();


        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("Questoins", MODE_PRIVATE);
        String userId = sharedPreferences.getString("userid", "");

        getBoost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main_profile_layout.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);
                webView.setWebViewClient(new WebViewClient());
                webView.loadUrl("http://admin.betterdate.info/buy-boost.php?id=" + userId);
                WebSettings webSettings = webView.getSettings();
                webSettings.setJavaScriptEnabled(true);

            }
        });
        buyCoins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main_profile_layout.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);
                webView.setWebViewClient(new WebViewClient());
                webView.loadUrl("http://admin.betterdate.info/buy-coins.php?id=" + userId);
                WebSettings webSettings = webView.getSettings();
                webSettings.setJavaScriptEnabled(true);

            }
        });
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
//                    main_profile_layout.setVisibility(View.VISIBLE);
//                    webView.setVisibility(View.GONE);
//                    webView.goBack();

                Intent i = new Intent(getActivity(), HomeScreen.class);  //your class
                getActivity().finish();
                startActivity(i);

            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getActivity(), callback);


        RequestQueue queue = Volley.newRequestQueue(getContext());

        String url = "http://api.betterdate.info/endpoints/user.php";
        ProgressDialog dialog = ProgressDialog.show(getActivity(), "",
                "Loading. Please wait...", true);

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    dialog.dismiss();
                    JSONArray array = new JSONArray(response);

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject object = array.getJSONObject(i);
                        String name = object.getString("userName");
                        String dob = object.getString("userDob");
                        String userDp = object.getString("userDp");
                        String coins = "Coins : " + object.getString("coins");

                        //  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                        String[] dateofBirth = dob.split("/");
                        String age = "Age : " + getAge(Integer.parseInt(dateofBirth[2]), Integer.parseInt(dateofBirth[1]), Integer.parseInt(dateofBirth[0]));
                        String dp_url = "http://api.betterdate.info/gallery/" + userDp;

                        username.setText(name);
                        userAge.setText(age);
                        available_coins.setText(coins);

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

                showInterstitial();
                Intent i = new Intent(getActivity(), EditProfile.class);
                startActivity(i);

            }
        });


        return v;
    }

    private void loadAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(
                getContext(),
                AD_UNIT_ID,
                adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        ProfileFragment.this.interstitialAd = interstitialAd;
                        Log.i(TAG, "onAdLoaded");
                        //  Toast.makeText(ProfileFragment.this, "onAdLoaded()", Toast.LENGTH_SHORT).show();
                        interstitialAd.setFullScreenContentCallback(
                                new FullScreenContentCallback() {
                                    @Override
                                    public void onAdDismissedFullScreenContent() {
                                        // Called when fullscreen content is dismissed.
                                        // Make sure to set your reference to null so you don't
                                        // show it a second time.
                                        ProfileFragment.this.interstitialAd = null;
                                        Log.d("TAG", "The ad was dismissed.");
                                    }

                                    @Override
                                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                                        // Called when fullscreen content failed to show.
                                        // Make sure to set your reference to null so you don't
                                        // show it a second time.
                                        ProfileFragment.this.interstitialAd = null;
                                        Log.d("TAG", "The ad failed to show.");
                                    }

                                    @Override
                                    public void onAdShowedFullScreenContent() {
                                        // Called when fullscreen content is shown.
                                        Log.d("TAG", "The ad was shown.");
                                    }
                                });
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.i(TAG, loadAdError.getMessage());
                        interstitialAd = null;

                        String error =
                                String.format(
                                        "domain: %s, code: %d, message: %s",
                                        loadAdError.getDomain(), loadAdError.getCode(), loadAdError.getMessage());
//                        Toast.makeText(
//                                MainActivity.this, "onAdFailedToLoad() with error: " + error, Toast.LENGTH_SHORT)
//                                .show();
                    }
                });
    }
    private void showInterstitial() {
        // Show the ad if it's ready. Otherwise toast and restart the game.
        if (interstitialAd != null) {
            interstitialAd.show(getActivity());
        } else {
           // Toast.makeText(this, "Ad did not load", Toast.LENGTH_SHORT).show();
            //startGame();
        }
    }



    private String getAge(int year, int month, int day) {

        Calendar now = Calendar.getInstance();
        int year1 = now.get(Calendar.YEAR);
        int age = year1 - year;

        return String.valueOf(age);
    }
}