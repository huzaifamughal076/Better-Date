package com.example.cupid.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cupid.Activities.FullProfileActivity;
import com.example.cupid.Activities.FullscreenVideo;
import com.example.cupid.Activities.HomeScreen;
import com.example.cupid.Activities.LiveActivity;
import com.example.cupid.Activities.LivePreview;
import com.example.cupid.Adapter.CardsAdapter;
import com.example.cupid.Adapter.liveRecyclerAdapter;
import com.example.cupid.Model.CardItem_test;
import com.example.cupid.Model.LiveModel;
import com.example.cupid.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import link.fls.swipestack.SwipeStack;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DiscoverFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DiscoverFragment extends Fragment {

    VideoView videoView2;
    ProgressBar vidLoading;
    private int index = 0;

    CircleImageView goLive;
    RecyclerView liveRecycler;
    List<LiveModel> liveModels;
    ImageView reload;
    private AdView mAdView;


    String dp_url;
    private SwipeStack cardStack;
    private CardsAdapter cardsAdapter;
    private ArrayList<CardItem_test> cardItems;
    private int currentPosition;
    private View See_full_profile;

    int counter = 0;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DiscoverFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DiscoverFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DiscoverFragment newInstance(String param1, String param2) {
        DiscoverFragment fragment = new DiscoverFragment();
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

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_discover, container, false);

        liveRecycler = v.findViewById(R.id.liveRecycler);

        goLive = v.findViewById(R.id.golive);
        reload = v.findViewById(R.id.reload);

        See_full_profile = v.findViewById(R.id.SeeFullProfile);
        cardStack = (SwipeStack) v.findViewById(R.id.container);

        videoView2=v.findViewById(R.id.videoView2);
        vidLoading=v.findViewById(R.id.vidLoading);

        String vidurl = "http://api.betterdate.info/endpoints/video.php";

        RequestQueue queue3 = Volley.newRequestQueue(getContext());

        ArrayList<String> videonames = new ArrayList<String>();
        JsonArrayRequest requesty = new JsonArrayRequest(Request.Method.GET, vidurl, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        String vidname = object.getString("videoName");

                        videonames.add(vidname);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                int size = (videonames.size()) - 1;
                videoView2.requestFocus();
                String videourl = "http://admin.betterdate.info/video/" + videonames.get(index);
                Uri uri = Uri.parse(videourl);
                videoView2.setVideoURI(uri);
                videoView2.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        vidLoading.setVisibility(View.GONE);
                        mp.setVolume(0f, 0f);
                        videoView2.start();
                    }
                });

                videoView2.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        if (index == size) {
                            index = -1;
                        }
                        index++;
                        String videourl = "http://admin.betterdate.info/video/" + videonames.get(index);
                        Uri uri = Uri.parse(videourl);
                        videoView2.setVideoURI(uri);
                    }
                });

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requesty.setShouldCache(false);
        queue3.add(requesty);




        MediaController mediaController=new MediaController(getContext());
        mediaController.setAnchorView(videoView2);

//        Uri uri=Uri.parse("http://admin.betterdate.info/video/47003.mp4");
//       // videoView2.setMediaController(mediaController);
//        videoView2.setVideoURI(uri);
//        videoView2.start();

