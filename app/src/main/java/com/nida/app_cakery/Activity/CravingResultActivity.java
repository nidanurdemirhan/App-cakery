package com.nida.app_cakery.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nida.app_cakery.Models.Recipe;
import com.nida.app_cakery.R;
import com.nida.app_cakery.Domain.CakeryDomain;
import com.nida.app_cakery.Listeners.FirebaseListener;
import com.nida.app_cakery.Adapters.RecipeAdapter;

import java.util.ArrayList;

public class CravingResultActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecipeAdapter recipeAdapter;
    private ArrayList<Recipe> recipeList = new ArrayList<>();
    private String answer = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.craving_test_result);
        setUpNavigationButtons();
        setUpTestResult();
    }

    private void setUpNavigationButtons() {
        ImageButton btnCart = findViewById(R.id.btnCart);
        ImageButton btnFav = findViewById(R.id.btnFavorites);
        ImageButton btnHome = findViewById(R.id.btnHome);
        ImageButton btnAdd = findViewById(R.id.btnMyRecipes);
        ImageButton btnList = findViewById(R.id.btnAllRecipes);

        btnCart.setOnClickListener(v -> startActivity(new Intent(CravingResultActivity.this, CartActivity.class)));
        btnFav.setOnClickListener(v -> startActivity(new Intent(CravingResultActivity.this, FavoriteActivity.class)));
        btnHome.setOnClickListener(v -> startActivity(new Intent(CravingResultActivity.this, HomeActivity.class)));
        btnAdd.setOnClickListener(v -> startActivity(new Intent(CravingResultActivity.this, AddActivity.class)));
        btnList.setOnClickListener(v -> startActivity(new Intent(CravingResultActivity.this, ListActivity.class)));
    }

    private void setUpTestResult() {
        recyclerView = findViewById(R.id.recyclerViewTEST);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        CakeryDomain.getInstance().readRecipes(new FirebaseListener() {
            @Override
            public void onTaskCompleted() {
                recipeList = CakeryDomain.getInstance().getCommonRecipeList();
                recipeAdapter = new RecipeAdapter(CravingResultActivity.this, getRecipes(recipeList));
                recyclerView.setAdapter(recipeAdapter);
            }
        });
    }

    private ArrayList<Recipe> getRecipes(ArrayList<Recipe> allRecipes) {
        ArrayList<Recipe> resultRecipes = new ArrayList<>();
        answer = CravingActivity.answer;
        for (int i = 0; i < allRecipes.size(); i++) {
            if (answer.equals("000")) { //CUPCAKE

            } else if (answer.equals("010")) { //KURU YEMİŞLİ KURABİYE

            } else if (answer.equals("020")) { //TARTALET

            } else if (answer.equals("100")) { //PEYNİRLİ BÖREK

            } else if (answer.equals("110")) { //PEYNİRLİ POĞAÇA

            } else if (answer.equals("120")) { //KRAKER

            } else if (answer.equals("001") && (allRecipes.get(i).getRecipeID().equals("1") ||
                    allRecipes.get(i).getRecipeID().equals("2"))) { //PASTA KEK = 1, 2
                resultRecipes.add(allRecipes.get(i));
            } else if (answer.equals("011")) { //ÇİKOLATALI KURABİYE

            } else if (answer.equals("021")) { //ŞERBETLİ TATLI

            } else if (answer.equals("101")) { //SEBZELİ BÖREK

            } else if (answer.equals("111")) { //SEBZELİ POĞAÇA

            } else if (answer.equals("121")) { //SİMİT

            } else if (answer.equals("002")) { //CHEESECAKE

            } else if (answer.equals("012")) { //MEYVELİ KURABİYE

            } else if (answer.equals("022")) { //SÜTLÜ TATLI

            } else if (answer.equals("102")) { //KIYMALI BÖREK

            } else if (answer.equals("112")) { //KIYMALI POĞAÇA

            } else if (answer.equals("122")) { //GALETA

            }
        }
        return resultRecipes;
    }

}