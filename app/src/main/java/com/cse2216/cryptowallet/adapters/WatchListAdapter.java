package com.cse2216.cryptowallet.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cse2216.cryptowallet.R;
import com.cse2216.cryptowallet.classes.domain.Coin;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class WatchListAdapter extends RecyclerView.Adapter<WatchListAdapter.WatchListViewHolder> {

    String upArrow = Character.toString((char)(8593));
    String downArrow = Character.toString((char)(8595));
    String leftSymbol = Character.toString((char)(8605));
    Context context;
    List<Coin> allCoins;
    ArrayList<Integer> watchList;

    public WatchListAdapter(List<Coin> allCoins, ArrayList<Integer> watchList, Context context){
        this.allCoins = allCoins;
        this.watchList = watchList;
        this.context = context;
    }

    public WatchListViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.watchlist_item_layout, parent, false);
        return new WatchListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull WatchListAdapter.WatchListViewHolder holder, int pos) {

        String symbol = allCoins.get(pos).getSymbol();
        holder.imgIcon.setImageResource(context.getResources().getIdentifier(symbol.toLowerCase(),"drawable", context.getPackageName()));
        holder.imgIcon.getLayoutParams().height =150 ;
        holder.imgIcon.getLayoutParams().width = 150 ;


        int position = watchList.get(pos);
        holder.currencyName.setText(allCoins.get(position).getName());
        holder.ltp.setText(String.format("%.2f", allCoins.get(position).getLatestPrice()) + " $");
        Double change_24hr = allCoins.get(position).getChange();
        holder.change_24hr.setText(String.format("%.2f", change_24hr) + " %");
        holder.volume.setText(String.format("%.2f", allCoins.get(position).getVolume()) + " $");
        holder.leftSymbol.setText(leftSymbol);
        if(change_24hr > 0){
            holder.change_24hr.setTextColor(context.getColor(R.color.positiveGreen));
            holder.arrow.setText(upArrow);
            holder.arrow.setTextColor(context.getColor(R.color.positiveGreen));
            holder.leftSymbol.setTextColor(context.getColor(R.color.positiveGreen));
            holder.leftSymbol.setScaleY(1);
        }
        else {
            holder.change_24hr.setTextColor(context.getColor(R.color.negativeRed));
            holder.arrow.setText(downArrow);
            holder.arrow.setTextColor(context.getColor(R.color.negativeRed));
            holder.leftSymbol.setTextColor(context.getColor(R.color.negativeRed));
            holder.leftSymbol.setScaleY(-1);
        }
    }

    @Override
    public int getItemCount() {
        return watchList.size();
    }

    public class WatchListViewHolder extends RecyclerView.ViewHolder {
        ImageView imgIcon;
        TextView currencyName, ltp, change_24hr, volume, arrow, leftSymbol;
        public WatchListViewHolder(View itemView) {
            super(itemView);

            imgIcon = itemView.findViewById(R.id.watchlist_img_icon);
            currencyName = itemView.findViewById(R.id.watchlist_currency_name);
            ltp = itemView.findViewById(R.id.watchlist_ltp_id);
            change_24hr = itemView.findViewById(R.id.watchlist_change_24hr_id);
            volume = itemView.findViewById(R.id.watchlist_volume_id);
            arrow = itemView.findViewById(R.id.watchlist_arrow_id);
            leftSymbol = itemView.findViewById(R.id.watchlist_left_symbol);

        }
    }
}
