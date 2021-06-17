package com.cse2216.cryptowallet.adapters;

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
        holder.buyPrice.setText(portfolioItems.get(position).getBuyingPrice().toString());
        holder.ltp.setText(portfolioItems.get(position).getLatestPrice().toString());
        holder.currencyName.setText(portfolioItems.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return portfolioItems.size();
    }

    public class PortfolioViewHolder extends RecyclerView.ViewHolder{
        ImageView imgIcon;
        TextView buyPrice, ltp, currencyName;

        public PortfolioViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imgIcon = itemView.findViewById(R.id.img_icon);
            buyPrice = itemView.findViewById(R.id.buyPriceId);
            ltp = itemView.findViewById(R.id.ltpId);
            currencyName = itemView.findViewById(R.id.currency_name);
        }
    }
}
