package com.cse2216.cryptowallet.adapters;

import android.content.Context;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.cse2216.cryptowallet.fragments.AllCoinsFragment;
import com.cse2216.cryptowallet.fragments.PortfolioFragment;
import com.cse2216.cryptowallet.fragments.WatchlistFragment;
import com.cse2216.cryptowallet.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class MainPageAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.main_tab_text_1, R.string.main_tab_text_2, R.string.main_tab_text_3};
    private final Context mContext;

    public MainPageAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }
    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new PortfolioFragment();
                break;
            case 1:
                fragment = new AllCoinsFragment();
                break;
            case 2:
                fragment = new WatchlistFragment();
                break;
        }
        return  fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return 3;
    }
}