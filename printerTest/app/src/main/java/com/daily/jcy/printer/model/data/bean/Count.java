package com.daily.jcy.printer.model.data.bean;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

@Entity
public class Count {
    @Id
    private long id;
    private int count;
    public ToOne<Order> order;

    public Count(long id, int count) {
        this.id = id;
        this.count = count;
    }

    public Count() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
