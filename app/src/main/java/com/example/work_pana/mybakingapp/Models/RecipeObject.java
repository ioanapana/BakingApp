package com.example.work_pana.mybakingapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class RecipeObject implements Parcelable, Serializable {
    @SerializedName("id")
    private Integer id;
    @SerializedName("name")
    private String name;
    @SerializedName("ingredients")
    private List<IngredientObject> ingredients = null;
    @SerializedName("steps")
    private List<StepObject> steps = null;
    @SerializedName("servings")
    private Integer servings;
    @SerializedName("image")
    private String image;

    ////////// Constructors ////////
//
//    public RecipeObject(Integer id, String name, List<IngredientObject> ingredients, List<StepObject> steps, Integer servings, String image) {
//        this.id = id;
//        this.name = name;
//        this.ingredients = ingredients;
//        this.steps = steps;
//        this.servings = servings;
//        this.image = image;
//    }

    public RecipeObject(int id, String name, int servings, String image) {
        this.id = id;
        this.name = name;
        this.servings = servings;
        this.image = image;
    }

    public RecipeObject() { }

///// Getters and setters for Ingredient Object //////

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<IngredientObject> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientObject> ingredients) {
        this.ingredients = ingredients;
    }

    public List<StepObject> getSteps() {
        return steps;
    }

    public void setSteps(List<StepObject> steps) {
        this.steps = steps;
    }

    public Integer getServings() {
        return servings;
    }

    public void setServings(Integer servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    ///// Parcelable requirements //////

    protected RecipeObject(Parcel in) {
        name = in.readString();
        ingredients = in.createTypedArrayList(IngredientObject.CREATOR);
        steps = in.createTypedArrayList(StepObject.CREATOR);
        image = in.readString();
    }

    public static final Parcelable.Creator<RecipeObject> CREATOR = new Parcelable.Creator<RecipeObject>() {
        @Override
        public RecipeObject createFromParcel(Parcel in) {
            return new RecipeObject(in);
        }

        @Override
        public RecipeObject[] newArray(int size) {
            return new RecipeObject[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeTypedList(ingredients);
        dest.writeTypedList(steps);
        dest.writeString(image);
    }
}
