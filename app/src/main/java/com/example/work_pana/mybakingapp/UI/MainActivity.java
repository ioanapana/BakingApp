package com.example.work_pana.mybakingapp.UI;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.work_pana.mybakingapp.Adapters.RecipesAdapter;
import com.example.work_pana.mybakingapp.Models.RecipeObject;
import com.example.work_pana.mybakingapp.Network.GetRecipeDataService;
import com.example.work_pana.mybakingapp.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.work_pana.mybakingapp.Utils.AppConstants.BASE_URL;


public class MainActivity extends AppCompatActivity implements RecipesAdapter.OnRecipeItemClickListener {

    @BindView(R.id.recipes_RV)
    RecyclerView recipesRecyclerView;
    private RecipesAdapter mRecipeAdapter;
    private List<RecipeObject> mRecipesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mRecipesList = new ArrayList<>();
        getRecipes();
    }

    public void getRecipes() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GetRecipeDataService recipeDataService = retrofit.create(GetRecipeDataService.class);

        Call<List<RecipeObject>> call = recipeDataService.getRecipesData();
        call.enqueue(new Callback<List<RecipeObject>>() {
            @Override
            public void onResponse(Call<List<RecipeObject>> call, Response<List<RecipeObject>> response) {
                mRecipesList = response.body();
                setUI();
                mRecipeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<RecipeObject>> call, Throwable t) {
            }
        });
    }

    private void setUI() {
        Configuration config = getResources().getConfiguration();
        if (config.smallestScreenWidthDp >= 600) {
            GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
            recipesRecyclerView.setLayoutManager(layoutManager);
        } else {
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recipesRecyclerView.setLayoutManager(layoutManager);
        }

        mRecipeAdapter = new RecipesAdapter(MainActivity.this, mRecipesList,this);
        recipesRecyclerView.setAdapter(mRecipeAdapter);
        recipesRecyclerView.setHasFixedSize(true);

    }


    @Override
    public void onRecipeItemClick(RecipeObject recipeObject) {
        Intent intent = new Intent(this, MasterListRecipeDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("key", recipeObject);
        intent.putExtras(bundle);
        startActivity(intent);

    }
}
