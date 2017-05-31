package org.itstep.pps2701.blokhin.androidshopproject;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Vit on 30.05.2017.
 */
public class Purchase implements Parcelable{
    private long id;
    private long orderId;
    private long productId;
    private int quantity;

    public Purchase(long id, long orderId, long productId, int quantity) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public Purchase(long orderId, long productId, int quantity) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
    }

    protected Purchase(Parcel in) {
        id = in.readLong();
        orderId = in.readLong();
        productId = in.readLong();
        quantity = in.readInt();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public static final Creator<Purchase> CREATOR = new Creator<Purchase>() {
        @Override
        public Purchase createFromParcel(Parcel in) {
            return new Purchase(in);
        }

        @Override
        public Purchase[] newArray(int size) {
            return new Purchase[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeLong(orderId);
        dest.writeLong(productId);
        dest.writeInt(quantity);
    }
} // class Purchase
