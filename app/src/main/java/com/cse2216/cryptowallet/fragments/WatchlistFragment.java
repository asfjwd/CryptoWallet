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
import com.cse2216.cryptowallet.adapters.WatchListAdapter;

import org.jetbrains.annotations.NotNull;

public class WatchlistFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private View rootView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView watchListRecyclerView;
    WatchListAdapter watchListAdapter;
    MainActivity rootActivity;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.watchlist_fragment_layout,container,false);
        rootActivity = (MainActivity) getActivity();
        watchListRecyclerView = rootView.findViewById(R.id.watchlist_fragment);
        swipeRefreshLayout = rootView.findViewById(R.id.watchlist_fragment_swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(this);
        watchListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        watchListRecyclerView.setHasFixedSize(true);
        updateList();
        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && swipeRefreshLayout != null) {
            updateList();
        }
    }
    @Override
    public void onRefresh() {
        updateList();
    }

    private void updateList() {
        swipeRefreshLayout.setRefreshing(true);
        rootActivity.lunarAPI.updateCoins();
        if(watchListAdapter == null){
            watchListAdapter = new WatchListAdapter(rootActivity);
            watchListRecyclerView.setAdapter(watchListAdapter);
        }
        else watchListAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }


}
