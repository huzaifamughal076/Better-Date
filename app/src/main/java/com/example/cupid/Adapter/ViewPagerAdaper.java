package com.example.cupid.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.cupid.Fragments.ProfileFragment;
import com.example.cupid.SlidingFragment.FirstFragment;
import com.example.cupid.SlidingFragment.SecondFragment;

public class ViewPagerAdaper extends FragmentPagerAdapter {


    public ViewPagerAdaper(ProfileFragment profileFragment, @NonNull FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = null;
        switch (position) {
            case 0:

                fragment = new FirstFragment();//create any fragment

                break;
            case 1:

                fragment = new SecondFragment();//create any fragment
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

            return "";
        } else  {
            return "";
        }


    }
}
