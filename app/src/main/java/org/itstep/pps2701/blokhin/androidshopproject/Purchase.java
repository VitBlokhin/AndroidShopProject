package org.itstep.pps2701.blokhin.androidshopproject;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Vit on 30.05.2017.
 */
public class Purchase implements Parcelable{
    private int orderId;
    private int productId;
    private int quantity;

    protected Purchase(Parcel in) {
        orderId = in.readInt();
        productId = in.readInt();
        quantity = in.readInt();
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
        dest.writeInt(orderId);
        dest.writeInt(productId);
        dest.writeInt(quantity);
    }
} // class Purchase
