package com.example.cupid.LikesFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cupid.Adapter.BoostAdapter;
import com.example.cupid.Adapter.ViewPagerAdaper;
import com.example.cupid.R;
import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Second_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Second_Fragment extends Fragment {

    ViewPager viewPager;
    BoostAdapter boostAdapter;

    TabLayout tabLayout;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Second_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Second_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Second_Fragment newInstance(String param1, String param2) {
        Second_Fragment fragment = new Second_Fragment();
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
        View v = inflater.inflate(R.layout.fragment_second_, container, false);

        boostAdapter = new BoostAdapter(this,getFragmentManager());

        viewPager = v.findViewById(R.id.viewPagers);
        viewPager.setAdapter(boostAdapter);

        tabLayout = (TabLayout) v.findViewById(R.id.tabing);
        tabLayout.setupWithViewPager(viewPager, true);



     return v;
    }
}