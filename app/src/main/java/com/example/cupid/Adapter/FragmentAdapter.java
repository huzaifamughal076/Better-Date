package com.example.cupid.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.cupid.LikesFragment.Second_Fragment;
import com.example.cupid.LikesFragment.Third_Fragment;
import com.example.cupid.LikesFragment.first_fragment;

public class FragmentAdapter extends FragmentPagerAdapter {
    public FragmentAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0: return new first_fragment();
            case 1: return new Second_Fragment();
            case 2: return new Third_Fragment();

            default: return new first_fragment();
        }

    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;

        if (position==0)
        {
            title="LIKES";

        }
        if (position==1)
        {
            title="BOOST";
        }
        if (position==2)
        {
            title="Favroate";
        }



        return title;
    }
}
