package com.example.jcy.testleancloud;


public class Mode {

    private String name = null;
    private Integer number = null;
    private Integer price = null;

    public Mode(String name, Integer number, Integer price) {
        this.name = name;
        this.number = number;
        this.price = price;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public Integer getNumber() {
        return number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Mode{" +
                "name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
