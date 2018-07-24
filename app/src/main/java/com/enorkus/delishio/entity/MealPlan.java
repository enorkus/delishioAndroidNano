package com.enorkus.delishio.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class MealPlan implements Parcelable {

    private int id;
    private String name;
    private List<Meal> meals;

    public MealPlan(int id, String name, List<Meal> meals) {
        this.id = id;
        this.name = name;
        this.meals = meals;
    }

    public MealPlan(String name, List<Meal> meals) {
        this.name = name;
        this.meals = meals;
    }

    protected MealPlan(Parcel in) {
        id = in.readInt();
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
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeTypedList(meals);
    }

    public String getName() {
        return name;
    }

    public List<Meal> getMeals() {
        return meals;
    }

    public int getId() {
        return id;
    }
}
