package com.example.trackmyreceiptapp;

public class item {
    String itemname;
    String itemdate;
    String itemprice;

    public item() {
    }

    public item(String itemname, String itemdate, String itemprice) {
        this.itemname = itemname;
        this.itemdate = itemdate;
        this.itemprice = itemprice;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getItemdate() {
        return itemdate;
    }

    public void setItemdescription(String itemdate) {
        this.itemdate = itemdate;
    }

    public String getItemprice() {
        return itemprice;
    }

    public void setItemprice(String itemprice) {
        this.itemprice = itemprice;
    }
}
