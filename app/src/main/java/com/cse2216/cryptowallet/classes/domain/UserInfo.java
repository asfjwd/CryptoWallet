package com.cse2216.cryptowallet.classes.domain;

import java.util.ArrayList;

public class UserInfo {
    private String email;
    private String token;
    public ArrayList < Integer > watchList = new ArrayList<Integer>();
    public ArrayList <PortfolioItem> portfolioItems = new ArrayList<PortfolioItem>();
    public UserInfo(){}
    public UserInfo(String email, String token, ArrayList<PortfolioItem> portfolioItems, ArrayList<Integer> watchList) {
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

    public ArrayList<PortfolioItem> getPortfolioItems() {
        return portfolioItems;
    }

    public void setPortfolioItems(ArrayList<PortfolioItem> portfolioItems) {
        this.portfolioItems = portfolioItems;
    }

    public ArrayList<Integer> getWatchList() {
        return watchList;
    }

    public void setWatchList(ArrayList<Integer> watchList) {
        this.watchList.clear();
        for(int i = 0; i < watchList.size(); i++){
            this.watchList.add(watchList.get(i));
        }
    }
    public String toString(){
        return "Email: " + email +"\n token : " + token +"\n" + portfolioItems.toString() +"\n" + watchList.toString();
    }
}
