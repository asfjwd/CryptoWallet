package com.cse2216.cryptowallet.adapters;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cse2216.cryptowallet.R;
import com.cse2216.cryptowallet.classes.domain.PortfolioItem;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PortfolioAdapter extends RecyclerView.Adapter<PortfolioAdapter.PortfolioViewHolder> {

    List<PortfolioItem> portfolioItems = new ArrayList<PortfolioItem>();

    public PortfolioAdapter(List<PortfolioItem> portfolioItems){
        this.portfolioItems = portfolioItems;
    }

    public PortfolioViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.portfolio_item_layout, parent, false);
        return new PortfolioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PortfolioAdapter.PortfolioViewHolder holder, int position) {

        String up = Character.toString((char)(8593));
        String down = Character.toString((char)(8595));

        holder.buyPrice.setText(String.format("%.2f", portfolioItems.get(position).getBuyingPrice()));
        holder.ltp.setText(String.format("%.2f", portfolioItems.get(position).getLatestPrice()));
        holder.currencyName.setText(portfolioItems.get(position).getName());
        holder.position.setText(String.format("%.4f", portfolioItems.get(position).getPosition()));
        Double gain = portfolioItems.get(position).getGain();
        Double gainPercentage = portfolioItems.get(position).getGainPercentage();
        holder.gain.setText(String.format("%.2f", gain));
        holder.gainPercentage.setText(String.format("%.2f", gainPercentage) + " %");
        holder.gainPercentage.setTypeface(null, Typeface.ITALIC);
        if(gain > 0){
            holder.gain.setTextColor(Color.GREEN);
            holder.gainPercentage.setTextColor(Color.GREEN);
            holder.arrow.setText(up);
            holder.arrow.setTextColor(Color.GREEN);
        }
        else {
            holder.gain.setTextColor(Color.RED);
            holder.gainPercentage.setTextColor(Color.RED);
            holder.arrow.setText(down);
            holder.arrow.setTextColor(Color.RED);
        }
    }

    @Override
    public int getItemCount() {
        return portfolioItems.size();
    }

    public class PortfolioViewHolder extends RecyclerView.ViewHolder{
        ImageView imgIcon;
        TextView buyPrice, ltp, currencyName, position, gain, gainPercentage, arrow;

        public PortfolioViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imgIcon = itemView.findViewById(R.id.img_icon);
            buyPrice = itemView.findViewById(R.id.buyPriceId);
            ltp = itemView.findViewById(R.id.ltpId);
            currencyName = itemView.findViewById(R.id.currency_name);
            position = itemView.findViewById(R.id.positionId);
            gain = itemView.findViewById(R.id.gainId);
            gainPercentage = itemView.findViewById(R.id.gainPercentageId);
            arrow = itemView.findViewById(R.id.arrowId);
        }
    }
}
