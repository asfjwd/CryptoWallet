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
import com.cse2216.cryptowallet.adapters.PortfolioAdapter;
import com.cse2216.cryptowallet.classes.domain.PortfolioItem;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PortfolioFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private View rootView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView portfolioItemRecyclerView;
    List<PortfolioItem> portfolioItems = new ArrayList<PortfolioItem>();

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        initPortfolioItems();
        rootView = inflater.inflate(R.layout.portfolio_fragment_layout, container, false);
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
    private void initPortfolioItems() {
        portfolioItems.add(0, new PortfolioItem("bitcoin", "1", 1, 0.0, 0.0, 0.0, 0.0, 0.0));
        portfolioItems.add(1, new PortfolioItem("etherium", "2", 2, 0.0, 0.0, 0.0, 0.0, 0.0));
        portfolioItems.add(2, new PortfolioItem("tether", "3", 3, 0.0, 0.0, 0.0, 0.0, 0.0));
        portfolioItems.add(3, new PortfolioItem("binance", "3", 3, 0.0, 0.0, 0.0, 0.0, 0.0));
        portfolioItems.add(4, new PortfolioItem("cardano", "4", 4, 0.0, 0.0, 0.0, 0.0, 0.0));
        portfolioItems.add(5, new PortfolioItem("dogecoin", "5", 5, 0.0, 0.0, 0.0, 0.0, 0.0));
        portfolioItems.add(6, new PortfolioItem("XRP", "6", 6, 0.0, 0.0, 0.0, 0.0, 0.0));
        portfolioItems.add(7, new PortfolioItem("polkadot", "7", 7, 0.0, 0.0, 0.0, 0.0, 0.0));
    }

    @Override
    public void onRefresh() {
        updateList();
    }

    private void updateList() {
        swipeRefreshLayout.setRefreshing(true);
        Random rand = new Random();
        for(int i = 0; i < portfolioItems.size(); i++){
            portfolioItems.get(i).setBuyingPrice(rand.nextDouble() * 100);
            portfolioItems.get(i).setLatestPrice(rand.nextDouble() * 100);
            portfolioItems.get(i).setPosition(rand.nextDouble() * 100);
        }
        portfolioItemRecyclerView.setAdapter(new PortfolioAdapter(portfolioItems));
        swipeRefreshLayout.setRefreshing(false);

    }
}
