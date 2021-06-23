package com.cse2216.cryptowallet.adapters;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cse2216.cryptowallet.R;
import com.cse2216.cryptowallet.activities.MainActivity;
import com.cse2216.cryptowallet.classes.domain.Coin;
import com.cse2216.cryptowallet.classes.domain.UserInfo;
import com.cse2216.cryptowallet.fragments.AllCoinsFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AllCoinsAdapter extends RecyclerView.Adapter<AllCoinsAdapter.AllCoinsViewHolder> {

    //Constants
    String upArrow = Character.toString((char)(8593));
    String downArrow = Character.toString((char)(8595));
    String leftSymbol = Character.toString((char)(8605));
    Integer negativeRed = Color.parseColor("#FF1744");
    Integer positiveGreen = Color.parseColor("#00E676");

    List<Coin> allCoins;
    ArrayList<Integer> watchList;


    public AllCoinsAdapter(List<Coin> allCoins, ArrayList<Integer> watchList){
        this.allCoins = allCoins;
        this.watchList = watchList;
    }

    public AllCoinsViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.all_coins_item_layout, parent, false);
        return new AllCoinsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AllCoinsAdapter.AllCoinsViewHolder holder, int position) {
        holder.currencyName.setText(allCoins.get(position).getName());
        holder.ltp.setText(String.format("%.2f", allCoins.get(position).getLatestPrice()) + " $");
        Double change_24hr = allCoins.get(position).getChange();
        holder.change_24hr.setText(String.format("%.2f", change_24hr) + " %");
        holder.volume.setText(String.format("%.2f", allCoins.get(position).getVolume()) + " $");
        holder.leftSymbol.setText(leftSymbol);

        Boolean isInWatchList = false;
        for(Integer watchListItem : watchList){
            if(watchListItem == position){
                isInWatchList = true;
                break;
            }
        }

        if(isInWatchList) {
            holder.watchlistToggle.setChecked(true);
        }

        if(change_24hr > 0){
            holder.change_24hr.setTextColor(positiveGreen);
            holder.arrow.setText(upArrow);
            holder.arrow.setTextColor(positiveGreen);
            holder.leftSymbol.setTextColor(positiveGreen);
        }
        else {
            holder.change_24hr.setTextColor(negativeRed);
            holder.arrow.setText(downArrow);
            holder.arrow.setTextColor(negativeRed);
            holder.leftSymbol.setTextColor(negativeRed);
        }


        holder.watchlistToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    watchList.add(position);
                } else {
                    int idxToRemove = 0;
                    for(int i = 0; i < watchList.size(); i++){
                        if(watchList.get(i) == position){
                            idxToRemove = i; break;
                        }
                    }
                    watchList.remove(idxToRemove);
                }




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
                            dummyUser.setWatchList(watchList);
                            firebaseDatabase.getReference("UserInfo").child(userToken).setValue(dummyUser);
                            Log.d("firebase", dummyUser.toString());
                        }
                    }
                });



            }
        });
    }

    @Override
    public int getItemCount() {
        return allCoins.size();
    }

    public static class AllCoinsViewHolder extends RecyclerView.ViewHolder {
        ImageView imgIcon;
        TextView currencyName, ltp, change_24hr, volume, arrow, leftSymbol;
        SwitchMaterial watchlistToggle;
        public AllCoinsViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            imgIcon = itemView.findViewById(R.id.all_coins_img_icon);
            currencyName = itemView.findViewById(R.id.all_coins_currency_name);
            ltp = itemView.findViewById(R.id.all_coins_ltp_id);
            change_24hr = itemView.findViewById(R.id.all_coins_change_24hr_id);
            volume = itemView.findViewById(R.id.all_coins_volume_id);
            arrow = itemView.findViewById(R.id.all_coins_arrow_id);
            leftSymbol = itemView.findViewById(R.id.all_coins_left_symbol);

            watchlistToggle = itemView.findViewById(R.id.all_coins_watchlist_toggle);
        }
    }
}
