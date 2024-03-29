package com.muhammadwildansyabani.katalogfilmkuv5.main.tv.model;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainPagerAdapter extends FragmentStatePagerAdapter {
    private final ArrayList<Fragment> fragments = new ArrayList<>();
    private final TabLayout tabLayout;
    public MainPagerAdapter(FragmentManager fm, TabLayout tabLayout) {
        super(fm);
        this.tabLayout = tabLayout;
    }

    public void addFragment(Fragment fragment, String title) {
        fragments.add(fragment);
        tabLayout.addTab(tabLayout.newTab().setText(title));
    }


    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }
}
