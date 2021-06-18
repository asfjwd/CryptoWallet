package com.cse2216.cryptowallet.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.cse2216.cryptowallet.R;
import com.cse2216.cryptowallet.classes.domain.Coin;
import com.cse2216.cryptowallet.classes.helper.LunarAPI;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public List<Coin> coins = new ArrayList<Coin>();
    public LunarAPI lunarAPI ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        lunarAPI = new LunarAPI(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        com.cse2216.cryptowallet.ui.main.SectionsPagerAdapter sectionsPagerAdapter = new com.cse2216.cryptowallet.ui.main.SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        lunarAPI.updateCoins();
        populateData();
    }

    private void populateData() {
//        hardcode or from database
          coins = lunarAPI.getCoinArrayList();
    }

}