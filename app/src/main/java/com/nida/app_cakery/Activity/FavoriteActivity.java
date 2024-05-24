package com.nida.app_cakery.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nida.app_cakery.Models.Recipe;
import com.nida.app_cakery.Models.User;
import com.nida.app_cakery.R;
import com.nida.app_cakery.Domain.CakeryDomain;
import com.nida.app_cakery.Listeners.FirebaseListener;
import com.nida.app_cakery.Adapters.RecipeAdapter;

import java.util.ArrayList;

public class FavoriteActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecipeAdapter recipeAdapter;
    private ArrayList<Recipe> recipeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorites_page);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fetchRecipes();
        favoriteActivity();
    }

    private void fetchRecipes() {
        recipeList = ((User)(CakeryDomain.getInstance().getPerson())).getFavoriteRecipes();
        recipeAdapter = new RecipeAdapter(this, recipeList);
        recyclerView.setAdapter(recipeAdapter);
    }

    public void favoriteActivity() {
        ImageButton btnCart = findViewById(R.id.btnCart);
        ImageButton btnFav = findViewById(R.id.btnFavorites);
        ImageButton btnHome = findViewById(R.id.btnHome);
        ImageButton btnAdd = findViewById(R.id.btnMyRecipes);
        ImageButton btnList = findViewById(R.id.btnAllRecipes);

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FavoriteActivity.this, CartActivity.class));
            }
        });

        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FavoriteActivity.this, HomeActivity.class));
                // Home butonu zaten HomeActivity'de olduğu için işlem yapmayabiliriz
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FavoriteActivity.this, AddActivity.class));
            }
        });

        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FavoriteActivity.this, ListActivity.class));
            }
        });
    }
}

/*
   private void fetchRecipes() {
        recipeList = ((User)(CakeryDomain.getInstance().getPerson())).getFavoriteRecipes();
        recipeList = ((User)(CakeryDomain.getInstance().getPerson())).getFavoriteRecipes();
        recipeAdapter.notifyDataSetChanged();

    }
 */
