package com.enorkus.delishio.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Meal implements Parcelable {

    private int id;
    private String name;
    private String picturePath;
    private List<Ingredient> ingredients;

    public Meal(String name, String picturePath, List<Ingredient> ingredients) {
        this.name = name;
        this.picturePath = picturePath;
        this.ingredients = ingredients;
    }

    protected Meal(Parcel in) {
        id = in.readInt();
        name = in.readString();
        picturePath = in.readString();
        ingredients = in.readArrayList(null);
    }

    public static final Creator<Meal> CREATOR = new Creator<Meal>() {
        @Override
        public Meal createFromParcel(Parcel in) {
            return new Meal(in);
        }

        @Override
        public Meal[] newArray(int size) {
            return new Meal[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(picturePath);
        parcel.writeList(ingredients);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }
}
