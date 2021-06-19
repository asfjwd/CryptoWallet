package com.cse2216.cryptowallet.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.cse2216.cryptowallet.R;
import com.cse2216.cryptowallet.adapters.LandingPageAdapter;
import com.google.android.material.tabs.TabLayout;

public class LandingPageActivity extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        LandingPageAdapter landingPageAdapter = new LandingPageAdapter(getSupportFragmentManager(), this);
        viewPager = findViewById(R.id.landing_viewpager);
        viewPager.setAdapter(landingPageAdapter);
        tabLayout = findViewById(R.id.landing_tablayout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }
}