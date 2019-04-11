package com.daily.jcy.printer.model.data.bean;

import android.os.Parcel;
import android.os.Parcelable;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

@Entity
public class Count implements Parcelable {
    @Id
    private long id;
    private int count;
//    public ToOne<Order> order;

    public Count(long id, int count) {
        this.id = id;
        this.count = count;
    }

    public Count() {
    }

    protected Count(Parcel in) {
        id = in.readLong();
        count = in.readInt();
    }

    public static final Creator<Count> CREATOR = new Creator<Count>() {
        @Override
        public Count createFromParcel(Parcel in) {
            return new Count(in);
        }

        @Override
        public Count[] newArray(int size) {
            return new Count[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeInt(count);
    }
}
