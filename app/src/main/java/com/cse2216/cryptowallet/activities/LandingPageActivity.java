package com.cse2216.cryptowallet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.cse2216.cryptowallet.R;
import com.cse2216.cryptowallet.adapters.LandingPageAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LandingPageActivity extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;
    public FirebaseAuth mAuth;
    public FirebaseUser user ;
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
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser()!=null)
            startMainActivity();

    }
    private void startMainActivity() {
        System.out.println("Starting Activity");
        Intent intent = new Intent(this, MainActivity.class);
        Log.d("wildcard","wilcard_login");
        intent.putExtra("loginType", "signin"); //sending email to MainActivity
        startActivity(intent);
    }

    public void onBackPressed() {
        return;
    }
}