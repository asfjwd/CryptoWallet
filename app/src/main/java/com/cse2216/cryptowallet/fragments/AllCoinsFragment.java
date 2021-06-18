package com.cse2216.cryptowallet.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.cse2216.cryptowallet.R;
import com.cse2216.cryptowallet.activities.MainActivity;
import com.cse2216.cryptowallet.adapters.AllCoinsAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class AllCoinsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private View rootView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView allCoinsItemRecyclerView;
    MainActivity rootActivity;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.all_coins_fragment_layout,container,false);
        rootActivity = (MainActivity) getActivity();
        allCoinsItemRecyclerView = rootView.findViewById(R.id.all_coins_fragment);
        swipeRefreshLayout = rootView.findViewById(R.id.all_coins_fragment_swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(this);
        allCoinsItemRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        allCoinsItemRecyclerView.setHasFixedSize(true);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                updateList();
            }
        });
        return rootView;
    }

    private void updateList() {
        swipeRefreshLayout.setRefreshing(true);
<<<<<<< HEAD
        Random rand = new Random();
        for(int i = 0; i < rootActivity.coins.size(); i++){
            rootActivity.coins.get(i).setLatestPrice(rand.nextDouble() * 100.0);
            rootActivity.coins.get(i).setChange(rand.nextDouble() * 100.0 - 50);
            rootActivity.coins.get(i).setVolume(rand.nextDouble() * 100.0);
        }
        allCoinsItemRecyclerView.setAdapter(new AllCoinsAdapter(rootActivity.coins, rootActivity.user.watchList));
=======
        rootActivity.lunarAPI.updateCoins();
        allCoinsItemRecyclerView.setAdapter(new AllCoinsAdapter(rootActivity.coins));
>>>>>>> 1ae8c2084e88f80b8e8df4b2fca073d72cb3ff85
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        updateList();
    }
}
