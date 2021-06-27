package com.cse2216.cryptowallet.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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

    public static List<Coin> coins = new ArrayList<Coin>();
    public static UserInfo user;
    public LunarAPI lunarAPI ;
    private TextView titleButton;
    private Toolbar toolbar;
    boolean doubleBackToExitPressedOnce = false;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        FirebaseAuth moth = FirebaseAuth.getInstance();

        if(moth.getCurrentUser()==null){
            Log.d("Login", "User Cache not found , loggin out");
            startLandingActivity();
            return  ;
        }
        lunarAPI = new LunarAPI(this);
        populateData();
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        MainPageAdapter MainPageAdapter = new MainPageAdapter(this, getSupportFragmentManager());
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
    }
    private void populateData() {
//        hardcode or from database
        user = new UserInfo("x","x",new ArrayList<PortfolioItem>(),new ArrayList <Integer >());
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
                    user.setEmail(dummyUser.getEmail());
                    user.setToken(dummyUser.getToken());
                    user.setPortfolioItems(dummyUser.getPortfolioItems());
                    user.setWatchList(dummyUser.getWatchList());
//                    AllCoinsFragment.allCoinsItemRecyclerView.setAdapter(new AllCoinsAdapter(MainActivity.this));
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
        if(mAuth.getCurrentUser()!=null)
            mAuth.signOut();
        intent.putExtra("loginType", "loggedout"); //sending email to MainActivity
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseAuth moth = FirebaseAuth.getInstance();
        if(moth.getCurrentUser()==null){
            Log.d("Force", "Forceloggedout");
            startLandingActivity();
            return;
        }
        doubleBackToExitPressedOnce = false;
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            moveTaskToBack(true);
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            startLandingActivity();
            Toast.makeText(this, "Logging Out!", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}