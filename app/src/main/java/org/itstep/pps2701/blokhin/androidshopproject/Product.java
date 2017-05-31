package org.itstep.pps2701.blokhin.androidshopproject;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Vit on 30.05.2017.
 */
public class Product  implements Parcelable {
    private long id;
    private String name;
    private String description;
    private double price;

    public Product(long id, String name, String description, double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public Product(String name, String description, double price) {
        this.name = name;
        this.description = description;
        this.price = price;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return name + ", " + price + "р.";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeDouble(price);
    } // writeToParcel

    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
        // распаковываем объект из Parcel
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    // конструктор, считывающий данные из Parcel
    private Product(Parcel parcel) {
        id = parcel.readLong();
        name = parcel.readString();
        description = parcel.readString();
        price = parcel.readDouble();
    }
} // class Product
