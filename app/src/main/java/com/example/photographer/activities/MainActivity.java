package com.example.photographer.activities;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.photographer.fragments.CameraFragment;
import com.example.photographer.fragments.EquationFragment;
import com.example.photographer.fragments.MainFragment;
import com.example.photographer.R;
import com.example.photographer.adapters.StatePagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private StatePagerAdapter adapter;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.container);
        adapter = new StatePagerAdapter(getSupportFragmentManager());
        setupViewPager(viewPager);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setSelectedTabIndicatorColor(Color.parseColor("#98E2FC"));
        tabs.setTabTextColors(Color.WHITE, Color.parseColor("#98E2FC"));
        tabs.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(1);
    }
    private void setupViewPager(ViewPager viewPager){
        adapter.addFragment(new CameraFragment(), getString(R.string.tab_title_camera));
        adapter.addFragment(new MainFragment(), getString(R.string.tab_title_home));
        adapter.addFragment(new EquationFragment(), getString(R.string.tab_title_keyboard));
        viewPager.setAdapter(adapter);
    }

    public void setViewPager(int fragmentNumber)
    {
        viewPager.setCurrentItem(fragmentNumber);

    }

    @Override
    public void onBackPressed() {
        if(viewPager.getCurrentItem() != 1) {
            viewPager.setCurrentItem(1);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        if(viewPager.getCurrentItem() != 1) {
            viewPager.setCurrentItem(1);
        }
        super.onResume();
    }
}
