package com.nida.app_cakery.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.nida.app_cakery.Adapters.IngredientAdapter;
import com.nida.app_cakery.Adapters.IngredientInRecipeAdapter;
import com.nida.app_cakery.Domain.CakeryDomain;
import com.nida.app_cakery.Models.IngredientInRecipe;
import com.nida.app_cakery.Models.Recipe;
import com.nida.app_cakery.R;

import java.util.ArrayList;
import java.util.List;

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
                // Geri düğmesine tıklandığında bir önceki aktiviteye dön
                finish();
            }
        });


        // Görünümleri bağla
        recipeName = findViewById(R.id.recipe_name);
        recipeDescription = findViewById(R.id.recipe_description);
        recipeCalories = findViewById(R.id.recipe_calories);
        recipePortion = findViewById(R.id.recipe_portion);
        recipeImage = findViewById(R.id.recipe_image);

        // Firestore instance
        db = FirebaseFirestore.getInstance();

        // Intent'ten Recipe ID'yi al
        recipeID = getIntent().getStringExtra("recipeID");

        // Firestore'dan tarifi çek
        loadRecipeDetails();
    }

    private void loadRecipeDetails() {
        // CakeryDomain sınıfından recipeList'i al
        ArrayList<Recipe> recipeList = CakeryDomain.getInstance().getRecipeList();

        // recipeID'ye göre doğru reçeteyi bul
        Recipe loadedRecipe = null;
        for (Recipe recipe : recipeList) {
            if (recipe.getRecipeID().equals(recipeID)) {
                loadedRecipe = recipe;
                break;
            }
        }

        // Eğer reçete bulunduysa, detayları yükle
        if (loadedRecipe != null) {
            String name = loadedRecipe.getName();
            String description = loadedRecipe.getDescription();
            double calorie = loadedRecipe.getCalorie();
            long portion = loadedRecipe.getPortion();
            String imageUrl = loadedRecipe.getImageUrl();

            recipeName.setText(name);
            recipeDescription.setText(description);
            recipeCalories.setText("Kalori: " + calorie);
            recipePortion.setText("Porsiyon: " + portion);

            Glide.with(RecipeDetailActivity.this)
                    .load(imageUrl)
                    .into(recipeImage);

            // Recipe'nin içindeki ingredientlerin listesini al
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

        // Firebase'den alınan recipe'deki ingredientleri kullanarak ingredientInRecipeList'i doldur

        for (IngredientInRecipe ingredientInRecipe : ingredientInRecipeList) {
            String ingredientName = ingredientInRecipe.getIngredient().getName();
            double amount = ingredientInRecipe.getAmount();
            String unit = ingredientInRecipe.getUnit();
            ingredientsList.add(ingredientName);
            ingredientsAmountList.add(amount);
            ingredientsUnitList.add(unit);
        }

        // RecyclerView ve Adapter kurulumu
        RecyclerView ingredientsRecyclerView = findViewById(R.id.ingredients_recycler_view);
        ingredientsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        IngredientInRecipeAdapter ingredientAdapter = new IngredientInRecipeAdapter(ingredientInRecipeList);
        ingredientsRecyclerView.setAdapter(ingredientAdapter);
    }



}