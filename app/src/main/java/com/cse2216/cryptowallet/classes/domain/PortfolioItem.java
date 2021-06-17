package com.cse2216.cryptowallet.classes.domain;

public class PortfolioItem extends Coin {
    private Double position;
    private Double buyingPrice;

    public PortfolioItem() {
    }

    public PortfolioItem(String name, String key, String symbol , Integer coinId, Double latestPrice, Double volume, Double change, Double position, Double buyingPrice) {
        super(name, key,symbol, coinId, latestPrice, volume, change);
        this.position = position;
        this.buyingPrice = buyingPrice;
    }

    public Double getBuyingPrice() {
        return buyingPrice;
    }

    public void setBuyingPrice(Double buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public Double getPosition() {
        return position;
    }

    public void setPosition(Double position) {
        this.position = position;
    }

    public Double getGain(){
        return (getLatestPrice() - buyingPrice) * position;
    }

    public Double getGainPercentage(){
        return (getGain()/(buyingPrice * position)) * 100.0;
    }
}
