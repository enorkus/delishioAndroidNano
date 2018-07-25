package com.enorkus.delishio.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Ingredient implements Parcelable {

    private int id;
    private int mealId;
    private String name;
    private double quantity;
    private String unit;

    public Ingredient(int id, int mealId, String name, double quantity, String unit) {
        this.id = id;
        this.mealId = mealId;
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
    }

    public Ingredient(int mealId, String name, double quantity, String unit) {
        this.mealId = mealId;
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
    }

    protected Ingredient(Parcel in) {
        id = in.readInt();
        mealId = in.readInt();
        name = in.readString();
        quantity = in.readDouble();
        unit = in.readString();
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(mealId);
        parcel.writeString(name);
        parcel.writeDouble(quantity);
        parcel.writeString(unit);
    }

    public int getId() {
        return id;
    }

    public int getMealId() {
        return mealId;
    }

    public String getName() {
        return name;
    }

    public double getQuantity() {
        return quantity;
    }

    public String getUnit() {
        return unit;
    }
}
