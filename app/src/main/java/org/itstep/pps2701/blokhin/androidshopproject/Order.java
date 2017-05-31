package org.itstep.pps2701.blokhin.androidshopproject;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Vit on 30.05.2017.
 */
public class Order implements Parcelable {
    private int id;
    private int number;
    private Date date;

    public Order(int id, int number, Date date) {
        this.id = id;
        this.number = number;
        this.date = date;
    }

    protected Order(Parcel in) {
        id = in.readInt();
        number = in.readInt();
        date = new Date(in.readLong());
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(number);
        dest.writeLong(date.getTime());
    } // writeToParcel

    @Override
    public String toString() {
        return "Заказ №" + number + ", от " + date;
    }
} // class Order
