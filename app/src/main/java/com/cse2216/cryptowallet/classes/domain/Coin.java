package com.cse2216.cryptowallet.classes.domain;

import org.json.JSONObject;

public class Coin {
    private String name;
    private String key;
    private String symbol;
    private Integer coinId;
    private Double latestPrice;
    private Double volume;
    private  Double change;

    public Coin(){}
    public Coin(String name, String key, String symbol , Integer coinId, Double latestPrice, Double volume, Double change) {
        this.name = name;
        this.coinId = coinId;
        this.key = key;
        this.symbol = symbol ;
        this.latestPrice = latestPrice;
        this.volume = volume;
        this.change = change;
    }
    public Coin(JSONObject object){
        try{
            this.name = object.getString("name");
            this.symbol = object.getString("symbol");
            this.key = "";
            this.coinId = Integer.parseInt(object.getString("id"));
            this.latestPrice = Double.parseDouble(object.getString("price"));
            this.volume = Double.parseDouble(object.getString("volume_24h"));
            this.change = Double.parseDouble(object.getString("percent_change_24h"));
        }
        catch (Exception e){

        }

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Integer getCoinId() {
        return coinId;
    }

    public void setCoinId(Integer coinId) {
        this.coinId = coinId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Double getLatestPrice() {
        return latestPrice;
    }

    public void setLatestPrice(Double latestPrice) {
        this.latestPrice = latestPrice;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public Double getChange() {
        return change;
    }

    public void setChange(Double change) {
        this.change = change;
    }
    public String toString(){
        String temporary = "Name: " + name +"\nSymbol: " + symbol + "\nLatest Price: " + latestPrice + "\nVolume: " + volume + "\nChange: " + change + "\nCoin Id: " + coinId;
        return  temporary ;
    }
}
