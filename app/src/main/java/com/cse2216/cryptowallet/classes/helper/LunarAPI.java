package com.cse2216.cryptowallet.classes.helper;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cse2216.cryptowallet.classes.domain.Coin;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class LunarAPI {
    RequestQueue queue;
    private Integer numberOfCoins ;
    private ArrayList < Coin > coinArrayList = new ArrayList< Coin>();
    public LunarAPI(){}
    public LunarAPI(Context activityContext) {
        queue = Volley.newRequestQueue(activityContext);
        numberOfCoins = 10;
        if(coinArrayList.size()!=numberOfCoins)
            init(numberOfCoins);
        updateCoins();
    }

    private void init(int numberOfCoins){
        for(int coinIndex = 0 ; coinIndex < numberOfCoins ;coinIndex++){
            coinArrayList.add(new Coin("","","",0,0.0,0.0,0.0));
        }
    }

    public void updateCoins(){

        String url = "https://api.lunarcrush.com/v2?data=assets&key=e5p6u53g6y5kqvapl6nurj&symbol=BTC,USDT,BNB,ETH,LTC,ADA,DOT,BCH,XLM,USDC";
        Log.d("response" , "clicked updateCoins()") ;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray responseData = response.getJSONArray("data");
                            for(int coinIndex = 0 ; coinIndex < numberOfCoins ; coinIndex++){
                                JSONObject jsonCoin = responseData.getJSONObject(coinIndex) ;
                                Coin coin = new Coin(jsonCoin) ;
                                Log.d("response" , coin.toString());
                                coinArrayList.set(coinIndex,coin);
                                Log.d("response" , coinArrayList.size() + " Addded ");
                            }
                        }
                        catch (Exception e){
                            Log.d("response" , "Error");
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d("response" , "ERROR") ;

                    }
                });
        queue.add(jsonObjectRequest) ;
        Log.d("response" , coinArrayList.size() + " LAST ");
    }
    public ArrayList<Coin> getCoinArrayList() {
        return coinArrayList;
    }
}
