package com.cse2216.cryptowallet.classes.domain;

import java.util.ArrayList;

public class User_Info {
    private String email;
    private String token;
    private ArrayList < Portfolio_Item > portfolioItems = new ArrayList<Portfolio_Item>();
    private ArrayList < Integer > watchList = new ArrayList<Integer>();
    public User_Info(){}
    public User_Info(String email, String token, ArrayList<Portfolio_Item> portfolioItems, ArrayList<Integer> watchList) {
        this.email = email;
        this.token = token;
        this.portfolioItems = portfolioItems;
        this.watchList = watchList;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ArrayList<Portfolio_Item> getPortfolioItems() {
        return portfolioItems;
    }

    public void setPortfolioItems(ArrayList<Portfolio_Item> portfolioItems) {
        this.portfolioItems = portfolioItems;
    }

    public ArrayList<Integer> getWatchList() {
        return watchList;
    }

    public void setWatchList(ArrayList<Integer> watchList) {
        this.watchList = watchList;
    }
}
