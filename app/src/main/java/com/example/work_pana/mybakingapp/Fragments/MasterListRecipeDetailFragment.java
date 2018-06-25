package com.example.work_pana.mybakingapp.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.work_pana.mybakingapp.Adapters.IngredientsAdapter;
import com.example.work_pana.mybakingapp.Adapters.StepsAdapter;
import com.example.work_pana.mybakingapp.Models.RecipeObject;
import com.example.work_pana.mybakingapp.R;
import com.example.work_pana.mybakingapp.UI.MasterListRecipeDetailActivity;
import com.example.work_pana.mybakingapp.Utils.AppConstants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MasterListRecipeDetailFragment extends Fragment implements StepsAdapter.OnStepClickListener{

    private RecipeObject mRecipe;
    private OnStepClickListener mListener;
    @BindView(R.id.ingredients_RV)
    RecyclerView ingredientsRecyclerView;
    @BindView(R.id.steps_RV)
    RecyclerView stepsRecyclerView;
    private StepsAdapter mStepAdapter;
    private IngredientsAdapter mIngredientAdapter;


    public interface OnStepClickListener {
        void OnStepClickListener(int position);
    }

    public MasterListRecipeDetailFragment(){
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            mListener = (OnStepClickListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement OnStepClickListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_detail_recipie, container, false);
        ButterKnife.bind(this, rootView);
        Bundle bundle = getArguments();
        if(bundle != null){
            mRecipe = getArguments().getParcelable(AppConstants.KEY_RECIPE);
            setUpIngredientsRecyclerView();
            setUpStepsRecyclerView();

            getActivity().setTitle(mRecipe.getName());
        }

        return rootView;
    }

    @Override
    public void onStepSelected(int position) {
        mListener.OnStepClickListener(position);
    }

    private void setUpStepsRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mStepAdapter = new StepsAdapter(mRecipe.getSteps(), this);
        stepsRecyclerView.setLayoutManager(layoutManager);
        stepsRecyclerView.setAdapter(mStepAdapter);
        stepsRecyclerView.setHasFixedSize(true);
        mStepAdapter.notifyDataSetChanged();
    }
    private void setUpIngredientsRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mIngredientAdapter = new IngredientsAdapter(mRecipe.getIngredients());
        ingredientsRecyclerView.setLayoutManager(layoutManager);
        ingredientsRecyclerView.setAdapter(mIngredientAdapter);
        ingredientsRecyclerView.setHasFixedSize(true);
        mIngredientAdapter.notifyDataSetChanged();
    }
}
