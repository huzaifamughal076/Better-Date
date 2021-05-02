package com.example.cupid.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cupid.Activities.FullProfileActivity;
import com.example.cupid.Adapter.CardsAdapter;
import com.example.cupid.Model.CardItem_test;
import com.example.cupid.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import link.fls.swipestack.SwipeStack;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DiscoverFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DiscoverFragment extends Fragment {

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

        See_full_profile = v.findViewById(R.id.SeeFullProfile);
        cardStack = (SwipeStack) v.findViewById(R.id.container);

        setCardStackAdapter();
        currentPosition = 0;

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("Questoins", MODE_PRIVATE);
        String userId = sharedPreferences.getString("userid", "");


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