package com.cse2216.cryptowallet.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.cse2216.cryptowallet.R;
import com.cse2216.cryptowallet.classes.domain.Coin;
import com.cse2216.cryptowallet.classes.domain.PortfolioItem;
import com.cse2216.cryptowallet.classes.domain.UserInfo;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public List<Coin> coins = new ArrayList<Coin>();
    public UserInfo user;
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
        coins.add(0, new Coin("Bitcoin", "0", "0", 0, 0.0, 0.0, 0.0));
        coins.add(1, new Coin("Ethereum", "1", "1", 1, 0.0, 0.0, 0.0));
        coins.add(2, new Coin("Tether", "2", "2", 2, 0.0, 0.0, 0.0));
        coins.add(3, new Coin("Binance Coin", "3", "3", 3, 0.0, 0.0, 0.0));
        coins.add(4, new Coin("Cardano", "4", "4", 4, 0.0, 0.0, 0.0));
        coins.add(5, new Coin("LiteCoin", "5", "5", 6, 0.0, 0.0, 0.0));
        coins.add(6, new Coin("Stellar", "6", "6", 6, 0.0, 0.0, 0.0));
        coins.add(7, new Coin("Polkadot", "7", "7", 7, 0.0, 0.0, 0.0));

        ArrayList<PortfolioItem> pfList = new ArrayList<PortfolioItem>();
        pfList.add(0, new PortfolioItem("Bitcoin", "" ,"BTC", 1, 0.0, 0.0, 0.0, 0.0, 50.0));
        pfList.add(1, new PortfolioItem("Ethereum", "","ETH", 2, 0.0, 0.0, 0.0, 0.0, 30.0));
        pfList.add(2, new PortfolioItem("Tether", "","USDT", 3, 0.0, 0.0, 0.0, 0.0, 056.0));
        pfList.add(3, new PortfolioItem("Binance Coin", "","BNB" ,3, 0.0, 0.0, 0.0, 0.0, 023.0));
        pfList.add(4, new PortfolioItem("Cardano", "", "ADA",4, 0.0, 0.0, 0.0, 0.0, 01.0));
        pfList.add(5, new PortfolioItem("Litecoin", "","LTC", 5, 0.0, 0.0, 0.0, 0.0, 032.0));
        pfList.add(6, new PortfolioItem("Stellar", "", "XLM",6, 0.0, 0.0, 0.0, 0.0, 012.0));
        pfList.add(7, new PortfolioItem("Polkadot", "","DOT", 7, 0.0, 0.0, 0.0, 0.0, 03.0));

        user = new UserInfo("aasfjwd@gmail.com", "", pfList, new ArrayList<Integer>());
    }

}