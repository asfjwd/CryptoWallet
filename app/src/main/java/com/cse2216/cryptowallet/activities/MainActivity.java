package com.cse2216.cryptowallet.activities;

import android.icu.text.CaseMap;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.cse2216.cryptowallet.R;
import com.cse2216.cryptowallet.adapters.MainPageAdapter;
import com.cse2216.cryptowallet.classes.domain.Coin;
import com.cse2216.cryptowallet.classes.domain.PortfolioItem;
import com.cse2216.cryptowallet.classes.domain.UserInfo;
import com.cse2216.cryptowallet.classes.helper.LunarAPI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public List<Coin> coins = new ArrayList<Coin>();
    public UserInfo user;
    public LunarAPI lunarAPI ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        lunarAPI = new LunarAPI(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainPageAdapter MainPageAdapter = new MainPageAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(MainPageAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        try{
            Log.d("intent",mAuth.getCurrentUser().getEmail());
            Log.d("intent" , getIntent().getExtras().getString("loginType"));
        }
        catch(Exception e) {

        }
        lunarAPI.updateCoins();
        populateData();
    }
    private void populateData() {
//        hardcode or from database
        ArrayList<PortfolioItem> pfList = new ArrayList<PortfolioItem>();
        pfList.add(0, new PortfolioItem("Bitcoin", "" ,"BTC", 1, 0.0, 0.0, 0.0, 0.0, 50.0));
        pfList.add(1, new PortfolioItem("Ethereum", "","ETH", 2, 0.0, 0.0, 0.0, 0.0, 30.0));
        pfList.add(2, new PortfolioItem("Tether", "","USDT", 3, 0.0, 0.0, 0.0, 0.0, 056.0));
        pfList.add(3, new PortfolioItem("Binance Coin", "","BNB" ,3, 0.0, 0.0, 0.0, 0.0, 023.0));
        pfList.add(4, new PortfolioItem("Cardano", "", "ADA",4, 0.0, 0.0, 0.0, 0.0, 01.0));
        pfList.add(5, new PortfolioItem("Litecoin", "","LTC", 5, 0.0, 0.0, 0.0, 0.0, 032.0));
        pfList.add(6, new PortfolioItem("Stellar", "", "XLM",6, 0.0, 0.0, 0.0, 0.0, 012.0));
        pfList.add(7, new PortfolioItem("Polkadot", "","DOT", 7, 0.0, 0.0, 0.0, 0.0, 03.0));
        user = new UserInfo("x","x",pfList,new ArrayList <Integer >());
        String userToken = FirebaseAuth.getInstance().getCurrentUser().getUid() ;
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance() ;
        firebaseDatabase.getReference("UserInfo").child(userToken).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    UserInfo dummyUser = task.getResult().getValue(UserInfo.class);
                    user = new UserInfo(dummyUser.getEmail(), dummyUser.getToken(), pfList, dummyUser.getWatchList());
                    firebaseDatabase.getReference("UserInfo").child(userToken).setValue(dummyUser);
                    Log.d("firebase", dummyUser.toString());
                }
            }
        });


        //Log.d("populate" , user.toString()) ;
        coins = lunarAPI.getCoinArrayList();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}