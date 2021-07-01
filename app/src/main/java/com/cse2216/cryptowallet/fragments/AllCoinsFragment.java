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
    private AllCoinsAdapter allCoinsAdapter;
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
        updateList(2000);
        return rootView;
    }
    @Override
    public void onRefresh() {
        updateList(100);
    }
    private void updateList(int waitTime) {

        rootActivity.lunarAPI.updateCoins();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("AllCoinsList");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                swipeRefreshLayout.setRefreshing(true);
                swipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(allCoinsAdapter == null){
                            allCoinsItemRecyclerView.setAdapter(new AllCoinsAdapter(rootActivity));
                        }
                        else allCoinsAdapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, waitTime);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Log.d("AllCoinsFragment" ,"Error in database listener" );
            }
        });
    }


}
