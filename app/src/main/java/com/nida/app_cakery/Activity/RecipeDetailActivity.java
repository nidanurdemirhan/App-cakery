package com.nida.app_cakery.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FirebaseFirestore;
import com.nida.app_cakery.Adapters.IngredientInRecipeAdapter;
import com.nida.app_cakery.Domain.CakeryDomain;
import com.nida.app_cakery.Models.IngredientInRecipe;
import com.nida.app_cakery.Models.Recipe;
import com.nida.app_cakery.R;

import java.util.ArrayList;

public class RecipeDetailActivity extends AppCompatActivity {
    private TextView recipeName, recipeDescription, recipeCalories, recipePortion;
    private ImageView recipeImage;
    private FirebaseFirestore db;
    private String recipeID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        recipeName = findViewById(R.id.recipe_name);
        recipeDescription = findViewById(R.id.recipe_description);
        recipeCalories = findViewById(R.id.recipe_calories);
        recipePortion = findViewById(R.id.recipe_portion);
        recipeImage = findViewById(R.id.recipe_image);

        db = FirebaseFirestore.getInstance();

        recipeID = getIntent().getStringExtra("recipeID");

        loadRecipeDetails();
    }

    private void loadRecipeDetails() {
        ArrayList<Recipe> recipeList = CakeryDomain.getInstance().getAllRecipeList();

        Recipe loadedRecipe = null;
        for (Recipe recipe : recipeList) {
            if (recipe.getRecipeID().equals(recipeID)) {
                loadedRecipe = recipe;
                break;
            }
        }

        if (loadedRecipe != null) {
            String name = loadedRecipe.getName();
            String description = loadedRecipe.getDescription();
            double calorie = loadedRecipe.getCalorie();
            long portion = loadedRecipe.getPortion();
            String imageUrl = loadedRecipe.getImageUrl();

            recipeName.setText(name);
            recipeDescription.setText(description);
            recipeCalories.setText("Calorie: " + calorie);
            recipePortion.setText("Portion: " + portion);

            Glide.with(RecipeDetailActivity.this)
                    .load(imageUrl)
                    .into(recipeImage);

            loadIngredients(loadedRecipe);
        } else {
            Log.d("RecipeDetailActivity", "Recipe not found");
        }
    }


    private void loadIngredients(Recipe loadedRecipe) {
        ArrayList<IngredientInRecipe> ingredientInRecipeList = loadedRecipe.getIngredientInRecipe();
        ArrayList<String> ingredientsList = new ArrayList<>();
        ArrayList<Double> ingredientsAmountList = new ArrayList<>();
        ArrayList<String> ingredientsUnitList = new ArrayList<>();

        for (IngredientInRecipe ingredientInRecipe : ingredientInRecipeList) {
            String ingredientName = ingredientInRecipe.getIngredient().getName();
            double amount = ingredientInRecipe.getAmount();
            String unit = ingredientInRecipe.getUnit();
            ingredientsList.add(ingredientName);
            ingredientsAmountList.add(amount);
            ingredientsUnitList.add(unit);
        }

        RecyclerView ingredientsRecyclerView = findViewById(R.id.ingredients_recycler_view);
        ingredientsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        IngredientInRecipeAdapter ingredientAdapter = new IngredientInRecipeAdapter(ingredientInRecipeList);
        ingredientsRecyclerView.setAdapter(ingredientAdapter);
    }



}