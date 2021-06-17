package com.cse2216.cryptowallet.classes.domain;

public class PortfolioItem extends Coin {
    private Double position;
    private Double buyingPrice;

    public PortfolioItem() {
    }

    public PortfolioItem(String name, String key, Integer coinId, Double latestPrice, Double volume, Double change, Double position, Double buyingPrice) {
        super(name, key, coinId, latestPrice, volume, change);
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

    public void setPosition(Integer quantity) {
        this.position = position;
    }

    public Double getGain(){
        return (buyingPrice - getLatestPrice());
    }

    public Double getGainPercentage(){
        return (getGain()/buyingPrice) * 100.0;
    }
}
