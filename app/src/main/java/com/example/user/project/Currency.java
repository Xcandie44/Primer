package com.example.user.project;

/**
 * Created by User on 08.05.2018.
 */

public class Currency {
    String symbol;
    String price;



    Currency(String symbol, String price){
        this.symbol=symbol;
        this.price=price;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getPrice() {
        return price;
    }
}

