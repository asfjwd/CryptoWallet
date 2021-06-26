package com.cse2216.cryptowallet.adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.cse2216.cryptowallet.R;
import com.cse2216.cryptowallet.activities.MainActivity;
import com.cse2216.cryptowallet.classes.domain.PortfolioItem;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PortfolioAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //Constants
    String upArrow = Character.toString((char)(8593));
    String downArrow = Character.toString((char)(8595));
    String leftSymbol = Character.toString((char)(8605));

    Context context;
    List<PortfolioItem> portfolioItems;
    List<Boolean> recyclerMenuStatus;

    private final int SHOW_MENU = 1;
    private final int HIDE_MENU = 2;

    public PortfolioAdapter(List<Boolean> recyclerMenuStatus, MainActivity activity){
        this.portfolioItems = activity.user.portfolioItems;
        this.recyclerMenuStatus = recyclerMenuStatus;
        this.context = activity;
    }

    @Override
    public int getItemViewType(int position) {
        if(recyclerMenuStatus.get(position)){
            return SHOW_MENU;
        }else{
            return HIDE_MENU;
        }
    }

    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View v;
        if(viewType==SHOW_MENU){
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.recycler_menu, parent, false);
            return new MenuViewHolder(view);
        }else{
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.portfolio_item_layout, parent, false);
            return new PortfolioViewHolder(view);

        }
    }

    String parseDouble(Double val){

        final double highLimit = 1e8, lowLimit = 1e5;

        if(val > highLimit){
            return String.format("%.2fM", val / 1000000.0);
        }
        else if(val > lowLimit){
            return String.format("%.2fK", val / 1000.0);
        }
        return String.format("%.2f", val);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof MenuViewHolder){
            ((MenuViewHolder) holder).back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    closeMenu();
                }
            });

            ((MenuViewHolder) holder).cashout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Dialog dialog;
                    dialog = new Dialog(context);
                    dialog.setContentView(R.layout.dialog_cashout);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();

                    EditText amount = dialog.findViewById(R.id.cashout_quantity);
                    TextView cancelButton = dialog.findViewById(R.id.cashout_cancel_button);
                    TextView confirmButton = dialog.findViewById(R.id.cashout_confirm_button);

                    confirmButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            double new_position = (portfolioItems.get(position).getPosition() - Double.parseDouble(amount.getText().toString()));
                            if(new_position < 0){
                                Toast.makeText(context, "Your position is smaller than " + amount.getText(), Toast.LENGTH_SHORT).show();
                            }
                            else {
                                dialog.dismiss();
                                portfolioItems.get(position).setPosition(new_position);
                                notifyDataSetChanged();
                            }
                        }
                    });

                    cancelButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                }
            });

            ((MenuViewHolder) holder).remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    portfolioItems.remove(position);
                    recyclerMenuStatus.remove(position);
                    notifyDataSetChanged();
                }
            });
        }
        else {
            ((PortfolioViewHolder) holder).buyPrice.setText(parseDouble(portfolioItems.get(position).getBuyingPrice()) + " $");
            ((PortfolioViewHolder) holder).ltp.setText(parseDouble(portfolioItems.get(position).getLatestPrice()) + " $");
            ((PortfolioViewHolder) holder).currencyName.setText(portfolioItems.get(position).getName());
            ((PortfolioViewHolder) holder).position.setText(parseDouble(portfolioItems.get(position).getPosition()));
            Double gain = portfolioItems.get(position).getGain();
            Double gainPercentage = portfolioItems.get(position).getGainPercentage();
            ((PortfolioViewHolder) holder).gain.setText(parseDouble(gain) + " $");
            ((PortfolioViewHolder) holder).gain.setTypeface(null, Typeface.ITALIC);
            ((PortfolioViewHolder) holder).gainPercentage.setText(parseDouble(gainPercentage) + " %");
            ((PortfolioViewHolder) holder).gainPercentage.setTypeface(null, Typeface.ITALIC);
            ((PortfolioViewHolder) holder).leftSymbol.setText(leftSymbol);
            ((PortfolioViewHolder) holder).currencySymbol.setText(portfolioItems.get(position).getSymbol());
            if(gain > 0){
                ((PortfolioViewHolder) holder).leftSymbol.setScaleY(1);
                ((PortfolioViewHolder) holder).gain.setTextColor(context.getColor(R.color.positiveGreen));
                ((PortfolioViewHolder) holder).gainPercentage.setTextColor(context.getColor(R.color.positiveGreen));
                ((PortfolioViewHolder) holder).arrow.setText(upArrow);
                ((PortfolioViewHolder) holder).arrow.setTextColor(context.getColor(R.color.positiveGreen));
                ((PortfolioViewHolder) holder).leftSymbol.setTextColor(context.getColor(R.color.positiveGreen));
            }
            else {
                ((PortfolioViewHolder) holder).leftSymbol.setScaleY(-1);
                ((PortfolioViewHolder) holder).gain.setTextColor(context.getColor(R.color.negativeRed));
                ((PortfolioViewHolder) holder).gainPercentage.setTextColor(context.getColor(R.color.negativeRed));
                ((PortfolioViewHolder) holder).arrow.setText(downArrow);
                ((PortfolioViewHolder) holder).arrow.setTextColor(context.getColor(R.color.negativeRed));
                ((PortfolioViewHolder) holder).leftSymbol.setTextColor(context.getColor(R.color.negativeRed));
            }
            String symbol = portfolioItems.get(position).getSymbol();
            ((PortfolioViewHolder) holder).imgIcon.setImageResource(context.getResources().getIdentifier(symbol.toLowerCase(),"drawable", context.getPackageName()));
            ((PortfolioViewHolder) holder).imgIcon.getLayoutParams().height =150 ;
            ((PortfolioViewHolder) holder).imgIcon.getLayoutParams().width = 150 ;
        }
    }

    public void showMenu(int position) {
        for(int i=0; i<recyclerMenuStatus.size(); i++){
            recyclerMenuStatus.set(i, false);
        }
        recyclerMenuStatus.set(position, true);
        notifyDataSetChanged();
    }


    public boolean isMenuShown() {
        for(int i=0; i<recyclerMenuStatus.size(); i++){
            if(recyclerMenuStatus.get(i)){
                return true;
            }
        }
        return false;
    }

    public void closeMenu() {
        for(int i=0; i<recyclerMenuStatus.size(); i++){
            recyclerMenuStatus.set(i, false);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return portfolioItems.size();
    }

    public class PortfolioViewHolder extends RecyclerView.ViewHolder{
        ImageView imgIcon;
        TextView buyPrice, ltp, currencyName, position, gain, gainPercentage, arrow, leftSymbol, currencySymbol;

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
            currencySymbol = itemView.findViewById(R.id.portfolio_currency_symbol);
        }
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder{
        LinearLayout back, cashout, remove;
        public MenuViewHolder(View itemView){

            super(itemView);
            back = itemView.findViewById(R.id.linearLayout_return);
            cashout = itemView.findViewById(R.id.linearLayout_cashout);
            remove = itemView.findViewById(R.id.linearLayout_delete);
        }
    }
}
