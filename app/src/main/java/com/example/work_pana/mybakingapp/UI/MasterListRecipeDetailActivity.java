package com.example.work_pana.mybakingapp.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.work_pana.mybakingapp.Fragments.MasterListRecipeDetailFragment;
import com.example.work_pana.mybakingapp.Fragments.StepDetailFragment;
import com.example.work_pana.mybakingapp.Models.RecipeObject;
import com.example.work_pana.mybakingapp.R;
import com.example.work_pana.mybakingapp.Utils.AppConstants;
import com.example.work_pana.mybakingapp.Utils.SharedPreferences;

import java.util.ArrayList;

public class MasterListRecipeDetailActivity extends AppCompatActivity implements MasterListRecipeDetailFragment.OnStepClickListener {

    public static final int GET_RECIPE_FROM_SHARED_PREFS = 9321;
    public static final String KEY_GET_RECIPE_FROM_SHARED_PREFS = "get_recipe_from_shared_prefs";

    private boolean isTwoPane;
    private RecipeObject recipe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_recipe);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle;

        // MasterDetailActivity launched from RecipeListActivity
        if (getIntent().hasExtra(AppConstants.KEY_RECIPE_BUNDLE)) {
            bundle = getIntent().getBundleExtra(AppConstants.KEY_RECIPE_BUNDLE);
            recipe = bundle.getParcelable(AppConstants.KEY_RECIPE);
        } else {
            // MasterDetailActivity launched from Widget
            recipe = SharedPreferences.getRecipeFromPreferences(this);
            bundle = new Bundle();
            bundle.putParcelable(AppConstants.KEY_RECIPE, recipe);
        }

        if (savedInstanceState == null) addMasterListFragment(bundle);

        if (findViewById(R.id.step_detail_fragment_container) != null) {
            isTwoPane = true;
            // populate detail view
            if (savedInstanceState == null) {
                addDetailFragment();
            }
        } else {
            isTwoPane = false;
        }
    }

    /**
     * Creates a new MasterListFragment for MasterDetailActivity, sets arguments, doesn't add
     * fragment to back stack
     *
     * @param bundle arguments that are passed to fragment
     */
    private void addMasterListFragment(Bundle bundle) {
        MasterListRecipeDetailFragment masterListFragment = new MasterListRecipeDetailFragment();
        masterListFragment.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.recipe_detail_fragment_container, masterListFragment)
                .commit();
    }

    /**
     * Callback triggered from MasterListFragment when list item is clicked
     *
     * @param position position of the item in the list
     */

    @Override
    public void OnStepClickListener(int position) {
        if (isTwoPane) {
            replaceDetailFragment(position);
        } else {
            startDetailActivity(position);
        }
    }

//    /**
//     * Callbacks triggered from DetailFragment when "previous" or "next" button is clicked
//     *
//     * @param position the position of the selected step
//     */
//    @Override
//    public void onDetailStepClicked(int position) {
//        if (isTwoPane) {
//            getSupportFragmentManager().popBackStack();
//            replaceDetailFragment(position);
//        }
//    }

    /**
     * Adds a nes DetailFragment in master-detail view if the app is in two pane mode
     */
    private void addDetailFragment() {
        StepDetailFragment detailFragment = new StepDetailFragment();
        detailFragment.setArguments(getArgsForDetailFragment
                (AppConstants.DEFAULT_FRAGMENT_DETAIL_ITEM));
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.step_detail_fragment_container, detailFragment)
                .addToBackStack(null)
                .commit();
    }

    /**
     * Replaces the existing DetailFragment with a new one. Triggered when step is selected in
     * master list or in DetailFragment navigation
     *
     * @param position position of the next item to display in fragment
     */
    private void replaceDetailFragment(int position) {
        StepDetailFragment detailFragment = new StepDetailFragment();
        detailFragment.setArguments(getArgsForDetailFragment(position));
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.step_detail_fragment_container, detailFragment)
                .addToBackStack(null)
                .commit();
    }

    private void startDetailActivity(int position) {
        Intent intent = new Intent(MasterListRecipeDetailActivity.this,
                StepDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(AppConstants.KEY_RECIPE, recipe);
        bundle.putInt(AppConstants.KEY_STEP_ID, position);

        intent.putExtra(AppConstants.KEY_RECIPE_BUNDLE, bundle);
        startActivity(intent);
    }


    /**
     * Create a new Bundle to hold the users selected information
     *
     * @param position item clicked; position 0 contains ingredient list, the rest- recipe steps
     * @return bundle for the DetailFragment
     */
    @NonNull
    private Bundle getArgsForDetailFragment(int position) {
        Bundle bundle = new Bundle();
        if (position == 0) {
            // ingredients clicked- send ingredients
            bundle.putParcelableArrayList(AppConstants.KEY_INGREDIENT_LIST,
                    (ArrayList) recipe.getIngredients());
            bundle.putInt(AppConstants.KEY_RECIPE_SERVINGS, recipe.getServings());
        } else {
            // step clicked - send step
            bundle.putParcelable(AppConstants.KEY_STEP, recipe.getSteps().get(position));
        }

        bundle.putString(AppConstants.KEY_RECIPE_NAME, recipe.getName());
        bundle.putInt(AppConstants.KEY_STEP_ID, position);
        int stepCount = recipe.getSteps().size();
        bundle.putInt(AppConstants.KEY_STEP_COUNT, stepCount);

        return bundle;
    }



@Override
public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
        }

@Override
public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
        case android.R.id.home:
        NavUtils.navigateUpFromSameTask(this);
        return true;
        case R.id.add_to_widget:
        SharedPreferences.saveRecipeInPreferences(this, recipe);
//        updateWidget();
        Toast.makeText(this, "added to widget", Toast.LENGTH_SHORT).show();

        return true;
        }

        super.onOptionsItemSelected(item);

        return false;
        }

///**
// * Get instance of AppWidgetManager and call WidgetProviders updateAppWidgets()
// * Notify remote adapter to update widget data
// */
//private void updateWidget() {
//        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getApplication());
//        int[] ids = appWidgetManager
//        .getAppWidgetIds(new ComponentName(getApplication(), BakingWidgetProvider.class));
//
//        BakingWidgetProvider.updateAppWidgets(getApplication(), appWidgetManager, ids);
//        appWidgetManager.notifyAppWidgetViewDataChanged(ids, R.id.widget_list_view);
//        }
}
