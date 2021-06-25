package com.cse2216.cryptowallet.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.cse2216.cryptowallet.R;
import com.cse2216.cryptowallet.activities.MainActivity;
import com.cse2216.cryptowallet.adapters.AllCoinsAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class AllCoinsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private View rootView;
    private SwipeRefreshLayout swipeRefreshLayout;
    public static RecyclerView allCoinsItemRecyclerView;
    private AllCoinsAdapter adapter ;
    MainActivity rootActivity;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.all_coins_fragment_layout,container,false);
        rootActivity = (MainActivity) getActivity();
        adapter = new AllCoinsAdapter(rootActivity.coins , rootActivity.user.watchList ,rootActivity);

        allCoinsItemRecyclerView = rootView.findViewById(R.id.all_coins_fragment);
        swipeRefreshLayout = rootView.findViewById(R.id.all_coins_fragment_swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(this);
        allCoinsItemRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        allCoinsItemRecyclerView.setHasFixedSize(true);
        allCoinsItemRecyclerView.setAdapter(new AllCoinsAdapter(rootActivity.coins, rootActivity.user.watchList , rootActivity));
        Log.d("Call" , "onCreateView Called");
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                updateList();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        return rootView;
    }

    private void updateList() {

        rootActivity.lunarAPI.updateCoins();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("AllCoinsList");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                swipeRefreshLayout.setRefreshing(true);
                allCoinsItemRecyclerView.setAdapter(new AllCoinsAdapter(rootActivity.coins, rootActivity.user.watchList,rootActivity));
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });



    }

    @Override
    public void onRefresh() {
        updateList();
    }
}
