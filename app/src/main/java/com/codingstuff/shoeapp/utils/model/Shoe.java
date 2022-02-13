package com.codingstuff.shoeapp.utils.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "shoe_table")
public class Shoe implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int quantity;
    private int shoeImage;
    private String shoeName, shoeBrandName;
    private double shoePrice;
    private double totalItemPrice;

    public Shoe(int shoeImage, String shoeName, String shoeBrandName, double shoePrice) {
        this.shoeImage = shoeImage;
        this.shoeName = shoeName;
        this.shoePrice = shoePrice;
        this.shoeBrandName = shoeBrandName;
    }

    protected Shoe(Parcel in) {
        id = in.readInt();
        shoeImage = in.readInt();
        shoeName = in.readString();
        shoeBrandName = in.readString();
        shoePrice = in.readDouble();
    }

    public static final Creator<Shoe> CREATOR = new Creator<Shoe>() {
        @Override
        public Shoe createFromParcel(Parcel in) {
            return new Shoe(in);
        }

        @Override
        public Shoe[] newArray(int size) {
            return new Shoe[size];
        }
    };

    public double getTotalItemPrice() {
        return totalItemPrice;
    }

    public void setTotalItemPrice(double totalItemPrice) {
        this.totalItemPrice = totalItemPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getShoeImage() {
        return shoeImage;
    }

    public void setShoeImage(int shoeImage) {
        this.shoeImage = shoeImage;
    }

    public String getShoeName() {
        return shoeName;
    }

    public void setShoeName(String shoeName) {
        this.shoeName = shoeName;
    }

    public double getShoePrice() {
        return shoePrice;
    }

    public void setShoePrice(double shoePrice) {
        this.shoePrice = shoePrice;
    }

    public String getShoeBrandName() {
        return shoeBrandName;
    }

    public void setShoeBrandName(String shoeBrandName) {
        this.shoeBrandName = shoeBrandName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(shoeImage);
        parcel.writeString(shoeName);
        parcel.writeString(shoeBrandName);
        parcel.writeDouble(shoePrice);
    }
}
