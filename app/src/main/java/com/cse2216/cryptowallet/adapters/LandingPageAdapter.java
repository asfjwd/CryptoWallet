package com.cse2216.cryptowallet.adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.cse2216.cryptowallet.R;
import com.cse2216.cryptowallet.fragments.LoginTabFragment;
import com.cse2216.cryptowallet.fragments.RegisterTabFragment;

import org.jetbrains.annotations.NotNull;

public class LandingPageAdapter extends FragmentPagerAdapter {

    private Context mContext;
    final int totalTabs = 2;

    private static final int[] TAB_TITLES = new int[]{R.string.landing_tab_text_1, R.string.landing_tab_text_2};

    public LandingPageAdapter(FragmentManager fm, Context context){
        super(fm);
        this.mContext = context;
    }

    @NonNull
    @NotNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 :
                return new LoginTabFragment();
            case 1:
                return new RegisterTabFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return totalTabs;
    }

    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }
}
