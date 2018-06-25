package com.example.work_pana.mybakingapp.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.work_pana.mybakingapp.Models.RecipeObject;
import com.example.work_pana.mybakingapp.R;
import com.example.work_pana.mybakingapp.UI.MainActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipeViewHolder> {
    private Context mContext;
    private List<RecipeObject> mRecipeList;
    private final OnRecipeItemClickListener mListener;

    public interface OnRecipeItemClickListener {
        void onRecipeItemClick(RecipeObject recipeObject);
    }

    public RecipesAdapter(Context context, List<RecipeObject> recipeList, OnRecipeItemClickListener recipeItemClickListener) {
        this.mListener = recipeItemClickListener;
        this.mContext = context;
        this.mRecipeList = recipeList;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_list_recipe, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        String recipeName = mRecipeList.get(position).getName();
        String imagePath = mRecipeList.get(position).getImage();
        int recipeImage;

        switch (recipeName) {
            case "Nutella Pie":
                recipeImage = R.drawable.nutella_pie;
                break;
            case "Brownies":
                recipeImage = R.drawable.brownies;
                break;
            case "Yellow Cake":
                recipeImage = R.drawable.yellow_cake;
                break;
            case "Cheesecake":
                recipeImage = R.drawable.cheesecake;
                break;
            default:
                recipeImage = R.drawable.img_placeholder;
        }

        holder.recipeTitle.setText(recipeName);

        if (imagePath.equals("")) {
            Picasso.with(mContext)
                    .load(recipeImage)
                    .placeholder(R.drawable.img_placeholder)
                    .error(R.drawable.img_placeholder)
                    .into(holder.recipeThumbnail);
        } else {
            Picasso.with(mContext)
                    .load(imagePath)
                    .placeholder(R.drawable.img_placeholder)
                    .error(R.drawable.img_placeholder)
                    .into(holder.recipeThumbnail);
        }

    }

    @Override
    public int getItemCount() {
        if (mRecipeList == null) return 0;
        return mRecipeList.size();
    }

    public void setRecipeList(List<RecipeObject> recipeList) {
        this.mRecipeList = recipeList;
        notifyDataSetChanged();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        @BindView(R.id.recipe_thumbnail)
        ImageView recipeThumbnail;
        @BindView(R.id.recipe_title)
        TextView recipeTitle;

        RecipeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mListener.onRecipeItemClick(mRecipeList.get(position));
        }
    }
}