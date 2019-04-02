package com.daily.jcy.printer.model.data.bean;


import android.support.annotation.Nullable;

import java.io.Serializable;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class Food implements Serializable {

    @Id(assignable = true)
    public long id;
    private String uid;
    private String CNname;
    private String GERname;
    private String price;
    private boolean isSweetAndWine;
    private int num = 0;


    public Food() {
    }

    public Food(String uid, String CNname, String GERname, String price, boolean isSweetAndWine, int num) {
        this.num = num;
        this.uid = uid;
        this.CNname = CNname;
        this.GERname = GERname;
        this.price = price;
        this.isSweetAndWine = isSweetAndWine;
    }

    public Food(String uid, String CNname, String GERname, String price, boolean isSweetAndWine) {
        this.uid = uid;
        this.CNname = CNname;
        this.GERname = GERname;
        this.price = price;
        this.isSweetAndWine = isSweetAndWine;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    @Nullable
    @Override
    public String toString() {
        return "[ id: " + getId()
                + " uid: " + getUid()
                + " CName: " + getCNname()
                + " count: " + getNum();
    }
}
