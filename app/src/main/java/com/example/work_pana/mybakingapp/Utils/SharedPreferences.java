package com.example.work_pana.mybakingapp.Utils;

import android.content.Context;

import com.example.work_pana.mybakingapp.Models.IngredientObject;
import com.example.work_pana.mybakingapp.Models.RecipeObject;
import com.google.gson.Gson;

import java.util.List;

public class SharedPreferences {
    public static String BAKING_SHARED_PREFERENCES = "bakingSharedPrefs";

    public static void saveRecipeInPreferences(Context context, RecipeObject recipe){
        Gson gson = new Gson();
        String json = gson.toJson(recipe);

        android.content.SharedPreferences preferences = context.getSharedPreferences(
                BAKING_SHARED_PREFERENCES, 0);

        android.content.SharedPreferences.Editor editor = preferences.edit();
        editor.putString(AppConstants.KEY_RECIPE, json);
        editor.apply();
    }

    public static RecipeObject getRecipeFromPreferences(Context context){
        android.content.SharedPreferences preferences = context.getSharedPreferences(
                BAKING_SHARED_PREFERENCES, 0);
        String json = preferences.getString(AppConstants.KEY_RECIPE, "");

        RecipeObject recipe = new Gson().fromJson(json, RecipeObject.class);

        return recipe;
    }

    public static List<IngredientObject> getIngredientListFromPreferences(Context context){
        return getRecipeFromPreferences(context).getIngredients();
    }

    public static String getRecipeNameFromPreferences(Context context){
        return getRecipeFromPreferences(context).getName();
    }

}
