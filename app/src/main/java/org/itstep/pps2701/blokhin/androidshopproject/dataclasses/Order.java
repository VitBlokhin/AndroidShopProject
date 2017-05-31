package org.itstep.pps2701.blokhin.androidshopproject.dataclasses;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Vit on 30.05.2017.
 */
public class Order implements Parcelable {
    private long id;
    private int number;
    private Date date;
    private List<Purchase> purchaseList;

    public Order(long id, int number, Date date) {
        this.id = id;
        this.number = number;
        this.date = date;
        purchaseList = new ArrayList<>();
    }

    public Order(long id, int number, long date) {
        this.id = id;
        this.number = number;
        this.date = new Date(date);
        purchaseList = new ArrayList<>();
    }

    protected Order(Parcel in) {
        id = in.readLong();
        number = in.readInt();
        date = new Date(in.readLong());
        purchaseList = in.readArrayList(Purchase.class.getClassLoader());
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

    public long getId() {
        return id;
    }
    public void setId(long id) {
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

    public List<Purchase> getPurchaseList() {
        return purchaseList;
    }

    public void addToPurchaseList(Product prod, int quantity){
        purchaseList.add(new Purchase(id, prod.getId(), quantity));
    } // addToPurchaseList
    public void addToPurchaseList(Purchase purchase){
        purchaseList.add(purchase);
    } // addToPurchaseList

    public void removeFromPurchaseList(Product prod){
        for(Purchase purchase : purchaseList) {
            if(purchase.getProductId() == prod.getId()) purchaseList.remove(purchase);
        }
    } // removeFromPurchaseList
    public void removeFromPurchaseList(Purchase purchase){
        purchaseList.remove(purchase);
    } // removeFromPurchaseList


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeInt(number);
        dest.writeLong(date.getTime());
        dest.writeList(purchaseList);
    } // writeToParcel

    @Override
    public String toString() {
        return "Заказ №" + number + ", от " + date;
    }
} // class Order
