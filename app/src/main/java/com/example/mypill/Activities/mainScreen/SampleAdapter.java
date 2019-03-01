package com.example.mypill.Activities.mainScreen;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class SampleAdapter extends FragmentPagerAdapter {

    public SampleAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return ListFragment.newInstance("0", "0");
        } else if (position == 1) {
            return ListFragment.newInstance("1", "1");
        } else if (position == 2) {
            return ListFragment.newInstance("2", "2");
        } else {
            return null;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "Tab 0 Item";
        } else if (position == 1) {
            return "Tab 1 Item";
        } else if (position == 2) {
            return "Tab 2 Item";
        } else {
            return "";
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
