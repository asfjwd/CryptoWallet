package com.cse2216.cryptowallet.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.cse2216.cryptowallet.R;
import com.cse2216.cryptowallet.classes.domain.Coin;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public List<Coin> coins = new ArrayList<Coin>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        populateData();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        com.cse2216.cryptowallet.ui.main.SectionsPagerAdapter sectionsPagerAdapter = new com.cse2216.cryptowallet.ui.main.SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }

    private void populateData() {
//        hardcode or from database
        coins.add(0, new Coin("bitcoin", "0", "0", 0, 0.0, 0.0, 0.0));
        coins.add(1, new Coin("ethereum", "1", "1", 1, 0.0, 0.0, 0.0));
        coins.add(2, new Coin("bitcoin", "2", "2", 2, 0.0, 0.0, 0.0));
        coins.add(3, new Coin("bitcoin", "3", "3", 3, 0.0, 0.0, 0.0));
        coins.add(4, new Coin("bitcoin", "4", "4", 4, 0.0, 0.0, 0.0));
        coins.add(5, new Coin("bitcoin", "5", "5", 6, 0.0, 0.0, 0.0));
        coins.add(6, new Coin("bitcoin", "6", "6", 6, 0.0, 0.0, 0.0));
    }

}