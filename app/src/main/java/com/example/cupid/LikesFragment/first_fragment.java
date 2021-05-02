package com.example.cupid.LikesFragment;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.cupid.Adapter.likesRecyclerAdapter;
import com.example.cupid.Model.CardItem_test;
import com.example.cupid.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link first_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class first_fragment extends Fragment {


    RecyclerView likesRecycler;
    List<CardItem_test> likes_card;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public first_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment first_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static first_fragment newInstance(String param1, String param2) {
        first_fragment fragment = new first_fragment();
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

        View v = inflater.inflate(R.layout.fragment_first_fragment, container, false);

        RequestQueue queue = Volley.newRequestQueue(getContext());
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("Questoins", MODE_PRIVATE);
        String userId = sharedPreferences.getString("userid", "");

        String url = "http://api.betterdate.info/endpoints/like.php?userId=" + userId;

        likes_card = new ArrayList<>();

        likesRecycler = v.findViewById(R.id.likesRecycler);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject liked_user = response.getJSONObject(i);

                        String name = liked_user.getString("userName");
                        String username = liked_user.getString("userUserName");
                        String id = liked_user.getString("userId");
                        String gender = liked_user.getString("userGender");
                        String dob = liked_user.getString("userDob");
                        String desc = liked_user.getString("userDesc");
                        String dp = liked_user.getString("userDp");

                        if (gender.equals("M")){
                            gender="Male";
                        }else if (gender.equals("F")){
                            gender="Female";
                        }
                        else if (gender.equals("O")){
                            gender="Other";
                        }
                        String dp_url = "http://api.betterdate.info/gallery/" + dp;

                        CardItem_test test = new CardItem_test(name, dp_url, desc, id, username, gender, dob);

                        likes_card.add(test);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
//                CardItem_test test1 = new CardItem_test("Rehan", "dasasd", "hellosadasd", "-12", "knjfsdas", "M", "12/3/2121");
//                likes_card.add(test1);
                final likesRecyclerAdapter myadapter = new likesRecyclerAdapter(getContext(), likes_card);

                final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                likesRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
                likesRecycler.setAdapter(myadapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);


        return v;
    }
}