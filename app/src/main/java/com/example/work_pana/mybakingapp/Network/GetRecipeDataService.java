package com.example.work_pana.mybakingapp.Network;

import com.example.work_pana.mybakingapp.Models.RecipeObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetRecipeDataService {

    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<List<RecipeObject>> getRecipesData();
}
