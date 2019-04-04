package com.daily.jcy.printer.model.data.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;
import io.objectbox.relation.ToOne;

@Entity
public class Order implements Parcelable {

    @Id
    private Long id;
    private String time;
    private String totalPrice;
    @Backlink(to = "order")
    public ToMany<Count> countsList;
    @Backlink(to = "order")
    public ToMany<Food> foodList;
    @Backlink(to = "order")
    public ToMany<Client> clientList;


    public Order() {
    }

    public Order(Long id, String time, String totalPrice) {
        this.id = id;
        this.time = time;
        this.totalPrice = totalPrice;
    }


    protected Order(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        time = in.readString();
        totalPrice = in.readString();
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<Food> getFoodList() {
        return foodList;
    }

    public void setFoodList(ToMany<Food> foodList) {
        this.foodList = foodList;
    }


    public ToMany<Client> getClientList() {
        return clientList;
    }

    public void setClientList(ToMany<Client> clientList) {
        this.clientList = clientList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
        dest.writeString(time);
        dest.writeString(totalPrice);
    }


}
