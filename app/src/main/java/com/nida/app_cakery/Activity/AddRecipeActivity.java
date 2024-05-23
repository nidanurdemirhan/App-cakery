package com.nida.app_cakery.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.nida.app_cakery.Models.Recipe_Request;
import com.nida.app_cakery.R;

import java.util.ArrayList;

public class AddRecipeActivity extends AppCompatActivity {

    private EditText etName, etDescription, etIngredients, etPortion;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        etName = findViewById(R.id.etName);
        etDescription = findViewById(R.id.etDescription);
        etIngredients = findViewById(R.id.etIngredients);
        etPortion = findViewById(R.id.etPortion);
        btnSubmit = findViewById(R.id.btnSubmit);

        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Geri düğmesine tıklandığında bir önceki aktiviteye dön
                finish();
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString().trim();
                String description = etDescription.getText().toString().trim();
                String ingredientsStr = etIngredients.getText().toString().trim();
                int portion = Integer.parseInt(etPortion.getText().toString().trim());

                // Split ingredients by comma
                String[] ingredientsArray = ingredientsStr.split(",");
                ArrayList<String> ingredients = new ArrayList<>();
                for (String ingredient : ingredientsArray) {
                    ingredients.add(ingredient.trim());
                }

                // Create Recipe_Request object
                Recipe_Request recipeRequest = new Recipe_Request("someID", name, description, ingredients, portion);

                // TODO: Submit the recipeRequest to Firebase or handle it as needed
            }
        });
    }
}
