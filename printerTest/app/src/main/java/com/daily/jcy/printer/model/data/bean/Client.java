package com.daily.jcy.printer.model.data.bean;

import java.io.Serializable;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class Client implements Serializable{

    @Id (assignable = true)
    private Long ID;

    private String id;
    private String name;
    private String tel;
    private String address;

    public Client(Long ID,String id, String name, String tel, String address) {
        this.id = id;
        this.name = name;
        this.tel = tel;
        this.address = address;
        this.ID = ID;
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

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }
}
