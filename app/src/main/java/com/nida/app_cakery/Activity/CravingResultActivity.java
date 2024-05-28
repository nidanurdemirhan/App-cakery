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
            if (answer.equals("000") && allRecipes.get(i).getRecipeID().equals("11")) { //CUPCAKE = 11
                resultRecipes.add(allRecipes.get(i));
            } else if (answer.equals("010") && allRecipes.get(i).getRecipeID().equals("14")) { //KURU YEMİŞLİ KURABİYE = 14
                resultRecipes.add(allRecipes.get(i));
            } else if (answer.equals("020") && allRecipes.get(i).getRecipeID().equals("4")) { //TARTALET = 4
                resultRecipes.add(allRecipes.get(i));
            } else if (answer.equals("100") && allRecipes.get(i).getRecipeID().equals("12")) { //PEYNİRLİ BÖREK = 12
                resultRecipes.add(allRecipes.get(i));
            } else if (answer.equals("110") && (allRecipes.get(i).getRecipeID().equals("5")
                    || allRecipes.get(i).getRecipeID().equals("18"))) { //PEYNİRLİ POĞAÇA = 5, 18
                resultRecipes.add(allRecipes.get(i));
            } else if (answer.equals("120") && allRecipes.get(i).getRecipeID().equals("19")) { //KRAKER = 19
                resultRecipes.add(allRecipes.get(i));
            } else if (answer.equals("001") && (allRecipes.get(i).getRecipeID().equals("1") ||
                    allRecipes.get(i).getRecipeID().equals("2") || allRecipes.get(i).getRecipeID().equals("7"))) { //PASTA KEK = 1, 2, 7
                resultRecipes.add(allRecipes.get(i));
            } else if (answer.equals("011") && allRecipes.get(i).getRecipeID().equals("3")) { //ÇİKOLATALI KURABİYE = 3
                resultRecipes.add(allRecipes.get(i));
            } else if (answer.equals("021") && allRecipes.get(i).getRecipeID().equals("6")) { //ŞERBETLİ TATLI = 6
                resultRecipes.add(allRecipes.get(i));
            } else if (answer.equals("101") && (allRecipes.get(i).getRecipeID().equals("12")
                    || allRecipes.get(i).getRecipeID().equals("15"))) { //SEBZELİ BÖREK = 12, 15
                resultRecipes.add(allRecipes.get(i));
            } else if (answer.equals("111") && allRecipes.get(i).getRecipeID().equals("18")) { //SEBZELİ POĞAÇA = 18
                resultRecipes.add(allRecipes.get(i));
            } else if (answer.equals("121") && allRecipes.get(i).getRecipeID().equals("9")) { //SİMİT = 9
                resultRecipes.add(allRecipes.get(i));
            } else if (answer.equals("002") && allRecipes.get(i).getRecipeID().equals("8")) { //CHEESECAKE = 8
                resultRecipes.add(allRecipes.get(i));
            } else if (answer.equals("012") && allRecipes.get(i).getRecipeID().equals("17")) { //MEYVELİ KURABİYE = 17
                resultRecipes.add(allRecipes.get(i));
            } else if (answer.equals("022") && allRecipes.get(i).getRecipeID().equals("16")) { //SÜTLÜ TATLI = 16
                resultRecipes.add(allRecipes.get(i));
            } else if (answer.equals("102") && allRecipes.get(i).getRecipeID().equals("13")) { //KIYMALI BÖREK = 13
                resultRecipes.add(allRecipes.get(i));
            } else if (answer.equals("112") && allRecipes.get(i).getRecipeID().equals("20")) { //KIYMALI POĞAÇA = 20
                resultRecipes.add(allRecipes.get(i));
            } else if (answer.equals("122") && allRecipes.get(i).getRecipeID().equals("10")) { //GALETA = 10
                resultRecipes.add(allRecipes.get(i));
            }
        }
        return resultRecipes;
    }

}