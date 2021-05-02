package com.example.cupid.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.cupid.BoostFragment.FirstBoost;
import com.example.cupid.BoostFragment.SecondBoost;
import com.example.cupid.LikesFragment.Second_Fragment;
import com.example.cupid.LikesFragment.first_fragment;
import com.example.cupid.SlidingFragment.FirstFragment;
import com.example.cupid.SlidingFragment.SecondFragment;

public class BoostAdapter extends FragmentPagerAdapter {


    public BoostAdapter(Second_Fragment second_fragment, @NonNull FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = null;
        switch (position) {
            case 0:

                fragment = new FirstBoost();//create any fragment

                break;
            case 1:

                fragment = new SecondBoost();//create any fragment
                break;
            // you may add more cases for more fragments

        }
        assert fragment != null;
        return fragment;
    }


    @Override
    public int getCount() {
        // Show total number of pages.
        return 2;
    }
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {

            return null;
        } else  {
            return  null;
        }


    }
}
