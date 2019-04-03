package com.daily.jcy.printer.model.data.bean;

import android.support.annotation.NonNull;

import java.io.Serializable;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class Client implements Serializable{
    @Id(assignable = true)
    public long id;
    private String name;
    private String tel;
    private String tel2 = "null";
    private String zip;
    private String street;
    private String unit = "null";
    private String floor = "null";
    private String room = "null";
    private String note = "备注(打印)";
    private String note2 = "备注(不打印)";


    public Client(long id, String name, String tel, String tel2, String zip, String street, String unit, String floor, String room, String note, String note2) {
        this.id = id;
        this.name = name;
        this.tel = tel;
        this.tel2 = tel2;
        this.zip = zip;
        this.street = street;
        this.unit = unit;
        this.floor = floor;
        this.room = room;
        this.note = note;
        this.note2 = note2;
    }

//    public Client(long id, String name, String tel, String address, String note) {
//        this.id = id;
//        this.name = name;
//        this.tel = tel;
//        this.address = address;
//        this.note = note;
//    }

    public Client() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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


    public void setNote(String note) {
        this.note = note;
    }

    public String getNote() {
        return note;
   }

    public String getTel2() {
        return tel2;
    }

    public void setTel2(String tel2) {
        this.tel2 = tel2;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getNote2() {
        return note2;
    }

    public void setNote2(String note2) {
        this.note2 = note2;
    }

    @NonNull
    @Override
    public String toString() {
        return "[ id = " + getId() +
                " name = " + getName() +
                "tel = " + getTel() +
                "street = " + getStreet() +
                "note = " + getNote();
    }
}
