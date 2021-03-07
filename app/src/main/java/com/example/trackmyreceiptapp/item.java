package com.example.trackmyreceiptapp;

public class item {
    String item_name;
    String item_date;
    String item_price;

    public item() {
    }

    public item(String item_name, String item_date, String item_price) {
        this.item_name = item_name;
        this.item_date = item_date;
        this.item_price = item_price;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_date() {
        return item_date;
    }

    public void setItem_description(String item_date) {
        this.item_date = item_date;
    }

    public String getItem_price() {
        return item_price;
    }

    public void setItem_price(String item_price) {
        this.item_price = item_price;
    }
}
