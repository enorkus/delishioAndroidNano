package com.enorkus.delishio.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class MealPlan implements Parcelable {

    private String name;
    private List<Meal> meals;

    protected MealPlan(Parcel in) {
        name = in.readString();
        meals = in.createTypedArrayList(Meal.CREATOR);
    }

    public static final Creator<MealPlan> CREATOR = new Creator<MealPlan>() {
        @Override
        public MealPlan createFromParcel(Parcel in) {
            return new MealPlan(in);
        }

        @Override
        public MealPlan[] newArray(int size) {
            return new MealPlan[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeTypedList(meals);
    }
}
