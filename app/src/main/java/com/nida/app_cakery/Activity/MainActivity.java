package com.nida.app_cakery.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.nida.app_cakery.Domain.CakeryDomain;
import com.nida.app_cakery.Models.Ingredient;
import com.nida.app_cakery.Models.IngredientInRecipe;
import com.nida.app_cakery.Models.Recipe;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(MainActivity.this, LoginActivity.class));

        /*Ingredient ingredient =  new Ingredient("2");
        IngredientInRecipe iR = new IngredientInRecipe("23", ingredient, 1, "cup");
        ArrayList<IngredientInRecipe> arr = new ArrayList<>();
        arr.add(iR);
        Recipe recipe = new Recipe("123", "deneme", "k", arr, 1, (new Random()).nextLong(), "", "");
        CakeryDomain.getInstance().addRecipe(recipe);

         */
    }
}