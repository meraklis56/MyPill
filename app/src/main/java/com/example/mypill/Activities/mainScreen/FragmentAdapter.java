package com.example.mypill.Activities.mainScreen;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class FragmentAdapter extends FragmentPagerAdapter {

    public FragmentAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return HistoryFragment.newInstance("History", 1);
        } else if (position == 1) {
            return GraphFragment.newInstance("Graph", 2);
        } else {
            return null;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "List";
        } else if (position == 1) {
            return "Calendar";
        } else {
            return "";
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
