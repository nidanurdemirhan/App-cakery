package com.nida.app_cakery.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nida.app_cakery.Models.Recipe;
import com.nida.app_cakery.R;
import com.nida.app_cakery.Domain.CakeryDomain;
import com.nida.app_cakery.Listeners.FirebaseListener;
import com.nida.app_cakery.Adapters.RecipeAdapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class HomeActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecipeAdapter recipeAdapter;
    private ArrayList<Recipe> recipeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        EditText searchBar = findViewById(R.id.searchbarhome);
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String searchText = s.toString();
                fetchRecipes(searchText); // Fetch recipes based on current search text
            }
        });

        fetchRecipes(""); // Initially fetch all recipes
        homeActivity();
    }


    private void fetchRecipes(String search) {

        if (search.isEmpty()) {
            CakeryDomain.getInstance().readRecipes(new FirebaseListener() {
                @Override
                public void onTaskCompleted() {
                    recipeList = CakeryDomain.getInstance().getCommonRecipeList();
                    recipeAdapter = new RecipeAdapter(HomeActivity.this, recipeList);
                    recyclerView.setAdapter(recipeAdapter);
                }
            });
        } else {
            CakeryDomain.getInstance().readRecipes(new FirebaseListener() {
                @Override
                public void onTaskCompleted() {
                    recipeList = CakeryDomain.getInstance().getCommonRecipeList();
                    recipeAdapter = new RecipeAdapter(HomeActivity.this, findRecipes(recipeList, search));
                    recyclerView.setAdapter(recipeAdapter);
                }
            });
        }

    }

    private ArrayList<Recipe> findRecipes(ArrayList<Recipe> allRecipes, String searchName) {
        ArrayList<Recipe> searchedRecipe = new ArrayList<>();
        String searchLower = searchName.toLowerCase(); // Convert search string to lowercase
        Set<String> uniqueRecipeIds = new HashSet<>(); // Store unique recipe IDs
        for (Recipe recipe : allRecipes) {
            String recipeNameLower = recipe.getName().toLowerCase(); // Convert recipe name to lowercase
            if (recipeNameLower.contains(searchLower)) { // Check for partial match
                if (!uniqueRecipeIds.contains(recipe.getRecipeID())) { // Check if recipe ID is already in the set
                    searchedRecipe.add(recipe);
                    uniqueRecipeIds.add(recipe.getRecipeID()); // Add recipe ID to set to mark it as seen
                }
            }
        }
        return searchedRecipe;
    }





    public void homeActivity() {
        ImageButton btnCart = findViewById(R.id.btnCart);
        ImageButton btnFav = findViewById(R.id.btnFavorites);
        ImageButton btnHome = findViewById(R.id.btnHome);
        ImageButton btnAdd = findViewById(R.id.btnMyRecipes);
        ImageButton btnList = findViewById(R.id.btnAllRecipes);
        LinearLayout llTest = findViewById(R.id.craveTestLayout);


        llTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, CravingActivity.class));
            }
        });

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, CartActivity.class));
            }
        });

        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, FavoriteActivity.class));
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Home butonu zaten HomeActivity'de olduğu için işlem yapmayabiliriz
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, AddActivity.class));
            }
        });

        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, ListActivity.class));
            }
        });
    }
}