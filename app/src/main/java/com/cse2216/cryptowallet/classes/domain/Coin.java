package com.cse2216.cryptowallet.classes.domain;

public class Coin {
    private String name ;
    private String key ;
    private Integer coinId ;
    private Double latest_price;
    private Double volume ;
    private Double change ;
    public Coin(){}
    public Coin(String name,  String key, Integer coinId, Double last_transaction_price, Double volume, Double change) {
        this.name = name;
        this.coinId = coinId;
        this.key = key;
        this.latest_price = last_transaction_price;
        this.volume = volume;
        this.change = change;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Double getLatest_price() {
        return latest_price;
    }

    public void setLatest_price(Double latest_price) {
        this.latest_price = latest_price;
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
}
