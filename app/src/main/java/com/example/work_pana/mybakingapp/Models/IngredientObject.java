package com.example.work_pana.mybakingapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class IngredientObject implements Parcelable {


    private float quantity;

    private String measure;

    private String ingredient;

    public IngredientObject() {

    }

    ///// Getters and setters for Ingredient Object //////

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    ///// Parcelable requirements //////

    protected IngredientObject(Parcel in) {
        quantity = in.readFloat();
        measure = in.readString();
        ingredient = in.readString();
    }

    public static final Creator<IngredientObject> CREATOR = new Creator<IngredientObject>() {
        @Override
        public IngredientObject createFromParcel(Parcel in) {
            return new IngredientObject(in);
        }

        @Override
        public IngredientObject[] newArray(int size) {
            return new IngredientObject[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeFloat(quantity);
        dest.writeString(measure);
        dest.writeString(ingredient);
    }
}


