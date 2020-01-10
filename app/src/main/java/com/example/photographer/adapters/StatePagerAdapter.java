package com.example.photographer.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Muskan Hussain on 09-01-2020
 */
public class StatePagerAdapter extends FragmentStatePagerAdapter {
    List<Fragment> fragmentList = new ArrayList<>();
    public StatePagerAdapter(FragmentManager fm) {
        super(fm);
    }
    public void addFragment(Fragment fragment) {
        fragmentList.add(fragment);
    }
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
