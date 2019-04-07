package com.daily.jcy.printer.model.data.bean;


import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;


import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

@Entity
public class Food implements Parcelable {

    public static final String FOOD_DB = "FOOD";
    public static final String FOOD_ID = "FOOD_ID";

    @Id(assignable = true)
    public long id;
    private String uid;
    private String CNname;
    private String GERname;
    private String price;
    private boolean isSweetAndWine;
    private int num = 0;
    public ToOne<Order> order;


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
        this.num = 0;
    }

    protected Food(Parcel in) {
        id = in.readLong();
        uid = in.readString();
        CNname = in.readString();
        GERname = in.readString();
        price = in.readString();
        isSweetAndWine = in.readByte() != 0;
        num = in.readInt();
    }

    public static final Creator<Food> CREATOR = new Creator<Food>() {
        @Override
        public Food createFromParcel(Parcel in) {
            return new Food(in);
        }

        @Override
        public Food[] newArray(int size) {
            return new Food[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeLong(id);
        dest.writeString(uid);
        dest.writeString(CNname);
        dest.writeString(GERname);
        dest.writeString(price);
        dest.writeByte((byte) (isSweetAndWine ? 1 : 0));
        dest.writeInt(num);
    }
}
