package com.enorkus.delishio.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Meal implements Parcelable {

    private String name;
    private List<String> ingredients;
    private String comments;

    public Meal(String name, List<String> ingredients) {
        this.name = name;
        this.ingredients = ingredients;
    }

    protected Meal(Parcel in) {
        name = in.readString();
        ingredients = in.createStringArrayList();
        comments = in.readString();
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
        parcel.writeString(name);
        parcel.writeStringList(ingredients);
        parcel.writeString(comments);
    }

    public String getName() {
        return name;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public String getComments() {
        return comments;
    }
}
