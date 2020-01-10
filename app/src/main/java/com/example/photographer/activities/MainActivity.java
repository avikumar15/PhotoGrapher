package com.example.photographer.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.photographer.fragments.CameraFragment;
import com.example.photographer.fragments.EquationFragment;
import com.example.photographer.fragments.MainFragment;
import com.example.photographer.R;
import com.example.photographer.adapters.StatePagerAdapter;

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
    }
    private void setupViewPager(ViewPager viewPager){
        adapter.addFragment(new MainFragment());
        adapter.addFragment(new CameraFragment());
        adapter.addFragment(new EquationFragment());
        viewPager.setAdapter(adapter);
    }

    public void setViewPager(int fragmentNumber)
    {
        viewPager.setCurrentItem(fragmentNumber);

    }

    @Override
    public void onBackPressed() {
        if(viewPager.getCurrentItem() != 0) {
            viewPager.setCurrentItem(0);
        }
        else {
            super.onBackPressed();
        }
    }
}
