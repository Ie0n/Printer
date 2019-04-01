package com.daily.jcy.printer.utils;


import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class StringIdEntity {
    @Id public long id;
    public String uid;

    public StringIdEntity() {
    }

    public StringIdEntity(long id, String uid) {
        this.id = id;
        this.uid = uid;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

}
