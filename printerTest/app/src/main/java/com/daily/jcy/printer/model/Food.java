package com.daily.jcy.printer.model;

public class Food {
    private Long id;
    private String CNname;
    private String GERname;
    private String price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
}
