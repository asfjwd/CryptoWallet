package com.cse2216.cryptowallet.fragments;

import android.os.Bundle;
import android.util.Log;
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
import com.cse2216.cryptowallet.adapters.PortfolioAdapter;
import com.cse2216.cryptowallet.classes.domain.Coin;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Random;

public class PortfolioFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private View rootView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView portfolioItemRecyclerView;
    MainActivity rootActivity;
    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.portfolio_fragment_layout, container, false);
        rootActivity = (MainActivity) getActivity();
        portfolioItemRecyclerView = rootView.findViewById(R.id.portfolio_fragment);
        swipeRefreshLayout = rootView.findViewById(R.id.portfolio_fragment_swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(this);
        portfolioItemRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        portfolioItemRecyclerView.setHasFixedSize(true);
        updateList();
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                updateList();
            }
        });
        return rootView;
    }

    @Override
    public void onRefresh() {
        updateList();
    }

    private void updateList() {
        rootActivity.lunarAPI.updateCoins();
        swipeRefreshLayout.setRefreshing(true);
        ArrayList < Coin > coinArrayList = rootActivity.lunarAPI.getCoinArrayList();
        Random rand = new Random();
        for(int i = 0; i < rootActivity.user.portfolioItems.size(); i++){
            rootActivity.user.portfolioItems.get(i).setBuyingPrice(rand.nextDouble() * 500);
            rootActivity.user.portfolioItems.get(i).setPosition(rand.nextDouble() * 100);
            Log.d("update" ,rootActivity.user.portfolioItems.get(i).getSymbol() );
            for(int j = 0 ; j < coinArrayList.size() ; j++){
                Log.d("update" ,coinArrayList.get(j).getSymbol() );
                // Log.d("update" ,rootActivity.user.portfolioItems.get(i).getSymbol() );
                if(rootActivity.user.portfolioItems.get(i).getSymbol().equals( coinArrayList.get(j).getSymbol() )){
                    rootActivity.user.portfolioItems.get(i).setLatestPrice( coinArrayList.get(j).getLatestPrice()  );
                }
            }
            //rootActivity.user.portfolioItems.get(i).setLatestPrice(rand.nextDouble() * 100);
        }
        portfolioItemRecyclerView.setAdapter(new PortfolioAdapter(rootActivity.user.portfolioItems));
        swipeRefreshLayout.setRefreshing(false);
    }
}
