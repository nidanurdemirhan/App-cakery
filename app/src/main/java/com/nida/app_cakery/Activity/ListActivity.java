package com.nida.app_cakery.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nida.app_cakery.Adapters.RecipeAdapter;
import com.nida.app_cakery.Domain.CakeryDomain;
import com.nida.app_cakery.Listeners.FirebaseListener;
import com.nida.app_cakery.Models.Recipe;
import com.nida.app_cakery.R;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecipeAdapter recipeAdapter;
    private ArrayList<Recipe> filteredRecipeList = new ArrayList<>(); // recipes which specify by ingredients
    private ArrayList<Recipe> recipeList = new ArrayList<>(); // recipe result(filter process like calorie )
    private SeekBar calorieSeekBar;
    private TextView seekBarValue;
    private int selectedCalorieValue = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        recyclerView = findViewById(R.id.recipeRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        calorieSeekBar = findViewById(R.id.calorieSeekBar);
        seekBarValue = findViewById(R.id.seekBarValue);

        calorieSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                selectedCalorieValue = progress;
                seekBarValue.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                filterRecipesByCalorie();
            }
        });

        createRecipeListByIngredients();
        ListActivity();
    }

    private void filterRecipesByCalorie() {
        filteredRecipeList.clear();
        for (Recipe recipe : recipeList ) {
            if (recipe.getCalorie() <= selectedCalorieValue) {
                filteredRecipeList.add(recipe);
            }
        }
        recipeAdapter.notifyDataSetChanged();
    }

    private void createRecipeListByIngredients() {
        CakeryDomain.getInstance().readRecipes(new FirebaseListener() {
            @Override
            public void onTaskCompleted() {
                filteredRecipeList.clear();
                recipeList.clear();
                ArrayList<Recipe> allRecipes = (CakeryDomain.getInstance()).getCommonRecipeList();
                for (int i = 0; i < allRecipes.size(); i++) {
                    Recipe recipe = allRecipes.get(i);
                    if (recipe.isIngredientsAvailable()) {
                        //filteredRecipeList.add(recipe);
                        recipeList.add(recipe);
                    }
                }
                filteredRecipeList.addAll(recipeList);
                recipeAdapter = new RecipeAdapter(ListActivity.this, filteredRecipeList);
                recyclerView.setAdapter(recipeAdapter);
            }
        });
    }
    public void ListActivity() {
        ImageButton btnCart = findViewById(R.id.btnCart);
        ImageButton btnFav = findViewById(R.id.btnFavorites);
        ImageButton btnHome = findViewById(R.id.btnHome);
        ImageButton btnAdd = findViewById(R.id.btnMyRecipes);
        ImageButton btnList = findViewById(R.id.btnAllRecipes);

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListActivity.this, CartActivity.class));
            }
        });

        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListActivity.this, FavoriteActivity.class));

            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListActivity.this, HomeActivity.class));
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListActivity.this, AddActivity.class));
            }
        });

        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }
}
