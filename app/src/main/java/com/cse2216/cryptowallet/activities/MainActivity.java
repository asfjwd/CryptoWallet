package com.cse2216.cryptowallet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.cse2216.cryptowallet.R;
import com.cse2216.cryptowallet.adapters.AllCoinsAdapter;
import com.cse2216.cryptowallet.adapters.MainPageAdapter;
import com.cse2216.cryptowallet.classes.domain.Coin;
import com.cse2216.cryptowallet.classes.domain.PortfolioItem;
import com.cse2216.cryptowallet.classes.domain.UserInfo;
import com.cse2216.cryptowallet.classes.helper.LunarAPI;
import com.cse2216.cryptowallet.fragments.AllCoinsFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static List<Coin> coins = new ArrayList<Coin>();
    public static UserInfo user;
    public LunarAPI lunarAPI ;
    private TextView titleButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        lunarAPI = new LunarAPI(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainPageAdapter MainPageAdapter = new MainPageAdapter(this, getSupportFragmentManager());
        titleButton = (TextView) findViewById(R.id.title) ;
        titleButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.d("TitleButton", "titlebuttonpressed");
                startLandingActivity();
                return false;
            }

        });
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(MainPageAdapter);
        viewPager.setOffscreenPageLimit(3);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        tabs.getTabAt(1).select();
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
        pfList.add(0, new PortfolioItem("Bitcoin", "" ,"BTC", 1, 0.0, 0.0, 0.0, 100.0, 50.0));
        pfList.add(1, new PortfolioItem("Ethereum", "","ETH", 2, 0.0, 0.0, 0.0, 100.0, 30.0));
        pfList.add(2, new PortfolioItem("Tether", "","USDT", 3, 0.0, 0.0, 0.0, 100.0, 056.0));
        pfList.add(3, new PortfolioItem("Binance Coin", "","BNB" ,3, 0.0, 0.0, 0.0, 100.0, 023.0));
        pfList.add(4, new PortfolioItem("Cardano", "", "ADA",4, 0.0, 0.0, 0.0, 100.0, 01.0));
        pfList.add(5, new PortfolioItem("Litecoin", "","LTC", 5, 0.0, 0.0, 0.0, 100.0, 032.0));
        pfList.add(6, new PortfolioItem("Bitcoin Cash", "", "BCH",6, 0.0, 0.0, 0.0, 100.0, 012.0));
        pfList.add(7, new PortfolioItem("Polkadot", "","DOT", 7, 0.0, 0.0, 0.0, 100.0, 03.0));
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
                    AllCoinsFragment.allCoinsItemRecyclerView.setAdapter(new AllCoinsAdapter(MainActivity.coins, user.watchList,MainActivity.this));
                    firebaseDatabase.getReference("UserInfo").child(userToken).setValue(dummyUser);
                    Log.d("firebase", dummyUser.toString());
                }
            }
        });


        //Log.d("populate" , user.toString()) ;
        coins = lunarAPI.getCoinArrayList();
    }

    private void startLandingActivity() {
       // System.out.println("Starting Activity");
        Intent intent = new Intent( MainActivity.this , LandingPageActivity.class);
        FirebaseAuth mAuth= FirebaseAuth.getInstance();
        mAuth.signOut();
        intent.putExtra("loginType", "loggedout"); //sending email to MainActivity
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}