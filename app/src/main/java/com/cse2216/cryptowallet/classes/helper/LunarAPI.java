package com.cse2216.cryptowallet.classes.helper;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cse2216.cryptowallet.activities.MainActivity;
import com.cse2216.cryptowallet.classes.domain.Coin;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class LunarAPI {
    RequestQueue queue;
    public LunarAPI(Context activityContext) {
        queue = Volley.newRequestQueue(activityContext);
    }
    public ArrayList <Coin> updateCoins(){
        String url = "https://api.lunarcrush.com/v2?data=assets&key=e5p6u53g6y5kqvapl6nurj&symbol=BTC,LTC";
        Log.d("response" , "clicked updateCoins()") ;
        ArrayList < Coin > coinArrayList = new ArrayList< Coin>();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray responseData = response.getJSONArray("data");
                            for(int coinIndex = 0 ; coinIndex < 2 ; coinIndex++){
                                JSONObject jsonCoin = responseData.getJSONObject(coinIndex) ;
                                Coin coin = new Coin(jsonCoin) ;
                                Log.d("response" , coin.toString());
                                coinArrayList.add(coin);
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
        return coinArrayList;

    }


}
