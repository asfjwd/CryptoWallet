package com.cse2216.cryptowallet.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.cse2216.cryptowallet.R;
import com.cse2216.cryptowallet.activities.MainActivity;
import com.cse2216.cryptowallet.adapters.PortfolioAdapter;
import com.cse2216.cryptowallet.classes.domain.Coin;
import com.cse2216.cryptowallet.classes.domain.PortfolioItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PortfolioFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private View rootView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView portfolioItemRecyclerView;
    PortfolioAdapter portfolioAdapter;
    MainActivity rootActivity;
    List<Boolean> recyclerMenuStatus = new ArrayList<Boolean>();

    @SuppressLint("NewApi")
    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.portfolio_fragment_layout, container, false);
        rootActivity = (MainActivity) getActivity();
        portfolioItemRecyclerView = rootView.findViewById(R.id.portfolio_fragment);
        swipeRefreshLayout = rootView.findViewById(R.id.portfolio_fragment_swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(this);
        portfolioItemRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        portfolioItemRecyclerView.setHasFixedSize(true);
        updateList();
        setupTouchSwipe();
        processFloatingButton();
        for(int i = 0; i < rootActivity.user.portfolioItems.size(); i++) recyclerMenuStatus.add(false);
        return rootView;
    }

    private void processFloatingButton() {
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.floatingActionButton);
        if(fab == null){
            Log.i("TAG", "Fab is null");
            return;
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(rootActivity);
                dialog.setContentView(R.layout.dialog_add_coin);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                EditText buyingPrice = dialog.findViewById(R.id.add_buy_price);
                EditText quantity = dialog.findViewById(R.id.add_quantity);
                TextView confirmButton = dialog.findViewById(R.id.add_confirm_button);
                TextView cancelButton = dialog.findViewById(R.id.add_cancel_button);

                TextInputLayout textInputLayout = (TextInputLayout) dialog.findViewById(R.id.add_dropdown);
                AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) dialog.findViewById(R.id.dropdown_autocomplete);
                ArrayList<String> coins = new ArrayList<String>();
                for(Coin coin : rootActivity.coins){
                    coins.add(coin.getName());
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(rootActivity, R.layout.dropdown_item, coins);
                autoCompleteTextView.setAdapter(arrayAdapter);
                autoCompleteTextView.setThreshold(1);
                dialog.show();

                confirmButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String selectedCoin = autoCompleteTextView.getText().toString();
                        Coin coin = getCoin(selectedCoin);
                        if(coin != null){
                            dialog.dismiss();
                            Double newBuyingPrice, newPosition;
                            int index = atPortfolio(selectedCoin);
                            newBuyingPrice = Double.parseDouble(buyingPrice.getText().toString());
                            newPosition = Double.parseDouble(quantity.getText().toString());
                            if(index != -1){
                                newBuyingPrice = (newBuyingPrice * newPosition) + rootActivity.user.portfolioItems.get(index).getBuyingPrice() * rootActivity.user.portfolioItems.get(index).getPosition();
                                newPosition = newPosition + rootActivity.user.portfolioItems.get(index).getPosition();
                                newBuyingPrice /= newPosition;
                                rootActivity.user.portfolioItems.get(index).setPosition(newPosition);
                                rootActivity.user.portfolioItems.get(index).setBuyingPrice(newBuyingPrice);
                            }
                            else{
                                rootActivity.user.portfolioItems.add(new PortfolioItem(coin, newPosition, newBuyingPrice));
                                recyclerMenuStatus.add(false);
                            }
                            updateList();
                        }
                        else {
                            Toast.makeText(rootActivity, "Entered Cryptocurrency is not in our list!", Toast.LENGTH_SHORT).show();
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
    }

    int atPortfolio(String selectedCoin) {
        for(int i = 0; i < rootActivity.user.portfolioItems.size(); i++){
            if(selectedCoin.equals(rootActivity.user.portfolioItems.get(i).getName())) return i;
        }
        return -1;
    }

    Coin getCoin(String selectedCoin) {
        for(int i = 0; i < rootActivity.coins.size(); i++){
            if(selectedCoin.equals(rootActivity.coins.get(i).getName())) return rootActivity.coins.get(i);
        }
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    void setupTouchSwipe(){
        ItemTouchHelper.SimpleCallback touchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            private ColorDrawable background;

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {

                switch (direction){
                    case ItemTouchHelper.LEFT:
                        portfolioItemRecyclerView.post(new Runnable() {
                            public void run() {
                                portfolioAdapter.showMenu(viewHolder.getAdapterPosition());
                            }
                        });
                        break;
                    case ItemTouchHelper.RIGHT:
                        portfolioItemRecyclerView.post(new Runnable() {
                            @Override
                            public void run() {
                                portfolioAdapter.closeMenu();
                            }
                        });
                        break;
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                if(recyclerMenuStatus.get(viewHolder.getAdapterPosition()) == false && dX > 0) return;
                if(dX > 0) background = new ColorDrawable(getResources().getColor(R.color.white));
                else background = new ColorDrawable(getResources().getColor(R.color.colorAccent));
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

                View itemView = viewHolder.itemView;

                if (dX > 0) {
                    background.setBounds(itemView.getLeft(), itemView.getTop(), itemView.getLeft() + ((int) dX), itemView.getBottom());
                } else if (dX < 0) {
                    background.setBounds(itemView.getRight() + ((int) dX), itemView.getTop(), itemView.getRight(), itemView.getBottom());
                } else {
                    background.setBounds(0, 0, 0, 0);
                }

                background.draw(c);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(touchHelperCallback);
        itemTouchHelper.attachToRecyclerView(portfolioItemRecyclerView);

        portfolioItemRecyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                portfolioItemRecyclerView.post(new Runnable() {
                    public void run() {
                        portfolioAdapter.closeMenu();
                    }
                });
            }
        });
    }

    @Override
    public void onRefresh() {
        updateList();
    }

    private void updateList() {
        rootActivity.lunarAPI.updateCoins();
        swipeRefreshLayout.setRefreshing(true);
        ArrayList < Coin > coinArrayList = rootActivity.lunarAPI.getCoinArrayList();
        Random rand = new Random();
        for(int i = 0; i < rootActivity.user.portfolioItems.size(); i++){
//            rootActivity.user.portfolioItems.get(i).setBuyingPrice(rand.nextDouble() * 500);
//            rootActivity.user.portfolioItems.get(i).setPosition(rand.nextDouble() * 100);
            Log.d("update" ,rootActivity.user.portfolioItems.get(i).getSymbol() );
            for(int j = 0 ; j < coinArrayList.size() ; j++){
                Log.d("update" ,coinArrayList.get(j).getSymbol() );
                // Log.d("update" ,rootActivity.user.portfolioItems.get(i).getSymbol() );
                if(rootActivity.user.portfolioItems.get(i).getSymbol().equals( coinArrayList.get(j).getSymbol() )){
                    rootActivity.user.portfolioItems.get(i).setLatestPrice( coinArrayList.get(j).getLatestPrice()  );
                }
            }
            //rootActivity.user.portfolioItems.get(i).setLatestPrice(rand.nextDouble() * 100);
        }
        if(portfolioAdapter == null){
            portfolioAdapter = new PortfolioAdapter(recyclerMenuStatus, rootActivity);
            portfolioItemRecyclerView.setAdapter(portfolioAdapter);
        }
        else portfolioAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
//        new Handler().postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//
//            }
//        }, 2000);
    }
}
