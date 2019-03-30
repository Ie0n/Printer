package com.daily.jcy.printer.model.data.bean;

import java.io.Serializable;

public class Client implements Serializable {
    private String id;
    private String name;
    private String tel;
    private String address;
    private String note;

    public Client(String id, String name, String tel, String address,String note) {
        this.id = id;
        this.name = name;
        this.tel = tel;
        this.address = address;
        this.note = note;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNote() {
        return note;
    }
}
