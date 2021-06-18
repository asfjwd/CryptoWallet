package com.cse2216.cryptowallet.adapters;

import android.annotation.SuppressLint;
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

import java.util.List;

public class PortfolioAdapter extends RecyclerView.Adapter<PortfolioAdapter.PortfolioViewHolder> {

    //Constants
    String upArrow = Character.toString((char)(8593));
    String downArrow = Character.toString((char)(8595));
    String leftSymbol = Character.toString((char)(8605));
    Integer negativeRed = Color.parseColor("#FF1744");
    Integer positiveGreen = Color.parseColor("#00E676");

    List<PortfolioItem> portfolioItems;

    public PortfolioAdapter(List<PortfolioItem> portfolioItems){
        this.portfolioItems = portfolioItems;
    }

    public PortfolioViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.portfolio_item_layout, parent, false);
        return new PortfolioViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull @NotNull PortfolioAdapter.PortfolioViewHolder holder, int position) {

        holder.buyPrice.setText(String.format("%.2f", portfolioItems.get(position).getBuyingPrice()) + " $");
        holder.ltp.setText(String.format("%.2f", portfolioItems.get(position).getLatestPrice()) + " $");
        holder.currencyName.setText(portfolioItems.get(position).getName());
        holder.position.setText(String.format("%.2f", portfolioItems.get(position).getPosition()));
        Double gain = portfolioItems.get(position).getGain();
        Double gainPercentage = portfolioItems.get(position).getGainPercentage();
        holder.gain.setText(String.format("%.2f", gain));
        holder.gainPercentage.setText(String.format("%.2f", gainPercentage) + " %");
        holder.gainPercentage.setTypeface(null, Typeface.ITALIC);
        holder.leftSymbol.setText(leftSymbol);
        if(gain > 0){
            holder.gain.setTextColor(positiveGreen);
            holder.gainPercentage.setTextColor(positiveGreen);
            holder.arrow.setText(upArrow);
            holder.arrow.setTextColor(positiveGreen);
            holder.leftSymbol.setTextColor(positiveGreen);
        }
        else {
            holder.leftSymbol.setScaleY(-1);
            holder.leftSymbol.setScaleX(1);
            holder.gain.setTextColor(negativeRed);
            holder.gainPercentage.setTextColor(negativeRed);
            holder.arrow.setText(downArrow);
            holder.arrow.setTextColor(negativeRed);
            holder.leftSymbol.setTextColor(negativeRed);
        }
    }

    @Override
    public int getItemCount() {
        return portfolioItems.size();
    }

    public class PortfolioViewHolder extends RecyclerView.ViewHolder{
        ImageView imgIcon;
        TextView buyPrice, ltp, currencyName, position, gain, gainPercentage, arrow, leftSymbol;

        public PortfolioViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imgIcon = itemView.findViewById(R.id.portfolio_img_icon);
            buyPrice = itemView.findViewById(R.id.portfolio_buy_price_id);
            ltp = itemView.findViewById(R.id.portfolio_ltp_id);
            currencyName = itemView.findViewById(R.id.currency_name);
            position = itemView.findViewById(R.id.portfolio_position_id);
            gain = itemView.findViewById(R.id.portfolio_gain_id);
            gainPercentage = itemView.findViewById(R.id.portfolio_gain_percentage_id);
            arrow = itemView.findViewById(R.id.portfolio_arrow_id);
            leftSymbol = itemView.findViewById(R.id.portfolio_left_symbol);
        }
    }
}