//        videoView2.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//            @Override
//            public void onPrepared(MediaPlayer mediaPlayer) {
//                vidLoading.setVisibility(View.GONE);
//                mediaPlayer.setVolume(0f, 0f);
//
//            }
//        });
        videoView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(), FullscreenVideo.class);
                startActivity(i);
            }
        });

        MobileAds.initialize(getContext(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = v.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        setCardStackAdapter();
        currentPosition = 0;

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("Questoins", MODE_PRIVATE);
        String userId = sharedPreferences.getString("userid", "");

        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRestart();

            }
        });

        RequestQueue queue = Volley.newRequestQueue(getContext());
        RequestQueue queue1 = Volley.newRequestQueue(getContext());
        liveModels = new ArrayList<>();
        String url = "http://api.betterdate.info/endpoints/broadcast.php";


        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject live_user = response.getJSONObject(i);
                        String userid = live_user.getString("broadUserId");
                        String broadId = live_user.getString("broadUserBroadId");


                        String url2 = "http://api.betterdate.info/endpoints/user.php";
                        StringRequest request1 = new StringRequest(Request.Method.POST, url2, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONArray array = new JSONArray(response);


                                    for (int i = 0; i < array.length(); i++) {

                                        JSONObject object = array.getJSONObject(i);
                                        String userDp = object.getString("userDp");
                                        dp_url = "http://api.betterdate.info/gallery/" + userDp;

                                        // Toast.makeText(getContext(),userid + "\n" + broadId + "\n" + dp_url,Toast.LENGTH_LONG).show();


                                        LiveModel liveModel = new LiveModel(userid, broadId, dp_url);

                                        liveModels.add(liveModel);

                                        //    Toast.makeText(getContext(),String.valueOf(liveModels.size()),Toast.LENGTH_LONG).show();


                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                final liveRecyclerAdapter myadapter = new liveRecyclerAdapter(getContext(), liveModels);
                                liveRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));
                                liveRecycler.setAdapter(myadapter);
                                myadapter.setOnItemClickListener(new liveRecyclerAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(int position) {
                                        LiveModel selected = liveModels.get(position);
                                        Intent i = new Intent(getActivity(), LivePreview.class);
                                        i.putExtra("userid", selected.getUserid());
                                        i.putExtra("broadid", selected.getBroadId());
                                        startActivity(i);

                                        //  Toast.makeText(getContext(),selected.getBroadId(),Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();
                                params.put("userId", userid);
                                return params;
                            }
                        };
                        queue1.add(request1);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);


        goLive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(getContext())
                        .setTitle("You are about to go LIVE!")
                        .setMessage("Are you sure?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(getActivity(), LiveActivity.class);
                                startActivity(i);
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
        });

        //Handling swipe event of Cards stack
        cardStack.setListener(new SwipeStack.SwipeStackListener() {
            @Override
            public void onViewSwipedToLeft(int position) {
                currentPosition = position + 1;
                counter++;
            }

            @Override
            public void onViewSwipedToRight(int position) {

                CardItem_test cardItem_test = cardsAdapter.getItem(position);

                String right_swiped_id = cardItem_test.getId();

                RequestQueue queue = Volley.newRequestQueue(getContext());

                String url = "http://api.betterdate.info/endpoints/like.php";

                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getContext(), "Added to liked matches", Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Failed to like match", Toast.LENGTH_LONG).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("userId", userId);
                        params.put("likedId", right_swiped_id);
                        return params;
                    }
                };
                queue.add(request);

                currentPosition = position + 1;
                counter++;
            }

            @Override
            public void onStackEmpty() {
                // Toast.makeText(getContext(), "No more users found", Toast.LENGTH_LONG).show();

                if (counter == 1) {
                    See_full_profile.setVisibility(View.VISIBLE);
                } else {
                    See_full_profile.setVisibility(View.GONE);
                }
            }


        });
        See_full_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = cardItems.get(currentPosition).getId();

                Intent i = new Intent(getActivity(), FullProfileActivity.class);
                i.putExtra("userId", userId);
                startActivity(i);

            }
        });


        return v;
    }

    protected void onRestart() {

        Intent i = new Intent(getActivity(), HomeScreen.class);  //your class
        getActivity().finish();
        startActivity(i);

    }

    private void setCardStackAdapter() {


        cardItems = new ArrayList<>();

        String url = "http://api.betterdate.info/endpoints/user.php";
        RequestQueue queue = Volley.newRequestQueue(getContext());

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("Questoins", MODE_PRIVATE);
        String userId = sharedPreferences.getString("userid", "");

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject match = response.getJSONObject(i);

                        //Toast.makeText(getContext(),match.toString(),Toast.LENGTH_LONG).show();

                        String name = match.getString("userName");
                        String username = match.getString("userUserName");
                        String id = match.getString("userId");
                        String email = match.getString("userEmail");
                        String gender = match.getString("userGender");
                        String dob = match.getString("userDob");
                        String desc = match.getString("userDesc");
                        String dp = match.getString("userDp");
                        String phone = match.getString("userPhone");

                        if (desc.equals("")) {
                            desc = "No description found";
                        }
                        String[] dateofBirth = dob.split("/");

                        String age = getAge(Integer.parseInt(dateofBirth[2]), Integer.parseInt(dateofBirth[1]), Integer.parseInt(dateofBirth[0]));

                        String dp_url = "http://api.betterdate.info/gallery/" + dp;

                        if (!id.equals(userId)) {

                            cardItems.add(new CardItem_test(id, desc, dp_url, name, age));
                        }
                        //  Toast.makeText(getContext(), dp_url, Toast.LENGTH_LONG).show();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        });

        queue.add(request);


        cardItems.add(new CardItem_test("-1", "Ad's description", "http://api.betterdate.info/gallery/11401619609187209.png", "Ad display card", "Ads"));

        cardsAdapter = new CardsAdapter(getActivity(), cardItems);
        cardStack.setAdapter(cardsAdapter);


//        String url = "http://api.betterdate.info/endpoints/user.php";
//        RequestQueue queue1 = Volley.newRequestQueue(getContext());
//
//        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//
//                for (int i = 0; i < 1; i++) {
//                    try {
//                        JSONObject match = response.getJSONObject(i);
//                        String name = match.getString("userName");
//                        String username = match.getString("userUserName");
//                        String id = match.getString("userId");
//                        String email = match.getString("userEmail");
//                        String gender = match.getString("userGender");
//                        String dob = match.getString("userDob");
//                        String desc = match.getString("userDesc");
//                        String dp = match.getString("userDp");
//                        String phone = match.getString("userPhone");
//
//                        CardItem cardItem = new CardItem(name, username, id, email, gender, dob, desc, dp, phone);
//
//
//                        //Toast.makeText(getContext(),cardItem.getName() , Toast.LENGTH_LONG).show();
//
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//                // Toast.makeText(getContext(), String.valueOf(response), Toast.LENGTH_LONG).show();
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//        queue1.add(request);


//        cardItems.add(new CardItem(R.drawable.a, "Huyen My", "Hanoi"));
//        cardItems.add(new CardItem(R.drawable.f, "Do Ha", "Nghe An"));
//        cardItems.add(new CardItem(R.drawable.g, "Dong Nhi", "Hue"));
//        cardItems.add(new CardItem(R.drawable.e, "Le Quyen", "Sai Gon"));
//        cardItems.add(new CardItem(R.drawable.c, "Phuong Linh", "Thanh Hoa"));
//        cardItems.add(new CardItem(R.drawable.d, "Phuong Vy", "Hanoi"));
//        cardItems.add(new CardItem(R.drawable.b, "Ha Ho", "Da Nang"));


    }

    private String getAge(int year, int month, int day) {

        Calendar now = Calendar.getInstance();
        int year1 = now.get(Calendar.YEAR);
        int age = year1 - year;

        return String.valueOf(age);
    }


}