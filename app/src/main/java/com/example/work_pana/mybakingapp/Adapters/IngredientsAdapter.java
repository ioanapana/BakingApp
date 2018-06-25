package com.example.work_pana.mybakingapp.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.work_pana.mybakingapp.Fragments.MasterListRecipeDetailFragment;
import com.example.work_pana.mybakingapp.Models.IngredientObject;
import com.example.work_pana.mybakingapp.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientsAdapter  extends RecyclerView.Adapter<IngredientsAdapter.IngredientViewHolder> {


    private List<IngredientObject> ingredientsList;

    public IngredientsAdapter(List<IngredientObject> ingredients){
        this.ingredientsList = ingredients;
    }

    @Override
    public IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_list_ingredient, parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredientViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        holder.ingredientTitle.setText(ingredientsList.get(position).getIngredient());
        holder.ingredientQuantity.setText(" " + String.valueOf(ingredientsList.get(position).getQuantity()));
        holder.ingredientMeasure.setText(" " + ingredientsList.get(position).getMeasure());
    }

    @Override
    public int getItemCount() {
        if (ingredientsList == null) return 0;
        return ingredientsList.size();
    }

    class IngredientViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ingredient_name)TextView ingredientTitle;
        @BindView(R.id.ingredient_quantity) TextView ingredientQuantity;
        @BindView(R.id.ingredient_measure) TextView ingredientMeasure;

        IngredientViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setIngredientsModelList(List<IngredientObject> ingredientsModelList) {
        this.ingredientsList = ingredientsModelList;
    }
}

