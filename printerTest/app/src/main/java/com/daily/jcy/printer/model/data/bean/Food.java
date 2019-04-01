package com.daily.jcy.printer.model.data.bean;

import java.io.Serializable;

public class Food{
    private String id;
    private String CNname;
    private String GERname;
    private String price;
    private boolean isSweetAndWine;
    private int num;


    public Food(String id, String CNname, String GERname, String price, boolean isSweetAndWine, int num) {
        this.num = num;
        this.id = id;
        this.CNname = CNname;
        this.GERname = GERname;
        this.price = price;
        this.isSweetAndWine = isSweetAndWine;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCNname() {
        return CNname;
    }

    public void setCNname(String CNname) {
        this.CNname = CNname;
    }

    public String getGERname() {
        return GERname;
    }

    public void setGERname(String GERname) {
        this.GERname = GERname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public boolean isSweetAndWine() {
        return isSweetAndWine;
    }

    public void setSweetAndWine(boolean sweetAndWine) {
        isSweetAndWine = sweetAndWine;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getNum() {
        return num;
    }
}
