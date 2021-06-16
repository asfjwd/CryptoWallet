package com.cse2216.cryptowallet.classes.domain;

public class PortfolioItem extends Coin {
    private Integer quantity ;
    private Double buyingPrice ;

    public PortfolioItem() {
    }

    public PortfolioItem(String name, String key, Integer coinId, Double last_transaction_price, Double volume, Double change, Integer quantity, Double buyingPrice) {
        super(name, key, coinId, last_transaction_price, volume, change);
        this.quantity = quantity;
        this.buyingPrice = buyingPrice;
    }

    public Double getBuyingPrice() {
        return buyingPrice;
    }

    public void setBuyingPrice(Double buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }


}
